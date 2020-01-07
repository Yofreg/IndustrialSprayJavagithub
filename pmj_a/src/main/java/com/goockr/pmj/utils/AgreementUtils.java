package com.goockr.pmj.utils;

import com.goockr.pmj.cons.PMJBasicCons;

import java.io.IOException;

/**
 * @author yofreg
 * @time 2018/5/30 14:36
 * @desc 协议数据合成
 */
public class AgreementUtils {

    /**
     *  合成最后发送的协议报文
     * @param type 发送类型
     * @param datas 数据码
     * @return 协议报文
     * @throws IOException
     */
    public static byte[] hecheng(byte type, byte[] datas) throws IOException {

        byte[] a = PMJBasicCons.TCP_TBT.getBytes("utf-8"); //请求头
        byte[] payload = new byte[1 + 4 + datas.length]; //类型码+长度码+数据码
        payload[0] = type;
        long len_tmp = datas.length;
        payload[1] = (byte)(len_tmp & 0xff); len_tmp = len_tmp >> 8;
        payload[2] = (byte)(len_tmp & 0xff); len_tmp = len_tmp >> 8;
        payload[3] = (byte)(len_tmp & 0xff); len_tmp = len_tmp >> 8;
        payload[4] = (byte)(len_tmp & 0xff);
        System.arraycopy(datas, 0, payload, 5, datas.length);
        long jiaoyan = CRC32Utils.byteCRC32(payload); //校验码
        byte[] b = new byte[4];
        b[0] = (byte) (jiaoyan & 0xff); jiaoyan = jiaoyan >> 8;
        b[1] = (byte) (jiaoyan & 0xff); jiaoyan = jiaoyan >> 8;
        b[2] = (byte) (jiaoyan & 0xff); jiaoyan = jiaoyan >> 8;
        b[3] = (byte) (jiaoyan & 0xff);
        return ByteMergerUtils.byteMerger(a, b, payload);

    }
}
