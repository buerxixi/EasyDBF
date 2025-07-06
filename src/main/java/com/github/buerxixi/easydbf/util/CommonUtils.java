package com.github.buerxixi.easydbf.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 通用工具类
 */
public class CommonUtils {

    /**
     * 获取共享锁
     *
     * @param filename 文件名
     * @param callback 回调raf
     * @throws IOException 异常
     */
    static public void sharedLock(String filename, Callback<RandomAccessFile> callback) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            // 获取共享锁（允许其他进程读，阻止写操作）
            FileChannel channel = raf.getChannel();
            FileLock sharedLock = null;
            try {
                sharedLock = channel.lock(0, Long.MAX_VALUE, false);
                callback.call(raf);
            } finally {
                if (sharedLock != null) {
                    sharedLock.release();
                }
            }

        }

    }
}
