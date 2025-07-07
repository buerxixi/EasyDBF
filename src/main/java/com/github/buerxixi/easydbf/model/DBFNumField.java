package com.github.buerxixi.easydbf.model;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFFieldType;

/**
 * 该类表示DBF文件中的数值字段，继承自DBFField类。
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">刘家强</a>
 * @since 2025/04/01 16:58
 */
public class DBFNumField extends DBFField {

    public DBFNumField(String name, Integer size, Integer digits) {
        super(DBFConstant.DEFAULT_CHARSET, name,  DBFFieldType.NUMERIC,-1, size, digits);
    }
}
