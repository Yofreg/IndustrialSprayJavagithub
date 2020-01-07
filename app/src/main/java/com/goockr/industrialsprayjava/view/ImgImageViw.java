package com.goockr.industrialsprayjava.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

//图片
public class ImgImageViw extends android.support.v7.widget.AppCompatImageView {

    private String imgNo;
    private String imgScale;
    private String imgDirection;
    private String path;

    public String getPath() {
        return path;
    }

    public String getImgNo() {
        return imgNo;
    }

    public void setImgNo(String imgNo,String p) {
        this.imgNo = imgNo;
        path = p;
    }
    public String getImgScale() {
        return imgScale;
    }

    public void setImgScale(String imgScale) {
        this.imgScale = imgScale;
    }

    public String getImgDirection() {
        return imgDirection;
    }

    public void setImgDirection(String imgDirection) {
        this.imgDirection = imgDirection;
    }

    public ImgImageViw(Context context) {
        super(context);
    }

    public ImgImageViw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImgImageViw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void upDate(){
        requestLayout();
       // invalidate();
    }

}
