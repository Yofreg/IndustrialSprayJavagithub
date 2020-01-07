package com.goockr.pmj.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * @author yofreg
 * @time 2016/7/25 9:11
 * @desc 在子线程和主线程都能运行的Toast
 */
public class Toast2Utils {

    public static void showToast(final Activity context, final String text) {
        //判断当前是否被子线程调用
        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        } else {
           context.runOnUiThread(new Runnable(){

               @Override
               public void run() {
                   Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
               }
           });
        }
    }


}
