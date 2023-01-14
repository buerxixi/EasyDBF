package com.github.buerxixi.easydbf;

import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * DBFRecord
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
@Data
public class DBFRow {

    /**
     * 第几行元素
     */
    private Integer rowNum;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 数据
     */
    private byte[] bytes;

    /**
     * 表空间
     */
    private DBFTable table;


    /**
     * 列元素
     */

    public DBFRow(Integer rowNum, byte[] bytes, DBFTable table) {
        this.rowNum = rowNum;
        this.bytes = bytes;
        this.deleted = bytes[0] == DBFConstant.DELETED_OF_FIELD;
        this.table = table;
    }

    // 跳过第一个字符位删除位
    public List<DBFRecord> getRecords(){
        List<DBFRecord> records = new ArrayList<>();
        List<DBFInnerField> fields = this.table.getFields();

        int startIndex = 1;
        for (DBFInnerField field : fields) {
            byte[] subarray = ArrayUtils.subarray(bytes, startIndex, startIndex + field.getSize());
            // 数据叠加
            startIndex += field.getSize();
            records.add(new DBFRecord(field, this, subarray));
        }
        return records;
    }
}
