package com.github.buerxixi.easydbf;

import lombok.Getter;
import java.nio.charset.Charset;

/**
 * DBFRow
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
@Getter
public class DBFRecord {

    private final Integer index;

    private final byte[] bytes;

    private final Charset charset;

    private final String type;

    private  DBFField field;

    public DBFRecord(Integer index, String type, Charset charset, byte[]bytes) {
        this.index = index;
        this.type = type;
        this.charset = charset;
        this.bytes = bytes;
    }

    public String getString(){
        return new String(this.bytes, this.charset).trim();
    }

    @Override
    public String toString(){
        return this.getString();
    }
}
