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
        if (bytes.length < 2) {
            throw new IllegalArgumentException("输入的字节数组长度必须至少为 2");
        }
        return (short) ((bytes[1] & 0xFF) << 8 | (bytes[0] & 0xFF));
    }

    /**
     * 将小端字节序的字节数组转换为 int 类型
     * @param bytes 小端字节序的字节数组，长度至少为 4
     * @return 转换后的 int 类型值
     */
    public static int bytesToIntLE(byte[] bytes) {
        if (bytes.length < 4) {
            throw new IllegalArgumentException("输入的字节数组长度必须至少为 4");
        }
        return (bytes[3] & 0xFF) << 24 | (bytes[2] & 0xFF) << 16 | (bytes[1] & 0xFF) << 8 | (bytes[0] & 0xFF);
    }


}
