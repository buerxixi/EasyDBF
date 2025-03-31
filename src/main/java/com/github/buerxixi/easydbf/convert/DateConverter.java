package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.pojo.DBFField;

/**
 * 日期转化类
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class DateConverter implements TypeConverterStrategy {


    @Override
    public String fromBytes(DBFField field,byte[] bytes) {
        return new String(bytes);
    }

    @Override
    public byte[] toBytes(DBFField field,String s) {
        return s.getBytes();
    }
}
