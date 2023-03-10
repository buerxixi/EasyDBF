package com.github.buerxixi.easydbf.pojo;

import lombok.SneakyThrows;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * 行迭代类
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class DBFRowIterator implements Iterator<DBFRow>, AutoCloseable {

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
    public DBFRowIterator(DBFTable table) {
        this.table = table;
        raf = new RandomAccessFile(table.getFilename(), "rw");
    }

    /**
     * 是否有下一个文件
     * <p>
     * TODO:该处为查询类的重点中的重点
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
     */
    @SneakyThrows
    @Override
    public DBFRow next() {

        if(this.index < 0) {
            throw new RuntimeException("请先获取next,当前游标为 " + this.index);
        }

        // 获取header
        DBFHeader header = table.getHeader();

        // 跳转位置
        raf.seek(header.getHeaderLength() + (long) header.getRecordLength() * this.index);

        byte[] bytes = new byte[header.getRecordLength()];
        raf.read(bytes);
        return new DBFRow(this.index, bytes, table);

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
