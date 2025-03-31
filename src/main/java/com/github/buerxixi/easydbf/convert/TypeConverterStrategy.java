package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.pojo.DBFField;

public interface TypeConverterStrategy {

    String fromBytes(DBFField field, byte[] bytes);

    byte[] toBytes(DBFField field, String s);
}
