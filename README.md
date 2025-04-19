# EasyDBF
> 目前支持格式为dbase III/DBF 2.5标准格式/FOXPRO2.5标准DBF格式
> 
> 您有任何问题可以邮件联系 liujiaqiang@outlook.com
> 
> 完美支持中登（中国证券登记结算有限责任公司）格式，例如：D-COM（深交所）、PROP（上交所）
> 
> 支持创建、插入、更新、删除功能（CRUD、增删改查）

上交所

[登记结算数据接口规范 （结算参与人版V3.95）](http://www.chinaclear.cn/zdjs/jshsc/202502/143bfe0cd2084f49bedc7f3d5f81788b/files/%E7%99%BB%E8%AE%B0%E7%BB%93%E7%AE%97%E6%95%B0%E6%8D%AE%E6%8E%A5%E5%8F%A3%E8%A7%84%E8%8C%83%EF%BC%88%E7%BB%93%E7%AE%97%E5%8F%82%E4%B8%8E%E4%BA%BA%E7%89%88V3.95%EF%BC%89.pdf)

深交所

[深市登记结算数据接口规范 （Ver5.16）](http://www.chinaclear.cn/zdjs/jszsc/202501/917200e703ea4708b3f6b424ac957fa9/files/%E6%B7%B1%E5%B8%82%E7%99%BB%E8%AE%B0%E7%BB%93%E7%AE%97%E6%95%B0%E6%8D%AE%E6%8E%A5%E5%8F%A3%E8%A7%84%E8%8C%83%EF%BC%88%E7%BB%93%E7%AE%97%E5%8F%82%E4%B8%8E%E4%BA%BA%E7%89%88Ver5.16%EF%BC%89.pdf)


## 安装

```xml
<!-- https://mvnrepository.com/artifact/io.github.buerxixi/EasyDBF -->
<dependency>
    <groupId>io.github.buerxixi</groupId>
    <artifactId>EasyDBF</artifactId>
    <version>0.3.1-RELEASE</version>
</dependency>
```

## DBF数据结构

```
参考链接：https://en.wikipedia.org/wiki/.dbf
Tab = Header(32bit) + n*Field(32bit) + 0x0D + n*Record() + 0x1A
Header(32bit) = bytes[0](版本信息) + bytes[1:3](年月日) + bytes[4:7](记录条数) + bytes[8:9](头文件长度) + bytes[10:11](记录长度)
Field(32bit) = bytes[0:10](字段名称) + bytes[11](数据类型) + bytes[12:15](偏移量) + bytes[16](字段长度) + bytes[17](字段精度)
Record = 字段类型“Character”表示文本型字符串，填写方式为左对齐右补空格，而“Numeric”为数字型字符串填写方式为右对齐，左补空格
```
## 说明
### 支持类型

| 名称           | 类型      | Java类型         | 示例     | 代码           |
| -------------- | --------- | ---------------- | -------- |--------------|
| 字符串         | Character | java.lang.String | 中文     | DBFCharField |
| 日期（年月日） | Date      | java.lang.String | 20230122 | DBFDateField |
| 金额           | Numeric   | java.lang.String | 5.21     | DBFNumField  |

## 使用方法
> 创建和查询默认编码为GBK格式

### 创建DBF

``` java
// 创建结构体
String filename = "D://dbf_test/test6.dbf";
// 字符串
DBFCharField ID = new DBFCharField("ID", 10);
// 字符串
DBFCharField NAME = new DBFCharField("NAME", 20);
// 数字
DBFNumField AGE = new DBFNumField("AGE", 3,0);
// 日期
DBFDateField BIRTHDAY = new DBFDateField("BIRTHDAY");
// 金额
DBFNumField SALARY = new DBFNumField("SALARY", 10,2);
DBFWriter writer = new DBFWriter(filename);
writer.create(ID,NAME,AGE,BIRTHDAY,SALARY);
```

### 0.2.4-RELEASE添加根据条件查询、更新和删除功能
``` java
// 条件查询
DBFHandler handler = new DBFHandler(filename);
List<List<DBFItem>> itemsList = handler.query(new QueryCondition().eq("AGE", "25"));
for (List<DBFItem> items : itemsList) {
    System.out.println(DBFUtils.items2Map(items));
}


// 查询第一条符合的数据
DBFHandler handler = new DBFHandler(filename);
Optional<List<DBFItem>> items = handler.first(new QueryCondition().eq("AGE", "26"));
if (items.isPresent()) {
    System.out.println(DBFUtils.items2Map(items.get()));
}

// 删除数据
DBFHandler handler = new DBFHandler(filename);
handler.delete(new QueryCondition().eq("AGE", "26"));


// 更新数据
DBFHandler handler = new DBFHandler(filename);
handler.update(new QueryCondition().eq("AGE", "25"), "AGE", "26");

```

### 查询DBF信息

``` java
// 查询Header信息
String filename = "D://dbf_test/test6.dbf";
DBFHeader header = DBFUtils.getHeader(filename);
System.out.println(header);

// 控制台输出
// DBFHeader(version=3, year=2025, month=4, day=1, numberOfRecords=2, headerLength=193, recordLength=52, languageDriver=77)

// 查询Fields信息
List<DBFField> fields = DBFUtils.getFields(filename);
for (DBFField field : fields) {
    System.out.println(field);
}

// 控制台输出
// DBFField(charset=GBK, name=ID, type=CHARACTER, offset=1, size=10, digits=0)
// DBFField(charset=GBK, name=NAME, type=CHARACTER, offset=11, size=20, digits=0)
// DBFField(charset=GBK, name=AGE, type=NUMERIC, offset=31, size=3, digits=0)
// DBFField(charset=GBK, name=BIRTHDAY, type=DATE, offset=34, size=8, digits=0)
// DBFField(charset=GBK, name=SALARY, type=NUMERIC, offset=42, size=10, digits=2)

```

### 根据InputStream读取数据（注:只能读取不支持写入）
``` java
// 创建结构体
String filename = "D://dbf_test/test6.dbf";
try (InputStream inputStream = Files.newInputStream(Paths.get(filename))){
    DBFInputStreamReaderIterator readerIterator = new DBFInputStreamReaderIterator(inputStream);
    System.out.println(readerIterator.getHeader());
    System.out.println(readerIterator.getFields());
    while (readerIterator.hasNext()) {
        List<DBFItem> item = readerIterator.next();
        LinkedHashMap<String, String> items2Map = DBFUtils.items2Map(item);
        System.out.println(items2Map);
    }
}

// 控制台输出
// DBFHeader(version=3, year=2025, month=4, day=1, numberOfRecords=2, headerLength=193, recordLength=52, languageDriver=77)
// [DBFField(charset=GBK, name=ID, type=CHARACTER, offset=1, size=10, digits=0), DBFField(charset=GBK, name=NAME, type=CHARACTER, offset=11, size=20, digits=0), DBFField(charset=GBK, name=AGE, type=NUMERIC, offset=31, size=3, digits=0), DBFField(charset=GBK, name=BIRTHDAY, type=DATE, offset=34, size=8, digits=0), DBFField(charset=GBK, name=SALARY, type=NUMERIC, offset=42, size=10, digits=2)]
// {ID=001, NAME=张三, AGE=25, BIRTHDAY=19980510, SALARY=5000.50}
// {ID=002, NAME=李四, AGE=30, BIRTHDAY=19930815, SALARY=8000.75}
```

### 插入数据
``` java
// 创建结构体
String filename = "D://dbf_test/test6.dbf";
DBFWriter writer = new DBFWriter(filename);

// 插入数据
List<Map<String, String>> list = new  ArrayList<>();
// 数据001
Map<String, String> id001 = new HashMap<>();
id001.put("ID", "001");
id001.put("NAME", "张三");
id001.put("AGE", "25");
id001.put("BIRTHDAY", "19980510");
id001.put("SALARY", "5000.50");
list.add(id001);
// 数据002
Map<String, String> id002 = new HashMap<>();
id002.put("ID", "002");
id002.put("NAME", "李四");
id002.put("AGE", "30");
id002.put("BIRTHDAY", "19930815");
id002.put("SALARY", "8000.75");
list.add(id002);

writer.insert(list);
```

### 查询数据
``` java
String filename = "D://dbf_test/test6.dbf";

try (DBFReaderIterator dbfRowIterator = new DBFReaderIterator(filename)) {
    while (dbfRowIterator.hasNext()) {
        List<DBFItem> items = dbfRowIterator.next();
        // item 包含id、fieldName、value
        // 其中id为Record的索引可用于删除、更新
        LinkedHashMap<String, String> items2Map = DBFUtils.items2Map(items);
        System.out.println(items2Map);
    }
}

// 控制台输出
// {ID=001, NAME=张三, AGE=25, BIRTHDAY=19980510, SALARY=5000.50}
// {ID=002, NAME=李四, AGE=30, BIRTHDAY=19930815, SALARY=8000.75}
```


### 删除数据（逻辑删除）
``` java
// id为查询数据的DBFItem::getId()
DBFWriter writer = new DBFWriter(filename);
writer.deleteById(0);
```

### 更新数据
``` java
// id为查询数据的DBFItem::getId()
DBFWriter writer = new DBFWriter(filename);
writer.updateById(0,"NAME", "刘家强");
```

## 源码构建

> 克隆到本地并使用maven进行构建

```shell
git clone https://github.com/buerxixi/EasyDBF.git
cd EasyDBF
mvn clean package
```

```shell
# 发布指令
mvn clean deploy -DskipTests=true
```

## 参考

> https://github.com/ethanfurman/dbf/tree/master/dbf
