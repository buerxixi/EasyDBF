package com.github.buerxixi.easydbf;

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

    private static   String filename = "D:\\赢时胜\\中登报文\\Prop2000\\Data\\YLZH20200309000046.dbf";

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
}