package com.github.buerxixi.easydbf.condition;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class QueryCondition {

    // 获取所有查询条件
    private final List<Condition> conditions = new ArrayList<>();

    // 添加左模糊查询条件
    public QueryCondition likeLeft(String field, String value) {
        conditions.add(new Condition(field, value, ConditionType.LIKE_LEFT));
        return this;
    }

    // 添加右模糊查询条件
    public QueryCondition likeRight(String field, String value) {
        conditions.add(new Condition(field, value, ConditionType.LIKE_LEFT));
        return this;
    }

    // 添加等于查询条件
    public QueryCondition eq(String field, String value) {
        conditions.add(new Condition(field, value, ConditionType.EQ));
        return this;
    }

    // 添加不相等查询条件
    public QueryCondition neq(String field, String value) {
        conditions.add(new Condition(field, value, ConditionType.NEQ));
        return this;
    }

}
