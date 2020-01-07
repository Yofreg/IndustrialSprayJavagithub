package com.goockr.pmj_b;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

/**
 * @author yofreg
 * @time 2018/6/29 15:22
 * @desc 串口操作类
 */
public class SerialUtils {

    private SerialPort serialPort;
    private InputStream inputStream;
    private OutputStream outputStream;

    private  volatile int size = -1;
    private static final int MAX = 1024;

    /**
     *  创建串口通讯
     * @param path 串口文件地址
     * @param baudrate 波特率
     * @param flags 0
     * @throws NullPointerException
     */
    public SerialUtils(String path, int baudrate, int flags) throws NullPointerException{
        try {
            serialPort = new SerialPort(new File(path), baudrate, flags);
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        }
        if(serialPort != null){
            //设置读、写
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
        } else {
            throw new NullPointerException("串口设置有误");
        }
    }

    /**
     * 取得byte的长度
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 串口读数据
     * @return
     */
    public synchronized byte[] getData() throws NullPointerException{
        //上锁，每次只能一个线程在取得数据
        try {
            byte [] buffer = new byte[MAX];
            if (inputStream == null) {
                throw new NullPointerException("inputStream is null");
            }
            //一次最多可读Max的长度
            size = inputStream.read(buffer);
            if (size > 0) {
                return buffer;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 串口读数据
     * @return
     */
    public synchronized byte[] getDataByte() {
        if (inputStream == null) {
            return null;
        }
        try {
            if (inputStream.available() > 0){ //有数据
                byte [] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                return buffer;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 串口写数据
     * @param data 显示的16进制的字符串
     */
    public synchronized void setData(byte[] data) throws Exception{

        if (outputStream == null) {
            throw new Exception("outputStream为空");
        }
        try {
            outputStream.write(data);
        } catch (IOException e) {
            throw new Exception("IO异常");
        }
    }

    /**
     * byte转hexString
     * @param buffer 数据
     * @param size  字符数
     * @return
     */
    public static String bytesToHexString(final byte[] buffer, final int size){
        StringBuilder stringBuilder=new StringBuilder("");
        if (buffer==null||size<=0) return null;
        for (int i = 0; i <size ; i++) {
            String hex=Integer.toHexString(buffer[i]&0xff);
            if(hex.length()<2) stringBuilder.append(0);
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

    /**
     * hexString转byte
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString){
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i <length ; i++) {
            int pos = i * 2;
            d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 关闭串口
     */
    public void closeSerialport(){
        if (serialPort != null){
            serialPort.close();
            serialPort = null;
        }
    }
}
