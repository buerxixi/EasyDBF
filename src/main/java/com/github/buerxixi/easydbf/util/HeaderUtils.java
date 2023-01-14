package com.github.buerxixi.easydbf.util;

import com.github.buerxixi.easydbf.DBFConstant;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class HeaderUtils {

    public static byte[] toBytes() {
        byte[] bytes = new byte[32];
        // 设置版本
        bytes[0] = DBFConstant.DBASE_III;

        // 设置年月日
        LocalDate now = LocalDate.now();
        // 设置年月日
        bytes[1] = (byte) (now.getYear() - DBFConstant.START_YEAR);
        bytes[2] = (byte) now.getMonthValue();
        bytes[3] = (byte) now.getDayOfYear();

        return bytes;
    }
}
