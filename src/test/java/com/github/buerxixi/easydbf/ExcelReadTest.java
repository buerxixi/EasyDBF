package com.github.buerxixi.easydbf;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * <p>
 *
 * @author liujiaqiang <liujiaqiang@outlook.com>
 */
public class ExcelReadTest {

    @Test
    public void excelRead() throws Exception {
        //创建输入流，接受目标excel文件
        FileInputStream in = new FileInputStream("D:/C盘迁移/Desktop/编程日常/POI导入测试数据.xls");
        //得到POI文件系统对象
        POIFSFileSystem fs = new POIFSFileSystem(in);
        //得到Excel工作簿对象
        HSSFWorkbook wk = new HSSFWorkbook(fs);
        //得到Excel工作簿的第一页，即excel工作表对象
        HSSFSheet sheet = wk.getSheetAt(0);
        //遍历工作表
        //遍历行对象
        for (Row row : sheet) {
            //打印行索引
            //System.out.println(row.getRowNum());
            //遍历单元格对象
            //表头不要打印
            for (Cell cell : row) {
                //获取每个单元格的类型
                CellType cellType = cell.getCellType();
                if(cellType == CellType.BLANK){
                    //System.out.println("空格类型");
                    System.out.print("\t");
                }
                if(cellType == CellType.NUMERIC){
                    //System.out.println("数字类型");
                    System.out.print(cell.getNumericCellValue()+"\t");
                }
                if(cellType == CellType.STRING){
                    //System.out.println("字符串类型");
                    System.out.print(cell.getStringCellValue()+"\t");
                }
            }
            //换行
            System.out.println();
        }

    }
}
