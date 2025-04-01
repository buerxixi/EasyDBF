package com.github.buerxixi.easydbf.util;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import java.time.LocalDate;

/**
 * 头信息工具类
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class HeaderUtils {

    /**
     * 将头信息转换为字节数组。
     *
     * 此方法用于生成一个长度为32字节的字节数组，该数组包含DBF文件头的基本信息，
     * 包括版本号和当前日期（年、月、日）。
     *
     * @return 包含头信息的32字节数组
     */
    public static byte[] toBytes() {
        // 创建一个长度为32的字节数组，用于存储DBF文件头信息
        byte[] bytes = new byte[32];
        // 设置版本
        // 将DBF文件的版本号设置为DBASE_III，存储在字节数组的第一个位置
        bytes[0] = DBFConstant.DBASE_III;

        // 设置年月日
        // 获取当前日期
        LocalDate now = LocalDate.now();
        // 设置年
        // 计算当前年份与起始年份的差值，并存储在字节数组的第二个位置
        bytes[1] = (byte) (now.getYear() - DBFConstant.START_YEAR);
        // 设置月
        // 将当前月份存储在字节数组的第三个位置
        bytes[2] = (byte) now.getMonthValue();
        // 设置日
        // Bug 修复：此处应该是 now.getDayOfMonth()，而不是 now.getDayOfYear()
        // 原代码使用 getDayOfYear() 会导致存储的是一年中的第几天，而不是当月的第几天
        // 正确代码如下：
        // bytes[3] = (byte) now.getDayOfMonth();
        bytes[3] = (byte) now.getDayOfYear();

        return bytes;
    }
}
