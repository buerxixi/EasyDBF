package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.DBFField;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public abstract class AbstractConverter {

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
            return new String(bytes, field.getCharset()).trim();
      }

      /**
       * String转数据存储字节数组
       *
       * @param s 字符串
       * @param field 字段类型
       * @return 字节数组
       */
      public abstract byte[] toBytes(String s, DBFField field);
}
