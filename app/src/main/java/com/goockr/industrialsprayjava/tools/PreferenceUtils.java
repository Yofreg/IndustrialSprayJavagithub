package com.goockr.industrialsprayjava.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.goockr.industrialsprayjava.MyApplication;

public class PreferenceUtils {
    private static PreferenceUtils instance;
    public String defaultMsg;
    public int nozzleCount;
    public String print1;
    public String print2;
    public String print3;
    public String print4;
    public String print5;
    public String print6;
    public String print7;
    public String print8;

    public String msgCount, typefaceCount, ImgCount;

    public boolean isMM;
    public boolean isHighLevel;
    public boolean isSound;

    public int detectionCount, printCount;
    public String resetTime;
    public int allDetectionCount, allPrintCount;
    public String allResetTime;

    public String no1, no2, no3, no4;
    public boolean isPwd;
    public String pwd;

    public int alert;
    public int alertCount;

    public String inkBox1, inkBox2, inkBox3, inkBox4, inkBox5, inkBox6, inkBox7, inkBox8;

    public int printMainFontLetterStyle;
    public int styleSettingCount;
    public int repeatPrint;
    public int repeatCount;
    public int repeatDelay;
    public int userEndSign;

    public String language;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static synchronized PreferenceUtils getInstance() {
        if (instance == null) {
            instance = new PreferenceUtils();
        }
        instance.InstanceData();
        return instance;
    }


    private void InstanceData() {
        preferences = MyApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        defaultMsg = preferences.getString("defaultMsg", "defaultMsg");
        nozzleCount = preferences.getInt("nozzleCount", 4);
        print1 = preferences.getString("print1", "");
        print2 = preferences.getString("print2", "");
        print3 = preferences.getString("print3", "");
        print4 = preferences.getString("print4", "");
        print5 = preferences.getString("print5", "");
        print6 = preferences.getString("print6", "");
        print7 = preferences.getString("print7", "");
        print8 = preferences.getString("print8", "");
        msgCount = preferences.getString("msgCount", "0");
        typefaceCount = preferences.getString("typefaceCount", "0");
        ImgCount = preferences.getString("imgCount", "0");
        isMM = preferences.getBoolean("isMM", true);
        isHighLevel = preferences.getBoolean("isHighLevel", true);
        isSound = preferences.getBoolean("isSound", true);
        detectionCount = preferences.getInt("detectionCount", 0);
        printCount = preferences.getInt("printCount", 0);
        resetTime = preferences.getString("resetTime", "");
        allDetectionCount = preferences.getInt("allDetectionCount", 0);
        allPrintCount = preferences.getInt("allPrintCount", 0);
        allResetTime = preferences.getString("allResetTime", "");
        no1 = preferences.getString("no1", "");
        no2 = preferences.getString("no2", "");
        no3 = preferences.getString("no3", "");
        no4 = preferences.getString("no4", "");
        isPwd = preferences.getBoolean("isPwd", false);
        pwd = preferences.getString("pwd", "service");
        alert = preferences.getInt("alert", 0);
        alertCount = preferences.getInt("alertCount", 10);  //提醒天数
        inkBox1 = preferences.getString("inkBox1", "");
        inkBox2 = preferences.getString("inkBox2", "");
        inkBox3 = preferences.getString("inkBox3", "");
        inkBox4 = preferences.getString("inkBox4", "");
        inkBox5 = preferences.getString("inkBox5", "");
        inkBox6 = preferences.getString("inkBox6", "");
        inkBox7 = preferences.getString("inkBox7", "");
        inkBox8 = preferences.getString("inkBox8", "");
        printMainFontLetterStyle = preferences.getInt("printMainFontLetterStyle", 1);
        styleSettingCount = preferences.getInt("styleSettingCount", 3);
        repeatPrint = preferences.getInt("repeatPrint", 0);
        repeatCount = preferences.getInt("repeatCount", 1);
        repeatDelay = preferences.getInt("repeatDelay", 1);
        userEndSign = preferences.getInt("userEndSign", 0);
        language= preferences.getString("language", "china");

    }

    public void setDefaultMsg(String defaultMsg) {
        editor.putString("defaultMsg", defaultMsg);
        editor.commit();
    }

    public void setNozzleCount(int nozzleCount) {
        editor.putInt("nozzleCount", nozzleCount);
        editor.commit();
    }

    public void setPrint1(String print1) {
        editor.putString("print1", print1);
        editor.commit();
    }

    public void setPrint2(String print2) {
        editor.putString("print2", print2);
        editor.commit();
    }

    public void setPrint3(String print3) {
        editor.putString("print3", print3);
        editor.commit();
    }

    public void setPrint4(String print4) {
        editor.putString("print4", print4);
        editor.commit();
    }

    public void setPrint5(String print5) {
        editor.putString("print5", print5);
        editor.commit();
    }

    public void setPrint6(String print6) {
        editor.putString("print6", print6);
        editor.commit();
    }

    public void setPrint7(String print7) {
        editor.putString("print7", print7);
        editor.commit();
    }

    public void setPrint8(String print8) {
        editor.putString("print8", print8);
        editor.commit();
    }

    public void setMsgCount(String msgCount) {
        editor.putString("msgCount", msgCount);
        editor.commit();
    }

    public void setTypefaceCount(String typefaceCount) {
        editor.putString("typefaceCount", typefaceCount);
        editor.commit();
    }

    public void setMM(boolean MM) {
        editor.putBoolean("isMM", MM);
        editor.commit();
    }

    public void setImgCount(String imgCount) {
        editor.putString("imgCount", imgCount);
        editor.commit();
    }

    public void setHighLevel(boolean highLevel) {
        editor.putBoolean("isHighLevel", highLevel);
        editor.commit();
    }

    public void setSound(boolean sound) {
        editor.putBoolean("isSound", sound);
        editor.commit();
    }


    public void setRestart(String time) {
        editor.putInt("detectionCount", 0);
        editor.putInt("printCount", 0);
        editor.putString("resetTime", time);
        editor.commit();
    }

    public void setAllRestart(String time) {
        editor.putInt("allDetectionCount", 0);
        editor.putInt("allPrintCount", 0);
        editor.putString("allResetTime", time);
        editor.commit();
    }

    public void setCount() {
        editor.putInt("detectionCount", detectionCount + 1);
        editor.putInt("printCount", printCount + repeatCount);
        editor.putInt("allDetectionCount", allDetectionCount + 1);
        editor.putInt("allPrintCount", allPrintCount + repeatCount);
        editor.commit();
    }

    public void setNo1(String no1) {
        editor.putString("no1", no1);
        editor.commit();
    }

    public void setNo2(String no2) {
        editor.putString("no2", no2);
        editor.commit();
    }

    public void setNo3(String no3) {
        editor.putString("no3", no3);
        editor.commit();
    }

    public void setNo4(String no4) {
        editor.putString("no4", no4);
        editor.commit();
    }

    public void setNoCLear() {
        editor.putString("no1", "");
        editor.putString("no2", "");
        editor.putString("no3", "");
        editor.putString("no4", "");
        editor.commit();
    }

    public void setPwd(boolean pwd) {
        editor.putBoolean("isPwd", pwd);
        editor.commit();
    }

    public void setPwd(String pwd) {
        editor.putString("pwd", pwd);
        editor.commit();
    }

    public void setAlert(int alert) {
        editor.putInt("alert", alert);
        editor.commit();
    }

    public void setAlertCount(int alertCount) {
        editor.putInt("alertCount", alertCount);
        editor.commit();
    }

    public void setInkBox1(String inkBox1) {
        editor.putString("inkBox1", inkBox1);
        editor.commit();
    }

    public void setInkBox2(String inkBox2) {
        editor.putString("inkBox2", inkBox2);
        editor.commit();
    }

    public void setInkBox3(String inkBox3) {
        editor.putString("inkBox3", inkBox3);
        editor.commit();
    }

    public void setInkBox4(String inkBox4) {
        editor.putString("inkBox4", inkBox4);
        editor.commit();
    }

    public void setInkBox5(String inkBox5) {
        editor.putString("inkBox5", inkBox5);
        editor.commit();
    }

    public void setInkBox6(String inkBox6) {
        editor.putString("inkBox6", inkBox6);
        editor.commit();
    }

    public void setInkBox7(String inkBox7) {
        editor.putString("inkBox7", inkBox7);
        editor.commit();
    }

    public void setInkBox8(String inkBox8) {
        editor.putString("inkBox8", inkBox8);
        editor.commit();
    }

    public void setPrintMainFontLetterStyle(int printMainFontLetterStyle) {
        editor.putInt("printMainFontLetterStyle", printMainFontLetterStyle);
        editor.commit();
    }

    public void setStyleSettingCount(int styleSettingCount) {
        editor.putInt("styleSettingCount", styleSettingCount);
        editor.commit();
    }

    public void setRepeatPrint(int repeatPrint) {
        editor.putInt("repeatPrint", repeatPrint);
        editor.commit();
    }

    public void setRepeatCount(int repeatCount) {
        editor.putInt("repeatCount", repeatCount);
        editor.commit();
    }

    public void setRepeatDelay(int repeatDelay) {
        editor.putInt("repeatDelay", repeatDelay);
        editor.commit();
    }

    public void setUserEndSign(int userEndSign) {
        editor.putInt("userEndSign", userEndSign);
        editor.commit();
    }

    public void setLanguage(String language) {
        editor.putString("language", language);
        editor.commit();
    }
}
