package com.github.buerxixi.easydbf.docm.module;

import com.github.buerxixi.easydbf.docm.annotation.FixedField;
import lombok.Data;

@Data
public class DcomFlag {

    /**
     * 开始传输标志
     */
    public static final String START_TRANSFER_FLAG = "00";
    /**
     * 结束传输标志
     */
    public static final String END_TRANSFER_FLAG = "10";

    /**
     * 数据文件名称
     * <p>
     * C60
     * 左对齐,不足部分补空格，
     * 举例：rtgshb00220191116.txt
     */
    @FixedField(length = 60)
    private String fileName;
    /**
     * 文件字节数
     * <p>
     * C16
     * 以字节为单位，左对齐，不足部分补空格
     */
    @FixedField(length = 16, offset = 1)
    private long fileSize;
    /**
     * 数据日期
     * <p>
     * C8
     * 数据日期，格式：YYYYMMDD
     */
    @FixedField(length = 8, offset = 1)
    private String dataDate;
    /**
     * 开始传输时间
     * <p>
     * C14
     * 服务机器时间，格式：YYYYMMDDHHMMSS
     */
    @FixedField(length = 14, offset = 1)
    private String startTime;
    /**
     * 结束传输时间
     * <p>
     * C14
     * 服务机器时间，格式：YYYYMMDDHHMMSS
     */
    @FixedField(length = 14, offset = 1)
    private String endTime;
    /**
     * 记录数
     * <p>
     * C12
     * 左对齐，不足部分补空格
     */
    @FixedField(length = 12, offset = 1)
    private int recordCount;
    /**
     * 传输标志
     * <p>
     * C2
     * “00”表示开始传输
     * “10”表示结束传输
     */
    @FixedField(length = 2, offset = 1)
    private int transferFlag;
    /**
     * 预留
     * <p>
     * C27
     * 保留字段
     */
    @FixedField(length = 27, offset = 1)
    private String reserved;

}
