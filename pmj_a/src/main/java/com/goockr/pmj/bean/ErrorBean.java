package com.goockr.pmj.bean;

import java.io.Serializable;

/**
 * @author yofreg
 * @time 2018/5/31 14:37
 * @desc 一般数据
 */
public class ErrorBean implements Serializable {

    private int ZTM; //状态码 1:通讯ok 0:一般返回 -1:机器故障
    private int P; //记录是哪个喷头
    private String data; //返回数据
    private int W; //记录错误位置

    @Override
    public String toString() {
        return "ErrorBean{" +
                "ZTM=" + ZTM +
                ", P=" + P +
                ", data='" + data + '\'' +
                ", W=" + W +
                '}';
    }

    public int getZTM() {
        return ZTM;
    }

    public void setZTM(int ZTM) {
        this.ZTM = ZTM;
    }

    public int getP() {
        return P;
    }

    public void setP(int p) {
        P = p;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getW() {
        return W;
    }

    public void setW(int w) {
        W = w;
    }
}
