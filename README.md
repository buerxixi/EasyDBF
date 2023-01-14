# EasyDBF

> 当前仅仅是dome，预计会在2023年春节完成。第一次写开源感觉力不从心，但肯定会有一个完结结果

## DBF数据结构

```
参考链接：http://www.xumenger.com/dbf-20160703/
Tab = Header(32bit) + n*Field(32bit) + 0x0D + n*Row() + 0x1A
Header(32bit) = bytes[0](版本信息) + bytes[1:3](年月日) + bytes[4:7](记录条数) + bytes[8:9](头文件长度) + bytes[10:11](记录长度)
Field(32bit) = bytes[0:10](字段名称) + bytes[11](数据类型) + bytes[16](字段长度) + bytes[17](字段精度)
Row = 0x20/0x2A + n*Record
Record = 一个删除位 + 数据本体
```

## 支持类型

| 名称           | 类型      | Java类型         | 示例     |
| -------------- | --------- | ---------------- | -------- |
| 字符串         | Character | java.lang.String | 中文     |
| 日期（年月日） | Date      | java.lang.String | 20230122 |
| 金额           | Numeric   | java.lang.String | 5.21     |

## 使用方法

### 读取

``` java
DBFReader reader = new DBFReader("/path/file.dbf", Charset.forName("GBK"))); 
// 返回表结构
reader.getFileds();
// 根据字段查找
reader.find("NAME", "中文");
// 查找所有
reader.findAll();
// 返回迭代器
DBFFieldIterator iterator = reader.iterator();
```

### 写入

``` java
DBFWriter writer = new DBFWriter("/path/file.dbf", Charset.forName("GBK"))); 
// 更新数据
writer.update("NAME","旧值", "NAME","新值");
// 删除数据
writer.delete("NAME","删除值");
// 追加数据
Map<String, String> row = new HashMap<>();
row.put("NAME", "中文");
writer.add(row);

// TODO：创建表结构
// 第一种创建方法 
writer.create("NAME C(25); AGE N(3,0); BIRTH D");
// 第二种创建方法
List<DBFFiled> fileds = new ArrayList<>();
DBFFiled nameFiled = DBFFiled.builder().setName("NAME").setType("C").setSize(20).build();
DBFFiled ageFiled = DBFFiled.builder().setName("AGE").setType("C").setSize(3).setDigits(0).build();
DBFFiled birthFiled = DBFFiled.builder().setName("BIRTH").setType("D").build();
filed.add(nameFiled);
filed.add(ageFiled);
filed.add(birthFiled);
writer.create(fileds);

```

## 未来拓展

- 是否可以通过注解实现更为简单的功能（数据与对象的平稳转换）
- 待完善测试用例
- 金额符号问题
- 简化语言和内部逻辑
  - https://mp.weixin.qq.com/s/jwZ4jfE4Rn-RLVWSe_eGog
  - https://mp.weixin.qq.com/s/zDghbgqjv0qTrDn9SYAODw

## 源码构建

克隆到本地并使用maven进行构建

```she
https://github.com/buerxixi/EasyDBF.git
cd EasyDBF
mvn clean package
```

## 参考

https://github.com/ethanfurman/dbf/tree/master/dbf

https://github.com/albfernandez/javadbf

https://github.com/abstractvector/node-dbf

http://www.xumenger.com/dbf-20160703/

https://en.wikipedia.org/wiki/.dbf

可以引入Rxjava

api可参考org.apache.poi


### TODO:
``` java
// 1.整理表结构
// 2.优化文档
// 3.发布maven

```