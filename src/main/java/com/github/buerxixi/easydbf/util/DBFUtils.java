package com.github.buerxixi.easydbf.util;

import com.github.buerxixi.easydbf.pojo.DBFField;
import com.github.buerxixi.easydbf.pojo.DBFHeader;
import com.github.buerxixi.easydbf.pojo.DBFRecord;
import com.github.buerxixi.easydbf.pojo.DBFRow;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DBFUtils {

    /**
     * 根据文件名称获取头文件
     *
     * @param filename 文件地址
     * @return header信息
     */
    public static DBFHeader getHeader(String filename) throws IOException {
        try(InputStream inStream = Files.newInputStream(Paths.get(filename))){
            byte[] bytes = new byte[32];
            inStream.read(bytes);
            DBFHeader header = DBFHeader.from(bytes);
            for (int i = 0; i < (header.getHeaderLength() - 32 - 1) / 32; i++) {
                inStream.read(bytes);
                header.getFields().add(DBFField.from(bytes));
            }

            return header;
        }

    }

    public static List<DBFRecord> getRecords(DBFRow row, List<DBFField> fields){
        List<DBFRecord> records = new ArrayList<>();

        int startIndex = 1;
        for (int i = 0; i < fields.size(); i++) {
            DBFField field = fields.get(i);
            byte[] subarray = ArrayUtils.subarray(row.getBytes(), startIndex, startIndex + field.getSize());
            // 数据叠加
            startIndex += field.getSize();
            records.add(DBFRecord.of(row.getRownum(), i, subarray));
        }

        return records;
    }
}
