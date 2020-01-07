package com.goockr.pmj.controler;

import com.goockr.pmj.utils.ByteMergerUtils;
import com.goockr.pmj.utils.CRC32Utils;
import com.goockr.pmj.utils.LongIntToBytesUtils;
import com.goockr.pmj.utils.MLog;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Timer;

/**
 * @author yofreg
 * @time 2018/6/6 10:44
 * @desc socket通讯的输入与输出
 */
public class SocketIsAndOs {

    /**
     *  读取设备发来的数据
     */
    public static String isRead(int rcvLen, byte[] buff, int p, Timer timer, Timer timer2) throws UnsupportedEncodingException {
        String a = aaa(rcvLen, buff, p);
        return a;
    }

    private static String aaa(int rcvLen, byte[] buff, int p) throws UnsupportedEncodingException {
        if (rcvLen >= 4) {
            //获得同步头
            byte[] isTBTByte = new byte[4];

            System.arraycopy(buff, 0, isTBTByte, 0, 4);

            String isTBTString = new String(isTBTByte, 0, 4, "utf-8");
            //判断是否为协议报文
            if (isTBTString.equals("gckr")) {
                if (rcvLen > 13) {

                        /*StringBuilder a = new StringBuilder();
                        for (int i = 0; i < rcvLen; i++) {
                            a.append(buff[i]).append(",");
                        }
                        MLog.v("回复码:" + a);*/

                    byte[] isJYM = new byte[4]; //校验码
                    byte[] isLXM = new byte[1]; //类型码
                    byte[] isCDM = new byte[4]; //长度码

                    System.arraycopy(buff, 4, isJYM, 0, 4);

                    isLXM[0] = buff[8];

                    System.arraycopy(buff, 9, isCDM, 0, 4);

                    int cd = LongIntToBytesUtils.bytesToInt2(isCDM);

                    if (rcvLen < cd + 13) {
                        SocketBeanFS.ebeanFS("回复不符合规范", p, 1);
                        return "";
                    }

                    byte[] isSJM = new byte[cd]; //数据码

                    System.arraycopy(buff, 13, isSJM, 0, cd);

                    byte[] f = ByteMergerUtils.byteMerger(isLXM, isCDM, isSJM);

                    long jiaoyan = CRC32Utils.byteCRC32(f);
                    byte[] b = new byte[4];
                    b[0] = (byte) (jiaoyan & 0xff); jiaoyan = jiaoyan >> 8;
                    b[1] = (byte) (jiaoyan & 0xff); jiaoyan = jiaoyan >> 8;
                    b[2] = (byte) (jiaoyan & 0xff); jiaoyan = jiaoyan >> 8;
                    b[3] = (byte) (jiaoyan & 0xff);

                    boolean isTrueJYM = true; //用来判断校验码
                    for (int i = 0; i < 4; i++) {

                        if (b[i] != isJYM[i]) {
                            isTrueJYM = false;
                        }
                    }

                    if (isTrueJYM) {
                        SocketBeanFS.beanFS(isLXM, isSJM, p);
                    } else {
                        SocketBeanFS.ebeanFS("校验失败", p, 0);

                    }

                    String a = "";
                    if ((cd + 13) < rcvLen) {
                        //MLog.v("lianglu");
                        byte[] x = new byte[rcvLen - cd - 13];
                        System.arraycopy(buff, cd + 13, x, 0, rcvLen - cd - 13);
                        a = aaa(rcvLen - cd - 13, x, p);
                    }

                    String aa = "";

                    switch (isLXM[0]) {
                        case -126:
                            aa = "2";
                            break;
                        case -121:
                            aa = "3";
                            break;
                        default:
                            aa = "1";
                            break;
                    }

                    return aa + a;
                } else {
                    SocketBeanFS.ebeanFS("回复不符合规范", p, 0);
                    return "";
                }
            } else {
                String rcvMsg = new String(buff, 0, rcvLen, "utf-8");
                SocketBeanFS.ebeanFS(rcvMsg, p, 0);
                return "";
            }
        } else {
            //String rcvMsg = new String(buff, 0, rcvLen, "utf-8");

            SocketBeanFS.ebeanFS(Arrays.toString(buff), p, 1);
            return "";
        }
    }

    /**
     *  读取设备发来的数据
     */
    public static void isRead(int rcvLen, byte[] buff, int p) throws UnsupportedEncodingException {
        aa(rcvLen, buff, p);
    }

    private static void aa(int rcvLen, byte[] buff, int p) throws UnsupportedEncodingException {
        if (rcvLen >= 4) {
            //获得同步头
            byte[] isTBTByte = new byte[4];
            isTBTByte[0] = buff[0];
            isTBTByte[1] = buff[1];
            isTBTByte[2] = buff[2];
            isTBTByte[3] = buff[3];
            String isTBTString = new String(isTBTByte, 0, 4, "utf-8");
            //判断是否为协议报文
            if (isTBTString.equals("gckr")) {
                if (rcvLen > 13) {

                        /*StringBuilder a = new StringBuilder();
                        for (int i = 0; i < rcvLen; i++) {
                            a.append(buff[i]).append(",");
                        }
                        MLog.v("回复码:" + a);*/

                    byte[] isJYM = new byte[4]; //校验码
                    byte[] isLXM = new byte[1]; //类型码
                    byte[] isCDM = new byte[4]; //长度码


                    isJYM[0] = buff[4];
                    isJYM[1] = buff[5];
                    isJYM[2] = buff[6];
                    isJYM[3] = buff[7];

                    isLXM[0] = buff[8];

                    isCDM[0] = buff[9];
                    isCDM[1] = buff[10];
                    isCDM[2] = buff[11];
                    isCDM[3] = buff[12];

                    int cd = LongIntToBytesUtils.bytesToInt2(isCDM);

                    if (rcvLen < cd + 13) {
                        SocketBeanFS.ebeanFS("回复不符合规范", p, 1);
                        return;
                    }

                    byte[] isSJM = new byte[cd]; //数据码

                    System.arraycopy(buff, 13, isSJM, 0, cd);

                    byte[] f = ByteMergerUtils.byteMerger(isLXM, isCDM, isSJM);
                    //MLog.v("f = " + Arrays.toString(f));
                    long jiaoyan = CRC32Utils.byteCRC32(f);
                    byte[] b = new byte[4];
                    b[0] = (byte) (jiaoyan & 0xff); jiaoyan = jiaoyan >> 8;
                    b[1] = (byte) (jiaoyan & 0xff); jiaoyan = jiaoyan >> 8;
                    b[2] = (byte) (jiaoyan & 0xff); jiaoyan = jiaoyan >> 8;
                    b[3] = (byte) (jiaoyan & 0xff);

                    boolean isTrueJYM = true; //用来判断校验码
                    for (int i = 0; i < 4; i++) {
                        //MLog.v("b = " + b[i] + "; isJYM = " + isJYM[i]);
                        if (b[i] != isJYM[i]) {
                            //MLog.v("b = " + b[i] + "; isJYM = " + isJYM[i]);
                            isTrueJYM = false;
                        }
                    }

                    if (isTrueJYM) {
                        SocketBeanFS.beanFS(isLXM, isSJM, p);
                    } else {
                        SocketBeanFS.ebeanFS("校验失败", p, 0);
                    }

                    if ((cd + 13) < rcvLen) {
                        //MLog.v("lianglu");
                        byte[] x = new byte[rcvLen - cd - 13];
                        System.arraycopy(buff, cd + 13, x, 0, rcvLen - cd - 13);
                        aa(rcvLen - cd - 13, x, p);
                    }

                } else {
                    SocketBeanFS.ebeanFS("回复不符合规范", p, 0);
                }
            } else {
                String rcvMsg = new String(buff, 0, rcvLen, "utf-8");
                SocketBeanFS.ebeanFS(rcvMsg, p, 0);
            }
        } else {
            String rcvMsg = new String(buff, 0, rcvLen, "utf-8");
            SocketBeanFS.ebeanFS(rcvMsg, p, 0);
        }
    }

    /**
     *  发送到设备的数据 实际用时屏蔽打印循环
     */
    public static void OsWrite(OutputStream pw, byte[] f, int p) throws IOException {
        if (f[8] == 2) {
            //MLog.v( "2喷头 = " + p + "; 类型码 = " + Arrays.toString(f) + "; 数据长度 = " + f.length);
            //MLog.v("2");
        } else if (f[8] == 4) {

        } else {
            byte[] a = new byte[f.length - 13];
            System.arraycopy(f, 13, a, 0, f.length - 13);
            MLog.v( "喷头 = " + p + "; 类型码 = " + f[8] + "; 发送的数据 = " + Arrays.toString(a) + "; 数据长度 = " + f.length);
        }
        pw.write(f);
        pw.flush();
    }
}
