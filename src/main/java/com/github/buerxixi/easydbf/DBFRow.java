package com.github.buerxixi.easydbf;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * DBFRow
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DBFRow {

    private final byte[] bytes;

    private final Charset charset;

    private final String type;

    public DBFRow(String type, Charset charset, byte[]bytes) {
        this.type = type;
        this.charset = charset;
        this.bytes = bytes;
    }

    public String getString(){
        return new String(this.bytes, this.charset).trim();
    }

    public BigDecimal getBigDecimal(){
        return new BigDecimal(this.getString());
    }

    public LocalDate getLocalDate(){
        return LocalDate.parse(this.getString(), DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public Object getValue(){
        switch (this.type) {
            case "C":
                return this.getString();
            case "N":
                return this.getBigDecimal();
            case "D":
                return this.getLocalDate();
            default:
                return "类型不存在";
        }
    }

    @Override
    public String toString(){
        return this.getString();
    }
}
