package com.github.buerxixi.easydbf.pojo;

import com.github.buerxixi.easydbf.util.ByteUtils;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.nio.charset.Charset;

/**
 * 字段
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
//@Builder
@Data
@SuperBuilder
@ToString
public class DBFField implements IConverter<DBFField> {

    /**
     * 删除标识
     */
    private byte deletion;

    @Builder.Default
    private Charset charset = DBFConstant.DEFAULT_CHARSET;

    /**
     * 0-10位
     * 字段的名称
     */
    private String name;

    /**
     * 字段偏移量
     * 12-15
     */
    private Integer offset;

    /**
     * 11
     * 数据类型
     */
    private DBFFieldType type;
    /**
     * 16
     * 字段长度
     */
    private Integer size;
    /**
     * 17
     * 字段精度
     */
    @Builder.Default
    private Integer digits = 0;

    @Override
    public DBFField fromBytes(byte[] bytes) {
        return DBFField.builder()
                // 可以取前10个字节然后去除空格
                .name(ByteUtils.byteToStr(bytes))
                .type(DBFFieldType.from((char) bytes[11]))
                .offset(ByteUtils.readIntLE(bytes, 12))
                .size((int) bytes[16])
                .digits( (int) bytes[17])
                .build();
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[32]; // 假设每个字段信息固定为32字节

        // 填充字段名称
        if (name != null) {
            byte[] nameBytes = name.getBytes();
            int length = Math.min(nameBytes.length, 11);
            System.arraycopy(nameBytes, 0, bytes, 0, length);
        }

        // 填充数据类型
//        if (type != null) {
            // TODO
            // bytes[11] = (byte) type.getType();
//        }

        // 填充字段长度
        if (size != null) {
            bytes[16] = (byte) size.intValue();
        }

        // 填充字段精度
        bytes[17] = (byte) digits.intValue();

        // 剩余部分填充 0x00
        for (int i = 18; i < 32; i++) {
            bytes[i] = 0x00;
        }

        return bytes;
    }
}
