package com.goockr.pmj.cons;

/**
 * @author yofreg
 * @time 2018/5/16 10:28
 * @desc 喷码机错误码
 */
public class PMJErrorCons {

    public static String errorReturn(byte data) {
        switch (data) {
            case 0:
                return null;
            case 1:
                return "非法类型码";
            case 2:
                return "校验码错误";
            case 3:
                return "数据正确但是无法执行";
            case 4:
                return "打印电压不在有效范围内";
            case 5:
                return "无法调节到指定打印电压";
            case 6:
                return "检测到物体同步信号，但是无足够的打印数据(至少1幅图片)";
            case 7:
                return "无法检测到速度同步信号";
            case 8:
                return "缓存不足";
            case 9:
                return "未识别到墨盒";
            case 10:
                return "RFID模块初始化失败";
            case 11:
                return "错误的打印次数(为0)";
            case 12:
                return "数据包大小错误";
            case 13:
                //"数据包包号错误"
                return null;
            case 14:
                return "没有足够的缓存数据(至少1幅图片)";
            default:
                return "";
        }
    }
}
