package com.github.buerxixi.easydbf.util;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFField;
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
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DBFUtils {

    /**
     * 根据文件名称获取头文件
     *
     * @param filename 文件地址
     * @return header信息
     */
    public static DBFHeader getHeader(String filename) throws IOException {
        try (InputStream inStream = Files.newInputStream(Paths.get(filename))) {
            byte[] bytes = new byte[32];
            inStream.read(bytes);
            return DBFHeader.builder().build().fromBytes(bytes);
        }
    }

    /**
     * 获取字段
     */
    public static List<DBFField> getFields(String filename, Charset charset) throws IOException {
        ArrayList<DBFField> fields = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            // 跳过32个字节头信息
            raf.seek(32);

            // 读取一个字节看看是否等于0x0D
            for (byte firstByte = raf.readByte(); firstByte != DBFConstant.END_OF_FIELD; firstByte = raf.readByte()) {

                // 再读取31个字节 把前面的字节 firstByte 加到前面
                byte[] next31Bytes = new byte[31];
                raf.readFully(next31Bytes);
                byte[] combinedBytes = new byte[32];
                combinedBytes[0] = firstByte;
                System.arraycopy(next31Bytes, 0, combinedBytes, 1, 31);
                DBFField field = DBFField.builder().charset(charset).build().fromBytes(combinedBytes);
                fields.add(field);
            }

            return fields;
        }
    }

    /**
     * 获取字段
     */
    public static List<DBFField> getFields(String filename) throws IOException {
        return getFields(filename, DBFConstant.DEFAULT_CHARSET);
    }

    /**
     * 转换成map
     */
    public static LinkedHashMap<String, String> items2Map(List<DBFItem> rows) {
        // 使用 Java 8 的 Stream API 和 Collectors.toMap 方法将列表转换为 Map
        return rows.stream()
                .collect(Collectors.toMap(
                        // 键的映射函数，这里假设 DBFRow 有一个 getKey 方法
                        DBFItem::getFieldName,
                        // 值的映射函数，这里假设 DBFRow 有一个 getValue 方法
                        DBFItem::getValue,
                        // 处理键冲突的合并函数，这里简单地使用后一个值覆盖前一个值
                        (existing, replacement) -> replacement,
                        LinkedHashMap::new
                ));
    }
}
