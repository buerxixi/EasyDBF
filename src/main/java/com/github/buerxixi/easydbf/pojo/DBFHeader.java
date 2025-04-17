package com.github.buerxixi.easydbf.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 头信息
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">刘家强</a>
 * @since 2025/04/01 16:58
 */
@Data
@NoArgsConstructor
@ToString
@SuperBuilder
public class DBFHeader implements IConverter<DBFHeader> {

    /**
     * DBF版本信息
     */
    private Byte version;

    /**
     * 保存时的年 - 1900
     */
    private Short year;

    /**
     * 保存时的月
     */
    private Byte month;

    /**
     * 保存时的日
     */
    private Byte day;

    /**
     * DBF文件中有多少条记录
     */
    private Integer numberOfRecords;

    /**
     * 当前DBF的文件头占用的字节长度 Header(32bit) + n*Field(32bit) + 0x0D
     */
    private Short headerLength;

    /**
     * 一条记录中的字节长度，即每行数据所占的长度 0x20/0x2A + SUM(field.size)
     */
    private Short recordLength;

    /**
     * 编码
     */
    @Builder.Default
    private Byte languageDriver = DBFConstant.LANGUAGE_DRIVER;

    @Override
    public DBFHeader fromBytes(byte[] bytes) {

        int recordCount = ByteBuffer.wrap(bytes, 4, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
        short headerLength = ByteBuffer.wrap(bytes, 8, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
        short recordLength = ByteBuffer.wrap(bytes, 10, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();

        return DBFHeader.builder()
                .version(bytes[0])
                .year((short) (bytes[1] + DBFConstant.START_YEAR))
                .month(bytes[2])
                .day(bytes[3])
                .numberOfRecords(recordCount)
                .headerLength(headerLength)
                .recordLength(recordLength)
                .languageDriver(bytes[29])
                .build();
    }

    @Override
    public byte[] toBytes() {
        // 写入头部信息
        byte[] bytes = new byte[32];
        // 设置版本
        bytes[0] = DBFConstant.DBASE_III;
        // 设置语言
        bytes[29] = DBFConstant.LANGUAGE_DRIVER;
        return bytes;
    }
}
