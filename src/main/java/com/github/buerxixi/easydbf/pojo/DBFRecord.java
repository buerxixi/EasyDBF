package com.github.buerxixi.easydbf.pojo;

import lombok.Data;

/**
 * 记录数据
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
@Data
public class DBFRecord {

    private DBFRow row;

    private DBFInnerField field;

    private final byte[] bytes;

    public DBFRecord(DBFInnerField field, DBFRow row, byte[]bytes) {
        this.field = field;
        this.row = row;
        this.bytes = bytes;
    }

    public String getString(){
        // TODO:
        return new String(this.bytes, field.getTable().getCharset()).trim();
    }

    @Override
    public String toString(){
        return this.getString();
    }
}
