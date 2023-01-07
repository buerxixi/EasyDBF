package com.github.buerxixi.easydbf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * DBFWriter
 * <p>
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class DBFWriter {

    /**
     * 创建表空间
     */
    public void create(Path path, List<DBFField> fields){
       // 判断文件是否存在
    }

    /**
     * 删除表空间
     *
     * @return 是否成功删除
     */
    public boolean drop(Path path) throws IOException {
        // 其实就是直接删除文件
        return Files.deleteIfExists(path);
    }

    public void insert(){
        // 插入数据

        // 插入数据

        // 插入结束符

        // 更改结束符
    }

    public void update(){
        // 更新数据
        // 按条件更新数据
        // 模糊查询
        // 左查询
        // 有查询
        // 不等于
        // 大于等于
        // 小于等于
        // 大于
        // 小于
        // 不为空
        // 更新日期
    }

    public void delete(){
        // 删除
        // 标识位置0
        // 更新日期
    }

    public void delete(List<Integer> indexes) throws IOException {
//        System.out.println(indexes);
//        DBFHeader header = this.getDBFHeader();
//        // 获取头文件的偏移量
//        // 获取数据的偏移量
//
//        try(RandomAccessFile raf = new RandomAccessFile(this.path, "rw")) {
//
//            for (Integer index : indexes) {
//                raf.seek(header.getHeaderLength() + (long) header.getRecordLength() * index);
//                raf.write(DBFConstant.DELETED_OF_FIELD);
//            }
//
//        }

    }

    public void delete(Integer index) throws IOException {
//        ArrayList<Integer> indexes = new ArrayList<>();
//        indexes.add(index);
//        delete(indexes);
    }
}
