package com.goockr.industrialsprayjava.tools;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 保存为单色位图，即使黑白位图
 */
public class BmpUtil {


    /**
     * 保存为单色bmp格式的完整字节数
     **/
    public static byte[] changeToMonochromeBitmap(Bitmap bmp) {
        int[] binarys = gray2Binary(bmp);
        byte[] data = compressMonoBitmap(bmp, binarys);
        byte[] header = addBMPImageHeader(data.length + 62);
        byte[] infos = addBMPImageInfosHeader(bmp.getWidth(), bmp.getHeight());
        byte[] buffer = new byte[62 + data.length];
        System.arraycopy(header, 0, buffer, 0, header.length);
        System.arraycopy(infos, 0, buffer, 14, infos.length);
        System.arraycopy(data, 0, buffer, 62, data.length);
        return buffer;
    }

    /**
     * 保存为单色bmp数据，不包含头，正向头
     **/
    public static byte[] changeSingleBytes(Bitmap bmp) {

        Matrix matrix = new Matrix(); //旋转图片 动作
        matrix.setRotate(-90); //旋转角度
        int width = bmp.getWidth();
        int height = bmp.getHeight(); // 创建新的图片

        Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);

        int[] binarys = gray2Binary(bitmap);
        byte[] data = compressMonoBitmap_ps(bitmap, binarys);
        return data;
    }

    /**
     * 将彩色图转换为灰阶图，并二值化处理
     *
     * @param bmp 位图
     * @return 返回灰阶图二值化后的颜色int[]
     */
    private static int[] gray2Binary(Bitmap bmp) {
        int width = bmp.getWidth();   // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        int[] pixels = new int[width * height];  // 通过位图的大小创建像素点数组,
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);   // int 0 代表0XFFFFFFFF,即是1.0完全不透明，0.0f完全透明。黑色完全透明。
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];  // 第几行，第几个
                // 分离三原色
                int alpha = ((grey & 0xFF000000) >> 24); // 透明度
                int red = ((grey & 0x00FF0000) >> 16);   // 红色
                int green = ((grey & 0x0000FF00) >> 8);  // 绿色
                int blue = (grey & 0x000000FF);          // 蓝色
                if (alpha == 0) {  // 透明度为0，则说明没有颜色，那变更为白色
                    pixels[width * i + j] = 0;           // 白色是0
                    continue;
                }
                /*if (red > 127) {
                    red = 255;
                }
                if (blue > 127) {
                    blue = 0;
                }*/
                grey = (int) (red * 0.299 + green * 0.587 + blue * 0.114);  // 转化为灰度图  灰度值：255为白色，0为黑色
                // TODO: 2016/12/27 灰度值为200，可调整该参数
                grey = grey < 200 ? 1 : 0;  // 灰度小于200就转化为黑色，不然就为白色。200为可调整参数。// 二值化
                pixels[width * i + j] = grey;
            }
        }
        return pixels;
    }


    /**
     * 压缩为完整单色bmp数组，并反向
     *
     * @param bmp     压缩需要用到位图的宽度，高度。
     * @param binarys 二值化数据
     * @return
     */
    private static byte[] compressMonoBitmap(Bitmap bmp, int[] binarys) {
        int width = bmp.getWidth();   // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        // 行补位的公式为 widthBytes = (width*biBitCount+31)/32*4
        // 需要转化为单色，所以biBitCount=1；
        // 确定一行几个字节
        int widthBytes = (width + 31) / 32 * 4;

        byte[] newss = new byte[widthBytes * height];
        for (int i = height; i > 0; i--) {
            for (int j = 0; j < width; j++) {
                if (binarys[width * (i - 1) + j] > 0)
                    newss[(height - i) * widthBytes + j / 8] |= (byte) (1 << (7 - j % 8));
            }
        }
        return newss;
        // 方法二:
        /*  // 确定需要一行要补足的位数
        int wei = widthBytes * 8 - width;
        int[] newbs = new int[widthBytes * 8 * height];  // 总字节数
        // 白色是要插入0，并完成翻转;  bmp保存格式是从下到上，从左到右
        for (int i = 0; i < height; i++) {
            System.arraycopy(binarys, i * width, newbs, (height - i - 1) * (width + wei), width);
        }
        // 压缩
        byte[] data = new byte[newbs.length / 8];
        for (int i = 0; i < newbs.length; i++) {
            if (newbs[i] > 0) {
                data[i / 8] |= (1 << (7 - i % 8));
            }
        }
        return data;*/
    }

    /**
     * 压缩为完整单色bmp数组，并正向
     *
     * @param bmp     压缩需要用到位图的宽度，高度。
     * @param binarys 二值化数据
     * @return
     */
    private static byte[] compressMonoBitmap_ps(Bitmap bmp, int[] binarys) {
        int width = bmp.getWidth();   // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        // 行补位的公式为 widthBytes = (width*biBitCount+31)/32*4
        // 需要转化为单色，所以biBitCount=1；
        // 确定一行几个字节
        int widthBytes = (width + 31) / 32 * 4;

        byte[] newss = new byte[widthBytes * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (binarys[width * i + j] > 0)  // 第几行第几个字节
                    newss[i * widthBytes + j / 8] |= (byte) (1 << (7 - j % 8));  // 新压缩的第几行第几个
            }
        }
        return newss;
        // 方法二:
        /*  // 确定需要一行要补足的位数
        int wei = widthBytes * 8 - width;
        int[] newbs = new int[widthBytes * 8 * height];  // 总字节数
        // 白色是要插入0，并完成翻转;  bmp保存格式是从下到上，从左到右
        for (int i = 0; i < height; i++) {
            System.arraycopy(binarys, i * width, newbs, (height - i - 1) * (width + wei), width);
        }
        // 压缩
        byte[] data = new byte[newbs.length / 8];
        for (int i = 0; i < newbs.length; i++) {
            if (newbs[i] > 0) {
                data[i / 8] |= (1 << (7 - i % 8));
            }
        }
        return data;*/
    }

    /**
     * BMP文件头
     *
     * @param size 整个文件的大小，包括文件头，信息头，和位图内容
     * @return
     */
    private static byte[] addBMPImageHeader(int size) {
        byte[] buffer = new byte[14];
        //magic number 'BM'
        buffer[0] = 0x42;
        buffer[1] = 0x4D;
        //记录大小
        buffer[2] = (byte) (size);
        buffer[3] = (byte) (size >> 8);
        buffer[4] = (byte) (size >> 16);
        buffer[5] = (byte) (size >> 24);
        buffer[6] = 0x00;
        buffer[7] = 0x00;
        buffer[8] = 0x00;
        buffer[9] = 0x00;
        buffer[10] = 0x3E;
        buffer[11] = 0x00;
        buffer[12] = 0x00;
        buffer[13] = 0x00;
        return buffer;
    }


    /**
     * BMP文件信息头
     *
     * @param w 宽，单位像素
     * @param h 高，单位像素
     * @return
     */
    private static byte[] addBMPImageInfosHeader(int w, int h) {
        byte[] buffer = new byte[48];
        //这个是固定的 BMP 信息头要40个字节
        buffer[0] = 0x28;
        buffer[1] = 0x00;
        buffer[2] = 0x00;
        buffer[3] = 0x00;
        //宽度 地位放在序号前的位置 高位放在序号后的位置
        buffer[4] = (byte) (w);
        buffer[5] = (byte) (w >> 8);
        buffer[6] = (byte) (w >> 16);
        buffer[7] = (byte) (w >> 24);
        //长度 同上
        buffer[8] = (byte) (h);
        buffer[9] = (byte) (h >> 8);
        buffer[10] = (byte) (h >> 16);
        buffer[11] = (byte) (h >> 24);
        //总是被设置为1
        buffer[12] = 0x01;
        buffer[13] = 0x00;
        //比特数 像素 32位保存一个比特 这个不同的方式(ARGB 32位 RGB24位不同的!!!!)
        //黑白图置1
        buffer[14] = 0x01;
        buffer[15] = 0x00;
        //0-不压缩 1-8bit位图
        //2-4bit位图 3-16/32位图
        //4 jpeg 5 png
        //设置为不压缩
        buffer[16] = 0x00;
        buffer[17] = 0x00;
        buffer[18] = 0x00;
        buffer[19] = 0x00;
        //说明图像大小
        buffer[20] = (byte) (((w * 1 + 31) / 32 * 4 * h) & 0xff);
        buffer[21] = (byte) (((w * 1 + 31) / 32 * 4 * h) >> 8 & 0xff);
        buffer[22] = (byte) (((w * 1 + 31) / 32 * 4 * h) >> 16 & 0xff);
        buffer[23] = (byte) (((w * 1 + 31) / 32 * 4 * h) >> 24 & 0xff);
        //水平分辨率
        buffer[24] = 0x00;
        buffer[25] = 0x00;
        buffer[26] = 0x00;
        buffer[27] = 0x00;
        //垂直分辨率
        buffer[28] = 0x00;
        buffer[29] = 0x00;
        buffer[30] = 0x00;
        buffer[31] = 0x00;
        //0 使用所有的调色板项
        buffer[32] = 0x00;
        buffer[33] = 0x00;
        buffer[34] = 0x00;
        buffer[35] = 0x00;
        //开颜色索引
        buffer[36] = 0x00;
        buffer[37] = 0x00;
        buffer[38] = 0x00;
        buffer[39] = 0x00;
        // 加上颜色表
        // 00 00 00 00 ff ff ff 00 那么 0代表黑,1代表白
        // 若为
        // ff ff ff 00 00 00 00 00 那么 1代表黑,0代表白  --- 选择这个
        buffer[40] = (byte) 0xFF;
        buffer[41] = (byte) 0xFF;
        buffer[42] = (byte) 0xFF;
        buffer[43] = (byte) 0x00;

        buffer[44] = (byte) 0x00;
        buffer[45] = (byte) 0x00;
        buffer[46] = (byte) 0x00;
        buffer[47] = (byte) 0xFF;
        return buffer;
    }
}
