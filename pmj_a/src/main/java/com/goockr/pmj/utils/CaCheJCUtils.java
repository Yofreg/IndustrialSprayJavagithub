package com.goockr.pmj.utils;

import java.util.List;

/**
 * @author yofreg
 * @time 2018/6/15 10:39
 * @desc 检测图片缓存
 */
public class CaCheJCUtils {

    //缓存不足
    public static boolean cacheS(int x, List...lists) {
        boolean a = false;
        for (int i = 0; i < x; i++) {
            if (lists[i].size() < 10) {
                a = true;
            }
        }
        return a;
    }

    //缓存过多
    public static boolean cacheB(int x, List...lists) {
        boolean a = true;
        for (int i = 0; i < x; i++) {
            if (lists[i].size() < 100) {
                a = false;
            }
        }
        return a;
    }

    //无缓存
    public static boolean cacheN(int x, List...lists) {

        boolean a = false;
        for (int i = 0; i < x; i++) {
            if (lists[i].size() <= 1) {
                a = true;
            }
        }
        return a;
    }
}
