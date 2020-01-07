package com.goockr.pmj_b;

import android.os.SystemClock;
import android.util.Log;

import com.goockr.pmj_b.listener.OnReceivedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yofreg
 * @time 2018/6/29 15:09
 * @desc 串口开放类
 */
public class SerialPortHelper {

    private static SerialPortHelper helper;
    private SerialUtils serialUtil;
    private ReadThread readThread;
    private static OnReceivedListener mOnReceivedListener;

    private List<Byte> list;
    private byte[] b = new byte[6];
    private byte[] a;

    private boolean isOne = true;

    /**
     *  创建通讯的方法
     * @param serialUtilOld 通讯操作类
     */
    private SerialPortHelper(SerialUtils serialUtilOld) {
        this.serialUtil = serialUtilOld;
        list = new ArrayList();
    }

    /**
     *  单例创建
     * @param serialUtilOld 传入activit
     * @return LocalControlerUtil对象
     */
    public static SerialPortHelper getInstance(SerialUtils serialUtilOld) {

        if (helper == null) {
            synchronized (SerialPortHelper.class) {
                if (helper == null) {
                    helper = new SerialPortHelper(serialUtilOld);
                }
            }
        }
        return helper;
    }

    //检测开放类是否创建
    public static SerialPortHelper getInstance() {
        if (helper != null) {
            return helper;
        } else {
            return null;
        }
    }

    /**
     *  发送数据的方法
     * @param bytes 数据码
     * @return true代表发送成功 false失败
     */
    public boolean sendData(byte[] bytes)  {
        try {
            Log.v("xxxxxf", "发送:" + Arrays.toString(bytes));
            serialUtil.setData(bytes);
            return true;
        } catch (Exception e) {
            Log.v("xxxxxf", "io异常");
            return false;
        }
    }

    /**
     *  启动打印的通知
     * @param p 启动的喷头数
     */
    public void sendQD(int p) {

        byte[] aa = new byte[] {0x00, 0x01, (byte) p};
        byte aaa = 0;

        for (byte bbb : aa) {
            aaa = (byte) (aaa ^ bbb);
        }

        byte[] bb = new byte[] {0x7e, 0x00, 0x01, (byte) p, aaa, 0x7f};

        sendData(bb);
    }

    /**
     *  故障回复
     */
    public void sendGZ() {
        sendData(new byte[] {0x7e, 0x00, 0x02, 0x00, 0x02, 0x7f});
    }

    /**
     * 关闭读取数据的回调接口
     */
    public void releaseReadThread() {

        mOnReceivedListener = null;
        if (readThread != null) {
            readThread.interrupt();
        }
    }

    //读取数据的线程
    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!Thread.currentThread().isInterrupted()){

                SystemClock.sleep(50);
                if (serialUtil == null) {
                    Log.v("xxxxxf", "嘟嘟");
                    return;
                }

                byte[] data = serialUtil.getDataByte();
                //Log.v("xxxxxf", data + "" + Arrays.toString(data));
                if (data != null) {
                    if (data.length == 15 && data[2] == -125) {
                    } else {

                    }
                    //串口获取的数据添加到list中
                    for (byte aData : data) {
                        list.add(aData);
                    }
                    Log.v("xxxxxf", "读到=" + Arrays.toString(data) + ":" + list.toString());

                    int tou = list.size();
                    for (int i = 0; i < tou; i++) {
                        if (list.get(0) == 126) {
                            if (list.size() >= 2) {
                                if (list.get(1) == 0) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        } else {
                            list.remove(0);
                        }
                    }

                    if (list.size() >= 6) {

                        if (list.get(0) == 126 && list.get(1) == 0 && list.get(2) == -127) { //喷头启动
                            for (int i = 0; i < 6; i++) {
                                b[i] = list.get(0);
                                list.remove(0);
                            }
                            byte c1 = 0;
                            for (int i = 1; i < 4; i++) { //校验
                                c1 = (byte) (c1 ^ b[i]);
                            }
                            //Log.v("xxxxxf", "c1=" + c1 + ";b[4]=" + b[4]);
                            if (c1 == b[4]) { //校验成功
                                if (mOnReceivedListener != null) {
                                    mOnReceivedListener.onReceived1(b); //启动回复
                                }
                            }
                        } else if (list.get(0) == 126 && list.get(1) == 0 && list.get(2) == -126) { //故障
                            if (list.get(3) < 0 || list.get(3) > 8) {
                                for (int i = 0; i < 4; i++) {
                                    list.remove(0);
                                }
                                return;
                            }
                            if (list.size() >= list.get(3) + 7) {
                                //Log.v("xxxxxf", "进行近");
                                int s = list.get(3) + 7;
                                a = new byte[s];
                                for (int i = 0; i < s; i++) {
                                    a[i] = list.get(0);
                                    list.remove(0);
                                }
                                byte c1 = 0;
                                for (int i = 1; i < (a[3] + 5); i++) { //校验
                                    c1 = (byte) (c1 ^ a[i]);
                                }
                                Log.v("xxxxxf", "c1=" + c1 + ";a[(a[3] + 5)]=" + a[(a[3] + 5)]);
                                if (c1 == a[(a[3] + 5)]) { //校验成功
                                    list.clear(); //清空list
                                    if (mOnReceivedListener != null) {
                                        mOnReceivedListener.onReceived2(a); //故障回复
                                    }
                                    //sendGZ();
                                }
                            }
                        } else if (list.get(0) == 126 && list.get(1) == 0 && list.get(2) == -125) { //高低电平检测
                            if (list.size() >= 15) {
                                for (int i = 0; i < 15; i++) {
                                    list.remove(0);
                                }
                            }
                        }
                    }
                    /*else if (list.size() == 6) {

                        if (list.get(0) == 126 && list.get(1) == 0 && list.get(2) == -127) {
                            for (int i = 0; i < 6; i++) {
                                b[i] = list.get(0);
                                list.remove(0);
                            }
                            byte c1 = 0;
                            for (int i = 1; i < 4; i++) { //校验
                                c1 = (byte) (c1 ^ b[i]);
                            }
                            //Log.v("xxxxxf", "c1=" + c1 + ";b[4]=" + b[4]);
                            if (c1 == b[4]) { //校验成功
                                mOnReceivedListener.onReceived1(b); //回复启动信号
                            }
                        }
                    }*/
                }
            }
        }
    }

    /**
     *  读取数据的回调接口
     * @param onReceivedListener 回调接口
     */
    public void setOnReceivedListener(OnReceivedListener onReceivedListener) {

        mOnReceivedListener = onReceivedListener;

        if (readThread != null) {
            readThread.interrupt();
            readThread = null;
        }

        readThread = new ReadThread();
        readThread.start();
    }


    /**
     * byte转hexString
     * @param buffer 数据
     * @param size  字符数
     * @return
     */
    private  String bytesToHexString(final byte[] buffer, final int size){

        StringBuilder stringBuilder=new StringBuilder("");
        if (buffer == null || size <= 0)
            return null;
        for (int i = 0; i <size ; i++) {
            String hex = Integer.toHexString(buffer[i] & 0xff);
            if(hex.length() < 2)
                stringBuilder.append(0);
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

    /**
     * 关闭串口
     */
    public void closeSerialport() {
        isOne = true;
        if (serialUtil != null) {
            releaseReadThread();
            serialUtil.closeSerialport();
            serialUtil = null;
        }
    }
}


