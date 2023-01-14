package com.github.buerxixi.easydbf;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;

import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class EasyDBF {
    public static DBFTable read(String filename, Charset charset){
        DBFTable table = new DBFTable(filename, charset);
        // 读取header
        table.setHeader(readHeader(table));

        // 读取fields
//        table.setFields(readFields(table));

        return table;
    }

    public static DBFTable write(String filename, Charset charset){
        return new DBFTable(filename, charset);
    }

    @SneakyThrows
    private static DBFHeader readHeader(DBFTable table) {
        // 判断文件是否存在

        byte[] bytes = new byte[32];
        try (RandomAccessFile raf = new RandomAccessFile(table.getFilename(), "r")) {
            raf.read(bytes);
        }
        return new DBFHeader(bytes);
    }

//    @SneakyThrows
//    private static ArrayList<DBFInnerField> readFields(DBFTable table) {
//
//        // 获取头文件
//        DBFHeader header = table.getHeader();
//
//        ArrayList<DBFInnerField> fields = new ArrayList<>();
//
//        byte[] bytes = new byte[header.getHeaderLength() - 32 - 1];
//        try (RandomAccessFile raf = new RandomAccessFile(table.getFilename(), "r")) {
//
//            raf.skipBytes(32);
//            raf.read(bytes);
//
//            for (int i = 0, j = 0; i < bytes.length; i += 32, j++) {
//                // 获取字段取值
//                byte[] subarray = ArrayUtils.subarray(bytes, i, i + 31);
//                // 字段
//                fields.add(new DBFInnerField(j, subarray));
//            }
//        }
//
//        return fields;
//    }
}
