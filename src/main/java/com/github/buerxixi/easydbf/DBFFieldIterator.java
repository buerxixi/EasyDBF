package com.github.buerxixi.easydbf;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DBFFieldIterator
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DBFFieldIterator implements Iterator<DBFRecord>, AutoCloseable {

    /**
     * table数据
     */
    final private DBFTable table;

    /**
     * 文件引用
     */
    final private RandomAccessFile raf;

    public Boolean getSkipDeleted() {
        return skipDeleted;
    }

    public void setSkipDeleted(Boolean skipDeleted) {
        this.skipDeleted = skipDeleted;
    }

    /**
     * 是否跳过删除
     */
    private Boolean skipDeleted = true;

    /**
     * 游标
     */
    private Integer index = -1;


    @SneakyThrows
    public DBFFieldIterator(DBFTable table) {
        this.table = table;
        raf = new RandomAccessFile(table.getFilename(), "rw");
    }

    /**
     * 是否有下一个文件
     *
     * @return
     */
    @SneakyThrows
    @Override
    public boolean hasNext() {

        // 获取header
        DBFHeader header = table.getHeader();

        // 下一个游标
        int nextIndex = this.index + 1;

        // 超出数量
        if (nextIndex >= header.getNumberOfRecords()) return false;

        // 判断长度是否超长
        if (header.getHeaderLength() + (long) nextIndex * header.getRecordLength() > raf.length()) return false;


        // 判断下一个是否为终止符
        raf.seek(header.getHeaderLength() + (long) this.index * header.getRecordLength());
        byte nextByte = raf.readByte();

        if (nextByte == DBFConstant.END_OF_DATA) {
            return false;
        }

        this.index = nextIndex;

        // 是否读取删除字段
        if(skipDeleted && nextByte == DBFConstant.DELETED_OF_FIELD) {
            return this.hasNext();
        }

        return true;
    }

    /**
     * 获取下一个文件
     *
     * @return
     */
    @SneakyThrows
    @Override
    public DBFRecord next() {

        if(this.index < 0) {
            throw new RuntimeException("请先获取next,当前游标为-1");
        }

        // 获取header
        DBFHeader header = table.getHeader();

        // 获取fields
        List<DBFField> fields = table.getFields();

        // 获取编码
        Charset charset = table.getCharset();

        // 跳转位置
        raf.seek(header.getHeaderLength() + (long) header.getRecordLength() * this.index);

        byte[] bytes = new byte[header.getRecordLength()];
        raf.read(bytes);
        DBFRecord record = new DBFRecord();
        record.setIndex(this.index);
        record.setDeleted(bytes[0] == DBFConstant.DELETED_OF_FIELD);

        List<Integer> sizes = fields.stream().map(DBFField::getSize).collect(Collectors.toList());

        // 跳过第一个字符位删除位
        int startIndex = 1;
        for (int i = 0; i < sizes.size(); i++) {
            byte[] subarray = ArrayUtils.subarray(bytes, startIndex, startIndex + sizes.get(i));

            startIndex += sizes.get(i);

            // 获取类型
            DBFField field = fields.get(i);

            // 判断类型
            // 初始化数据

            record.rows.add(new DBFRow(field.getType(), this.table.getCharset(), subarray));
        }

        return record;
    }

    public void close() {
        try {
            if (raf != null) {
                raf.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
}
