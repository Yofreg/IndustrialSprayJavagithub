package com.goockr.pmj.bean;

/**
 * @author yofreg
 * @time 2018/6/14 17:51
 * @desc 用于存放传输参数
 */
public class CSDataBean {

    //private byte[] qdData = new byte[]{(byte) 10, 0, 22, 1, 1, 100, 8, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0};

    private int DYYS = 10; //打印延时
    private int DYDY = 90; //打印电压(8.5, 9.0, 10.0, 10.8)
    private int TBZ = 1; //同步值
    private int YDZ = 1; //应答值
    private int XPJX = 10; //闲喷间隙
    private int XPLS = 1; //闲喷列数
    private int PZXZ = 1; //喷嘴选择
    private int CFFS = 1; //喷嘴触发方式
    private int KZFS = 1; //字宽控制方式
    private int ZKZ = 3; //字宽值
    private int DYMS = 0; //重复打印模式
    private int DYCS = 0; //重复打印次数
    private int DYJG = 0; //重复打印间隔
    private int JSXH = 0; //产品结束信号
    private int CEBJ = 1000; //超额报警
    private int FZDD = 0; //翻转颠倒(0正常, 1翻转, 2颠倒, 3翻转颠倒)

    public CSDataBean(int KZFS, int ZKZ, int DYMS, int DYCS, int DYJG, int JSXH, int CEBJ, int FZDD) {
        this.KZFS = KZFS;
        this.ZKZ = ZKZ;
        this.DYMS = DYMS;
        this.DYCS = DYCS;
        this.DYJG = DYJG;
        this.JSXH = JSXH;
        this.CEBJ = CEBJ;
        this.FZDD = FZDD;
    }

    public CSDataBean(int DYYS, int DYDY, int TBZ, int YDZ, int XPJX, int XPLS, int PZXZ, int CFFS,
                      int KZFS, int ZKZ, int DYMS, int DYCS, int DYJG, int JSXH, int CEBJ, int FZDD) {
        this.DYYS = DYYS;
        this.DYDY = DYDY;
        this.TBZ = TBZ;
        this.YDZ = YDZ;
        this.XPJX = XPJX;
        this.XPLS = XPLS;
        this.PZXZ = PZXZ;
        this.CFFS = CFFS;
        this.KZFS = KZFS;
        this.ZKZ = ZKZ;
        this.DYMS = DYMS;
        this.DYCS = DYCS;
        this.DYJG = DYJG;
        this.JSXH = JSXH;
        this.CEBJ = CEBJ;
        this.FZDD = FZDD;
    }

    public CSDataBean() {
    }

    public int getFZDD() {
        return FZDD;
    }

    public void setFZDD(int FZDD) {
        this.FZDD = FZDD;
    }

    public int getCEBJ() {
        return CEBJ;
    }

    public void setCEBJ(int CEBJ) {
        this.CEBJ = CEBJ;
    }

    public int getDYYS() {
        return DYYS;
    }

    public void setDYYS(int DYYS) {
        this.DYYS = DYYS;
    }

    public int getDYDY() {
        return DYDY;
    }

    public void setDYDY(int DYDY) {
        this.DYDY = DYDY;
    }

    public int getTBZ() {
        return TBZ;
    }

    public void setTBZ(int TBZ) {
        this.TBZ = TBZ;
    }

    public int getYDZ() {
        return YDZ;
    }

    public void setYDZ(int YDZ) {
        this.YDZ = YDZ;
    }

    public int getXPJX() {
        return XPJX;
    }

    public void setXPJX(int XPJX) {
        this.XPJX = XPJX;
    }

    public int getXPLS() {
        return XPLS;
    }

    public void setXPLS(int XPLS) {
        this.XPLS = XPLS;
    }

    public int getPZXZ() {
        return PZXZ;
    }

    public void setPZXZ(int PZXZ) {
        this.PZXZ = PZXZ;
    }

    public int getCFFS() {
        return CFFS;
    }

    public void setCFFS(int CFFS) {
        this.CFFS = CFFS;
    }

    public int getKZFS() {
        return KZFS;
    }

    public void setKZFS(int KZFS) {
        this.KZFS = KZFS;
    }

    public int getZKZ() {
        return ZKZ;
    }

    public void setZKZ(int ZKZ) {
        this.ZKZ = ZKZ;
    }

    public int getDYMS() {
        return DYMS;
    }

    public void setDYMS(int DYMS) {
        this.DYMS = DYMS;
    }

    public int getDYCS() {
        return DYCS;
    }

    public void setDYCS(int DYCS) {
        this.DYCS = DYCS;
    }

    public int getDYJG() {
        return DYJG;
    }

    public void setDYJG(int DYJG) {
        this.DYJG = DYJG;
    }

    public int getJSXH() {
        return JSXH;
    }

    public void setJSXH(int JSXH) {
        this.JSXH = JSXH;
    }
}
