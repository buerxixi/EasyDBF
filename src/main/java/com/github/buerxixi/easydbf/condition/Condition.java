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
        switch (this.getType()) {
            case LIKE_LEFT:
                if (dbfValue.startsWith(this.value)) {
                    return true;
                }
                break;
            case LIKE_RIGHT:
                if (dbfValue.endsWith(this.value)) {
                    return true;
                }
                break;
            case EQ:
                if (dbfValue.equals(this.value)) {
                    return true;
                }
                break;
            case NEQ:
                if (!dbfValue.equals(this.value)) {
                    return true;
                }
                break;
            // 数字比较
            case GT:
            case LT:
            case GTE:
            case LTE:
                int compared = new BigDecimal(dbfValue).compareTo(new BigDecimal(this.value));
                switch (this.getType()) {
                    case GT:
                        if (compared > 0) {
                            return true;
                        }
                        break;
                    case LT:
                        if (compared < 0) {
                            return true;
                        }
                        break;
                    case GTE:
                        if (compared >= 0) {
                            return true;
                        }
                        break;

                    case LTE:
                        if (compared <= 0) {
                            return true;
                        }
                        break;
                }
                break;

        }

        return false;
    }
}
