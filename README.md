# EasyDBF

> 写作初心是因为没有一个可以满足当前需求的第三方jar包
>
> 第一次写开源感觉力不从心，但还是完成了第一期功能和相关的API
>
> 写的时候最大的问题居然是写着写着时候的退缩-_-!

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

### 写入

``` java
final static String filename = "C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\测试文件3.dbf";
final static Charset charset = Charset.forName("GBK");

// 创建DBF文件
ArrayList<DBFField> list = new ArrayList<>();
DBFWriter writer = new DBFWriter(filename, charset);
DBFField nameField = DBFField.builder().name("NAME").type(DBFFieldType.CHARACTER).size(20).build();
DBFField ageFiled = DBFField.builder().name("AGE").type(DBFFieldType.NUMERIC).size(5).digits(1).build();
DBFField birthFiled = DBFField.builder().name("BIRTH").type(DBFFieldType.DATE).build();
list.add(nameField);
list.add(ageFiled);
list.add(birthFiled);
writer.create(list);

// 写入单条数据
HashMap<String, String> hashMap = new HashMap<>();
hashMap.put("NAME", "刘家强");
hashMap.put("AGE", "23.0");
hashMap.put("BIRTH", "19930119");
writer.add(hashMap);

// 更新数据
writer.update("NAME","刘家强", "NAME","李风娇");

// 删除数据
writer.delete("NAME","李风娇");
```

### 查询

``` java
final static String filename = "C:\\Users\\fangs\\Desktop\\开源项目\\EasyDBF\\测试文件3.dbf";
final static Charset charset = Charset.forName("GBK");

// 查询数据
DBFReader reader = new DBFReader(filename, charset);
System.out.println(RecordUtils.toMap(reader.find("NAME", "李风娇")));
// system.out [{NAME=李风娇, AGE=23.0, BIRTH=19930119}]

// 查询迭代器
try (DBFRowIterator iterator = reader.iterator()) {
    if(iterator.hasNext()){
    	System.out.println(iterator.next());
    }
    
    // WARNING: 需要关闭
}


```

## 未来拓展

- 是否可以通过注解实现更为简单的功能（数据与对象的平稳转换）
- 待完善测试用例
- 金额符号问题

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
