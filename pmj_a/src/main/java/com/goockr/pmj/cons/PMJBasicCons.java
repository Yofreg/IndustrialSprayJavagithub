package com.goockr.pmj.cons;

/**
 * @author yofreg
 * @time 2018/5/14 18:45
 * @desc 喷码机协议相关配置
 */
public class PMJBasicCons {

    //设备ip端口
    public static String DATA_IP = "192.168.1.150";
    public static String DATA_IP2 = "192.168.1.151";
    public static String DATA_IP3 = "192.168.1.152";
    public static String DATA_IP4 = "192.168.1.153";
    public static String DATA_IP5 = "192.168.1.154";
    public static String DATA_IP6 = "192.168.1.155";
    public static String DATA_IP7 = "192.168.1.156";
    public static String DATA_IP8 = "192.168.1.157";
    public static int DATA_PORT = 8082;

    public static String TCP_TBT = "gckr"; //同步头

    //类型码
    public static String TCP_QDDY = "0x01"; //启动打印
    public static String TCP_QDDY_ERROR = "0x81"; //启动打印错误返回

    public static String TCP_TPCS = "0x02"; //图片传输
    public static String TCP_TPCS_ERROR = "0x82"; //图片传输错误返回

    public static String TCP_KZDY = "0x03"; //控制打印
    public static String TCP_KZDY_ERROR = "0x83"; //控制打印错误返回

    public static String TCP_CWXX = "0x04"; //上传错误信息
    public static String TCP_CWXX_ERROR = "0x84"; //上传错误信息错误返回

    public static String TCP_MHXX = "0x05"; //查询墨盒信息
    public static String TCP_MHXX_ERROR = "0x85"; //查询墨盒信息错误返回

    public static String TCP_SJCX = "0x06"; //升级程序
    public static String TCP_SJCX_ERROR = "0x86"; //升级程序错误返回

}
