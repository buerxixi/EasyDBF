package com.github.buerxixi.easydbf.pojo;

import com.github.buerxixi.easydbf.util.ByteUtils;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 头信息
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
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
    private Integer headerLength;

    /**
     * 一条记录中的字节长度，即每行数据所占的长度 0x20/0x2A + SUM(field.size)
     */
    private Integer recordLength;

    /**
     * 编码
     */
    @Builder.Default
    private Byte languageDriver = DBFConstant.LANGUAGE_DRIVER;

    @Override
    public DBFHeader fromBytes(byte[] bytes) {
        return DBFHeader.builder()
                .version(bytes[0])
                .year((short) (bytes[1] + DBFConstant.START_YEAR))
                .month(bytes[2])
                .day(bytes[3])
                .numberOfRecords(ByteUtils.readIntLE(bytes,4))
                .headerLength(ByteUtils.readShortLE(bytes,8))
                .recordLength(ByteUtils.readShortLE(bytes,0x0A))
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
