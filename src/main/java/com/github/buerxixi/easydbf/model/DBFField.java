package com.github.buerxixi.easydbf.model;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFFieldType;
import com.github.buerxixi.easydbf.pojo.IConverter;
import com.github.buerxixi.easydbf.util.ByteUtils;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 字段
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
//@Builder
@Data
@Builder
@ToString
public class DBFField implements IConverter<DBFField> {

    /**
     * 删除标识
     * TODO row相关字段
     */
    //    private byte deletion;

    @Builder.Default
    private Charset charset = DBFConstant.DEFAULT_CHARSET;

    /**
     * 0-10位
     * 字段的名称
     */
    private String name;


    /**
     * 11
     * 数据类型
     */
    private DBFFieldType type;
    /**
     * 字段偏移量
     * 12-15
     */
    private Integer offset;

    /**
     * 16
     * 字段长度
     */
    private byte size;
    /**
     * 17
     * 字段精度
     */
    @Builder.Default
    private byte digits = 0;

    @Override
    public DBFField fromBytes(byte[] bytes) {
        return DBFField.builder()
                // 可以取前10个字节然后去除空格
                .name(ByteUtils.byteToStr(bytes))
                .type(DBFFieldType.from((char) bytes[11]))
                .offset(ByteUtils.readIntLE(bytes, 12))
                .size(bytes[16])
                .digits(bytes[17])
                .build();
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[32]; // 假设每个字段信息固定为32字节
        // 设置名称
        byte[] nameBytes = name.getBytes(charset);
        System.arraycopy(nameBytes, 0, bytes, 0, Math.min(nameBytes.length, 10));
        // 设置字段类型
        bytes[11] = (byte) type.getType();
        // 字段偏移（大段读取）
        byte[] offsetBytes = ByteUtils.int2LE(offset);
        System.arraycopy(offsetBytes, 0, bytes, 12, 4);
        // 字段长度
        bytes[16] = size;
        // 字段经度
        bytes[17] = digits;

        return bytes;
    }
}
