package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.condition.QueryCondition;
import com.github.buerxixi.easydbf.model.DBFField;
import com.github.buerxixi.easydbf.pojo.DBFHeader;
import com.github.buerxixi.easydbf.pojo.DBFInputStreamReaderIterator;
import com.github.buerxixi.easydbf.pojo.DBFItem;
import com.github.buerxixi.easydbf.pojo.DBFReaderIterator;
import com.github.buerxixi.easydbf.util.DBFUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *
 * @author 刘家强
 * <a href="https://www.clicketyclick.dk/databases/xbase/index.shtml.en">xbase</a>
 */
public class DBFReaderTest {

    private static  String filename = "D://dbf_test/test6.dbf";

    @Test
    public void readerDbfItems2() throws IOException {
        try (DBFReaderIterator readerIterator = new DBFReaderIterator(filename)) {
            while (readerIterator.hasNext()) {
                List<DBFItem> item = readerIterator.next();
                LinkedHashMap<String, String> items2Map = DBFUtils.items2Map(item);
                System.out.println(items2Map);
            }
        }
    }

    @Test
    public void inputStreamReaderDbfItems2() throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(filename))){
            DBFInputStreamReaderIterator readerIterator = new DBFInputStreamReaderIterator(inputStream);
            System.out.println(readerIterator.getHeader());
            System.out.println(readerIterator.getFields());
            while (readerIterator.hasNext()) {
                List<DBFItem> item = readerIterator.next();
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