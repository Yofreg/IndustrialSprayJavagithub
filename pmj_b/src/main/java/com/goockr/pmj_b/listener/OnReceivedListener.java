package com.goockr.pmj_b.listener;

/**
 * @author yofreg
 * @time 2018/8/20 15:48
 * @desc ${TODD}
 */
public interface OnReceivedListener {

     /**
      *  启动回复
      * @param bytes 数据码
      */
     void onReceived1(byte[] bytes);

     /**
      *  故障回复
      * @param bytes 数据码
      */
     void onReceived2(byte[] bytes);
}
