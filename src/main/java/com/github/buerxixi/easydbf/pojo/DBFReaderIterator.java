package com.github.buerxixi.easydbf.pojo;

import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * DBFReader迭代器
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">刘家强</a>
 * @since 2025/04/01 16:58
 */
public class DBFReaderIterator extends DBFInputStreamReaderIterator implements AutoCloseable {


    public DBFReaderIterator(String filename) throws IOException {
        this(filename, DBFConstant.DEFAULT_CHARSET);
    }

    public DBFReaderIterator(String filename, Charset charset) throws IOException {
        super(Files.newInputStream(Paths.get(filename)), charset);
    }

    public void close() {
        IOUtils.closeQuietly(this.inputStream);
    }
}
