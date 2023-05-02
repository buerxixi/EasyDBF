package com.github.buerxixi.easydbf.pojo;

import com.github.buerxixi.easydbf.util.DBFUtils;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 行迭代类
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class DBFRowIterator implements Iterator<List<DBFResult>>, AutoCloseable {

    final private Charset charset;
    final private DBFHeader header;
    final private boolean showDeletedRows;

    /**
     * 当前游标
     */
    private Integer index = 0;

    /**
     * 数据数据流
     */
    private final InputStream inputStream;

    private DBFRow row;

    public DBFRowIterator(String filename, Charset charset, boolean showDeletedRows) throws IOException {
        this.charset = charset;
        this.showDeletedRows = showDeletedRows;
        this.header = DBFUtils.getHeader(filename);
        inputStream = Files.newInputStream(Paths.get(filename));
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

        // 判断是否越界
        if (index >= this.header.getNumberOfRecords()) return false;

        // 判断是否删除，如果删除继续下一个
        byte[] bytes = new byte[this.header.getRecordLength()];
        index++;

        if (inputStream.read(bytes) < bytes.length) return false;

        if (BooleanUtils.isFalse(showDeletedRows) && bytes[0] == DBFConstant.DELETED_OF_FIELD) {
            return hasNext();
        }


        this.row = DBFRow.of(this.index, bytes);
        return true;
    }

    /**
     * 获取下一个文件
     */
    @SneakyThrows
    @Override
    public List<DBFResult> next() {

        List<DBFRecord> records = DBFUtils.getRecords(this.row, this.header.getFields());
        ArrayList<DBFResult> results = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {

            DBFField field = this.header.getFields().get(i);
            DBFRecord record = records.get(i);
            DBFResult result = DBFResult.builder()
                    .rownum(record.getRownum())
                    .key(field.getName())
                    .value(new String(record.getBytes(), charset).trim())
                    .type(field.getType())
                    .build();
            results.add(result);
        }


        return results;
    }

    public void close() {
        IOUtils.closeQuietly(this.inputStream);
    }
}
