package com.goockr.industrialsprayjava.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//md5加密
public class EncryptForStr {

    private static String key = "g";

    public static String getStr(int count) {
        String md="abc";
        for(int i=0;i<count;i++){
            md=md5(md);
        }
        return md;
    }


    private static String md5(String string) {
        if(string.equals("")){
            return "";

        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
