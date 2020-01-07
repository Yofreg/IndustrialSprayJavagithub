package com.goockr.pmj.bean;

/**
 * @author yofreg
 * @time 2018/6/21 14:11
 * @desc 存储翻转信息
 */
public class CJCopeBean {

    private boolean isSP; //翻转 true代表执行
    private boolean isCZ; //颠倒

    public CJCopeBean(boolean isSP, boolean isCZ) {
        this.isSP = isSP;
        this.isCZ = isCZ;
    }

    public boolean isSP() {
        return isSP;
    }

    public void setSP(boolean SP) {
        isSP = SP;
    }

    public boolean isCZ() {
        return isCZ;
    }

    public void setCZ(boolean CZ) {
        isCZ = CZ;
    }
}
