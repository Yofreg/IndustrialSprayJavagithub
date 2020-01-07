package com.goockr.industrialsprayjava.tools;

import android.os.Handler;

public class ThreadUtils {
    // 子线程执行Task
    public static void runInThread(Runnable task) {
        new Thread(task).start();
    }


    //UI执行Task
    public static Handler mHandler = new Handler();

    public static void runInUiThread(Runnable task) {
        mHandler.post(task);
    }
}
