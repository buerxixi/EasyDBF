package com.github.buerxixi.easydbf.pojo;

public interface IConverter<T extends IConverter<T>> {
    T fromBytes(byte[] bytes);

    byte[] toBytes();
}
