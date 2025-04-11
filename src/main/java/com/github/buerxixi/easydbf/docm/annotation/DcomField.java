package com.github.buerxixi.easydbf.docm.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DcomField {
    // 长度
    int length();
    // 距离上一个值的偏移量默认为0
    int offset() default 0;
}
