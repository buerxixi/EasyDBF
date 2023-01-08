package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.DBFField;

import java.util.List;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public abstract class AbstractStructConverter<T> {

      // TODO：filed和row的转化
    abstract List<T> toStruct(String s);

    abstract String toString(List<T> structs);
}
