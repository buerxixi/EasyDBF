package com.github.buerxixi.easydbf.convert;

import org.junit.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <p>
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public class NumericAbstractConverterTest {

    @Test
    public void test1(){
        BigDecimal bigDecimal = new BigDecimal("1.");

        // 获取小数位长度
        System.out.println(bigDecimal.scale());

        System.out.println(bigDecimal.setScale(2, RoundingMode.FLOOR).toPlainString());
    }
}