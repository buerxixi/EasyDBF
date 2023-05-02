package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.convert.AbstractTypeConverter;
import com.github.buerxixi.easydbf.convert.CharacterConverter;
import com.github.buerxixi.easydbf.convert.DateConverter;
import com.github.buerxixi.easydbf.convert.NumericConverter;
import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFField;
import com.github.buerxixi.easydbf.pojo.DBFFieldType;
import com.github.buerxixi.easydbf.pojo.DBFHeader;
import com.github.buerxixi.easydbf.util.ByteUtils;
import com.github.buerxixi.easydbf.util.DBFUtils;
import com.github.buerxixi.easydbf.util.FieldUtils;
import com.github.buerxixi.easydbf.util.HeaderUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DBFWriter
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class DBFWriter {

    static Map<DBFFieldType, AbstractTypeConverter> strategyMap = new ConcurrentHashMap<>();

    static {
        strategyMap.put(DBFFieldType.CHARACTER, new CharacterConverter());
        strategyMap.put(DBFFieldType.NUMERIC, new NumericConverter());
        strategyMap.put(DBFFieldType.DATE, new DateConverter());
    }

    final private String filename;
    final private Charset charset;

    /**
     * 创建表空间
     */
    public DBFWriter(String filename, Charset charset) {
        this.filename = filename;
        this.charset = charset;
    }


    public void update(Integer rownum, String key, String value) throws IOException {
        DBFHeader header = DBFUtils.getHeader(filename);
        int findIndex = -1;
        for (int i = 0; i < header.getFields().size(); i++) {
            if (header.getFields().get(i).getName().equals(key)) {
                findIndex = i;
            }
        }

        if (findIndex == -1) {
            return;
        }

        Integer sumLength = header.getFields().stream().limit(findIndex).map(DBFField::getSize).reduce(0, Integer::sum);
        DBFField field = header.getFields().get(findIndex);
        AbstractTypeConverter converter = strategyMap.get(field.getType());
        byte[] bytes = converter.toBytes(value, field, charset);

        try (RandomAccessFile raf = new RandomAccessFile(this.filename, "rw")) {
            raf.seek(header.getHeaderLength() + (long) (rownum - 1) * header.getRecordLength() + 1 + sumLength);
            raf.write(bytes);
            updateHeader(raf, header);
        }
    }

    public void delete(Integer rownum) throws IOException {
        DBFHeader header = DBFUtils.getHeader(filename);
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            raf.seek(header.getHeaderLength() + (long) (rownum - 1) * header.getRecordLength());
            raf.write(DBFConstant.DELETED_OF_FIELD);
            updateHeader(raf, header);
        }
    }

    public void append(Map<String, String> map) throws IOException {

        // 获取元素header
        DBFHeader header = DBFUtils.getHeader(filename);

        byte[] recordBytes = header.getFields().stream()
                // 转换为对应的字节
                // TODO: 该处为转化类的重点
                .map(field -> strategyMap.get(field.getType()).toBytes(map.get(field.getName()), field, charset))
                .reduce(new byte[0], ArrayUtils::addAll);

        try (RandomAccessFile raf = new RandomAccessFile(this.filename, "rw")) {
            raf.seek(raf.length() - 1);
            // 写入未删除
            raf.write(DBFConstant.UNDELETED_OF_FIELD);
            // 写入数据
            raf.write(recordBytes);
            // 写入截止符
            raf.write(DBFConstant.END_OF_DATA);
            updateHeader(raf, header);
        }
    }

    @SneakyThrows
    public void create(List<DBFField> fields) {

        // 获取文件
        File file = new File(this.filename);
        if (file.exists()) {
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
                .reduce(0, Integer::sum) + 1;
        ByteUtils.writerInt16(headerBytes, recordLength, 10);

        // 写入数据
        try (RandomAccessFile raf = new RandomAccessFile(this.filename, "rw")) {
            raf.write(headerBytes);
            raf.write(fieldsBytes);
            // 添加结束符
            raf.write(DBFConstant.END_OF_FIELD);
            raf.write(DBFConstant.END_OF_DATA);
        }
    }

    /**
     * 更新头信息数据
     */
    @SneakyThrows
    private void updateHeader(RandomAccessFile raf, DBFHeader header) {
        // 更新时间内
        raf.seek(1);
        // 获取当前日期
        LocalDate now = LocalDate.now();
        // 更新日期
        raf.writeByte(now.getYear() - DBFConstant.START_YEAR);
        raf.writeByte(now.getMonthValue());
        raf.writeByte(now.getDayOfYear());

        // 更新数量 TODO:数量计算有问题
        Integer numberOfRecords = Math.toIntExact((raf.length() - header.getHeaderLength() - 1) / header.getRecordLength());
        // TODO: raf.seek(null);
        raf.write(ByteUtils.int32LE(numberOfRecords));
    }
}
