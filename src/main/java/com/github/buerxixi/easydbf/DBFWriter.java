package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.convert.TypeConverterStrategy;
import com.github.buerxixi.easydbf.convert.TypeConverterStrategyFactory;
import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFField;
import com.github.buerxixi.easydbf.pojo.DBFHeader;
import com.github.buerxixi.easydbf.util.ByteUtils;
import com.github.buerxixi.easydbf.util.DBFUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DBFWriter {
    final private String filename;
    final private Charset charset;

    DBFWriter(String filename, Charset charset) {
        this.filename = filename;
        this.charset = charset;
    }

    DBFWriter(String filename) {
        this(filename, DBFConstant.DEFAULT_CHARSET);
    }

    /**
     * 更新头部信息
     */
    private void updateHeader(DBFHeader header, RandomAccessFile raf) throws IOException {

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

    /**
     * 更新数据
     */
    public void updateById(Integer rowId, String key, String value) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put(key, value);
        updateById(rowId, map);
    }

    /**
     * 更新数据
     */
    public void updateById(Integer rowId, Map<String, String> values) throws IOException {
        DBFHeader header = DBFUtils.getHeader(filename);
        List<DBFField> fields = DBFUtils.getFields(filename, charset);
        Map<String, DBFField> name2Field = fields.stream().collect(Collectors.toMap(DBFField::getName, f -> f));
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            for (Map.Entry<String, String> entry : values.entrySet()) {
                DBFField field = name2Field.get(entry.getKey());
                if (field != null) {
                    raf.seek(header.getHeaderLength() + (long) rowId * header.getRecordLength() + field.getOffset());
                    // 获取转换类
                    TypeConverterStrategy strategy = TypeConverterStrategyFactory.getStrategy(field.getType());
                    byte[] bytes = strategy.toBytes(field, entry.getValue());
                    raf.write(bytes);
                }
            }
            updateHeader(header, raf);
        }

    }

    /**
     * 删除（更新删除状态）
     */
    public void deleteById(Integer rowId) throws IOException {
        List<Integer> rowIds = new ArrayList<>();
        rowIds.add(rowId);
        deleteByIds(rowIds);
    }

    /**
     * 删除（更新删除状态）
     */
    public void deleteByIds(List<Integer> rowIds) throws IOException {
        DBFHeader header = DBFUtils.getHeader(filename);
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            // 更新时间内
            for (Integer rowId : rowIds) {
                raf.seek(header.getHeaderLength() + (long) header.getRecordLength() * rowId);
                raf.writeByte(DBFConstant.DELETED_OF_FIELD);
            }

            updateHeader(header, raf);
        }
    }

    /**
     * 创建
     */

    /**
     * 添加数据
     */
    public void insert(Map<String, String> value) throws IOException {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        list.add(value);
        insert(list);
    }

    /**
     * 添加数据
     */
    public void insert(List<Map<String, String>> values) throws IOException {
        DBFHeader header = DBFUtils.getHeader(filename);
        List<DBFField> fields = DBFUtils.getFields(filename, charset);
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            Map<String, DBFField> name2Field = fields.stream().collect(Collectors.toMap(DBFField::getName, f -> f));
            for (Map<String, String> value : values) {

                byte[] bytes = new byte[header.getRecordLength()];
                // 填入数据
                Arrays.fill(bytes, DBFConstant.UNDELETED_OF_FIELD);



                // DBFConstant.UNDELETED_OF_FIELD;
                // 根据偏移量依次写入数据
                for (Map.Entry<String, String> entry : value.entrySet()) {

                    // 依次获取字段
                    DBFField field = name2Field.get(entry.getKey());

                    // 更新字段所对应的值
                    if (field != null) {
                        // 获取转换类
                        TypeConverterStrategy strategy = TypeConverterStrategyFactory.getStrategy(field.getType());
                        byte[] filedBytes = strategy.toBytes(field, entry.getValue());
                        System.arraycopy(filedBytes, 0, bytes, field.getOffset(), field.getSize());
                    }
                }

                raf.seek(header.getHeaderLength() + (long) header.getRecordLength() * header.getNumberOfRecords());
                raf.write(bytes);
            }

            raf.write(DBFConstant.END_OF_DATA);
            updateHeader(header, raf);
        }
    }


}
