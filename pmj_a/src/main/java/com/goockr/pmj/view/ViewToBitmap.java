package com.goockr.pmj.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.goockr.pmj.bean.CJCopeBean;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yofreg
 * @time 2018/5/25 13:58
 * @desc 将控件变为bitmap并旋转成新的
 */
public class ViewToBitmap {

    //把布局变成Bitmap 在旋转90度成新的bitmap
    public static Bitmap getViewBitmap(View addViewContent) {

        //将view变为bitmap
        addViewContent.setDrawingCacheEnabled(true);

        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        //Log.v("xxxxxf", "cacheBitmap = " + cacheBitmap);

        //得到旋转的bitmap
        Matrix matrix = new Matrix(); //旋转图片 动作
        matrix.setRotate(-90); //旋转角度
        //matrix.postScale(-1, 1); //水平翻转 1, -1是垂直翻转
        int width = cacheBitmap.getWidth();
        int height = cacheBitmap.getHeight(); // 创建新的图片

        return Bitmap.createBitmap(cacheBitmap, 0, 0, width, height, matrix, true);
    }

    public static void saveBitmapFile(Bitmap bitmap){
        File file=new File("lala");//将要保存图片的路径

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片裁剪的方法
     *
     * @param bitmap 原图
     * @return 裁剪后的图像
     */
    public static Bitmap cropBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int cropWidth = w >= h ? h : w;// 裁切后所取的正方形区域边长
        cropWidth /= 2;
        int cropHeight = (int) (cropWidth / 1.2);
        return Bitmap.createBitmap(bitmap, w / 3, 0, cropWidth, cropHeight, null, false);
    }

    /**
     *  图片逆向旋转90的方法
     * @param bitmap 原图
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, boolean f, boolean d) {
        Matrix matrix = new Matrix(); //旋转图片 动作
        matrix.setRotate(-90); //旋转角度
        if (f) {
            matrix.postScale(-1, 1); //水平翻转
        }
        if (d) {
            matrix.postScale(1, -1); //垂直颠倒
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight(); // 创建新的图片

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     *  图片先逆向旋转90,根据需求做切割翻转,在合并
     * @param bitmap 原图
     * @param list 要剪切的片段
     * @param x 几喷头
     * @return
     */
    public static Bitmap rotateBitmap2(Bitmap bitmap, List<CJCopeBean> list, int x) {
        Matrix matrix = new Matrix(); //旋转图片 动作
        matrix.setRotate(-90); //旋转角度
        int width = bitmap.getWidth();
        int height = bitmap.getHeight(); // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        List<Bitmap> mbBitmapList = new ArrayList<>();
        for (int i = 0; i < (resizedBitmap.getWidth() / 48); i++) {

            Bitmap bitmap1 = Bitmap.createBitmap(resizedBitmap, 48 * i, 0, 48, resizedBitmap.getHeight(), null, false);
            if (x > i && list.size() > i) {
                Matrix matrix2 = new Matrix();
                if (list.get(i).isSP()) {
                    matrix2.postScale(1, -1); //垂直颠倒 实际打印时以横做竖大,垂直颠倒跟水平翻转需要相反着写
                }
                if (list.get(i).isCZ()) {
                    matrix2.postScale(-1, 1); //水平翻转
                }
                int width2 = bitmap1.getWidth();
                int height2 = bitmap1.getHeight(); // 创建新的图片
                Bitmap resizedBitmap2 = Bitmap.createBitmap(bitmap1, 0, 0, width2, height2, matrix2, true);
                mbBitmapList.add(resizedBitmap2);
            } else {
                mbBitmapList.add(bitmap1);
            }
        }

        if (resizedBitmap.getWidth() % 48 != 0) {
            Bitmap bitmap1 = Bitmap.createBitmap(resizedBitmap, (resizedBitmap.getWidth() / 48) * 48, 0, 48, resizedBitmap.getHeight(), null, false);
            if (x > resizedBitmap.getWidth() / 48 && list.size() > resizedBitmap.getWidth() / 48) {
                Matrix matrix2 = new Matrix();
                if (list.get(resizedBitmap.getWidth() / 48).isSP()) {
                    matrix2.postScale(1, -1); //垂直颠倒
                }
                if (list.get(resizedBitmap.getWidth() / 48).isCZ()) {
                    matrix2.postScale(-1, 1); //水平翻转
                }
                int width2 = bitmap1.getWidth();
                int height2 = bitmap1.getHeight(); // 创建新的图片
                Bitmap resizedBitmap2 = Bitmap.createBitmap(bitmap1, 0, 0, width2, height2, matrix2, true);
                mbBitmapList.add(resizedBitmap2);
            } else {
                mbBitmapList.add(bitmap1);
            }
        }
        return mergeBitmap(mbBitmapList);
    }

    private static Bitmap mergeBitmap(List<Bitmap> list) {
        int w = 0;
        for (int i = 0; i < list.size(); i++) {
            w = w + list.get(i).getWidth();
        }

        Bitmap bitmap = Bitmap.createBitmap(w, list.get(0).getHeight(), list.get(0).getConfig());
        Canvas canvas = new Canvas(bitmap);
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                canvas.drawBitmap(list.get(0), new Matrix(), null);
            } else {
                canvas.drawBitmap(list.get(i), bitmapCD(list, i), 0, null);
            }
        }
        return bitmap;
    }

    private static int bitmapCD(List<Bitmap> list, int x) {

        int w = 0;
        for (int i = 0; i < x; i++) {
            w = w + list.get(i).getWidth();
        }
        return w;
    }

    /***
     * 图片去色,返回灰度图片
     * @param bmpOriginal 传入的图片
     * @return 去色后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {

        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public static Bitmap convertGreyImgByFloyd(Bitmap img) {

        int width = img.getWidth();         //获取位图的宽
        int height = img.getHeight();       //获取位图的高

        int[] pixels = new int[width * height]; //通过位图的大小创建像素点数组

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int[] gray = new int[height * width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];
                int red = ((grey  & 0x00FF0000 ) >> 16);
                gray[width * i + j] = red;
            }
        }

        int e = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int g = gray[width * i + j];
                if (g >= 128) {
                    pixels[width * i + j] = 0xffffffff;
                    e = g - 255;

                } else {
                    pixels[width * i + j]=0xff000000;
                    e = g;
                }
                if (j < width - 1 && i < height-1) {
                    //右边像素处理
                    gray[width * i+j+1]+=3*e/8;
                    //下
                    gray[width*(i+1)+j]+=3*e/8;
                    //右下
                    gray[width*(i+1)+j+1]+=e/4;
                }else if (j==width-1&&i<height-1) {//靠右或靠下边的像素的情况
                    //下方像素处理
                    gray[width*(i+1)+j]+=3*e/8;
                }else if (j<width-1&&i==height-1) {
                    //右边像素处理
                    gray[width*(i)+j+1]+=e/4;
                }
            }
        }
        Bitmap mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return mBitmap;
    }

}
