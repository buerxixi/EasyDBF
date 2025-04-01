package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.pojo.DBFField;
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
        DBFHeader header = DBFUtils.getHeader(filename);
        System.out.println(header);
    }

    @Test
    public void readDbfFields() throws IOException {
        String filename = "D:\\赢时胜\\中登报文\\tzxx.DBF";
        List<DBFField> fields = DBFUtils.getFields(filename);
        for (DBFField field : fields) {
            System.out.println(field);
        }
    }
}