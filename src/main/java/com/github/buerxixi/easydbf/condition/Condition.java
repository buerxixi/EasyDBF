package com.github.buerxixi.easydbf.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public class Condition {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("[+-]?\\d+(\\.\\d+)?");

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
                if (isNumber(dbfValue, value)) {
                    return compareNumbers(dbfValue, value) == 0;
                }
                return dbfValue.equals(value);
            case NEQ:
                if (isNumber(dbfValue, value)) {
                    return compareNumbers(dbfValue, value) != 0;
                }
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

    private static boolean isNumber(String str) {
        return NUMBER_PATTERN.matcher(str).matches();
    }

    private static boolean isNumber(String dbfValue, String conditionValue) {
        return isNumber(dbfValue) && isNumber(conditionValue);
        // 如果不是数字，你可以根据需要处理，这里简单返回 0
    }

    private int compareNumbers(String dbfValue, String conditionValue) {
        return new BigDecimal(dbfValue).compareTo(new BigDecimal(conditionValue));
    }
}
