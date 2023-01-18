package com.github.buerxixi.easydbf.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * DBFField
 * <p>
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
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
