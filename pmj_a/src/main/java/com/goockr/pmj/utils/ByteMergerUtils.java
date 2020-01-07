package com.goockr.pmj.utils;

/**
 * @author yofreg
 * @time 2018/5/16 9:35
 * @desc 用于合并多个byte数组
 */
public class ByteMergerUtils {

    public static byte[] byteMerger(byte[]... byte0) {
        int len = 0;
        for (byte[] aByte0 : byte0) {
            len = len + aByte0.length;
        }
        byte[] byte_3 = new byte[len];
        for (int i = 0; i < byte0.length; i++) {
            System.arraycopy(byte0[i], 0, byte_3, changdu(i, byte0), byte0[i].length);
        }
        return byte_3;
    }

    private static int changdu( int i, byte[]... byte0) {
        int j = 0;
        for (int k = 0; k < i; k++) {
            j = j + byte0[k].length;
        }
        return  j;
    }
}
