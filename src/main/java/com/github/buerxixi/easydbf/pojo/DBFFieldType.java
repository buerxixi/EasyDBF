package com.github.buerxixi.easydbf.pojo;

import lombok.Getter;

/**
 * 字段类型
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 * @since 2025/04/01 16:58
 */
@Getter
public enum DBFFieldType {

    /**
     * 字符串
     */
    CHARACTER('C'),

    /**
     * 金额
     */
    NUMERIC('N'),

    /**
     * 日期
     */
    DATE('D');

    private final char type;

    DBFFieldType(char type) {
        this.type = type;
    }

    public static DBFFieldType from(char s) {
        for (DBFFieldType fieldType : values()) {
            if (fieldType.type == s) {
                return fieldType;
            }
        }
        throw new IllegalArgumentException("Invalid DBFFieldType s: " + s);
    }

}
