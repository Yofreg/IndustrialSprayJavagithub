package com.goockr.pmj.bean;

/**
 * @author yofreg
 * @time 2018/6/8 14:20
 * @desc 储存墨盒信息
 */
public class MHdataBean {

    private String QDBB; //驱动版本号
    private String JXS; //经销商代码
    private String MSXH; //墨水型号
    private String BZQ; //保质期
    private int LSH; //流水号
    private int MSYL; //墨水余量
    private String SCRQ; //生产日期

    @Override
    public String toString() {
        return "MHdataBean{" +
                "QDBB='" + QDBB + '\'' +
                ", JXS='" + JXS + '\'' +
                ", MSXH='" + MSXH + '\'' +
                ", BZQ='" + BZQ + '\'' +
                ", LSH=" + LSH +
                ", MSYL=" + MSYL +
                ", SCRQ='" + SCRQ + '\'' +
                '}';
    }

    public int getMSYL() {
        return MSYL;
    }

    public void setMSYL(int MSYL) {
        this.MSYL = MSYL;
    }

    public String getSCRQ() {
        return SCRQ;
    }

    public void setSCRQ(String SCRQ) {
        this.SCRQ = SCRQ;
    }

    public String getQDBB() {
        return QDBB;
    }

    public void setQDBB(String QDBB) {
        this.QDBB = QDBB;
    }

    public String getJXS() {
        return JXS;
    }

    public void setJXS(String JXS) {
        this.JXS = JXS;
    }

    public String getMSXH() {
        return MSXH;
    }

    public void setMSXH(String MSXH) {
        this.MSXH = MSXH;
    }

    public String getBZQ() {
        return BZQ;
    }

    public void setBZQ(String BZQ) {
        this.BZQ = BZQ;
    }

    public int getLSH() {
        return LSH;
    }

    public void setLSH(int LSH) {
        this.LSH = LSH;
    }
}
