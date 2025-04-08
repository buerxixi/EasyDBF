package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.condition.Condition;
import com.github.buerxixi.easydbf.condition.QueryCondition;
import com.github.buerxixi.easydbf.model.DBFField;
import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFItem;
import com.github.buerxixi.easydbf.pojo.DBFReaderIterator;
import lombok.SneakyThrows;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 该类用于读取DBF文件，实现了Iterable接口，可迭代获取DBF文件中的数据项列表。
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">刘家强</a>
 * @since 2025/04/01 16:58
 */
// @Log4j2
public class DBFReader implements Iterable<List<DBFItem>> {

    // 要读取的DBF文件的文件名
    private final String filename;
    // 读取DBF文件时使用的字符集
    private final Charset charset;

    /**
     * 构造一个DBFReader对象，使用指定的文件名和字符集。
     *
     * @param filename 要读取的DBF文件的文件名
     * @param charset  读取DBF文件时使用的字符集
     */
    public DBFReader(String filename, Charset charset) {
        // 增加参数检查
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("文件名不能为空");
        }
        if (charset == null) {
            throw new IllegalArgumentException("字符集不能为空");
        }
        this.filename = filename;
        this.charset = charset;
    }

    /**
     * 构造一个DBFReader对象，使用指定的文件名和默认字符集。
     *
     * @param filename 要读取的DBF文件的文件名
     */
    public DBFReader(String filename) {
        this(filename, DBFConstant.DEFAULT_CHARSET);
    }

    /**
     * 匹配
     *
     * @param fields         字段
     * @param queryCondition 条件
     * @param items          数据
     * @return 返回匹配结果
     */
    private boolean matched(List<DBFField> fields, QueryCondition queryCondition, List<DBFItem> items) {

        // 转化成映射关系
        Map<String, Condition> field2ConditionMap = queryCondition.getConditions().stream().collect(Collectors.toMap(Condition::getField, f -> f));

        // 索引映射过滤条件
        Map<Integer, Condition> idx2ConditionMap = new HashMap<>(queryCondition.getConditions().size());

        // 转化数据结构
        for (int i = 0; i < fields.size(); i++) {
            DBFField field = fields.get(i);
            Condition condition = field2ConditionMap.get(field.getName());
            if (condition != null) {
                idx2ConditionMap.put(i, condition);
            }
        }

        // 都匹配上才可以添加数据
        for (Map.Entry<Integer, Condition> entry : idx2ConditionMap.entrySet()) {
            DBFItem dbfItem = items.get(entry.getKey());
            Condition condition = entry.getValue();
            if (!condition.matched(dbfItem.getValue())) {
                return false;
            }
        }

        return true;
    }

    /**
     * 查询数据
     * TODO:此处查询可以优化直接在内存中查找即可
     *
     * @param queryCondition 查询条件
     * @return 返回查询后的数据
     * @throws IOException 异常
     */
    public List<List<DBFItem>> query(QueryCondition queryCondition) throws IOException {

        // 没有查询小件返回控制
        if (queryCondition.getConditions().isEmpty()) {
            // TODO 添加日志 没有查询条件
            return new ArrayList<>();
        }


        List<List<DBFItem>> result = new ArrayList<>();
        try (DBFReaderIterator iterator = new DBFReaderIterator(filename, charset)) {
            List<DBFField> fields = iterator.getFields();

            while (iterator.hasNext()) {
                List<DBFItem> next = iterator.next();
                // 都匹配上才可以添加数据
                boolean matched = matched(fields, queryCondition, next);

                if (matched) {
                    result.add(next);
                }
            }
        }
        return result;
    }


    /**
     * 查询单个数据
     *
     * @param queryCondition 查询条件
     * @return 返回查询后的数据
     * @throws IOException 异常
     */
    public Optional<List<DBFItem>> first(QueryCondition queryCondition) throws IOException {
        // 没有查询小件返回控制
        if (queryCondition.getConditions().isEmpty()) {
            // TODO 添加日志 没有查询条件
            return Optional.empty();
        }


        try (DBFReaderIterator iterator = new DBFReaderIterator(filename, charset)) {
            List<DBFField> fields = iterator.getFields();


            while (iterator.hasNext()) {
                List<DBFItem> next = iterator.next();
                boolean matched = matched(fields, queryCondition, next);

                if (matched) {
                    return Optional.of(next);
                }
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    @Override
    public Iterator<List<DBFItem>> iterator() {
        return new DBFReaderIterator(filename, charset);
    }
}
