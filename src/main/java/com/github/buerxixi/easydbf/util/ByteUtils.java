package com.github.buerxixi.easydbf.util;

import java.nio.charset.StandardCharsets;

/**
 * 字节工具类
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 * @since 2025/04/01 16:58
 */
public class ByteUtils {

    /**
     * 私有构造函数，防止实例化工具类
     */
    private ByteUtils() {

    }

    /**
     * 字节数组大端读取int
     *
     * @param bytes 字节数组
     * @param offset 偏移量
     * @param byteLen 字节长度
     * @return 数值
     */
    private static Integer readShortLE(byte[] bytes, Integer offset, Integer byteLen) {

        // 结果
        int le = 0;

        // 累计
        for (int i = 0; i < byteLen; i++) {
            // 将当前字节转换为无符号整数，并左移相应的位数，然后累加到结果中
            le += (bytes[i + offset] & 0xFF) << i * 8;
        }

        return le;
    }

    /**
     * 字节数组大端读取int16
     *
     * @param bytes 字节数组
     * @param offset 偏移量
     * @return 数值
     */
    public static Integer readShortLE(byte[] bytes, Integer offset) {
        // 调用 readShortLE 方法，指定字节长度为 2
        return readShortLE(bytes, offset, 2);
    }

    /**
     * 字节数组大端读取int32
     *
     * @param bytes 字节数组
     * @param offset 偏移量
     * @return 数值
     */
    public static Integer readIntLE(byte[] bytes, Integer offset) {
        // 调用 readShortLE 方法，指定字节长度为 4
        return readShortLE(bytes, offset, 4);
    }

    /**
     * 字节转字符
     *
     * @param bytes 字节数组
     * @return 字符
     */
    public static String byteToStr(byte[] bytes) {
        try {
            int length = 0;
            // 找到字节数组中第一个为 0 的位置
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] == 0) {
                    length = i;
                    break;
                }
            }
            // 将字节数组转换为字符串，从 0 到 length 的位置，使用 UTF-8 编码
            return new String(bytes, 0, length, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // 若出现异常，返回空字符串
            return "";
        }
    }

    /**
     * 从左侧合并数据
     * @param to 最终数据
     * @param from 被合并数据
     * @return 最终数据
     */
    public static byte[] mergeL(byte[] to, byte[] from) {
        // 将 from 数组的内容复制到 to 数组的左侧，复制长度为两个数组长度的较小值
        System.arraycopy(from, 0, to, 0, Math.min(to.length, from.length));
        return to;
    }

    /**
     * 从右侧合并数据
     * @param to 最终数据
     * @param from 被合并数据
     * @return 最终数据
     */
    public static byte[] mergeR(byte[] to, byte[] from) {
        // 从右向左将 from 数组的内容复制到 to 数组的右侧，复制长度为两个数组长度的较小值
        for (int i = 1; i <= Math.min(to.length, from.length); i++) {
            to[to.length - i] = from[from.length - i];
        }
        return to;
    }

    /**
     * short转成小端字节数组
     * @param value short值
     * @return 小端字节数组
     */
    public static byte[] shortToBytesLE(Short value) {
        byte[] result = new byte[2];
        // 提取低 8 位
        result[0] = (byte) (value & 0xFF);
        // 提取高 8 位
        result[1] = (byte) ((value >> 8) & 0xFF);
        return result;
    }

    /**
     * int转成小端字节数组
     * @param value int值
     * @return 小端字节数组
     */
    public static byte[] intToBytesLE(Integer value) {
        byte[] result = new byte[4];
        // 提取第 1 个字节（最低位字节）
        result[0] = (byte) (value & 0xFF);
        // 提取第 2 个字节
        result[1] = (byte) ((value >> 8) & 0xFF);
        // 提取第 3 个字节
        result[2] = (byte) ((value >> 16) & 0xFF);
        // 提取第 4 个字节（最高位字节）
        result[3] = (byte) ((value >> 24) & 0xFF);
        return result;
    }

    /**
     * 将小端字节序的字节数组转换为 short 类型
     * @param bytes 小端字节序的字节数组，长度至少为 2
     * @return 转换后的 short 类型值
     */
    public static short bytesToShortLE(byte[] bytes) {
        // 检查输入的字节数组长度是否至少为 2
        if (bytes.length < 2) {
            throw new IllegalArgumentException("输入的字节数组长度必须至少为 2");
        }
        // 将字节数组转换为 short 类型，先将高字节左移 8 位，再与低字节按位或
        return (short) ((bytes[1] & 0xFF) << 8 | (bytes[0] & 0xFF));
    }

    /**
     * 将小端字节序的字节数组转换为 int 类型
     * @param bytes 小端字节序的字节数组，长度至少为 4
     * @return 转换后的 int 类型值
     */
    public static int bytesToIntLE(byte[] bytes) {
        // 检查输入的字节数组长度是否至少为 4
        if (bytes.length < 4) {
            throw new IllegalArgumentException("输入的字节数组长度必须至少为 4");
        }
        // 将字节数组转换为 int 类型，依次将每个字节左移相应的位数，然后按位或
        return (bytes[3] & 0xFF) << 24 | (bytes[2] & 0xFF) << 16 | (bytes[1] & 0xFF) << 8 | (bytes[0] & 0xFF);
    }
}