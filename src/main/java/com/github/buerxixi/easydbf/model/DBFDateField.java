package com.github.buerxixi.easydbf.model;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFFieldType;

/**
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class DBFDateField extends DBFField {

    public DBFDateField(String name) {
        super(DBFConstant.DEFAULT_CHARSET, name, DBFFieldType.DATE,-1, (byte) 8, (byte) 0);
    }
}
