package com.goockr.pmj.utils;

import android.app.Activity;
import android.content.Intent;

import com.goockr.pmj.LocalControlerUtil;
import com.goockr.pmj.bean.ErrorBean;
import com.goockr.pmj.bean.PMJstorageBean;


/**
 * @author yofreg
 * @time 2016/7/24 10:14
 * @desc 页面跳转的工具方法
 */
public class IntentUtils {

    /**
     *
     * @param context activity
     * @param clazz 字节码
     * @param delayTime 开启一个activity需要延迟 子线程开启需要用到
     */
    public static void startIntentAndDelteyAndFinish(final Activity context,
                                                     final Class clazz,
                                                     final long delayTime) {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(delayTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, clazz);
                context.startActivity(intent);
                context.finish();
            }
        }.start();
    }

    //需要关闭原来的activity
    public static void statrtIntentAndFinish(Activity context, final Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
        context.finish();
    }

    //不需要关闭原来的activity
    public static void statrIntent(Activity context, final Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    //socket发送数据给界面
    public static void sendBroadcast(boolean isXY, Object sj) {
        //MLog.v("广播");
        if (isXY) {
            PMJstorageBean bean = (PMJstorageBean) sj;
            Intent intent =new Intent();
            intent.setAction("tcpClientReceiver");
            intent.putExtra("isXY", isXY);
            intent.putExtra("tcpClientReceiver", bean);
            LocalControlerUtil.mContext.sendBroadcast(intent);
        } else {
            ErrorBean s = (ErrorBean) sj;
            Intent intent = new Intent();
            intent.setAction("tcpClientReceiver");
            intent.putExtra("isXY", isXY);
            intent.putExtra("tcpClientReceiver", s);
            LocalControlerUtil.mContext.sendBroadcast(intent);
        }
    }

    //socket发送数据给界面
    public static void sendBroadcast2(boolean isXY, Object sj) {
        if (LocalControlerUtil.getInstance() != null) {
            LocalControlerUtil.getInstance().sendHD(isXY, sj);
        }
        //MLog.v("广播");
        /*if (isXY) {
            PMJstorageBean bean = (PMJstorageBean) sj;
            Intent intent =new Intent();
            intent.setAction("tcpClientReceiver");
            intent.putExtra("isXY", isXY);
            intent.putExtra("tcpClientReceiver", bean);
            LocalControlerUtil.mContext.sendBroadcast(intent);



        } else {
            ErrorBean s = (ErrorBean) sj;
            Intent intent = new Intent();
            intent.setAction("tcpClientReceiver");
            intent.putExtra("isXY", isXY);
            intent.putExtra("tcpClientReceiver", s);
            LocalControlerUtil.mContext.sendBroadcast(intent);
        }*/
    }
}
