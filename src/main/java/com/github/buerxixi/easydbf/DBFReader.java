package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFItem;
import com.github.buerxixi.easydbf.pojo.DBFReaderIterator;
import lombok.SneakyThrows;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

/**
 * 该类用于读取DBF文件，实现了Iterable接口，可迭代获取DBF文件中的数据项列表。
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 * @since 2025/04/01 16:58
 */
public class DBFReader implements Iterable<List<DBFItem>> {

    // 要读取的DBF文件的文件名
    private final String filename;
    // 读取DBF文件时使用的字符集
    private final Charset charset;

    /**
     * 构造一个DBFReader对象，使用指定的文件名和字符集。
     *
     * @param filename 要读取的DBF文件的文件名
     * @param charset  读取DBF文件时使用的字符集
     */
    public DBFReader(String filename, Charset charset) {
        // 增加参数检查
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("文件名不能为空");
        }
        if (charset == null) {
            throw new IllegalArgumentException("字符集不能为空");
        }
        this.filename = filename;
        this.charset = charset;
    }

    /**
     * 构造一个DBFReader对象，使用指定的文件名和默认字符集。
     *
     * @param filename 要读取的DBF文件的文件名
     */
    public DBFReader(String filename) {
        this(filename, DBFConstant.DEFAULT_CHARSET);
    }

    @SneakyThrows
    @Override
    public Iterator<List<DBFItem>> iterator() {
        return new DBFReaderIterator(filename, charset);
    }
}
