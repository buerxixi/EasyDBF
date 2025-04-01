package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.model.DBFField;
import com.github.buerxixi.easydbf.pojo.DBFHeader;
import com.github.buerxixi.easydbf.pojo.DBFItem;
import com.github.buerxixi.easydbf.pojo.DBFReaderIterator;
import com.github.buerxixi.easydbf.util.DBFUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 *
 * @author liujiaqiang
 * <a href="https://www.clicketyclick.dk/databases/xbase/index.shtml.en">xbase</a>
 */
public class DBFReaderTest {

    private static  String filename = "D:\\赢时胜\\中登报文\\tzxx.DBF";

    @Test
    public void readerDbfItems2() throws IOException {
        try (DBFReaderIterator dbfRowIterator = new DBFReaderIterator(filename)) {
            while (dbfRowIterator.hasNext()) {
                List<DBFItem> item = dbfRowIterator.next();
                LinkedHashMap<String, String> items2Map = DBFUtils.items2Map(item);
                System.out.println(items2Map);
            }
        }
    }

    @Test
    public void readDbfHeader() throws IOException {
        DBFHeader header = DBFUtils.getHeader(filename);
        System.out.println(header);
    }

    @Test
    public void readDbfFields() throws IOException {
        List<DBFField> fields = DBFUtils.getFields(filename);
        for (DBFField field : fields) {
            System.out.println(field);
        }
    }
}