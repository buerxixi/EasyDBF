package com.github.buerxixi.easydbf;

import org.junit.Test;

import java.nio.charset.Charset;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DBFWriterTest {

    @Test
    public void add() {
        DBFWriter writer = new DBFWriter("C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\rep.dbf", Charset.forName("GBK"));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("SBBH", "中文是一个很优雅的事情");
        writer.add(hashMap);
    }

    @Test
    public void create() {
    }

    @Test
    public void drop() {
    }

    @Test
    public void update() {
        DBFWriter writer = new DBFWriter("C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\测试文件.dbf", Charset.forName("GBK"));
        writer.update("NAME","中文", "NAME","英文");
    }

    @Test
    public void delete() {
    }
}