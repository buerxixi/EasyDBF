# EasyDBF
> 目前支持格式为dbase III/DBF 2.5标准格式/FOXPRO2.5标准DBF格式
> 
> 您有任何问题可以邮件联系 liujiaqiang@outlook.com
> 
> 完美支持中登（中国证券登记结算有限责任公司）格式，例如：D-COM（深交所）、PROP（上交所）
> 
> 支持创建、插入、更新、删除功能（CRUD、增删改查）

## 安装

```pom
<!-- https://mvnrepository.com/artifact/io.github.buerxixi/EasyDBF -->
<dependency>
    <groupId>io.github.buerxixi</groupId>
    <artifactId>EasyDBF</artifactId>
    <version>0.2.1-RELEASE</version>
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
