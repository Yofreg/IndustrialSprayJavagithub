package com.goockr.pmj.utils;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author yofreg
 * @time 2018/6/15 9:18
 * @desc debug的log
 */
public class MLog {

    private static final boolean debug = true;

    private static File mFile = new File("/sdcard/mlog.log");

    public static void v(String msg) {
        if (debug) {
            Log.v("xxxxxf", msg);
            if (mFile.exists()) {

                BufferedWriter out = null;
                try {
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mFile, true)));
                    out.write(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(out != null){
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }
}
