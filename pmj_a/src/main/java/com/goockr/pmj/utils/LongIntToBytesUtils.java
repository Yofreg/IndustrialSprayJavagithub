package com.goockr.pmj.utils;

/**
 * @author yofreg
 * @time 2018/5/17 16:27
 * @desc long int 转为byte[]小端模式
 *
 */
public class LongIntToBytesUtils {

    //通用int类型转小端
    public static byte[] intToBytesXDTY(int data, int x) {
        byte[] b = new byte[x];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (data & 0xff);
            if (i != b.length -1) {
                data = data >> 8;
            }
        }
        return b;
    }

    //通用long类型转小端
    public static byte[] longToBytesXDTY(long data, int x) {
        byte[] b = new byte[x];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (data & 0xff);
            if (i != b.length -1) {
                data = data >> 8;
            }
        }
        return b;
    }

    //byte小端转int数值
    public static int byteToIntXD(byte[] datas) {

        int a = 0;

        for (int i = datas.length - 1; i >= 0; i--) {
            a = datas[i] & 0xff;
            a = a << 8;
        }

        return a;
    }

    //byte小端转int数据 2位
    public static int bytesToInt(byte[] src) {
        int value;
        value = (src[0] & 0xFF) | ((src[1] & 0xFF) << 8);
        return value;
    }

    public static int bytesToInt0(byte[] src) {
        int value;
        if (src.length > 2) {
            value = (src[1] & 0xFF) | ((src[2] & 0xFF) << 8);
            return value;
        } else {
            return 0;
        }
    }

    //byte小端转int数据 4位
    public static int bytesToInt2(byte[] src) {
        int value;
        value = (src[0] & 0xFF) | ((src[1] & 0xFF) << 8) | ((src[2] & 0xff) << 16) | ((src[3] & 0xff) << 24);
        return value;
    }

    //byte小端转int数据 4位
    public static int bytesToInt3(byte[] src) {
        int value;
        value = (src[1] & 0xFF) | ((src[2] & 0xFF) << 8) | ((src[3] & 0xff) << 16) | ((src[4] & 0xff) << 24);
        return value;
    }

    public static int bytesToInt4(byte[] src) {
        int value;
        value = (src[3] & 0xFF) | ((src[4] & 0xFF) << 8) | ((src[5] & 0xff) << 16) | ((src[6] & 0xff) << 24);
        return value;
    }
}
