package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.util.RecordUtils;
import org.junit.Test;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DBFReaderTest {

    final static String filename = "C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\测试文件3.dbf";
    final static Charset charset = StandardCharsets.UTF_8;

    @Test
    public void findAll() {

        DBFReader reader = new DBFReader("C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\rep.dbf", Charset.forName("GBK"));
        System.out.println(RecordUtils.toMap(reader.findAll()));
    }

    @Test
    public void find() {
        DBFReader reader = new DBFReader(filename, Charset.forName("GBK"));
        List<DBFRow> rows = reader.find("NAME", "李风娇");
        List<DBFRecord> records = rows.get(1).getRecords();
        System.out.println(records);
        System.out.println(RecordUtils.toMap(rows));
    }

    @Test
    public void iterator() {
    }
}