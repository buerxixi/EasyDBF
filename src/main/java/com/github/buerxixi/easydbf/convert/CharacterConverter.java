package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.DBFInnerField;
import com.github.buerxixi.easydbf.util.ByteUtils;
import com.github.buerxixi.easydbf.DBFField;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class CharacterConverter extends AbstractTypeConverter {

    @Override
    public byte[] toBytes(String s, DBFInnerField field) {
        byte[] bytes = new byte[field.getSize()];
        Arrays.fill(bytes, (byte) ' ');
        if (StringUtils.isNotEmpty(s)) {
            return ByteUtils.merge(bytes, s.getBytes(field.getTable().getCharset()));
        }
        return new byte[field.getSize()];
    }
}
