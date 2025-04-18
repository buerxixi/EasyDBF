package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.docm.annotation.FixedField;
import com.github.buerxixi.easydbf.util.ArrayUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 魔法数字转换逻辑
 * 可参考<a href="https://gitee.com/mtils/magic-byte">magic-byte</a>
 */
public class FixedFiledReader {

    public static <T> List<T> reader(Class<T> clazz, InputStream is, Charset charset) throws Exception {
        List<T> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset))) {
            String line;
            // 按行读取数据
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                // 这里可以添加对每行数据的处理逻辑
                // 例如将每行数据转换为 T 类型的对象
                // 示例代码假设 T 类型有一个从字符串构造对象的方法
                T item = createObjectFromStr(clazz, line);
                result.add(item);
            }
        }
        return result;
    }

    /**
     * 根据字符串创建对象
     *
     * @param clazz 类
     * @param s     字符串
     * @param <T>   类型
     * @return 对象
     * @throws Exception 异常
     */
    public static <T> T createObjectFromStr(Class<T> clazz, String s) throws Exception {
        // 获取无参构造方法
        Constructor<T> constructor = clazz.getConstructor();
        // 使用无参构造方法创建对象
        T instance = constructor.newInstance();

        // 获取类的所有声明的字段（包括私有字段）
        Field[] fields = clazz.getDeclaredFields();
        int sumOffset = 0;
        for (Field field : fields) {
            // 检查字段是否有 DcomField 注解
            if (field.isAnnotationPresent(FixedField.class)) {
                FixedField dcomField = field.getAnnotation(FixedField.class);
                int offset = dcomField.offset();
                int length = dcomField.length();
                sumOffset += offset;
                field.setAccessible(true);

                // 从 line 中截取数据并设置到 field 中
                String fieldValue = s.substring(sumOffset, sumOffset + length).trim();
                field.set(instance, fieldValue);

                // 这里可以根据 offset 和 length 从 line 中截取数据并设置到 field 中
                // 示例代码未实现具体的数据截取和设置逻辑
                sumOffset += length;
            }
        }

        return instance;
    }

    /**
     * 根据字节数组创建对象
     *
     * @param clazz   类
     * @param bytes   字节数组
     * @param charset 字符集
     * @param <T>     类型
     * @return 对象
     * @throws Exception 异常
     */
    public static <T> T createObjectFromBytes(Class<T> clazz, byte[] bytes, Charset charset) throws Exception {
        // 获取无参构造方法
        Constructor<T> constructor = clazz.getConstructor();
        // 使用无参构造方法创建对象
        T instance = constructor.newInstance();

        // 获取类的所有声明的字段（包括私有字段）
        Field[] fields = clazz.getDeclaredFields();
        int sumOffset = 0;
        for (Field field : fields) {
            // 检查字段是否有 DcomField 注解
            if (field.isAnnotationPresent(FixedField.class)) {
                FixedField dcomField = field.getAnnotation(FixedField.class);
                int offset = dcomField.offset();
                int length = dcomField.length();
                sumOffset += offset;
                field.setAccessible(true);


                // 获取相关字节
                byte[] subBytes = ArrayUtil.sub(bytes, sumOffset, sumOffset + length);
                if (dcomField.dynamic()) { // 动态字段返回最后长度
                    subBytes = ArrayUtil.sub(bytes, sumOffset, bytes.length - 1);
                }

                // 从 line 中截取数据并设置到 field 中
                String fieldValue = new String(subBytes, charset).trim();
                field.set(instance, fieldValue);

                if (dcomField.dynamic()) { // 动态字段一般为尾部字段直接返回数据
                    return instance;
                }

                // 这里可以根据 offset 和 length 从 line 中截取数据并设置到 field 中
                // 示例代码未实现具体的数据截取和设置逻辑
                sumOffset += length;
            }
        }

        return instance;
    }
}
