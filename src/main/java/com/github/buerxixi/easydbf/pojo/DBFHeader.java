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
        byte[] bytes = new byte[32]; // 假设文件头固定为32字节
        // 填充版本信息
        if (version != null) {
            bytes[0] = version;
        } else {
            bytes[0] = 0x00;
        }
        // 填充年
        if (year != null) {
            short yearValue = (short) (year - DBFConstant.START_YEAR);
            bytes[1] = (byte) (yearValue & 0xFF);
        } else {
            bytes[1] = 0x00;
        }
        // 填充月
        if (month != null) {
            bytes[2] = month;
        } else {
            bytes[2] = 0x00;
        }
        // 填充日
        if (day != null) {
            bytes[3] = day;
        } else {
            bytes[3] = 0x00;
        }
        // 填充记录数量
        if (numberOfRecords != null) {
            byte[] numRecordsBytes = ByteUtils.writeInt32LE(numberOfRecords);
            System.arraycopy(numRecordsBytes, 0, bytes, 4, 4);
        } else {
            for (int i = 4; i < 8; i++) {
                bytes[i] = 0x00;
            }
        }
        // 填充文件头长度
        if (headerLength != null) {
            byte[] headerLengthBytes = ByteUtils.writeInt16LE(headerLength);
            System.arraycopy(headerLengthBytes, 0, bytes, 8, 2);
        } else {
            bytes[8] = 0x00;
            bytes[9] = 0x00;
        }
        // 填充记录长度
        if (recordLength != null) {
            byte[] recordLengthBytes = ByteUtils.writeInt16LE(recordLength);
            System.arraycopy(recordLengthBytes, 0, bytes, 0x0A, 2);
        } else {
            bytes[0x0A] = 0x00;
            bytes[0x0B] = 0x00;
        }
        // 填充语言驱动
        if (languageDriver != null) {
            bytes[29] = languageDriver;
        } else {
            bytes[29] = 0x00;
        }
        // 剩余部分填充 0x00
        for (int i = 30; i < 32; i++) {
            bytes[i] = 0x00;
        }
        return bytes;
    }
}
