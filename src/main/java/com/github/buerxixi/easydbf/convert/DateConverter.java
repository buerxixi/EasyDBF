package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.util.ByteUtils;
import com.github.buerxixi.easydbf.DBFField;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DateConverter extends AbstractTypeConverter {

    @Override
    public byte[] toBytes(String s, DBFField field, Charset charset) {
        // 日期为8字节字段
        // TODO: 是否校验正确性
        byte[] bytes = new byte[8];
        if (StringUtils.isNotEmpty(s)) {
            return ByteUtils.merge(bytes, s.getBytes());
        }
        return bytes;
    }
}
