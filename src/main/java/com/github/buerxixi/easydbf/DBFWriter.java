package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.convert.AbstractTypeConverter;
import com.github.buerxixi.easydbf.convert.CharacterConverter;
import com.github.buerxixi.easydbf.convert.DateConverter;
import com.github.buerxixi.easydbf.convert.NumericConverter;
import com.github.buerxixi.easydbf.pojo.*;
import com.github.buerxixi.easydbf.util.ByteUtils;
import com.github.buerxixi.easydbf.util.FieldUtils;
import com.github.buerxixi.easydbf.util.HeaderUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DBFWriter
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DBFWriter {

    static Map<DBFFieldType, AbstractTypeConverter> strategyMap = new ConcurrentHashMap<>();

    static {
        strategyMap.put(DBFFieldType.CHARACTER, new CharacterConverter());
        strategyMap.put(DBFFieldType.NUMERIC, new NumericConverter());
        strategyMap.put(DBFFieldType.DATE, new DateConverter());
    }

    final private DBFTable table;

    /**
     * 创建表空间
     */
    public DBFWriter(String filename, Charset charset) {
        this.table = new DBFTable(filename, charset);
    }

    @SneakyThrows
    public void add(Map<String, String> map) {

        // 获取元素header
        this.table.readTable();
        byte[] recordBytes = this.table.getFields().stream()
                // 转换为对应的字节
                // TODO: 该处为转化类的重点
                .map(field -> strategyMap.get(field.getType()).toBytes(map.get(field.getName()), field))
                .reduce(DBFConstant.UNDELETED_OF_FIELD_BYTES, ArrayUtils::addAll);
        byte[] writeBytes = ArrayUtils.add(recordBytes, DBFConstant.END_OF_DATA);

        try (RandomAccessFile raf = new RandomAccessFile(this.table.getFilename(), "rw")) {
            raf.seek(raf.length() - 1);
            raf.write(writeBytes);
        }

        // 更新数量和时间
        updateHeader();
    }

    @SneakyThrows
    public void create(List<DBFField> fields) {

        // 获取文件
        File file = new File(this.table.getFilename());
        if(file.exists()){
            throw new RuntimeException("文件已存在");
        }

        file.getParentFile().mkdirs();

        // 头信息
        byte[] headerBytes = HeaderUtils.toBytes();

        // 字段信息
        byte[] fieldsBytes = FieldUtils.toBytes(fields);

        // 8-9文件头长度
        // 头信息+filed信息+结束符
        Integer headerLength = (fields.size() + 1) * 32 + 1;
        ByteUtils.writerInt16(headerBytes, headerLength, 8);

        // 10-11每条记录长度
        // 字符信息+结束符
        Integer recordLength = fields.stream()
                .map(field -> Optional.of(field.getSize()).orElse(0))
                .reduce(0,Integer::sum) + 1;
        ByteUtils.writerInt16(headerBytes, recordLength, 10);

        // 写入数据
        try (RandomAccessFile raf = new RandomAccessFile(this.table.getFilename(), "rw")) {
            raf.write(headerBytes);
            raf.write(fieldsBytes);
            // 添加结束符
            raf.write(DBFConstant.END_OF_FIELD);
            raf.write(DBFConstant.END_OF_DATA);
        }
    }

    /**
     * 删除表空间
     */
    @SneakyThrows
    public void drop() {
        // 其实就是直接删除文件
        Files.deleteIfExists(Paths.get(this.table.getFilename()));
    }

    /**
     * 根据id 更新某个字段某个特定的值
     */
    @SneakyThrows
    public void update(String pk, String value, String updateKey ,String updateValue) {
        this.table.readTable();

        // 查找key
        Optional<DBFInnerField> pkFiled = this.table.findField(pk);

        // 查找更新key
        Optional<DBFInnerField> updateFiled = this.table.findField(updateKey);
        if(!pkFiled.isPresent() || !updateFiled.isPresent()){
            return;
        }

        // 先查询 TODO: 此处可以改为直接遍历修改 减少内存使用
        DBFReader reader = new DBFReader(this.table.getFilename(), this.table.getCharset());
        List<DBFRow> records = reader.find(pk, value);
        if (records.isEmpty()) return;
        Integer index = this.table.getFields().subList(0, pkFiled.get().getFieldNum()).stream().map(DBFField::getSize).reduce(0, Integer::sum);
        // 直接修改
        DBFHeader header = reader.getTable().getHeader();
        try (RandomAccessFile raf = new RandomAccessFile(this.table.getFilename(), "rw")) {
            for (DBFRow record : records) {
                raf.seek(header.getHeaderLength() + (long) header.getRecordLength() * record.getRowNum() + index + 1 );
                raf.write(strategyMap.get(updateFiled.get().getType()).toBytes(updateValue, updateFiled.get()));
            }
        }

        // 更新数量和时间
        updateHeader();
    }

    @SneakyThrows
    public void delete(String key, String value) {
        this.table.readTable();

        // 先查询 TODO:此处可以改为直接遍历修改 减少内存使用
        DBFReader reader = new DBFReader(this.table.getFilename(), this.table.getCharset());
        List<DBFRow> records = reader.find(key, value);
        DBFHeader header = reader.getTable().getHeader();
        try (RandomAccessFile raf = new RandomAccessFile(this.table.getFilename(), "rw")) {
            for (DBFRow record : records) {
                raf.seek(header.getHeaderLength() + (long) header.getRecordLength() * record.getRowNum());
                raf.write(DBFConstant.DELETED_OF_FIELD);
            }
        }

        // 更新数量和时间
        updateHeader();
    }

    /**
     * 更新头信息数据
     */
    @SneakyThrows
    private void updateHeader() {

        try (RandomAccessFile raf = new RandomAccessFile(this.table.getFilename(), "rw")) {
            raf.seek(1);
            // 获取当前日期
            LocalDate now = LocalDate.now();
            // 更新日期
            raf.writeByte(now.getYear() - DBFConstant.START_YEAR);
            raf.writeByte(now.getMonthValue());
            raf.writeByte(now.getDayOfYear());

            // 更新数量 TODO:数量计算有问题
            Integer numberOfRecords = Math.toIntExact((raf.length() - this.table.getHeader().getHeaderLength() - 1) / this.table.getHeader().getRecordLength());
            raf.write(ByteUtils.int32LE(numberOfRecords));

        }
    }
}
