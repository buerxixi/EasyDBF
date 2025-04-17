package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.docm.annotation.DcomField;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DcomFiledReader {

    public static <T> List<T> reader(Class<T> clazz, InputStream is, Charset charset) throws Exception {
        List<T> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset))) {
            String line;
            // 按行读取数据
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                // 这里可以添加对每行数据的处理逻辑
                // 例如将每行数据转换为 T 类型的对象
                // 示例代码假设 T 类型有一个从字符串构造对象的方法
                T item = createObjectFromLine(clazz, line);
                result.add(item);
            }
        }
        return result;
    }

    private static <T> T createObjectFromLine(Class<T> clazz, String line) throws Exception {
        // 获取无参构造方法
        Constructor<T> constructor = clazz.getConstructor();
        // 使用无参构造方法创建对象
        T instance = constructor.newInstance();

        // 获取类的所有声明的字段（包括私有字段）
        Field[] fields = clazz.getDeclaredFields();
        int sumOffset = 0;
        for (Field field : fields) {
            // 检查字段是否有 DcomField 注解
            if (field.isAnnotationPresent(DcomField.class)) {
                DcomField dcomField = field.getAnnotation(DcomField.class);
                int offset = dcomField.offset();
                int length = dcomField.length();
                sumOffset += offset;
                field.setAccessible(true);

                // 从 line 中截取数据并设置到 field 中
                String fieldValue = line.substring(sumOffset, sumOffset + length).trim();
                field.set(instance, fieldValue);

                // 这里可以根据 offset 和 length 从 line 中截取数据并设置到 field 中
                // 示例代码未实现具体的数据截取和设置逻辑
                sumOffset += length;
            }
        }

        return instance;
    }
}
