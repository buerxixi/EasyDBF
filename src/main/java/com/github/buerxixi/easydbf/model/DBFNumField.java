package com.github.buerxixi.easydbf.model;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFFieldType;

/**
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class DBFNumField extends DBFField {

    public DBFNumField(String name, Integer size, Integer digits) {
        super(DBFConstant.DEFAULT_CHARSET, name,  DBFFieldType.NUMERIC,-1, size.byteValue(), digits.byteValue());
    }
}
