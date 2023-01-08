package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.DBFField;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class FieldConverter extends AbstractStructConverter<DBFField> {

    @Override
    List<DBFField> toStruct(String s) {
        // 示例NAME C(25); AGE N(3,0); BIRTH D
        Arrays.stream(s.split(";")).map(v -> v.split("\r+"));
        return null;
    }

    @Override
    String toString(List<DBFField> structs) {
        return null;
    }
}
