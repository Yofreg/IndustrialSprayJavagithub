package com.goockr.industrialsprayjava.tools;

import android.content.Context;

import com.goockr.industrialsprayjava.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveJsonToMemory {

    private String saveFilePath = MyApplication.getContext().getFilesDir().toString() + "/";

    private String saveAllMsgInfoFileName = "AllMsgInfo.msg";
    private String saveAllImgFileName = "AllImg.msg";
    private String saveAllTypefaceFileName = "AllTypeface.msg";

    private String saveDateCodeFileName = "DateCode.setting";
    private String saveTimeCodeFileName = "TimeCode.setting";

    private Context context;
    public SaveJsonToMemory() {
        context = MyApplication.getContext();
    }

    public String LoadMsgInfo(String msgName) {
        String MsgInfo = "";
        try {
            File file = new File(saveFilePath + msgName+".msg");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fileInputStream = context.openFileInput(msgName+".msg");
            int length = fileInputStream.available();//获取文件长度
            byte[] buffer = new byte[length];//创建byte数组用于读入数据
            fileInputStream.read(buffer);
            fileInputStream.close();
            MsgInfo = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MsgInfo;
    }

    public void SaveMsgInfo(String MsgInfo,String fileName) {
        try {
            File file = new File(saveFilePath + fileName + ".msg");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = context.openFileOutput(fileName + ".msg", Context.MODE_PRIVATE);
            outputStream.write(MsgInfo.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void SaveDateCode(String deteCodeString) {
        try {
            File file = new File(saveFilePath+saveDateCodeFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = context.openFileOutput(saveDateCodeFileName, Context.MODE_PRIVATE);
            outputStream.write(deteCodeString.getBytes());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String LoadDateCodeToMemory() {
        String DateCode = "";

        try {
            File file = new File(saveFilePath+saveDateCodeFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fileInputStream = context.openFileInput(saveDateCodeFileName);
            int length = fileInputStream.available();//获取文件长度
            byte[] buffer = new byte[length];//创建byte数组用于读入数据
            fileInputStream.read(buffer);
            fileInputStream.close();
            DateCode = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return DateCode;
    }

    public void SaveTimeCode(String deteCodeString) {
        try {
            File file = new File(saveFilePath+saveTimeCodeFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = context.openFileOutput(saveTimeCodeFileName, Context.MODE_PRIVATE);
            outputStream.write(deteCodeString.getBytes());
            outputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String LoadTimeCodeToMemory() {
        String DateCode = "";
        try {
            File file = new File(saveFilePath+saveTimeCodeFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fileInputStream = context.openFileInput(saveTimeCodeFileName);

            int length = fileInputStream.available();//获取文件长度
            byte[] buffer = new byte[length];//创建byte数组用于读入数据
            fileInputStream.read(buffer);
            fileInputStream.close();
            DateCode = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DateCode;
    }

}
