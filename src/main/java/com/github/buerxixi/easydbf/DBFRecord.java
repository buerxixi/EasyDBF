package com.github.buerxixi.easydbf;

import lombok.Data;

import java.nio.charset.Charset;

/**
 * DBFRow
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
@Data
public class DBFRecord {

    private DBFRow row;

    private DBFField field;

    private final byte[] bytes;

    public DBFRecord(DBFField field, DBFRow row, byte[]bytes) {
        this.field = field;
        this.row = row;
        this.bytes = bytes;
    }

    public String getString(){
        // TODO:
        return new String(this.bytes, Charset.forName("GBK")).trim();
    }

    @Override
    public String toString(){
        return this.getString();
    }
}
