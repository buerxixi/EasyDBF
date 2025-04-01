import dbf

# 定义表结构（字段名、类型、长度、小数位）
table = dbf.Table(
    filename='example.dbf',  # 文件名
    field_specs=[
        'ID C(10)',

        'NAME C(20)',

        'AGE N(3,0)',

        'BIRTHDAY D',

        'SALARY N(10,2)',
    ],
    codepage='cp936',  # 指定编码（中文字符需用GBK）
)

# 创建并打开表
table.open(mode=dbf.READ_WRITE)

# 添加数据
table.append(('001', '张三', 25, dbf.Date(1998, 5, 10), 5000.50))
table.append(('002', '李四', 30, dbf.Date(1993, 8, 15), 8000.75))

# 关闭表（保存到文件）
table.close()

print("DBF文件创建成功！")