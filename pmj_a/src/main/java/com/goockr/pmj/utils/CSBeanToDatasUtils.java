package com.goockr.pmj.utils;

import com.goockr.pmj.bean.CSDataBean;

/**
 * @author yofreg
 * @time 2018/6/14 18:02
 * @desc 合并传递的启动参数
 */
public class CSBeanToDatasUtils {

    public static byte[] csBeanToDatas(CSDataBean bean) {

        byte[] a = LongIntToBytesUtils.intToBytesXDTY(bean.getDYYS(), 2);
        byte[] b = new byte[] {(byte) bean.getDYDY(), (byte) bean.getTBZ(), (byte) bean.getYDZ(), (byte) bean.getXPJX(),
                (byte) bean.getXPLS(), (byte) bean.getPZXZ(), (byte) bean.getCFFS(), (byte) bean.getKZFS()};
        byte[] c = LongIntToBytesUtils.intToBytesXDTY(bean.getZKZ(), 2);
        byte[] d = new byte[] {(byte) bean.getDYMS(), (byte) bean.getDYCS()};
        byte[] e = LongIntToBytesUtils.intToBytesXDTY(bean.getDYJG(), 2);
        byte[] f = new byte[] {(byte) bean.getJSXH()};
        byte[] g = LongIntToBytesUtils.intToBytesXDTY(bean.getCEBJ(), 2);
        byte[] h = new byte[] {(byte) bean.getFZDD()};

        return ByteMergerUtils.byteMerger(a, b, c, d, e, f, g, h);
    }
}
