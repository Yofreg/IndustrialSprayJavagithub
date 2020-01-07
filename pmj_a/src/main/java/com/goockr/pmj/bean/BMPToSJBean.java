package com.goockr.pmj.bean;

import java.util.Arrays;

/**
 * @author yofreg
 * @time 2018/5/21 10:41
 * @desc 存储bmp位图数据及宽高
 */
public class BMPToSJBean {

    private boolean isBMP; //判断是否为bmp数据
    private int width; //图片宽像素
    private int height; //图片高像素
    private byte[] data; //位图数据

    @Override
    public String toString() {
        return "BMPToSJBean{" +
                "isBMP=" + isBMP +
                ", width=" + width +
                ", height=" + height +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    public boolean isBMP() {
        return isBMP;
    }

    public void setBMP(boolean BMP) {
        isBMP = BMP;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
