package com.github.buerxixi.easydbf;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.Charset;

/**
 * DBFField
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
@Data
public class DBFField {

    /**
     * 索引
     * TODO: 优化索引
     */
    private Integer index;

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
    private Integer digits = 0;

    /**
     * 对应的表字段
     */
    private DBFTable table;

    /**
     * 字符集
     */
    private Charset charset;

    public DBFField(Integer index, byte[] bytes, Charset charset) {
        // 索引
        this.index = index;
        // 字段名称ASCII
        this.name = ByteUtils.byteToStr(bytes);
        // 数据类型
        this.type = String.valueOf((char) bytes[11]);
        // 字段长度
        this.size = (int) bytes[16];
        // 字段精度
        this.digits = (int) bytes[17];
        // 字符集
        this.charset = charset;
    }

}
