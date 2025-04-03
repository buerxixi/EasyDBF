package com.github.buerxixi.easydbf.model;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFFieldType;

/**
 * 表示DBF文件中的日期字段，继承自DBFField类。
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">刘家强</a>
 * @since 2025/04/01 16:58
 */
public class DBFDateField extends DBFField {

    public DBFDateField(String name) {
        super(DBFConstant.DEFAULT_CHARSET, name, DBFFieldType.DATE,-1, (byte) 8, (byte) 0);
    }
}
