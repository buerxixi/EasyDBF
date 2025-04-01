package com.github.buerxixi.easydbf.convert.impl;

import com.github.buerxixi.easydbf.convert.TypeConverterStrategy;
import com.github.buerxixi.easydbf.model.DBFField;

/**
 * 日期转化类
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 * @since 2025/04/01 16:58
 */
public class DateTypeConverter implements TypeConverterStrategy {


    @Override
    public String fromBytes(DBFField field,byte[] bytes) {
        return new String(bytes);
    }

    @Override
    public byte[] toBytes(DBFField field,String s) {
        return s.getBytes();
    }
}
