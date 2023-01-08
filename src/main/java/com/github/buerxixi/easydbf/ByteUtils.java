package com.github.buerxixi.easydbf;

import org.apache.commons.lang3.ArrayUtils;

import java.nio.charset.StandardCharsets;

/**
 * 字节工具类
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
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
    public static Integer readIntLE(byte[] bytes, Integer offset, Integer byteLen) {

        // 结果
        int le = 0;

        // 累计
        for (int i = 0; i < byteLen; i++) {
            le += (bytes[i + offset] & 0xFF) << i * 8;
        }

        return le;
    }

    /**
     * int大端写入字节数组
     *
     * @param number 数值
     * @return 字节数组
     */
    public static byte[] writeIntLE(Integer number) {
        byte[] bytes = new byte[4];

        // 累计
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((number >> i * 8) & 0xFF);
        }

        return bytes;
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
}
