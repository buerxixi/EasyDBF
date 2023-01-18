package com.github.buerxixi.easydbf.convert;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class FieldConverterTest {

    @Test
    public void toStruct() {
        // 示例NAME NAME C(25); AGE N(3,0); BIRTH D
        String s = "NAME C(25); AGE N(3,0); BIRTH D";
        List<String[]> stream = Arrays.stream(s.split(";")).map(String::trim).map(v -> v.split("\\s+")).collect(Collectors.toList());
        System.out.println(stream);
        stream.forEach(v -> {
            System.out.println("v = " + v[0]);
            getTypeAndSize(v[1]);
        });
    }

    public String[] getTypeAndSize(String s){
        System.out.println(s.charAt(0));
        // 获取精度
        return null;
    }

    @Test
    public void testToString() {
    }
}