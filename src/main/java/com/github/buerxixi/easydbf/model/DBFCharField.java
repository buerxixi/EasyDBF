package com.github.buerxixi.easydbf.model;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFFieldType;

import java.nio.charset.Charset;

/**
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class DBFCharField extends DBFField {

    public DBFCharField(String name, Integer size) {
        this(DBFConstant.DEFAULT_CHARSET, name, size);
    }

    DBFCharField(Charset charset, String name, Integer size) {
        super(charset, name, DBFFieldType.CHARACTER,-1, size.byteValue(), (byte) 0);
    }
}
