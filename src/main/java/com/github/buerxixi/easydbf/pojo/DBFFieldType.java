package com.github.buerxixi.easydbf.pojo;

/**
 * DBFDataType
 * <p>
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public enum DBFFieldType {

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

    private DBFFieldType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static DBFFieldType forString(String s) {
        for (DBFFieldType type : values()) {
            if (type.type.equals(s)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid DBFFieldType s: " + s);
    }
}
