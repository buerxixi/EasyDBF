package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.model.DBFCharField;
import com.github.buerxixi.easydbf.model.DBFDateField;
import com.github.buerxixi.easydbf.model.DBFField;
import com.github.buerxixi.easydbf.model.DBFNumField;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 * <a href="https://www.clicketyclick.dk/databases/xbase/index.shtml.en">xbase</a>
 */
public class DBFWriterTest {

    private static   String filename = "D:\\张新师姐论文\\python\\D250401\\example.dbf";

    @Test
    public void deleteById() throws IOException {
        DBFWriter writer = new DBFWriter(filename);
        writer.deleteById(0);
    }

    @Test
    public void updateById() throws IOException {
        DBFWriter writer = new DBFWriter(filename);
        writer.updateById(0,"ZLZH", "中登资金账户00006");
    }

    @Test
    public void insert() throws IOException {
        DBFWriter writer = new DBFWriter(filename);
        HashMap<String, String> map = new HashMap<>();
        map.put("ZLZH", "中登资金账户00007");
        writer.insert(map);
    }

    @Test
    public void create() throws IOException {
//        'ID C(10)',
//        'NAME C(20)',
//        'AGE N(3,0)',
//        'BIRTHDAY D',
//        'SALARY N(10,2)',
        String filename = "D://dbf_test/test6.dbf";
        DBFCharField ID = new DBFCharField("ID", 10);
        DBFCharField NAME = new DBFCharField("NAME", 20);
        DBFNumField AGE = new DBFNumField("AGE", 3,0);
        DBFDateField BIRTHDAY = new DBFDateField("BIRTHDAY");
        DBFNumField SALARY = new DBFNumField("SALARY", 10,2);
        DBFWriter writer = new DBFWriter(filename);
        writer.create(ID,NAME,AGE,BIRTHDAY,SALARY);
    }
}