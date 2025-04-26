package com.github.buerxixi.easydbf.annotation;

import com.github.buerxixi.easydbf.pojo.DBFFieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBFColumn {

    /**
     * 字段名
     * 如果为空则字段自动变为大写
     */
    String name() default "";

    /**
     * 字段类型
     * 默认字段名称为CHARACTER类型
     */
    DBFFieldType type() default DBFFieldType.CHARACTER;


    /**
     * 字段长度
     * 日期字段长度默认为8
     */
    int size() default 8;


    /**
     * 字段精度
     * 只有num类型才有字段精度默认字段精度为0
     */
    int digits() default 0;
}
