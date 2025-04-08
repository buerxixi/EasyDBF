package com.github.buerxixi.easydbf.util;

import java.io.IOException;

@FunctionalInterface
public interface Callback<T> {
    void call(T t) throws IOException;
}
