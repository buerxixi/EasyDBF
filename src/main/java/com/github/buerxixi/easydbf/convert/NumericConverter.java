package com.github.buerxixi.easydbf.convert;

import com.github.buerxixi.easydbf.util.ByteUtils;
import com.github.buerxixi.easydbf.DBFField;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class NumericConverter extends AbstractTypeConverter {

    @Override
    public byte[] toBytes(String s, DBFField field, Charset charset) {
        byte[] bytes = new byte[field.getSize()];
        Arrays.fill(bytes, (byte) ' ');
        if (StringUtils.isNotEmpty(s)) {

            // TODO:校验是否为正确的小数结构
            // 获取小数位长度
            // 转成数字格式
            BigDecimal decimal = new BigDecimal(s);
            // 小数位
            int scale = Math.min(decimal.scale(), field.getDigits());

            // 格式化字符串
            String plain = decimal.setScale(scale, RoundingMode.FLOOR).toPlainString();

            return ByteUtils.mergeRight(bytes, plain.getBytes());
        }
        return bytes;
    }
}
