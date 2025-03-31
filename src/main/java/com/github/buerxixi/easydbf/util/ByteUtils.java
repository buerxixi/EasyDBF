package com.github.buerxixi.easydbf.util;

import java.nio.charset.StandardCharsets;

/**
 * 字节工具类
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class ByteUtils {

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
        return readShortLE(bytes, offset, 4);
    }

    /**
     * int大端写入字节数组
     *
     * @param number 数值
     * @return 字节数组
     */
    public static byte[] int32LE(Integer number) {
        byte[] bytes = new byte[4];

        // 累计
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((number >> i * 8) & 0xFF);
        }

        return bytes;
    }

    public static void writerInt(byte[] bytes, Integer number, Integer offset, Integer byteLen) {
        byte[] byte4 = int32LE(number);
        for (Integer i = 0; i < byteLen; i++) {
            bytes[offset + i] = byte4[i];
        }
    }

    public static void writerInt16(byte[] bytes, Integer number, Integer offset) {
        writerInt(bytes, number, offset, 2);
    }

    public static void writerInt32(byte[] bytes, Integer number, Integer offset) {
        writerInt(bytes, number, offset, 4);
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
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] == 0) {
                    length = i;
                    break;
                }
            }
            return new String(bytes, 0, length, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    public static byte[] merge(byte[] to, byte[] from) {
        System.arraycopy(from, 0, to, 0, Math.min(to.length, from.length));
        return to;
    }


    public static byte[] mergeRight(byte[] to, byte[] from) {
        for (int i = 1; i <= Math.min(to.length, from.length); i++) {
            to[to.length - i] = from[from.length - i];
        }
        return to;
    }

    /**
     * 将一个 16 位整数按照小端字节序转换为字节数组
     * @param value 要转换的 16 位整数
     * @return 转换后的字节数组
     */
    public static byte[] writeInt16LE(int value) {
        byte[] result = new byte[2];
        // 提取低 8 位
        result[0] = (byte) (value & 0xFF);
        // 提取高 8 位
        result[1] = (byte) ((value >> 8) & 0xFF);
        return result;
    }

    /**
     * 将一个 32 位整数按照小端字节序转换为字节数组
     * @param value 要转换的 32 位整数
     * @return 转换后的字节数组
     */
    public static byte[] writeInt32LE(int value) {
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

//    /**
//     * 从字节数组中按照小端字节序读取整数
//     *
//     * @param bytes 字节数组
//     * @param start 起始位置
//     * @param end   结束位置
//     * @return 读取到的整数值
//     * @throws IllegalArgumentException 如果字节数组为空，或者起始位置、结束位置不合法
//     */
//    public static int readIntLE(byte[] bytes, int start, int end) {
//        if (bytes == null) {
//            throw new IllegalArgumentException("字节数组不能为空");
//        }
//        if (start < 0 || end > bytes.length || start >= end) {
//            throw new IllegalArgumentException("起始位置或结束位置不合法");
//        }
//
//        int result = 0;
//        int byteLen = end - start;
//        for (int i = 0; i < byteLen; i++) {
//            result += (bytes[start + i] & 0xFF) << (i * 8);
//        }
//        return result;
//    }
}
