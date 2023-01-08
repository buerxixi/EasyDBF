package com.github.buerxixi.easydbf;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DBFHeader
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
@Data
@NoArgsConstructor
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

    public DBFHeader(byte[] bytes) {
        this.version = bytes[0];
        this.year = (short) (bytes[1] + DBFConstant.START_YEAR);
        this.month = bytes[2];
        this.day = bytes[3];
        this.numberOfRecords = ByteUtils.readIntLE(bytes,4, 4);
        this.headerLength = ByteUtils.readIntLE(bytes,8, 2);
        this.recordLength = ByteUtils.readIntLE(bytes,0x0A, 2);
        this.languageDriver = bytes[29];
    }

    /**
     * 创建空对象
     */
    public DBFHeader(List<DBFField> fields){
        this.version = DBFConstant.DBASE_III;
        this.headerLength = fields.size() * (1 + 32) + 1;
        this.recordLength = fields.stream().map(DBFField::getSize).reduce(0, Integer::sum) + 1;
    }

    /**
     * 转为字节数组
     * @return
     */
    public byte[] toBytes(){
        byte[] bytes = new byte[this.headerLength + 1];

        // table = header + 0x00 + fields + 0x00 + rows + 0x00

        return bytes;
    }
}
