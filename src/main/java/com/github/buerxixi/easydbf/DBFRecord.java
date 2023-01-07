package com.github.buerxixi.easydbf;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * DBFRecord
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
@Data
public class DBFRecord {

    /**
     * 索引
     */
    private Integer index;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 列元素
     */
    final public List<DBFRow> rows = new ArrayList<>();
}
