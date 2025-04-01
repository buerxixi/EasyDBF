package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFItem;
import com.github.buerxixi.easydbf.pojo.DBFReaderIterator;
import lombok.SneakyThrows;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

public class DBFReader implements Iterable<List<DBFItem>>{

    final private String filename;
    final private Charset charset;

    DBFReader(String filename, Charset charset) {
        this.filename = filename;
        this.charset = charset;
    }

    DBFReader(String filename) {
        this(filename, DBFConstant.DEFAULT_CHARSET);
    }

    @SneakyThrows
    @Override
    public Iterator<List<DBFItem>> iterator() {
        return new DBFReaderIterator(filename, charset);
    }
}
