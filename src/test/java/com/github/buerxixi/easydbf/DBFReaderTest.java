package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.pojo.DBFRecord;
import com.github.buerxixi.easydbf.pojo.DBFRow;
import com.github.buerxixi.easydbf.pojo.DBFRowIterator;
import com.github.buerxixi.easydbf.util.RecordUtils;
import org.junit.Test;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DBFReaderTest {

    final static String filename = "C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\测试文件3.dbf";
    final static Charset charset = Charset.forName("GBK");

    @Test
    public void findAll() {

        DBFReader reader = new DBFReader("C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\rep.dbf", Charset.forName("GBK"));
        System.out.println(RecordUtils.toMap(reader.findAll()));
    }

    @Test
    public void findAll2() {

        DBFReader reader = new DBFReader("C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\tzxx.dbf", Charset.forName("GBK"));
        System.out.println(RecordUtils.toMap(reader.findAll()));
    }

    @Test
    public void fin2() {

        DBFReader reader = new DBFReader("C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\tzxx.dbf", Charset.forName("GBK"));
        System.out.println(RecordUtils.toMap(reader.find("QSBH","100205")));
    }


    @Test
    public void find() {
        DBFReader reader = new DBFReader(filename, Charset.forName("GBK"));
        List<DBFRow> rows = reader.find("NAME", "李风娇");
        System.out.println(RecordUtils.toMap(rows));
    }

    @Test
    public void iterator() {
        DBFReader reader = new DBFReader(filename, Charset.forName("GBK"));
        try (DBFRowIterator iterator = reader.iterator()) {
            if(iterator.hasNext()){
                System.out.println(iterator.next());
            }
        }
    }
}