# EasyDBF

> 当前仅仅是dome，预计会在2023年春节完成。第一次写开源感觉力不从心，但肯定会有一个完结结果

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
