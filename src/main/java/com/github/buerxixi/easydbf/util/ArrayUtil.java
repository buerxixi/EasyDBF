package com.github.buerxixi.easydbf.util;

public class ArrayUtil {
    public static byte[] sub(byte[] array, int startIndex, int endIndex) {
        // 提前处理数组为 null 的情况
        if (array == null) {
            return null;
        }
        // 确保 startIndex 和 endIndex 在合理范围内
        startIndex = Math.max(0, startIndex);
        endIndex = Math.min(array.length, Math.max(startIndex, endIndex));

        int length = endIndex - startIndex;
        if (length <= 0) {
            return new byte[0];
        }

        byte[] subArray = new byte[length];
        System.arraycopy(array, startIndex, subArray, 0, length);
        return subArray;
    }
}
