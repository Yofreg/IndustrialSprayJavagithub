package com.goockr.pmj.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author yofreg
 * @time 2018/5/16 14:05
 * @desc 用于存储设备发送的协议报文
 */
public class PMJstorageBean implements Serializable {

    private int P; //记录是哪个喷头
    private byte[] lXM; //类型码
    private byte[] sJM; //数据码

    @Override
    public String toString() {
        return "PMJstorageBean{" +
                "P=" + P +
                ", lXM=" + Arrays.toString(lXM) +
                ", sJM=" + Arrays.toString(sJM) +
                '}';
    }

    public int getP() {
        return P;
    }

    public void setP(int p) {
        P = p;
    }

    public byte[] getlXM() {
        return lXM;
    }

    public void setlXM(byte[] lXM) {
        this.lXM = lXM;
    }

    public byte[] getsJM() {
        return sJM;
    }

    public void setsJM(byte[] sJM) {
        this.sJM = sJM;
    }
}
