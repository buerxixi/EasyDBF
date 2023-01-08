package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.ByteUtils;
import com.github.buerxixi.easydbf.DBFField;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class CharacterConverter extends AbstractTypeConverter {

    @Override
    public byte[] toBytes(String s, DBFField field) {
        byte[] bytes = new byte[field.getSize()];
        if (StringUtils.isNotEmpty(s)) {
            return ByteUtils.merge(bytes, s.getBytes(field.getCharset()));
        }
        return bytes;
    }
}
