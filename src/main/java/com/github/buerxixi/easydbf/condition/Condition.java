package com.github.buerxixi.easydbf.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
        }

        return false;
    }
}
