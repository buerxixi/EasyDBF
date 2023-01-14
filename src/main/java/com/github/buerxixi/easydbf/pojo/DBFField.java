package com.github.buerxixi.easydbf.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * DBFField
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
//@Builder
@Data
@SuperBuilder
public class DBFField {

    /**
     * 字段的名称
     */
    protected String name;

    /**
     * 数据类型
     */
    protected DBFFieldType type;
    /**
     * 字段长度
     */
    protected Integer size;
    /**
     * 字段精度
     */
    @Builder.Default
    protected Integer digits = 0;

    public DBFField() {

    }
}
