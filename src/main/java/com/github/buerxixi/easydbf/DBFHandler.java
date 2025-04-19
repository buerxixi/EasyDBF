package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.condition.Condition;
import com.github.buerxixi.easydbf.condition.QueryCondition;
import com.github.buerxixi.easydbf.model.DBFField;
import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFItem;
import com.github.buerxixi.easydbf.pojo.DBFReaderIterator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 该类用于处理DBF文件，提供查询、删除和更新等操作。
 */
public class DBFHandler {

    /**
     * 要操作的DBF文件的文件名。
     */
    final private String filename;
    /**
     * 用于读取和写入DBF文件的字符集。
     */
    final private Charset charset;

    /**
     * 构造函数，指定文件名和字符集。
     *
     * @param filename 要操作的DBF文件的文件名。
     * @param charset  用于读取和写入DBF文件的字符集。
     */
    public DBFHandler(String filename, Charset charset) {
        this.filename = filename;
        this.charset = charset;
    }

    /**
     * 构造函数，只指定文件名，使用默认字符集。
     *
     * @param filename 要操作的DBF文件的文件名。
     */
    public DBFHandler(String filename) {
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
        // 转化成映射关系，将查询条件中的字段名和条件对象建立映射
        Map<String, Condition> field2ConditionMap = queryCondition.getConditions().stream()
                .collect(Collectors.toMap(Condition::getField, f -> f));

        // 索引映射过滤条件，将字段索引和条件对象建立映射
        Map<Integer, Condition> idx2ConditionMap = new HashMap<>(queryCondition.getConditions().size());

        // 转化数据结构，遍历字段列表，将匹配的字段索引和条件对象放入idx2ConditionMap中
        for (int i = 0; i < fields.size(); i++) {
            DBFField field = fields.get(i);
            Condition condition = field2ConditionMap.get(field.getName());
            if (condition != null) {
                idx2ConditionMap.put(i, condition);
            }
        }

        // 都匹配上才可以添加数据，遍历idx2ConditionMap，检查每个条件是否匹配
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
        // 没有查询条件返回空列表
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
        // 没有查询条件返回空Optional
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

    /**
     * 根据查询条件删除DBF文件中的记录
     *
     * @param queryCondition 查询条件
     * @return 如果成功删除记录返回true，否则返回false
     * @throws IOException 当文件操作出现异常时抛出
     */
    public boolean delete(QueryCondition queryCondition) throws IOException {
        // 先根据查询条件查询出符合条件的记录
        List<List<DBFItem>> itemsList = query(queryCondition);
        // 如果没有符合条件的记录，返回false
        if (itemsList.isEmpty()) {
            return false;
        }
        // 提取出符合条件记录的行ID
        List<Integer> rowIds = itemsList.stream()
                .map(list -> list.get(0))
                .map(DBFItem::getRowId)
                .collect(Collectors.toList());
        // 使用DBFWriter根据行ID删除记录
        new DBFWriter(filename, charset).deleteByIds(rowIds);
        return true;
    }

    /**
     * 根据查询条件更新DBF文件中的记录
     *
     * @param queryCondition 查询条件
     * @param key            要更新的字段名
     * @param value          要更新的值
     * @return 如果成功更新记录返回true，否则返回false
     * @throws IOException 当文件操作出现异常时抛出
     */
    public boolean update(QueryCondition queryCondition, String key, String value) throws IOException {
        // 先根据查询条件查询出符合条件的记录
        List<List<DBFItem>> itemsList = query(queryCondition);
        // 如果没有符合条件的记录，返回false
        if (itemsList.isEmpty()) {
            return false;
        }
        // 创建DBFWriter对象用于更新记录
        DBFWriter writer = new DBFWriter(filename, charset);
        // 遍历符合条件的记录，根据行ID更新记录
        for (List<DBFItem> items : itemsList) {
            writer.updateById(items.get(0).getRowId(), key, value);
        }
        return true;
    }
}
