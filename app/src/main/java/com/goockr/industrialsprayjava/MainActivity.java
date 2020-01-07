package com.goockr.industrialsprayjava;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.goockr.industrialsprayjava.bean.InkpotInfoItem;
import com.goockr.industrialsprayjava.tools.BaseUtils;
import com.goockr.industrialsprayjava.tools.FileUtils;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.goockr.industrialsprayjava.tools.ToastUtils;
import com.goockr.industrialsprayjava.ui.FileManagerFragment;
import com.goockr.industrialsprayjava.ui.HomeFragment;
import com.goockr.industrialsprayjava.ui.InfoEditFragment;
import com.goockr.industrialsprayjava.ui.MachineParamarsFragment;
import com.goockr.industrialsprayjava.ui.PrintParamarsFragment;
import com.goockr.industrialsprayjava.ui.ServiceMenuFragment;
import com.goockr.pmj.LocalControlerUtil;
import com.goockr.pmj.bean.CJCopeBean;
import com.goockr.pmj.bean.CSDataBean;
import com.goockr.pmj.bean.ErrorBean;
import com.goockr.pmj.bean.MHdataBean;
import com.goockr.pmj.listener.LocalControlerListener;
import com.goockr.pmj.utils.LongIntToBytesUtils;
import com.goockr.pmj.utils.MLog;
import com.goockr.pmj_b.SerialPortHelper;
import com.goockr.pmj_b.SerialUtils;
import com.goockr.pmj_b.listener.OnReceivedListener;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionGen;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity {

    @BindView(R.id.textClock)
    TextView textClock;
    @BindView(R.id.btnInfoEdit)
    Button btnInfoEdit;
    @BindView(R.id.btnFileManager)
    Button btnFileManager;
    @BindView(R.id.btnPrintParameter)
    Button btnPrintParameter;
    @BindView(R.id.btnMachineParameter)
    Button btnMachineParameter;
    @BindView(R.id.btnService)
    Button btnService;
    @BindView(R.id.ivMore)
    ImageButton ivMore;
    @BindView(R.id.btnShowControlView)
    Button btnShowControlView;
    @BindView(R.id.controlView)
    LinearLayout controlView;

    int FIRST = 0;
    int SECOND = 1;
    int THIRD = 2;
    int FOURTH = 3;
    int FIVE = 4;
    int SIX = 5;

    @BindView(R.id.ibtnTopMove)
    ImageButton ibtnTopMove;
    @BindView(R.id.ibtnLeftMove)
    ImageButton ibtnLeftMove;
    @BindView(R.id.ibtnRightMove)
    ImageButton ibtnRightMove;
    @BindView(R.id.ibtnBottonMove)
    ImageButton ibtnBottonMove;
    @BindView(R.id.ibtnAdd)
    ImageButton ibtnAdd;
    @BindView(R.id.ibtnSub)
    ImageButton ibtnSub;
    @BindView(R.id.btnGrade)
    Button btnGrade;
    @BindView(R.id.MachinePrintStatus)
    TextView MachinePrintStatus;
    @BindView(R.id.MachineStatus)
    TextView MachineStatus;
    @BindView(R.id.MachineStatusType)
    TextView MachineStatusType;

    private Timer timer;
    private int currentPotion = 0;
    private List<SupportFragment> fragments = new ArrayList<SupportFragment>(6);

    private int nowGrade = 1;
    private HomeFragment homeFragment;
    private InfoEditFragment infoEditFragment;
    private FileManagerFragment fileManagerFragment;
    private PrintParamarsFragment printParamarsFragment;
    private MachineParamarsFragment machineParamarsFragment;
    private ServiceMenuFragment serviceMenuFragment;

    private static MainActivity instance;

    private boolean isNeedPwd = true;
    private Context context;
    private int type = 0;
    //private String[] ConnectIp = {"192.168.1.151", "192.168.1.155", "192.168.1.156",  "192.168.1.154", "192.168.1.153", "192.168.1.152", "192.168.1.151", "192.168.1.155"};
    private String[] ConnectIp = {"192.168.1.151", "192.168.1.152", "192.168.1.153", "192.168.1.154", "192.168.1.155", "192.168.1.156", "192.168.1.157", "192.168.1.158"};

    private int MinInkAllowance = 10;
    private boolean isInkAllowance1, isInkAllowance2, isInkAllowance3, isInkAllowance4,
            isInkAllowance5, isInkAllowance6, isInkAllowance7, isInkAllowance8;
    public LocalControlerUtil mLocalControlerUtil;
    public boolean printIsOk = false;
    private int times = 240;

    private Timer mTimer; //重连定时器
    public boolean clFlag = false; //自动重连的标记
    public boolean noOne = false;
    public int clFlag2 = 0;

    public static MainActivity getInstance() {
        return instance;
    }

    public static boolean cb = false; //用来判断定时测试

    //创建串口通讯类及相关数据
    private String path = "/dev/ttyS1";
    private int baudrate = 9600;
    private SerialUtils serialUtil;
    public SerialPortHelper portHelper;

    private int yuan; //原来的喷头数

    /*********************************************************************/

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this); //控件获取

        BaseUtils.StoppageSetting3();

        HomeFragment firstFragment = findFragment(HomeFragment.class); //第一个fragment
        //基本数据
        context = this;
        instance = this;
        //相应fragment加载
        homeFragment = HomeFragment.newInstance();
        infoEditFragment = InfoEditFragment.newInstance();
        fileManagerFragment = FileManagerFragment.newInstance();
        printParamarsFragment = PrintParamarsFragment.newInstance();
        machineParamarsFragment = MachineParamarsFragment.newInstance();
        serviceMenuFragment = ServiceMenuFragment.newInstance();
        if (firstFragment == null) {
            fragments.add(FIRST, homeFragment);
            fragments.add(SECOND, infoEditFragment);
            fragments.add(THIRD, fileManagerFragment);
            fragments.add(FOURTH, printParamarsFragment);
            fragments.add(FIVE, machineParamarsFragment);
            fragments.add(SIX, serviceMenuFragment);

            loadMultipleRootFragment(R.id.mainFrame, FIRST,
                    fragments.get(FIRST),
                    fragments.get(SECOND),
                    fragments.get(THIRD),
                    fragments.get(FOURTH),
                    fragments.get(FIVE),
                    fragments.get(SIX));
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            fragments.add(FIRST, firstFragment);
            fragments.add(SECOND, findFragment(InfoEditFragment.class));
            fragments.add(THIRD, findFragment(FileManagerFragment.class));
            fragments.add(FOURTH, findFragment(PrintParamarsFragment.class));
            fragments.add(FIVE, findFragment(MachineParamarsFragment.class));
            fragments.add(SIX, findFragment(ServiceMenuFragment.class));
        }

        //初始化串口
        initCK();
        //初始化通讯
        initView(false);
        initCKHD();
        //定时查询墨盒信息
        initTime();
        //权限管理
        requestPhotoPermiss();

        //ivMore.performClick();
    }

    //定时查询墨盒信息
    private void initTime() {
        timer = new Timer(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                GoockrLog.LogIn("timer+ " + Thread.currentThread().getName());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String format2 = new SimpleDateFormat("yyyy-MM-dd \n HH ：mm ：ss").format(System.currentTimeMillis());
                        textClock.setText(format2);
                        if (times++ > 300 && printIsOk) {
                            times = 0;
                            mLocalControlerUtil.sendCXXX(new byte[]{0}, 0);
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    //权限管理
    private void requestPhotoPermiss() {
        PermissionGen.with(MainActivity.this)
                .addRequestCode(1001)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .request();
    }

    //串口回调
    private void initCKHD() {
        portHelper.setOnReceivedListener(new OnReceivedListener() { //数据回调接口
            @Override
            public void onReceived1(byte[] bytes) { //启动回复
                Log.v("xxxxxf", "启动回复了" + Arrays.toString(bytes));

            }

            @Override
            public void onReceived2(final byte[] bytes) { //故障回复
                Log.v( "xxxxxf","故障回复了" + Arrays.toString(bytes));
                portHelper.sendGZ();
                SystemClock.sleep(50);
                portHelper.sendQD(0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //homeFragment.StopPrint();
                        ToastUtils.ToastShow("打印错位");
                        homeFragment.isNeedReConnect(true);
                        printIsOk = false;
                        mLocalControlerUtil.close(false);
                        //homeFragment.isNeedReConnect(true);
                        StringBuilder s = new StringBuilder();
                        for (int i = 4; i < 4 + bytes[3]; i++) {
                            if (i == 3 + bytes[3]) {
                                s.append(bytes[i]).append("号喷头");
                            } else {
                                s.append(bytes[i]).append(",");
                            }
                        }
                        SettingStatus(2, "打印错位:" + s.toString(), 0); //p=0
                    }
                });
            }
        });
    }

    private boolean iscs = true; //用于单次判是否传图片

    //串口开启
    private void initCK() {
        //串口初始化
        try {
            serialUtil = new SerialUtils(path, baudrate, 0);
            portHelper = SerialPortHelper.getInstance(serialUtil);
        } catch (NullPointerException e) {
            MLog.v("串口设置出错");
            Toast.makeText(this, "串口设置出错", Toast.LENGTH_SHORT).show();
        }

    }

    //pmj通讯开启
    private void initView(boolean flag) {

        if (flag) { //重新替换喷头数量时需要关闭在开启
            if (mLocalControlerUtil != null) {
                mLocalControlerUtil.close();
                mLocalControlerUtil = null;
            }
        }
        mLocalControlerUtil = LocalControlerUtil.getInstance(this, new LocalControlerListener() {
            /**
             *  接口返回的一般数据
             * @param ebean
             */
            @Override
            public void socketSendString(final ErrorBean ebean) {
                Message message = handler.obtainMessage();
                message.what = 0;
                message.obj = ebean;
                handler.sendMessage(message);
            }

            /**
             *  启动打印的返回
             * @param p 喷头
             * @param datas 数据码
             */
            @Override
            public void socketSendQDDY(final int p, final byte[] datas) {
                Message message = Message.obtain();
                message.what = 1;
                message.arg1 = p;
                message.obj = datas;
                handler.sendMessage(message);
            }

            /**
             * 传输图片的返回
             * @param p 喷头
             * @param datas 数据码
             */
            @Override
            public void socketSendCSTP(final int p, final byte[] datas) {
                Message message = Message.obtain();
                message.what = 2;
                message.arg1 = p;
                message.obj = datas;
                handler.sendMessage(message);
            }

            /**
             *  检测图片剩余
             * @param a 0:不足10张 1:达到30张 -1:没有图片
             */
            @Override
            public void socketSendHCBZ(final int a) {

                if (a == 1) {
                    String cc8 = "类型码: 2.5" + "; 数据码: " + a;
                    MLog.v(cc8);
                    iscs = true;
                    homeFragment.sendPrintCount = false;
                } else {
                    if (iscs) {
                        String cc8 = "类型码: 2.5" + "; 数据码: " + a;
                        MLog.v(cc8);
                        iscs = false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                homeFragment.sendBmp(true);
                            }
                        });
                    }
                }
            }

            /**
             *  控制打印的返回
             * @param p 喷头
             * @param datas 数据码
             */
            @Override
            public void socketSendKZDY(int p, byte[] datas) {
                Message message = Message.obtain();
                message.what = 3;
                message.arg1 = p;
                message.obj = datas;
                handler.sendMessage(message);
            }

            /**
             *  上传错误信息的返回
             * @param p 喷头
             * @param datas 错误信息
             */
            @Override
            public void socketSendSCXX(int p, String datas) {
                handler.sendEmptyMessage(4);
            }

            /**
             * 查询墨盒信息的返回
             *
             * @param p     喷头
             * @param datas 数据码
             */
            @Override
            public void socketSendCXXX(int p, MHdataBean datas) {
                Message message = Message.obtain();
                message.what = 5;
                message.arg1 = p;
                message.obj = datas;
                handler.sendMessage(message);
            }

            /**
             * 升级程序的返回
             * @param p 喷头
             * @param datas 数据码
             */
            @Override
            public void socketSendSJCX(int p, byte[] datas) {
                //todo
                handler.sendEmptyMessage(6);
            }

            /**
             *      应答打印的返回
             * @param p 喷头
             * @param datas 数据码
             */
            @Override
            public void socketSendYDDY(int p, byte[] datas) {
                dayin = LongIntToBytesUtils.bytesToInt3(datas);
                Message message = Message.obtain();
                message.what = 7;
                message.arg1 = p;
                handler.sendMessage(message);
            }

        }, ConnectIp, PreferenceUtils.getInstance().nozzleCount);

        yuan = PreferenceUtils.getInstance().nozzleCount;
        portHelper.sendGZ();
        SystemClock.sleep(50);
        portHelper.sendQD(0);
    }

    private int dayin;

    private MyHandler handler = new MyHandler(this);

    private class MyHandler extends Handler {

        private WeakReference<MainActivity> mFragmentWeakReference;

        MyHandler(MainActivity main){
            mFragmentWeakReference = new WeakReference<MainActivity>(main);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: //一般数据
                    ErrorBean ebean = (ErrorBean) msg.obj;
                    final String cc0 = "类型码: 0"+ "; 喷头:" + ebean.getP() + "; 数据码: " + ebean.getData() + "; w = " + ebean.getW() + ":" + ebean.getZTM();
                    MLog.v(cc0);
                    switch (ebean.getZTM()) {
                        case 1:
                            clFlag2 = 0;
                            ToastUtils.ToastShow(ebean.getData());
                            homeFragment.isNeedReConnect(false);
                            printIsOk = true;
                            SettingStatus(0, ebean.getData(), ebean.getP());
                            homeFragment.settingInkBox2(ebean.getP(), true, PreferenceUtils.getInstance().nozzleCount);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                        if (clFlag) {
                                            homeFragment.StartPrint(false);
                                        }
                                }
                            }, 2000);
                            break;
                        case 0:
                            ToastUtils.ToastShow(ebean.getData());
                            SettingStatus(3, ebean.getData(), ebean.getP());
                            break;
                        case -1:
                            homeFragment.coverMsg();
                            ToastUtils.ToastShow(ebean.getData());
                            printIsOk = false;
                            homeFragment.isNeedReConnect(true);
                            switch (ebean.getW()) {
                                case 0:
                                    SettingStatus(2, getString(R.string.pingno), ebean.getP()); //p=0
                                    break;
                                case 19:
                                    SettingStatus(2, getString(R.string.cscc), ebean.getP()); //p有值
                                    break;
                                case 14:
                                    SettingStatus(2, getString(R.string.dpjtzgz), ebean.getP()); //p=0
                                    break;
                                case 17:
                                    SettingStatus(2, getString(R.string.dycw), ebean.getP()); //p有值
                                    break;
                                case -1:
                                    SettingStatus(2, getString(R.string.dpjwfh), ebean.getP()); //p有值
                                    break;
                                case 15:
                                    SettingStatus(2, getString(R.string.hcbz), ebean.getP()); //p有值
                                    break;
                            }
                            break;
                        case 2:
                            SettingStatus(3, ebean.getData(), ebean.getP());
                            homeFragment.settingInkBox2(ebean.getW(), false, PreferenceUtils.getInstance().nozzleCount);
                            break;
                        case 5:
                            homeFragment.isStart = 0;
                            homeFragment.setibstart();
                            break;
                    }
                    break;
                case 1: //启动打印
                    int p1 = msg.arg1;
                    byte[] datas1 = (byte[]) msg.obj;
                    String cc1 = "类型码: 1"+ "; 喷头:" + p1 + "; 数据码: " + datas1[0];
                    MLog.v(cc1);
                    if (datas1[0] == 0) {
                        //portHelper.sendQD(PreferenceUtils.getInstance().nozzleCount); //串口启动
                        SettingPrintStatus(2);
                        //SystemClock.sleep(3000);
                        BaseUtils.StoppageSetting(1, false); //开启光眼
                    } else if (datas1[0] == 16) {
                        ToastUtils.ToastShow("输入的启动参数出错,请检测");
                        SettingPrintStatus(100);
                        homeFragment.sendPrintCount = false;
                    }
                    break;
                case 2: //传输图片
                    byte[] datas2 = (byte[]) msg.obj;

                    if (datas2[0] == 0) {
                        SettingPrintStatus(3);
                    } else if (datas2[0] == -1) {
                        homeFragment.StopPrint();
                        SettingPrintStatus(5);
                    } else {
                        SettingPrintStatus(6);
                    }
                    break;
                case 3: //控制打印
                    int p3 = msg.arg1;
                    byte[] datas3 = (byte[]) msg.obj;
                    String cc = "类型码: 3"+ "; 喷头:" + p3 + "; 数据码: " + datas3[0] + ":" + datas3[1];
                    MLog.v(cc);
                    if (datas3[0] == 0) {
                        SettingPrintStatus(datas3[1]);
                    }
                    break;
                case 4: //上传错误
                    break;
                case 5: //查询墨盒
                    int p = msg.arg1;
                    MHdataBean datas = (MHdataBean) msg.obj;
                    InkpotInfoItem inkpotInfoItem = new InkpotInfoItem(p + "", datas.getMSXH(), datas.getJXS(), datas.getMSYL() + "", datas.getSCRQ(), datas.getBZQ());
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(inkpotInfoItem);
                    homeFragment.SettingInkBox(p, datas);
                    switch (p) {
                        case 1:
                            isInkAllowance1 = datas.getMSYL() >= MinInkAllowance;
                            PreferenceUtils.getInstance().setInkBox1(jsonObject.toJSONString());
                            break;
                        case 2:
                            isInkAllowance2 = datas.getMSYL() >= MinInkAllowance;
                            PreferenceUtils.getInstance().setInkBox2(jsonObject.toJSONString());
                            break;
                        case 3:
                            isInkAllowance3 = datas.getMSYL() >= MinInkAllowance;
                            PreferenceUtils.getInstance().setInkBox3(jsonObject.toJSONString());
                            break;
                        case 4:
                            isInkAllowance4 = datas.getMSYL() >= MinInkAllowance;
                            PreferenceUtils.getInstance().setInkBox4(jsonObject.toJSONString());
                            break;
                        case 5:
                            isInkAllowance5 = datas.getMSYL() >= MinInkAllowance;
                            PreferenceUtils.getInstance().setInkBox5(jsonObject.toJSONString());
                            break;
                        case 6:
                            isInkAllowance6 = datas.getMSYL() >= MinInkAllowance;
                            PreferenceUtils.getInstance().setInkBox6(jsonObject.toJSONString());
                            break;
                        case 7:
                            isInkAllowance7 = datas.getMSYL() >= MinInkAllowance;
                            PreferenceUtils.getInstance().setInkBox7(jsonObject.toJSONString());
                            break;
                        case 8:
                            isInkAllowance8 = datas.getMSYL() >= MinInkAllowance;
                            PreferenceUtils.getInstance().setInkBox8(jsonObject.toJSONString());
                            break;
                    }
                    if (!(isInkAllowance1 && isInkAllowance2 && isInkAllowance3 && isInkAllowance4 && isInkAllowance5 && isInkAllowance6 && isInkAllowance7 && isInkAllowance8)) {
                        SettingStatus(1, "", 0);
                    }
                    if (type == 2) {
                        machineParamarsFragment.getInkInfo();
                    }
                    break;
                case 6: //升级程序
                    break;
                case 7: //应答

                    if (msg.arg1 == 1) {
                        PreferenceUtils.getInstance().setCount();
                        homeFragment.updatePrint(1);
                    } else if (msg.arg1 == 2) {
                        homeFragment.updatePrint(2);
                    }

                    if (cb) {
                        homeFragment.sendBmp(true);
                    }
                    break;
                case 8:
                    //系统自动重连的发送数据
                    homeFragment.sendZD();
                    break;
            }
        }
    }

    public void StartPrint() {
        mLocalControlerUtil.sendQDDY();
    }

    @OnClick({R.id.btnInfoEdit, R.id.btnFileManager, R.id.btnPrintParameter, R.id.btnMachineParameter, R.id.btnService, R.id.ivMore, R.id.btnShowControlView, R.id.ibtnTopMove, R.id.ibtnLeftMove, R.id.ibtnRightMove, R.id.ibtnBottonMove, R.id.ibtnAdd, R.id.ibtnSub})
    public void onViewClicked(View view) {
        closeTimer();
        clFlag = false;
        clFlag2 = 0;
        switch (view.getId()) {
            case R.id.btnInfoEdit:
                //信息编辑
                showHind(1);
                break;
            case R.id.btnFileManager:
                //文件管理
                showHind(2);
                break;
            case R.id.btnPrintParameter:
                //打印参数
                showHind(3);
                break;
            case R.id.btnMachineParameter:
                //机器参数
                showHind(4);
                break;
            case R.id.btnService:
                //服务菜单
                showHind(5);
                break;
            case R.id.ivMore:
                //功能键
                showHind(0);
                break;
            case R.id.btnShowControlView:
                if (homeFragment.isStart != 0) {
                    ToastUtils.ToastShow("请先停止打印!");
                    return;
                }
                //主页
                if (controlView.getVisibility() == View.GONE) {
                    controlView.setVisibility(View.VISIBLE);
                } else {
                    controlView.setVisibility(View.GONE);
                }
                break;
            case R.id.ibtnTopMove:
                infoEditFragment.MoveText(1, nowGrade);
                break;
            case R.id.ibtnLeftMove:
                infoEditFragment.MoveText(2, nowGrade);
                break;
            case R.id.ibtnRightMove:
                infoEditFragment.MoveText(3, nowGrade);
                break;
            case R.id.ibtnBottonMove:
                infoEditFragment.MoveText(4, nowGrade);
                break;
            case R.id.ibtnAdd:
                setGrade(0);
                break;
            case R.id.ibtnSub:
                setGrade(1);
                break;
        }
    }

    private void setGrade(int tag) {
        if (tag == 0) {
            if (nowGrade >= 3) {
                return;
            }
            nowGrade++;
            if (nowGrade == 2) {
                btnGrade.setText("5mm");
            } else if (nowGrade == 3) {
                btnGrade.setText("10mm");
            }
        } else {
            if (nowGrade <= 1) {
                return;
            }
            nowGrade--;
            if (nowGrade == 2) {
                btnGrade.setText("5mm");
            } else if (nowGrade == 1) {
                btnGrade.setText("1mm");
            }
        }
    }

    public void sendMsg() {
        showHind(0);
        if (PreferenceUtils.getInstance().nozzleCount == yuan) { //判断是否出现喷头数变化
            initView(false);
        } else {
            initView(true);
        }
        if (homeFragment.isStart != 0) {
            homeFragment.StopPrint();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                homeFragment.loadInSdcard();
                homeFragment.SettingInkBox(PreferenceUtils.getInstance().nozzleCount);
            }
        }, 500);

    }

    public void showHind(final int index) {
        /*if (index != 3 && index != 0) {
            if (homeFragment.isStart != 0) {
                ToastUtils.ToastShow("请先停止打印!");
                return;
            }
        }*/

        if (isNeedPwd && currentPotion != index && PreferenceUtils.getInstance().isPwd) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final View view = LayoutInflater.from(context).inflate(R.layout.showpwd, null);
            final EditText editText = view.findViewById(R.id.EnterPwd);
            builder.setView(view);
            builder.setCancelable(false);
            builder.setTitle(getString(R.string.PleaseEnte))
                    .setNegativeButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!editText.getText().toString().trim().equals(PreferenceUtils.getInstance().pwd)) {
                                ToastUtils.ToastShow(getString(R.string.ErrorPwd));
                            } else {
                                ShowFrag(index);
                            }
                            hiddenInput(view);
                        }
                    })
                    .setPositiveButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hiddenInput(view);
                        }
                    }).setIcon(R.mipmap.ic_launcher).show();
        } else {
            ShowFrag(index);
        }
    }

    //关闭软键盘
    private void hiddenInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 获取软键盘的显示状态
        imm.isActive();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    private void ShowFrag(int index) {
        isNeedPwd = index == 0;
        if (index == 1) {
            btnShowControlView.setVisibility(View.VISIBLE);
        } else {
            btnShowControlView.setVisibility(View.INVISIBLE);
            controlView.setVisibility(View.GONE);
        }
        //主页
        if (currentPotion != index) {
            showHideFragment(fragments.get(index), fragments.get(currentPotion));
            currentPotion = index;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseUtils.StoppageSetting3();
        noOne = false;
        clFlag = false;
        clFlag2 = 0;
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        BaseUtils.StoppageSetting(1, true); //关闭光眼
        homeFragment.coverMsg(); //保存当前数据
        portHelper.sendGZ();
        SystemClock.sleep(50);
        portHelper.sendQD(0);
        portHelper.releaseReadThread();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (mLocalControlerUtil != null) {
            mLocalControlerUtil.close();
            mLocalControlerUtil = null;
        }
    }

    public void Chose(int index) {
        showHind(index);
        infoEditFragment.loadInSdcard();
    }

    //  0 ---> 正常  1 ---> 不影响打印   2 --->不能打印
    public void SettingStatus(int type, String errorMsg, int p) {
        //BaseUtils.StoppageSetting(type);
        switch (type) {
            case 0:
                MachinePrintStatus.setText(getString(R.string.NormalEquipment));
                MachinePrintStatus.setBackgroundColor(Color.argb(255, 50, 247, 101));
                break;
            case 1:
                MachinePrintStatus.setText(getString(R.string.ChangeInkBOx));
                MachinePrintStatus.setBackgroundColor(Color.argb(255, 255, 155, 0));
                break;
            case 2:
                if (noOne) {
                    BaseUtils.StoppageSetting4();
                    BaseUtils.StoppageSetting2(2);
                }
                homeFragment.StopPrint();

                MachinePrintStatus.setText("机器故障:" + errorMsg + "喷头:" + p);
                MachinePrintStatus.setBackgroundColor(Color.argb(255, 255, 0, 0));
                BaseUtils.StoppageSetting(1, true); //关闭光眼
                homeFragment.coverMsg(); //保存当前数据

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
                //获取当前时间
                Date date = new Date(System.currentTimeMillis());
                try {
                    FileUtils.writeWJ(simpleDateFormat.format(date) + " : " + errorMsg);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (mTimer != null) {
                   mTimer.cancel();
                   mTimer = null;
               }
                mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (noOne) {
                            clFlag = true;
                            ++clFlag2;
                            if (clFlag2 < 4) {
                                handler.sendEmptyMessage(8); //重连的标记
                            } else {
                                clFlag2 = 0;
                                noOne = false;
                            }
                        }
                    }
                }, 60000);
                break;
            case 3:
                MachinePrintStatus.setText(errorMsg);
                MachinePrintStatus.setBackgroundColor(Color.argb(255, 255, 155, 0));
                break;
        }
    }

    // 0、机器关闭     1、正在开机     2、可以打印    3、正在关机  4 正在打印
    //0(打印停止)、1(正在启动) 、2(打印就绪) 、3(正在打印)、4(打印暂停) 、5(打印故障)
    public void SettingPrintStatus(int type) {
        //BaseUtils.StoppageSetting(type);
        switch (type) {
            case 0:
                BaseUtils.StoppageSetting(1, true);
                homeFragment.coverMsg(); //保存当前数据
                MachineStatus.setText(getString(R.string.Printstop));
                homeFragment.isStart = 0;
                homeFragment.printCount = 0;
                homeFragment.printCount2 = 0;
                homeFragment.updatePrint(3);
                break;
            case 1:
                MachineStatus.setText(getString(R.string.Beingstarted));
                break;
            case 2:
                MachineStatus.setText(getString(R.string.Printreadiness));
                homeFragment.isStart = 1;
                homeFragment.mPromptDialog.dismiss();
                break;
            case 3:
                MachineStatus.setText(getString(R.string.printnow));
                break;
            case 4:
                MachineStatus.setText(getString(R.string.PrintPause));
                homeFragment.isStart = 2;
                break;
            case 5:
                MachineStatus.setText(getString(R.string.Printfailure));
                homeFragment.isStart = 0;
                break;
            default:
                homeFragment.mPromptDialog.dismiss();
                break;
        }
    }

    public void getInkInfo(int type) {
        this.type = type;
        mLocalControlerUtil.sendCXXX(new byte[]{0}, 0);
    }

    //获取打印喷头对应的配置
    private String getPrintSetting(int position) {
        String setting = "";
        switch (position) {
            case 0:
                setting = PreferenceUtils.getInstance().print1;
                break;
            case 1:
                setting = PreferenceUtils.getInstance().print2;
                break;
            case 2:
                setting = PreferenceUtils.getInstance().print3;
                break;
            case 3:
                setting = PreferenceUtils.getInstance().print4;
                break;
            case 4:
                setting = PreferenceUtils.getInstance().print5;
                break;
            case 5:
                setting = PreferenceUtils.getInstance().print6;
                break;
            case 6:
                setting = PreferenceUtils.getInstance().print7;
                break;
            case 7:
                setting = PreferenceUtils.getInstance().print8;
                break;
        }
        return setting;
    }

    // 开始打印
    public void startPrint() {
        List<CSDataBean> csDataBeans = new ArrayList<>();
        List<CJCopeBean> cjCopeBeanList = new ArrayList<>();
        //todo
        int KZFS = PreferenceUtils.getInstance().printMainFontLetterStyle;
        int ZKZ = PreferenceUtils.getInstance().styleSettingCount;
        int DYMS = PreferenceUtils.getInstance().repeatPrint;
        int DYCS = PreferenceUtils.getInstance().repeatCount;
        int DYJG = PreferenceUtils.getInstance().repeatDelay;
        int JSXH = PreferenceUtils.getInstance().userEndSign;
        int CEBJ = 0; //超额报警
        int FZDD = 0; //翻转颠倒
        for (int i = 0; i < 8; i++) {
            String x = getPrintSetting(i);
            if (x.equals("")) {
                cjCopeBeanList.add(new CJCopeBean(false, false));
                csDataBeans.add(new CSDataBean(KZFS, ZKZ, DYMS, DYCS, DYJG, JSXH, CEBJ, 0));
            } else {
                JSONObject object = JSONObject.parseObject(x);
                int DYYS = object.getInteger("printDelay");  //打印延时
                double DYDY1 = object.getDouble("dianya");  //打印电压
                int DYDY = (int) (DYDY1 * 10);
                int TBZ = 1;//同步值
                int YDZ = 1;//应答值
                int XPJX = object.getInteger("muzzleInterval");//闲喷间隙
                int XPLS = object.getInteger("muzzleCount");//闲喷列数
                /*object.getInteger("muzzleChose")*/
                int PZXZ = object.getInteger("muzzleChose");//喷嘴选择
                int CFFS = object.getInteger("muzzleLevel");//喷嘴触发方式
                int reversal = object.getInteger("reversal"); //水平
                int overturn = object.getInteger("overturn"); //垂直

                cjCopeBeanList.add(new CJCopeBean(overturn != 0, reversal != 0));
                //MLog.v(overturn + ":" + reversal);
                if (overturn != 0 && reversal != 0) {
                    FZDD = 3;
                } else if (overturn != 0) {
                    FZDD = 2;
                } else if (reversal != 0) {
                    FZDD = 1;
                } else {
                    FZDD = 0;
                }
                csDataBeans.add(new CSDataBean(DYYS, DYDY, TBZ, YDZ, XPJX, XPLS, PZXZ, CFFS, KZFS, ZKZ, DYMS, DYCS, DYJG, JSXH, CEBJ, FZDD));
            }

        }
        mLocalControlerUtil.setQdDatas(csDataBeans);
        mLocalControlerUtil.setDYXZS(cjCopeBeanList);
        mLocalControlerUtil.isCESI(cb);
        homeFragment.sendBmp(true);
    }

    //打印控制  0(查询打印状态)、1(暂停打印)、2(恢复打印）、3(中止打印)。
    public void PrintControl(int type) {
        mLocalControlerUtil.sendKZDY(new byte[]{(byte) type}, 0);
    }

    /*@Override
    public void onBackPressedSupport() {

    }*/

    //重新配置参数
    public void sendPZCS(int p) {

        if (homeFragment.isStart != 0) { //开始打印后就可以发送新的配置参数
            int KZFS = PreferenceUtils.getInstance().printMainFontLetterStyle;
            int ZKZ = PreferenceUtils.getInstance().styleSettingCount;
            int DYMS = PreferenceUtils.getInstance().repeatPrint;
            int DYCS = PreferenceUtils.getInstance().repeatCount;
            int DYJG = PreferenceUtils.getInstance().repeatDelay;
            int JSXH = PreferenceUtils.getInstance().userEndSign;
            int CEBJ = 0; //超额报警
            int FZDD = 0; //翻转颠倒
            String x = getPrintSetting(p);
            if (x.equals("")) {
                mLocalControlerUtil.setDSZData(new CSDataBean(KZFS, ZKZ, DYMS, DYCS, DYJG, JSXH, CEBJ, 0), p);
            } else {
                JSONObject object = JSONObject.parseObject(x);
                int DYYS = object.getInteger("printDelay");  //打印延时
                double DYDY1 = object.getDouble("dianya");  //打印电压
                int DYDY = (int) (DYDY1 * 10);
                int TBZ = 1;//同步值
                int YDZ = 1;//应答值
                int XPJX = object.getInteger("muzzleInterval");//闲喷间隙
                int XPLS = object.getInteger("muzzleCount");//闲喷列数
                int PZXZ = object.getInteger("muzzleChose");//喷嘴选择
                int CFFS = object.getInteger("muzzleLevel");//喷嘴触发方式
                int reversal = object.getInteger("reversal"); //水平
                int overturn = object.getInteger("overturn"); //垂直
                if (overturn != 0 && reversal != 0) {
                    FZDD = 3;
                } else if (overturn != 0) {
                    FZDD = 2;
                } else if (reversal != 0) {
                    FZDD = 1;
                } else {
                    FZDD = 0;
                }
                mLocalControlerUtil.setDSZData(new CSDataBean(DYYS, DYDY, TBZ, YDZ, XPJX, XPLS, PZXZ, CFFS, KZFS, ZKZ, DYMS, DYCS, DYJG, JSXH, CEBJ, FZDD), p);
            }
            mLocalControlerUtil.sendSZCS(p); //重新发送配置参数
        }
    }

    //关闭定时器
    public void closeTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
