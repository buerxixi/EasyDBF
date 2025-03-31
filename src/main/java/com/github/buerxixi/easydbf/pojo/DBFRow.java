package com.github.buerxixi.easydbf.pojo;

import com.github.buerxixi.easydbf.DBFReader;
import com.github.buerxixi.easydbf.convert.TypeConverterStrategyFactory;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * 行数据
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 */
@Data
@SuperBuilder
public class DBFRow {

    private DBFField field;
    private DBFRecord record;

    public String getKey() {
       return field.getName();
    }

    public String getValue() {
        return TypeConverterStrategyFactory.getStrategy(field.getType()).fromBytes(field, record.getBytes());
    }
}
