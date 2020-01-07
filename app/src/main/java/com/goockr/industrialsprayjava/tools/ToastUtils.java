package com.goockr.industrialsprayjava.tools;

import android.widget.Toast;

import com.goockr.industrialsprayjava.MyApplication;

public class ToastUtils {
    private static Toast toast;

    public static void ToastShow(String task) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(MyApplication.getContext(), task, Toast.LENGTH_LONG);
        toast.show();
    }
    /**
     * 子线程弹出Toast
     *
     * @param task
     */
    public static void ToastShowInUI( final String task) {
        ThreadUtils.runInUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(MyApplication.getContext(), task, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

}
