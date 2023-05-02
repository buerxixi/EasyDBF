package com.github.buerxixi.easydbf.pojo;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * 行数据
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
@Data
@SuperBuilder
public class DBFRow {

    /**
     * 第几行元素
     */
    private Integer rownum;

    /**
     * 数据
     */
    private byte[] bytes;

    public static DBFRow of(Integer rownum, byte[] bytes) {
        return DBFRow.builder()
                .rownum(rownum)
                .bytes(bytes)
                .build();
    }
}
