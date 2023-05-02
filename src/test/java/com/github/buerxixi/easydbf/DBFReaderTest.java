package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.pojo.DBFRowIterator;
import org.junit.Test;
import java.nio.charset.Charset;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 * https://www.clicketyclick.dk/databases/xbase/index.shtml.en
 */
public class DBFReaderTest {

    @Test
    public void reader() {
        String filename = "D:\\赢时胜\\中登报文\\tzxx.DBF";
        Charset charset = Charset.forName("GBK");
        DBFReader reader = new DBFReader(filename, charset);
        try(DBFRowIterator iterator = reader.iterator();){
            int i = 0;
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
                i++;
            }

            System.out.println(i);
        }
    }
}