package com.github.buerxixi.easydbf;

import com.github.buerxixi.easydbf.convert.TypeConverterStrategy;
import com.github.buerxixi.easydbf.convert.TypeConverterStrategyFactory;
import com.github.buerxixi.easydbf.model.DBFField;
import com.github.buerxixi.easydbf.pojo.DBFConstant;
import com.github.buerxixi.easydbf.pojo.DBFHeader;
import com.github.buerxixi.easydbf.util.ByteUtils;
import com.github.buerxixi.easydbf.util.CommonUtils;
import com.github.buerxixi.easydbf.util.DBFUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用于写入和更新DBF文件的类。
 *
 * @author <a href="mailto:liujiaqiang@outlook.com">刘家强</a>
 * @since 2025/04/01 16:58
 */
public class DBFWriter {
    /**
     * 要操作的DBF文件的文件名。
     */
    final private String filename;
    /**
     * 用于读取和写入DBF文件的字符集。
     */
    final private Charset charset;

    /**
     * 构造函数，指定文件名和字符集。
     *
     * @param filename 要操作的DBF文件的文件名。
     * @param charset  用于读取和写入DBF文件的字符集。
     */
    public DBFWriter(String filename, Charset charset) {
        this.filename = filename;
        this.charset = charset;
    }

    /**
     * 构造函数，只指定文件名，使用默认字符集。
     *
     * @param filename 要操作的DBF文件的文件名。
     */
    public DBFWriter(String filename) {
        this(filename, DBFConstant.DEFAULT_CHARSET);
    }

    /**
     * 更新DBF文件的头部信息。
     *
     * @param header 包含DBF文件头部信息的对象。
     * @param raf    用于访问DBF文件的RandomAccessFile对象。
     * @throws IOException 如果在更新过程中发生I/O错误。
     */
    private void updateHeader(DBFHeader header, RandomAccessFile raf) throws IOException {
        updateDate(raf);
        updateNumberOfRecords(header, raf);
    }

    /**
     * 更新DBF文件头部的日期信息。
     *
     * @param raf 用于访问DBF文件的RandomAccessFile对象。
     * @throws IOException 如果在更新过程中发生I/O错误。
     */
    private void updateDate(RandomAccessFile raf) throws IOException {
        // 定位到文件头部的日期信息位置
        raf.seek(1);
        // 获取当前日期
        LocalDate now = LocalDate.now();
        // 写入当前年份相对于起始年份的偏移量
        raf.writeByte(now.getYear() - DBFConstant.START_YEAR);
        // 写入当前月份
        raf.writeByte(now.getMonthValue());
        // 写入当前日期
        raf.writeByte(now.getDayOfMonth());
    }

    /**
     * 更新DBF文件头部的记录长度信息。
     *
     * @param raf          用于访问DBF文件的RandomAccessFile对象。
     * @param recordLength 新的记录长度。
     * @throws IOException 如果在更新过程中发生I/O错误。
     */
    private void updateRecordLength(RandomAccessFile raf, Short recordLength) throws IOException {
        // 定位到文件头部的记录长度信息位置
        raf.seek(0x0A);
        // 写入新的记录长度（小端字节序）
        raf.write(ByteUtils.shortToBytesLE(recordLength));
    }

    /**
     * 更新DBF文件头部的头部长度信息。
     *
     * @param raf          用于访问DBF文件的RandomAccessFile对象。
     * @param headerLength 新的头部长度。
     * @throws IOException 如果在更新过程中发生I/O错误。
     */
    private void updateHeaderLength(RandomAccessFile raf, Short headerLength) throws IOException {
        // 定位到文件头部的头部长度信息位置
        raf.seek(8);
        // 写入新的头部长度（小端字节序）
        raf.write(ByteUtils.shortToBytesLE(headerLength));
    }

    /**
     * 更新DBF文件头部的记录数量信息。
     *
     * @param header 包含DBF文件头部信息的对象。
     * @param raf    用于访问DBF文件的RandomAccessFile对象。
     * @throws IOException 如果在更新过程中发生I/O错误。
     */
    private void updateNumberOfRecords(DBFHeader header, RandomAccessFile raf) throws IOException {
        // 计算记录数量，当前计算方式可能有问题
        Integer numberOfRecords = Math.toIntExact((raf.length() - header.getHeaderLength() - 1) / header.getRecordLength());
        // TODO: 定位到正确的位置写入记录数量
        raf.write(ByteUtils.intToBytesLE(numberOfRecords));
    }

    /**
     * 根据行ID和键值对更新DBF文件中的单条记录。
     *
     * @param rowId 要更新的记录的行ID。
     * @param key   要更新的字段名。
     * @param value 要更新的字段值。
     * @throws IOException 如果在更新过程中发生I/O错误。
     */
    public void updateById(Integer rowId, String key, String value) throws IOException {
        // 创建一个包含单个键值对的Map
        HashMap<String, String> map = new HashMap<>();
        map.put(key, value);
        // 调用更新多条记录的方法
        updateById(rowId, map);
    }

    /**
     * 根据行ID和键值对更新DBF文件中的单条记录。
     *
     * @param rowId  要更新的记录的行ID。
     * @param values 包含要更新的字段名和值的Map。
     * @throws IOException 如果在更新过程中发生I/O错误。
     */
    public void updateById(Integer rowId, Map<String, String> values) throws IOException {
        // 获取DBF文件的头部信息
        DBFHeader header = DBFUtils.getHeader(filename);
        // 获取DBF文件的字段信息
        List<DBFField> fields = DBFUtils.getFields(filename, charset);
        // 将字段名映射到对应的DBFField对象
        Map<String, DBFField> name2Field = fields.stream().collect(Collectors.toMap(DBFField::getName, f -> f));


        CommonUtils.sharedLock(filename, (raf) -> {
            // 遍历要更新的键值对
            for (Map.Entry<String, String> entry : values.entrySet()) {
                // 获取要更新的字段
                DBFField field = name2Field.get(entry.getKey());
                if (field != null) {
                    // 定位到要更新的记录的字段位置
                    raf.seek(header.getHeaderLength() + (long) rowId * header.getRecordLength() + field.getOffset());
                    // 获取字段类型对应的转换策略
                    TypeConverterStrategy strategy = TypeConverterStrategyFactory.getStrategy(field.getType());
                    // 将值转换为字节数组
                    byte[] bytes = strategy.toBytes(field, entry.getValue());
                    // 写入更新后的字节数组
                    raf.write(bytes);
                }
            }
            // 更新文件头部信息
            updateHeader(header, raf);
        });
    }

    /**
     * 根据行ID删除DBF文件中的单条记录。
     *
     * @param rowId 要删除的记录的行ID。
     * @throws IOException 如果在删除过程中发生I/O错误。
     */
    public void deleteById(Integer rowId) throws IOException {
        // 创建一个包含单个行ID的列表
        List<Integer> rowIds = new ArrayList<>();
        rowIds.add(rowId);
        // 调用删除多条记录的方法
        deleteByIds(rowIds);
    }

    /**
     * 根据行ID列表删除DBF文件中的多条记录。
     *
     * @param rowIds 要删除的记录的行ID列表。
     * @throws IOException 如果在删除过程中发生I/O错误。
     */
    public void deleteByIds(List<Integer> rowIds) throws IOException {
        // 获取DBF文件的头部信息
        DBFHeader header = DBFUtils.getHeader(filename);
        CommonUtils.sharedLock(filename, (raf) -> {
            // 遍历要删除的行ID列表
            for (Integer rowId : rowIds) {
                // 定位到要删除的记录的起始位置
                raf.seek(header.getHeaderLength() + (long) header.getRecordLength() * rowId);
                // 写入删除标记
                raf.writeByte(DBFConstant.DELETED_OF_FIELD);
            }
            // 更新文件头部信息
            updateDate(raf);
        });
    }

    /**
     * 根据可变参数创建DBF文件。
     *
     * @param fields 要创建的DBF文件的字段列表。
     * @param <T>    字段类型，必须继承自DBFField。
     * @throws IOException 如果在创建过程中发生I/O错误。
     */
    @SafeVarargs
    public final <T extends DBFField> void create(T... fields) throws IOException {
        // 调用创建方法，传入字段列表
        create(Arrays.asList(fields));
    }

    /**
     * 根据字段列表创建DBF文件。
     *
     * @param fields 要创建的DBF文件的字段列表。
     * @param <T>    字段类型，必须继承自DBFField。
     * @throws IOException 如果在创建过程中发生I/O错误。
     */
    public <T extends DBFField> void create(List<T> fields) throws IOException {
        // 创建文件对象
        File file = new java.io.File(filename);
        // 获取文件的父目录
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            // 如果父目录不存在，则创建父目录
            if (!parentDir.mkdirs()) {
                throw new IOException("无法创建文件夹: " + parentDir.getAbsolutePath());
            }
        }
        if (!file.exists()) {
            // 如果文件不存在，则创建文件
            if (!file.createNewFile()) {
                throw new IOException("无法创建文件: " + file.getAbsolutePath());
            }
        }
        CommonUtils.sharedLock(filename, (raf) -> {
            // 构建DBF文件的头部信息
            DBFHeader header = DBFHeader.builder().build();
            // 写入头部信息
            raf.write(header.toBytes());
            // 初始化字段偏移量
            int offset = 1;
            // 遍历字段列表
            for (T field : fields) {
                // 设置字段的偏移量
                field.setOffset(offset);
                // 更新偏移量
                offset += field.getSize();
                // 写入字段信息
                raf.write(field.toBytes());
            }
            // 写入字段结束标记
            raf.writeByte(DBFConstant.END_OF_FIELD);
            // 写入文件结束标记
            raf.writeByte(DBFConstant.END_OF_DATA);
            // 更新文件头部的日期信息
            updateDate(raf);
            // 计算并更新文件头部的头部长度信息
            Short headerLength = (short) ((32 * (1 + fields.size())) + 1);
            updateHeaderLength(raf, headerLength);
            // 计算并更新文件头部的记录长度信息
            Short recordLength = (short) (fields.stream()
                    .map(DBFField::getSize)
                    .reduce((pre, cur) -> (byte) (pre.intValue() + cur.intValue()))
                    .get().intValue() + 1);
            updateRecordLength(raf, recordLength);
        });
    }

    /**
     * 向DBF文件中插入单条记录。
     *
     * @param value 要插入的记录的键值对。
     * @throws IOException 如果在插入过程中发生I/O错误。
     */
    public void insert(Map<String, String> value) throws IOException {
        // 创建一个包含单个记录的列表
        ArrayList<Map<String, String>> list = new ArrayList<>();
        list.add(value);
        // 调用插入多条记录的方法
        insert(list);
    }

    /**
     * 向DBF文件中插入多条记录。
     *
     * @param values 要插入的记录的键值对列表。
     * @throws IOException 如果在插入过程中发生I/O错误。
     */
    public void insert(List<Map<String, String>> values) throws IOException {
        // 获取DBF文件的头部信息
        DBFHeader header = DBFUtils.getHeader(filename);
        // 获取DBF文件的字段信息
        List<DBFField> fields = DBFUtils.getFields(filename, charset);

        CommonUtils.sharedLock(filename, raf -> {
            // 将字段名映射到对应的DBFField对象
            Map<String, DBFField> name2Field = fields.stream().collect(Collectors.toMap(DBFField::getName, f -> f));
            // 定位到文件末尾的下一个记录位置
            raf.seek(header.getHeaderLength() + (long) header.getRecordLength() * header.getNumberOfRecords());
            // 遍历要插入的记录列表
            for (Map<String, String> value : values) {
                // 创建一个字节数组，用于存储记录
                byte[] bytes = new byte[header.getRecordLength()];
                // 初始化字节数组为未删除状态
                Arrays.fill(bytes, DBFConstant.UNDELETED_OF_FIELD);
                // 遍历记录的键值对
                for (Map.Entry<String, String> entry : value.entrySet()) {
                    // 获取要插入的字段
                    DBFField field = name2Field.get(entry.getKey());
                    if (field != null) {
                        // 获取字段类型对应的转换策略
                        TypeConverterStrategy strategy = TypeConverterStrategyFactory.getStrategy(field.getType());
                        // 将值转换为字节数组
                        byte[] filedBytes = strategy.toBytes(field, entry.getValue());
                        // 将字节数组复制到记录的相应位置
                        System.arraycopy(filedBytes, 0, bytes, field.getOffset(), field.getSize());
                    }
                }
                // 写入记录
                raf.write(bytes);
            }
            // 写入文件结束标记
            raf.write(DBFConstant.END_OF_DATA);
            // 更新文件头部信息
            updateHeader(header, raf);
        });
    }
}