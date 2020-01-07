package com.goockr.pmj.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yofreg
 * @time 2018/5/25 17:19
 * @desc 将一个Bytes切割成多个Bytes供使用
 */
public class BytesToListBytesUtils {

    /**
     *  根据总数据和组数做分割
     * @param bytes 总数据
     * @param w 喷头数
     * @return
     */
    public static List<byte[]> bytesToList(byte[] bytes, int w, int h) {

        List<byte[]> list = new ArrayList<>();

        if (w == 1) {
            list.add(bytes);
        } else {
            int s = (bytes.length / h) % 6; //第一段的宽
            if (s == 0) {
                for (int i = 0; i < w; i++) {
                    byte[] data = new byte[6 * h];
                    for (int j = 0; j < 6 * h; j++) {
                        int ss = j / 6;
                        int sss = j % 6;

                        data[j] = bytes[ss * (bytes.length / h) + i * 6 + sss];
                    }
                    list.add(data);
                }
            } else {
                for (int i = 0; i < w; i++) {
                    byte[] data;
                    if (i == 0) {
                        data = new byte[s * h];
                        for (int j = 0; j < s * h; j++) {
                            int ss = j / s; //第几行
                            data[j] = bytes[j + ss * 6 * (w - 1)];
                        }
                    } else {
                        data = new byte[6 * h];
                        for (int j = 0; j < 6 * h; j++) {
                            int ss = j / 6;
                            int sss = j % 6;
                            //data[j] = bytes[j + s * (ss + 1) + (i - 1) * 6 + ss * 6 * (w - 1)];
                            data[j] = bytes[sss + ss * (bytes.length / h) + s + (i - 1) * 6];

                        }
                    }
                    list.add(data);
                }
            }

        }

        return list;
    }
}
