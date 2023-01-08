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
    final static public byte END_OF_DATA = 0x1A;

    /**
     * DBF版本信息
     */
    final static public byte DBASE_III = 0x03;

    /**
     * 删除文件标识/空白标识
     */
    final static public byte DELETED_OF_FIELD = 0x2A;

    /**
     * 未删除文件标识
     */
    final static public byte UNDELETED_OF_FIELD = 0x20;

    /**
     * 未删除文件标识数组
     */
    final static public byte[] UNDELETED_OF_FIELD_BYTES= new byte[]{DBFConstant.UNDELETED_OF_FIELD};

    /**
     * 起始年份
     */
    final static public short START_YEAR = 1900;

    /**
     * 日期格式化
     */
    final static public String DATE_PATTERN = "yyyyMMdd";

    /**
     * 字符串
     */
    final static public String CHARACTER = "C";

    /**
     * 金额
     */

    final static public String NUMERIC ="N";

    /**
     * 日期
     */
    final static public String DATE = "D";

    /**
     * 最大列数量
     */
    final static Integer MAX_FIELDS = 255;

    /**
     * 最大文件数量
     */
    final static Long MAX_RECORDS = 4294967296L;
}
