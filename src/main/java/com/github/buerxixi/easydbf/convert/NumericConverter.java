package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.ByteUtils;
import com.github.buerxixi.easydbf.DBFField;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class NumericConverter extends AbstractTypeConverter {

    @Override
    public byte[] toBytes(String s, DBFField field) {
        byte[] bytes = new byte[field.getSize()];
        if (StringUtils.isNotEmpty(s)) {

            // TODO:校验是否为正确的小数结构
            // 获取小数位长度
            // 转成数字格式
            BigDecimal decimal = new BigDecimal(s);
            // 小数位
            int scale = Math.min(decimal.scale(), field.getDigits());

            // 格式化字符串
            String plain = decimal.setScale(scale, RoundingMode.FLOOR).toPlainString();

            return ByteUtils.mergeRight(bytes, plain.getBytes(field.getCharset()));
        }
        return bytes;
    }
}
