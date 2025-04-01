package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFHeader;
import com.github.buerxixi.easydbf.util.ByteUtils;
import com.github.buerxixi.easydbf.util.DBFUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void updateById(Integer rowId, String fileName, String value) throws IOException {

    }

    /**
     * 更新数据
     */
    public void updateById(Integer rowId, Map<String, String> values) throws IOException {

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


}
