package com.github.buerxixi.easydbf.pojo;

import com.github.buerxixi.easydbf.util.ByteUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * 头信息
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
@Data
@NoArgsConstructor
@ToString
@SuperBuilder
public class DBFHeader {

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
     * 当前DBF的文件头占用的字节长度
     */
    private Integer headerLength;

    /**
     * 一条记录中的字节长度，即每行数据所占的长度
     */
    private Integer recordLength;

    /**
     * 编码
     */
    private Byte languageDriver;

    private List<DBFField> fields;

    public static DBFHeader from(byte[] bytes) {
        return DBFHeader.builder()
                .version(bytes[0])
                .year((short) bytes[1])
                .month(bytes[2])
                .day(bytes[3])
                .numberOfRecords(ByteUtils.readInt32LE(bytes,4))
                .headerLength(ByteUtils.readInt16LE(bytes,8))
                .recordLength(ByteUtils.readInt16LE(bytes,0x0A))
                .languageDriver(bytes[29])
                .fields(new ArrayList<>())
                .build();
    }
}
