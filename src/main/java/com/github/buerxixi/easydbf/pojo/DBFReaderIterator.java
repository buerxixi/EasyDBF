package com.github.buerxixi.easydbf.pojo;

import com.github.buerxixi.easydbf.convert.TypeConverterStrategyFactory;
import com.github.buerxixi.easydbf.model.DBFField;
import com.github.buerxixi.easydbf.util.DBFUtils;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * DBFReader迭代器
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 * @since 2025/04/01 16:58
 */
public class DBFReaderIterator implements Iterator<List<DBFItem>>, AutoCloseable {

    private final Charset charset;
    private final DBFHeader header;
    private final InputStream inputStream;
    private final List<DBFField> fields;
    private Integer rowId = -1;

    public DBFReaderIterator(String filename) throws IOException {
        this(filename, DBFConstant.DEFAULT_CHARSET);
    }

    public DBFReaderIterator(String filename, Charset charset) throws IOException {
        this.charset = charset;
        this.header = DBFUtils.getHeader(filename);
        this.fields = DBFUtils.getFields(filename);
        inputStream = Files.newInputStream(Paths.get(filename));

        // 跳过头部信息
        inputStream.skip(this.header.getHeaderLength());
    }

    /**
     * 是否有下一个文件
     * <p>
     * TODO:该处为查询类的重点中的重点
     */
    @SneakyThrows
    @Override
    public boolean hasNext() {
        // 索引++
        rowId++;

        // 读取一个元素
        byte[] flag = new byte[1];

        // 判断是否已经越界
        if (inputStream.read(flag) == -1) {
            return false;
        }

        if (flag[0] == DBFConstant.UNDELETED_OF_FIELD) {
            return true;
        }

        // 是否是删除符号
        if (flag[0] == DBFConstant.DELETED_OF_FIELD) {
            // 跳过文件长度
            inputStream.skip(this.header.getRecordLength());
            return hasNext();
        }

        return false;
    }

    /**
     * 获取下一个文件
     */
    @SneakyThrows
    @Override
    public List<DBFItem> next() {
        // 返回数据
        ArrayList<DBFItem> items = new ArrayList<>();


        // 根据请求头拆分数据
        for (DBFField field : this.fields) {
            byte[] bytes = new byte[field.getSize()];
            inputStream.read(bytes);
            String value = TypeConverterStrategyFactory.getStrategy(field.getType()).fromBytes(field, bytes);
            DBFItem item = DBFItem.builder().rowId(rowId).fieldName(field.getName()).value(value).build();
            items.add(item);
        }

        return items;
    }

    public void close() {
        IOUtils.closeQuietly(this.inputStream);
    }
}
