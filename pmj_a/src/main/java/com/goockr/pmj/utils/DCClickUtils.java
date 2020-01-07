package com.goockr.pmj.utils;

/**
 * @author yofreg
 * @time 2018/6/8 16:18
 * @desc 多重点击事件
 */
public class DCClickUtils {

    static long[] himt = new long[3];

    //3重点击事件
    public static boolean djClick() {

        System.arraycopy(himt, 1, himt, 0, himt.length - 1);
        himt[himt.length - 1] = System.currentTimeMillis();
        return himt[0] - himt[himt.length - 1] < -500;
    }


}
