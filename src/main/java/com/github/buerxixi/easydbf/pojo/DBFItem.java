package com.github.buerxixi.easydbf.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 数据存储块
 * 该类用于表示DBF文件中的一个数据项，包含行号、字段名和字段值。
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 * @since 2025/04/01 16:58
 */
@Data
@NoArgsConstructor
@ToString
@SuperBuilder
public class DBFItem {
    /**
     * 数据项所在的行号
     */
    private Integer rowId;
    /**
     * 数据项对应的字段名
     */
    private String fieldName;
    /**
     * 数据项的字段值
     */
    private String value;
}