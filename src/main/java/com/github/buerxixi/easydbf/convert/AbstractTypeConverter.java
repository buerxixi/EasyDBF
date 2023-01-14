package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.DBFField;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public abstract class AbstractTypeConverter {

      /**
       * 数据库读取字段转String
       * <p>
       * TODO:目前类型传入和返回都为String 以后可以针对类型单独处理
       * @param bytes 字节数组
       * @param field 字段类型
       * @return String
       */
      @Deprecated
      public String toString(byte[] bytes, DBFField field) {
            return new String(bytes, StandardCharsets.UTF_8).trim();
      }

      /**
       * String转数据存储字节数组
       *
       * @param s 字符串
       * @param field 字段类型
       * @return 字节数组
       */
      public abstract byte[] toBytes(String s, DBFField field, Charset charset);
}
