package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.pojo.DBFField;

import java.nio.charset.Charset;

/**
 * 类型转化抽象类
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
public abstract class AbstractTypeConverter {

      /**
       * String转数据存储字节数组
       *
       * @param s 字符串
       * @param field 字段类型
       * @return 字节数组
       */
      public abstract byte[] toBytes(String s, DBFField field,  Charset charset);
}
