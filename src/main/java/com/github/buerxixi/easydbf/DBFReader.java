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

    /**
     * 要读取的DBF文件的文件名
     */
    final private String filename;

    /**
     * 读取DBF文件时使用的字符集
     */
    final private Charset charset;

    /**
     * 构造一个DBFReader对象，使用指定的文件名和字符集。
     *
     * @param filename 要读取的DBF文件的文件名
     * @param charset  读取DBF文件时使用的字符集
     */
    DBFReader(String filename, Charset charset) {
        this.filename = filename;
        this.charset = charset;
    }

    /**
     * 构造一个DBFReader对象，使用指定的文件名和默认字符集。
     *
     * @param filename 要读取的DBF文件的文件名
     */
    DBFReader(String filename) {
        this(filename, DBFConstant.DEFAULT_CHARSET);
    }

    @SneakyThrows
    @Override
    public Iterator<List<DBFItem>> iterator() {
        return new DBFReaderIterator(filename, charset);
    }
}
