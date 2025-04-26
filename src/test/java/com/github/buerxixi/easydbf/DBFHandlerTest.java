package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.condition.QueryCondition;
import com.github.buerxixi.easydbf.model.DBFCharField;
import com.github.buerxixi.easydbf.model.DBFDateField;
import com.github.buerxixi.easydbf.model.DBFNumField;
import com.github.buerxixi.easydbf.pojo.DBFItem;
import com.github.buerxixi.easydbf.util.DBFUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 *
 * @author liujiaqiang
 * <a href="https://www.clicketyclick.dk/databases/xbase/index.shtml.en">xbase</a>
 */
public class DBFHandlerTest {

    String filename = "D://dbf_test/test6.dbf";

    @Test
    public void create() throws IOException {

        // 创建结构体

        DBFCharField ID = new DBFCharField("ID", 10);
        DBFCharField NAME = new DBFCharField("NAME", 20);
        DBFNumField AGE = new DBFNumField("AGE", 3,0);
        DBFDateField BIRTHDAY = new DBFDateField("BIRTHDAY");
        DBFNumField SALARY = new DBFNumField("SALARY", 10,2);
        DBFWriter writer = new DBFWriter(filename);
        writer.create(ID,NAME,AGE,BIRTHDAY,SALARY);

        // 插入数据
        List<Map<String, String>> list = new  ArrayList<>();
        Map<String, String> id001 = new HashMap<>();
        id001.put("ID", "001");
        id001.put("NAME", "张三");
        id001.put("AGE", "25");
        id001.put("BIRTHDAY", "19980510");
        id001.put("SALARY", "5000.50");
        list.add(id001);
        Map<String, String> id002 = new HashMap<>();
        id002.put("ID", "002");
        id002.put("NAME", "李四");
        id002.put("AGE", "30");
        id002.put("BIRTHDAY", "19930815");
        id002.put("SALARY", "8000.75");
        list.add(id002);

        writer.insert(list);
    }

    @Test
    public void query() throws IOException {
        DBFHandler handler = new DBFHandler(filename);
        List<List<DBFItem>> itemsList = handler.query(new QueryCondition().lte("SALARY", "5000.50"));
        for (List<DBFItem> items : itemsList) {
            System.out.println(DBFUtils.items2Map(items));
        }
    }

    @Test
    public void first() throws IOException {
        DBFHandler handler = new DBFHandler(filename);
        Optional<List<DBFItem>> items = handler.first(new QueryCondition().lt("AGE", "26"));
        items.ifPresent(dbfItems -> System.out.println(DBFUtils.items2Map(dbfItems)));
    }

    @Test
    public void delete() throws IOException {
        DBFHandler handler = new DBFHandler(filename);
        handler.delete(new QueryCondition().eq("AGE", "26"));
    }

    @Test
    public void update() throws IOException {
        DBFHandler handler = new DBFHandler(filename);
        handler.update(new QueryCondition().eq("AGE", "25"), "AGE", "26");
    }
}