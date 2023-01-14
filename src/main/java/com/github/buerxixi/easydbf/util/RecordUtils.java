package com.github.buerxixi.easydbf.util;

import com.github.buerxixi.easydbf.DBFField;
import com.github.buerxixi.easydbf.DBFInnerField;
import com.github.buerxixi.easydbf.DBFRecord;
import com.github.buerxixi.easydbf.DBFRow;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class RecordUtils {

    public static List<Map<String, String>> toMap(List<DBFRow> rows) {
        if(rows.isEmpty()) {
            return new ArrayList<>();
        }
        List<DBFInnerField> fields = rows.get(0).getTable().getFields();
        return rows.stream().map(row -> {
            HashMap<String, String> hashMap = new LinkedHashMap<>();
            List<DBFRecord> records = row.getRecords();
            for (int i = 0; i < records.size(); i++) {
                String value = records.get(i).getString();
                if(StringUtils.isNotEmpty(value)) {
                    hashMap.put(fields.get(i).getName(), value);
                }
            }
          return  hashMap;
        }).collect(Collectors.toList());
    }
}