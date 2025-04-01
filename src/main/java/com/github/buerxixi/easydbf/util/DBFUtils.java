package com.github.buerxixi.easydbf.util;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.model.DBFField;
import com.github.buerxixi.easydbf.pojo.DBFHeader;
import com.github.buerxixi.easydbf.pojo.DBFItem;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DBF工具类
 * <p>
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class DBFUtils {

    /**
     * 根据文件名称获取头文件
     *
     * @param filename 文件地址
     * @return header信息
     * @throws IOException 如果在读取文件时发生I/O错误
     */
    public static DBFHeader getHeader(String filename) throws IOException {
        // 打开文件输入流
        try (InputStream inStream = Files.newInputStream(Paths.get(filename))) {
            // 创建一个长度为32的字节数组，用于存储DBF文件的头部信息
            byte[] bytes = new byte[32];
            // 从输入流中读取32个字节到字节数组中
            inStream.read(bytes);
            // 调用DBFHeader的fromBytes方法，将字节数组转换为DBFHeader对象并返回
            return DBFHeader.builder().build().fromBytes(bytes);
        }
    }

    /**
     * 获取字段
     *
     * @param filename 文件地址
     * @param charset  字符集
     * @return 字段列表
     * @throws IOException 如果在读取文件时发生I/O错误
     */
    public static List<DBFField> getFields(String filename, Charset charset) throws IOException {
        // 创建一个ArrayList用于存储DBFField对象
        ArrayList<DBFField> fields = new ArrayList<>();
        // 以只读模式打开文件
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            // 跳过32个字节的头信息
            raf.seek(32);

            // 读取一个字节，检查是否等于0x0D（字段结束标记）
            for (byte firstByte = raf.readByte(); firstByte != DBFConstant.END_OF_FIELD; firstByte = raf.readByte()) {
                // 再读取31个字节
                byte[] next31Bytes = new byte[31];
                raf.readFully(next31Bytes);
                // 创建一个长度为32的字节数组，将第一个字节和接下来的31个字节合并
                byte[] combinedBytes = new byte[32];
                combinedBytes[0] = firstByte;
                System.arraycopy(next31Bytes, 0, combinedBytes, 1, 31);
                // 调用DBFField的fromBytes方法，将字节数组转换为DBFField对象
                DBFField field = DBFField.builder().charset(charset).build().fromBytes(combinedBytes);
                // 将DBFField对象添加到列表中
                fields.add(field);
            }

            // 返回字段列表
            return fields;
        }
    }

    /**
     * 获取字段，使用默认字符集
     *
     * @param filename 文件地址
     * @return 字段列表
     * @throws IOException 如果在读取文件时发生I/O错误
     */
    public static List<DBFField> getFields(String filename) throws IOException {
        // 调用getFields方法，使用默认字符集
        return getFields(filename, DBFConstant.DEFAULT_CHARSET);
    }

    /**
     * 转换成map
     *
     * @param rows 包含DBFItem对象的列表
     * @return 转换后的LinkedHashMap，键为字段名，值为字段值
     */
    public static LinkedHashMap<String, String> items2Map(List<DBFItem> rows) {
        // 使用 Java 8 的 Stream API 和 Collectors.toMap 方法将列表转换为 Map
        return rows.stream()
                .collect(Collectors.toMap(
                        // 键的映射函数，这里假设 DBFItem 有一个 getFieldName 方法
                        DBFItem::getFieldName,
                        // 值的映射函数，这里假设 DBFItem 有一个 getValue 方法
                        DBFItem::getValue,
                        // 处理键冲突的合并函数，这里简单地使用后一个值覆盖前一个值
                        (existing, replacement) -> replacement,
                        LinkedHashMap::new
                ));
    }
}