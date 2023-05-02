package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.pojo.DBFRowIterator;
import com.github.buerxixi.easydbf.pojo.DBFResult;
import lombok.Data;
import lombok.SneakyThrows;
import java.nio.charset.Charset;
import java.util.List;

/**
 * DBFReader
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
@Data
public class DBFReader implements Iterable<List<DBFResult>> {

    final private String filename;
    final private Charset charset;
    final private boolean showDeletedRows;

    public DBFReader(String filename, Charset charset, boolean showDeletedRows) {
        this.filename  =filename;
        this.charset = charset;
        this.showDeletedRows = showDeletedRows;
    }

    public DBFReader(String filename, Charset charset) {
        this(filename, charset, false);
    }

    /**
     * 迭代器
     *
     * @return
     */
    @SneakyThrows
    @Override
    public DBFRowIterator iterator() {
        return new DBFRowIterator(filename, charset, showDeletedRows);
    }

}
