package com.goockr.pmj.utils;

/**
 * @author yofreg
 * @time 2018/5/22 9:50
 * @desc 将一个左下右上单色数组装成左上右下
 */
public class ArraysToNewUtils {

    /**
     * 将从左下往右往上读取修改为左上往右往下读取 测试用
     * @param bytes
     * @param weith2
     * @param height
     * @return
     */
    public static byte[] arraysToNewArrays(byte[] bytes,int weith0, int weith2, int height) {

        byte[] newBytes = new byte[bytes.length];

        int weith = weith2 / 8;
        int a = 0;
        int b = 0;

        if (height % 2 == 0) {

            for (int i = 0; i < bytes.length; i++) {
                a = i / weith;
                b = i % weith;
                newBytes[i] = bytes[weith * (height - a - 1) + b];
            }

        } else {

            for (int i = 0; i < bytes.length; i++) {
                a = i / weith;
                b = i % weith;
                newBytes[i] = bytes[weith * (height - a - 1) + b];
            }

        }
        return newBytes;
    }

    /**
     *   取倒
     * @param bytes
     * @return
     */
    public static byte[] arraysToNewArrays2(byte[] bytes) {

        byte[] newBytes = new byte[bytes.length];

        for (int i = 0; i <bytes.length; i++) {
            newBytes[i] = bytes[bytes.length - 1 - i];
        }
        return newBytes;
    }

    /**
     *  转反0变1,1变0  00000000变11111111  00110011变11001100
     * @param bytes
     * @return
     */
    public static byte[] arraysToNewArrays3(byte[] bytes) {

        byte[] newBytes = new byte[bytes.length];

        for (int i = 0; i <bytes.length; i++) {
            newBytes[i] = (byte) ~bytes[i];
        }
        return newBytes;
    }

    //先倒取
    public static byte[] aa(byte[] hh) {

        byte[] aa = new byte[hh.length];

        for (int i = 0; i < (hh.length / 6); i++) {
            aa[i * 6] = hh[i * 6 + 5];
            aa[i * 6 + 1] = hh[i * 6 + 4];
            aa[i * 6 + 2] = hh[i * 6 + 3];
            aa[i * 6 + 3] = hh[i * 6 + 2];
            aa[i * 6 + 4] = hh[i * 6 + 1];
            aa[i * 6 + 5] = hh[i * 6];
        }

        return bb(aa);
    }

    //将00000001变为10000000
    private static byte[] bb(byte[] hh) {

        byte[] aa = new byte[hh.length];

        for (int i = 0; i <hh.length; i++) {
            aa[i] = (byte) ~hh[i];
            aa[i] = (byte) (255 - aa[i]);
        }

        return aa;
    }
}
