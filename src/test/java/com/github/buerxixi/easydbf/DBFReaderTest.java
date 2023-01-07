package com.github.buerxixi.easydbf;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class DBFReaderTest {

    @Test
    public void findAll() throws IOException {

        DBFReader reader = new DBFReader("C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\rep.dbf", Charset.forName("GBK"));
        System.out.println(reader.findAll());
    }
}