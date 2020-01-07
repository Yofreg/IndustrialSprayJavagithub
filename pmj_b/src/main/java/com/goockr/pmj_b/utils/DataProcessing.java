package com.goockr.pmj_b.utils;

/**
 * @author yofreg
 * @time 2018/6/29 15:39
 * @desc 数据校验及转换
 */
public class DataProcessing {

    static int len = 6;
    static int buff_out[]=new int[len];

    /////数据反转译与校验处理///
    public static int[] dataCheck(Byte[] dataBuff) {

        int headValue = 254;
        int footValue = 255;

        int sum=0;//校验和
        //1.数据结构判断
        //数据格式错误，直接返回

        if((dataBuff[0] & 255) != headValue && (dataBuff[len-1] & 255) != footValue) {
            return new int[]{0};
        }

        //2.2数据校验
        //计算校验和
        for (int i = 1; i < len-2 ; ++i) {
            sum = sum + (dataBuff[i] & 255);
            buff_out[i] = dataBuff[i] & 255;
        }
        //联合运算
        int dataL = dataBuff[len - 2] & 255;
        //和校验
        if(dataL != sum)
            return new int[]{0};
        buff_out[0] = headValue;
        buff_out[len-2] = sum;
        buff_out[len-1] = footValue;
        return buff_out;
    }
}


