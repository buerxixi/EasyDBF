package com.github.buerxixi.easydbf;

import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public DBFRecord(Integer index, byte[] bytes, List<DBFField> fields){
        this.index = index;
        this.deleted = bytes[0] == DBFConstant.DELETED_OF_FIELD;

        // 跳过第一个字符位删除位
        int startIndex = 1;
        for (DBFField field : fields) {
            byte[] subarray = ArrayUtils.subarray(bytes, startIndex, startIndex + field.getSize());
            // 数据叠加
            startIndex += field.getSize();
            this.rows.add(new DBFRow(field.getIndex(), field.getType(), field.getCharset(), subarray));
        }
    }
}
