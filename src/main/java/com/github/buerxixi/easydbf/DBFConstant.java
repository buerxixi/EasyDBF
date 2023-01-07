package com.github.buerxixi.easydbf;

/**
 * DBFConstant
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DBFConstant {

    /**
     * 字段结束表示
     */
    final static public Byte END_OF_FIELD = 0x0D;

    /**
     * 数据结束表示
     */
    final static public Byte END_OF_DATA = 0x1A;

    /**
     * DBF版本信息
     */
    final static public Byte DBASE_III = 0x03;

    /**
     * 删除文件标识/空白标识
     */
    final static public Byte DELETED_OF_FIELD = 0x2A;

    /**
     * 未删除文件标识
     */
    final static public Byte UNDELETED_OF_FIELD = 0x20;

    /**
     * 起始年份
     */
    final static public Short START_YEAR = 1900;
}
