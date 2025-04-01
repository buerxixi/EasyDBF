package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.convert.impl.CharTypeConverter;
import com.github.buerxixi.easydbf.pojo.DBFFieldType;

import java.util.HashMap;
import java.util.Map;

public class TypeConverterStrategyFactory {

    private static final Map<DBFFieldType, TypeConverterStrategy> strategyMap = new HashMap<>();

    static {
        strategyMap.put(DBFFieldType.NUMERIC, new NumTypeConverter());
        strategyMap.put(DBFFieldType.CHARACTER, new CharTypeConverter());
        strategyMap.put(DBFFieldType.DATE, new DateTypeConverter());
    }

    public static TypeConverterStrategy getStrategy(DBFFieldType type) {
        return strategyMap.getOrDefault(type, new CharTypeConverter());
    }
}
