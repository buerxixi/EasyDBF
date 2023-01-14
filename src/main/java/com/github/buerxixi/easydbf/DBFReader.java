package com.github.buerxixi.easydbf;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import org.apache.commons.collections4.IteratorUtils;

/**
 * DBFReader
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
@Data
public class DBFReader implements Iterable<DBFRow> {

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
    public List<DBFRow> find(String pk, String value){
        Optional<DBFInnerField> filed = this.table.findField(pk);

        List<DBFRow> rows = new ArrayList<>();

        if(!filed.isPresent()) {
            return rows;
        }

        List<DBFInnerField> fields = this.table.getFields();

        try (DBFRowIterator iterator = this.iterator()){
            while (iterator.hasNext()) {
                DBFRow row = iterator.next();
                DBFRecord record = row.getRecords().get(filed.get().getFieldNum());
                if(record.getString().equals(value)) {
                    rows.add(iterator.next());
                }
            }
        }

        return rows;
    }

    /**
     * 获取全部数据
     *
     * @return
     */
    public List<DBFRow> findAll() {
        try (DBFRowIterator iterator = this.iterator()) {
            return IteratorUtils.toList(iterator);
        }
    }

    /**
     * 迭代器
     *
     * @return
     */
    @Override
    public DBFRowIterator iterator() {
        return new DBFRowIterator(this.table);
    }
}
