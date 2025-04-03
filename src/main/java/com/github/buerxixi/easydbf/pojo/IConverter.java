package com.github.buerxixi.easydbf.pojo;

/**
 * 数据转换接口
 *
 * @param <T> 泛型类型，用于指定实现此接口的具体类型
 * @author <a href="mailto:liujiaqiang@outlook.com">刘家强</a>
 * @since 2025/04/01 16:58
 */
public interface IConverter<T extends IConverter<T>> {

    /**
     * 从字节数组中解析出对象。
     * <p>
     * 该方法将传入的字节数组转换为实现此接口的具体对象。
     * 字节数组应包含对象的完整状态信息，以便能够正确地恢复对象。
     *
     * @param bytes 包含对象状态信息的字节数组
     * @return 解析后的对象实例，类型为实现此接口的具体类型
     */
    T fromBytes(byte[] bytes);

    /**
     * 将当前对象转换为字节数组。
     * <p>
     * 该方法用于将实现此接口的对象的状态信息转换为字节数组。
     * 转换后的字节数组可以用于存储、传输等操作，并且可以通过 fromBytes 方法恢复对象。
     *
     * @return 包含对象状态信息的字节数组
     */
    byte[] toBytes();
}
