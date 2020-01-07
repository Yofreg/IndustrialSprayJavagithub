package com.goockr.pmj.bean;

import java.util.Arrays;

/**
 * @author yofreg
 * @time 2018/6/7 17:49
 * @desc 用于存储传输的图片的缓存
 */
public class BitmapAndCSBean {

    //private Bitmap mBitmap; //bitmap图片
    //private byte[] datas;
    private int CS; //次数
    //private int bmph;
    //private ArrayList<byte[]> datas2;
    //private byte[] byteh;
    //private byte[] bytech;

    private byte[] datas; //点阵数据
    private int TPXH;

    @Override
    public String toString() {
        return "BitmapAndCSBean{" +
                "CS=" + CS +
                ", datas=" + Arrays.toString(datas) +
                ", TPXH=" + TPXH +
                '}';
    }

    public int getTPXH() {
        return TPXH;
    }

    public void setTPXH(int TPXH) {
        this.TPXH = TPXH;
    }

    public byte[] getDatas() {
        return datas;
    }

    /*public ArrayList<byte[]> getDatas2() {
        return datas2;
    }*/

    /*public void setDatas2(ArrayList<byte[]> datas2) {
        this.datas2 = datas2;
    }*/

    public void setDatas(byte[] datas) {
        this.datas = datas;
    }

    /*public Bitmap getBitmap() {
        return mBitmap;
    }*/

    /*public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }*/

    public int getCS() {
        return CS;
    }

    public void setCS(int CS) {
        this.CS = CS;
    }

    /*public int getBmph() {
        return bmph;
    }

    public void setBmph(int bmph) {
        this.bmph = bmph;
    }

    public byte[] getByteh() {
        return byteh;
    }

    public void setByteh(byte[] byteh) {
        this.byteh = byteh;
    }

    public byte[] getBytech() {
        return bytech;
    }

    public void setBytech(byte[] bytech) {
        this.bytech = bytech;
    }*/
}
