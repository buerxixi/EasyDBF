package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFResult;
import com.github.buerxixi.easydbf.pojo.DBFRow;
import com.github.buerxixi.easydbf.pojo.DBFRowIterator;
import lombok.SneakyThrows;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

public class DBFReader implements Iterable<List<DBFRow>>{

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
    public Iterator<List<DBFRow>> iterator() {
        return new DBFRowIterator(filename, charset);
    }
}
