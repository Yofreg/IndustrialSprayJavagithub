package com.goockr.industrialsprayjava.tools;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.goockr.industrialsprayjava.bean.FileItem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class FileUtils {
    private static FileUtils instance;
    private static final int SUCCESS = 1;
    private static final int FAILED = 0;
    private Context context;
    private FileOperateCallback callback;
    private volatile boolean isSuccess;
    private String errorStr;

    public static FileUtils getInstance(Context context) {
        if (instance == null)
            instance = new FileUtils(context);
        return instance;
    }

    private FileUtils(Context context) {
        this.context = context;
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (callback != null) {
                if (msg.what == SUCCESS) {
                    callback.onSuccess();
                }
                if (msg.what == FAILED) {
                    callback.onFailed("复制失败");
                }
            }
        }
    };

    public FileUtils deleteFile(final List<FileItem> fileItems) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                isSuccess = true;
                for (int i = 0; i < fileItems.size(); i++) {
                    FileItem fileItem = fileItems.get(i);
                    if (fileItem.isCheck()) {
                        File file = new File(fileItem.getFilePath());
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
                if (isSuccess)
                    handler.obtainMessage(SUCCESS).sendToTarget();
                else
                    handler.obtainMessage(FAILED, errorStr).sendToTarget();
            }
        }).start();
        return this;
    }

    public FileUtils copyFileToSD(final List<FileItem> fileItems, final String tag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < fileItems.size(); i++) {
                    FileItem fileItem = fileItems.get(i);
                    if (fileItem.isCheck())
                        copyFileToDst(fileItem.getFilePath(), fileItem.getName(), tag);
                }
                if (isSuccess)
                    handler.obtainMessage(SUCCESS).sendToTarget();
                else
                    handler.obtainMessage(FAILED, errorStr).sendToTarget();
            }
        }).start();
        return this;
    }

    private void copyFileToDst(String srcPath, String name, String tag) {
        try {
            File file = new File(BaseUtils.ImportPath + "/industrial/" + tag + "/");
            if (!file.exists()) file.mkdirs();

            File outFile = new File(BaseUtils.ImportPath + "/industrial/" + tag + "/" + name);

            InputStream is = new FileInputStream(srcPath);
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            errorStr = e.getMessage();
            isSuccess = false;
        }
    }

    public FileUtils copyMsgToSD(final List<FileItem> fileItems, final String tag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < fileItems.size(); i++) {
                    FileItem fileItem = fileItems.get(i);
                    if (fileItem.isCheck())
                        copyMsgToDst(fileItem.getFilePath(), fileItem.getName(), tag);
                }
                if (isSuccess)
                    handler.obtainMessage(SUCCESS).sendToTarget();
                else
                    handler.obtainMessage(FAILED, errorStr).sendToTarget();
            }
        }).start();
        return this;
    }

    private void copyMsgToDst(String srcPath, String name, String tag) {
        try {
            File file = new File(BaseUtils.codePath);
            if (!file.exists()) file.mkdirs();

            File outFile = new File(BaseUtils.codePath + "/" + name);

            InputStream is = new FileInputStream(srcPath);
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            errorStr = e.getMessage();
            isSuccess = false;
        }
    }

    public FileUtils copyAssetsToSD(final List<FileItem> fileItems, final String tag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < fileItems.size(); i++) {
                    FileItem fileItem = fileItems.get(i);
                    if (fileItem.isCheck())
                        copyAssetsToDst(context, fileItem.getFilePath(), fileItem.getName(), tag);
                }
                if (isSuccess)
                    handler.obtainMessage(SUCCESS).sendToTarget();
                else
                    handler.obtainMessage(FAILED, errorStr).sendToTarget();
            }
        }).start();
        return this;
    }

    public FileUtils copyAssetsToSD(final  FileItem fileItem, final String tag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                copyAssetsToDst(context, fileItem.getFilePath(), fileItem.getName(), tag);
                if (isSuccess)
                    handler.obtainMessage(SUCCESS).sendToTarget();
                else
                    handler.obtainMessage(FAILED, errorStr).sendToTarget();
            }
        }).start();
        return this;
    }

    public void setFileOperateCallback(FileOperateCallback callback) {
        this.callback = callback;
    }

    private void copyAssetsToDst(Context context, String srcPath, String name, String tag) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/industrial/" + tag + "/");
            if (!file.exists()) file.mkdirs();

            File outFile = new File(Environment.getExternalStorageDirectory() + "/industrial/" + tag + "/" + name);

            InputStream is = new FileInputStream(srcPath);
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            errorStr = e.getMessage();
            isSuccess = false;
        }
    }

    public interface FileOperateCallback {
        void onSuccess();

        void onFailed(String error);
    }

    public static void writeWJ(String data) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/xianxian.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
        out.write(data);
        out.write("\r\n");
        out.close();
    }
}
