package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.util.ByteUtils;
import lombok.Getter;

/**
 * DBFField
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
@Getter
public class DBFInnerField extends DBFField {

    /**
     * 索引
     */
    private final Integer fieldNum;

    private final byte[] bytes;

    public DBFInnerField(Integer fieldNum, byte[] bytes) {
        // 索引
        this.fieldNum = fieldNum;
        // 字段名称ASCII
        this.name = ByteUtils.byteToStr(bytes);
        // 数据类型
        this.type = DBFFieldType.forString(String.valueOf((char) bytes[11]));
        // 字段长度
        this.size = (int) bytes[16];
        // 字段精度
        this.digits = (int) bytes[17];
        // 数据
        this.bytes = bytes;
    }

}
