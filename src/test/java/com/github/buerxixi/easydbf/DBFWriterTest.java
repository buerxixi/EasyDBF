package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.pojo.DBFField;
import com.github.buerxixi.easydbf.pojo.DBFHeader;
import com.github.buerxixi.easydbf.pojo.DBFItem;
import com.github.buerxixi.easydbf.pojo.DBFReaderIterator;
import com.github.buerxixi.easydbf.util.DBFUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

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
}