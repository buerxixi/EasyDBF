package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.pojo.DBFHeader;
import com.github.buerxixi.easydbf.pojo.DBFRow;
import com.github.buerxixi.easydbf.pojo.DBFRowIterator;
import com.github.buerxixi.easydbf.util.DBFUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.List;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 * https://www.clicketyclick.dk/databases/xbase/index.shtml.en
 */
public class DBFReaderTest {

    @Test
    public void reader() throws IOException {
        String filename = "D:\\赢时胜\\中登报文\\tzxx.DBF";
        try (DBFRowIterator dbfRowIterator = new DBFRowIterator(filename)) {
            while (dbfRowIterator.hasNext()) {
                List<DBFRow> rows = dbfRowIterator.next();
                System.out.println(DBFUtils.rows2Map(rows));
            }
        }
    }

    @Test
    public void readDbfHeader() throws IOException {
        String filename = "D:\\赢时胜\\中登报文\\tzxx.DBF";
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            // 读取最后更新时间（偏移量 0x01-0x03）
            raf.seek(0x01);
            int year = 1900 + raf.readByte(); // 年份：存储的是自 1900 的偏移
            int month = raf.readByte();       // 月份 (1-12)
            int day = raf.readByte();         // 日 (1-31)
            System.out.printf("Last Updated: %04d-%02d-%02d%n", year, month, day);

            // 读取记录总数（偏移量 0x04-0x07，小端序）
            raf.seek(0x04);
            int recordCount = raf.readInt();
            System.out.println("Total Records (Header): " + recordCount);
        }
    }

    @Test
    public void readDbfHeader2() throws IOException {
        String filename = "D:\\赢时胜\\中登报文\\tzxx.DBF";
        DBFHeader header = DBFUtils.getHeader(filename);
        System.out.println(header);
    }
}