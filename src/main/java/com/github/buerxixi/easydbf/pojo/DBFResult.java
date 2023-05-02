package com.github.buerxixi.easydbf.pojo;

import com.github.buerxixi.easydbf.pojo.DBFFieldType;
import lombok.Builder;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
@SuperBuilder
@ToString
public class DBFResult {

    /**
     * 列索引从1开始
     */
    private Integer rownum;

    /**
     * 表列明
     */
    private String key;

    /**
     * 列对应的数据
     */
    private String value;

    /**
     * 列的类型
     */
    private DBFFieldType type;
}
