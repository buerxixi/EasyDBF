package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.pojo.DBFField;
import com.github.buerxixi.easydbf.util.ByteUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 字符串转化类
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class CharacterConverter implements TypeConverterStrategy {


    @Override
    public String fromBytes(DBFField field,byte[] bytes) {
        return new String(bytes, field.getCharset()).trim();
    }

    @Override
    public byte[] toBytes(DBFField field,String s) {
        byte[] bytes = new byte[field.getSize()];
        Arrays.fill(bytes, (byte) ' ');
        if (StringUtils.isNotEmpty(s)) {
            return ByteUtils.merge(bytes, s.getBytes(field.getCharset()));
        }
        return new byte[field.getSize()];
    }
}
