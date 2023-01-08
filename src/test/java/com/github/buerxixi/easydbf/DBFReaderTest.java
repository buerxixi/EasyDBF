package com.github.buerxixi.easydbf;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class DBFReaderTest {

    @Test
    public void findAll() {

        DBFReader reader = new DBFReader("C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\rep.dbf", Charset.forName("GBK"));
        System.out.println(reader.findAll());
    }

    @Test
    public void find() {
        DBFReader reader = new DBFReader("C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\测试文件.dbf", Charset.forName("GBK"));
        System.out.println(reader.find("NAME", "中文"));
    }

    @Test
    public void iterator() {
    }
}