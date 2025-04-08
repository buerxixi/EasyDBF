package com.github.buerxixi.easydbf.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Condition {
    private String field;
    private String value;
    private ConditionType type;

    /**
     * 匹配
     *
     * @param dbfValue 数据库中的值
     * @return true:匹配 false:不匹配
     */
    public boolean matched(String dbfValue) {
        // 去除左右空格
        dbfValue = dbfValue.trim();

        switch (type) {
            case LIKE_LEFT:
                return dbfValue.startsWith(value);
            case LIKE_RIGHT:
                return dbfValue.endsWith(value);
            case EQ:
                return dbfValue.equals(value);
            case NEQ:
                return !dbfValue.equals(value);
            case GT:
                return compareNumbers(dbfValue, value) > 0;
            case LT:
                return compareNumbers(dbfValue, value) < 0;
            case GTE:
                return compareNumbers(dbfValue, value) >= 0;
            case LTE:
                return compareNumbers(dbfValue, value) <= 0;
            default:
                return false;
        }
    }

    private int compareNumbers(String dbfValue, String conditionValue) {
        return new BigDecimal(dbfValue).compareTo(new BigDecimal(conditionValue));
    }
}
