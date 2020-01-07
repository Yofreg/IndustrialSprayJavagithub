package com.goockr.industrialsprayjava.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.goockr.industrialsprayjava.MyApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BaseUtils {
    public static String ImportPath = "/mnt/usb_storage/USB_DISK1/udisk0";
    public static String TypefaceImportPath = ImportPath + "/industrial/typeface";
    public static String ImgInfoImportPath = ImportPath + "/industrial/img";
    public static String CodeImportPath = ImportPath + "/industrial/codeimg";

    public static String TypefacePath = Environment.getExternalStorageDirectory() + "/industrial/typeface";
    public static String codePath = MyApplication.getContext().getFilesDir().toString();
    public static String ImgInfoPath = Environment.getExternalStorageDirectory() + "/industrial/img";

    public static int muzzleHeight = 76;

    public static final String procpath = "/proc/gpio_set/rp_gpio_set";

    /**
     * convert px to its equivalent dp
     * <p>
     * 将px转换为与之相等的dp
     */
    public static int px2dp(float pxValue) {
        final float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * convert dp to its equivalent px
     * <p>
     * 将dp转换为与之相等的px
     */
    public static int dp2px(float dipValue) {
        final float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * convert px to its equivalent sp
     * <p>
     * 将px转换为sp
     */
    public static int px2sp(float pxValue) {
        final float fontScale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * convert sp to its equivalent px
     * <p>
     * 将sp转换为px
     */
    public static int sp2px(float spValue) {
        final float fontScale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath) {
        try {

            File dirFile = new File(TypefacePath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
                dirFile.createNewFile();
            }
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(TypefacePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            GoockrLog.LogInfo(e.getStackTrace().toString());
            e.printStackTrace();

        }

    }

    public static List<String> traverseFolder1(String path) {
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        List<String> typeface = new ArrayList<>();
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
//                    System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else {
                    if (file2.getName().contains("tff")) {
                        typeface.add(file2.getPath());
                    }
//                    System.out.println("文件:" + file2.getAbsolutePath());
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                        folderNum++;
                    } else {
                        if (file2.getName().contains("tff")) {
                            typeface.add(file2.getPath());
                        }
//                        System.out.println("文件:" + file2.getAbsolutePath());
                        fileNum++;
                    }
                }
            }
        } else {
//            System.out.println("文件不存在!");
        }
//        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
        return typeface;
    }

    //获取屏幕宽
    public static int getDisplayWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    //将当前日期转换成JuLian
    public static int dateToJuLian(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR) - 1900;
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        return year * 1000 + dayOfYear;
    }

    public static void saveFileBmp(byte[] data, String fileName) {
        File path = new File(Environment.getExternalStorageDirectory() + "/test1");
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(Environment.getExternalStorageDirectory() + "/test1/" + fileName.replace(":", ""));
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存图片
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static void saveFile(Bitmap bm, String fileName) {
//        createFile();
        try {
            File path = new File(Environment.getExternalStorageDirectory() + "/test");
            if (!path.exists()) {
                path.mkdirs();
            }
            File myCaptureFile = new File(Environment.getExternalStorageDirectory() + "/test/" + fileName.replace(":", ""));
            if (myCaptureFile.exists()) {
                myCaptureFile.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 30, bos);
            Log.e("aaaa", "保存成功?" + myCaptureFile.toString());
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmapForDisk(String path) {
        Bitmap bitmap = null;

        File file = new File(path);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(path);
        }
        return bitmap;
    }

    public static Bitmap zoomImg(Bitmap bm, float bili) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = bili;
        float scaleHeight = bili;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    public static Bitmap directionImg(Bitmap bm, int direction) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        Matrix matrix = new Matrix();
        switch (direction) {
            case 0:
                matrix.postRotate(0);
                break;
            case 1:
                matrix.postRotate(90);
                break;
            case 2:
                matrix.postRotate(180);
                break;
            case 3:
                matrix.postRotate(270);
                break;
        }
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    public static Bitmap cutBitmap(Bitmap bitmap, int w, int h) {
        if (w + 28 > bitmap.getWidth()) {
            w = bitmap.getWidth() - 29;
        }
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        float x = ((float) 48 * PreferenceUtils.getInstance().nozzleCount) / h;
        matrix.postScale(x, x);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 28, 28, w, h, matrix, true);
        return bmp;
    }

    //  0 ---> 正常  1 ---> 不影响打印   2 --->不能打印 下位机的灯
    public static void StoppageSetting(int tag, boolean flag) {
        //writeProc("1_d0_1_0");
        //writeProc("1_d2_1_0");
        //writeProc("1_d3_1_0");

        writeProc("6_b3_1_0");
        writeProc("6_a5_1_0");
        writeProc("6_a6_1_0");

        if (flag) {
            return;
        }

        switch (tag) {
            case 0:
                //writeProc("1_d3_1_1");
                writeProc("6_a5_1_1");
                break;
            case 1:
                //writeProc("1_d2_1_1");
                writeProc("6_a6_1_1");
                break;
            case 2:
                //writeProc("1_d0_1_1");
                writeProc("6_b3_1_1");
                break;
        }
    }

    public static void StoppageSetting2(int tag) {
        //writeProc("1_d0_1_0");
        //writeProc("1_d2_1_0");
        //writeProc("1_d3_1_0");

        writeProc("6_b3_1_1");
        writeProc("6_a5_1_0");
        writeProc("6_a6_1_0");

        switch (tag) {
            case 0:
                //writeProc("1_d3_1_1");
                writeProc("6_a5_1_1");
                break;
            case 1:
                //writeProc("1_d2_1_1");
                writeProc("6_a6_1_1");
                break;
            case 2:
                //writeProc("1_d0_1_1");
                writeProc("6_b3_1_0");

                break;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                writeProc("6_b3_1_1");
            }
        }, 50);
    }

    public static void StoppageSetting3() {
        writeProc("6_b3_1_0");
    }

    public static void StoppageSetting4() {
        writeProc("6_b3_1_1");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                writeProc("6_b3_1_0");
            }
        }, 2000);
    }

    private static String writeProc(String buffer) {
        try {
            File file = new File(procpath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer.getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "write error!";
        }
        return (buffer);
    }

    public static boolean existForName(String name) {
        boolean exist = false;
        String path = MyApplication.getContext().getFilesDir().toString() + "/";
        List<String> list = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) file.mkdirs();
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.getName().contains(".msg")) {
                    list.add(file2.getName());
                }
            }
        }
        if (list.size() == 0) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            String xx = list.get(i).split("\\.")[0];
            if (xx.equals(name)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

}
