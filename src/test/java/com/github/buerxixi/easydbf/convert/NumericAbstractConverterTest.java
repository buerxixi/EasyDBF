package com.github.buerxixi.easydbf.convert;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
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