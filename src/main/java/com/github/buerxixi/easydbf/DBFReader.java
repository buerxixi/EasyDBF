package com.github.buerxixi.easydbf;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.collections4.IteratorUtils;

/**
 * DBFReader
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DBFReader implements Iterable<DBFRecord> {

    final private DBFTable table;

    public DBFReader(String filename, Charset charset) {
        this.table = new DBFTable(filename, charset);
        this.table.readTable();
    }
    /**
     * 查询
     */
    public Optional<DBFRecord> find(Map<String, String> map){
        // 默认返回值
        return Optional.empty();
    }

    /**
     * 获取全部数据
     *
     * @return
     */
    public ArrayList<DBFRecord> findAll() throws IOException {
        try (DBFFieldIterator iterator = this.iterator()) {
            return (ArrayList<DBFRecord>) IteratorUtils.toList(iterator);
        }
    }

    /**
     * 迭代器
     *
     * @return
     */
    @Override
    public DBFFieldIterator iterator() {
        return new DBFFieldIterator(this.table);
    }
}
