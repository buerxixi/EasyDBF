package com.github.buerxixi.easydbf;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * DBFTable
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
@Getter
public class DBFTable {

    private final String filename;
    private final Charset charset;

    private DBFHeader header;

    private List<DBFField> fields;

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

    public void readHeader() {
        // 判断文件是否存在

        byte[] bytes = new byte[32];
        try (RandomAccessFile raf = new RandomAccessFile(this.filename, "r")) {
            raf.read(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.header = new DBFHeader(bytes);
    }

    public void readFields() {

        // 获取头文件
        ArrayList<DBFField> fields = new ArrayList<>();

        byte[] bytes = new byte[this.header.getHeaderLength() - 32 - 1];
        try (RandomAccessFile raf = new RandomAccessFile(this.filename, "r")) {

            raf.skipBytes(32);
            raf.read(bytes);

            for (int i = 0; i < bytes.length; i += 32) {
                // 获取字段取值
                byte[] subarray = ArrayUtils.subarray(bytes, i, i + 31);
                // 字段
                fields.add(new DBFField(subarray));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.fields = fields;
    }
}
