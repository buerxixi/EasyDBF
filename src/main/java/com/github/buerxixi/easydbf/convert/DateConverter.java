package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.DBFInnerField;
import com.github.buerxixi.easydbf.util.ByteUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DateConverter extends AbstractTypeConverter {

    @Override
    public byte[] toBytes(String s, DBFInnerField field) {
        // 日期为8字节字段
        // TODO: 是否校验正确性
        byte[] bytes = new byte[8];
        if (StringUtils.isNotEmpty(s)) {
            return ByteUtils.merge(bytes, s.getBytes());
        }
        return bytes;
    }
}
