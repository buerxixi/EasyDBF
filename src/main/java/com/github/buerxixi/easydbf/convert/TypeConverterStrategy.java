package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.model.DBFField;

/**
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public interface TypeConverterStrategy {

    /**
     * 将字节数组转换为字符串。
     *
     * @param field 包含字段定义的DBFField对象
     * @param bytes 要转换为字符串的字节数组
     * @return 转换后的字符串
     */
    String fromBytes(DBFField field, byte[] bytes);

    /**
     * 将字符串转换为字节数组。
     *
     * @param field 包含字段定义的DBFField对象
     * @param s 要转换为字节数组的字符串
     * @return 转换后的字节数组
     */
    byte[] toBytes(DBFField field, String s);
}
