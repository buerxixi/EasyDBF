package com.github.buerxixi.easydbf;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.ListUtils;

/**
 * DBFReader
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
@Data
public class DBFReader implements Iterable<DBFRecord> {

    final private DBFTable table;

    public DBFReader(String filename, Charset charset) {
        this.table = new DBFTable(filename, charset);
        this.table.readTable();
    }
    /**
     * 查询
     * <p>
     * TODO:该处可以通过迭代器实现 减少内存使用情况
     */
    public List<DBFRecord> find(String pk, String value){
        Optional<DBFField> filed = this.table.getFields().stream().filter(field -> field.getType().equals(pk)).findFirst();
        return filed.map(field -> this.findAll().stream().filter(record -> record.rows.get(field.getIndex()).getString().equals(value))
                .collect(Collectors.toList()))
                // 获取空置
                .orElseGet(ArrayList::new);
    }

    /**
     * 获取全部数据
     *
     * @return
     */
    public List<DBFRecord> findAll() {
        try (DBFFieldIterator iterator = this.iterator()) {
            return IteratorUtils.toList(iterator);
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
