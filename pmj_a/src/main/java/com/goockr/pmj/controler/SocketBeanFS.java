package com.goockr.pmj.controler;

import com.goockr.pmj.bean.ErrorBean;
import com.goockr.pmj.bean.PMJstorageBean;
import com.goockr.pmj.utils.IntentUtils;

/**
 * @author yofreg
 * @time 2018/6/6 11:01
 * @desc 存储发送的相关bean
 */
public class SocketBeanFS {

    /**
     *  一般数据发送
     * @param data 数据
     * @param p 喷头
     * @param w 暂无用
     */
    public static void ebeanFS(String data, int p, int w) {
        ErrorBean ebean = new ErrorBean();
        ebean.setData(data);
        ebean.setP(p);
        ebean.setW(w);
        IntentUtils.sendBroadcast2(false, ebean);
    }

    /**
     *  设备发送过来的符合协议的数据
     * @param isLXM 类型码
     * @param isSJM 数据码
     * @param p 喷头
     */
    public static void beanFS(byte[] isLXM, byte[] isSJM, int p) {
        PMJstorageBean bean = new PMJstorageBean();
        bean.setlXM(isLXM);
        bean.setsJM(isSJM);
        bean.setP(p);
        IntentUtils.sendBroadcast2(true, bean);
    }
}
