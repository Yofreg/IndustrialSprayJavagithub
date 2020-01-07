package com.goockr.pmj.utils;

import com.goockr.pmj.bean.MHdataBean;

import java.util.Arrays;

/**
 * @author yofreg
 * @time 2018/6/8 15:09
 * @desc 用于bean的添加数据
 */
public class BeanHCUtils {

    public static MHdataBean mhdatabean(byte[] bytes) {
        MHdataBean mHdataBean = new MHdataBean();
        switch (bytes.length) {
            case 12:
                //驱动版本号
                mHdataBean.setQDBB(new String(Arrays.copyOfRange(bytes, 0, 12)));
                return mHdataBean;
            case 16:
                //驱动版本号
                mHdataBean.setQDBB(new String(Arrays.copyOfRange(bytes, 0, 12)));
                //经销商代码
                mHdataBean.setJXS(new String(Arrays.copyOfRange(bytes, 12, 15)) + bytes[15]);
                return mHdataBean;
            case 20:
                //驱动版本号
                mHdataBean.setQDBB(new String(Arrays.copyOfRange(bytes, 0, 12)));
                //经销商代码
                mHdataBean.setJXS(new String(Arrays.copyOfRange(bytes, 12, 15)) + bytes[15]);
                //墨水型号
                mHdataBean.setMSXH(new String(Arrays.copyOfRange(bytes, 16, 18)) +
                        LongIntToBytesUtils.bytesToInt(Arrays.copyOfRange(bytes, 18, 20)));
                return mHdataBean;
            case 28:
                //驱动版本号
                mHdataBean.setQDBB(new String(Arrays.copyOfRange(bytes, 0, 12)));
                //经销商代码
                mHdataBean.setJXS(new String(Arrays.copyOfRange(bytes, 12, 15)) + bytes[15]);
                //墨水型号
                mHdataBean.setMSXH(new String(Arrays.copyOfRange(bytes, 16, 18)) +
                        LongIntToBytesUtils.bytesToInt(Arrays.copyOfRange(bytes, 18, 20)));
                //保质期
                mHdataBean.setBZQ(new String(Arrays.copyOfRange(bytes, 20, 28)));
                return mHdataBean;
            case 30:
                //驱动版本号
                mHdataBean.setQDBB(new String(Arrays.copyOfRange(bytes, 0, 12)));
                //经销商代码
                mHdataBean.setJXS(new String(Arrays.copyOfRange(bytes, 12, 15)) + bytes[15]);
                //墨水型号
                mHdataBean.setMSXH(new String(Arrays.copyOfRange(bytes, 16, 18)) +
                        LongIntToBytesUtils.bytesToInt(Arrays.copyOfRange(bytes, 18, 20)));
                //保质期
                mHdataBean.setBZQ(new String(Arrays.copyOfRange(bytes, 20, 28)));
                //流水号
                mHdataBean.setLSH(LongIntToBytesUtils.bytesToInt(Arrays.copyOfRange(bytes, 28, 30)));
                return mHdataBean;
            case 31:
                //驱动版本号
                mHdataBean.setQDBB(new String(Arrays.copyOfRange(bytes, 0, 12)));
                //经销商代码
                mHdataBean.setJXS(new String(Arrays.copyOfRange(bytes, 12, 15)) + bytes[15]);
                //墨水型号
                mHdataBean.setMSXH(new String(Arrays.copyOfRange(bytes, 16, 18)) +
                        LongIntToBytesUtils.bytesToInt(Arrays.copyOfRange(bytes, 18, 20)));
                //保质期
                mHdataBean.setBZQ(new String(Arrays.copyOfRange(bytes, 20, 28)));
                //流水号
                mHdataBean.setLSH(LongIntToBytesUtils.bytesToInt(Arrays.copyOfRange(bytes, 28, 30)));
                //墨盒余量
                mHdataBean.setMSYL(bytes[30]);
                return mHdataBean;
            default:
                //驱动版本号
                mHdataBean.setQDBB(new String(Arrays.copyOfRange(bytes, 0, 12)));
                //经销商代码
                mHdataBean.setJXS(new String(Arrays.copyOfRange(bytes, 12, 15)) + bytes[15]);
                //墨水型号
                mHdataBean.setMSXH(new String(Arrays.copyOfRange(bytes, 16, 18)) +
                        LongIntToBytesUtils.bytesToInt(Arrays.copyOfRange(bytes, 18, 20)));
                //保质期
                mHdataBean.setBZQ(new String(Arrays.copyOfRange(bytes, 20, 28)));
                //流水号
                mHdataBean.setLSH(LongIntToBytesUtils.bytesToInt(Arrays.copyOfRange(bytes, 28, 30)));
                //墨盒余量
                mHdataBean.setMSYL(bytes[30]);
                //生产日期
                mHdataBean.setSCRQ(new String(Arrays.copyOfRange(bytes, 31, 39)));
                return mHdataBean;
        }
    }
}
