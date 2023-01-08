package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.ByteUtils;
import com.github.buerxixi.easydbf.DBFField;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DateConverter extends AbstractConverter {

    @Override
    public byte[] toBytes(String s, DBFField field) {
        // 日期为8字节字段
        // TODO: 是否校验正确性
        byte[] bytes = new byte[8];
        if (StringUtils.isNotEmpty(s)) {
            return ByteUtils.merge(bytes, s.getBytes());
        }
        return bytes;
    }
}
