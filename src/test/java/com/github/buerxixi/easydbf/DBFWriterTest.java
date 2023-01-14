package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.pojo.DBFField;
import com.github.buerxixi.easydbf.pojo.DBFFieldType;
import org.junit.Test;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DBFWriterTest {

    final static String filename = "C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\测试文件3.dbf";
    final static Charset charset = Charset.forName("GBK");

    @Test
    public void create() {

        ArrayList<DBFField> list = new ArrayList<>();
        DBFWriter writer = new DBFWriter(filename, charset);
        DBFField nameField = DBFField.builder().name("NAME").type(DBFFieldType.CHARACTER).size(20).build();
        DBFField ageFiled = DBFField.builder().name("AGE").type(DBFFieldType.NUMERIC).size(5).digits(1).build();
        DBFField birthFiled = DBFField.builder().name("BIRTH").type(DBFFieldType.DATE).build();
        list.add(nameField);
        list.add(ageFiled);
        list.add(birthFiled);
        writer.create(list);
    }

    @Test
    public void add() {
        DBFWriter writer = new DBFWriter(filename, charset);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("NAME", "刘家强");
        hashMap.put("AGE", "23.0");
        hashMap.put("BIRTH", "19930119");
        writer.add(hashMap);
    }

    @Test
    public void update() {
        DBFWriter writer = new DBFWriter(filename, charset);
        writer.update("NAME","刘家强", "NAME","李风娇");
    }

    @Test
    public void delete() {
        DBFWriter writer = new DBFWriter(filename, charset);
        writer.delete("NAME","李风娇");
    }

    @Test
    public void drop() {
        DBFWriter writer = new DBFWriter(filename, charset);
        writer.drop();
    }
}