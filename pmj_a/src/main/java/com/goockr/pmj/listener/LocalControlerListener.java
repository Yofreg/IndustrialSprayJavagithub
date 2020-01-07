package com.goockr.pmj.listener;


import com.goockr.pmj.bean.ErrorBean;
import com.goockr.pmj.bean.MHdataBean;

/**
 * @author yofreg
 * @time 2018/5/17 9:33
 * @desc socket设备数据回复接口
 */
public interface LocalControlerListener {

    /**
     *  接口返回的符合协议报文的数据
     * @param bean
     */
    //void socketSendBean(PMJstorageBean bean);

    /**
     *  接口返回的一般数据
     * @param ebean
     */
    void socketSendString(ErrorBean ebean);

    /**
     *  启动打印的返回
     * @param p 喷头
     * @param datas 数据码
     */
    void socketSendQDDY(int p, byte[] datas);

    /**
     *  传输图片的返回
     * @param p 喷头
     * @param datas 数据码
     */
    void socketSendCSTP(int p, byte[] datas);

    /**
     *  检测图片剩余
     * @param a 0:不足10张 1:达到30张 -1:没有图片
     */
    void socketSendHCBZ(int a);

    /**
     *  控制打印的返回
     * @param p 喷头
     * @param datas 数据码
     */
    void socketSendKZDY(int p, byte[] datas);

    /**
     *  上传错误信息的返回
     * @param p 喷头
     * @param datas 错误信息
     */
    void socketSendSCXX(int p, String datas);

    /**
     *  查询墨盒信息的返回
     * @param p 喷头
     * @param datas 数据码
     */
    void socketSendCXXX(int p, MHdataBean datas);

    /**
     *  升级程序的返回
     * @param p 喷头
     * @param datas 数据码
     */
    void socketSendSJCX(int p, byte[] datas);

    /**
     *  应答打印的返回
     * @param p 喷头
     * @param datas 数据码 1ok -1错误
     */
    void socketSendYDDY(int p, byte[] datas);
}
