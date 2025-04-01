package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.model.DBFField;

public interface TypeConverterStrategy {

    String fromBytes(DBFField field, byte[] bytes);

    /**
     * 如无特殊说明，字段类型“Character”表示文本型字符串，填写方式为左对齐右补空格，
     * 而“Numeric”为数字型字符串填写方式为右对齐，左补空格。
     * @param field
     * @param s
     * @return
     */
    byte[] toBytes(DBFField field, String s);
}
