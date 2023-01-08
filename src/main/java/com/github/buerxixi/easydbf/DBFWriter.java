package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.convert.AbstractTypeConverter;
import com.github.buerxixi.easydbf.convert.CharacterConverter;
import com.github.buerxixi.easydbf.convert.DateConverter;
import com.github.buerxixi.easydbf.convert.NumericConverter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
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

    static Map<String, AbstractTypeConverter> strategyMap = new ConcurrentHashMap<>();

    static {
        strategyMap.put(DBFConstant.CHARACTER, new CharacterConverter());
        strategyMap.put(DBFConstant.NUMERIC, new NumericConverter());
        strategyMap.put(DBFConstant.DATE, new DateConverter());
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
    public boolean create(ArrayList<DBFField> list) {

        // 创建上级目录
         Files.createDirectories(Paths.get(this.table.getFilename()));

        DBFHeader header = new DBFHeader();

        // 获取header
        // 获取fields
        // 写入
//        DBFConstant.END_OF_FIELD;
//        DBFConstant.END_OF_DATA;

        // 创建包含表结构
        return false;
    }

    /**
     * 删除表空间
     *
     * @return 是否成功删除
     */
    @SneakyThrows
    public boolean drop() {
        // 其实就是直接删除文件
        return Files.deleteIfExists(Paths.get(this.table.getFilename()));
    }

    /**
     * 根据id 更新某个字段某个特定的值
     */
    @SneakyThrows
    public void update(String pk, String value, String updateKey ,String updateValue) {
        this.table.readTable();

        // 查找key
        Optional<DBFField> pkFiled = this.table.getFields().stream().filter(filed -> filed.getName().equals(pk)).findFirst();
        if(!pkFiled.isPresent()){
            return;
        }

        // 查找更新key
        Optional<DBFField> updateFiled = this.table.getFields().stream().filter(filed -> filed.getName().equals(updateKey)).findFirst();
        if(!updateFiled.isPresent()){
            return;
        }

        // 先查询 TODO:此处可以改为直接遍历修改 减少内存使用
        DBFReader reader = new DBFReader(this.table.getFilename(), this.table.getCharset());
        List<DBFRow> records = reader.find(pk, value);
        if (records.isEmpty()) return;
        Integer index = this.table.getFields().subList(0, pkFiled.get().getIndex()).stream().map(DBFField::getSize).reduce(0, Integer::sum);
        // 直接修改
        DBFHeader header = reader.getTable().getHeader();
        try (RandomAccessFile raf = new RandomAccessFile(this.table.getFilename(), "rw")) {
            for (DBFRow record : records) {
                raf.seek(header.getHeaderLength() + (long) header.getRecordLength() * record.getIndex() + index + 1 );
                raf.write(strategyMap.get(updateFiled.get().getType()).toBytes(updateValue, updateFiled.get()));
            }
        }

        // 更新数量和时间
        updateHeader();
    }

    @SneakyThrows
    public void delete(String key, String value) throws IOException {
        this.table.readTable();

        // 先查询 TODO:此处可以改为直接遍历修改 减少内存使用
        DBFReader reader = new DBFReader(this.table.getFilename(), this.table.getCharset());
        List<DBFRow> records = reader.find(key, value);
        DBFHeader header = reader.getTable().getHeader();
        try (RandomAccessFile raf = new RandomAccessFile(this.table.getFilename(), "rw")) {
            for (DBFRow record : records) {
                raf.seek(header.getHeaderLength() + (long) header.getRecordLength() * record.getIndex());
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
            raf.write(ByteUtils.writeIntLE(numberOfRecords));

        }
    }
}
