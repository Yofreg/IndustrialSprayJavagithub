package com.goockr.industrialsprayjava.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goockr.industrialsprayjava.MainActivity;
import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.bean.AllSaveBean;
import com.goockr.industrialsprayjava.bean.ClassItem;
import com.goockr.industrialsprayjava.bean.CodeBean;
import com.goockr.industrialsprayjava.bean.PrintNo;
import com.goockr.industrialsprayjava.graphics.InnerRulers.BooheeRuler;
import com.goockr.industrialsprayjava.tools.BarCode;
import com.goockr.industrialsprayjava.tools.BaseUtils;
import com.goockr.industrialsprayjava.tools.BitmapSaveBmp;
import com.goockr.industrialsprayjava.tools.BmpUtil;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.goockr.industrialsprayjava.tools.SaveJsonToMemory;
import com.goockr.industrialsprayjava.tools.ToastUtils;
import com.goockr.industrialsprayjava.view.ClassTextView;
import com.goockr.industrialsprayjava.view.ClockTextView;
import com.goockr.industrialsprayjava.view.CodeImageView;
import com.goockr.industrialsprayjava.view.Horizon;
import com.goockr.industrialsprayjava.view.ImgImageViw;
import com.goockr.industrialsprayjava.view.NoTextView;
import com.goockr.industrialsprayjava.view.Scroll;
import com.goockr.industrialsprayjava.view.TextSourceTextView;
import com.goockr.industrialsprayjava.view.VerticalTextView;
import com.goockr.pmj.bean.MHdataBean;
import com.goockr.pmj.utils.GetCPUUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * Created by lijinning on 2018/3/12.
 * 主界面
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.br_bottom_head)
    BooheeRuler brBottomHead;
    @BindView(R.id.br_right_head)
    BooheeRuler brRightHead;
    @BindView(R.id.MyRe)
    ConstraintLayout conss;
    @BindView(R.id.scroll)
    Scroll scroll;
    @BindView(R.id.hroizon)
    Horizon hroizon;
    @BindView(R.id.ibStart)
    ImageButton ibStart;
    Unbinder unbinder;
    @BindView(R.id.RealTimeRefresh)
    Button RealTimeRefresh;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.DpiTv)
    TextView DpiTv;
    @BindView(R.id.Print1)
    TextView Print1;
    @BindView(R.id.Print2)
    TextView Print2;
    @BindView(R.id.Print3)
    TextView Print3;
    @BindView(R.id.Print4)
    TextView Print4;
    @BindView(R.id.Print5)
    TextView Print5;
    @BindView(R.id.Print6)
    TextView Print6;
    @BindView(R.id.Print7)
    TextView Print7;
    @BindView(R.id.Print8)
    TextView Print8;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tvbvv)
    TextView tvbvv;
    @BindView(R.id.Print1Right)
    TextView Print1Right;
    @BindView(R.id.Print2Right)
    TextView Print2Right;
    @BindView(R.id.Print3Right)
    TextView Print3Right;
    @BindView(R.id.Print4Right)
    TextView Print4Right;
    @BindView(R.id.Print5Right)
    TextView Print5Right;
    @BindView(R.id.Print6Right)
    TextView Print6Right;
    @BindView(R.id.Print7Right)
    TextView Print7Right;
    @BindView(R.id.Print8Right)
    TextView Print8Right;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv_pl)
    TextView tv_pl;
    @BindView(R.id.tv_daying)
    TextView tv_daying;
    @BindView(R.id.tv_huancun)
    TextView tv_huancun;

    public int isStart = 0;
    private List<View> lineView;
    private List<View> AddViews;
    private ConstraintSet set;
    private Context context;
    private int lineId = 20123;
    private int defaultWidth = 2000; //2000
    private int nowTextViewId = 2000;
    private int nowClockId = 2100;
    private int nowCodeId = 2200;
    private int nowClassId = 2300;
    private int nowImgId = 2400;
    private int nowNoId = 2500;
    private int nowTextSourceId = 2600;
    private int nowShadeId = 2700;
    private int nowShadeId2 = 2800;

    private SaveJsonToMemory saveJsonToMemory;
    private int bili = 10;
    private SimpleDateFormat df1 = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    public int printCount = 0;
    public int printCount2 = 0;
    public PromptDialog mPromptDialog;

    private android.support.v7.app.AlertDialog dialog; //对话框
    private android.support.v7.app.AlertDialog.Builder builder;

    private Timer mTimer;
    private Timer mTimer2;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    private int x = 0;
    private int y = 0;
    private boolean isf; //判断是否已经发送
    private int z = 18;
    int i = 0;
    public boolean sendPrintCount = false;
    private TextView shadeTextView;
    private TextView shadeTextView2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);

        set = new ConstraintSet(); //ConstraintLayout布局管理
        set.clone(conss);

        context = getContext();

        saveJsonToMemory = new SaveJsonToMemory(); //文件管理
        mPromptDialog = new PromptDialog(getActivity()); //弹出框

        //初始化控件
        initView();

        loadInSdcard();

        SettingInkBox(PreferenceUtils.getInstance().nozzleCount);

        Trends();

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(200);
            }
        }, 1000, 1000);

        return view;
    }

    private void clearView() {
        if (AddViews == null) {
            AddViews = new ArrayList<>();
        } else {
            for (int i = 0; i < AddViews.size(); i++) {
                conss.removeView(AddViews.get(i));
            }
            AddViews.clear();
        }
        if (shadeTextView != null) {
            conss.removeView(shadeTextView);
        }
        if (shadeTextView2 != null) {
            conss.removeView(shadeTextView2);
        }
    }

    //文件名
    private void UpdateScreen() {
        text1.setText(getString(R.string.NowMsgName) + ": " + PreferenceUtils.getInstance().defaultMsg + ".msg");
    }

    public void loadInSdcard() {
        //更新文件名
        UpdateScreen();
        //获取文件控件
        String str = saveJsonToMemory.LoadMsgInfo(PreferenceUtils.getInstance().defaultMsg);
        JSONArray array = JSONArray.parseArray(str);
        boolean xx;
        boolean yy;
        boolean zz;
        Typeface typeFace = null;
        if (array == null) { //空文件时
            clearView();
            InitLine(PreferenceUtils.getInstance().nozzleCount);
            return;
        }
        clearView(); //非空文件时
        PreferenceUtils.getInstance().setNoCLear();

        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            String type = jsonObject.getString("type");
            JSONObject date;
            File file;
            if (type == null) {
                continue;
            }
            switch (type) {
                case "count":
                    InitLine(jsonObject.getInteger("count"));
                    PreferenceUtils.getInstance().setNozzleCount(jsonObject.getInteger("count"));
                    break;
                case "ClockTextView":
                    date = jsonObject.getJSONObject("date");
                    yy = Boolean.parseBoolean(date.getString("bolder"));
                    xx = Boolean.parseBoolean(date.getString("italic"));
                    zz = Boolean.parseBoolean(date.getString("underline"));

                    ClockTextView clockTextView = new ClockTextView(context);
                    clockTextView.setId(nowClockId++);
                    clockTextView.setTextColor(Color.BLACK);
                    // 获取typeface
                    file = new File(date.getString("typeface"));
                    if (file.exists()) {
                        typeFace = Typeface.createFromFile(date.getString("typeface"));
                        clockTextView.setTypefaces(typeFace, date.getString("typeface"));
                    } else {
                        clockTextView.setTypefaces(typeFace, "");
                    }
                    clockTextView.setClockStyleNo(Integer.parseInt(date.getString("contentStyle")));
                    String xxxx = date.getString("dayStyle");
                    clockTextView.setDateStyle(xxxx);
                    clockTextView.setTimeStyle(date.getString("timeStyle"));
                    clockTextView.setDeviationDay(date.getString("deviation"));
                    clockTextView.upText();
                    clockTextView.setNo(Integer.parseInt(date.getString("no")));
                    if (date.getString("direction").equals("0")) {
                        clockTextView.setDirection(0);
                    } else if (date.getString("direction").equals("1")) {
                        clockTextView.setDirection(1);
                    } else if (date.getString("direction").equals("2")) {
                        clockTextView.setDirection(2);
                    } else if (date.getString("direction").equals("3")) {
                        clockTextView.setDirection(3);
                    }
                    clockTextView.setOnClickListener(this);
                    clockTextView.setTextSize(Integer.parseInt(date.getString("size")));
                    clockTextView.setFontStyle(yy, xx, zz);

                    conss.addView(clockTextView);
                    set.connect(clockTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, Integer.parseInt(date.getString("top")));
                    set.connect(clockTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, Integer.parseInt(date.getString("left")));
                    set.constrainWidth(clockTextView.getId(), ConstraintSet.WRAP_CONTENT);
                    set.constrainHeight(clockTextView.getId(), ConstraintSet.WRAP_CONTENT);

                    AddViews.add(clockTextView);
                    set.applyTo(conss);
                    break;
                case "CodeImageView":
                    date = jsonObject.getJSONObject("date");
                    CodeImageView codeImageView = new CodeImageView(context);
                    JSONObject mapObject = date.getJSONObject("map");
                    Map map = new HashMap();
                    Map hashMap = mapObject.getInnerMap();
                    Collection collection = hashMap.values();
                    Iterator iterator = collection.iterator();
                    codeImageView.setCodeReverse(date.getString("codeReversal"));
                    codeImageView.setCodeCorrection(date.getString("codeCorrection"));
                    codeImageView.setCodeDirection(date.getString("codeDirection"));
                    codeImageView.setCodeSize(date.getString("codeSize"));
                    codeImageView.setCodeStyle(date.getString("codeStyle"));
                    codeImageView.setPadding(5, 5, 5, 5);
                    int value = 0;
                    while (iterator.hasNext()) {
                        value++;
                        JSONObject object = (JSONObject) iterator.next();
                        String typeCode = object.getString("typeCode");
                        switch (typeCode) {
                            case "CodeClock":
                                map.put("" + value, new CodeBean.CodeClock(object.getString("contentStyle"), object.getString("dayStyle"), object.getString("timeStyle"), object.getString("deviation")));
                                break;
                            case "CodeNo":
                                map.put("" + value, new CodeBean.CodeNo(object.getString("nowValue"), Integer.parseInt(object.getString("repeatCount")), Integer.parseInt(object.getString("lengthAdd")),
                                        Integer.parseInt(object.getString("limitNo1")), Integer.parseInt(object.getString("limitNo2")), Boolean.parseBoolean(object.getString("isPreZero"))));
                                break;
                            case "CodeClazz":
                                JSONArray array1 = object.getJSONArray("classItems");
                                List<ClassItem> classItems = new ArrayList<>();
                                for (int x = 0; x < array1.size(); x++) {
                                    JSONObject object1 = array1.getJSONObject(x);
                                    classItems.add(new ClassItem(object1.getString("startTime"), object1.getString("endTime"), object1.getString("clazzText")));
                                }
                                map.put("" + value, new CodeBean.CodeClazz(classItems));
                                break;
                            case "CodeText":
                                map.put("" + value, new CodeBean.CodeText(object.getString("str")));
                                break;
                            case "CodeSource":
                                CodeBean.CodeSource codeSource = new CodeBean.CodeSource(Integer.parseInt(object.getString("length")), Integer.parseInt(object.getString("type")));
                                if (codeSource.getType() == 2) {
                                    codeSource.setPath(object.getString("path"));
                                }
                                map.put("" + value, codeSource);
                                break;
                        }
                    }
                    codeImageView.setMap(map);
                    Bitmap bitmap1 = BarCode.getCodeForType(codeImageView.upDataStr(), Integer.parseInt(codeImageView.getCodeStyle()), Integer.parseInt(codeImageView.getCodeSize()) * bili,
                            Integer.parseInt(codeImageView.getCodeDirection()), Integer.parseInt(codeImageView.getCodeCorrection()), codeImageView.getCodeReverse().equals("0") ? false : true, context);
                    if (bitmap1 == null) {
                        break;
                    }
                    codeImageView.setImageBitmap(bitmap1);
                    codeImageView.setId(nowCodeId++);
                    codeImageView.setOnClickListener(this);
                    conss.addView(codeImageView);
                    set.connect(codeImageView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, Integer.parseInt(date.getString("top")));
                    set.connect(codeImageView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, Integer.parseInt(date.getString("left")));

                    set.constrainWidth(codeImageView.getId(), ConstraintSet.WRAP_CONTENT);
                    set.constrainHeight(codeImageView.getId(), ConstraintSet.WRAP_CONTENT);

                    AddViews.add(codeImageView);
                    set.applyTo(conss);
                    break;
                case "ImgImageViw":
                    date = jsonObject.getJSONObject("date");

                    ImgImageViw imgImageViw = new ImgImageViw(context);
                    String path = date.getString("imgNo");
                    Bitmap bitmap = BaseUtils.getBitmapForDisk(path);
                    if (bitmap == null) {
                        break;
                    }
                    if (date.getString("imgScale").equals("1")) {
                        bitmap = BaseUtils.zoomImg(bitmap, 2);
                    } else if (date.getString("imgScale").equals("2")) {
                        bitmap = BaseUtils.zoomImg(bitmap, 3);
                    } else if (date.getString("imgScale").equals("3")) {
                        bitmap = BaseUtils.zoomImg(bitmap, 4);
                    }
                    bitmap = BaseUtils.directionImg(bitmap, Integer.parseInt(date.getString("imgDirection")));
                    imgImageViw.setImageBitmap(bitmap);
                    imgImageViw.setOnClickListener(this);
                    imgImageViw.setId(nowImgId++);
                    imgImageViw.setPadding(5, 5, 5, 5);
                    conss.addView(imgImageViw);
                    set.connect(imgImageViw.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, Integer.parseInt(date.getString("top")));
                    set.connect(imgImageViw.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, Integer.parseInt(date.getString("left")));

                    imgImageViw.setImgDirection(date.getString("imgDirection"));
                    imgImageViw.setImgNo(0 + "", date.getString("imgNo"));
                    imgImageViw.setImgScale(date.getString("imgScale"));

                    set.constrainWidth(imgImageViw.getId(), ConstraintSet.WRAP_CONTENT);
                    set.constrainHeight(imgImageViw.getId(), ConstraintSet.WRAP_CONTENT);

                    AddViews.add(imgImageViw);
                    set.applyTo(conss);

                    break;
                case "ClassTextView":
                    date = jsonObject.getJSONObject("date");
                    JSONArray array1 = date.getJSONArray("classItems");
                    List<ClassItem> classItems = new ArrayList<>();
                    for (int x = 0; x < array1.size(); x++) {
                        JSONObject jsonObject1 = array1.getJSONObject(x);
                        classItems.add(new ClassItem(jsonObject1.getString("startTime"), jsonObject1.getString("endTime"), jsonObject1.getString("clazzText")));
                    }
                    if (classItems.size() == 0) {
                        break;
                    }
                    ClassTextView classTextView = new ClassTextView(context);
                    classTextView.setId(nowClassId++);
                    // 获取typeface
                    file = new File(date.getString("typeface"));
                    if (file.exists()) {
                        typeFace = Typeface.createFromFile(date.getString("typeface"));
                    }
                    // 应用字体
                    classTextView.setTypefaces(typeFace, date.getString("typeface"));

                    if (date.getString("direction").equals("0")) {
                        classTextView.setDirection(0);
                    } else if (date.getString("direction").equals("1")) {
                        classTextView.setDirection(1);
                    } else if (date.getString("direction").equals("2")) {
                        classTextView.setDirection(2);
                    } else if (date.getString("direction").equals("3")) {
                        classTextView.setDirection(3);
                    }

                    classTextView.setOnClickListener(this);
                    classTextView.setTextSize(Integer.parseInt(date.getString("size")));
                    classTextView.setClassItems(classItems);
                    classTextView.setId(nowClassId++);

                    conss.addView(classTextView);

                    set.connect(classTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, Integer.parseInt(date.getString("top")));
                    set.connect(classTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, Integer.parseInt(date.getString("left")));
                    set.constrainWidth(classTextView.getId(), ConstraintSet.WRAP_CONTENT);
                    set.constrainHeight(classTextView.getId(), ConstraintSet.WRAP_CONTENT);

                    AddViews.add(classTextView);
                    set.applyTo(conss);
                    break;
                case "NoTextView":
                    date = jsonObject.getJSONObject("date");
                    NoTextView noTextView = new NoTextView(context);
                    noTextView.setId(nowNoId++);
                    noTextView.setmText(date.getString("str"));
                    noTextView.setTextColor(Color.BLACK);
                    // 获取typeface
                    file = new File(date.getString("typeface"));
                    if (file.exists()) {
                        typeFace = Typeface.createFromFile(date.getString("typeface"));
                    }
                    // 应用字体
                    noTextView.setTypefacePath(typeFace, date.getString("typeface"));
                    noTextView.setSpaceletter(Integer.parseInt(date.getString("superscript")));
                    noTextView.setDirection(Integer.parseInt(date.getString("direction")));

                    noTextView.setOnClickListener(this);
                    noTextView.setNo(Integer.parseInt(date.getString("no")));
                    noTextView.setRepectCount(Integer.parseInt(date.getString("repeatCount")));
                    noTextView.setLengthAdd(Integer.parseInt(date.getString("lengthAdd")));
                    noTextView.setLimitNo1(Integer.parseInt(date.getString("limitNo1")));
                    noTextView.setLimitNo2(Integer.parseInt(date.getString("limitNo2")));
                    noTextView.setProPositionZero(Boolean.parseBoolean(date.getString("proPositionZero")));
                    JSONObject object = new JSONObject();
                    PrintNo printNo = new PrintNo(date.getString("no"), date.getString("str"), date.getString("limitNo1"), date.getString("limitNo2"), false);
                    object.put("PrintNo", printNo);
                    if (noTextView.getNo() == 0) {
                        PreferenceUtils.getInstance().setNo1(object.toJSONString());
                    } else if (noTextView.getNo() == 1) {
                        PreferenceUtils.getInstance().setNo2(object.toJSONString());
                    } else if (noTextView.getNo() == 2) {
                        PreferenceUtils.getInstance().setNo3(object.toJSONString());
                    } else if (noTextView.getNo() == 3) {
                        PreferenceUtils.getInstance().setNo4(object.toJSONString());
                    }
                    noTextView.setmTextSize(Integer.parseInt(date.getString("size")));
                    yy = Boolean.parseBoolean(date.getString("bolder"));
                    xx = Boolean.parseBoolean(date.getString("italic"));
                    zz = Boolean.parseBoolean(date.getString("underline"));

                    noTextView.setFontStyle(yy, xx, zz);
                    conss.addView(noTextView);
                    set.connect(noTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, Integer.parseInt(date.getString("top")));
                    set.connect(noTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, Integer.parseInt(date.getString("left")));

                    set.constrainWidth(noTextView.getId(), ConstraintSet.WRAP_CONTENT);
                    set.constrainHeight(noTextView.getId(), ConstraintSet.WRAP_CONTENT);

                    AddViews.add(noTextView);
                    set.applyTo(conss);

                    break;
                case "TextSourceTextView":
                    date = jsonObject.getJSONObject("date");
                    TextSourceTextView textSourceTextView = new TextSourceTextView(context);

                    textSourceTextView.setId(nowTextSourceId++);
                    // 获取typeface
                    file = new File(date.getString("typeface"));
                    if (file.exists()) {
                        typeFace = Typeface.createFromFile(date.getString("typeface"));
                    }
                    // 应用字体
                    textSourceTextView.setTypefacePath(typeFace, date.getString("typeface"));
                    textSourceTextView.setSpaceletter(Integer.parseInt(date.getString("superscript")));
                    textSourceTextView.setDirection(Integer.parseInt(date.getString("direction")));

                    textSourceTextView.setOnClickListener(this);

                    textSourceTextView.setmTextSize(Integer.parseInt(date.getString("size")));
                    textSourceTextView.setTextLength(Integer.parseInt(date.getString("textLength")));
                    textSourceTextView.setSoutceType(Integer.parseInt(date.getString("soutceType")));
                    textSourceTextView.setUsbPath(date.getString("usbPath"));
                    textSourceTextView.setNowPrint(date.getInteger("nowPrint"));

                    yy = Boolean.parseBoolean(date.getString("bolder"));
                    xx = Boolean.parseBoolean(date.getString("italic"));
                    zz = Boolean.parseBoolean(date.getString("underline"));
                    textSourceTextView.setFontStyle(yy, xx, zz);
                    conss.addView(textSourceTextView);
                    set.connect(textSourceTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, Integer.parseInt(date.getString("top")));
                    set.connect(textSourceTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, Integer.parseInt(date.getString("left")));

                    set.constrainWidth(textSourceTextView.getId(), ConstraintSet.WRAP_CONTENT);
                    set.constrainHeight(textSourceTextView.getId(), ConstraintSet.WRAP_CONTENT);

                    AddViews.add(textSourceTextView);
                    set.applyTo(conss);

                    break;
                case "VerticalTextView":
                    date = jsonObject.getJSONObject("date");
                    VerticalTextView InsertTextView = new VerticalTextView(context);
                    InsertTextView.setId(nowTextViewId++);
                    InsertTextView.setText(date.getString("str"));
                    InsertTextView.setTextColor(Color.BLACK);
                    // 获取typeface
                    file = new File(date.getString("typeface"));
                    if (file.exists()) {
                        typeFace = Typeface.createFromFile(date.getString("typeface"));
                    }
                    // 应用字体
                    InsertTextView.setTypefaces(typeFace, date.getString("typeface"));
                    InsertTextView.setLetterSpace(Float.parseFloat(date.getString("superscript")));

                    if (date.getString("direction").equals("0")) {
                        InsertTextView.setDirection(0);
                    } else if (date.getString("direction").equals("1")) {
                        InsertTextView.setDirection(1);
                    } else if (date.getString("direction").equals("2")) {
                        InsertTextView.setDirection(2);
                    } else if (date.getString("direction").equals("3")) {
                        InsertTextView.setDirection(3);
                    }

                    InsertTextView.setOnClickListener(this);

                    InsertTextView.setTextSize(Integer.parseInt(date.getString("size")));
                    yy = Boolean.parseBoolean(date.getString("bolder"));
                    xx = Boolean.parseBoolean(date.getString("italic"));
                    zz = Boolean.parseBoolean(date.getString("underline"));
                    InsertTextView.setFontStyle(yy, xx, zz);
                    conss.addView(InsertTextView);
                    set.connect(InsertTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, Integer.parseInt(date.getString("top")));
                    set.connect(InsertTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, Integer.parseInt(date.getString("left")));

                    set.constrainWidth(InsertTextView.getId(), ConstraintSet.WRAP_CONTENT);
                    set.constrainHeight(InsertTextView.getId(), ConstraintSet.WRAP_CONTENT);

                    AddViews.add(InsertTextView);
                    set.applyTo(conss);

                    break;
            }
        }
        addShaed();
    }

    //灰色区域
    private void addShaed() {
        TextView textView = new TextView(context);
        textView.setId(nowShadeId++);
        textView.setBackgroundColor(Color.argb(255, 153, 153, 153));
        textView.setText("灰色区域以下无法打印");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        conss.addView(textView);
        set.connect(textView.getId(), ConstraintSet.TOP, lineView.get(lineView.size() - 1).getId(), ConstraintSet.BOTTOM, 0);
        set.connect(textView.getId(), ConstraintSet.LEFT, brRightHead.getId(), ConstraintSet.RIGHT, 0);

        set.constrainWidth(textView.getId(), BaseUtils.dp2px(defaultWidth));
        set.constrainHeight(textView.getId(), BaseUtils.dp2px(200));
        shadeTextView = textView;
        set.applyTo(conss);

        TextView textView2 = new TextView(context);
        textView2.setId(nowShadeId2++);
        textView2.setBackgroundColor(Color.argb(255, 153, 153, 153));
        textView2.setText("右边区域无法打印");
        textView2.setTextSize(30);
        textView2.setGravity(Gravity.CENTER);
        /*Drawable d = getResources().getDrawable(R.mipmap.xuxian);
        //必须设置图片大小，否则不显示【0,0表示坐标x,y坐标，50,50表示宽高】
        d.setBounds(0, 0, 5, BaseUtils.dp2px((BaseUtils.muzzleHeight + 1) * PreferenceUtils.getInstance().nozzleCount));
        //四个参数分别表示文本左、上、右、下四个方向上的图片，null表示没有图片
        textView2.setCompoundDrawables(d , null, null, null);*/
        conss.addView(textView2);
        set.connect(textView2.getId(), ConstraintSet.TOP, brBottomHead.getId(), ConstraintSet.BOTTOM, 0);
        set.connect(textView2.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, 400);

        set.constrainWidth(textView2.getId(), BaseUtils.dp2px(defaultWidth - 400));
        //set.constrainWidth(textView2.getId(), 5);
        set.constrainHeight(textView2.getId(), BaseUtils.dp2px((BaseUtils.muzzleHeight + 1) * PreferenceUtils.getInstance().nozzleCount));
        shadeTextView2 = textView2;
        set.applyTo(conss);
    }

    //去掉横
    private void InitLine(int defaultCount) {
        if (lineView == null) {
            lineView = new ArrayList<>();
        } else {
            for (int i = 0; i < lineView.size(); i++) {
                conss.removeView(lineView.get(i));
            }
            lineView.clear();
        }
        set.constrainHeight(brRightHead.getId(), BaseUtils.dp2px(80) * (defaultCount + 1));
        set.applyTo(conss);
        for (int x = 0; x < defaultCount; x++) {
            AddLine(x);
        }
    }

    //添加横
    private void AddLine(int x) {
        TextView line = new TextView(context);
        line.setBackgroundColor(getResources().getColor(R.color.textColor));
        line.setId(lineId);
        lineId++;
        conss.addView(line);
        if (x == 0) {
            set.connect(line.getId(), ConstraintSet.TOP, brBottomHead.getId(), ConstraintSet.BOTTOM, BaseUtils.dp2px(BaseUtils.muzzleHeight));
            set.connect(line.getId(), ConstraintSet.LEFT, brRightHead.getId(), ConstraintSet.RIGHT, BaseUtils.dp2px(1));
        } else {
            set.connect(line.getId(), ConstraintSet.TOP, lineView.get(x - 1).getId(), ConstraintSet.BOTTOM, BaseUtils.dp2px(BaseUtils.muzzleHeight));
            set.connect(line.getId(), ConstraintSet.LEFT, brRightHead.getId(), ConstraintSet.RIGHT, BaseUtils.dp2px(1));
        }
        set.constrainWidth(line.getId(), BaseUtils.dp2px(defaultWidth));
        set.constrainHeight(line.getId(), BaseUtils.dp2px(1));
        set.applyTo(conss);
        lineView.add(line);
    }

    private void initView() {

        lineView = new ArrayList<>();
        AddViews = new ArrayList<>();
////获取需要打印的Bitmap
//        timer = new CountDownTimer(6000000, 100) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                updateView();
////                PreferenceUtils.getInstance().setCount();
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        };
    }

    public void finish() {
        pop();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            loadInSdcard();
            Trends();
        }
    }

    private void Trends() {
        if (!PreferenceUtils.getInstance().isMM) {
            brRightHead.setInterval(15);
            brBottomHead.setInterval(15);
        } else {
            brRightHead.setInterval(6);
            brBottomHead.setInterval(6);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimer2 != null) {
            mTimer2.cancel();
            mTimer2 = null;
        }
    }

    @OnClick({R.id.ibStart, R.id.RealTimeRefresh})
    public void onViewClicked(View view) {
        MainActivity.getInstance().closeTimer();
        MainActivity.getInstance().clFlag = false;
        MainActivity.getInstance().clFlag2 = 0;
        switch (view.getId()) {
            case R.id.ibStart:
                //todo
                //getNewBitmap();
                //MLog.v("......");
                if (!MainActivity.getInstance().printIsOk) {
                    ToastUtils.ToastShow("机器存在故障,请检查后点击重连!");
                    return;
                }
                StartOrStop();
                break;
            case R.id.RealTimeRefresh:
                MainActivity.getInstance().noOne = false; //手动点击重连后关闭故障断电
                isStart = 0;
                ibStart.setImageResource(R.mipmap.start);
                MainActivity.getInstance().portHelper.sendGZ();
                SystemClock.sleep(50);
                MainActivity.getInstance().portHelper.sendQD(0);
                MainActivity.getInstance().mLocalControlerUtil.sendCL();
                break;
        }
    }

    public void isNeedReConnect(boolean connect) {
        if (connect) {
            RealTimeRefresh.setVisibility(View.VISIBLE);
        } else {
            RealTimeRefresh.setVisibility(View.INVISIBLE);
        }
    }

    public void setibstart() {
        ibStart.setImageResource(R.mipmap.start);
    }

    private void StartOrStop() {

        if (isStart == 2) {
            new AlertDialog.Builder(context).setTitle(getString(R.string.prompt)).setMessage(getString(R.string.operation)).setPositiveButton(getString(R.string.Continue), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    StartPrint(true);
                }
            }).setNegativeButton(getString(R.string.Stop), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    StopPrint();
                }
            }).setIcon(R.mipmap.ic_launcher).show();

        } else if (isStart == 0) {
            new AlertDialog.Builder(context).setTitle(getString(R.string.prompt)).setMessage(getString(R.string.StartPrinting)).setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.getInstance().noOne = true; //表示有点击开始打印过
                    StartPrint(false);
                }
            }).setNegativeButton(getString(R.string.Cancel), null).setIcon(R.mipmap.ic_launcher).show();
        } else if (isStart == 1) {
            new AlertDialog.Builder(context).setTitle(getString(R.string.prompt)).setMessage(getString(R.string.SuspendPrinting)).setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pausePrint();
                }
            }).setNegativeButton("取消", null).setIcon(R.mipmap.ic_launcher).show();
        }
    }

    public void pausePrint() {
        ibStart.setImageResource(R.mipmap.stop);
        isStart = 2;
        sendPrintCount = false;
        MainActivity.getInstance().portHelper.sendQD(0);
        SystemClock.sleep(10);
        MainActivity.getInstance().PrintControl(1);
        MainActivity.getInstance().SettingPrintStatus(4);
        showEnterPasswordDialog2("正在暂停打印,请稍等!");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 500);
    }

    // 停止打印
    public void StopPrint() {
        if (isStart == 0) {
            return;
        }
        coverMsg(); //保存数据
        printCount = 0;
        printCount2 = 0;
        a = 0;
        isStart = 0;
        sendPrintCount = false;
        mPromptDialog.dismiss();
        ibStart.setImageResource(R.mipmap.start);
        MainActivity.getInstance().portHelper.sendQD(0);
        SystemClock.sleep(10);
        MainActivity.getInstance().PrintControl(3);
        MainActivity.getInstance().SettingPrintStatus(0);

        showEnterPasswordDialog2("正在停止打印,请稍等!");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 500);
    }

    // 停止打印
    public void StopPrint2() {
        if (isStart == 0) {
            return;
        }
        coverMsg(); //保存数据
        printCount = 0;
        printCount2 = 0;
        a = 0;
        isStart = 0;
        sendPrintCount = false;
        //mPromptDialog.dismiss();
        ibStart.setImageResource(R.mipmap.start);
        MainActivity.getInstance().portHelper.sendQD(0);
        //SystemClock.sleep(10);
        //MainActivity.getInstance().PrintControl(3);
        MainActivity.getInstance().SettingPrintStatus(0);

        /*showEnterPasswordDialog2("正在停止打印,请稍等!");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 500);*/
    }

    //输入弹出框
    private void showEnterPasswordDialog2(String a) {

        if (MainActivity.getInstance().isDestroyed()) {
            return;
        }

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        builder = new android.support.v7.app.AlertDialog.Builder(getActivity(), R.style.dialog);
        View view = View.inflate(getActivity(),  R.layout.entpassword_dialog2, null);
        builder.setView(view);

        TextView tv0 = view.findViewById(R.id.tv0);
        tv0.setText(a);

        dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 开始打印
     *
     * @param isRestart 是否为继续打印
     */
    public void StartPrint(boolean isRestart) {
        if (isRestart) {
            MainActivity.getInstance().SettingPrintStatus(3);
            MainActivity.getInstance().portHelper.sendQD(PreferenceUtils.getInstance().nozzleCount);
            SystemClock.sleep(10);
            MainActivity.getInstance().PrintControl(2);
            showEnterPasswordDialog2("正在重启打印,请稍等!");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }, 500);

        } else {
            mPromptDialog.showLoading(getString(R.string.PleaseWait));
            MainActivity.getInstance().startPrint();
            MainActivity.getInstance().SettingPrintStatus(1);
            text4.setText(getString(R.string.starttime) + ": " + df1.format(new Date(System.currentTimeMillis())));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPromptDialog.dismiss();
                }
            }, 3000);

        }
        ibStart.setImageResource(R.mipmap.pause);
        isStart = 1;
    }

    private int a = 0;

    public void updatePrint(int type) {

        if (type == 1) {
            if (text2 != null) {
                text2.setText("已打印:" + (++printCount));
            }
            /*if (printCount != dayin) {
                daying(++a);
            }*/
        } else if (type == 2) {
            if (tv_daying != null) {
                tv_daying.setText(++printCount2 + "");
            }
        } else if (type == 0) {
            tv_daying.setText(0 + "");
            text2.setText("已打印:" + 0);
        }
    }

    //返回要输出的Bitmap
    private Bitmap getNewBitmap() {
        for (int i = 0; i < lineView.size(); i++) {
            lineView.get(i).setVisibility(View.INVISIBLE);
        }
        if (scroll == null || hroizon == null) {
            return null;
        }

        Bitmap bitmap = BitmapSaveBmp.getScrollBitmap(scroll, hroizon);
        //BaseUtils.saveFile(bitmap, df1.format(new Date(System.currentTimeMillis())) + "6666.png");

        int w = getMaxLenth();
        int h = (BaseUtils.muzzleHeight + 1) * PreferenceUtils.getInstance().nozzleCount;
        bitmap = BaseUtils.cutBitmap(bitmap, w, h);

        //BaseUtils.saveFile(bitmap, df1.format(new Date(System.currentTimeMillis())) + ".png");
        //BaseUtils.saveFileBmp(BmpUtil.changeToMonochromeBitmap(bitmap), (i++) + "0.bmp");
        for (int i = 0; i < lineView.size(); i++) {
            lineView.get(i).setVisibility(View.VISIBLE);
        }
        return bitmap;
    }

    private MyHandler handler = new MyHandler(this);

    private class MyHandler extends Handler {

        private WeakReference<HomeFragment> mFragmentWeakReference;

        MyHandler(HomeFragment homeFragment){
            mFragmentWeakReference = new WeakReference<HomeFragment>(homeFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 123:
                    //MLog.v("123123");
                    Bitmap b = getNewBitmap();
                    if (b != null) {
                        if (PreferenceUtils.getInstance().nozzleCount % 2 == 0) {
                            tv3.setText("图片大小: " + (BmpUtil.changeSingleBytes(b).length / PreferenceUtils.getInstance().nozzleCount) + "/10000");
                        } else if (PreferenceUtils.getInstance().nozzleCount == 1) {
                            tv3.setText("图片大小: " + (BmpUtil.changeSingleBytes(b).length / PreferenceUtils.getInstance().nozzleCount * 3 / 4) + "/10000");
                        } else if (PreferenceUtils.getInstance().nozzleCount == 3) {
                            tv3.setText("图片大小: " + (BmpUtil.changeSingleBytes(b).length / PreferenceUtils.getInstance().nozzleCount * 9 / 10) + "/10000");
                        } else if (PreferenceUtils.getInstance().nozzleCount == 5) {
                            tv3.setText("图片大小: " + (BmpUtil.changeSingleBytes(b).length / PreferenceUtils.getInstance().nozzleCount * 15 / 16) + "/10000");
                        } else if (PreferenceUtils.getInstance().nozzleCount == 7) {
                            tv3.setText("图片大小: " + (BmpUtil.changeSingleBytes(b).length / PreferenceUtils.getInstance().nozzleCount * 21 / 22) + "/10000");
                        }
                        MainActivity.getInstance().mLocalControlerUtil.sendCSTP(b, 1);
                        if (MainActivity.cb) {
                            return;
                        }
                    }
                    //MLog.v("123123123");
                    if (sendPrintCount && MainActivity.getInstance().printIsOk) {
                       // MLog.v("123456");
                        updateView();
                    }
                    break;
                case 200:
                    if (tv_pl != null) {
                        tv_pl.setText(GetCPUUtils.getMinCPU() + "/" + GetCPUUtils.getCurCPU() + "/" + GetCPUUtils.getMaxCPU());
                    }
                    break;
            }
        }
    }

    public void SettingInkBox(int p, MHdataBean bean) {
        switch (p) {
            case 1:
                Print1.setText("喷头" + p + ": " + bean.getMSYL() + " %");
                break;
            case 2:
                Print2.setText("喷头" + p + ": " + bean.getMSYL() + " %");
                break;
            case 3:
                Print3.setText("喷头" + p + ": " + bean.getMSYL() + " %");
                break;
            case 4:
                Print4.setText("喷头" + p + ": " + bean.getMSYL() + " %");
                break;
            case 5:
                Print5.setText("喷头" + p + ": " + bean.getMSYL() + " %");
                break;
            case 6:
                Print6.setText("喷头" + p + ": " + bean.getMSYL() + " %");
                break;
            case 7:
                Print7.setText("喷头" + p + ": " + bean.getMSYL() + " %");
                break;
            case 8:
                Print8.setText("喷头" + p + ": " + bean.getMSYL() + " %");
                break;
        }
    }

    public void SettingInkBox(int p) {
        Print1Right.setText("关闭");
        Print2Right.setText("关闭");
        Print3Right.setText("关闭");
        Print4Right.setText("关闭");
        Print5Right.setText("关闭");
        Print6Right.setText("关闭");
        Print7Right.setText("关闭");
        Print8Right.setText("关闭");

        switch (p) {
            case 8:
                Print8Right.setText("开启");
            case 7:
                Print7Right.setText("开启");
            case 6:
                Print6Right.setText("开启");
            case 5:
                Print5Right.setText("开启");
            case 4:
                Print4Right.setText("开启");
            case 3:
                Print3Right.setText("开启");
            case 2:
                Print2Right.setText("开启");
            case 1:
                Print1Right.setText("开启");
                break;
        }
    }

    public void settingInkBox2(int p, boolean flag, int a) {

        Print8Right.setText("关闭");
        Print7Right.setText("关闭");
        Print6Right.setText("关闭");
        Print5Right.setText("关闭");
        Print4Right.setText("关闭");
        Print3Right.setText("关闭");
        Print2Right.setText("关闭");
        Print1Right.setText("关闭");

        //得到一串数据13578
        String b = String.valueOf(p);

        if (flag) {
            switch (a) {
                case 8:
                    Print8Right.setText("开启");
                case 7:
                    Print7Right.setText("开启");
                case 6:
                    Print6Right.setText("开启");
                case 5:
                    Print5Right.setText("开启");
                case 4:
                    Print4Right.setText("开启");
                case 3:
                    Print3Right.setText("开启");
                case 2:
                    Print2Right.setText("开启");
                case 1:
                    Print1Right.setText("开启");
                    break;
            }
        } else {
            if (b.contains("8")) {
                Print8Right.setText("失败");
            }
            if (b.contains("7")) {
                Print7Right.setText("失败");
            }
            if (b.contains("6")) {
                Print6Right.setText("失败");
            }
            if (b.contains("5")) {
                Print5Right.setText("失败");
            }
            if (b.contains("4")) {
                Print4Right.setText("失败");
            }
            if (b.contains("3")) {
                Print3Right.setText("失败");
            }
            if (b.contains("2")) {
                Print2Right.setText("失败");
            }
            if (b.contains("1")) {
                Print1Right.setText("失败");
            }
        }
    }

    private void updateView() {
        x = 0;
        y = 0;
        isf = false;
        for (int i = 0; i < AddViews.size(); i++) {
            final View view = AddViews.get(i);
            //Log.v("xxxxxf", "5");
            if (view instanceof ClockTextView) {
                ((ClockTextView) view).upText();
            } else if (view instanceof ClassTextView) {
                ((ClassTextView) view).upText();
            } else if (view instanceof NoTextView) {
                ((NoTextView) view).PrintText();
            } else if (view instanceof TextSourceTextView) {
                ((TextSourceTextView) view).upText();
            } else if (view instanceof CodeImageView) {
                CodeImageView codeImageView = (CodeImageView) view;
                Bitmap bitmap = BarCode.getCodeForType(codeImageView.upDataStr(), Integer.parseInt(codeImageView.getCodeStyle()), Integer.parseInt(codeImageView.getCodeSize()) * bili,
                        Integer.parseInt(codeImageView.getCodeDirection()), Integer.parseInt(codeImageView.getCodeCorrection()), !codeImageView.getCodeReverse().equals("0"), context);
                codeImageView.setImageBitmap(bitmap);
            } else if (view instanceof ImgImageViw) {
                ((ImgImageViw) view).upDate();
            } else {
                ((VerticalTextView) view).upText();
            }

            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    x++;
                    //Log.v("xxxxxf", "3333333333333333333333    "+ x + "   "+AddViews.size());
                    if (x == AddViews.size()) {
                        if (!isf) {
                            isf = true;
                            if (mTimer2 != null) {
                                mTimer2.cancel();
                                mTimer2 = null;
                            }
                            //MLog.v("123123");
                            handler.sendEmptyMessage(123);
                        }
                    }
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

            });
            //切换界面的时候view绘画回调不执行,用这个去继续
            y++;
            z = 18;
            if (y == AddViews.size()) {
                z = z + y * 2;
               // MLog.v("3");
                mTimer2 = new Timer();
                mTimer2.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!isf) {
                            isf = true;
                            //MLog.v("321321321321");
                            handler.sendEmptyMessage(123);
                        }
                    }
                }, z);
            }
            //Log.v("xxxxxf", "3333333333333333333333    "+ x + "   "+AddViews.size());
        }
    }

    private int getMaxLenth() {
        int x = 0;
        for (int i = 0; i < AddViews.size(); i++) {
            int y = AddViews.get(i).getLeft() + AddViews.get(i).getWidth();
            x = x > y ? x : y;
        }
        //return x;
        return x > 400 ? 400 : x; //设置最大为400
    }

    @Override
    public void onClick(View v) {

    }

    public void sendBmp(boolean print) {
        //Log.v("xxxxxf", "1111111111111111111111111111111111111111111");
        sendPrintCount = print;
        updateView();
    }

    public void daying(int daying) {
        tv_daying.setText(daying + "");
    }

    //根据打印的次数去保存数据
    public void BaocunBitmap(int d) {

    }

    //保存数据
    public void coverMsg() {
        JSONArray array = new JSONArray();
        JSONObject count = new JSONObject();
        count.put("type", "count");
        count.put("count", PreferenceUtils.getInstance().nozzleCount);
        array.add(count);
        for (int i = 0; i < AddViews.size(); i++) {
            JSONObject obj = new JSONObject();
            if (AddViews.get(i) instanceof VerticalTextView) {
                VerticalTextView verticalTextView = (VerticalTextView) AddViews.get(i);
                obj.put("type", "VerticalTextView");
                obj.put("date", new AllSaveBean.TextInfo(verticalTextView.getmText(), verticalTextView.getSize() + "", verticalTextView.getTypefacePath(),
                        verticalTextView.getDirection() + "", verticalTextView.getTop() + "", verticalTextView.getLeft() + "", verticalTextView.getLetterSpace() + "",
                        verticalTextView.isIt() + "", verticalTextView.isBold() + "", verticalTextView.isUnder() + ""));
            } else if (AddViews.get(i) instanceof ClockTextView) {
                ClockTextView clockTextView = (ClockTextView) AddViews.get(i);
                obj.put("type", "ClockTextView");
                obj.put("date", new AllSaveBean.ClockInfo(clockTextView.getmText(), clockTextView.getSize() + "", clockTextView.getTypefacePath(), clockTextView.getDirection() + "",
                        clockTextView.getTop() + "", clockTextView.getLeft() + "", clockTextView.getNo() + "",
                        clockTextView.isIt() + "", clockTextView.isBold() + "", clockTextView.isUnder() + "", clockTextView.getDeviationDay(), clockTextView.getClockStyleNo() + "",
                        clockTextView.getDateStyle(), clockTextView.getTimeStyle()));
            } else if (AddViews.get(i) instanceof CodeImageView) {
                CodeImageView codeImageView = (CodeImageView) AddViews.get(i);
                obj.put("type", "CodeImageView");
                obj.put("date", new AllSaveBean.CodeInfo(codeImageView.getCodeStyle(), codeImageView.getCodeSize(), codeImageView.getCodeDirection(), codeImageView.getCodeCorrection(),
                        codeImageView.getCodeReverse(), codeImageView.getCodeContent(), codeImageView.getTop() + "", codeImageView.getLeft() + "", codeImageView.getMap()));
            } else if (AddViews.get(i) instanceof ImgImageViw) {
                ImgImageViw imgImageViw = (ImgImageViw) AddViews.get(i);
                obj.put("type", "ImgImageViw");
                obj.put("date", new AllSaveBean.ImgInfo(imgImageViw.getPath(), imgImageViw.getImgScale(), imgImageViw.getImgDirection(), imgImageViw.getTop() + "", imgImageViw.getLeft() + ""));
            } else if (AddViews.get(i) instanceof ClassTextView) {
                ClassTextView classTextView = (ClassTextView) AddViews.get(i);
                obj.put("type", "ClassTextView");
                obj.put("date", new AllSaveBean.ClassInfo("", classTextView.getSize() + "", classTextView.getTypefacePath(), classTextView.getDirection() + "",
                        classTextView.getTop() + "", classTextView.getLeft() + "", classTextView.getClassItems()));
            } else if (AddViews.get(i) instanceof NoTextView) {
                NoTextView noTextView = (NoTextView) AddViews.get(i);
                obj.put("type", "NoTextView");
                obj.put("date", new AllSaveBean.NoInfo(noTextView.getmText(), noTextView.getmTextSize() + "", noTextView.getTypefacePath(),
                        noTextView.getDirection() + "", noTextView.getTop() + "", noTextView.getLeft() + "", noTextView.getSpaceletter() + "",
                        noTextView.isIt() + "", noTextView.isBolder() + "", noTextView.isUnderline() + "", noTextView.getNo() + "", noTextView.getRepectCount() + "",
                        noTextView.getLengthAdd() + "", noTextView.getLimitNo1() + "", noTextView.getLimitNo2() + "", noTextView.isProPositionZero() + ""));
            } else if (AddViews.get(i) instanceof TextSourceTextView) {
                TextSourceTextView textSourceTextView = (TextSourceTextView) AddViews.get(i);
                obj.put("type", "TextSourceTextView");
                obj.put("date", new AllSaveBean.TextSourceInfo(textSourceTextView.getmText(), textSourceTextView.getmTextSize() + "", textSourceTextView.getTypefacePath(), textSourceTextView.getDirection() + "",
                        textSourceTextView.getTop() + "", textSourceTextView.getLeft() + "", textSourceTextView.getSpaceletter() + "", textSourceTextView.isIt() + "", textSourceTextView.isBolder() + "",
                        textSourceTextView.isUnderline() + "", textSourceTextView.getTextLength(), textSourceTextView.getSoutceType(), textSourceTextView.getUsbPath(), textSourceTextView.getNowPrint()));
            }
            String xxx = obj.toJSONString();
            //Log.e("", xxx);
            array.add(obj);
        }
        //if (isCover) {
        saveJsonToMemory.SaveMsgInfo(array.toJSONString(), PreferenceUtils.getInstance().defaultMsg);
            //ToastUtils.ToastShow(getString(R.string.Successfullycovered));
        /*} else {
            String endName = PreferenceUtils.getInstance().defaultMsg;
            endName = endName.split("\\(")[0];
            int x = 1;
            int y = 0;
            while (BaseUtils.existForName(endName + "(" + x + ")") && (y++ < 100)) {
                x++;
            }

            saveJsonToMemory.SaveMsgInfo(array.toJSONString(), endName + "(" + x + ")");
            PreferenceUtils.getInstance().setDefaultMsg(endName + "(" + x + ")");
            ToastUtils.ToastShow(getString(R.string.Createdsuccessfully));
        }*/
    }

    //自动重连
    public void sendZD() {
        isStart = 0;
        ibStart.setImageResource(R.mipmap.start);
        MainActivity.getInstance().portHelper.sendGZ();
        SystemClock.sleep(50);
        MainActivity.getInstance().portHelper.sendQD(0);
        MainActivity.getInstance().mLocalControlerUtil.sendCL();
    }
}
