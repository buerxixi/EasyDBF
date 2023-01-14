package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.pojo.DBFInnerField;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public abstract class AbstractTypeConverter {

      /**
       * String转数据存储字节数组
       *
       * @param s 字符串
       * @param field 字段类型
       * @return 字节数组
       */
      public abstract byte[] toBytes(String s, DBFInnerField field);
}
