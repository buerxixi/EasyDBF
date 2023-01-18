package com.github.buerxixi.easydbf.pojo;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 表
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
@Data
public class DBFTable {

    private final String filename;
    private final Charset charset;

    private DBFHeader header;

    private List<DBFInnerField> fields;

    public Optional<DBFInnerField> findField(String name) {
        return fields.stream().filter(filed -> filed.getName().equals(name)).findFirst();
    }

    public DBFTable(String filename, Charset charset) {
        this.filename = filename;
        this.charset = charset;
    }

    /**
     * 初始化数据
     */
    public void readTable(){

        // 判断文件是否存在 & 是否可读

        this.readHeader();
        this.readFields();
    }

    @SneakyThrows
    public void readHeader() {
        // 判断文件是否存在

        byte[] bytes = new byte[32];
        try (RandomAccessFile raf = new RandomAccessFile(this.filename, "r")) {
            raf.read(bytes);
        }
        this.header = new DBFHeader(bytes);
    }

    public void readFields() {

        // 获取头文件
        ArrayList<DBFInnerField> fields = new ArrayList<>();

        byte[] bytes = new byte[this.header.getHeaderLength() - 32 - 1];
        try (RandomAccessFile raf = new RandomAccessFile(this.filename, "r")) {

            raf.skipBytes(32);
            raf.read(bytes);

            for (int i = 0, j = 0; i < bytes.length; i += 32, j++) {
                // 获取字段取值
                byte[] subarray = ArrayUtils.subarray(bytes, i, i + 31);
                // 字段
                fields.add(new DBFInnerField(j, subarray, this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.fields = fields;
    }
}
