package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.pojo.DBFField;
import com.github.buerxixi.easydbf.pojo.DBFFieldType;
import com.github.buerxixi.easydbf.util.FieldUtils;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class FieldUtilsTest {

    @Test
    public void toBytes() {
        ArrayList<DBFField> fields = new ArrayList<>();

        DBFField nameField = DBFField.builder().name("NAME").type(DBFFieldType.CHARACTER).size(20).build();
        DBFField ageFiled = DBFField.builder().name("AGE").type(DBFFieldType.DATE).size(5).digits(1).build();
        DBFField birthFiled = DBFField.builder().name("BIRTH").type(DBFFieldType.DATE).build();
        fields.add(nameField);
        fields.add(ageFiled);
        fields.add(birthFiled);
        byte[] bytes = FieldUtils.toBytes(fields);
        System.out.println(Arrays.toString(bytes));
    }
}