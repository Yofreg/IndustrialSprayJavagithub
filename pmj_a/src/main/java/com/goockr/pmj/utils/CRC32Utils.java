package com.goockr.pmj.utils;

import java.util.zip.CRC32;

/**
 * @author yofreg
 * @time 2018/5/16 10:12
 * @desc 用于做crc32校验
 */
public class CRC32Utils {

    /**
     *  byte[]做crc32转换成long
     * @param b
     * @return
     */
    public static long byteCRC32(byte[] b) {
        CRC32 crc32 = new CRC32();
        crc32.update(b);
        return crc32.getValue();
    }

    public static long stringCRC32(String data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data.getBytes());
        return crc32.getValue();
    }

    /**
     *  crc32校验转16进制string
     * @param data
     * @return
     */
    public static String String16CRC32(String data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data.getBytes());
        return Long.toHexString(crc32.getValue());
    }
}
