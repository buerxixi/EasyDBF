package com.github.buerxixi.easydbf.pojo;

import java.nio.charset.Charset;

/**
 * 常量类
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 * @since 2025/04/01 16:58
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
     * 未删除文件标识(空格)
     */
    final static public byte UNDELETED_OF_FIELD = 0x20;

    /**
     * 删除文件标识/空白标识(*)
     */
    final static public byte DELETED_OF_FIELD = 0x2A;



    /**
     * 起始年份
     */
    final static public Short START_YEAR = 1900;

    /**
     * 默认语言驱动
     */
    final static public Byte LANGUAGE_DRIVER = 0x4D;

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

    final static public String NUMERIC = "N";

    /**
     * 日期
     */
    final static public String DATE = "D";

    /**
     * 默认编码集
     */
    final static public Charset DEFAULT_CHARSET= Charset.forName("GBK");

    /**
     * 最大列数量
     */
    final static Integer MAX_FIELDS = Byte.MAX_VALUE - Byte.MIN_VALUE;

    /**
     * 最大文件数量
     * C++long长度
     */
    final static Long MAX_RECORDS = 4294967296L;
}
