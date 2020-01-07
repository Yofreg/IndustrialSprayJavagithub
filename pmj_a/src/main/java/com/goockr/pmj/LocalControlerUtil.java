package com.goockr.pmj;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;

import com.goockr.pmj.bean.BMPToSJBean;
import com.goockr.pmj.bean.BitmapAndCSBean;
import com.goockr.pmj.bean.CJCopeBean;
import com.goockr.pmj.bean.CSDataBean;
import com.goockr.pmj.bean.ErrorBean;
import com.goockr.pmj.bean.MHdataBean;
import com.goockr.pmj.bean.PMJstorageBean;
import com.goockr.pmj.cons.Controlflag;
import com.goockr.pmj.cons.PMJBasicCons;
import com.goockr.pmj.cons.PMJErrorCons;
import com.goockr.pmj.controler.LocalControler;
import com.goockr.pmj.controler.LocalControler2;
import com.goockr.pmj.controler.LocalControler3;
import com.goockr.pmj.controler.LocalControler4;
import com.goockr.pmj.controler.LocalControler5;
import com.goockr.pmj.controler.LocalControler6;
import com.goockr.pmj.controler.LocalControler7;
import com.goockr.pmj.controler.LocalControler8;
import com.goockr.pmj.controler.SocketBeanFS;
import com.goockr.pmj.listener.LocalControlerListener;
import com.goockr.pmj.utils.BeanHCUtils;
import com.goockr.pmj.utils.BmpToDZUtils;
import com.goockr.pmj.utils.BmpUtil;
import com.goockr.pmj.utils.ByteMergerUtils;
import com.goockr.pmj.utils.BytesToListBytesUtils;
import com.goockr.pmj.utils.CSBeanToDatasUtils;
import com.goockr.pmj.utils.CaCheJCUtils;
import com.goockr.pmj.utils.LongIntToBytesUtils;
import com.goockr.pmj.utils.MLog;
import com.goockr.pmj.utils.PingUtils;
import com.goockr.pmj.view.ViewToBitmap;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yofreg
 * @time 2018/5/29 14:59
 * @desc 开启socket通讯的中间类
 */
public class LocalControlerUtil {

    private static LocalControlerUtil utils;

    private static LocalControler tcpClient = null; //tcp客户端
    private static LocalControler2 tcpClient2 = null; //tcp客户端
    private static LocalControler3 tcpClient3 = null; //tcp客户端
    private static LocalControler4 tcpClient4 = null; //tcp客户端
    private static LocalControler5 tcpClient5 = null; //tcp客户端
    private static LocalControler6 tcpClient6 = null; //tcp客户端
    private static LocalControler7 tcpClient7 = null; //tcp客户端
    private static LocalControler8 tcpClient8 = null; //tcp客户端

    //private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver(); //接收数据的广播
    //线程池
    private ExecutorService exec = Executors.newCachedThreadPool();

    public static Context mContext;
    private LocalControlerListener mListener; //回调接口

    //private boolean isHf = true; //判断设备是否已经回应
    private ArrayList<byte[]> datas; //得到每个喷头要传递的数据 data.size就是需要的喷头数
    private ArrayList<byte[]> datas2; //得到每个喷头要传递的数据 data.size就是需要的喷头数
    private ArrayList<byte[]> datas3; //得到每个喷头要传递的数据 data.size就是需要的喷头数
    private ArrayList<byte[]> datas4; //得到每个喷头要传递的数据 data.size就是需要的喷头数
    private ArrayList<byte[]> datas5; //得到每个喷头要传递的数据 data.size就是需要的喷头数
    private ArrayList<byte[]> datas6; //得到每个喷头要传递的数据 data.size就是需要的喷头数
    private ArrayList<byte[]> datas7; //得到每个喷头要传递的数据 data.size就是需要的喷头数
    private ArrayList<byte[]> datas8; //得到每个喷头要传递的数据 data.size就是需要的喷头数

    private int bmph; //高度 也是打印时的行
    private int bmph2; //高度 也是打印时的行
    private int bmph3; //高度 也是打印时的行
    private int bmph4; //高度 也是打印时的行
    private int bmph5; //高度 也是打印时的行
    private int bmph6; //高度 也是打印时的行
    private int bmph7; //高度 也是打印时的行
    private int bmph8; //高度 也是打印时的行

    private int OHF = 0; //数据返回次数 1号喷头
    private int THF = 0; //数据返回次数 2号喷头
    private int ThHF = 0; //数据返回次数 3号喷头
    private int FHF = 0; //数据返回次数 4号喷头
    private int FiHF = 0; //数据返回次数 5号喷头
    private int SHF = 0; //数据返回次数 6号喷头
    private int SeHF = 0; //数据返回次数 7号喷头
    private int EHF = 0; //数据返回次数 8号喷头

    private boolean OFlag = true; //判断是单包还是多包 true单包 1号喷头
    private boolean TFlag = true; //判断是单包还是多包 true单包 2号喷头
    private boolean ThFlag = true; //判断是单包还是多包 true单包 3号喷头
    private boolean FFlag = true; //判断是单包还是多包 true单包 4号喷头
    private boolean FiFlag = true; //判断是单包还是多包 true单包 5号喷头
    private boolean SFlag = true; //判断是单包还是多包 true单包 6号喷头
    private boolean SeFlag = true; //判断是单包还是多包 true单包 7号喷头
    private boolean EFlag = true; //判断是单包还是多包 true单包 8号喷头

    private int OCD = 0; //数据的长度 1号喷头
    private int TCD = 0; //数据的长度 2号喷头
    private int ThCD = 0; //数据的长度 3号喷头
    private int FCD = 0; //数据的长度 4号喷头
    private int FiCD = 0; //数据的长度 5号喷头
    private int SCD = 0; //数据的长度 6号喷头
    private int SeCD = 0; //数据的长度 7号喷头
    private int ECD = 0; //数据的长度 8号喷头

    private byte[] byteh; //实际打印的行数 (打印的行对应显示图片的列)
    private byte[] byteh2; //实际打印的行数 (打印的行对应显示图片的列)
    private byte[] byteh3; //实际打印的行数 (打印的行对应显示图片的列)
    private byte[] byteh4; //实际打印的行数 (打印的行对应显示图片的列)
    private byte[] byteh5; //实际打印的行数 (打印的行对应显示图片的列)
    private byte[] byteh6; //实际打印的行数 (打印的行对应显示图片的列)
    private byte[] byteh7; //实际打印的行数 (打印的行对应显示图片的列)
    private byte[] byteh8; //实际打印的行数 (打印的行对应显示图片的列)

    private byte[] bytech; //打印次数
    private byte[] bytech2; //打印次数
    private byte[] bytech3; //打印次数
    private byte[] bytech4; //打印次数
    private byte[] bytech5; //打印次数
    private byte[] bytech6; //打印次数
    private byte[] bytech7; //打印次数
    private byte[] bytech8; //打印次数

    private boolean OQD = false; //判断设备是否已经启动打印 1号喷头
    private boolean TQD = false; //判断设备是否已经启动打印 2号喷头
    private boolean ThQD = false; //判断设备是否已经启动打印 3号喷头
    private boolean FQD = false; //判断设备是否已经启动打印 4号喷头
    private boolean FiQD = false; //判断设备是否已经启动打印 5号喷头
    private boolean SQD = false; //判断设备是否已经启动打印 6号喷头
    private boolean SeQD = false; //判断设备是否已经启动打印 7号喷头
    private boolean EQD = false; //判断设备是否已经启动打印 8号喷头

    private boolean OCS = true; //判断是否可以传输 1号喷头
    private boolean TCS = true; //判断是否可以传输 2号喷头
    private boolean ThCS = true; //判断是否可以传输 3号喷头
    private boolean FCS = true; //判断是否可以传输 4号喷头
    private boolean FiCS = true; //判断是否可以传输 5号喷头
    private boolean SCS = true; //判断是否可以传输 6号喷头
    private boolean SeCS = true; //判断是否可以传输 7号喷头
    private boolean ECS = true; //判断是否可以传输 8号喷头

    private boolean isTXOK = true; //判断设备是否故障 有一个喷头不行就是故障 true代表正常
    private boolean isSH = false; //判断多个喷头是否全部没有损坏 true代表有损坏

    private byte[] qdData = new byte[]{(byte) 10, 0, 22, 1, 1, 100, 8, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0, -24, 3, 0}; //喷头启动数据
    private byte[] qdData2 = new byte[]{(byte) 10, 0, 22, 1, 1, 100, 8, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0, -24, 3, 0};
    private byte[] qdData3 = new byte[]{(byte) 10, 0, 22, 1, 1, 100, 8, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0, -24, 3, 0};
    private byte[] qdData4 = new byte[]{(byte) 10, 0, 22, 1, 1, 100, 8, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0, -24, 3, 0};
    private byte[] qdData5 = new byte[]{(byte) 10, 0, 22, 1, 1, 100, 8, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0, -24, 3, 0};
    private byte[] qdData6 = new byte[]{(byte) 10, 0, 22, 1, 1, 100, 8, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0, -24, 3, 0};
    private byte[] qdData7 = new byte[]{(byte) 10, 0, 22, 1, 1, 100, 8, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0, -24, 3, 0};
    private byte[] qdData8 = new byte[]{(byte) 10, 0, 22, 1, 1, 100, 8, 0, 1, 1, 3, 0, 0, 0, 0, 0, 0, -24, 3, 0};

    //定时检测网络通讯
    private Timer timer; //检测线路ping
    private TimerTask task;
    private Timer timer2; //定时心跳包
    private TimerTask task2;

    //连接的ip
    private String[] mIp;
    //开启的喷头数
    private int mX;

    private List<BitmapAndCSBean> mList = new ArrayList<>(); //用于存储多余相片的数据
    private List<BitmapAndCSBean> mList2 = new ArrayList<>(); //用于存储多余相片的数据
    private List<BitmapAndCSBean> mList3 = new ArrayList<>(); //用于存储多余相片的数据
    private List<BitmapAndCSBean> mList4 = new ArrayList<>(); //用于存储多余相片的数据
    private List<BitmapAndCSBean> mList5 = new ArrayList<>(); //用于存储多余相片的数据
    private List<BitmapAndCSBean> mList6 = new ArrayList<>(); //用于存储多余相片的数据
    private List<BitmapAndCSBean> mList7 = new ArrayList<>(); //用于存储多余相片的数据
    private List<BitmapAndCSBean> mList8 = new ArrayList<>(); //用于存储多余相片的数据

    private List<Integer> kzList = new ArrayList(); //控制打印回复一致性
    private List tcpOK = new ArrayList(); //连接回复一致性
    private List<PMJstorageBean> qdList = new ArrayList(); //启动打印回复一致性

    private boolean isZX = false; //断开连接时方法不执行

    private long[] himtQD = new long[2]; //操作频繁
    private long[] himtKZ = new long[2];
    private long[] cl = new long[2]; //重连过于频繁

    private boolean isOWHC = false; //判断下位机缓存不足 新版改为分别测试缓存
    private boolean isTWHC = false;
    private boolean isThWHC = false;
    private boolean isFWHC = false;
    private boolean isFiWHC = false;
    private boolean isSWHC = false;
    private boolean isSeWHC = false;
    private boolean isEWHC = false;

    private boolean isYD = false; //判断是否开始打印,开始打印后传输进来的图片都不直接传输给下位机,等应答信号去传输

    private boolean iscloce = false; //用于检测断线重连 多喷头故障不用多次重连

    private List<CJCopeBean> mCJCopeBeanList = new ArrayList<>(); //存储翻转信息对象

    private boolean iscs; //测试状态 true表示是测试

    private boolean isODY = false; //机子有两种状态 1.准备状态 2.打印状态
    private boolean isTDY = false;
    private boolean isThDY = false;
    private boolean isFDY = false;
    private boolean isFiDY = false;
    private boolean isSDY = false;
    private boolean isSeDY = false;
    private boolean isEDY = false;

    private byte[] OTPXH; //发送图片序号
    private byte[] TTPXH;
    private byte[] ThTPXH;
    private byte[] FTPXH;
    private byte[] FiTPXH;
    private byte[] STPXH;
    private byte[] SeTPXH;
    private byte[] ETPXH;

    private byte[] HCBJ = new byte[] {-24, 3}; //用于缓存报警默认9000

    public void setHCBJ(int HCBJ) {
        this.HCBJ = LongIntToBytesUtils.intToBytesXDTY(HCBJ, 2);
    }

    //是否为测试状态
    public void isCESI(boolean iscs) {
        this.iscs = iscs;
    }

    /**
     *  获取传输图片需要的是否旋转信息
     * @param list
     */
    public void setDYXZS(List<CJCopeBean> list) {

        if (!isZX) {
            SocketBeanFS.ebeanFS("机器故障,请先重启线路", 0, 0);
            return;
        }
        mCJCopeBeanList = list;
        /*MLog.v("mCJCopeBeanList" + mCJCopeBeanList.size() + mCJCopeBeanList.get(0).isCZ() + ":" + mCJCopeBeanList.get(0).isSP() + ";"
                + mCJCopeBeanList.get(1).isCZ() + ":" + mCJCopeBeanList.get(1).isSP());*/
    }

    /**
     *  //todo 获取打印的相关参数
     * @param list
     */
    public void setQdDatas(List<CSDataBean> list) {

        if (!isZX) {
            SocketBeanFS.ebeanFS("机器故障,请先重启线路", 0, 0);
            return;
        }

        qdData = CSBeanToDatasUtils.csBeanToDatas(list.get(0));
        qdData2 = CSBeanToDatasUtils.csBeanToDatas(list.get(1));
        qdData3 = CSBeanToDatasUtils.csBeanToDatas(list.get(2));
        qdData4 = CSBeanToDatasUtils.csBeanToDatas(list.get(3));
        qdData5 = CSBeanToDatasUtils.csBeanToDatas(list.get(4));
        qdData6 = CSBeanToDatasUtils.csBeanToDatas(list.get(5));
        qdData7 = CSBeanToDatasUtils.csBeanToDatas(list.get(6));
        qdData8 = CSBeanToDatasUtils.csBeanToDatas(list.get(7));

        //MLog.v( "qd1" + Arrays.toString(qdData) + "\n" + "qd2" + Arrays.toString(qdData2) + "\n" + "qd3" + Arrays.toString(qdData3));
    }

    /**
     *  //todo 重新配置参数
     * @param bean 参数
     * @param p 对应的喷头
     */
    public void setDSZData(CSDataBean bean, int p) {
        if (!isZX) {
            SocketBeanFS.ebeanFS("机器故障,请先重启线路", 0, 0);
            return;
        }

        switch (p) {
            case 1:
                qdData = CSBeanToDatasUtils.csBeanToDatas(bean);
                break;
            case 2:
                qdData2 = CSBeanToDatasUtils.csBeanToDatas(bean);
                break;
            case 3:
                qdData3 = CSBeanToDatasUtils.csBeanToDatas(bean);
                break;
            case 4:
                qdData4 = CSBeanToDatasUtils.csBeanToDatas(bean);
                break;
            case 5:
                qdData5 = CSBeanToDatasUtils.csBeanToDatas(bean);
                break;
            case 6:
                qdData6 = CSBeanToDatasUtils.csBeanToDatas(bean);
                break;
            case 7:
                qdData7 = CSBeanToDatasUtils.csBeanToDatas(bean);
                break;
            case 8:
                qdData8 = CSBeanToDatasUtils.csBeanToDatas(bean);
                break;
        }
    }

    /**
     *  获取打印的相关参数
     * @param csDataBean 数据码
     * @param Y 喷头之间的延时差
     */
    public void setQdData(CSDataBean csDataBean, int Y) {

        if (!isZX) {
            SocketBeanFS.ebeanFS("机器故障,请先重启线路", 0, 0);
            return;
        }

        qdData = CSBeanToDatasUtils.csBeanToDatas(csDataBean);
        qdData2 = CSBeanToDatasUtils.csBeanToDatas(csDataBean);
        qdData3 = CSBeanToDatasUtils.csBeanToDatas(csDataBean);
        qdData4 = CSBeanToDatasUtils.csBeanToDatas(csDataBean);
        qdData5 = CSBeanToDatasUtils.csBeanToDatas(csDataBean);
        qdData6 = CSBeanToDatasUtils.csBeanToDatas(csDataBean);
        qdData7 = CSBeanToDatasUtils.csBeanToDatas(csDataBean);
        qdData8 = CSBeanToDatasUtils.csBeanToDatas(csDataBean);

        byte[] tmp = new byte[2];
        tmp[0] = qdData[0];
        tmp[1] = qdData[1];

        int a = LongIntToBytesUtils.bytesToInt(tmp);

        if (Y > 0) {
            a += Y;
            qddata(a, qdData2);
            a += Y;
            qddata(a, qdData3);
            a += Y;
            qddata(a, qdData4);
            a += Y;
            qddata(a, qdData5);
            a += Y;
            qddata(a, qdData6);
            a += Y;
            qddata(a, qdData7);
            a += Y;
            qddata(a, qdData8);
        } else {
            switch (mX) {
                case 8:
                    a -= Y;
                    qddata(a, qdData7);
                case 7:
                    a -= Y;
                    qddata(a, qdData6);
                case 6:
                    a -= Y;
                    qddata(a, qdData5);
                case 5:
                    a -= Y;
                    qddata(a, qdData4);
                case 4:
                    a -= Y;
                    qddata(a, qdData3);
                case 3:
                    a -= Y;
                    qddata(a, qdData2);
                case 2:
                    a -= Y;
                    qddata(a, qdData);
                    break;
            }
        }
    }

    //设置喷头延时
    private void qddata(int a, byte[] data) {
        byte[] tmp2 = LongIntToBytesUtils.intToBytesXDTY(a, 2);
        data[0] = tmp2[0];
        data[1] = tmp2[1];
    }

    //初始化中间类时把通讯开启
    private LocalControlerUtil(Activity context, final LocalControlerListener listener, String[] ip2, int x) {
        mContext = context;
        mListener = listener;
        mIp = ip2;
        mX = x;
        //开启广播
        //bindReceiver();
        if (mIp.length < 1 || mIp.length > 8 || mX < 1 || mX > 8) {
            SocketBeanFS.ebeanFS("开启喷头参数不正确,请检测重连", 0, 0);
            return;
        }

        tcpCL();
    }

    //tcp通讯检测开启
    private void tcpCL() {
        if (mIp.length < mX) {
            mX = mIp.length;
            SocketBeanFS.ebeanFS("检测到只有" + mIp.length + "个喷头,已开启", 0, 0);
        }
        if (mX == 0) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        iscloce = false;
                        isZX = true;
                        isTXOK = PingUtils.ping(PMJBasicCons.DATA_IP);
                        if (!isTXOK) {
                            SocketBeanFS.ebeanFS("ping不通", 1, 0);
                            SocketBeanFS.ebeanFS("机器故障",  0, 0);
                        } else {

                            tcpClient = new LocalControler();
                            exec.execute(tcpClient);
                            //定时检查通讯
                            timer = new Timer();
                            task = new TimerTask() {
                                @Override
                                public void run() {
                                    try {
                                        isTXOK = PingUtils.ping(PMJBasicCons.DATA_IP);
                                        if (!isTXOK) {
                                            SocketBeanFS.ebeanFS("ping不通", 1, 0);
                                            SocketBeanFS.ebeanFS("机器故障",  0, 0);
                                        }
                                    } catch (IOException | InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };
                            timer.schedule(task, 20000, 20000);
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            return;
        }

        exec.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    iscloce = false;
                    isZX = true;
                    StringBuilder ping = new StringBuilder();
                    for (int i = 0; i < mX; i++) {
                        isTXOK = PingUtils.ping(mIp[i]);
                        /*if (!isTXOK) {
                            isTXOK = PingUtils.ping(mIp[i]);
                        }*/
                        if (!isTXOK) {
                            //SocketBeanFS.ebeanFS("ping不通",  i + 1, 0);
                            ping.append(i + 1);
                            isSH = true;
                        }
                    }
                    if (!isSH){ //判断多个喷头全部没有损坏

                        tcpclientCJ(mIp, mX);
                        timer = new Timer();
                        task = new TimerTask() {
                            @Override
                            public void run() {
                                //tcpclientJC(mIp);
                                try {
                                    isSH = false;
                                    StringBuilder ping2 = new StringBuilder();
                                    for (int i = 0; i < mX; i++) {
                                        isTXOK = PingUtils.ping(mIp[i]);
                                        if (!isTXOK) {
                                            //SocketBeanFS.ebeanFS("ping不通", i + 1, 0);
                                            ping2.append(i + 1);
                                            isSH = true;
                                        }
                                    }
                                    if (isSH) {
                                        SocketBeanFS.ebeanFS("ping不通",  0, Integer.parseInt(ping2.toString()));
                                        SocketBeanFS.ebeanFS("机器故障",  Integer.parseInt(ping2.toString()), 0);
                                    }
                                } catch (IOException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        timer.schedule(task, 10000, 10000);
                        timer2 = new Timer();
                        task2 = new TimerTask() {
                            @Override
                            public void run() {
                                sendSCXX(new byte[] {0}, 0);
                            }
                        };
                        timer2.schedule(task2, 1000, 1000);
                    } else {
                        SocketBeanFS.ebeanFS("ping不通",  0, Integer.parseInt(ping.toString()));
                        SocketBeanFS.ebeanFS("机器故障",  Integer.parseInt(ping.toString()), 0);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //用于创建tcpclient
    private void tcpclientCJ(String[] ip, int mX) {
        switch (mX) {
            case 8:
                PMJBasicCons.DATA_IP8 = ip[7];
                tcpClient8 = new LocalControler8();
                exec.execute(tcpClient8);
            case 7:
                PMJBasicCons.DATA_IP7 = ip[6];
                tcpClient7 = new LocalControler7();
                exec.execute(tcpClient7);
            case 6:
                PMJBasicCons.DATA_IP6 = ip[5];
                tcpClient6 = new LocalControler6();
                exec.execute(tcpClient6);
            case 5:
                PMJBasicCons.DATA_IP5 = ip[4];
                tcpClient5 = new LocalControler5();
                exec.execute(tcpClient5);
            case 4:
                PMJBasicCons.DATA_IP4 = ip[3];
                tcpClient4 = new LocalControler4();
                exec.execute(tcpClient4);
            case 3:
                PMJBasicCons.DATA_IP3 = ip[2];
                tcpClient3 = new LocalControler3();
                exec.execute(tcpClient3);
            case 2:
                PMJBasicCons.DATA_IP2 = ip[1];
                tcpClient2 = new LocalControler2();
                exec.execute(tcpClient2);
            case 1:
                PMJBasicCons.DATA_IP = ip[0];
                tcpClient = new LocalControler();
                exec.execute(tcpClient);
                break;
        }
    }

    /**
     *  单例创建
     * @param context 传入activity
     * @param listener 传入的数据回调接口
     * @return LocalControlerUtil对象
     */
    public static LocalControlerUtil getInstance(Activity context, LocalControlerListener listener, String[] ip, int x) {

        if (utils == null) {
            synchronized (LocalControlerUtil.class) {
                if (utils == null) {
                    utils = new LocalControlerUtil(context, listener, ip, x);
                }
            }
        }
        return utils;
    }

    //检测开放类是否创建
    public static LocalControlerUtil getInstance() {
        if (utils != null) {
            return utils;
        } else {
            return null;
        }
    }

    /**
     * 发送数据的方法
     * @param type 类型码
     * @param bytes 数据码
     */
    private void send(final byte type, final byte[] bytes) {

        exec.execute(new Runnable() {
            @Override
            public void run() {

                if (tcpClient != null) {
                    tcpClient.send(type, bytes);
                }
                if (tcpClient2 != null) {
                    tcpClient2.send(type, bytes);
                }
                if (tcpClient3 != null) {
                    tcpClient3.send(type, bytes);
                }
                if (tcpClient4 != null) {
                    tcpClient4.send(type, bytes);
                }
                if (tcpClient5 != null) {
                    tcpClient5.send(type, bytes);
                }
                if (tcpClient6 != null) {
                    tcpClient6.send(type, bytes);
                }
                if (tcpClient7 != null) {
                    tcpClient7.send(type, bytes);
                }
                if (tcpClient8 != null) {
                    tcpClient8.send(type, bytes);
                }
            }
        });
    }

    //private lis

    /**
     *  //todo 重连的方法
     */
    public void sendCL() {

        System.arraycopy(cl, 1, cl, 0, cl.length - 1);
        cl[cl.length - 1] = System.currentTimeMillis();
        if (cl[himtQD.length - 1] - cl[0] <= 1000) {
            return;
        }

        close(true);
    }

    /**
     *  //todo 启动打印的方法
     */
    public void sendQDDY() {

        if (!isZX) {
            SocketBeanFS.ebeanFS("机器故障,请先重启线路", 0, 0);
            return;
        }

        System.arraycopy(himtQD, 1, himtQD, 0, himtQD.length - 1);
        himtQD[himtQD.length - 1] = System.currentTimeMillis();
        if (himtQD[himtQD.length - 1] - himtQD[0] <= 1000) {
            SocketBeanFS.ebeanFS("服务器繁忙了,请稍后再试1", 0, 0);
            return;
        }

        exec.execute(new Runnable() {
            @Override
            public void run() {
                switch (mX) {
                    case 8:
                        if (tcpClient8 != null) {
                            tcpClient8.sendQDDY(qdData8);
                        }
                    case 7:
                        if (tcpClient7 != null) {
                            tcpClient7.sendQDDY(qdData7);
                        }
                    case 6:
                        if (tcpClient6 != null) {
                            tcpClient6.sendQDDY(qdData6);
                        }
                    case 5:
                        if (tcpClient5 != null) {
                            tcpClient5.sendQDDY(qdData5);
                        }
                    case 4:
                        if (tcpClient4 != null) {
                            tcpClient4.sendQDDY(qdData4);
                        }
                    case 3:
                        if (tcpClient3 != null) {
                            tcpClient3.sendQDDY(qdData3);
                        }
                    case 2:
                        if (tcpClient2 != null) {
                            tcpClient2.sendQDDY(qdData2);
                        }
                    case 1:
                        if (tcpClient != null) {
                            tcpClient.sendQDDY(qdData);
                        }
                        break;
                }
            }
        });
    }

    /**
     *  //todo 重新设置参数
     * @param p 第几个喷头执行
     */
    public void sendSZCS(int p) {

        if (!isZX) {
            SocketBeanFS.ebeanFS("机器故障,请先重启线路", 0, 0);
            return;
        }

        switch (p) {
            case 1:
                if (tcpClient != null) {
                    tcpClient.sendSZCS(qdData);
                }
                break;
            case 2:
                if (tcpClient2 != null) {
                    tcpClient2.sendSZCS(qdData2);
                }
                break;
            case 3:
                if (tcpClient3 != null) {
                    tcpClient3.sendSZCS(qdData3);
                }
                break;
            case 4:
                if (tcpClient4 != null) {
                    tcpClient4.sendSZCS(qdData4);
                }
                break;
            case 5:
                if (tcpClient5 != null) {
                    tcpClient5.sendSZCS(qdData5);
                }
                break;
            case 6:
                if (tcpClient6 != null) {
                    tcpClient6.sendSZCS(qdData6);
                }
                break;
            case 7:
                if (tcpClient7 != null) {
                    tcpClient7.sendSZCS(qdData7);
                }
                break;
            case 8:
                if (tcpClient8 != null) {
                    tcpClient8.sendSZCS(qdData8);
                }
                break;
        }
    }

    private int TPXH = 0; //发送图片序号

    /**
     *  //todo 传输图片的方法
     * @param viewBitmap bitmap图片
     * @param cishu 打印次数
     */
    public void sendCSTP(Bitmap viewBitmap, int cishu) {

        MLog.v("绘图 = ");

        if (!isZX) {
            SocketBeanFS.ebeanFS("机器已故障,请先重启线路", 0, 0);
            return;
        }

        synchronized (LocalControlerUtil.this) {
            BitmapAndCSBean bitmapAndCSBean = new BitmapAndCSBean();
            //每次进来图片先存储起来
            byte[] aaa =  BmpUtil.changeToMonochromeBitmap(ViewToBitmap.rotateBitmap(viewBitmap, false, false));
            //byte[] aaa =  BmpUtil.changeToMonochromeBitmap(ViewToBitmap.rotateBitmap2(viewBitmap, mCJCopeBeanList, mX));

            bitmapAndCSBean.setDatas(aaa);
            bitmapAndCSBean.setCS(cishu);
            bitmapAndCSBean.setTPXH(++TPXH);

            switch (mX) {
                case 8:
                    mList8.add(bitmapAndCSBean);
                case 7:
                    mList7.add(bitmapAndCSBean);
                case 6:
                    mList6.add(bitmapAndCSBean);
                case 5:
                    mList5.add(bitmapAndCSBean);
                case 4:
                    mList4.add(bitmapAndCSBean);
                case 3:
                    mList3.add(bitmapAndCSBean);
                case 2:
                    mList2.add(bitmapAndCSBean);
                case 1:
                    mList.add(bitmapAndCSBean);
                    break;
            }
            //检测图片存储量过大
            if (CaCheJCUtils.cacheB(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                mListener.socketSendHCBZ(1);
            }
        }

            /*if (!iscs) {
                return;
            }*/

            //MLog.v("1111" + OCS + TCS + ThCS + FCS);
        if (!isYD) {
            sendTP(0);
        }
    }

    //实际发送图片的方法
    private void sendTP(int p) {
        switch (p) {
            case 0:
                if (!isOWHC && !isODY && OCS && mList.size() != 0) {
                    onecstp(mList);
                }
                if (!isTWHC && !isTDY && TCS && mX > 1 && mList2.size() != 0) {
                    twocstp(mList2);
                }
                if (!isThWHC && !isThDY && ThCS && mX > 2 && mList3.size() != 0) {
                    threecstp(mList3);
                }
                if (!isFWHC && !isFDY && FCS && mX > 3 && mList4.size() != 0) {
                    fourcstp(mList4);
                }
                if (!isFiWHC && !isFiDY && FiCS && mX > 4 && mList5.size() != 0) {
                    fivecstp(mList5);
                }
                if (!isSWHC && !isSDY && SCS && mX > 5 && mList6.size() != 0) {
                    sixcstp(mList6);
                }
                if (!isSeWHC && !isSeDY && SeCS && mX > 6 && mList7.size() != 0) {
                    sevencstp(mList7);
                }
                if (!isEWHC && !isEDY && ECS && mX > 7 && mList8.size() != 0) {
                    eightcstp(mList8);
                }
                break;
            case 1:
                if (!isOWHC && !isODY && OCS && mList.size() != 0) {
                    onecstp(mList);
                }
                break;
            case 2:
                if (!isTWHC && !isTDY && TCS && mX > 1 && mList2.size() != 0) {
                    twocstp(mList2);
                }
                break;
            case 3:
                if (!isThWHC && !isThDY && ThCS && mX > 2 && mList3.size() != 0) {
                    threecstp(mList3);
                }
                break;
            case 4:
                if (!isFWHC && !isFDY && FCS && mX > 3 && mList4.size() != 0) {
                    fourcstp(mList4);
                }
                break;
            case 5:
                if (!isFiWHC && !isFiDY && FiCS && mX > 4 && mList5.size() != 0) {
                    fivecstp(mList5);
                }
                break;
            case 6:
                if (!isSWHC && !isSDY && SCS && mX > 5 && mList6.size() != 0) {
                    sixcstp(mList6);
                }
                break;
            case 7:
                if (!isSeWHC && !isSeDY && SeCS && mX > 6 && mList7.size() != 0) {
                    sevencstp(mList7);
                }
                break;
            case 8:
                if (!isEWHC && !isEDY && ECS && mX > 7 && mList8.size() != 0) {
                    eightcstp(mList8);
                }
                break;
        }
    }

    //传输完图片缓存清除
    private void lockdata(List mlist) {
        synchronized (LocalControlerUtil.this) {
            mlist.remove(0);
        }
    }

    //1号喷头数据处理
    private void onecstp(final List<BitmapAndCSBean> mLists) {
        OCS = false;
        exec.execute(new Runnable() {
            @Override
            public void run() {

                //SystemClock.sleep(2000);
                if (mLists.size() == 0) {
                    return;
                }
                BMPToSJBean bean  = BmpToDZUtils.button1_Click2(mLists.get(0).getDatas(), false); //bmp宽高数据
                byte[] bb = bean.getData(); //位图数据
                int bmpw2 = bean.getWidth(); //宽
                bmph = bean.getHeight();
                int w = bmpw2 / 48; //几个喷头
                int y = bmpw2 % 48;

                if (y != 0) {
                    w = w + 1; //得到最后需要的喷头打印数
                }
                datas = (ArrayList<byte[]>) BytesToListBytesUtils.bytesToList(bb, w, bmph); //得到每个喷头要传递的数据 data.size就是需要的喷头数
                //datas = ArraysToNewUtils.aa(datas.get(datas.size() - 1));
                byteh = LongIntToBytesUtils.intToBytesXDTY(bmph, 4); //实际打印的行数 (打印的行对应显示图片的列)
                bytech = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getCS(), 2); //次数
                OTPXH = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getTPXH(), 4); //图片序号
                tcpclientsendcstp();
            }
        });
    }

    //2号喷头数据处理
    private void twocstp(final List<BitmapAndCSBean> mLists) {
        TCS = false;
        exec.execute(new Runnable() {
            @Override
            public void run() {
                if (mLists.size() == 0) {
                    return;
                }
                BMPToSJBean bean  = BmpToDZUtils.button1_Click2(mLists.get(0).getDatas(), false); //bmp宽高数据
                byte[] bb = bean.getData(); //位图数据
                int bmpw2 = bean.getWidth(); //宽
                bmph2 = bean.getHeight();
                int w = bmpw2 / 48;
                int y = bmpw2 % 48;

                if (y != 0) {
                    w = w + 1; //得到最后需要的喷头打印数
                }
                datas2 = (ArrayList<byte[]>) BytesToListBytesUtils.bytesToList(bb, w, bmph2); //得到每个喷头要传递的数据 data.size就是需要的喷头数
                byteh2 = LongIntToBytesUtils.intToBytesXDTY(bmph2, 4); //实际打印的行数 (打印的行对应显示图片的列)
                bytech2 = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getCS(), 2); //次数
                TTPXH = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getTPXH(), 4); //图片序号
                if (datas2.size() > 1) {
                    tcpclient2sendcstp();
                } else {
                    if (mLists.size() != 0) {
                        lockdata(mLists);
                    }
                    TCS = true;
                }
            }
        });

    }

    //3号喷头数据处理
    private void threecstp(final List<BitmapAndCSBean> mLists) {
        ThCS = false;
        exec.execute(new Runnable() {
            @Override
            public void run() {
                if (mLists.size() == 0) {
                    return;
                }
                BMPToSJBean bean  = BmpToDZUtils.button1_Click2(mLists.get(0).getDatas(), false); //bmp宽高数据
                byte[] bb = bean.getData(); //位图数据
                int bmpw2 = bean.getWidth(); //宽
                bmph3 = bean.getHeight();
                int w = bmpw2 / 48;
                int y = bmpw2 % 48;

                if (y != 0) {
                    w = w + 1; //得到最后需要的喷头打印数
                }
                datas3 = (ArrayList<byte[]>) BytesToListBytesUtils.bytesToList(bb, w, bmph3); //得到每个喷头要传递的数据 data.size就是需要的喷头数
                byteh3 = LongIntToBytesUtils.intToBytesXDTY(bmph3, 4); //实际打印的行数 (打印的行对应显示图片的列)
                bytech3 = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getCS(), 2); //次数
                ThTPXH = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getTPXH(), 4); //图片序号
                if (datas3.size() > 2) {
                    tcpclient3sendcstp();
                } else {
                    if (mLists.size() != 0) {
                        lockdata(mLists);
                    }
                    ThCS = true;
                }
            }
        });
    }

    //4号喷头数据处理
    private void fourcstp(final List<BitmapAndCSBean> mLists) {
        FCS = false;
        exec.execute(new Runnable() {
            @Override
            public void run() {
                if (mLists.size() == 0) {
                    return;
                }
                BMPToSJBean bean  = BmpToDZUtils.button1_Click2(mLists.get(0).getDatas(), false); //bmp宽高数据
                byte[] bb = bean.getData(); //位图数据
                int bmpw2 = bean.getWidth(); //宽
                bmph4 = bean.getHeight();
                int w = bmpw2 / 48;
                int y = bmpw2 % 48;

                if (y != 0) {
                    w = w + 1; //得到最后需要的喷头打印数
                }
                datas4 = (ArrayList<byte[]>) BytesToListBytesUtils.bytesToList(bb, w, bmph4); //得到每个喷头要传递的数据 data.size就是需要的喷头数
                byteh4 = LongIntToBytesUtils.intToBytesXDTY(bmph4, 4); //实际打印的行数 (打印的行对应显示图片的列)
                bytech4 = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getCS(), 2); //次数
                FTPXH = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getTPXH(), 4); //图片序号
                if (datas4.size() > 3) {
                    tcpclient4sendcstp();
                } else {
                    if (mLists.size() != 0) {
                        lockdata(mLists);
                    }
                    FCS = true;
                }
            }
        });
    }

    //5号喷头数据处理
    private void fivecstp(final List<BitmapAndCSBean> mLists) {
        FiCS = false;
        exec.execute(new Runnable() {
            @Override
            public void run() {
                if (mLists.size() == 0) {
                    return;
                }
                BMPToSJBean bean  = BmpToDZUtils.button1_Click2(mLists.get(0).getDatas(),false); //bmp宽高数据
                byte[] bb = bean.getData(); //位图数据
                int bmpw2 = bean.getWidth(); //宽
                bmph5 = bean.getHeight();
                int w = bmpw2 / 48;
                int y = bmpw2 % 48;

                if (y != 0) {
                    w = w + 1; //得到最后需要的喷头打印数
                }
                datas5 = (ArrayList<byte[]>) BytesToListBytesUtils.bytesToList(bb, w, bmph5); //得到每个喷头要传递的数据 data.size就是需要的喷头数
                byteh5 = LongIntToBytesUtils.intToBytesXDTY(bmph5, 4); //实际打印的行数 (打印的行对应显示图片的列)
                bytech5 = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getCS(), 2); //次数
                FiTPXH = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getTPXH(), 4); //图片序号
                if (datas5.size() > 4) {
                    tcpclient5sendcstp();
                } else {
                    if (mLists.size() != 0) {
                        lockdata(mLists);
                    }
                    FiCS = true;
                }
            }
        });
    }

    //6号喷头数据处理
    private void sixcstp(final List<BitmapAndCSBean> mLists) {
        SCS = false;
        exec.execute(new Runnable() {
            @Override
            public void run() {
                if (mLists.size() == 0) {
                    return;
                }
                BMPToSJBean bean  = BmpToDZUtils.button1_Click2(mLists.get(0).getDatas(),false); //bmp宽高数据
                byte[] bb = bean.getData(); //位图数据
                int bmpw2 = bean.getWidth(); //宽
                bmph6 = bean.getHeight();
                int w = bmpw2 / 48;
                int y = bmpw2 % 48;

                if (y != 0) {
                    w = w + 1; //得到最后需要的喷头打印数
                }
                datas6 = (ArrayList<byte[]>) BytesToListBytesUtils.bytesToList(bb, w, bmph6); //得到每个喷头要传递的数据 data.size就是需要的喷头数
                byteh6 = LongIntToBytesUtils.intToBytesXDTY(bmph6, 4); //实际打印的行数 (打印的行对应显示图片的列)
                bytech6 = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getCS(), 2); //次数
                STPXH = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getTPXH(), 4); //图片序号
                if (datas6.size() > 5) {
                    tcpclient6sendcstp();
                } else {
                    if (mLists.size() != 0) {
                        lockdata(mLists);
                    }
                    SCS = true;
                }
            }
        });
    }

    //7号喷头数据处理
    private void sevencstp(final List<BitmapAndCSBean> mLists) {
        SeCS = false;
        exec.execute(new Runnable() {
            @Override
            public void run() {
                if (mLists.size() == 0) {
                    return;
                }
                BMPToSJBean bean  = BmpToDZUtils.button1_Click2(mLists.get(0).getDatas(), false); //bmp宽高数据
                byte[] bb = bean.getData(); //位图数据
                int bmpw2 = bean.getWidth(); //宽
                bmph7 = bean.getHeight();
                int w = bmpw2 / 48;
                int y = bmpw2 % 48;

                if (y != 0) {
                    w = w + 1; //得到最后需要的喷头打印数
                }
                datas7 = (ArrayList<byte[]>) BytesToListBytesUtils.bytesToList(bb, w, bmph7); //得到每个喷头要传递的数据 data.size就是需要的喷头数
                byteh7 = LongIntToBytesUtils.intToBytesXDTY(bmph7, 4); //实际打印的行数 (打印的行对应显示图片的列)
                bytech7 = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getCS(), 2); //次数
                SeTPXH = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getTPXH(), 4); //图片序号
                if (datas7.size() > 6) {
                    tcpclient7sendcstp();
                } else {
                    if (mLists.size() != 0) {
                        lockdata(mLists);
                    }
                    SeCS = true;
                }
            }
        });
    }

    //8号喷头数据处理
    private void eightcstp(final List<BitmapAndCSBean> mLists) {
        ECS = false;
        exec.execute(new Runnable() {
            @Override
            public void run() {
                if (mLists.size() == 0) {
                    return;
                }
                BMPToSJBean bean  = BmpToDZUtils.button1_Click2(mLists.get(0).getDatas(), false); //bmp宽高数据
                byte[] bb = bean.getData(); //位图数据
                int bmpw2 = bean.getWidth(); //宽
                bmph8 = bean.getHeight();
                int w = bmpw2 / 48;
                int y = bmpw2 % 48;

                if (y != 0) {
                    w = w + 1; //得到最后需要的喷头打印数
                }
                datas8 = (ArrayList<byte[]>) BytesToListBytesUtils.bytesToList(bb, w, bmph8); //得到每个喷头要传递的数据 data.size就是需要的喷头数
                byteh8 = LongIntToBytesUtils.intToBytesXDTY(bmph8, 4); //实际打印的行数 (打印的行对应显示图片的列)
                bytech8 = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getCS(), 2); //次数
                ETPXH = LongIntToBytesUtils.intToBytesXDTY(mLists.get(0).getTPXH(), 4); //图片序号
                if (datas8.size() > 7) {
                    tcpclient8sendcstp();
                } else {
                    if (mLists.size() != 0) {
                        lockdata(mLists);
                    }
                    ECS = true;
                }
            }
        });
    }

    //1号喷头传输
    private void tcpclientsendcstp() {
        if (tcpClient != null) {
            OHF = 1;
            if (datas.get(datas.size() - 1).length > 1024) {
                OFlag = false; //多包的标记
                OCD = datas.get(datas.size() - 1).length / 1024;
                int yu = datas.get(datas.size() - 1).length % 1024;
                int zhk = datas.get(datas.size() - 1).length / bmph;
                if (yu != 0) {
                    OCD = OCD + 1;
                }

                byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(OCD, 2); //包数

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0}, byte2, bytech, new byte[]{(byte) zhk}, byteh, OTPXH);

                tcpClient.sendCSTP(bytedata);

            } else {
                OFlag = true; //多包的标记
                int zhk = datas.get(datas.size() - 1).length / bmph;

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0, 1, 0}, bytech, new byte[]{(byte) zhk}, byteh, OTPXH);

                tcpClient.sendCSTP(bytedata);

            }
        } else {
            OCS = true;
        }
    }

    //2号喷头传输
    private void tcpclient2sendcstp() {
        if (tcpClient2 != null) {
            THF = 1;
            if (datas2.get(datas2.size() - 2).length > 1024) {
                TFlag = false; //多包的标记
                TCD = datas2.get(datas2.size() - 2).length / 1024;
                int yu = datas2.get(datas2.size() - 2).length % 1024;
                int zhk = datas2.get(datas2.size() - 2).length / bmph2;
                if (yu != 0) {
                    TCD = TCD + 1;
                }

                byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(TCD, 2); //包数

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0}, byte2, bytech2, new byte[]{(byte) zhk}, byteh2, TTPXH);

                tcpClient2.sendCSTP(bytedata);

            } else {
                TFlag = true; //多包的标记
                int zhk = datas2.get(datas2.size() - 2).length / bmph2;

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0, 1, 0}, bytech2, new byte[]{(byte) zhk}, byteh2, TTPXH);

                tcpClient2.sendCSTP(bytedata);

            }
        } else {
            TCS = true;
        }
    }

    //3号喷头传输
    private void tcpclient3sendcstp() {
        if (tcpClient3 != null) {

            ThHF = 1;
            if (datas3.get(datas3.size() - 3).length > 1024) {
                ThFlag = false; //多包的标记
                ThCD = datas3.get(datas3.size() - 3).length / 1024;
                int yu = datas3.get(datas3.size() - 3).length % 1024;
                int zhk = datas3.get(datas3.size() - 3).length / bmph3;
                if (yu != 0) {
                    ThCD = ThCD + 1;
                }

                byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(ThCD, 2); //包数

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0}, byte2, bytech3, new byte[]{(byte) zhk}, byteh3, ThTPXH);

                tcpClient3.sendCSTP(bytedata);

            } else {
                ThFlag = true; //多包的标记
                int zhk = datas3.get(datas3.size() - 3).length / bmph3;

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0, 1, 0}, bytech3, new byte[]{(byte) zhk}, byteh3, ThTPXH);

                tcpClient3.sendCSTP(bytedata);

            }
        } else {
            ThCS = true;
        }
    }

    //4号喷头传输
    private void tcpclient4sendcstp() {
        if (tcpClient4 != null) {
            FHF = 1;
            if (datas4.get(datas4.size() - 4).length > 1024) {
                FFlag = false; //多包的标记
                FCD = datas4.get(datas4.size() - 4).length / 1024;
                int yu = datas4.get(datas4.size() - 4).length % 1024;
                int zhk = datas4.get(datas4.size() - 4).length / bmph4;
                if (yu != 0) {
                    FCD = FCD + 1;
                }

                byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(FCD, 2); //包数

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0}, byte2, bytech4, new byte[]{(byte) zhk}, byteh4, FTPXH);

                tcpClient4.sendCSTP(bytedata);

            } else {
                FFlag = true; //多包的标记
                int zhk = datas4.get(datas4.size() - 4).length / bmph4;

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0, 1, 0}, bytech4, new byte[]{(byte) zhk}, byteh4, FTPXH);

                tcpClient4.sendCSTP(bytedata);

            }
        } else {
            FCS = true;
        }
    }

    //5号喷头传输
    private void tcpclient5sendcstp() {
        if (tcpClient5 != null) {

            FiHF = 1;
            if (datas5.get(datas5.size() - 5).length > 1024) {
                FiFlag = false; //多包的标记
                FiCD = datas5.get(datas5.size() - 5).length / 1024;
                int yu = datas5.get(datas5.size() - 5).length % 1024;
                int zhk = datas5.get(datas5.size() - 5).length / bmph5;

                if (yu != 0) {
                    FiCD = FiCD + 1;
                }

                byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(FiCD, 2); //包数

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0}, byte2, bytech5, new byte[]{(byte) zhk}, byteh5, FiTPXH);

                tcpClient5.sendCSTP(bytedata);

            } else {
                FiFlag = true; //多包的标记
                int zhk = datas5.get(datas5.size() - 5).length / bmph5;

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0, 1, 0}, bytech5, new byte[]{(byte) zhk}, byteh5, FiTPXH);

                tcpClient5.sendCSTP(bytedata);

            }
        } else {
            FiCS = true;
        }
    }

    //6号喷头传输
    private void tcpclient6sendcstp() {
        if (tcpClient6 != null) {

            SHF = 1;
            if (datas6.get(datas6.size() - 6).length > 1024) {
                SFlag = false; //多包的标记
                SCD = datas6.get(datas6.size() - 6).length / 1024;
                int yu = datas6.get(datas6.size() - 6).length % 1024;
                int zhk = datas6.get(datas6.size() - 6).length / bmph6;

                if (yu != 0) {
                    SCD = SCD + 1;
                }

                byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(SCD, 2); //包数

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0}, byte2, bytech6, new byte[]{(byte) zhk}, byteh6, STPXH);

                tcpClient6.sendCSTP(bytedata);

            } else {
                SFlag = true; //多包的标记
                int zhk = datas6.get(datas6.size() - 6).length / bmph6;

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0, 1, 0}, bytech6, new byte[]{(byte) zhk}, byteh6, STPXH);

                tcpClient6.sendCSTP(bytedata);

            }
        } else {
            SCS = true;
        }
    }

    //7号喷头传输
    private void tcpclient7sendcstp() {
        if (tcpClient7 != null) {

            SeHF = 1;
            if (datas7.get(datas7.size() - 7).length > 1024) {
                SeFlag = false; //多包的标记
                SeCD = datas7.get(datas7.size() - 7).length / 1024;
                int yu = datas7.get(datas7.size() - 7).length % 1024;
                int zhk = datas7.get(datas7.size() - 7).length / bmph7;

                if (yu != 0) {
                    SeCD = SeCD + 1;
                }

                byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(SeCD, 2); //包数

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0}, byte2, bytech7, new byte[]{(byte) zhk}, byteh7, SeTPXH);

                tcpClient7.sendCSTP(bytedata);

            } else {
                SeFlag = true; //多包的标记
                int zhk = datas7.get(datas7.size() - 7).length / bmph7;

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0, 1, 0}, bytech7, new byte[]{(byte) zhk}, byteh7, SeTPXH);

                tcpClient7.sendCSTP(bytedata);

            }
        } else {
            SeCS = true;
        }
    }

    //8号喷头传输
    private void tcpclient8sendcstp() {
        if (tcpClient8 != null) {

            EHF = 1;
            if (datas8.get(datas8.size() - 8).length > 1024) {
                EFlag = false; //多包的标记
                ECD = datas8.get(datas8.size() - 8).length / 1024;
                int yu = datas8.get(datas8.size() - 8).length % 1024;
                int zhk = datas8.get(datas8.size() - 8).length / bmph8;

                if (yu != 0) {
                    ECD = ECD + 1;
                }

                byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(ECD, 2); //包数

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0}, byte2, bytech8, new byte[]{(byte) zhk}, byteh8, ETPXH);

                tcpClient8.sendCSTP(bytedata);

            } else {
                EFlag = true; //多包的标记
                int zhk = datas8.get(datas8.size() - 8).length / bmph8;

                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{0, 0, 1, 0}, bytech8, new byte[]{(byte) zhk}, byteh8, ETPXH);

                tcpClient8.sendCSTP(bytedata);
            }
        } else {
            ECS = true;
        }
    }

    /**
     *  //todo 控制打印的方法
     * @param datas 数据码
     */
    public void sendKZDY(final byte[] datas, final int p) {
        if (!isZX) {
            SocketBeanFS.ebeanFS("机器故障,请先重启线路", 0, 0);
            return;
        }

        System.arraycopy(himtKZ, 1, himtKZ, 0, himtKZ.length - 1);
        himtKZ[himtKZ.length - 1] = System.currentTimeMillis();

        if (himtKZ[himtKZ.length - 1] - himtKZ[0] <= 500 && (p != 100)) {
            SocketBeanFS.ebeanFS("服务器繁忙了,请稍后再试2", 0, 0);
            return;
        }
        exec.execute(new Runnable() {
            @Override
            public void run() {

                switch (p) {
                    case 0:
                    case 100:
                        if (tcpClient != null) {
                            tcpClient.sendKZDY(datas);
                        }
                        if (tcpClient2 != null) {
                            tcpClient2.sendKZDY(datas);
                        }
                        if (tcpClient3 != null) {
                            tcpClient3.sendKZDY(datas);
                        }
                        if (tcpClient4 != null) {
                            tcpClient4.sendKZDY(datas);
                        }
                        if (tcpClient5 != null) {
                            tcpClient5.sendKZDY(datas);
                        }
                        if (tcpClient6 != null) {
                            tcpClient6.sendKZDY(datas);
                        }
                        if (tcpClient7 != null) {
                            tcpClient7.sendKZDY(datas);
                        }
                        if (tcpClient8 != null) {
                            tcpClient8.sendKZDY(datas);
                        }
                        break;
                    case 1:
                        if (tcpClient != null) {
                            tcpClient.sendKZDY(datas);
                        }
                        break;
                    case 2:
                        if (tcpClient2 != null) {
                            tcpClient2.sendKZDY(datas);
                        }
                        break;
                    case 3:
                        if (tcpClient3 != null) {
                            tcpClient3.sendKZDY(datas);
                        }
                        break;
                    case 4:
                        if (tcpClient4 != null) {
                            tcpClient4.sendKZDY(datas);
                        }
                        break;
                    case 5:
                        if (tcpClient5 != null) {
                            tcpClient5.sendKZDY(datas);
                        }
                        break;
                    case 6:
                        if (tcpClient6 != null) {
                            tcpClient6.sendKZDY(datas);
                        }
                        break;
                    case 7:
                        if (tcpClient7 != null) {
                            tcpClient7.sendKZDY(datas);
                        }
                        break;
                    case 8:
                        if (tcpClient8 != null) {
                            tcpClient8.sendKZDY(datas);
                        }
                        break;
                }
            }
        });
    }

    /**
     *  上传错误信息的方法
     * @param datas 数据码
     */
    private void sendSCXX(final byte[] datas, final int p) {
        if (!isZX) {
            SocketBeanFS.ebeanFS("机器故障,请先重启线路", 0, 0);
            return;
        }
        exec.execute(new Runnable() {
            @Override
            public void run() {
                switch (p) {
                    case 0:
                        if (tcpClient != null) {
                            tcpClient.sendSCXX(datas);
                        }
                        if (tcpClient2 != null) {
                            tcpClient2.sendSCXX(datas);
                        }
                        if (tcpClient3 != null) {
                            tcpClient3.sendSCXX(datas);
                        }
                        if (tcpClient4 != null) {
                            tcpClient4.sendSCXX(datas);
                        }
                        if (tcpClient5 != null) {
                            tcpClient5.sendSCXX(datas);
                        }
                        if (tcpClient6 != null) {
                            tcpClient6.sendSCXX(datas);
                        }
                        if (tcpClient7 != null) {
                            tcpClient7.sendSCXX(datas);
                        }
                        if (tcpClient8 != null) {
                            tcpClient8.sendSCXX(datas);
                        }
                        break;
                    case 1:
                        if (tcpClient != null) {
                            tcpClient.sendSCXX(datas);
                        }
                        break;
                    case 2:
                        if (tcpClient2 != null) {
                            tcpClient2.sendSCXX(datas);
                        }
                        break;
                    case 3:
                        if (tcpClient3 != null) {
                            tcpClient3.sendSCXX(datas);
                        }
                        break;
                    case 4:
                        if (tcpClient4 != null) {
                            tcpClient4.sendSCXX(datas);
                        }
                        break;
                    case 5:
                        if (tcpClient5 != null) {
                            tcpClient5.sendSCXX(datas);
                        }
                        break;
                    case 6:
                        if (tcpClient6 != null) {
                            tcpClient6.sendSCXX(datas);
                        }
                        break;
                    case 7:
                        if (tcpClient7 != null) {
                            tcpClient7.sendSCXX(datas);
                        }
                        break;
                    case 8:
                        if (tcpClient8 != null) {
                            tcpClient8.sendSCXX(datas);
                        }
                        break;
                }
            }
        });
    }

    /**
     *  //todo 查询墨盒信息的方法
     * @param datas 数据码
     */
    public void sendCXXX(final byte[] datas, final int p) {

        if (!isZX) {
            SocketBeanFS.ebeanFS("机器故障,请先重启线路", 0, 0);
            return;
        }
        exec.execute(new Runnable() {
            @Override
            public void run() {
                switch (p) {
                    case 0:
                        if (tcpClient != null) {
                            tcpClient.sendCXXX(datas);
                        }
                        if (tcpClient2 != null) {
                            tcpClient2.sendCXXX(datas);
                        }
                        if (tcpClient3 != null) {
                            tcpClient3.sendCXXX(datas);
                        }
                        if (tcpClient4 != null) {
                            tcpClient4.sendCXXX(datas);
                        }
                        if (tcpClient5 != null) {
                            tcpClient5.sendCXXX(datas);
                        }
                        if (tcpClient6 != null) {
                            tcpClient6.sendCXXX(datas);
                        }
                        if (tcpClient7 != null) {
                            tcpClient7.sendCXXX(datas);
                        }
                        if (tcpClient8 != null) {
                            tcpClient8.sendCXXX(datas);
                        }
                        break;
                    case 1:
                        if (tcpClient != null) {
                            tcpClient.sendCXXX(datas);
                        }
                        break;
                    case 2:
                        if (tcpClient2 != null) {
                            tcpClient2.sendCXXX(datas);
                        }
                        break;
                    case 3:
                        if (tcpClient3 != null) {
                            tcpClient3.sendCXXX(datas);
                        }
                        break;
                    case 4:
                        if (tcpClient4 != null) {
                            tcpClient4.sendCXXX(datas);
                        }
                        break;
                    case 5:
                        if (tcpClient5 != null) {
                            tcpClient5.sendCXXX(datas);
                        }
                        break;
                    case 6:
                        if (tcpClient6 != null) {
                            tcpClient6.sendCXXX(datas);
                        }
                        break;
                    case 7:
                        if (tcpClient7 != null) {
                            tcpClient7.sendCXXX(datas);
                        }
                        break;
                    case 8:
                        if (tcpClient8 != null) {
                            tcpClient8.sendCXXX(datas);
                        }
                        break;
                }
            }
        });
    }

    /**
     *  //todo 升级程序的方法 (实际处理与传输图片一致)
     * @param datas 数据码
     */
    public void sendSJCX(final byte[] datas, final int p) {

        if (!isZX) {
            SocketBeanFS.ebeanFS("机器故障,请先重启线路", 0, 0);
            return;
        }
        exec.execute(new Runnable() {
            @Override
            public void run() {
                switch (p) {
                    case 0:
                        if (tcpClient != null) {
                            tcpClient.sendSJCX(datas);
                        }
                        if (tcpClient2 != null) {
                            tcpClient2.sendSJCX(datas);
                        }
                        if (tcpClient3 != null) {
                            tcpClient3.sendSJCX(datas);
                        }
                        if (tcpClient4 != null) {
                            tcpClient4.sendSJCX(datas);
                        }
                        if (tcpClient5 != null) {
                            tcpClient5.sendSJCX(datas);
                        }
                        if (tcpClient6 != null) {
                            tcpClient6.sendSJCX(datas);
                        }
                        if (tcpClient7 != null) {
                            tcpClient7.sendSJCX(datas);
                        }
                        if (tcpClient8 != null) {
                            tcpClient8.sendSJCX(datas);
                        }
                        break;
                    case 1:
                        if (tcpClient != null) {
                            tcpClient.sendSJCX(datas);
                        }
                        break;
                    case 2:
                        if (tcpClient2 != null) {
                            tcpClient2.sendSJCX(datas);
                        }
                        break;
                    case 3:
                        if (tcpClient3 != null) {
                            tcpClient3.sendSJCX(datas);
                        }
                        break;
                    case 4:
                        if (tcpClient4 != null) {
                            tcpClient4.sendSJCX(datas);
                        }
                        break;
                    case 5:
                        if (tcpClient5 != null) {
                            tcpClient5.sendSJCX(datas);
                        }
                        break;
                    case 6:
                        if (tcpClient6 != null) {
                            tcpClient6.sendSJCX(datas);
                        }
                        break;
                    case 7:
                        if (tcpClient7 != null) {
                            tcpClient7.sendSJCX(datas);
                        }
                        break;
                    case 8:
                        if (tcpClient8 != null) {
                            tcpClient8.sendSJCX(datas);
                        }
                        break;
                }
            }
        });
    }

    //创建广播
    private void bindReceiver(){

        //IntentFilter intentFilter = new IntentFilter("tcpClientReceiver");
        //mContext.registerReceiver(myBroadcastReceiver, intentFilter);

    }

    //用于接收socket发送数据的广播,注意关闭 速度较慢 替换成回调接口使用
    /*private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String mAction = intent.getAction();
            switch (mAction){
                case "tcpClientReceiver": //socket发送的数据位
                    //MLog.v("广播2");
                    boolean isXY = intent.getBooleanExtra("isXY", false); //是否为协议报文返回

                    if (isZX) { //没有断开才接听广播

                        if (isXY) { //报文返回
                            PMJstorageBean bean;
                            try {
                                bean = (PMJstorageBean) intent.getSerializableExtra("tcpClientReceiver");
                            } catch (ClassCastException e) {
                                return;
                            }

                            switch (bean.getlXM()[0]) {
                                case (byte) 0x81: //启动打印
                                    MLog.v(bean.toString());
                                    if (!qdList.contains(bean.getP())) {
                                        qdList.add(bean);
                                        if (qdList.size() == mX) {
                                            qdList.clear();

                                            for (int i = 0; i < qdList.size(); i++) {
                                                if (qdList.get(i).getsJM()[0] != 0) {
                                                    mListener.socketSendQDDY(qdList.get(i).getP(), qdList.get(i).getsJM());
                                                    return;
                                                }
                                            }

                                            mListener.socketSendQDDY(0, bean.getsJM());
                                        }
                                    }
                                    sendQD(bean);
                                    break;
                                case (byte) 0x82: //传输图片
                                    String d = PMJErrorCons.errorReturn(bean.getsJM()[0]);
                                    if (d == null) {
                                        sendSJ(bean.getP(), bean.getsJM());
                                    } else {

                                        if (!csList2.contains(bean.getP())) {
                                            csList2.add(bean.getP());
                                            if (csList2.size() == mX) {
                                                csList2.clear();
                                                if (bean.getsJM()[0] == 8) {
                                                    isOWHC = true;
                                                }
                                                OCS = true;
                                                TCS = true;
                                                ThCS = true;
                                                FCS = true;
                                                FiCS = true;
                                                SCS = true;
                                                SeCS = true;
                                                ECS = true;
                                                mListener.socketSendCSTP(0, bean.getsJM());
                                                MLog.v(bean.toString());
                                            }
                                        }
                                    }

                                    break;
                                case (byte) 0x83: //控制打印
                                    MLog.v(bean.toString());
                                    //判断该喷头是否回复过
                                    if (!kzList.contains(bean.getP())) {
                                        if (bean.getsJM()[1] != 5) { //不故障时添加
                                            kzList.add(bean.getP());
                                        } else {
                                            kzList.add(100);
                                        }
                                        if (kzList.size() == mX) {
                                            for (int i = 0; i < kzList.size(); i++) {
                                                if (kzList.get(i) == 100) {
                                                    SocketBeanFS.ebeanFS("机器故障",  0, 13);
                                                    return;
                                                }
                                            }
                                            kzList.clear();
                                            mListener.socketSendKZDY(0, bean.getsJM());
                                        }
                                    } else {
                                        SocketBeanFS.ebeanFS("机器故障",  0, 13);
                                    }
                                    sendKZ(bean);
                                    break;
                                case (byte) 0x84: //上传错误信息
                                    String dd = PMJErrorCons.errorReturn(bean.getsJM()[0]);
                                    if (dd != null) {
                                        mListener.socketSendSCXX(bean.getP(), dd);
                                    }
                                    break;
                                case (byte) 0x85: //查询墨盒信息
                                    MLog.v(bean.toString());
                                    MHdataBean mHdataBean = BeanHCUtils.mhdatabean(bean.getsJM());

                                    mListener.socketSendCXXX(bean.getP(), mHdataBean);
                                    break;
                                case (byte) 0x86: //升级程序

                                    mListener.socketSendSJCX(bean.getP(), bean.getsJM());
                                    break;
                                case (byte) 0x87: //应答打印

                                    //判断该喷头是否回复过
                                    if (!ydList.contains(bean.getP())) {
                                        ydList.add(bean.getP());
                                        if (ydList.size() == mX) {
                                            ydList.clear();

                                            if (ydList2.size() != 0) {
                                                ydList.addAll(ydList2);
                                                ydList2.clear();
                                            }

                                            mListener.socketSendYDDY(0, new byte[]{0});
                                            MLog.v("打印结束 : " + mList.size());
                                            isOWHC = false;
                                            if (CaCheJCUtils.cacheN(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                                mListener.socketSendHCBZ(-1);
                                            } else if (CaCheJCUtils.cacheS(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                                mListener.socketSendHCBZ(0);
                                            }
                                        }

                                    } else {
                                        //SocketBeanFS.ebeanFS("机器故障",  0, 17);
                                        if (!ydList2.contains(bean.getP())) {
                                            ydList2.add(bean.getP());
                                        } else {

                                                SocketBeanFS.ebeanFS("机器故障",  0, 17);
                                                return;

                                        }
                                    }

                                    switch (bean.getP()) {
                                        case 1:
                                            if (OCS && mList.size() != 0) {
                                                onecstp(mList);
                                            }
                                            break;
                                        case 2:
                                            if (TCS && mList2.size() != 0) {
                                                twocstp(mList2);
                                            }
                                            break;
                                        case 3:
                                            if (ThCS && mList3.size() != 0) {
                                                threecstp(mList3);
                                            }
                                            break;
                                        case 4:
                                            if (FCS && mList4.size() != 0) {
                                                fourcstp(mList4);
                                            }
                                            break;
                                        case 5:
                                            if (FiCS && mList5.size() != 0) {
                                                fivecstp(mList5);
                                            }
                                            break;
                                        case 6:
                                            if (SCS && mList6.size() != 0) {
                                                sixcstp(mList6);
                                            }
                                            break;
                                        case 7:
                                            if (SeCS && mList7.size() != 0) {
                                                sevencstp(mList7);
                                            }
                                            break;
                                        case 8:
                                            if (ECS && mList8.size() != 0) {
                                                eightcstp(mList8);
                                            }
                                            break;
                                    }
                                    break;
                            }

                        } else { //一般返回 --> 例如初连接的返回

                            ErrorBean ebean;
                            try {
                                ebean = (ErrorBean) intent.getSerializableExtra("tcpClientReceiver");
                            } catch (ClassCastException e) {
                                return;
                            }

                            MLog.v(ebean.toString());
                            if (ebean.getData().trim().equals("通讯连接不上") || ebean.getData().trim().equals("服务器无返回")) {
                                if (!iscloce) {
                                    iscloce = true;
                                    close(true);
                                }
                                //正式时需要监听是否断开重启连接
                                ebean.setData("通讯断开,正在重连...");
                                ebean.setZTM(5);
                                mListener.socketSendString(ebean);
                                //chonglian(ebean.getP());
                            } else if (ebean.getData().trim().equals("tcp_service connect ok!")) {
                                if (!tcpOK.contains(ebean.getP())) {

                                    tcpOK.add(ebean.getP());
                                    if (tcpOK.size() == mX) {
                                        tcpOK.clear();
                                        ebean.setData("tcp_service connect ok!");
                                        ebean.setZTM(1);
                                        mListener.socketSendString(ebean);
                                        sendKZDY(new byte[]{3}, 100);
                                    }
                                } else {
                                    close(true);
                                    ebean.setData("通讯断开,正在重连...");
                                    ebean.setZTM(5);
                                    mListener.socketSendString(ebean);
                                }
                            } else if (ebean.getData().trim().equals("ping不通")) {
                                ebean.setData("通讯连接不上,请检测设备!");
                                ebean.setZTM(2);
                                mListener.socketSendString(ebean);
                            } else if (ebean.getData().trim().equals("ping得通")) {
                                ebean.setZTM(5);
                                mListener.socketSendString(ebean);
                            } else if (ebean.getData().trim().equals("机器故障")) {
                                close(false);
                                ebean.setZTM(-1);
                                mListener.socketSendString(ebean);
                            } else {
                                ebean.setZTM(0);
                                mListener.socketSendString(ebean);
                            }
                        }
                        break;
                    }
            }
        }
    }*/

    private int OSJM = 0; //回复序号
    private int TSJM = 0;
    private int ThSJM = 0;
    private int FSJM = 0;
    private int FiSJM = 0;
    private int SSJM = 0;
    private int SeSJM = 0;
    private int ESJM = 0;

    private int whc = 0;

    private List mListCW = new ArrayList();

    //下位机数据回调
    public void sendHD(boolean isXY, Object sj) {

        if (isZX) { //没有断开才接听广播

            if (isXY) { //报文返回
                PMJstorageBean bean;
                try {
                    bean = (PMJstorageBean) sj;
                } catch (ClassCastException e) {
                    return;
                }

                switch (bean.getlXM()[0]) {
                    case (byte) 0x81: //启动打印
                        MLog.v(bean.toString());
                        if (bean.getsJM()[0] == 6 || bean.getsJM()[0] == 14) {
                            //缓存不足直接停机
                            sendKZDY(new byte[]{3}, 100);
                            SocketBeanFS.ebeanFS("机器故障", bean.getP(), 15);
                        }
                        if (!qdList.contains(bean.getP())) {

                            synchronized (LocalControlerUtil.this) {
                                qdList.add(bean);
                            }
                            if (qdList.size() == mX) {
                                qdList.clear();

                                for (int i = 0; i < qdList.size(); i++) {
                                    if (qdList.get(i).getsJM()[0] != 0) {
                                        mListener.socketSendQDDY(qdList.get(i).getP(), qdList.get(i).getsJM());
                                        return;
                                    }
                                }

                                mListener.socketSendQDDY(0, bean.getsJM());
                            }
                        }
                        sendQD(bean);
                        break;
                    case (byte) 0x82: //传输图片
                        //MLog.v("22222222");
                        String d = PMJErrorCons.errorReturn(bean.getsJM()[0]);
                        if (d == null) {
                            sendSJ(bean.getP(), bean.getsJM());
                        } else {
                            //MLog.v("222" + Arrays.toString(bean.getsJM()));
                            switch (bean.getsJM()[0]) {
                                case 8:
                                    switch (bean.getP()) {
                                        case 1:
                                            isOWHC = true;
                                            isODY = true;
                                            OCS = true;
                                            /*if (!OQD) {
                                                if (tcpClient != null) {
                                                    tcpClient.sendQDDY(qdData);
                                                }
                                                OQD = true;
                                            }*/
                                            MLog.v(bean.toString());
                                            break;
                                        case 2:
                                            isTWHC = true;
                                            isTDY = true;
                                            TCS = true;
                                            /*if (!TQD) {
                                                if (tcpClient2 != null) {
                                                    tcpClient2.sendQDDY(qdData2);
                                                }
                                                TQD = true;
                                            }*/
                                            MLog.v(bean.toString());
                                            break;
                                        case 3:
                                            isThWHC = true;
                                            isThDY = true;
                                            ThCS = true;
                                            /*if (!ThQD) {
                                                if (tcpClient3 != null) {
                                                    tcpClient3.sendQDDY(qdData3);
                                                }
                                                ThQD = true;
                                            }*/
                                            MLog.v(bean.toString());
                                            break;
                                        case 4:
                                            isFWHC = true;
                                            isFDY = true;
                                            FCS = true;
                                            /*if (!FQD) {
                                                if (tcpClient4 != null) {
                                                    tcpClient4.sendQDDY(qdData4);
                                                }
                                                FQD = true;
                                            }*/
                                            MLog.v(bean.toString());
                                            break;
                                        case 5:
                                            isFiWHC = true;
                                            isFiDY = true;
                                            FiCS = true;
                                            /*if (!FiQD) {
                                                if (tcpClient5 != null) {
                                                    tcpClient5.sendQDDY(qdData5);
                                                }
                                                FiQD = true;
                                            }*/
                                            MLog.v(bean.toString());
                                            break;
                                        case 6:
                                            isSWHC = true;
                                            isSDY = true;
                                            SCS = true;
                                            /*if (!SQD) {
                                                if (tcpClient6 != null) {
                                                    tcpClient6.sendQDDY(qdData6);
                                                }
                                                SQD = true;
                                            }*/
                                            MLog.v(bean.toString());
                                            break;
                                        case 7:
                                            isSeWHC = true;
                                            isSeDY = true;
                                            SeCS = true;
                                            /*if (!SeQD) {
                                                if (tcpClient7 != null) {
                                                    tcpClient7.sendQDDY(qdData7);
                                                }
                                                SeQD = true;
                                            }*/
                                            MLog.v(bean.toString());
                                            break;
                                        case 8:
                                            isEWHC = true;
                                            isEDY = true;
                                            ECS = true;
                                            /*if (!EQD) {
                                                if (tcpClient8 != null) {
                                                    tcpClient8.sendQDDY(qdData8);
                                                }
                                                EQD = true;
                                            }*/
                                            MLog.v(bean.toString());
                                            break;
                                    }
                                synchronized (LocalControlerUtil.this) {
                                    ++whc;
                                    if (whc == mX) {
                                        whc = 0;
                                        isYD = true;
                                        //SystemClock.sleep(500);
//                                        if (SerialPortHelper.getInstance() != null) {
//                                            SerialPortHelper.getInstance().sendQD(mX);
//                                        }
                                        if (!OQD) {
                                            if (tcpClient != null) {
                                                tcpClient.sendQDDY(qdData);
                                            }
                                            OQD = true;
                                        }
                                        if (!TQD) {
                                            if (tcpClient2 != null) {
                                                tcpClient2.sendQDDY(qdData2);
                                            }
                                            TQD = true;
                                        }
                                        if (!ThQD) {
                                            if (tcpClient3 != null) {
                                                tcpClient3.sendQDDY(qdData3);
                                            }
                                            ThQD = true;
                                        }
                                        if (!FQD) {
                                            if (tcpClient4 != null) {
                                                tcpClient4.sendQDDY(qdData4);
                                            }
                                            FQD = true;
                                        }
                                        if (!FiQD) {
                                            if (tcpClient5 != null) {
                                                tcpClient5.sendQDDY(qdData5);
                                            }
                                            FiQD = true;
                                        }
                                        if (!SQD) {
                                            if (tcpClient6 != null) {
                                                tcpClient6.sendQDDY(qdData6);
                                            }
                                            SQD = true;
                                        }
                                        if (!SeQD) {
                                            if (tcpClient7 != null) {
                                                tcpClient7.sendQDDY(qdData7);
                                            }
                                            SeQD = true;
                                        }
                                        if (!EQD) {
                                            if (tcpClient8 != null) {
                                                tcpClient8.sendQDDY(qdData8);
                                            }
                                            EQD = true;
                                        }
                                    }
                                }
                                break;
                                case 17: //传输图片出错
                                    /*if (SerialPortHelper.getInstance() != null) {
                                        SerialPortHelper.getInstance().sendQD(0);
                                    }
                                    sendKZDY(new byte[]{3}, 100);*/
                                    //todo
                                    switch (bean.getP()) {
                                        case 1:
                                            MLog.v("传输出错" + Arrays.toString(bean.getsJM()) + ":" + mList.get(0).getTPXH() + ":" + bean.getP());
                                            break;
                                        case 2:
                                            MLog.v("传输出错" + Arrays.toString(bean.getsJM()) + ":" + mList2.get(0).getTPXH() + ":" + bean.getP());
                                            break;
                                        case 3:
                                            MLog.v("传输出错" + Arrays.toString(bean.getsJM()) + ":" + mList3.get(0).getTPXH() + ":" + bean.getP());
                                            break;
                                        case 4:
                                            MLog.v("传输出错" + Arrays.toString(bean.getsJM()) + ":" + mList4.get(0).getTPXH() + ":" + bean.getP());
                                            break;
                                        case 5:
                                            MLog.v("传输出错" + Arrays.toString(bean.getsJM()) + ":" + mList5.get(0).getTPXH() + ":" + bean.getP());
                                            break;
                                        case 6:
                                            MLog.v("传输出错" + Arrays.toString(bean.getsJM()) + ":" + mList6.get(0).getTPXH() + ":" + bean.getP());
                                            break;
                                        case 7:
                                            MLog.v("传输出错" + Arrays.toString(bean.getsJM()) + ":" + mList7.get(0).getTPXH() + ":" + bean.getP());
                                            break;
                                        case 8:
                                            MLog.v("传输出错" + Arrays.toString(bean.getsJM()) + ":" + mList8.get(0).getTPXH() + ":" + bean.getP());
                                            break;

                                    }
                                    SocketBeanFS.ebeanFS("机器故障", bean.getP(), 19);
                                    break;
                            }
                        }
                        break;
                    case (byte) 0x83: //控制打印
                        MLog.v(bean.toString());
                        //判断该喷头是否回复过
                        if (!kzList.contains(bean.getP())) {
                            if (bean.getsJM()[1] != 5) { //不故障时添加
                                synchronized (LocalControlerUtil.this) {
                                    kzList.add(bean.getP());
                                }
                            } else {
                                synchronized (LocalControlerUtil.this) {
                                    kzList.add(100);
                                }
                            }
                            if (kzList.size() == mX) {
                                for (int i = 0; i < kzList.size(); i++) {
                                    if (kzList.get(i) == 100) {
                                        SocketBeanFS.ebeanFS("机器故障", 0, 14);
                                        return;
                                    }
                                }
                                kzList.clear();
                                mListener.socketSendKZDY(0, bean.getsJM());
                            }
                        } else {
                            SocketBeanFS.ebeanFS("机器故障", 0, 13);
                        }
                        sendKZ(bean);
                        break;
                    case (byte) 0x84: //上传错误信息
                        MLog.v("上传错误" + bean.getP() + Arrays.toString(bean.getsJM()));
                        String dd = PMJErrorCons.errorReturn(bean.getsJM()[0]);
                        if (dd != null) {
                            mListener.socketSendSCXX(bean.getP(), dd);
                        }
                        //SocketBeanFS.ebeanFS("机器故障", 0, 21);
                        break;
                    case (byte) 0x85: //查询墨盒信息
                        MLog.v(bean.toString());
                        MHdataBean mHdataBean = BeanHCUtils.mhdatabean(bean.getsJM());

                        mListener.socketSendCXXX(bean.getP(), mHdataBean);
                        break;
                    case (byte) 0x86: //升级程序
                        //todo
                        mListener.socketSendSJCX(bean.getP(), bean.getsJM());
                        break;
                    case (byte) 0x87: //应答打印

                            //MLog.v(bean.getP() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()) + "");
                            if (!isYD) {
                                return;
                            }

                            switch (bean.getP()) {
                                case 1: //1号喷头负责
                                    if (LongIntToBytesUtils.bytesToInt3(bean.getsJM()) - OSJM == 1) {
                                        OSJM = LongIntToBytesUtils.bytesToInt3(bean.getsJM());
                                        isOWHC = false;
                                        //MLog.v("1打印结束 : " + mList.size() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()));
                                        MLog.v(bean.getP() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()) + ":" + mList.size());
                                        mListener.socketSendYDDY(1, bean.getsJM());
                                    } else {
                                        SocketBeanFS.ebeanFS("机器故障", 1, 17);
                                        return;
                                    }

                                    if (CaCheJCUtils.cacheN(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(-1);
                                    } else if (CaCheJCUtils.cacheS(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(0);
                                    }

                                    synchronized (LocalControlerUtil.this) {
                                        if (isODY && OCS && mList.size() != 0) {
                                            //MLog.v("进1" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()));
                                            onecstp(mList);
                                        }
                                    }
                                    break;
                                case 2:
                                    if (LongIntToBytesUtils.bytesToInt3(bean.getsJM()) - TSJM == 1) {
                                        TSJM = LongIntToBytesUtils.bytesToInt3(bean.getsJM());
                                        isTWHC = false;
                                        mListener.socketSendYDDY(2, bean.getsJM());
                                        MLog.v(bean.getP() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()) + ":" + mList2.size());
                                        //MLog.v("2打印结束 : " + mList.size() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()));
                                    } else {
                                        SocketBeanFS.ebeanFS("机器故障", 2, 17);
                                        return;
                                    }

                                    if (CaCheJCUtils.cacheN(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(-1);
                                    } else if (CaCheJCUtils.cacheS(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(0);
                                    }
                                    synchronized (LocalControlerUtil.this) {
                                        if (isTDY && TCS && mList2.size() != 0) {
                                            //MLog.v("进2" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()));
                                            twocstp(mList2);
                                        }
                                    }
                                    break;
                                case 3:
                                    if (LongIntToBytesUtils.bytesToInt3(bean.getsJM()) - ThSJM == 1) {
                                        ThSJM = LongIntToBytesUtils.bytesToInt3(bean.getsJM());
                                        isThWHC = false;
                                       // MLog.v("3打印结束 : " + mList.size() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()));
                                        MLog.v(bean.getP() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()) + ":" + mList3.size());
                                    } else {
                                        SocketBeanFS.ebeanFS("机器故障", 3, 17);
                                        return;
                                    }
                                    if (CaCheJCUtils.cacheN(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(-1);
                                    } else if (CaCheJCUtils.cacheS(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(0);
                                    }
                                    synchronized (LocalControlerUtil.this) {
                                        if (isThDY && ThCS && mList3.size() != 0) {
                                            threecstp(mList3);
                                        }
                                    }
                                    break;
                                case 4:
                                    if (LongIntToBytesUtils.bytesToInt3(bean.getsJM()) - FSJM == 1) {
                                        FSJM = LongIntToBytesUtils.bytesToInt3(bean.getsJM());
                                        isFWHC = false;
                                        //MLog.v("4打印结束 : " + mList.size() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()));
                                        MLog.v(bean.getP() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()) + ":" + mList4.size());
                                    } else {
                                        SocketBeanFS.ebeanFS("机器故障", 4, 17);
                                        return;
                                    }
                                    if (CaCheJCUtils.cacheN(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(-1);
                                    } else if (CaCheJCUtils.cacheS(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(0);
                                    }
                                    synchronized (LocalControlerUtil.this) {
                                        if (isFDY && FCS && mList4.size() != 0) {
                                            fourcstp(mList4);
                                        }
                                    }
                                    break;
                                case 5:
                                    if (LongIntToBytesUtils.bytesToInt3(bean.getsJM()) - FiSJM == 1) {
                                        FiSJM = LongIntToBytesUtils.bytesToInt3(bean.getsJM());
                                        isFiWHC = false;
                                        //MLog.v("5打印结束 : " + mList.size() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()));
                                        MLog.v(bean.getP() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()) + ":" + mList5.size());
                                    } else {
                                        SocketBeanFS.ebeanFS("机器故障", 5, 17);
                                        return;
                                    }
                                    if (CaCheJCUtils.cacheN(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(-1);
                                    } else if (CaCheJCUtils.cacheS(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(0);
                                    }
                                    synchronized (LocalControlerUtil.this) {
                                        if (isFiDY && FiCS && mList5.size() != 0) {
                                            fivecstp(mList5);
                                        }
                                    }
                                    break;
                                case 6:
                                    if (LongIntToBytesUtils.bytesToInt3(bean.getsJM()) - SSJM == 1) {
                                        SSJM = LongIntToBytesUtils.bytesToInt3(bean.getsJM());
                                        isSWHC = false;
                                        //MLog.v("6打印结束 : " + mList.size() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()));
                                        MLog.v(bean.getP() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()) + ":" + mList6.size());
                                    } else {
                                        SocketBeanFS.ebeanFS("机器故障", 6, 17);
                                        return;
                                    }
                                    if (CaCheJCUtils.cacheN(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(-1);
                                    } else if (CaCheJCUtils.cacheS(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(0);
                                    }
                                    synchronized (LocalControlerUtil.this) {
                                        if (isSDY && SCS && mList6.size() != 0) {
                                            sixcstp(mList6);
                                        }
                                    }
                                    break;
                                case 7:
                                    if (LongIntToBytesUtils.bytesToInt3(bean.getsJM()) - SeSJM == 1) {
                                        SeSJM = LongIntToBytesUtils.bytesToInt3(bean.getsJM());
                                        isSeWHC = false;
                                       // MLog.v("7打印结束 : " + mList.size() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()));
                                        MLog.v(bean.getP() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()) + ":" + mList7.size());
                                    } else {
                                        SocketBeanFS.ebeanFS("机器故障", 7, 17);
                                        return;
                                    }
                                    if (CaCheJCUtils.cacheN(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(-1);
                                    } else if (CaCheJCUtils.cacheS(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(0);
                                    }
                                    synchronized (LocalControlerUtil.this) {
                                        if (isSeDY && SeCS && mList7.size() != 0) {
                                            sevencstp(mList7);
                                        }
                                    }
                                    break;
                                case 8:
                                    if (LongIntToBytesUtils.bytesToInt3(bean.getsJM()) - ESJM == 1) {
                                        ESJM = LongIntToBytesUtils.bytesToInt3(bean.getsJM());
                                        isEWHC = false;
                                        //MLog.v("8打印结束 : " + mList.size() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()));
                                        MLog.v(bean.getP() + ":" + LongIntToBytesUtils.bytesToInt3(bean.getsJM()) + ":" + mList8.size());
                                    } else {
                                        SocketBeanFS.ebeanFS("机器故障", 8, 17);
                                        return;
                                    }
                                    if (CaCheJCUtils.cacheN(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(-1);
                                    } else if (CaCheJCUtils.cacheS(mX, mList, mList2, mList3, mList4, mList5, mList6, mList7, mList8)) {
                                        mListener.socketSendHCBZ(0);
                                    }
                                    synchronized (LocalControlerUtil.this) {
                                        if (isEDY && ECS && mList8.size() != 0) {
                                            eightcstp(mList8);
                                        }
                                    }
                                    break;
                            }
                            break;
                    case (byte) 0x88: //缓存不够回复
                        break;
                    case (byte) 0x89: //设置参数回复
                        MLog.v(bean.toString());
                        break;
                }

            } else { //一般返回 --> 例如初连接的返回

                ErrorBean ebean;
                try {
                    ebean = (ErrorBean) sj;
                } catch (ClassCastException e) {
                    return;
                }

                MLog.v(ebean.toString());
                if (ebean.getData().trim().equals("通讯连接不上") || ebean.getData().trim().equals("服务器无返回")) {
                    if (!iscloce) {
                        iscloce = true;
                        SocketBeanFS.ebeanFS("机器故障",  ebean.getP(), -1);
                    }

                    //chonglian(ebean.getP());
                } else if (ebean.getData().trim().equals("tcp_service connect ok!")) {
                    if (!tcpOK.contains(ebean.getP())) {
                        synchronized (LocalControlerUtil.this) {
                            tcpOK.add(ebean.getP());
                        }
                        if (tcpOK.size() == mX) {
                            tcpOK.clear();
                            ebean.setData("tcp_service connect ok!");
                            ebean.setZTM(1);
                            mListener.socketSendString(ebean);
//                            if (SerialPortHelper.getInstance() != null) {
//                                SerialPortHelper.getInstance().sendQD(0);
//                            }
                            sendKZDY(new byte[]{3}, 100);
                        }
                    }
                } else if (ebean.getData().trim().equals("ping不通")) {
                    ebean.setData("通讯连接不上,请检测设备!");
                    ebean.setZTM(2);
                    mListener.socketSendString(ebean);
                } else if (ebean.getData().trim().equals("ping得通")) {
                    ebean.setZTM(5);
                    mListener.socketSendString(ebean);
                } else if (ebean.getData().trim().equals("机器故障")) {
                    //机器故障码w 0:ping不通 19:传输出错 14:下位机通知的故障 17:打印不连续 -1:服务器无返回 15:缓存不足停机
                    close(false);
                    ebean.setZTM(-1);
                    mListener.socketSendString(ebean);
                } else {
                    ebean.setZTM(0);
                    mListener.socketSendString(ebean);
                }
            }
        }
    }

    //启动打印相关操作
    private void sendQD(PMJstorageBean bean) {
        switch (bean.getP()) {
            case 1:
                switch (bean.getsJM()[0]) {
                    case 0:
                    case 3:
                        OQD = true;
                        break;
                    case 6:
                    case 14:
                        cshData();
                    case 5:
                    case 16:
                        OQD = false;
                        break;
                }
                break;
            case 2:
                switch (bean.getsJM()[0]) {
                    case 0:
                    case 3:
                        TQD = true;
                        break;
                    case 6:
                    case 14:
                        cshData();
                    case 5:
                    case 16:
                        TQD = false;
                        break;
                }
                break;
            case 3:
                switch (bean.getsJM()[0]) {
                    case 0:
                    case 3:
                        ThQD = true;
                        break;
                    case 6:
                    case 14:
                        cshData();
                    case 5:
                    case 16:
                        ThQD = false;
                        break;
                }
                break;
            case 4:
                switch (bean.getsJM()[0]) {
                    case 0:
                    case 3:
                        FQD = true;
                        break;
                    case 6:
                    case 14:
                        cshData();
                    case 5:
                    case 16:
                        FQD = false;
                        break;
                }
                break;
            case 5:
                switch (bean.getsJM()[0]) {
                    case 0:
                    case 3:
                        FiQD = true;
                        break;
                    case 6:
                    case 14:
                        cshData();
                    case 5:
                    case 16:
                        FiQD = false;
                        break;
                }
                break;
            case 6:
                switch (bean.getsJM()[0]) {
                    case 0:
                    case 3:
                        SQD = true;
                        break;
                    case 6:
                    case 14:
                        cshData();
                    case 5:
                    case 16:
                        SQD = false;
                        break;
                }
                break;
            case 7:
                switch (bean.getsJM()[0]) {
                    case 0:
                    case 3:
                        SeQD = true;
                        break;
                    case 6:
                    case 14:
                        cshData();
                    case 5:
                    case 16:
                        SeQD = false;
                        break;
                }
                break;
            case 8:
                switch (bean.getsJM()[0]) {
                    case 0:
                    case 3:
                        EQD = true;
                        break;
                    case 6:
                    case 14:
                        cshData();
                    case 5:
                    case 16:
                        EQD = false;
                        break;
                }
                break;
        }

    }

    //控制打印相关操作
    private void sendKZ(PMJstorageBean bean) {
        switch (bean.getP()) {
            case 1:
                switch (bean.getsJM()[1]) {
                    case 0:
                        cshData();
                        OQD = false;
                        break;
                    case 2:
                    case 4:
                        OQD = true;
                        break;
                }
                break;
            case 2:
                switch (bean.getsJM()[1]) {
                    case 0:
                        cshData();
                        TQD = false;
                        break;
                    case 2:
                    case 4:
                        TQD = true;
                        break;
                }
                break;
            case 3:
                switch (bean.getsJM()[1]) {
                    case 0:
                        cshData();
                        ThQD = false;
                        break;
                    case 2:
                    case 4:
                        ThQD = true;
                        break;
                }
                break;
            case 4:
                switch (bean.getsJM()[1]) {
                    case 0:
                        cshData();
                        FQD = false;
                        break;
                    case 2:
                    case 4:
                        FQD = true;
                        break;
                }
                break;
            case 5:
                switch (bean.getsJM()[1]) {
                    case 0:
                        cshData();
                        FiQD = false;
                        break;
                    case 2:
                    case 4:
                        FiQD = true;
                        break;
                }
                break;
            case 6:
                switch (bean.getsJM()[1]) {
                    case 0:
                        cshData();
                        SQD = false;
                        break;
                    case 2:
                    case 4:
                        SQD = true;
                        break;
                }
                break;
            case 7:
                switch (bean.getsJM()[1]) {
                    case 0:
                        cshData();
                        SeQD = false;
                        break;
                    case 2:
                    case 4:
                        SeQD = true;
                        break;
                }
                break;
            case 8:
                switch (bean.getsJM()[1]) {
                    case 0:
                        cshData();
                        EQD = false;
                        break;
                    case 2:
                    case 4:
                        EQD = true;
                        break;
                }
                break;

        }
    }

    //传输图片的多重操作
    private void sendSJ(int p, final byte[] sjm) {

        if (isZX) {
            switch (p) {
                case 1: //第一个喷头负责传输给界面
                    if (OFlag) { //单包
                        if (OHF < 2) { //单包只有一个
                            ++OHF;
                            if (datas != null && tcpClient != null) {
                                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{1, 0}, datas.get(datas.size() - 1));
                                //byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{1, 0}, ArraysToNewUtils.aa(datas.get(datas.size() - 1)));
                                tcpClient.sendCSTP(bytedata);
                            }
                        } else {
                            if (mList.size() != 0) {
                                MLog.v("1传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList.get(0).getTPXH());
                                lockdata(mList);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isODY = false;
                            }
                            OCS = true;
                            //cstpCL(1, sjm);
                            mListener.socketSendCSTP(0, sjm);
                            if (isYD) {
                                sendTP(1);
                            }
                        }
                    } else { //多包
                        if (OHF < OCD + 1) {
                            if (OHF == OCD) {
                                ++OHF;
                                if (datas != null && tcpClient != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas.get(datas.size() - 1), (OHF - 2) * 1024, datas.get(datas.size() - 1).length);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(OCD, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient.sendCSTP(bytedata);
                                }

                            } else {
                                ++OHF;
                                if (datas != null && tcpClient != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas.get(datas.size() - 1), (OHF - 2) * 1024, (OHF - 2) * 1024 + 1024);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(OHF - 1, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient.sendCSTP(bytedata);
                                }
                            }
                        } else {
                            if (mList.size() != 0) {
                                MLog.v("1传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList.get(0).getTPXH());
                                lockdata(mList);
                            }
                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isODY = false;
                            }
                            OCS = true;
                            //cstpCL(1, sjm);
                            mListener.socketSendCSTP(0, sjm);
                            if (isYD) {
                                sendTP(1);
                            }
                        }
                    }
                    break;
                case 2:
                    if (TFlag) { //单包
                        if (THF < 2) { //单包只有一个
                            ++THF;
                            if (datas2 != null && tcpClient2 != null) {
                                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{1, 0}, datas2.get(datas2.size() - 2));
                                tcpClient2.sendCSTP(bytedata);
                            }
                        } else {
                            if (mList2.size() != 0) {
                                MLog.v("2传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList2.get(0).getTPXH());
                                lockdata(mList2);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isTDY = false;
                            }
                            TCS = true;
                            //cstpCL(2, sjm);
                            if (isYD) {
                                sendTP(2);
                            }
                        }
                    } else { //多包
                        if (THF < TCD + 1) {
                            if (THF == TCD) {
                                ++THF;
                                if (datas2 != null && tcpClient2 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas2.get(datas2.size() - 2), (THF - 2) * 1024, datas2.get(datas2.size() - 2).length);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(TCD, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient2.sendCSTP(bytedata);
                                }
                            } else {
                                ++THF;
                                if (datas2 != null && tcpClient2 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas2.get(datas2.size() - 2), (THF - 2) * 1024, (THF - 2) * 1024 + 1024);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(THF - 1, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient2.sendCSTP(bytedata);
                                }
                            }
                        } else {
                            if (mList2.size() != 0) {
                                MLog.v("2传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList2.get(0).getTPXH());
                                lockdata(mList2);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isTDY = false;
                            }
                            TCS = true;
                            //cstpCL(2, sjm);
                            if (isYD) {
                                sendTP(2);
                            }
                        }
                    }
                    break;
                case 3:
                    if (ThFlag) { //单包
                        if (ThHF < 2) { //单包只有一个
                            ++ThHF;
                            if (datas3 != null && tcpClient3 != null) {
                                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{1, 0}, datas3.get(datas3.size() - 3));
                                tcpClient3.sendCSTP(bytedata);
                            }
                        } else {
                            if (mList3.size() != 0) {
                                MLog.v("3传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList3.get(0).getTPXH());
                                lockdata(mList3);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isThDY = false;
                            }
                            ThCS = true;
                            //cstpCL(3, sjm);
                            if (isYD) {
                                sendTP(3);
                            }
                        }
                    } else { //多包
                        if (ThHF < ThCD + 1) {
                            if (ThHF == ThCD) {
                                ++ThHF;
                                if (datas3 != null && tcpClient3 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas3.get(datas3.size() - 3), (ThHF - 2) * 1024, datas3.get(datas3.size() - 3).length);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(ThCD, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient3.sendCSTP(bytedata);
                                }

                            } else {
                                ++ThHF;
                                if (datas3 != null && tcpClient3 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas3.get(datas3.size() - 3), (ThHF - 2) * 1024, (ThHF - 2) * 1024 + 1024);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(ThHF - 1, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient3.sendCSTP(bytedata);
                                }
                            }
                        } else {
                            if (mList3.size() != 0) {
                                MLog.v("3传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList3.get(0).getTPXH());
                                lockdata(mList3);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isThDY = false;
                            }

                            ThCS = true;
                            //cstpCL(3, sjm);
                            if (isYD) {
                                sendTP(3);
                            }
                        }
                    }
                    break;
                case 4:
                    if (FFlag) { //单包
                        if (FHF < 2) { //单包只有一个
                            ++FHF;
                            if (datas4 != null && tcpClient4 != null) {
                                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{1, 0}, datas4.get(datas4.size() - 4));
                                tcpClient4.sendCSTP(bytedata);
                            }
                        } else {
                            if (mList4.size() != 0) {
                                MLog.v("4传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList4.get(0).getTPXH());
                                lockdata(mList4);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isFDY = false;
                            }

                            FCS = true;
                            if (isYD) {
                                sendTP(4);
                            }
                            //cstpCL(4, sjm);
                        }
                    } else { //多包
                        if (FHF < FCD + 1) {
                            if (FHF == FCD) {
                                ++FHF;
                                if (datas4 != null && tcpClient4 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas4.get(datas4.size() - 4), (FHF - 2) * 1024, datas4.get(datas4.size() - 4).length);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(FCD, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient4.sendCSTP(bytedata);
                                }

                            } else {
                                ++FHF;
                                if (datas4 != null && tcpClient4 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas4.get(datas4.size() - 4), (FHF - 2) * 1024, (FHF - 2) * 1024 + 1024);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(FHF - 1, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient4.sendCSTP(bytedata);
                                }
                            }
                        } else {
                            if (mList4.size() != 0) {
                                MLog.v("4传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList4.get(0).getTPXH());
                                lockdata(mList4);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isFDY = false;
                            }

                            FCS = true;
                            if (isYD) {
                                sendTP(4);
                            }
                            //cstpCL(4, sjm);
                        }
                    }
                    break;
                case 5:
                    if (FiFlag) { //单包
                        if (FiHF < 2) { //单包只有一个
                            ++FiHF;
                            if (datas5 != null && tcpClient5 != null) {
                                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{1, 0}, datas5.get(datas5.size() - 5));
                                tcpClient5.sendCSTP(bytedata);
                            }
                        } else {
                            if (mList5.size() != 0) {
                                MLog.v("5传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList5.get(0).getTPXH());
                                lockdata(mList5);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isFiDY = false;
                            }

                            FiCS = true;
                            if (isYD) {
                                sendTP(5);
                            }
                            //cstpCL(5, sjm);
                        }
                    } else { //多包
                        if (FiHF < FiCD + 1) {
                            if (FiHF == FiCD) {
                                ++FiHF;
                                if (datas5 != null && tcpClient5 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas5.get(datas5.size() - 5), (FiHF - 2) * 1024, datas5.get(datas5.size() - 5).length);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(FiCD, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient5.sendCSTP(bytedata);
                                }

                            } else {
                                ++FiHF;
                                if (datas5 != null && tcpClient5 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas5.get(datas5.size() - 5), (FiHF - 2) * 1024, (FiHF - 2) * 1024 + 1024);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(FiHF - 1, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient5.sendCSTP(bytedata);
                                }
                            }
                        } else {
                            if (mList5.size() != 0) {
                                MLog.v("5传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList5.get(0).getTPXH());
                                lockdata(mList5);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isFiDY = false;
                            }

                            FiCS = true;
                            if (isYD) {
                                sendTP(5);
                            }
                            //cstpCL(5, sjm);
                        }
                    }
                    break;
                case 6:
                    if (SFlag) { //单包
                        if (SHF < 2) { //单包只有一个
                            ++SHF;
                            if (datas6 != null && tcpClient6 != null) {
                                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{1, 0}, datas6.get(datas6.size() - 6));
                                tcpClient6.sendCSTP(bytedata);
                            }
                        } else {
                            if (mList6.size() != 0) {
                                MLog.v("6传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList6.get(0).getTPXH());
                                lockdata(mList6);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isSDY = false;
                            }

                            SCS = true;
                            if (isYD) {
                                sendTP(6);
                            }

                            //cstpCL(6, sjm);
                        }
                    } else { //多包
                        if (SHF < SCD + 1) {
                            if (SHF == SCD) {
                                ++SHF;
                                if (datas6 != null && tcpClient6 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas6.get(datas6.size() - 6), (SHF - 2) * 1024, datas6.get(datas6.size() - 6).length);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(SCD, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient6.sendCSTP(bytedata);
                                }

                            } else {
                                ++SHF;
                                if (datas6 != null && tcpClient6 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas6.get(datas6.size() - 6), (SHF - 2) * 1024, (SHF - 2) * 1024 + 1024);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(SHF - 1, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient6.sendCSTP(bytedata);
                                }
                            }
                        } else {
                            if (mList6.size() != 0) {
                                MLog.v("6传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList6.get(0).getTPXH());
                                lockdata(mList6);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isSDY = false;
                            }

                            SCS = true;
                            if (isYD) {
                                sendTP(6);
                            }

                            //cstpCL(6, sjm);
                        }
                    }
                    break;
                case 7:
                    if (SeFlag) { //单包
                        if (SeHF < 2) { //单包只有一个
                            ++SeHF;
                            if (datas7 != null && tcpClient7 != null) {
                                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{1, 0}, datas7.get(datas7.size() - 7));
                                tcpClient7.sendCSTP(bytedata);
                            }
                        } else {
                            if (mList7.size() != 0) {
                                MLog.v("7传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList7.get(0).getTPXH());
                                lockdata(mList7);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isSeDY = false;
                            }

                            SeCS = true;
                            if (isYD) {
                                sendTP(7);
                            }
                            //cstpCL(7, sjm);
                        }
                    } else { //多包
                        if (SeHF < SeCD + 1) {
                            if (SeHF == SeCD) {
                                ++SeHF;
                                if (datas7 != null && tcpClient7 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas7.get(datas7.size() - 7), (SeHF - 2) * 1024, datas7.get(datas7.size() - 7).length);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(SeCD, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient7.sendCSTP(bytedata);
                                }

                            } else {
                                ++SeHF;
                                if (datas7 != null && tcpClient7 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas7.get(datas7.size() - 7), (SeHF - 2) * 1024, (SeHF - 2) * 1024 + 1024);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(SeHF - 1, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient7.sendCSTP(bytedata);
                                }
                            }
                        } else {
                            if (mList7.size() != 0) {
                                MLog.v("7传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList7.get(0).getTPXH());
                                lockdata(mList7);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isSeDY = false;
                            }

                            SeCS = true;
                            if (isYD) {
                                sendTP(7);
                            }
                            //cstpCL(7, sjm);
                        }
                    }
                    break;
                case 8:
                    if (EFlag) { //单包
                        if (EHF < 2) { //单包只有一个
                            ++EHF;
                            if (datas8 != null && tcpClient8 != null) {
                                byte[] bytedata = ByteMergerUtils.byteMerger(new byte[]{1, 0}, datas8.get(datas8.size() - 8));
                                tcpClient8.sendCSTP(bytedata);
                            }
                        } else {
                            if (mList8.size() != 0) {
                                MLog.v("8传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList8.get(0).getTPXH());
                                lockdata(mList8);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isEDY = false;
                            }

                            ECS = true;
                            if (isYD) {
                                sendTP(8);
                            }

                            //cstpCL(8, sjm);
                        }
                    } else { //多包
                        if (EHF < ECD + 1) {
                            if (EHF == ECD) {
                                ++EHF;
                                if (datas8 != null && tcpClient8 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas8.get(datas8.size() - 8), (EHF - 2) * 1024, datas8.get(datas8.size() - 8).length);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(ECD, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient8.sendCSTP(bytedata);
                                }

                            } else {
                                ++EHF;
                                if (datas8 != null && tcpClient8 != null) {
                                    byte[] byte1 = Arrays.copyOfRange(datas8.get(datas8.size() - 8), (EHF - 2) * 1024, (EHF - 2) * 1024 + 1024);
                                    byte[] byte2 = LongIntToBytesUtils.intToBytesXDTY(EHF - 1, 2);
                                    byte[] bytedata = ByteMergerUtils.byteMerger(byte2, byte1);
                                    tcpClient8.sendCSTP(bytedata);
                                }
                            }
                        } else {
                            if (mList8.size() != 0) {
                                MLog.v("8传完:" + LongIntToBytesUtils.bytesToInt0(sjm) + ":" + LongIntToBytesUtils.bytesToInt4(sjm) + ":" + mList8.get(0).getTPXH());
                                lockdata(mList8);
                            }

                            if (LongIntToBytesUtils.bytesToInt0(sjm) > Controlflag.huancun) {
                                isEDY = false;
                            }
                            ECS = true;
                            if (isYD) {
                                sendTP(8);
                            }
                            //cstpCL(8, sjm);
                        }
                    }
                    break;
            }
        }
    }

    /**
     *  //todo 关闭的方法
     */
    public void close(boolean...flag) {
        MLog.v("zhongzhi");
        if (flag.length == 0 || !flag[0]) { //用户关闭时执行
//            if (SerialPortHelper.getInstance() != null) {
//                SerialPortHelper.getInstance().sendQD(0);
//            }
            sendKZDY(new byte[]{3}, 100);
        }
        isZX = false;
        SystemClock.sleep(100);
        if (utils != null) {
            if (timer != null) {
                timer.purge();
                timer.cancel();
                timer = null;
            }
            if (timer2 != null) {
                timer2.cancel();
                timer2 = null;
            }
            if (flag.length == 0) {
                //mContext.unregisterReceiver(myBroadcastReceiver); //关闭广播
                exec.shutdown(); //关闭线程池
            }
            if (tcpClient != null) { //关闭通讯
                tcpClient.closeSelf();
                tcpClient.releaseSocket();
                tcpClient = null;
            }
            if (tcpClient2 != null) { //关闭通讯
                tcpClient2.closeSelf();
                tcpClient2.releaseSocket();
                tcpClient2 = null;
            }
            if (tcpClient3 != null) { //关闭通讯
                tcpClient3.closeSelf();
                tcpClient3.releaseSocket();
                tcpClient3 = null;
            }
            if (tcpClient4 != null) { //关闭通讯
                tcpClient4.closeSelf();
                tcpClient4.releaseSocket();
                tcpClient4 = null;
            }
            if (tcpClient5 != null) { //关闭通讯
                tcpClient5.closeSelf();
                tcpClient5.releaseSocket();
                tcpClient5 = null;
            }
            if (tcpClient6 != null) { //关闭通讯
                tcpClient6.closeSelf();
                tcpClient6.releaseSocket();
                tcpClient6 = null;
            }
            if (tcpClient7 != null) { //关闭通讯
                tcpClient7.closeSelf();
                tcpClient7.releaseSocket();
                tcpClient7 = null;
            }
            if (tcpClient8 != null) { //关闭通讯
                tcpClient8.closeSelf();
                tcpClient8.releaseSocket();
                tcpClient8 = null;
            }
            if (flag.length == 0) {
                utils = null; //置空
            } else if (flag.length == 1 && flag[0]) {
                cshData();
                //关闭并重连
                tcpCL();
            } else {
                cshData();
            }
        }
    }

    //重新初始化数据
    private void cshData() {

        OSJM = 0;
        TSJM = 0;
        ThSJM = 0;
        FSJM = 0;
        FiSJM = 0;
        SSJM = 0;
        SeSJM = 0;
        ESJM = 0;

        TPXH = 0;

        whc = 0;

        OCS = true;
        TCS = true;
        ThCS = true;
        FCS = true;
        FiCS = true;
        SCS = true;
        SeCS = true;
        ECS = true;

        isSH = false;

        isOWHC = false;
        isTWHC = false;
        isThWHC = false;
        isFWHC = false;
        isFiWHC = false;
        isSWHC = false;
        isSeWHC = false;
        isEWHC = false;

        isODY = false;
        isTDY = false;
        isThDY = false;
        isFDY = false;
        isFiDY = false;
        isSDY = false;
        isSeDY = false;
        isEDY = false;

        isYD = false;

        mList.clear();
        mList2.clear();
        mList3.clear();
        mList4.clear();
        mList5.clear();
        mList6.clear();
        mList7.clear();
        mList8.clear();

        kzList.clear();
        qdList.clear();
        tcpOK.clear();
    }
}
