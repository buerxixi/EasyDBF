package com.github.buerxixi.easydbf.module;

import com.github.buerxixi.easydbf.annotation.DBFColumn;
import lombok.Data;

/**
 * name C(25); age N(3,0); birth D; qualified L
 */
@Data
public class Req {
    /**
     * 申报编号，长度为 10 的字符类型
     */
    @DBFColumn(size = 10)
    private String sbbh;
    /**
     * 服务域名，长度为 16 的字符类型
     */
    @DBFColumn(size = 16)
    private String fwym;
    /**
     * 服务名，长度为 16 的字符类型
     */
    @DBFColumn(size = 16)
    private String fwm;
    /**
     * 服务类型，长度为 2 的字符类型
     */
    @DBFColumn(size = 2)
    private String fwlx;
    /**
     * 请求数据文件标志，长度为 1 的字符类型
     */
    @DBFColumn(size = 1)
    private String wjbz;
    /**
     * 请求主记录数据文件名，长度为 24 的字符类型
     */
    @DBFColumn(size = 24)
    private String zwjm;
    /**
     * 请求从记录数据文件名，长度为 24 的字符类型
     */
    @DBFColumn(size = 24)
    private String cwjm;
    /**
     * 申报日期，长度为 8 的字符类型
     */
    @DBFColumn(size = 8)
    private String sbrq;
    /**
     * 申报时间，长度为 6 的字符类型
     */
    @DBFColumn(size = 6)
    private String sbsj;
    /**
     * 最大待处理时间，长度为 5 的字符类型
     */
    @DBFColumn(size = 5)
    private String zddclsj;
    /**
     * 请求处理标志，长度为 1 的字符类型
     */
    @DBFColumn(size = 1)
    private String clbz;
    /**
     * 交易流水号，长度为 16 的字符类型
     */
    @DBFColumn(size = 16)
    private String jylsh;
    /**
     * 备用字段，长度为 20 的字符类型
     */
    @DBFColumn(size = 20)
    private String by;
    /**
     * 请求数据文件校验码，长度为 4 的字符类型
     */
    @DBFColumn(size = 4)
    private String jym;
}
