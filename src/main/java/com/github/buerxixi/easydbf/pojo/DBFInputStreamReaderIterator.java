package com.github.buerxixi.easydbf.pojo;

import com.github.buerxixi.easydbf.convert.TypeConverterStrategyFactory;
import com.github.buerxixi.easydbf.model.DBFField;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * DBFReader迭代器
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">刘家强</a>
 * @since 2025/04/01 16:58
 */
public class DBFInputStreamReaderIterator implements Iterator<List<DBFItem>> {

    protected final Charset charset;
    @Getter
    protected final DBFHeader header;
    @Getter
    protected final List<DBFField> fields = new ArrayList<>();
    protected final InputStream inputStream;
    protected Integer rowId = -1;

    public DBFInputStreamReaderIterator(InputStream inputStream) throws IOException {
        this(inputStream, DBFConstant.DEFAULT_CHARSET);
    }

    public DBFInputStreamReaderIterator(InputStream inputStream, Charset charset) throws IOException {
        this.charset = charset;
        this.inputStream = inputStream;

        // 读取头文件
        byte[] headerBytes = new byte[32];
        int bytesRead = inputStream.read(headerBytes);
        if (bytesRead != 32) {
            throw new IOException("Failed to read full header");
        }
        this.header = DBFHeader.builder().build().fromBytes(headerBytes);

        // 读取fields
        // 读取剩余header长度
        byte[] fieldsBytes = new byte[this.header.getHeaderLength() - 32];
        inputStream.read(fieldsBytes);
        // 依次按照32字节读取
        for (int i = 0;; i += 32) {

            // 区间范围超过就退出
            if (i + 32 > fieldsBytes.length) {
                break;
            }
            byte[] fieldBytes = new byte[32];
            System.arraycopy(fieldsBytes, i, fieldBytes, 0, 32);
            if (fieldBytes[0] == DBFConstant.END_OF_FIELD) {
                break;
            }
            DBFField field = DBFField.builder().build().fromBytes(fieldBytes);
            this.fields.add(field);
        }
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
            inputStream.skip(this.header.getRecordLength() - 1);
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
}
