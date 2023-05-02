package com.github.buerxixi.easydbf.pojo;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * 记录数据
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
@Data
@SuperBuilder
public class DBFRecord {

    private Integer rownum;

    private Integer fieldnum;

    private final byte[] bytes;

    public static DBFRecord of(Integer rownum, Integer fieldnum, byte[] bytes){
        return DBFRecord.builder()
                .rownum(rownum)
                .fieldnum(fieldnum)
                .bytes(bytes)
                .build();
    }
}
