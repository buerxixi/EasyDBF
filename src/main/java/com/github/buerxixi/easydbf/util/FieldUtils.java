package com.github.buerxixi.easydbf.util;

import com.github.buerxixi.easydbf.DBFField;
import com.github.buerxixi.easydbf.DBFFieldType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class FieldUtils {

    public static byte[] toBytes(List<DBFField> fields) {
        return fields.stream().map(FieldUtils::toBytes).reduce(ArrayUtils::addAll).orElseThrow(() -> new RuntimeException("生成Field失败"));
    }

    public static byte[] toBytes(DBFField field) {

        // 预处理数据
        preprocess(field);

        // 默认字段
        byte[] bytes = new byte[32];
        // 0-10字节字段名称
        String name = field.getName();
        ByteUtils.merge(bytes, StringUtils.substring(name, 0, 11).getBytes());

        // 11字节字段类型
        String type = field.getType().getType();
        bytes[11] = type.getBytes()[0];

        // 16字节字段长度
        Optional.ofNullable(field.getSize()).ifPresent(size -> bytes[16] = (byte) (size & 0xFF));

        // 17字节字段精度
        Optional.ofNullable(field.getDigits()).ifPresent(digits -> bytes[17] = (byte) (digits & 0xFF));

        return bytes;
    }

    public static void preprocess(DBFField field){
        if (field.getType().equals(DBFFieldType.CHARACTER)) {
            field.setDigits(0);
        }

        if(field.getType().equals(DBFFieldType.DATE)) {
            field.setSize(8);
            field.setDigits(0);
        }


        if(field.getType().equals(DBFFieldType.NUMERIC)) {
//            field.setSize(8);
//            field.setDigits(0);
        }
    }
}
