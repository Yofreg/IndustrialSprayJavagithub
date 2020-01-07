package com.goockr.pmj.utils;

import com.goockr.pmj.bean.BMPToSJBean;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yofreg
 * @time 2018/5/18 19:11
 * @desc 根据bmp流或者bmp单色数据得到位图数据
 */
public class BmpToDZUtils {

    //将byte转成int数据
    private static int charsToInt(byte[] cs) {
        int r = 0;
        for (int i = cs.length - 1; i >= 0; i--)
        //for (int i = 0;i<cs.Length; i++)
        {
            r |= (cs[i] & 0x00ff);
            if (i != 0)
                r = r << 8;
        }
        return r;
    }

    private static String charsToIntString(byte[] cs) {
        int r = charsToInt(cs);
        return String.valueOf(r);
    }

    private static String charsToString(byte[] cs) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < cs.length; i++)
            s.append(cs[i]);
        return s.toString();
    }
    private static void append(String tip, String value) {
        //textBox1.Text += tip+ "\t\t\t\t" + value+"\r\n";
        //MLog.v(tip+ "\t\t\t\t" + value+"\r\n");

    }

    String charsToAr(byte[] cs) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < cs.length; i++) {
            sb.append(String.valueOf(cs[i])).append(",");
            if ((i + 1) % 16 == 0)
                sb.append("\r\n");
        }
        return sb.toString();
    }

    //由流得到点阵数据
    public static BMPToSJBean button1_Click(InputStream fs) {

        BMPToSJBean bean = new BMPToSJBean();

        try {
            byte[] tmp;

            // BM
            byte[] bfType = new byte[2];
            tmp = bfType;
            fs.read(tmp, 0, tmp.length);
            append("BM",charsToString(tmp));

            String aa = new String(tmp);
            //Log.v("xxxxxf", "类型" + aa);
            if (!aa.equals("BM")) {
                bean.setBMP(false);
                return bean;
            }

        // BMP图像文件的大小
        byte[] bfSize = new byte[4];
        tmp = bfSize;
        fs.read(tmp, 0, tmp.length);
        append("文件大小", charsToIntString(tmp));

        // 恒为零
        byte[] bfReserved1 = new byte[2];
        tmp = bfReserved1;
        fs.read(tmp, 0, tmp.length);

        // 恒为零
        byte[] bfReserved2 = new byte[2];
        tmp = bfReserved2;
        fs.read(tmp, 0, tmp.length);

        // BMP图像数据的地址
        byte[] bfOffBits = new byte[4];
        tmp = bfOffBits;
        fs.read(tmp, 0, tmp.length);
        append("图像数据的地址", charsToIntString(tmp));

        // biSize,在Windows中，此字段的值总为28h字节=40字节
        byte[] biSize = new byte[4];
        tmp = biSize;
        fs.read(tmp, 0, tmp.length);
        append("biSize此字段的值总为28h字节", charsToIntString(tmp));

        // BMP图像的宽度，单位像素
        byte[] biWidth = new byte[4];
        tmp = biWidth;
        fs.read(tmp, 0, tmp.length);
        append("图像的宽度", charsToIntString(tmp));

        // BMP图像的高度，单位像素
        byte[] biHeight = new byte[4];
        tmp = biHeight;
        fs.read(tmp, 0, tmp.length);
        append("图像的高度", charsToIntString(tmp));

        // 总为1
        byte[] biPlanes = new byte[2];
        tmp = biPlanes;
        fs.read(tmp, 0, tmp.length);

        // BMP图像的色深，即一个像素用多少位表示，常见有1、4、8、16、24和32，分别对应单色、16色、256色、16位高彩色、24位真彩色和32位增强型真彩色
        byte[] biBitCount = new byte[2];
        tmp = biBitCount;
        fs.read(tmp, 0, tmp.length);
        append("图像的色深", charsToIntString(tmp));

        // 压缩方式，0表示不压缩，1表示RLE8压缩，2表示RLE4压缩，3表示每个像素值由指定的掩码决定
        byte[] biCompression = new byte[4];
        tmp = biCompression;
        fs.read(tmp, 0, tmp.length);
        append("压缩方式", charsToIntString(tmp));

        // BMP图像数据大小，必须是4的倍数，图像数据大小不是4的倍数时用0填充补足
        byte[] biSizeImage = new byte[4];
        tmp = biSizeImage;
        fs.read(tmp, 0, tmp.length);
        append("图像数据大小", charsToIntString(tmp));

        // 水平分辨率，单位像素/m
        byte[] biXPelsPerMeter = new byte[4];
        tmp = biXPelsPerMeter;
        fs.read(tmp, 0, tmp.length);
        append("水平分辨率", charsToIntString(tmp));

        // 垂直分辨率，单位像素/m
        byte[] biYPelsPerMeter = new byte[4];
        tmp = biYPelsPerMeter;
        fs.read(tmp, 0, tmp.length);
        append("垂直分辨率", charsToIntString(tmp));

        // BMP图像使用的颜色，0表示使用全部颜色，对于256色位图来说，此值为100h=256
        byte[] biClrUsed = new byte[4];
        tmp = biClrUsed;
        fs.read(tmp, 0, tmp.length);
        append("BMP图像使用的颜色", charsToIntString(tmp));

        // 重要的颜色数，此值为0时所有颜色都重要，对于使用调色板的BMP图像来说，当显卡不能够显示所有颜色时，此值将辅助驱动程序显示颜色
        byte[] biClrImportant = new byte[4];
        tmp = biClrImportant;
        fs.read(tmp, 0, tmp.length);
        append("重要的颜色数", charsToIntString(tmp));

            byte[] biClrrbg = new byte[8];
            tmp = biClrrbg;
            fs.read(tmp, 0, tmp.length);

        //
        int bmpoff = charsToInt(bfOffBits);
        //fs.Seek(bmpoff, System.IO.SeekOrigin.Begin);
        int bmplen = charsToInt(biSizeImage);
        byte[] bmpdata = new byte[charsToInt(biSizeImage)];

        fs.read(bmpdata, 0, bmplen);
        int bmpw = charsToInt(biWidth);
        int bmph = charsToInt(biHeight);
        //a = charsToAr(bmpdata);

            for (int i = 0; i < bmpdata.length; i++) {
                if (i % 8 == 0) {
                    bmpdata[i] = (byte) 0xff;
                } else {
                    bmpdata[i] = (byte) 0x00;
                }
            }

        //从00000030,6开始读
            //Log.v("xxxxxf", "bmpw" + bmpw);
            //bmpw = (int) (Math.round((bmpw / 32.0)) * 32);

            int bmpw2 = 0;

            if (bmpw % 32 == 0) {
                bmpw2 = bmpw;
            } else {
                bmpw2 = (bmpw / 32 + 1) * 32;
            }

            //Log.v("xxxxxf", "bmpw2" + bmpw2);


            //测试用
            //bmpdata = ArraysToNewUtils.arraysToNewArrays(bmpdata, bmpw, bmpw2, bmph);

            //打印用
            bmpdata = ArraysToNewUtils.arraysToNewArrays2(bmpdata);
            bmpdata = ArraysToNewUtils.arraysToNewArrays3(bmpdata);

        //位图数据
        /*String a = "";
        int k = 0;
        for(int j = 0; j < bmph; j++) {
            for (int i = 0; i < bmpw2; i += 8) {
                byte bmpdatak = bmpdata[k];
                //bmpdatak = (byte) ~bmpdatak;
                //Log.v("xxxxxf", "bmpdatak = " + bmpdatak);
                for (int n = 0; n < 8; n++) {
                    if ((bmpdatak & 0x80) == 0)
                        a += "*";
                    else
                        a += "_";
                    bmpdatak = (byte)(bmpdatak << 1);
                    //Log.v("xxxxxf", "bmpdatak2 = " + bmpdatak);
                }
                ++k;
            }
            a += "\r\n";
        }
        Log.v("xxxxxf", "a =\n" + a);*/

        bean.setBMP(true);
        bean.setWidth(bmpw2);
        bean.setHeight(bmph);
        bean.setData(bmpdata);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bean;
    }

    //由buffer得到点阵数据
    public static BMPToSJBean button1_Click2(byte[] bytes, boolean i) {

        BMPToSJBean bean = new BMPToSJBean();
            byte[] tmp;

            // BM
            byte[] bfType = new byte[2];
            tmp = bfType;
        System.arraycopy(bytes, 0, bfType, 0, 2);
            //append("BM",charsToString(tmp));

            String aa = new String(tmp);
            //Log.v("xxxxxf", "类型" + aa);
            if (!aa.equals("BM")) {
                bean.setBMP(false);
                return bean;
            }

            // BMP图像文件的大小
            byte[] bfSize = new byte[4];
            tmp = bfSize;
        System.arraycopy(bytes, 2, bfSize, 0, 4);
            if (i) {

                append("文件大小", charsToIntString(tmp));
            }

            // 恒为零
            byte[] bfReserved1 = new byte[2];
            tmp = bfReserved1;
        System.arraycopy(bytes, 6, bfReserved1, 0, 2);

            // 恒为零
            byte[] bfReserved2 = new byte[2];
            tmp = bfReserved2;
        System.arraycopy(bytes, 8, bfReserved2, 0, 2);

            // BMP图像数据的地址
            byte[] bfOffBits = new byte[4];
            tmp = bfOffBits;
        System.arraycopy(bytes, 10, bfOffBits, 0, 4);
            //append("图像数据的地址", charsToIntString(tmp));

            // biSize,在Windows中，此字段的值总为28h字节=40字节
            byte[] biSize = new byte[4];
            tmp = biSize;
        System.arraycopy(bytes, 14, biSize, 0, 4);
            //append("biSize此字段的值总为28h字节", charsToIntString(tmp));

            // BMP图像的宽度，单位像素
            byte[] biWidth = new byte[4];
            tmp = biWidth;
        System.arraycopy(bytes, 18, biWidth, 0, 4);
            if (i) {
                append("图像的宽度", charsToIntString(tmp));
            }

            // BMP图像的高度，单位像素
            byte[] biHeight = new byte[4];
            tmp = biHeight;
        System.arraycopy(bytes, 22, biHeight, 0, 4);
            if (i) {
                append("图像的高度", charsToIntString(tmp));
            }

            // 总为1
            byte[] biPlanes = new byte[2];
            tmp = biPlanes;
        System.arraycopy(bytes, 26, biPlanes, 0, 2);

            // BMP图像的色深，即一个像素用多少位表示，常见有1、4、8、16、24和32，分别对应单色、16色、256色、16位高彩色、24位真彩色和32位增强型真彩色
            byte[] biBitCount = new byte[2];
            tmp = biBitCount;
        System.arraycopy(bytes, 28, biBitCount, 0, 2);
            //append("图像的色深", charsToIntString(tmp));

            // 压缩方式，0表示不压缩，1表示RLE8压缩，2表示RLE4压缩，3表示每个像素值由指定的掩码决定
            byte[] biCompression = new byte[4];
            tmp = biCompression;
        System.arraycopy(bytes, 30, biCompression, 0, 4);
            //append("压缩方式", charsToIntString(tmp));

            // BMP图像数据大小，必须是4的倍数，图像数据大小不是4的倍数时用0填充补足
            byte[] biSizeImage = new byte[4];
            tmp = biSizeImage;
        System.arraycopy(bytes, 34, biSizeImage, 0, 4);
            if (i) {
                append("图像数据大小", charsToIntString(tmp));
            }

            // 水平分辨率，单位像素/m
            byte[] biXPelsPerMeter = new byte[4];
            tmp = biXPelsPerMeter;
        System.arraycopy(bytes, 38, biXPelsPerMeter, 0, 4);
            //append("水平分辨率", charsToIntString(tmp));

            // 垂直分辨率，单位像素/m
            byte[] biYPelsPerMeter = new byte[4];
            tmp = biYPelsPerMeter;
        System.arraycopy(bytes, 42, biYPelsPerMeter, 0, 4);
            //append("垂直分辨率", charsToIntString(tmp));

            // BMP图像使用的颜色，0表示使用全部颜色，对于256色位图来说，此值为100h=256
            byte[] biClrUsed = new byte[4];
            tmp = biClrUsed;
        System.arraycopy(bytes, 46, biClrUsed, 0, 4);
            //append("BMP图像使用的颜色", charsToIntString(tmp));

            // 重要的颜色数，此值为0时所有颜色都重要，对于使用调色板的BMP图像来说，当显卡不能够显示所有颜色时，此值将辅助驱动程序显示颜色
            byte[] biClrImportant = new byte[4];
            tmp = biClrImportant;
        System.arraycopy(bytes, 50, biClrImportant, 0, 4);
            //append("重要的颜色数", charsToIntString(tmp));

            //颜色表
            byte[] biClrrbg = new byte[8];
            tmp = biClrrbg;
        System.arraycopy(bytes, 54, biClrrbg, 0, 8);

            //数据
            int bmplen = charsToInt(biSizeImage);
            byte[] bmpdata = new byte[charsToInt(biSizeImage)];

        System.arraycopy(bytes, 62, bmpdata, 0, bmplen);

        /*for (int k = 0; k < bmplen; k++) {


            if ((k / 192) % 2 == 1) {
                bmpdata[k] = (byte) 0xff;
            } else {
                bmpdata[k] = (byte) 0x00;
            }
        }*/

            int bmpw = charsToInt(biWidth);
            int bmph = charsToInt(biHeight);
            //a = charsToAr(bmpdata);

            //从00000030,6开始读
        /*if (i) {

            //MLog.v("bmpw" + bmpw);
        }*/
            //bmpw = (int) (Math.round((bmpw / 32.0)) * 32);

            int bmpw2 = 0;

            if (bmpw % 32 == 0) {
                bmpw2 = bmpw;
            } else {
                bmpw2 = (bmpw / 32 + 1) * 32;
            }
            /*if (i) {

                //MLog.v("bmpw2" + bmpw2);
            }*/

            //打印用
            byte[] bmpdata2 = ArraysToNewUtils.arraysToNewArrays2(bmpdata);
            //bmpdata = ArraysToNewUtils.arraysToNewArrays3(bmpdata);

            //测试用
            //byte[] bmpdata3 = ArraysToNewUtils.arraysToNewArrays(bmpdata, bmpw, bmpw2, bmph);

            //位图数据
            /*String a = "";
            int k = 0;
            for(int j = 0; j < bmph; j++) {
                for (int l = 0; l < bmpw2; l += 8) {
                    byte bmpdatak = bmpdata3[k];
                    //bmpdatak = (byte) ~bmpdatak; //反转
                    for (int n = 0; n < 8; n++) {
                        if ((bmpdatak & 0x80) == 0)
                            a += "*";
                        else
                            a += "_";
                        bmpdatak = (byte)(bmpdatak << 1);
                    }
                    ++k;
                }
                a += "\r\n";
            }
            Log.v("xxxxxf", "图案 =\n" + a);*/

            bean.setBMP(true);
            bean.setWidth(bmpw2);
            bean.setHeight(bmph);
            bean.setData(bmpdata2);

        return bean;
    }
}
