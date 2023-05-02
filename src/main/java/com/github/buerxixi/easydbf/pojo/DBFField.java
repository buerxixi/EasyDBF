package com.github.buerxixi.easydbf.pojo;

import com.github.buerxixi.easydbf.util.ByteUtils;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 字段
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
//@Builder
@Data
@SuperBuilder
@ToString
public class DBFField {

    /**
     * 字段的名称
     *
     * TODO 1-11位
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

    private DBFField() {

    }

    public static DBFField from(byte[] bytes) {
        return DBFField.builder()
                .name(ByteUtils.byteToStr(bytes))
                .type(DBFFieldType.from(String.valueOf((char) bytes[11])))
                .size((int) bytes[16])
                .digits( (int) bytes[17])
                .build();
    }
}
