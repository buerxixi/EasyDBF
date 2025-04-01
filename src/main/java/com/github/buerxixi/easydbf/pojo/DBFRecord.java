//package com.github.buerxixi.easydbf.pojo;
//
//import lombok.Data;
//import lombok.experimental.SuperBuilder;
//
///**
// * 记录数据
// *
// * @author <a href="mailto:liujiaqiang@outlook.com">Liujiaqiang</a>
// */
//@Data
//@SuperBuilder
//public class DBFRecord implements IConverter<DBFRecord> {
//
//    private final byte[] bytes;
//
//    @Override
//    public DBFRecord fromBytes(byte[] bytes) {
//        return DBFRecord.builder().bytes(bytes).build();
//    }
//
//    @Override
//    public byte[] toBytes() {
//        return bytes;
//    }
//}
