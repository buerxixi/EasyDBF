package com.github.buerxixi.easydbf;

/**
 * DBFDataType
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public enum DBFDataType {

    /**
     * 字符串
     */
    CHARACTER("C"),

    /**
     * 金额
     */
    NUMERIC("N"),

    /**
     * 日期
     */
    DATE("D");

    private final String type;

    private  DBFDataType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
