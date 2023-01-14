package com.github.buerxixi.easydbf.util;

import com.github.buerxixi.easydbf.DBFField;
import com.github.buerxixi.easydbf.DBFInnerField;
import com.github.buerxixi.easydbf.DBFRecord;
import com.github.buerxixi.easydbf.DBFRow;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class RecordUtils {

    public static List<Map<String, String>> toMap(List<DBFRow> rows, List<DBFInnerField> fields) {
        return rows.stream().map(row -> {
            HashMap<String, String> hashMap = new LinkedHashMap<>();
            for (int i = 0; i < row.getRecords(fields).size(); i++) {
                hashMap.put(fields.get(i).getName(), row.getRecords(fields).get(i).getString());
            }
          return  hashMap;
        }).collect(Collectors.toList());
    }
}
