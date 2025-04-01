package com.github.buerxixi.easydbf.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 数据存储块
 */
@Data
@NoArgsConstructor
@ToString
@SuperBuilder
public class DBFItem {
    private Integer rowId;
    private String fieldName;
    private String value;
}