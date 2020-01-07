package com.goockr.pmj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * @author yofreg
 * @time 2018/6/4 18:17
 * @desc 用于检测网络是否ok
 */
public class PingUtils {

    /**
     *  用于检测ip ping的联通
     * @param ip 需要ping的ip
     * @return true表示畅通 false表示不通
     * @throws IOException
     */
    public static boolean pingData(String ip) throws IOException {

        Process process = Runtime.getRuntime().exec("ping "+ ip);
        InputStreamReader r = new InputStreamReader(process.getInputStream());
        LineNumberReader returnData = new LineNumberReader(r);
        String returnMsg="";
        String line = "";
        while ((line = returnData.readLine()) != null) {
            System.out.println(line);
            returnMsg += line;
        }

        if(returnMsg.contains("100% loss")){
            return false;
        }  else{
            return true;
        }
    }

    public static boolean ping(String ip) throws IOException, InterruptedException {

        Process p = Runtime.getRuntime().exec("ping -c1 -w1 " + ip);
        // 读取ping的内容，可不加
        InputStream input = p.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        StringBuffer stringBuffer = new StringBuffer();
        String content = "";
        while ((content = in.readLine()) != null) {
            stringBuffer.append(content);
        }
        // PING的状态
        int status = p.waitFor();
        if (status == 0) {
            return true;
        } else {
            return false;
        }
    }
}
