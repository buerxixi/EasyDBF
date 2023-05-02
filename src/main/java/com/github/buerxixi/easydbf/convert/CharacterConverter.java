package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.pojo.DBFField;
import com.github.buerxixi.easydbf.util.ByteUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 字符串转化类
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class CharacterConverter extends AbstractTypeConverter {

    @Override
    public byte[] toBytes(String s, DBFField field,  Charset charset) {
        byte[] bytes = new byte[field.getSize()];
        Arrays.fill(bytes, (byte) ' ');
        if (StringUtils.isNotEmpty(s)) {
            return ByteUtils.merge(bytes, s.getBytes(charset));
        }
        return new byte[field.getSize()];
    }
}
