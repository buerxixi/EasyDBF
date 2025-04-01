package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.convert.impl.CharTypeConverter;
import com.github.buerxixi.easydbf.convert.impl.DateTypeConverter;
import com.github.buerxixi.easydbf.convert.impl.NumTypeConverter;
import com.github.buerxixi.easydbf.pojo.DBFFieldType;

import java.util.HashMap;
import java.util.Map;

/**
 * 字段类型转换工厂+策略类
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
 * @since 2025/04/01 16:58
 */
public class TypeConverterStrategyFactory {

    /**
     * 存储DBFFieldType和对应的TypeConverterStrategy实例的映射。
     */
    private static final Map<DBFFieldType, TypeConverterStrategy> strategyMap = new HashMap<>();

    static {
        // 初始化策略映射，将数值类型映射到NumTypeConverter实例
        strategyMap.put(DBFFieldType.NUMERIC, new NumTypeConverter());
        // 初始化策略映射，将字符类型映射到CharTypeConverter实例
        strategyMap.put(DBFFieldType.CHARACTER, new CharTypeConverter());
        // 初始化策略映射，将日期类型映射到DateTypeConverter实例
        strategyMap.put(DBFFieldType.DATE, new DateTypeConverter());
    }

    /**
     * 根据给定的DBFFieldType类型，返回对应的TypeConverterStrategy实例。
     * 如果没有找到对应的策略，则返回默认的CharTypeConverter实例。
     *
     * @param type 要转换的DBFFieldType类型
     * @return 对应的TypeConverterStrategy实例，如果未找到则返回CharTypeConverter实例
     */
    public static TypeConverterStrategy getStrategy(DBFFieldType type) {
        return strategyMap.getOrDefault(type, new CharTypeConverter());
    }
}
