package com.github.buerxixi.easydbf;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DBFField
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
@Data
@NoArgsConstructor
public class DBFField {

    /**
     * 字段的名称
     */
    private String name;

    /**
     * 数据类型
     */
    private String type;
    /**
     * 字段长度
     */
    private Integer size;
    /**
     * 字段精度
     */
    private Integer digits;

    public DBFField(byte[] bytes) {
        this.name = ByteUtils.byteToStr(bytes);
        // 数据类型
        this.type = String.valueOf((char) bytes[11]);
        // 字段长度
        this.size = (int) bytes[16];
        // 字段精度
        this.digits = (int) bytes[17];
    }

}
