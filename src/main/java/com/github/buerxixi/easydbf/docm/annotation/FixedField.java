package com.github.buerxixi.easydbf.docm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FixedField {
    // 长度
    int length();

    // 距离上一个值的偏移量默认为0
    int offset() default 0;

    // 动态字段
    boolean dynamic() default false;
}
