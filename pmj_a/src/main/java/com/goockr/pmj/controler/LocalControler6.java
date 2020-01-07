package com.goockr.pmj.controler;

import android.util.Log;

import com.goockr.pmj.cons.PMJBasicCons;
import com.goockr.pmj.utils.AgreementUtils;
import com.goockr.pmj.utils.ByteMergerUtils;
import com.goockr.pmj.utils.CRC32Utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yofreg
 * @time 2018/5/31 15:06
 * @desc socket本地通讯 第六个喷头
 */
public class LocalControler6 implements Runnable {

    private Socket mSocket;
    private OutputStream pw;
    private InputStream is;
    private DataInputStream dis;
    private boolean isRun = true;
    byte buff[]  = new byte[1024];
    private int rcvLen;

    private boolean testSend = false; //用于测试发送,测试时为true

    private Timer timer;
    private Timer timer2;

    public void closeSelf(){
        isRun = false;
    }

    //发送数据的方法
    private void pwsend(byte[] f) throws IOException {

        //计时器,判断服务器是否有返回
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    SocketBeanFS.ebeanFS("服务器无返回", 6, 0);

                }
            }, 2000);
        }
        SocketIsAndOs.OsWrite(pw, f, 6);
    }

    /**
     * 发送
     * @param type byte类型的类型码
     * @param datas byte数组类型的数据码
     */
    public void send(byte type, byte[] datas){

        if (mSocket != null && mSocket.isConnected()) {
            try {
                if (testSend) {
                    //测试用
                    byte[] a = "gckr".getBytes("utf-8");
                    byte[] c = new byte[] {0x01};
                    byte[] d = new byte[] {0, 0, 0, 13};
                    //byte[] e = new byte[] {1, 100, 20, 1, 1, 100, 1, 0, 0, 0, 0, 0, 0};
                    byte[] e = new byte[] {0};
                    byte[] data = e;
                    byte[] payload = new byte[1 + 4 + data.length];
                    payload[0] = 0x05;//type
                    long len_tmp = data.length;
                    payload[1] = (byte)(len_tmp&0xff); len_tmp=len_tmp >> 8;
                    payload[2] = (byte)(len_tmp&0xff); len_tmp=len_tmp >> 8;
                    payload[3] = (byte)(len_tmp&0xff); len_tmp=len_tmp >> 8;
                    payload[4] = (byte)(len_tmp&0xff);
                /*
                payload[4]=(byte)(len_tmp&0xff);len_tmp=len_tmp>>8;
                payload[3]=(byte)(len_tmp&0xff);len_tmp=len_tmp>>8;
                payload[2]=(byte)(len_tmp&0xff);len_tmp=len_tmp>>8;
                payload[1]=(byte)(len_tmp&0xff);*/
                    for(int i = 0; i < data.length; i++) {
                        payload[5 + i] = data[i];
                    }
                    long jiaoyan = CRC32Utils.byteCRC32(payload);
                    byte[] b = new byte[4];
                    b[0] = (byte) (jiaoyan&0xff); jiaoyan = jiaoyan >> 8;
                    b[1] = (byte) (jiaoyan&0xff); jiaoyan=jiaoyan >> 8;
                    b[2] = (byte) (jiaoyan&0xff); jiaoyan=jiaoyan >> 8;
                    b[3] = (byte) (jiaoyan&0xff);
                /*
                b[3] = (byte) (jiaoyan&0xff);jiaoyan=jiaoyan>>8;
                b[2] = (byte) (jiaoyan&0xff);jiaoyan=jiaoyan>>8;
                b[1] = (byte) (jiaoyan&0xff);jiaoyan=jiaoyan>>8;
                b[0] = (byte) (jiaoyan&0xff);*/
                    byte[] f = ByteMergerUtils.byteMerger(a, b, payload);
                    pwsend(f);
                } else {
                    //正式用
                    byte[] a = PMJBasicCons.TCP_TBT.getBytes("utf-8");
                    byte[] payload = new byte[1 + 4 + datas.length];
                    payload[0] = type;
                    long len_tmp = datas.length;
                    payload[1] = (byte)(len_tmp & 0xff); len_tmp=len_tmp >> 8;
                    payload[2] = (byte)(len_tmp & 0xff); len_tmp=len_tmp >> 8;
                    payload[3] = (byte)(len_tmp & 0xff); len_tmp=len_tmp >> 8;
                    payload[4] = (byte)(len_tmp & 0xff);
                    for(int i = 0; i < datas.length; i++) {
                        payload[5 + i] = datas[i];
                    }
                    long jiaoyan = CRC32Utils.byteCRC32(payload);
                    byte[] b = new byte[4];
                    b[0] = (byte) (jiaoyan & 0xff); jiaoyan = jiaoyan >> 8;
                    b[1] = (byte) (jiaoyan & 0xff); jiaoyan = jiaoyan >> 8;
                    b[2] = (byte) (jiaoyan & 0xff); jiaoyan = jiaoyan >> 8;
                    b[3] = (byte) (jiaoyan & 0xff);
                    byte[] f = ByteMergerUtils.byteMerger(a, b, payload);
                    Log.v("xxxxxf", "start= " +  System.currentTimeMillis());
                    pwsend(f);
                }

            } catch (IOException e) {
                SocketBeanFS.ebeanFS("通讯连接不上", 6, 1);
            }
        } else {
            SocketBeanFS.ebeanFS("通讯连接不上", 6, 2);
        }
    }

    /**
     * 启动打印
     * @param datas byte数组类型的数据码
     */
    public void sendQDDY(byte[] datas){

        if (mSocket != null && mSocket.isConnected()) {
            try {

                byte[] f = AgreementUtils.hecheng((byte) 0x01, datas);
                pwsend(f);

            } catch (IOException e) {
                SocketBeanFS.ebeanFS("通讯连接不上", 6, 3);
            }
        } else {
            SocketBeanFS.ebeanFS("通讯连接不上", 6, 4);
        }
    }

    /**
     *  重新设置参数
     * @param datas
     */
    public void sendSZCS(byte[] datas) {
        if (mSocket != null && mSocket.isConnected()) {
            try {
                byte[] f = AgreementUtils.hecheng((byte) 0x09, datas);
                pwsend(f);
            } catch (IOException e) {
                SocketBeanFS.ebeanFS("通讯连接不上", 6, 17);
            }
        } else {
            SocketBeanFS.ebeanFS("通讯连接不上", 6, 18);
        }
    }

    /**
     * 传输图片
     * @param datas byte数组类型的数据码
     */
    public void sendCSTP(byte[] datas){

        if (mSocket != null && mSocket.isConnected()) {
            try {

                byte[] f = AgreementUtils.hecheng((byte) 0x02, datas);
                //计时器,判断服务器是否有返回
                if (timer2 == null) {
                    timer2 = new Timer();
                    timer2.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            SocketBeanFS.ebeanFS("服务器无返回", 6, 1);

                        }
                    }, 1500);
                }
                SocketIsAndOs.OsWrite(pw, f, 1);

            } catch (IOException e) {
                SocketBeanFS.ebeanFS("通讯连接不上", 6, 5);
            }
        } else {
            SocketBeanFS.ebeanFS("通讯连接不上", 6, 6);
        }
    }

    /**
     * 控制打印
     * @param datas byte数组类型的数据码
     */
    public void sendKZDY(byte[] datas){

        if (mSocket != null && mSocket.isConnected()) {
            try {

                byte[] f = AgreementUtils.hecheng((byte) 0x03, datas);
                pwsend(f);

            } catch (IOException e) {
                SocketBeanFS.ebeanFS("通讯连接不上", 6, 7);
            }
        } else {
            SocketBeanFS.ebeanFS("通讯连接不上", 6, 8);
        }
    }

    /**
     * 上传错误信息
     * @param datas byte数组类型的数据码
     */
    public void sendSCXX(byte[] datas){

        if (mSocket != null && mSocket.isConnected()) {
            try {

                byte[] f = AgreementUtils.hecheng((byte) 0x04, datas);
                SocketIsAndOs.OsWrite(pw, f, 6);

            } catch (IOException e) {
                SocketBeanFS.ebeanFS("通讯连接不上", 6, 9);
            }
        } else {
            SocketBeanFS.ebeanFS("通讯连接不上", 6, 10);
        }
    }

    /**
     * 查询墨盒信息
     * @param datas byte数组类型的数据码
     */
    public void sendCXXX(byte[] datas){

        if (mSocket != null && mSocket.isConnected()) {
            try {

                byte[] f = AgreementUtils.hecheng((byte) 0x05, datas);
                pwsend(f);

            } catch (IOException e) {
                SocketBeanFS.ebeanFS("通讯连接不上", 6, 11);
            }
        } else {
            SocketBeanFS.ebeanFS("通讯连接不上", 6, 12);
        }
    }

    /**
     * 升级程序
     * @param datas byte数组类型的数据码
     */
    public void sendSJCX(byte[] datas){

        if (mSocket != null && mSocket.isConnected()) {
            try {

                byte[] f = AgreementUtils.hecheng((byte) 0x06, datas);
                pwsend(f);

            } catch (IOException e) {
                SocketBeanFS.ebeanFS("通讯连接不上", 6, 13);
            }
        } else {
            SocketBeanFS.ebeanFS("通讯连接不上", 6, 14);
        }
    }

    @Override
    public void run() {

        initSocket();
    }

    //初始化socket
    private void initSocket() {
        try {

            mSocket = new Socket(PMJBasicCons.DATA_IP6, PMJBasicCons.DATA_PORT);
            mSocket.setSoTimeout(5000);
            if (mSocket.isConnected()) {
                pw = mSocket.getOutputStream();
                is = mSocket.getInputStream();
                dis = new DataInputStream(is);
                //sendBeatData();
            } else {
                SocketBeanFS.ebeanFS("通讯连接不上", 6, 15);
            }

        } catch (IOException e) {
            SocketBeanFS.ebeanFS("通讯连接不上", 6, 16);
        }
        //循环获取设备传递的数据
        while (isRun){
            try {
                if (is != null) {
                    rcvLen = dis.read(buff);
                    if (rcvLen != -1) {

                        String a = SocketIsAndOs.isRead(rcvLen, buff, 6, timer, timer2);
                        if (a.contains("1")) {
                            if (timer != null) {
                                timer.cancel();
                                timer = null;
                            }
                        }
                        if (a.contains("2")) {
                            if (timer2 != null) {
                                timer2.cancel();
                                timer2 = null;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        releaseSocket();

    }

    /*释放资源*/
    public void releaseSocket() {

        try {
            if (pw != null) {
                pw.close();
                pw = null;
            }
            if (is != null) {
                is.close();
                is = null;
            }
            if (dis != null) {
                dis.close();
                dis = null;
            }
            if (mSocket != null) {
                mSocket.close();
                mSocket = null;
            }
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            if (timer2 != null) {
                timer2.cancel();
                timer2 = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

