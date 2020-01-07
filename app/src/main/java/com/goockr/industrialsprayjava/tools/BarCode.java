package com.goockr.industrialsprayjava.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//二维码生成
public class BarCode {

    public static Bitmap getCodeForType(String content, int type, int size, int angle, int correction, boolean isReverse, Context context) {
        Bitmap bitmap = null;
        switch (type) {
            case 0:
                bitmap = createQRCode(content, size, correction, isReverse);
                break;
            case 1:
                bitmap = createDATAMATRIX1(content, size, correction, isReverse);
                break;
            case 2:
                bitmap = CreateEAN13(content, size, angle, correction, isReverse, context);
                break;
            case 3:
                bitmap = createCode39(content, size, angle, correction, isReverse,context);
                break;
            case 4:
                bitmap = createCode128(content, size, angle, correction, isReverse,context);
                break;
        }

        return bitmap;
    }

    private static Bitmap createQRCode(String content, int size, int correction, boolean isReverse) {
        try {
            if (content == null || "".equals(content)) {
                return null;
            }
            content = content.trim();
            // 配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.MARGIN, 0);

            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size,
                    size, hints);

            int[] pixels = new int[size * size];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (bitMatrix.get(x, y) ^ isReverse) {
                        pixels[y * size + x] = 0xff000000;
                    } else {
                        pixels[y * size + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * EAN_13字符必须在12-13   且必须是特定格式
     *
     * @param content
     * @param widthPix
     * @param angel    0 正常  1 向左  2 向下   3 向右
     * @param reversal 0  正常  1 水平 2垂直 3水平+垂直
     * @return
     */
    private static Bitmap CreateEAN13(String content, int widthPix, int angel, int correction, boolean reversal, Context context) {
        try {
            widthPix = widthPix * 2;
            int height = widthPix / 4;
            content = content.trim();
            int xx = getStandardUPCEANChecksum(content);
            if (xx != -999 && content.length() == 12) {
                content += getStandardUPCEANChecksum(content);

            }
            if (content == null || "".equals(content) || (xx == -999) || !checkStandardUPCEANChecksum(content)) {
                return null;
            }

            if (content.length() != 12 && content.length() != 13) {
                return null;
            }

            // 配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.MARGIN, 2);

            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.EAN_13, widthPix, height, hints);
            int[] pixels = new int[widthPix * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y) ^ reversal) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, height);

            Canvas canvas = new Canvas(bitmap);
            Matrix matrix = new Matrix();
            if (angel == 1) {
                matrix.postRotate(-90);
            } else if (angel == 2) {
                matrix.postRotate(180);
            } else if (angel == 3) {
                matrix.postRotate(90);
            }
//            if (reversal == 1) {
//                matrix.postScale(-1, 1);
//            } else if (reversal == 2) {
//                matrix.postScale(1, -1);
//            } else if (reversal == 3) {
//                matrix.postScale(-1, -1);
//            } else if (reversal == 4) {
//                matrix.postScale(1, 1);
//            }

            TextView textView = new TextView(context);
            textView.setText(content);
            textView.setWidth(widthPix);
            textView.setTextSize(widthPix / 30);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setDrawingCacheEnabled(true);
            textView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
            Bitmap bitmaps = Bitmap.createBitmap(textView.getDrawingCache());
            //千万别忘最后一步
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawBitmap(bitmaps, 0, bitmap.getHeight() - bitmaps.getHeight(), null);
            textView.destroyDrawingCache();

            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return newBitmap;
        } catch (WriterException | FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Bitmap createDATAMATRIX1(String content, int size, int correction, boolean isReverse) {
        try {
            if (content == null || "".equals(content)) {
                return null;
            }
            content = content.trim();
            content = new String(content.getBytes("UTF-8"), "iso-8859-1");
            // 配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            BitMatrix bitMatrix = new DataMatrixWriter().encode(content, BarcodeFormat.DATA_MATRIX, size, size, hints);

            int BLACK = 0xFF000000;
            int WHITE = 0xFFFFFFFF;

            // change the values to your needs
            int requestedWidth = size;
            int requestedHeight = size;

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();

            // calculating the scaling factor
            int pixelsize = requestedWidth / width;
            if (pixelsize > requestedHeight / height) {
                pixelsize = requestedHeight / height;
            }

            int[] pixels = new int[requestedWidth * requestedHeight];
            // All are 0, or black, by default
            for (int y = 0; y < height; y++) {
                int offset = y * requestedWidth * pixelsize;

                // scaling pixel height
                for (int pixelsizeHeight = 0; pixelsizeHeight < pixelsize; pixelsizeHeight++, offset += requestedWidth) {
                    for (int x = 0; x < width; x++) {
                        int color = (bitMatrix.get(x, y) ^ isReverse) ? BLACK : WHITE;
                        // scaling pixel width
                        for (int pixelsizeWidth = 0; pixelsizeWidth < pixelsize; pixelsizeWidth++) {
                            pixels[offset + x * pixelsize + pixelsizeWidth] = color;
                        }
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(requestedWidth, requestedHeight, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, requestedWidth, 0, 0, requestedWidth, requestedHeight);

            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //不支持汉字
    private static Bitmap createCode39(String content, int widthPix, int angle, int correction, boolean reversal, Context context) {
        widthPix = widthPix * 2;
        int height = widthPix / 4;

        try {
            if (!CheackCode39(content)) {
                return null;
            }

            if (content == null || "".equals(content)) {
                return null;
            }
            content = content.trim();
            // 配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODE_39, widthPix,
                    height, hints);
            int[] pixels = new int[widthPix * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)^reversal) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, height);

            Canvas canvas = new Canvas(bitmap);
            Matrix matrix = new Matrix();
            if (angle == 1) {
                matrix.postRotate(-90);
            } else if (angle == 2) {
                matrix.postRotate(180);
            } else if (angle == 3) {
                matrix.postRotate(90);
            }

            TextView textView = new TextView(context);
            textView.setText(content);
            textView.setWidth(widthPix);
            textView.setTextSize(widthPix / 30);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setDrawingCacheEnabled(true);
            textView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
            Bitmap bitmaps = Bitmap.createBitmap(textView.getDrawingCache());
            //千万别忘最后一步
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawBitmap(bitmaps, 0, bitmap.getHeight() - bitmaps.getHeight(), null);
            textView.destroyDrawingCache();

            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return newBitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean CheackCode39(String code39) {
        String codeval_regexp = "^[A-Z0-9\\-\\+\\. \\$\\/%]+$";
        Pattern p = Pattern.compile(codeval_regexp);
        Matcher m = p.matcher(code39);
        boolean isMatch = m.matches();
        if (isMatch) {
            return true;
        } else {
            return false;
        }
    }

    //不支持汉字
    private static Bitmap createCode128(String content, int widthPix, int angle, int correction, boolean reversal, Context context) {
        try {
            widthPix = widthPix * 2;
            int height = widthPix / 4;
            if (content == null || "".equals(content)) {
                return null;
            }
            if (!CheckCode128(content)) {
                return null;
            }
            content = content.trim();
            // 配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, widthPix,
                    height, hints);
            int[] pixels = new int[widthPix * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y <height; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)^reversal) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, height);

            Canvas canvas = new Canvas(bitmap);
            Matrix matrix = new Matrix();
            if (angle == 1) {
                matrix.postRotate(-90);
            } else if (angle == 2) {
                matrix.postRotate(180);
            } else if (angle == 3) {
                matrix.postRotate(90);
            }

            TextView textView = new TextView(context);
            textView.setText(content);
            textView.setWidth(widthPix);
            textView.setTextSize(widthPix / 30);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setDrawingCacheEnabled(true);
            textView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
            Bitmap bitmaps = Bitmap.createBitmap(textView.getDrawingCache());
            //千万别忘最后一步
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawBitmap(bitmaps, 0, bitmap.getHeight() - bitmaps.getHeight(), null);
            textView.destroyDrawingCache();

            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return newBitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean CheckCode128(String code128) {
        String codeval_regexp = "^[\\x00-\\x7f]+$";
        Pattern p = Pattern.compile(codeval_regexp);
        Matcher m = p.matcher(code128);
        boolean isMatch = m.matches();
        if (isMatch) {
            System.out.println("okok");
            return true;
        } else {
            System.out.println("nonono");

            return false;
        }
    }

    private static int getStandardUPCEANChecksum(CharSequence s) {
        int length = s.length();
        int sum = 0;
        for (int i = length - 1; i >= 0; i -= 2) {
            int digit = s.charAt(i) - '0';
            if (digit < 0 || digit > 9) {
                return -999;
            }
            sum += digit;
        }
        sum *= 3;
        for (int i = length - 2; i >= 0; i -= 2) {
            int digit = s.charAt(i) - '0';
            if (digit < 0 || digit > 9) {
                return -999;
            }
            sum += digit;
        }
        return (1000 - sum) % 10;
    }

    //检验EAN13是否合格
    private static boolean checkStandardUPCEANChecksum(CharSequence s) throws FormatException {
        int length = s.length();
        if (length == 0) {
            return false;
        }

        int sum = 0;
        for (int i = length - 2; i >= 0; i -= 2) {
            int digit = (int) s.charAt(i) - (int) '0';
            if (digit < 0 || digit > 9) {
                throw FormatException.getFormatInstance();
            }
            sum += digit;
        }
        sum *= 3;
        for (int i = length - 1; i >= 0; i -= 2) {
            int digit = (int) s.charAt(i) - (int) '0';
            if (digit < 0 || digit > 9) {
                throw FormatException.getFormatInstance();
            }
            sum += digit;
        }
        return sum % 10 == 0;
    }
}
