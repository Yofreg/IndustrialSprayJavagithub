package com.goockr.industrialsprayjava.ui;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goockr.industrialsprayjava.MainActivity;
import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.bean.AllSaveBean;
import com.goockr.industrialsprayjava.bean.ClassItem;
import com.goockr.industrialsprayjava.bean.CodeBean;
import com.goockr.industrialsprayjava.bean.FileItem;
import com.goockr.industrialsprayjava.bean.PrintNo;
import com.goockr.industrialsprayjava.graphics.InnerRulers.BooheeRuler;
import com.goockr.industrialsprayjava.tools.BarCode;
import com.goockr.industrialsprayjava.tools.BaseUtils;
import com.goockr.industrialsprayjava.tools.BitmapSaveBmp;
import com.goockr.industrialsprayjava.tools.FileUtils;
import com.goockr.industrialsprayjava.tools.GoockrLog;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.goockr.industrialsprayjava.tools.SaveJsonToMemory;
import com.goockr.industrialsprayjava.tools.ToastUtils;
import com.goockr.industrialsprayjava.view.ClassTextView;
import com.goockr.industrialsprayjava.view.ClockTextView;
import com.goockr.industrialsprayjava.view.CodeImageView;
import com.goockr.industrialsprayjava.view.Horizon;
import com.goockr.industrialsprayjava.view.ImgImageViw;
import com.goockr.industrialsprayjava.view.MyTimepickerDialog;
import com.goockr.industrialsprayjava.view.NoTextView;
import com.goockr.industrialsprayjava.view.Scroll;
import com.goockr.industrialsprayjava.view.TextSourceTextView;
import com.goockr.industrialsprayjava.view.VerticalTextView;
import com.goockr.pmj.utils.MLog;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.leon.lfilepickerlibrary.LFilePicker;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by lijinning on 2018/3/12.
 * 信息编辑
 */

public class InfoEditFragment extends BaseFragment implements View.OnClickListener, View.OnTouchListener {


    @BindView(R.id.btnSendPrint)
    Button btnSendPrint;
    @BindView(R.id.btnCreateMsg)
    Button btnCreateMsg;
    @BindView(R.id.btnSavaMsg)
    Button btnSavaMsg;
    @BindView(R.id.btnInsertText)
    Button btnInsertText;
    @BindView(R.id.btnInsertclock)
    Button btnInsertclock;
    @BindView(R.id.btnInsertNo)
    Button btnInsertNo;
    @BindView(R.id.ibStart)
    ImageButton ibStart;
    @BindView(R.id.homeLlControl)
    LinearLayout homeLlControl;
    @BindView(R.id.ibStart1)
    ImageButton ibStart1;
    @BindView(R.id.btnInsertCode)
    Button btnInsertCode;
    @BindView(R.id.btnInsertImg)
    Button btnInsertImg;
    @BindView(R.id.btnInsertClass)
    Button btnInsertClass;
    @BindView(R.id.btnSpildString)
    Button btnSpildString;
    @BindView(R.id.homeLlControl2)
    LinearLayout homeLlControl2;
    Unbinder unbinder;
    @BindView(R.id.MyRe)
    ConstraintLayout conss;
    @BindView(R.id.br_bottom_head)
    BooheeRuler brBottomHead;
    @BindView(R.id.br_right_head)
    BooheeRuler brRightHead;
    @BindView(R.id.BigConss)
    ConstraintLayout BigConss;
    @BindView(R.id.scroll)
    Scroll scroll;
    @BindView(R.id.hroizon)
    Horizon hroizon;

    private boolean isMove = false;

    private EditText editTextText;
    private MaterialSpinner FontSizeSpinnerText, FontAngleSpinnerText, FontSpaceSpinnerText, FontTypefaceSpinnerText;
    private CheckBox FontBoldText, FontUnderLineText, FontTileText;
    private Button btnCancelText, btnSureText, btnDelText;
    private List<TypeFaceInfo> fontTypeface;
    private List<String> fontTypefaceString;

    private MaterialSpinner ClockNoSpinnerClock, ClockContentSpinnerClock, DateStyleSpinnerClock, TimeStyleSpinnerClock, FontSizeSpinnerClock, FontAngleSpinnerClock, FontTypefaceSpinnerClock;
    private CheckBox FontTileClock, FontBoldClock, FontUnderLineClock;
    private Button btnCancelClock, btnSureClock, btnDelClock;
    private EditText DeviationClock;
    private boolean ClockNo1 = false, ClockNo2 = false, ClockNo3 = false, ClockNo4 = false;
    List<String> dateList, timeList;

    private MaterialSpinner CodeTypeSpinner, CodeSizeSpinner, CodeDirectionSpinner, CorrectionRatioSpinner, ReverseSpinner, CodeContent1Spinner, CodeContent2Spinner, CodeContent3Spinner, CodeContent4Spinner, CodeContent5Spinner;
    private TextView CodeStyle;
    private ImageButton DelCode;
    private List<String> CodeList1, CodeList2, CodeList3;
    private String CodeContent1 = "", CodeContent2 = "", CodeContent3 = "", CodeContent4 = "", CodeContent5 = "";
    private Button btnCancelCode, btnSureCode, btnDelCode;
    private FileItem fileItem1;
    private EditText CodeClassNo1, CodeClassNo2, CodeClassNo3, CodeClassNo4, CodeClassNo5;
    private TextView CodeTimeStart1, CodeTimeStart2, CodeTimeStart3, CodeTimeStart4, CodeTimeStart5;
    private TextView CodeTimeEnd1, CodeTimeEnd2, CodeTimeEnd3, CodeTimeEnd4, CodeTimeEnd5;
    private Map codeMap;

    private Button btnCancelClass, btnSureClass, btnDelClass;
    private TextView TimeStart1, TimeStart2, TimeStart3, TimeStart4, TimeStart5, TimeEnd1, TimeEnd2, TimeEnd3, TimeEnd4, TimeEnd5;
    private EditText ClassNo1, ClassNo2, ClassNo3, ClassNo4, ClassNo5;
    private MaterialSpinner FontSizeSpinnerClass, FontAngleSpinnerClass, FontTypefaceSpinnerClass;


    private Button btnCancelImg, btnSureImg, btnDelImg;
    private MaterialSpinner ImgDireciton, ImgName, ImgScale;
    private List<TypeFaceInfo> imgInfos;
    private List<String> imgInfoStrings;

    private Button btnCancelTextSource, btnSureTextSource, btnDelTextSource;
    private EditText textTextSource;
    private TextView TextSourceForUSB;
    private CheckBox FontTileTextSource, FontBoldTextSource, FontUnderLineTextSource;
    private MaterialSpinner FontSizeSpinnerTextSource, FontAngleSpinnerTextSource, FontSpaceSpinnerTextSource, FontTypefaceSpinnerTextSource, TextSourceSpinner;
    private FileItem fileItem;

    private Button btnCancelTextNo, btnSureTextNo, btnDelTextNo;
    private TextView NowValueEt;
    private EditText RepeatCountEt, StringLengthEt, LimitNo1, LimitNo2;
    private CheckBox FontTileNo, FontBoldNo, FontUnderLineNo, PrePositionZero;
    private MaterialSpinner FontSizeSpinnerNo, FontAngleSpinnerNo, FontSpaceSpinnerNo, FontTypefaceSpinnerNo, NoSpinnerNo;
    private boolean NoNo1 = false, NoNo2 = false, NoNo3 = false, NoNo4 = false;


    private SimpleDateFormat df = new SimpleDateFormat("MMddHHmm");
    private SimpleDateFormat df1 = new SimpleDateFormat("MM-dd HH-mm-ss");
    private DecimalFormat df11 = new DecimalFormat("0.00");
    private Context context;
    private ConstraintSet set;
    private ConstraintSet set1;
    private int nowTextViewId = 1000;
    private int nowClockId = 1100;
    private int nowCodeId = 1200;
    private int nowClassId = 1300;
    private int nowImgId = 1400;
    private int nowNoId = 1500;
    private int nowTextSourceId = 1600;

    private int lineId = 10123;
    public static int flag = -1;

    private View clickView;
    private View oldClickView;

    private List<View> AddViews;
    private List<View> lineView;

    private int defaultHeight = 1000;

    private int moveDistance = 1;
    private int _xDelta;
    private int _yDelta;
    private int topMar;
    private int leftMar;
    private int bili = 10;
    private SaveJsonToMemory saveJsonToMemory;

    private View nowShowView = null;
    //    private int nozzleCount = 4;
    private int REQUESTCODE_FROM_ACTIVITY = 1000;
    private int REQUESTCODE_FROM_ACTIVITY1 = 1001;
    private int x = 0;

    public static InfoEditFragment newInstance() {
        Bundle args = new Bundle();
        InfoEditFragment homeFragment = new InfoEditFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_edit, container, false);
        //初始化控件
        unbinder = ButterKnife.bind(this, view);

        context = getContext();
        //绑定布局
        set = new ConstraintSet();
        set1 = new ConstraintSet();
        set.clone(conss);
        set1.clone(BigConss);
        //文件管理
        saveJsonToMemory = new SaveJsonToMemory();

        initView();

        loadInSdcard();

        return view;
    }

    //清空view
    private void clearView() {
        if (AddViews == null) {
            AddViews = new ArrayList<>();
        } else {
            for (int i = 0; i < AddViews.size(); i++) {
                conss.removeView(AddViews.get(i));
            }
            AddViews.clear();
        }
    }

    //初始化行数
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

    //重新添加横数
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
        set.constrainWidth(line.getId(), BaseUtils.dp2px(defaultHeight));
        set.constrainHeight(line.getId(), BaseUtils.dp2px(1));
        set.applyTo(conss);
        lineView.add(line);
    }

    private void trendsView() {

        fontTypeface = traverseFolder1(BaseUtils.TypefacePath, "ttf", "fon");
        fontTypeface.add(0, new TypeFaceInfo("", "DroidSans.ttf"));
        fontTypefaceString.clear();
        for (TypeFaceInfo typeFaceInfo : fontTypeface) {
            fontTypefaceString.add(typeFaceInfo.getName());
        }
        imgInfos = traverseFolder1(BaseUtils.ImgInfoPath, "bmp", "bmp");
        imgInfoStrings.clear();
        for (TypeFaceInfo typeFaceInfo : imgInfos) {
            imgInfoStrings.add(typeFaceInfo.getName());
        }

        SaveJsonToMemory sj = new SaveJsonToMemory();
        String dateString = sj.LoadDateCodeToMemory();
        String timeString = sj.LoadTimeCodeToMemory();
        JSONArray CodeArDate = JSONArray.parseArray(dateString);
        JSONArray CodeArTime = JSONArray.parseArray(timeString);
        dateList.clear();
        if (CodeArDate != null) {
            for (int i = 0; i < CodeArDate.size(); i++) {
                JSONObject jsonObject = CodeArDate.getJSONObject(i);
                dateList.add(jsonObject.getString("dateStyle"));
            }
        }
        if (dateList.size() == 0) {
            dateList.add("yyyy/MM/dd");
        }

        timeList.clear();
        if (CodeArTime != null) {
            for (int i = 0; i < CodeArTime.size(); i++) {
                JSONObject jsonObject = CodeArTime.getJSONObject(i);
                timeList.add(jsonObject.getString("timeStyle"));
            }
        }
        if (timeList.size() == 0) {
            timeList.add("HH:mm:ss");
        }
    }

    private void initView() {
        dateList = new ArrayList<>();
        timeList = new ArrayList<>();
        CodeList1 = new ArrayList<>();
        CodeList2 = new ArrayList<>();
        CodeList3 = new ArrayList<>();
        fontTypeface = new ArrayList<>();
        fontTypefaceString = new ArrayList<>();
        imgInfos = new ArrayList<>();
        imgInfoStrings = new ArrayList<>();
        CodeList1.add(getString(R.string.PleaseChoose));
        CodeList1.add(getString(R.string.text));

        CodeList2.add(getString(R.string.PleaseChoose));
        CodeList2.add(getString(R.string.text));
        CodeList2.add(getString(R.string.Clock));
        CodeList2.add(getString(R.string.SerialNumber));

        CodeList3.add(getString(R.string.PleaseChoose));
        CodeList3.add(getString(R.string.text));
        CodeList3.add(getString(R.string.Clock));
        CodeList3.add(getString(R.string.SerialNumber));
        CodeList3.add(getString(R.string.serialport));
        CodeList3.add(getString(R.string.Changements));

        trendsView();
    }

    public void loadInSdcard() {

        String str = saveJsonToMemory.LoadMsgInfo(PreferenceUtils.getInstance().defaultMsg);
        JSONArray array = JSONArray.parseArray(str);
        boolean xx;
        boolean yy;
        boolean zz;
        Typeface typeFace = null;
        if (array == null) {
            clearView();
            InitLine(PreferenceUtils.getInstance().nozzleCount);
            return;
        }
        clearView();
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
                    clearNoOrAddNo(Integer.parseInt(date.getString("no")), false);
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
                    clockTextView.setOnTouchListener(this);
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
                    codeImageView.setCodeContent(date.getString("codeContent"));
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
                    codeImageView.setId(nowCodeId);
                    codeImageView.setOnClickListener(this);
                    codeImageView.setOnTouchListener(this);
                    nowCodeId++;
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
                        return;
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
                    imgImageViw.setOnTouchListener(this);
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
                        return;
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
                    classTextView.setOnTouchListener(this);
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
                    noTextView.setId( nowNoId++);
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
                    noTextView.setOnTouchListener(this);
                    noTextView.setNo(Integer.parseInt(date.getString("no")));
                    noTextView.setRepectCount(Integer.parseInt(date.getString("repeatCount")));
                    noTextView.setLengthAdd(Integer.parseInt(date.getString("lengthAdd")));
                    noTextView.setLimitNo1(Integer.parseInt(date.getString("limitNo1")));
                    noTextView.setLimitNo2(Integer.parseInt(date.getString("limitNo2")));
                    noTextView.setProPositionZero(Boolean.parseBoolean(date.getString("proPositionZero")));

                    clearNoOrAddNoToNo(Integer.parseInt(date.getString("no")), false);
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
                    textSourceTextView.setOnTouchListener(this);

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
                    InsertTextView.setId(nowTextViewId);
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
                    InsertTextView.setOnTouchListener(this);
                    nowTextViewId++;

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


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void DynamicShow(int i, boolean isChecked) {
        if (clickView instanceof VerticalTextView) {
            VerticalTextView verticalTextView = (VerticalTextView) clickView;
            switch (i) {
                case 5:
                    verticalTextView.setIt(isChecked);
                    break;
                case 6:
                    verticalTextView.setBold(isChecked);
                    break;
                case 7:
                    verticalTextView.setUnder(isChecked);
                    break;
            }
        } else if (clickView instanceof ClockTextView) {
            ClockTextView verticalTextView = (ClockTextView) clickView;
            switch (i) {
                case 5:
                    verticalTextView.setIt(isChecked);
                    break;
                case 6:
                    verticalTextView.setBold(isChecked);
                    break;
                case 7:
                    verticalTextView.setUnder(isChecked);
                    break;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void DynamicShow(int i, int position) {
        if (clickView instanceof VerticalTextView) {
            VerticalTextView verticalTextView = (VerticalTextView) clickView;
            switch (i) {
                case 1:
                    verticalTextView.setTextSize(Integer.parseInt((String) FontSizeSpinnerText.getItems().get(position)));
                    break;
                case 2:
                    if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[position]) == 90) {
                        verticalTextView.setDirection(1);
                    } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[position]) == 270) {
                        verticalTextView.setDirection(0);
                    } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[position]) == 0) {
                        verticalTextView.setDirection(2);
                    } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[position]) == 180) {
                        verticalTextView.setDirection(3);
                    }
                    break;
                case 3:
                    verticalTextView.setLetterSpace(Float.parseFloat(getResources().getStringArray(R.array.FontSpace)[position]));
                    break;
                case 4:
                    File file = new File(fontTypeface.get(position).getPath());
                    Typeface typeFace = null;
                    if (file.exists()) {
                        typeFace = Typeface.createFromFile(file);
                        verticalTextView.setTypefaces(typeFace, fontTypeface.get(position).getPath());
                    } else {
                        verticalTextView.setTypefaces(typeFace, "");
                    }
                    break;
            }
        } else if (clickView instanceof ClockTextView) {
            ClockTextView verticalTextView = (ClockTextView) clickView;
            switch (i) {
                case 12:
                    verticalTextView.setClockStyleNo(position);
                    verticalTextView.upText();
                    break;
                case 13:
                    verticalTextView.setDateStyle(dateList.get(position));
                    verticalTextView.upText();
                    break;
                case 14:
                    verticalTextView.setTimeStyle(timeList.get(position));
                    verticalTextView.upText();
                    break;
                case 15:
                    verticalTextView.setTextSize(Integer.parseInt((String) FontSizeSpinnerClock.getItems().get(position)));
                    break;
                case 16:
                    if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[position]) == 90) {
                        verticalTextView.setDirection(1);
                    } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[position]) == 270) {
                        verticalTextView.setDirection(0);
                    } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[position]) == 0) {
                        verticalTextView.setDirection(2);
                    } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[position]) == 180) {
                        verticalTextView.setDirection(3);
                    }
                    break;
                case 18:
                    File file = new File(fontTypeface.get(position).getPath());
                    Typeface typeface = null;
                    if (file.exists()) {
                        typeface = Typeface.createFromFile(file);
                        verticalTextView.setTypefaces(typeface, fontTypeface.get(position).getPath());
                    } else {
                        verticalTextView.setTypefaces(typeface, "");
                    }
                    break;
            }
        } else if (clickView instanceof NoTextView) {
            NoTextView noTextView = (NoTextView) clickView;
            switch (i) {
                case 1:
                    noTextView.setmTextSize(Integer.parseInt((String) FontSizeSpinnerNo.getItems().get(FontSizeSpinnerNo.getSelectedIndex())));
                    break;
                case 2:
                    noTextView.setDirection(FontAngleSpinnerNo.getSelectedIndex());
                    break;
                case 3:
                    noTextView.setSpaceletter(FontSpaceSpinnerNo.getSelectedIndex());
                    break;
                case 4:
                    File file = new File(fontTypeface.get(FontTypefaceSpinnerNo.getSelectedIndex()).getPath());
                    Typeface typeFace = null;
                    if (file.exists()) {
                        typeFace = Typeface.createFromFile(file);
                        noTextView.setTypefacePath(typeFace, fontTypeface.get(FontTypefaceSpinnerNo.getSelectedIndex()).getPath());
                    } else {
                        noTextView.setTypefacePath(typeFace, "");
                    }
                    break;
            }
        } else if (clickView instanceof TextSourceTextView) {
            TextSourceTextView noTextView = (TextSourceTextView) clickView;
            switch (i) {
                case 1:
                    noTextView.setmTextSize(Integer.parseInt((String) FontSizeSpinnerTextSource.getItems().get(FontSizeSpinnerTextSource.getSelectedIndex())));
                    break;
                case 2:
                    noTextView.setDirection(FontAngleSpinnerTextSource.getSelectedIndex());
                    break;
                case 3:
                    noTextView.setSpaceletter(FontSpaceSpinnerTextSource.getSelectedIndex());
                    break;
                case 4:
                    File file = new File(fontTypeface.get(FontTypefaceSpinnerTextSource.getSelectedIndex()).getPath());
                    Typeface typeFace = null;
                    if (file.exists()) {
                        typeFace = Typeface.createFromFile(file);
                        noTextView.setTypefacePath(typeFace, fontTypeface.get(FontTypefaceSpinnerTextSource.getSelectedIndex()).getPath());
                    } else {
                        noTextView.setTypefacePath(typeFace, "");
                    }
                    break;
            }
        }

    }

    public void MoveText(int tag, int mm) {
        if (mm == 1) {
            mm = 1;
        } else if (mm == 2) {
            mm = 5;
        } else if (mm == 3) {
            mm = 10;
        }
        switch (tag) {
            case 1:
                MoveStartTop(mm);
                break;
            case 2:
                MoveStartLeft(mm);
                break;
            case 3:
                MoveStartRight(mm);
                break;
            case 4:
                MoveStartBottom(mm);
                break;
        }
    }

    private void MoveStartTop(int mm) {
        if (clickView != null) {
            int x = clickView.getLeft();
            int y = clickView.getTop();
            set.connect(clickView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, y - moveDistance * mm);
            set.applyTo(conss);
        }
    }

    private void MoveStartLeft(int mm) {
        if (clickView != null) {
            int x = clickView.getLeft();
            int y = clickView.getTop();
            set.connect(clickView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, x - moveDistance * mm);
            set.applyTo(conss);
        }
    }

    private void MoveStartRight(int mm) {
        if (clickView != null) {
            int x = clickView.getLeft();
            int y = clickView.getTop();
            set.connect(clickView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, x + moveDistance * mm);
            set.applyTo(conss);
        }
    }

    private void MoveStartBottom(int mm) {
        if (clickView != null) {
            int x = clickView.getLeft();
            int y = clickView.getTop();
            set.connect(clickView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, y + moveDistance * mm);
            set.applyTo(conss);
        }

    }

    //文字点击事件
    @Override
    public void onClick(View v) {
        int clickI = v.getId();
        if (clickView != null) {
            //去掉边框
            clickView.setBackgroundColor(Color.TRANSPARENT);
        }
        if (v instanceof VerticalTextView) {
            UpdateTv(v);
        } else if (v instanceof ClockTextView) {
            UpdateClock(v);
        } else if (v instanceof CodeImageView) {
            UpdateCode(v);
        } else if (v instanceof ImgImageViw) {
            UpdateImg(v);
        } else if (v instanceof ClassTextView) {
            UpdateClass(v);
        } else if (v instanceof NoTextView) {
            UpdateNo(v);
        } else if (v instanceof TextSourceTextView) {
            UpdateTextSource(v);
        }
    }

    private void UpdateTextSource(View v) {
        clickView = v;
        btnSpildStringShow();
        hideMenu(true, 2);
        clickView.setBackground(getResources().getDrawable(R.drawable.tv_border));
        btnDelTextSource.setVisibility(View.VISIBLE);

        final TextSourceTextView textSourceTextView = (TextSourceTextView) clickView;
        textTextSource.setText(textSourceTextView.getTextLength() + "");
        TextSourceSpinner.setSelectedIndex(textSourceTextView.getSoutceType());
        FontAngleSpinnerTextSource.setSelectedIndex(textSourceTextView.getDirection());
        FontSpaceSpinnerTextSource.setSelectedIndex(textSourceTextView.getSpaceletter());
        FontTypefaceSpinnerTextSource.setSelectedIndex(0);

        FontBoldTextSource.setChecked(textSourceTextView.isBolder());
        FontTileTextSource.setChecked(textSourceTextView.isIt());
        FontUnderLineTextSource.setChecked(textSourceTextView.isUnderline());

        int x = FontSizeSpinnerTextSource.getItems().indexOf(textSourceTextView.getmTextSize() + "");
        FontSizeSpinnerTextSource.setSelectedIndex(x == -1 ? 0 : x);


        FontBoldTextSource.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                textSourceTextView.setBolder(isChecked);
            }
        });
        FontTileTextSource.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                textSourceTextView.setIt(isChecked);
            }
        });
        FontUnderLineTextSource.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                textSourceTextView.setUnderline(isChecked);
            }
        });
        FontSizeSpinnerTextSource.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DynamicShow(1, 0);
            }
        });
        FontAngleSpinnerTextSource.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DynamicShow(2, 0);
            }
        });
        FontSpaceSpinnerTextSource.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DynamicShow(3, 0);
            }
        });
        FontTypefaceSpinnerTextSource.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DynamicShow(4, 0);
            }
        });
    }

    private void UpdateNo(View v) {
        clickView = v;
        btnInsertNoShow();
        hideMenu(true, 1);
        clickView.setBackground(getResources().getDrawable(R.drawable.tv_border));
        btnDelTextNo.setVisibility(View.VISIBLE);
        final NoTextView noTextView = (NoTextView) clickView;
        NoSpinnerNo.setEnabled(false);
        NoSpinnerNo.setSelectedIndex(noTextView.getNo());
        NowValueEt.setText(noTextView.getmText());
        RepeatCountEt.setText(noTextView.getRepectCount() + "");
        StringLengthEt.setText(noTextView.getLengthAdd() + "");
        PrePositionZero.setChecked(noTextView.isProPositionZero());
        FontBoldNo.setChecked(noTextView.isBolder());
        FontTileNo.setChecked(noTextView.isIt());
        FontUnderLineNo.setChecked(noTextView.isUnderline());
        LimitNo1.setText(noTextView.getLimitNo1() + "");
        LimitNo2.setText(noTextView.getLimitNo2() + "");

        int x = FontSizeSpinnerNo.getItems().indexOf(noTextView.getmTextSize() + "");
        FontSizeSpinnerNo.setSelectedIndex(x == -1 ? 0 : x);
        FontAngleSpinnerNo.setSelectedIndex(noTextView.getDirection());
        FontSpaceSpinnerNo.setSelectedIndex(noTextView.getSpaceletter());
        FontTypefaceSpinnerNo.setSelectedIndex(0);

        PrePositionZero.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                noTextView.setProPositionZero(isChecked);
            }
        });
        FontBoldNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                noTextView.setBolder(isChecked);
            }
        });
        FontTileNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                noTextView.setIt(isChecked);
            }
        });
        FontUnderLineNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                noTextView.setUnderline(isChecked);
            }
        });
        FontSizeSpinnerNo.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DynamicShow(1, 0);
            }
        });
        FontAngleSpinnerNo.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DynamicShow(2, 0);
            }
        });
        FontSpaceSpinnerNo.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DynamicShow(3, 0);
            }
        });
        FontTypefaceSpinnerNo.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                DynamicShow(4, 0);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            trendsView();
            Trends();
            getPrintInfo();
        }
    }

    //  修改 序号
    private void getPrintInfo() {
        JSONObject jsonObject;
        JSONObject object;
        for (int i = 0; i < AddViews.size(); i++) {
            View view = AddViews.get(i);
            if (view instanceof NoTextView) {
                if (((NoTextView) view).getNo() == 0 && !PreferenceUtils.getInstance().no1.equals("")) {
                    //Log.e("xxxxxf", PreferenceUtils.getInstance().no1);
                    jsonObject = JSONObject.parseObject(PreferenceUtils.getInstance().no1);
                    object = jsonObject.getJSONObject("PrintNo");
                    if (object.size() == 0) continue;
                    ((NoTextView) view).setmText(object.getString("value"));
                } else if (((NoTextView) view).getNo() == 1 && !PreferenceUtils.getInstance().no2.equals("")) {
                    jsonObject = JSONObject.parseObject(PreferenceUtils.getInstance().no2);
                    object = jsonObject.getJSONObject("PrintNo");
                    if (object.size() == 0) continue;
                    ((NoTextView) view).setmText(object.getString("value"));
                } else if (((NoTextView) view).getNo() == 2 && !PreferenceUtils.getInstance().no3.equals("")) {
                    jsonObject = JSONObject.parseObject(PreferenceUtils.getInstance().no3);
                    object = jsonObject.getJSONObject("PrintNo");
                    if (object.size() == 0) continue;
                    ((NoTextView) view).setmText(object.getString("value"));
                } else if (((NoTextView) view).getNo() == 3 && !PreferenceUtils.getInstance().no4.equals("")) {
                    jsonObject = JSONObject.parseObject(PreferenceUtils.getInstance().no4);
                    object = jsonObject.getJSONObject("PrintNo");
                    if (object.size() == 0) continue;
                    ((NoTextView) view).setmText(object.getString("value"));
                }
            }
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

    @OnClick({R.id.btnSendPrint, R.id.btnCreateMsg, R.id.btnSavaMsg, R.id.btnInsertText, R.id.btnInsertclock, R.id.btnInsertNo, R.id.ibStart, R.id.ibStart1, R.id.btnInsertCode, R.id.btnInsertImg, R.id.btnInsertClass, R.id.btnSpildString})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSendPrint:
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("提示");
                builder.setMessage("确定是否发送");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                builder.setNegativeButton("发送", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        sendMsg();
                    }
                });
                builder.create().show();
                break;
            case R.id.btnCreateMsg:
                btnCreateMsgShow();
                hideMenu(true, 1);
                break;
            case R.id.btnSavaMsg:
                SavaMsg();
                break;
            case R.id.btnInsertText:
                //插入文本显示
                clearClick();
                btnInsertTextShow();
                hideMenu(true, 1);
                break;
            case R.id.btnInsertclock:
                //插入时钟
                clearClick();
                btnInsertclockShow();
                hideMenu(true, 1);
                break;
            case R.id.btnInsertNo:
                //插入序号
                clearClick();
                btnInsertNoShow();
                hideMenu(true, 1);
                break;
            case R.id.ibStart:
                homeLlControl.setVisibility(View.INVISIBLE);
                homeLlControl2.setVisibility(View.VISIBLE);
                break;
            case R.id.ibStart1:
                homeLlControl.setVisibility(View.VISIBLE);
                homeLlControl2.setVisibility(View.INVISIBLE);
                break;
            case R.id.btnInsertCode:
                //插入条码
                clearClick();
                btnInsertCodeShow(null);
                hideMenu(true, 2);
                break;
            case R.id.btnInsertImg:
                //插入图案
                clearClick();
                btnInsertImgShow();
                hideMenu(true, 2);
                break;
            case R.id.btnInsertClass:
                //插入班次
                clearClick();
                btnInsertClassShow();
                hideMenu(true, 2);
                break;
            case R.id.btnSpildString:
                clearClick();
                btnSpildStringShow();
                hideMenu(true, 2);
                break;
        }
    }

    private void sendMsg() {
        coverMsg(true);
        MainActivity.getInstance().sendMsg();
//        updateView();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 123:
                    for (int i = 0; i < lineView.size(); i++) {
                        lineView.get(i).setVisibility(View.INVISIBLE);
                    }
                    BitmapSaveBmp bitmapSaveBmp = new BitmapSaveBmp();
                    Bitmap bitmap = bitmapSaveBmp.getScrollBitmap(scroll, hroizon);
                    int w = getMaxLenth();
                    int h = (BaseUtils.muzzleHeight + 1) * PreferenceUtils.getInstance().nozzleCount;
                    bitmap = BaseUtils.cutBitmap(bitmap, w, h);
                    //BaseUtils.saveFile(bitmap, "1" + ".jpg");

                    //                    BaseUtils.saveFileBmp(BmpUtil.changeToMonochromeBitmap(bitmap), "test2.bmp");
                    for (int i = 0; i < lineView.size(); i++) {
                        lineView.get(i).setVisibility(View.VISIBLE);
                    }
                    break;
            }
            return false;
        }
    });

    private void updateView() {
        x = 0;
        for (int i = 0; i < AddViews.size(); i++) {
            final View view = AddViews.get(i);
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    x++;
                    if (x == AddViews.size()) {
                        handler.sendEmptyMessage(123);
                    }
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
            if (view instanceof TextSourceTextView) {
                if (!((TextSourceTextView) view).upText()) {
                    ToastUtils.ToastShow("文本分区已打印完,请重新设置!!");
                    new AlertDialog.Builder(context).setTitle("提示").setMessage("文本分区内容已打印完毕,请重新设置").
                            setPositiveButton("确定", null).show();
                    break;
                }
            } else if (view instanceof ClockTextView) {
                ((ClockTextView) view).upText();
            } else if (view instanceof ClassTextView) {
                ((ClassTextView) view).upText();
            } else if (view instanceof NoTextView) {
                ((NoTextView) view).PrintText();
            } else if (view instanceof CodeImageView) {
                CodeImageView codeImageView = (CodeImageView) view;
                String code = codeImageView.upDataStr();
                if (code.equals("")) {
                    ToastUtils.ToastShow("文本分区已打印完,请重新设置!!");
                    new AlertDialog.Builder(context).setTitle("提示").setMessage("文本分区内容已打印完毕,请重新设置").
                            setPositiveButton("确定", null).show();
                    return;
                }
                Bitmap bitmap = BarCode.getCodeForType(code, Integer.parseInt(codeImageView.getCodeStyle()), Integer.parseInt(codeImageView.getCodeSize()) * bili,
                        Integer.parseInt(codeImageView.getCodeDirection()), Integer.parseInt(codeImageView.getCodeCorrection()), codeImageView.getCodeReverse().equals("0") ? false : true, context);
                codeImageView.setImageBitmap(bitmap);
            }
        }
    }

    //最大的横向
    private int getMaxLenth() {
        int x = 0;
        for (int i = 0; i < AddViews.size(); i++) {
            int y = AddViews.get(i).getLeft() + AddViews.get(i).getWidth();
            x = x > y ? x : y;
        }

        //return x; //最大横
        return x > 400 ? 400 : x; //最大横不超过400
    }

    private void SavaMsg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(getString(R.string.prompt));
        builder.setMessage(getString(R.string.CurrentFile) + ":" + PreferenceUtils.getInstance().defaultMsg + ".msg" + "\n" + getString(R.string.SaveMethod));
        //  第一个按钮
        builder.setPositiveButton(getString(R.string.cover), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //  提示信息
                coverMsg(true);
            }
        });
        //  中间的按钮
        builder.setNeutralButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //  提示信息
                ToastUtils.ToastShow(getString(R.string.Notsaved));
            }
        });
        //  第三个按钮
        builder.setNegativeButton(getString(R.string.Create), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //  提示信息
                coverMsg(false);
            }
        });
        builder.create().show();
    }

    //创建新布局
    private void coverMsg(boolean isCover) {
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
        if (isCover) {
            saveJsonToMemory.SaveMsgInfo(array.toJSONString(), PreferenceUtils.getInstance().defaultMsg);
            ToastUtils.ToastShow(getString(R.string.Successfullycovered));
        } else {
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
        }
    }

    public void showTimePickerDialog(final int position, final boolean start) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new MyTimepickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, new MyTimepickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                showInText(position, start, hourOfDay, minute);
            }
        }, 0, 0, true).show();
    }

    private void showInText(int position, boolean start, int hourOfDay, int minute) {
        String strH = new DecimalFormat("00").format(hourOfDay);
        String strM = new DecimalFormat("00").format(minute);

        switch (position) {
            case 1:
                if (start) {
                    TimeStart1.setText(strH + ":" + strM);
                } else {
                    TimeEnd1.setText(strH + ":" + strM);
                }
                break;
            case 2:
                if (start) {
                    TimeStart2.setText(strH + ":" + strM);
                } else {
                    TimeEnd2.setText(strH + ":" + strM);
                }
                break;
            case 3:
                if (start) {
                    TimeStart3.setText(strH + ":" + strM);
                } else {
                    TimeEnd3.setText(strH + ":" + strM);
                }
                break;
            case 4:
                if (start) {
                    TimeStart4.setText(strH + ":" + strM);
                } else {
                    TimeEnd4.setText(strH + ":" + strM);
                }
                break;
            case 5:
                if (start) {
                    TimeStart5.setText(strH + ":" + strM);
                } else {
                    TimeEnd5.setText(strH + ":" + strM);
                }
                break;
        }
    }

    //条码内容选择
    private void CodeInsert(String i, View view, int position, Map map, int index) {
        switch (position) {
            case 1:
                CodeText(view, i, map, index);
                break;
            case 2:
                CodeClock(view, i, map);
                break;
            case 3:
                CodeNo(view, i, map);
                break;
            case 4:
                CodeSource(view, i, map);
                break;
            case 5:
                CodeClass(view, i, map);
                break;
        }
    }

    //条码串口
    private void CodeSource(final View v, final String i, final Map map) {
        set1.clone(BigConss);
        final View view = LayoutInflater.from(context).inflate(R.layout.code_serial_port, null);
        view.setOnClickListener(this);

        final EditText editText = view.findViewById(R.id.editText);
        final MaterialSpinner TextSourceSoinner = view.findViewById(R.id.TextSourceSoinner);
        TextSourceSoinner.setItems(getString(R.string.SelectSource), "Port", "USB", "Ethernet");
        Button btnCancelTextSource = view.findViewById(R.id.btnCancelTextSource);
        Button btnSureTextSource = view.findViewById(R.id.btnSureTextSource);
        if (codeMap != null) {
            Object o = codeMap.get("" + i);
            if (o instanceof CodeBean.CodeSource) {
                editText.setText(((CodeBean.CodeSource) o).getLength() + "");
            }
        }


        TextSourceSoinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                ChoseTextSource1(position, TextSourceSoinner);
            }
        });


        btnCancelTextSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                BigConss.removeView(view);
                v.setVisibility(View.VISIBLE);
            }
        });

        btnSureTextSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (editText.getText().toString().trim().equals("")) {
                    ToastUtils.ToastShow(getString(R.string.DataEmpty));
                    return;
                }
                if (TextSourceSoinner.getSelectedIndex() == 0) {
                    ToastUtils.ToastShow(getString(R.string.DataSource));
                    return;
                }
                if (fileItem1 == null && TextSourceSoinner.getSelectedIndex() == 2) {
                    ToastUtils.ToastShow(getString(R.string.DataSource));
                    return;
                }

                BigConss.removeView(view);
                DelCode.setVisibility(View.VISIBLE);
                CodeBean.CodeSource codeSource = new CodeBean.CodeSource(Integer.parseInt(editText.getText().toString().trim()), TextSourceSoinner.getSelectedIndex());
                if (TextSourceSoinner.getSelectedIndex() == 2) {
                    codeSource.setPath(Environment.getExternalStorageDirectory() + "/industrial/text/" + fileItem1.getName());
                }
                map.put(i, codeSource);
                getCodeStyle(i, map);
                v.setVisibility(View.VISIBLE);
            }
        });

        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainWidth(view.getId(), BigConss.getWidth());
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);

        v.setVisibility(View.INVISIBLE);
    }

    private void ChoseTextSource1(int position, MaterialSpinner TextSourceSoinner) {
        switch (position) {
            case 0:
                break;
            case 1:
                ChoseForPort1();
                break;
            case 2:
                ChoseForUSB1(TextSourceSoinner);
                break;
            case 3:
                ChoseForEthernet1();
                break;
        }
    }

    private void ChoseForEthernet1() {

    }

    private void ChoseForPort1() {

    }

    private void ChoseForUSB1(MaterialSpinner TextSourceSoinner) {
        File file = new File(BaseUtils.ImportPath);
        if (!file.exists()) {
            ToastUtils.ToastShow(getString(R.string.UdiskDetected));
            TextSourceSoinner.setSelectedIndex(0);
            return;
        }
        new LFilePicker()
                .withSupportFragment(InfoEditFragment.this)
                .withBackgroundColor("#868686")
                .withMutilyMode(false)
                .withRequestCode(REQUESTCODE_FROM_ACTIVITY1)
                .withFileFilter(new String[]{".txt"})
                .withStartPath("/mnt/usb_storage/USB_DISK1/udisk0")//指定初始显示路径
                .withIsGreater(false)//过滤文件大小 小于指定大小的文件
                .withFileSize(500 * 1024)//指定文件大小为500K
                .start();
    }

    //条码班次
    private void CodeClass(final View v, final String i, final Map map) {
        set1.clone(BigConss);
        final View view = LayoutInflater.from(context).inflate(R.layout.code_class, null);
        view.setOnClickListener(this);
        List<ClassItem> classItems = new ArrayList<>();
        CodeTimeStart1 = view.findViewById(R.id.TimeStart1);
        CodeTimeStart2 = view.findViewById(R.id.TimeStart2);
        CodeTimeStart3 = view.findViewById(R.id.TimeStart3);
        CodeTimeStart4 = view.findViewById(R.id.TimeStart4);
        CodeTimeStart5 = view.findViewById(R.id.TimeStart5);

        CodeTimeEnd1 = view.findViewById(R.id.TimeEnd1);
        CodeTimeEnd2 = view.findViewById(R.id.TimeEnd2);
        CodeTimeEnd3 = view.findViewById(R.id.TimeEnd3);
        CodeTimeEnd4 = view.findViewById(R.id.TimeEnd4);
        CodeTimeEnd5 = view.findViewById(R.id.TimeEnd5);

        CodeClassNo1 = view.findViewById(R.id.ClassNo1);
        CodeClassNo2 = view.findViewById(R.id.ClassNo2);
        CodeClassNo3 = view.findViewById(R.id.ClassNo3);
        CodeClassNo4 = view.findViewById(R.id.ClassNo4);
        CodeClassNo5 = view.findViewById(R.id.ClassNo5);

        if (codeMap != null) {
            Object o = codeMap.get("" + i);
            if (o instanceof CodeBean.CodeClazz) {
                classItems = ((CodeBean.CodeClazz) o).getClassItems();
                for (int xx = 0; xx < classItems.size(); xx++) {
                    ClassItem classItem = classItems.get(xx);
                    switch (xx) {
                        case 0:
                            CodeTimeStart1.setText(classItem.getStartTime());
                            CodeTimeEnd1.setText(classItem.getEndTime());
                            CodeClassNo1.setText(classItem.getClazzText());
                            break;
                        case 1:
                            CodeTimeStart2.setText(classItem.getStartTime());
                            CodeTimeEnd2.setText(classItem.getEndTime());
                            CodeClassNo2.setText(classItem.getClazzText());
                            break;
                        case 2:
                            CodeTimeStart3.setText(classItem.getStartTime());
                            CodeTimeEnd3.setText(classItem.getEndTime());
                            CodeClassNo3.setText(classItem.getClazzText());
                            break;
                        case 3:
                            CodeTimeStart4.setText(classItem.getStartTime());
                            CodeTimeEnd4.setText(classItem.getEndTime());
                            CodeClassNo4.setText(classItem.getClazzText());
                            break;
                        case 4:
                            CodeTimeStart5.setText(classItem.getStartTime());
                            CodeTimeEnd5.setText(classItem.getEndTime());
                            CodeClassNo5.setText(classItem.getClazzText());
                            break;
                    }
                }
            }
        }


        CodeTimeStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog1(1, true);
            }
        });

        CodeTimeStart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog1(2, true);
            }
        });
        CodeTimeStart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog1(3, true);
            }
        });
        CodeTimeStart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog1(4, true);
            }
        });
        CodeTimeStart5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog1(5, true);
            }
        });


        CodeTimeEnd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog1(1, false);
            }
        });

        CodeTimeEnd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog1(2, false);
            }
        });
        CodeTimeEnd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog1(3, false);
            }
        });
        CodeTimeEnd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog1(4, false);
            }
        });
        CodeTimeEnd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog1(5, false);
            }
        });


        Button btnCancelClass = view.findViewById(R.id.btnCancelClass);
        Button btnSureClass = view.findViewById(R.id.btnSureClass);

        btnCancelClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                BigConss.removeView(view);
                v.setVisibility(View.VISIBLE);
            }
        });

        final List<ClassItem> finalClassItems = classItems;
        btnSureClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                getClassItem1(finalClassItems);
                if (finalClassItems.size() == 0) {
                    ToastUtils.ToastShow(getString(R.string.SelectTmeAndShift));
                    return;
                }

                BigConss.removeView(view);
                DelCode.setVisibility(View.VISIBLE);
                map.put(i, new CodeBean.CodeClazz(finalClassItems));
                getCodeStyle(i, map);
                v.setVisibility(View.VISIBLE);
            }
        });

        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainWidth(view.getId(), BigConss.getWidth());
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);

        v.setVisibility(View.INVISIBLE);
    }

    private void getClassItem1(List<ClassItem> classItems) {
        if (!CodeTimeStart1.getText().equals(getString(R.string.ClickSettings)) && !CodeTimeEnd1.getText().equals(getString(R.string.ClickSettings)) && !CodeClassNo1.getText().toString().equals("")) {
            classItems.add(new ClassItem(CodeTimeStart1.getText().toString(), CodeTimeEnd1.getText().toString(), CodeClassNo1.getText().toString()));
        }
        if (!CodeTimeStart2.getText().equals(getString(R.string.ClickSettings)) && !CodeTimeEnd2.getText().equals(getString(R.string.ClickSettings)) && !CodeClassNo2.getText().equals("")) {
            classItems.add(new ClassItem(CodeTimeStart2.getText().toString(), CodeTimeEnd2.getText().toString(), CodeClassNo2.getText().toString()));
        }
        if (!CodeTimeStart3.getText().equals(getString(R.string.ClickSettings)) && !CodeTimeEnd3.getText().equals(getString(R.string.ClickSettings)) && !CodeClassNo3.getText().equals("")) {
            classItems.add(new ClassItem(CodeTimeStart3.getText().toString(), CodeTimeEnd3.getText().toString(), CodeClassNo3.getText().toString()));
        }
        if (!CodeTimeStart4.getText().equals(getString(R.string.ClickSettings)) && !CodeTimeEnd4.getText().equals(getString(R.string.ClickSettings)) && !CodeClassNo4.getText().equals("")) {
            classItems.add(new ClassItem(CodeTimeStart4.getText().toString(), CodeTimeEnd4.getText().toString(), CodeClassNo4.getText().toString()));
        }
        if (!CodeTimeStart5.getText().equals(getString(R.string.ClickSettings)) && !CodeTimeEnd5.getText().equals(getString(R.string.ClickSettings)) && !CodeClassNo5.getText().equals("")) {
            classItems.add(new ClassItem(CodeTimeStart5.getText().toString(), CodeTimeEnd5.getText().toString(), CodeClassNo5.getText().toString()));
        }
    }

    public void showTimePickerDialog1(final int position, final boolean start) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new MyTimepickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, new MyTimepickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                showInText1(position, start, hourOfDay, minute);
            }
        }, 0, 0, true).show();
    }

    private void showInText1(int position, boolean start, int hourOfDay, int minute) {
        String strH = new DecimalFormat("00").format(hourOfDay);
        String strM = new DecimalFormat("00").format(minute);

        switch (position) {
            case 1:
                if (start) {
                    CodeTimeStart1.setText(strH + ":" + strM);
                } else {
                    CodeTimeEnd1.setText(strH + ":" + strM);
                }
                break;
            case 2:
                if (start) {
                    CodeTimeStart2.setText(strH + ":" + strM);
                } else {
                    CodeTimeEnd2.setText(strH + ":" + strM);
                }
                break;
            case 3:
                if (start) {
                    CodeTimeStart3.setText(strH + ":" + strM);
                } else {
                    CodeTimeEnd3.setText(strH + ":" + strM);
                }
                break;
            case 4:
                if (start) {
                    CodeTimeStart4.setText(strH + ":" + strM);
                } else {
                    CodeTimeEnd4.setText(strH + ":" + strM);
                }
                break;
            case 5:
                if (start) {
                    CodeTimeStart5.setText(strH + ":" + strM);
                } else {
                    CodeTimeEnd5.setText(strH + ":" + strM);
                }
                break;
        }
    }

    //条码序号
    private void CodeNo(final View v, final String i, final Map map) {
        set1.clone(BigConss);

        final View view = LayoutInflater.from(context).inflate(R.layout.code_no, null);
        view.setOnClickListener(this);
        final TextView NowValueEt = view.findViewById(R.id.NowValueEt);
        final EditText RepeatCountEt = view.findViewById(R.id.RepeatCountEt);
        final EditText StringLengthEt = view.findViewById(R.id.StringLengthEt);
        final EditText LimitNo1 = view.findViewById(R.id.LimitNo1);
        final EditText LimitNo2 = view.findViewById(R.id.LimitNo2);
        final CheckBox PrePositionZero = view.findViewById(R.id.PrePositionZero);
        Button btnCancelNo = view.findViewById(R.id.btnCancelNo);
        Button btnSureNo = view.findViewById(R.id.btnSureNo);

        if (codeMap != null) {
            Object o = codeMap.get("" + i);
            if (o instanceof CodeBean.CodeNo) {
                NowValueEt.setText(((CodeBean.CodeNo) o).getNowValue() + "");
                RepeatCountEt.setText(((CodeBean.CodeNo) o).getRepeatCount() + "");
                StringLengthEt.setText(((CodeBean.CodeNo) o).getLengthAdd() + "");
                LimitNo1.setText(((CodeBean.CodeNo) o).getLimitNo1() + "");
                LimitNo2.setText(((CodeBean.CodeNo) o).getLimitNo2() + "");
                PrePositionZero.setChecked(((CodeBean.CodeNo) o).isPreZero());
            }

        }

        btnCancelNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                BigConss.removeView(view);
                v.setVisibility(View.VISIBLE);
            }
        });

        btnSureNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (RepeatCountEt.getText().toString().trim().equals("") || StringLengthEt.getText().toString().trim().equals("") ||
                        LimitNo1.getText().toString().trim().equals("") || LimitNo2.getText().toString().trim().equals("")) {
                    ToastUtils.ToastShow(getString(R.string.CompleteIinformation));
                    return;
                }
                if (Integer.parseInt(LimitNo2.getText().toString().trim()) < Integer.parseInt(LimitNo1.getText().toString().trim()) + Integer.parseInt(StringLengthEt.getText().toString().trim())) {
                    ToastUtils.ToastShow(getString(R.string.LlimitThan));
                    return;
                }
                BigConss.removeView(view);
                DelCode.setVisibility(View.VISIBLE);
                map.put(i, new CodeBean.CodeNo(NowValueEt.getText().toString().trim(), Integer.parseInt(RepeatCountEt.getText().toString().trim())
                        , Integer.parseInt(StringLengthEt.getText().toString().trim()), Integer.parseInt(LimitNo1.getText().toString().trim()),
                        Integer.parseInt(LimitNo2.getText().toString().trim()), PrePositionZero.isChecked()));
                getCodeStyle(i, map);
                v.setVisibility(View.VISIBLE);
            }
        });

        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainWidth(view.getId(), BigConss.getWidth());
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);

        v.setVisibility(View.INVISIBLE);
    }

    private void clearCodeStyle(int i) {
        switch (i) {
            case 0:
                CodeContent1 = "";
                CodeContent2 = "";
                CodeContent3 = "";
                CodeContent4 = "";
                CodeContent5 = "";

                CodeContent1Spinner.setSelectedIndex(0);
                CodeContent2Spinner.setSelectedIndex(0);
                CodeContent3Spinner.setSelectedIndex(0);
                CodeContent4Spinner.setSelectedIndex(0);
                CodeContent5Spinner.setSelectedIndex(0);

                break;
            case 1:
                CodeContent1 = "";
                break;
            case 2:
                CodeContent2 = "";
                break;
            case 3:
                CodeContent3 = "";
                break;
            case 4:
                CodeContent4 = "";
                break;
            case 5:
                CodeContent5 = "";
                break;
        }

        CodeStyle.setText(CodeContent1 + CodeContent2 + CodeContent3 + CodeContent4 + CodeContent5);
    }

    private void getCodeStyle(String i, Map map) {
        switch (i) {
            case "1":
                CodeContent1 = getStringForCode(map.get(i));
                break;
            case "2":
                CodeContent2 = getStringForCode(map.get(i));
                break;
            case "3":
                CodeContent3 = getStringForCode(map.get(i));
                break;
            case "4":
                CodeContent4 = getStringForCode(map.get(i));
                break;
            case "5":
                CodeContent5 = getStringForCode(map.get(i));
                break;
        }
        CodeStyle.setText(CodeContent1 + " " + CodeContent2 + " " + CodeContent3 + " " + CodeContent4 + " " + CodeContent5);
    }

    private String getStringForCode(Object o) {
        String x = "";
        if (o instanceof CodeBean.CodeText) {
            x = ((CodeBean.CodeText) o).getStr();
        } else if (o instanceof CodeBean.CodeClock) {
            CodeBean.CodeClock clock = (CodeBean.CodeClock) o;
            x = clock.getClockString();
        } else if (o instanceof CodeBean.CodeNo) {
            CodeBean.CodeNo codeNo = (CodeBean.CodeNo) o;
            x = codeNo.updateText();
        } else if (o instanceof CodeBean.CodeSource) {
            x = ((CodeBean.CodeSource) o).upText();
        } else if (o instanceof CodeBean.CodeClazz) {
            x = ((CodeBean.CodeClazz) o).updateText();
        }
        return x;
    }

    private void ChangeCodeType(int position) {
        clearCodeStyle(0);
        switch (position) {
            case 0:
            case 1:
                CodeContent1Spinner.setItems(CodeList3);
                CodeContent2Spinner.setItems(CodeList3);
                CodeContent3Spinner.setItems(CodeList3);
                CodeContent4Spinner.setItems(CodeList3);
                CodeContent5Spinner.setItems(CodeList3);

                CodeContent1Spinner.setEnabled(true);
                CodeContent2Spinner.setEnabled(true);
                CodeContent3Spinner.setEnabled(true);
                CodeContent4Spinner.setEnabled(true);
                CodeContent5Spinner.setEnabled(true);

                CodeDirectionSpinner.setEnabled(false);
                break;
            case 2:
            case 3:
                CodeContent1Spinner.setItems(CodeList1);
                CodeContent2Spinner.setItems(CodeList1);
                CodeContent3Spinner.setItems(CodeList1);
                CodeContent4Spinner.setItems(CodeList1);
                CodeContent5Spinner.setItems(CodeList1);

                CodeContent1Spinner.setEnabled(true);
                CodeContent2Spinner.setEnabled(false);
                CodeContent3Spinner.setEnabled(false);
                CodeContent4Spinner.setEnabled(false);
                CodeContent5Spinner.setEnabled(false);

                CodeDirectionSpinner.setEnabled(true);
                break;
            case 4:
                CodeContent1Spinner.setItems(CodeList2);
                CodeContent2Spinner.setItems(CodeList2);
                CodeContent3Spinner.setItems(CodeList2);
                CodeContent4Spinner.setItems(CodeList2);
                CodeContent5Spinner.setItems(CodeList2);

                CodeContent1Spinner.setEnabled(true);
                CodeContent2Spinner.setEnabled(true);
                CodeContent3Spinner.setEnabled(true);
                CodeContent4Spinner.setEnabled(true);
                CodeContent5Spinner.setEnabled(true);
                CodeDirectionSpinner.setEnabled(true);
                break;
        }
    }

    //条码时钟
    private void CodeClock(final View v, final String i, final Map map) {
        set1.clone(BigConss);
        final View view = LayoutInflater.from(context).inflate(R.layout.code_clock, null);
        view.setOnClickListener(this);

        Button btnCancelClock = view.findViewById(R.id.btnCancelClock);
        Button btnSureClock = view.findViewById(R.id.btnSureClock);
        final EditText DayDeviation = view.findViewById(R.id.DayDeviation);

        MaterialSpinner ClockContentSpinnerClock = view.findViewById(R.id.ClockContentSpinnerClock);
        final MaterialSpinner DateStyleSpinnerClock = view.findViewById(R.id.DateStyleSpinnerClock);
        final MaterialSpinner TimeStyleSpinnerClock = view.findViewById(R.id.TimeStyleSpinnerClock);

        ClockContentSpinnerClock.setItems(getString(R.string.date), getString(R.string.date) + "+" + getString(R.string.time), getString(R.string.time) + "+" + getString(R.string.date), getString(R.string.time));

        if (codeMap != null) {
            Object o = codeMap.get("" + i);
            if (o instanceof CodeBean.CodeClock) {
                DayDeviation.setText(((CodeBean.CodeClock) o).getDeviation());
            }
        }


        DateStyleSpinnerClock.setItems(dateList);
        TimeStyleSpinnerClock.setItems(timeList);
        DayDeviation.setMovementMethod(ScrollingMovementMethod.getInstance());
        final int[] spinner = {0, 0, 0};
        ClockContentSpinnerClock.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                spinner[0] = position;
                if (position == 0) {
                    DateStyleSpinnerClock.setEnabled(true);
                    TimeStyleSpinnerClock.setEnabled(false);
                } else if (position == 1) {
                    DateStyleSpinnerClock.setEnabled(true);
                    TimeStyleSpinnerClock.setEnabled(true);
                } else if (position == 2) {
                    DateStyleSpinnerClock.setEnabled(true);
                    TimeStyleSpinnerClock.setEnabled(true);
                } else if (position == 3) {
                    DateStyleSpinnerClock.setEnabled(false);
                    TimeStyleSpinnerClock.setEnabled(true);
                } else if (position == 4) {
                    DateStyleSpinnerClock.setEnabled(false);
                    TimeStyleSpinnerClock.setEnabled(false);
                }
            }
        });

        DateStyleSpinnerClock.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                spinner[1] = position;
            }
        });

        TimeStyleSpinnerClock.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                spinner[2] = position;
            }
        });

        btnCancelClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                BigConss.removeView(view);
                v.setVisibility(View.VISIBLE);
            }
        });
        btnSureClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                BigConss.removeView(view);
                DelCode.setVisibility(View.VISIBLE);
                map.put(i + "", new CodeBean.CodeClock(spinner[0] + "", dateList.get(spinner[1]), timeList.get(spinner[2]), DayDeviation.getText().toString()));
                getCodeStyle(i + "", map);
                v.setVisibility(View.VISIBLE);
            }
        });

        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainWidth(view.getId(), BigConss.getWidth());
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);

        v.setVisibility(View.INVISIBLE);
    }

    //文本条码
    private <T> void CodeText(final View v, final String i, final Map map, int index) {
        set1.clone(BigConss);
        final View view = LayoutInflater.from(context).inflate(R.layout.code_text, null);
        view.setOnClickListener(this);

        final EditText editText = view.findViewById(R.id.editTextText);
        Button btnCancelText = view.findViewById(R.id.btnCancelText);
        Button btnSureText = view.findViewById(R.id.btnSureText);

        if (index != 0 && index != 1) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER); //只能输入数字
        }

        btnCancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                BigConss.removeView(view);
                v.setVisibility(View.VISIBLE);
            }
        });
        btnSureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                if (editText.getText().toString().trim().equals("")) {
                    ToastUtils.ToastShow("内容不能为空,请重新输入!");
                    return;
                }
                BigConss.removeView(view);
                DelCode.setVisibility(View.VISIBLE);
                map.put(i, new CodeBean.CodeText(editText.getText().toString()));
                getCodeStyle(i, map);
                v.setVisibility(View.VISIBLE);
            }
        });

        if (codeMap != null) {
            Object o = codeMap.get("" + i);
            if (o instanceof CodeBean.CodeText) {
                editText.setText(((CodeBean.CodeText) o).getStr());
            }
        }


        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainWidth(view.getId(), BigConss.getWidth());
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);

        v.setVisibility(View.INVISIBLE);
    }

    //点击事件
    private void UpdateCode(View v) {
        clickView = v;
        oldClickView = v;
        btnInsertCodeShow(codeMap);
        hideMenu(true, 2);
        clickView.setBackground(getResources().getDrawable(R.drawable.tv_border));
        DelCode.setVisibility(View.VISIBLE);
        CodeImageView textView = (CodeImageView) clickView;

        CodeTypeSpinner.setSelectedIndex(Integer.parseInt(textView.getCodeStyle()));
        ChangeCodeType(Integer.parseInt(textView.getCodeStyle()));
        String y1 = textView.getCodeSize();
        int codeSize = CodeSizeSpinner.getItems().indexOf(y1);
        CodeSizeSpinner.setSelectedIndex(codeSize == -1 ? 0 : codeSize);

        codeMap = textView.getMap();
        for (int i = 1; i < 6; i++) {
            Object o = codeMap.get("" + i);
            if (o == null) continue;
            DynamicCode(i, o);
            getCodeStyle(i+"",codeMap);
            GoockrLog.Loge(o.toString());
        }
        CodeDirectionSpinner.setSelectedIndex(Integer.parseInt(textView.getCodeDirection()));
        CorrectionRatioSpinner.setSelectedIndex(Integer.parseInt(textView.getCodeCorrection()));
        ReverseSpinner.setSelectedIndex(Integer.parseInt(textView.getCodeReverse()));
        CodeStyle.setText(textView.getCodeContent());
    }

    private void DynamicCode(int i, Object o) {
        switch (i) {
            case 1:
                if (o instanceof CodeBean.CodeText) {
                    CodeContent1Spinner.setSelectedIndex(1);
                } else if (o instanceof CodeBean.CodeClock) {
                    CodeContent1Spinner.setSelectedIndex(2);
                } else if (o instanceof CodeBean.CodeNo) {
                    CodeContent1Spinner.setSelectedIndex(3);
                } else if (o instanceof CodeBean.CodeSource) {
                    CodeContent1Spinner.setSelectedIndex(4);
                } else if (o instanceof CodeBean.CodeClazz) {
                    CodeContent1Spinner.setSelectedIndex(5);
                }
                break;
            case 2:
                if (o instanceof CodeBean.CodeText) {
                    CodeContent2Spinner.setSelectedIndex(1);
                } else if (o instanceof CodeBean.CodeClock) {
                    CodeContent2Spinner.setSelectedIndex(2);
                } else if (o instanceof CodeBean.CodeNo) {
                    CodeContent2Spinner.setSelectedIndex(3);
                } else if (o instanceof CodeBean.CodeSource) {
                    CodeContent2Spinner.setSelectedIndex(4);
                } else if (o instanceof CodeBean.CodeClazz) {
                    CodeContent2Spinner.setSelectedIndex(5);
                }
                break;
            case 3:
                if (o instanceof CodeBean.CodeText) {
                    CodeContent3Spinner.setSelectedIndex(1);
                } else if (o instanceof CodeBean.CodeClock) {
                    CodeContent3Spinner.setSelectedIndex(2);
                } else if (o instanceof CodeBean.CodeNo) {
                    CodeContent3Spinner.setSelectedIndex(3);
                } else if (o instanceof CodeBean.CodeSource) {
                    CodeContent3Spinner.setSelectedIndex(4);
                } else if (o instanceof CodeBean.CodeClazz) {
                    CodeContent3Spinner.setSelectedIndex(5);
                }
                break;
            case 4:
                if (o instanceof CodeBean.CodeText) {
                    CodeContent4Spinner.setSelectedIndex(1);
                } else if (o instanceof CodeBean.CodeClock) {
                    CodeContent4Spinner.setSelectedIndex(2);
                } else if (o instanceof CodeBean.CodeNo) {
                    CodeContent4Spinner.setSelectedIndex(3);
                } else if (o instanceof CodeBean.CodeSource) {
                    CodeContent4Spinner.setSelectedIndex(4);
                } else if (o instanceof CodeBean.CodeClazz) {
                    CodeContent4Spinner.setSelectedIndex(5);
                }
                break;
            case 5:
                if (o instanceof CodeBean.CodeText) {
                    CodeContent5Spinner.setSelectedIndex(1);
                } else if (o instanceof CodeBean.CodeClock) {
                    CodeContent5Spinner.setSelectedIndex(2);
                } else if (o instanceof CodeBean.CodeNo) {
                    CodeContent5Spinner.setSelectedIndex(3);
                } else if (o instanceof CodeBean.CodeSource) {
                    CodeContent5Spinner.setSelectedIndex(4);
                } else if (o instanceof CodeBean.CodeClazz) {
                    CodeContent5Spinner.setSelectedIndex(5);
                }
                break;
        }
    }


    //清除选中的按钮
    private void clearClick() {
        if (clickView != null) {
            clickView.setBackgroundColor(Color.TRANSPARENT);
            clickView = null;
        }
    }

    private void hideMenu(boolean hideM, int position) {
        if (position == 1) {
            if (hideM) {
                homeLlControl.setVisibility(View.INVISIBLE);
                homeLlControl2.setVisibility(View.INVISIBLE);
            } else {
                homeLlControl.setVisibility(View.VISIBLE);
            }
        } else if (position == 2) {
            if (hideM) {
                homeLlControl.setVisibility(View.INVISIBLE);
                homeLlControl2.setVisibility(View.INVISIBLE);
            } else {
                homeLlControl2.setVisibility(View.VISIBLE);
            }
        }
    }

    private void backInsertText() {
//        if (oldClickView instanceof VerticalTextView && clickView instanceof VerticalTextView) {
//            VerticalTextView oC = (VerticalTextView) oldClickView;
//            VerticalTextView c = (VerticalTextView) clickView;
//            c.setText(oC.getmText());
//            c.setTextSize(oC.getTextSize());
//            c.setDirection(oC.getDirection());
//            c.setIt(oC.isIt());
//            c.setBold(oC.isBold());
//            c.setUnder(oC.isUnder());
//        }
    }

    //控件点击滑动事件
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean isTrue = false;
        if (v instanceof VerticalTextView || v instanceof ClockTextView || v instanceof ImgImageViw || v instanceof ClassTextView
                || v instanceof CodeImageView || v instanceof NoTextView || v instanceof TextSourceTextView) {
            isTrue = MoveView(v, event);
        }
        return isTrue;
    }

    private boolean MoveView(View v, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
//        GoockrLog.Loge(+event.getX() + "   " + event.getY() + "  " + v.getWidth() + "  " + v.getHeight() + "  " + v.getLeft() + "  " + v.getTop());
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isMove = false;
            flag = 0;
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            _xDelta = location[0];
            _yDelta = location[1];
            topMar = v.getTop() - (int) event.getY();
            leftMar = v.getLeft() - (int) event.getX();
//            GoockrLog.Loge(location[0] + "  " + location[1] + "   " + v.getTop() + "   " + v.getLeft());
            return false;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            isMove = true;
//            GoockrLog.Loge("  " + _xDelta + "   " + _yDelta + "   " + X + "   " + Y + "  " + v.getTop() + " " + v.getLeft() + "  " + v.getWidth() + "  " + v.getHeight());
            set.connect(v.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, Y - _yDelta + topMar);
            set.connect(v.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, X - _xDelta + leftMar);
            set.applyTo(conss);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            flag = -1;
            //todo 滑动粘合部分
            if ((Y - _yDelta + topMar) > 20) {

            }
            return isMove;
        }
        return false;

    }

    private class TypeFaceInfo {
        String path;
        String name;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public TypeFaceInfo(String path, String name) {

            this.path = path;
            this.name = name;
        }
    }

    private List<TypeFaceInfo> traverseFolder1(String path, String cons, String cons1) {
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        List<TypeFaceInfo> typeface = new ArrayList<>();
        if (!file.exists()) file.mkdirs();
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    list.add(file2);
                    folderNum++;
                } else {
                    if (file2.getName().toLowerCase().contains(cons) || file2.getName().toLowerCase().contains(cons1)) {
                        typeface.add(new TypeFaceInfo(file2.getPath(), file2.getName()));
                    }
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        list.add(file2);
                        folderNum++;
                    } else {
                        if (file2.getName().contains(cons)) {
                            typeface.add(new TypeFaceInfo(file2.getPath(), file2.getName()));
                        }
                        fileNum++;
                    }
                }
            }
        }
        return typeface;
    }

    private boolean ClockNoExist() {
        boolean isEx = false;
        switch (ClockNoSpinnerClock.getSelectedIndex()) {
            case 0:
                if (ClockNo1) {
                    isEx = true;
                }
                break;
            case 1:
                if (ClockNo2) {
                    isEx = true;
                }
                break;
            case 2:
                if (ClockNo3) {
                    isEx = true;
                }
                break;
            case 3:
                if (ClockNo4) {
                    isEx = true;
                }
                break;
        }
        return isEx;
    }

    private boolean NoNoExist() {
        boolean isEx = false;
        switch (NoSpinnerNo.getSelectedIndex()) {
            case 0:
                if (NoNo1) {
                    isEx = true;
                }
                break;
            case 1:
                if (NoNo2) {
                    isEx = true;
                }
                break;
            case 2:
                if (NoNo3) {
                    isEx = true;
                }
                break;
            case 3:
                if (NoNo4) {
                    isEx = true;
                }
                break;
        }
        return isEx;
    }

    private void clearNoOrAddNoToNo(int no, boolean claerNo) {
        switch (no) {
            case 0:
                if (claerNo) {
                    NoNo1 = false;
                } else {
                    NoNo1 = true;
                }
                break;
            case 1:
                if (claerNo) {
                    NoNo2 = false;
                } else {
                    NoNo2 = true;
                }
                break;
            case 2:
                if (claerNo) {
                    NoNo3 = false;
                } else {
                    NoNo3 = true;
                }
                break;
            case 3:
                if (claerNo) {
                    NoNo4 = false;
                } else {
                    NoNo4 = true;
                }
                break;
            case 4:
                if (claerNo) {
                    NoNo1 = false;
                    NoNo2 = false;
                    NoNo3 = false;
                    NoNo4 = false;
                }
                break;
        }
    }

    private void clearNoOrAddNo(int no, boolean claerNo) {
        switch (no) {
            case 0:
                if (claerNo) {
                    ClockNo1 = false;
                } else {
                    ClockNo1 = true;
                }
                break;
            case 1:
                if (claerNo) {
                    ClockNo2 = false;
                } else {
                    ClockNo2 = true;
                }
                break;
            case 2:
                if (claerNo) {
                    ClockNo3 = false;
                } else {
                    ClockNo3 = true;
                }
                break;
            case 3:
                if (claerNo) {
                    ClockNo4 = false;
                } else {
                    ClockNo4 = true;
                }
                break;
            case 4:
                if (claerNo) {
                    ClockNo1 = false;
                    ClockNo2 = false;
                    ClockNo3 = false;
                    ClockNo4 = false;
                }
                break;
        }
    }

    //插入序号
    private void btnInsertNoShow() {
        final View view = LayoutInflater.from(context).inflate(R.layout.insert_no, null);
        if (nowShowView != null) {
            BigConss.removeView(nowShowView);
        }
        nowShowView = view;

        btnCancelTextNo = view.findViewById(R.id.btnCancelNo);
        btnSureTextNo = view.findViewById(R.id.btnSureNo);
        btnDelTextNo = view.findViewById(R.id.btnDelNo);

        FontSizeSpinnerNo = view.findViewById(R.id.FontSizeSpinner);
        FontAngleSpinnerNo = view.findViewById(R.id.FontAngleSpinner);
        FontSpaceSpinnerNo = view.findViewById(R.id.FontSpaceSpinner);
        FontTypefaceSpinnerNo = view.findViewById(R.id.FontTypefaceSpinner);
        NoSpinnerNo = view.findViewById(R.id.NoNo);
        NoSpinnerNo.setItems("1", "2", "3", "4");
        FontSizeSpinnerNo.setItems("24", "32", "40", "48", "64", "80", "96", "112", "144");
        FontSizeSpinnerNo.setSelectedIndex(2);
        FontAngleSpinnerNo.setItems("0", "90", "180", "270");
        FontSpaceSpinnerNo.setItems("1", "2", "3", "4");
        FontTypefaceSpinnerNo.setItems(fontTypefaceString);
        NowValueEt = view.findViewById(R.id.NowValueEt);
        RepeatCountEt = view.findViewById(R.id.RepeatCountEt);
        StringLengthEt = view.findViewById(R.id.StringLengthEt);
        LimitNo1 = view.findViewById(R.id.LimitNo1);
        LimitNo2 = view.findViewById(R.id.LimitNo2);
        FontTileNo = view.findViewById(R.id.FontTile);
        FontBoldNo = view.findViewById(R.id.FontBold);
        FontUnderLineNo = view.findViewById(R.id.FontUnderLine);
        PrePositionZero = view.findViewById(R.id.PrePositionZero);

        FontSizeSpinnerNo.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

            }
        });

        btnCancelTextNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMenu(false, 1);
                clearClick();
                nowShowView = null;
                BigConss.removeView(view);
            }
        });
        btnSureTextNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SureNo(view);
            }
        });
        btnDelTextNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigConss.removeView(view);
                hideMenu(false, 1);
                conss.removeView(clickView);
                AddViews.remove(clickView);
                clearNoOrAddNoToNo(((NoTextView) clickView).getNo(), true);
                if (clickView instanceof NoTextView) {
                    if (((NoTextView) clickView).getNo() == 0) {
                        PreferenceUtils.getInstance().setNo1("");
                    } else if (((NoTextView) clickView).getNo() == 1) {
                        PreferenceUtils.getInstance().setNo2("");
                    } else if (((NoTextView) clickView).getNo() == 2) {
                        PreferenceUtils.getInstance().setNo3("");
                    } else if (((NoTextView) clickView).getNo() == 3) {
                        PreferenceUtils.getInstance().setNo4("");
                    }
                }
                nowShowView = null;
                clearClick();
            }
        });

        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainWidth(view.getId(), BaseUtils.getDisplayWidth(context) * 7 / 9);
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);

    }

    private void SureNo(View view) {
        if (clickView == null) {
            if (NoNoExist()) {
                ToastUtils.ToastShow(getString(R.string.NumberExists));
                return;
            }
            if (!AccordWithNo()) {
                ToastUtils.ToastShow(getString(R.string.FillInformation));
                return;
            }
            if (Integer.parseInt(LimitNo2.getText().toString().trim()) < Integer.parseInt(LimitNo1.getText().toString().trim()) + Integer.parseInt(StringLengthEt.getText().toString().trim())) {
                ToastUtils.ToastShow(getString(R.string.LlimitThan));
                return;
            }
            clearNoOrAddNoToNo(NoSpinnerNo.getSelectedIndex(), false);

            boolean xx = FontTileNo.isChecked();
            boolean yy = FontBoldNo.isChecked();
            boolean zz = FontUnderLineNo.isChecked();

            NoTextView noTextView = new NoTextView(context);
            noTextView.setId(nowNoId);
            noTextView.setFontStyle(yy, xx, zz);
            noTextView.setmText(NowValueEt.getText().toString().trim());
            if (FontTypefaceSpinnerNo.getSelectedIndex() != 0) {
                // 获取typeface
                Typeface typeFace = Typeface.createFromFile(fontTypeface.get(FontTypefaceSpinnerNo.getSelectedIndex()).getPath());
                // 应用字体
                noTextView.setTypefacePath(typeFace, fontTypeface.get(FontTypefaceSpinnerNo.getSelectedIndex()).getPath());
            }
            noTextView.setNo(NoSpinnerNo.getSelectedIndex());
            noTextView.setSpaceletter(FontSpaceSpinnerNo.getSelectedIndex());

            noTextView.setDirection(FontAngleSpinnerNo.getSelectedIndex());

            noTextView.setLimitNo1(Integer.parseInt(LimitNo1.getText().toString().trim()));
            noTextView.setLimitNo2(Integer.parseInt(LimitNo2.getText().toString().trim()));
            noTextView.setRepectCount(Integer.parseInt(RepeatCountEt.getText().toString().trim()));
            noTextView.setLengthAdd(Integer.parseInt(StringLengthEt.getText().toString().trim()));

            noTextView.setProPositionZero(PrePositionZero.isChecked());
            noTextView.setOnClickListener(this);
            noTextView.setOnTouchListener(this);
            nowNoId++;
            noTextView.setmTextSize(Integer.parseInt((String) FontSizeSpinnerNo.getItems().get(FontSizeSpinnerNo.getSelectedIndex())));

            JSONObject object = new JSONObject();
            PrintNo printNo = new PrintNo(NoSpinnerNo.getSelectedIndex() + "", LimitNo1.getText().toString().trim(), LimitNo2.getText().toString().trim());
            object.put("PrintNo", printNo);
            if (NoSpinnerNo.getSelectedIndex() == 0) {
                PreferenceUtils.getInstance().setNo1(object.toJSONString());
            } else if (NoSpinnerNo.getSelectedIndex() == 1) {
                PreferenceUtils.getInstance().setNo2(object.toJSONString());
            } else if (NoSpinnerNo.getSelectedIndex() == 2) {
                PreferenceUtils.getInstance().setNo3(object.toJSONString());
            } else if (NoSpinnerNo.getSelectedIndex() == 3) {
                PreferenceUtils.getInstance().setNo4(object.toJSONString());
            }

            conss.addView(noTextView);
            if (clickView == null && AddViews.size() == 0) {
                set.connect(noTextView.getId(), ConstraintSet.TOP, brBottomHead.getId(), ConstraintSet.BOTTOM, 0);
                set.connect(noTextView.getId(), ConstraintSet.LEFT, brRightHead.getId(), ConstraintSet.RIGHT, 0);
            } else if (clickView == null && AddViews.size() != 0) {
                set.connect(noTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, AddViews.get(AddViews.size() - 1).getTop());
                set.connect(noTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, AddViews.get(AddViews.size() - 1).getLeft() + AddViews.get(AddViews.size() - 1).getWidth() + 5);
            } else {
                set.connect(noTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, clickView.getTop());
                set.connect(noTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, clickView.getLeft());

                conss.removeView(clickView);
                AddViews.remove(clickView);
            }

            set.constrainWidth(noTextView.getId(), ConstraintSet.WRAP_CONTENT);
            set.constrainHeight(noTextView.getId(), ConstraintSet.WRAP_CONTENT);

            AddViews.add(noTextView);
            set.applyTo(conss);

        } else {
            if (Integer.parseInt(LimitNo2.getText().toString().trim()) < Integer.parseInt(LimitNo1.getText().toString().trim()) + Integer.parseInt(StringLengthEt.getText().toString().trim())) {
                ToastUtils.ToastShow(getString(R.string.LlimitThan));
                return;
            }
            JSONObject object = new JSONObject();
            PrintNo printNo = new PrintNo(NoSpinnerNo.getSelectedIndex() + "", LimitNo1.getText().toString().trim(), LimitNo2.getText().toString().trim());
            object.put("PrintNo", printNo);
            if (NoSpinnerNo.getSelectedIndex() == 0) {
                PreferenceUtils.getInstance().setNo1(object.toJSONString());
            } else if (NoSpinnerNo.getSelectedIndex() == 1) {
                PreferenceUtils.getInstance().setNo2(object.toJSONString());
            } else if (NoSpinnerNo.getSelectedIndex() == 2) {
                PreferenceUtils.getInstance().setNo3(object.toJSONString());
            } else if (NoSpinnerNo.getSelectedIndex() == 3) {
                PreferenceUtils.getInstance().setNo4(object.toJSONString());
            }
            ((NoTextView) clickView).setLimitNo1(Integer.parseInt(LimitNo1.getText().toString().trim()));
            ((NoTextView) clickView).setLimitNo2(Integer.parseInt(LimitNo2.getText().toString().trim()));
            ((NoTextView) clickView).setRepectCount(Integer.parseInt(RepeatCountEt.getText().toString().trim()));
            ((NoTextView) clickView).setLengthAdd(Integer.parseInt(StringLengthEt.getText().toString().trim()));
        }
        //关闭当前的弹窗
        BigConss.removeView(view);
        clearClick();
        //显示菜单
        nowShowView = null;
        hideMenu(false, 1);
    }

    private boolean AccordWithNo() {
        if (RepeatCountEt.getText().toString().trim().equals("") || StringLengthEt.getText().toString().trim().equals("") || LimitNo1.getText().toString().trim().equals("") || LimitNo2.getText().toString().trim().equals("")) {
            return false;
        }
        return true;
    }

    //显示插入文本分区界面
    private void btnSpildStringShow() {
        final View view = LayoutInflater.from(context).inflate(R.layout.insert_text_source, null);
        if (nowShowView != null) {
            BigConss.removeView(nowShowView);
        }
        nowShowView = view;
        btnCancelTextSource = view.findViewById(R.id.btnCancelTextSource);
        btnSureTextSource = view.findViewById(R.id.btnSureTextSource);
        btnDelTextSource = view.findViewById(R.id.btnDelTextSource);
        textTextSource = view.findViewById(R.id.editText);
        TextSourceForUSB = view.findViewById(R.id.SourceForUSB);
        FontTileTextSource = view.findViewById(R.id.FontTileText);
        FontBoldTextSource = view.findViewById(R.id.FontBoldText);
        FontUnderLineTextSource = view.findViewById(R.id.FontUnderLineText);

        FontSizeSpinnerTextSource = view.findViewById(R.id.FontSizeSpinnerText);
        FontAngleSpinnerTextSource = view.findViewById(R.id.FontAngleSpinnerText);
        FontSpaceSpinnerTextSource = view.findViewById(R.id.FontSpaceSpinnerText);
        FontTypefaceSpinnerTextSource = view.findViewById(R.id.FontTypefaceSpinnerText);
        TextSourceSpinner = view.findViewById(R.id.SourceSpinner);
        TextSourceSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                ChoseTextSource(position);
            }
        });
        TextSourceForUSB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoseForUSB();
            }
        });
        FontSizeSpinnerTextSource.setItems("24", "32", "40", "48", "64", "80", "96", "112", "144");
        FontSizeSpinnerTextSource.setSelectedIndex(2);
        FontAngleSpinnerTextSource.setItems("0", "90", "180", "270");
        FontSpaceSpinnerTextSource.setItems("1", "2", "3", "4");
        FontTypefaceSpinnerTextSource.setItems(fontTypefaceString);
        TextSourceSpinner.setItems(getString(R.string.SelectSource), "port", "USB", "Ethernet");

        btnCancelTextSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMenu(false, 2);
                clearClick();
                nowShowView = null;
                BigConss.removeView(view);
            }
        });

        btnSureTextSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SureSpildString(view);
            }
        });
        btnDelTextSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigConss.removeView(view);
                hideMenu(false, 2);
                conss.removeView(clickView);
                AddViews.remove(clickView);
                nowShowView = null;
                clearClick();
            }
        });

        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainWidth(view.getId(), BaseUtils.getDisplayWidth(context) * 7 / 9);
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);

    }

    private void ChoseTextSource(int position) {
        if (position != 2) {
            TextSourceForUSB.setVisibility(View.INVISIBLE);
        } else {
            TextSourceForUSB.setVisibility(View.VISIBLE);
        }
        switch (position) {
            case 0:
                break;
            case 1:
                ChoseForPort();
                break;
            case 2:
                ChoseForUSB();
                break;
            case 3:
                ChoseForEthernet();
                break;
        }
    }

    private void ChoseForEthernet() {

    }

    private void ChoseForPort() {

    }

    private void ChoseForUSB() {
        File file = new File(BaseUtils.ImportPath);
        if (!file.exists()) {
            ToastUtils.ToastShow(getString(R.string.UdiskDetected));
            TextSourceSpinner.setSelectedIndex(0);
            return;
        }
        new LFilePicker()
                .withSupportFragment(InfoEditFragment.this)
                .withBackgroundColor("#868686")
                .withMutilyMode(false)
                .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                .withFileFilter(new String[]{".txt"})
                .withStartPath("/mnt/usb_storage/USB_DISK1/udisk0")//指定初始显示路径
                .withIsGreater(false)//过滤文件大小 小于指定大小的文件
                .withFileSize(500 * 1024)//指定文件大小为500K
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESTCODE_FROM_ACTIVITY) {
            List<String> list = data.getStringArrayListExtra("paths");
            final File file = new File(list.get(0));
            fileItem = new FileItem(file.getName(), df1.format(file.lastModified()), file.getPath(), df11.format(file.length() / 1000.0) + "k");
            FileUtils.getInstance(context).copyAssetsToSD(fileItem, "text").setFileOperateCallback(new FileUtils.FileOperateCallback() {
                @Override
                public void onSuccess() {
                    ToastUtils.ToastShow(getString(R.string.SuccessfulFileSelection));
                    TextSourceForUSB.setText(getString(R.string.chosen) + ":" + file.getName() + "->" + getString(R.string.ClickReselect));
                }

                @Override
                public void onFailed(String error) {
                    GoockrLog.Loge(getString(R.string.FileSelectionFailed));

                }
            });
        } else if (resultCode == RESULT_OK && requestCode == REQUESTCODE_FROM_ACTIVITY1) {
            List<String> list = data.getStringArrayListExtra("paths");
            final File file = new File(list.get(0));
            fileItem1 = new FileItem(file.getName(), df1.format(file.lastModified()), file.getPath(), df11.format(file.length() / 1000.0) + "k");
            FileUtils.getInstance(context).copyAssetsToSD(fileItem1, "text").setFileOperateCallback(new FileUtils.FileOperateCallback() {
                @Override
                public void onSuccess() {
                    ToastUtils.ToastShow(getString(R.string.SuccessfulFileSelection));
                }

                @Override
                public void onFailed(String error) {
                    GoockrLog.Loge(getString(R.string.FileSelectionFailed));
                }
            });
        }
    }

    //插入Code 到界面
    private void SureCode(View view, Map map) {
        Bitmap bitmap = BarCode.getCodeForType(CodeStyle.getText().toString(), CodeTypeSpinner.getSelectedIndex(), Integer.parseInt(getResources().getStringArray(R.array.CodeSize)[CodeSizeSpinner.getSelectedIndex()]) * bili,
                CodeDirectionSpinner.getSelectedIndex(), CorrectionRatioSpinner.getSelectedIndex(), ReverseSpinner.getSelectedIndex() == 0 ? false : true, context);

        CodeImageView imageView = new CodeImageView(context);
        if (bitmap == null) {
            ToastUtils.ToastShow(getString(R.string.ContentError));
            return;
        }
        imageView.setImageBitmap(bitmap);
        imageView.setId(nowCodeId++);
        imageView.setOnClickListener(this);
        imageView.setOnTouchListener(this);
        imageView.setMap(map);
        imageView.setCodeContent(CodeStyle.getText().toString());
        imageView.setCodeStyle(CodeTypeSpinner.getSelectedIndex() + "");
        imageView.setCodeSize(getResources().getStringArray(R.array.CodeSize)[CodeSizeSpinner.getSelectedIndex()]);
        imageView.setCodeDirection(CodeDirectionSpinner.getSelectedIndex() + "");
        imageView.setCodeCorrection(CorrectionRatioSpinner.getSelectedIndex() + "");
        imageView.setCodeReverse(ReverseSpinner.getSelectedIndex() + "");
        imageView.setPadding(5, 5, 5, 5);
        conss.addView(imageView);



        if (clickView == null && AddViews.size() == 0) {
            set.connect(imageView.getId(), ConstraintSet.TOP, brBottomHead.getId(), ConstraintSet.BOTTOM, 0);
            set.connect(imageView.getId(), ConstraintSet.LEFT, brRightHead.getId(), ConstraintSet.RIGHT, 0);
        } else if (clickView == null && AddViews.size() != 0) {
            set.connect(imageView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, AddViews.get(AddViews.size() - 1).getTop());
            set.connect(imageView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, AddViews.get(AddViews.size() - 1).getLeft() + AddViews.get(AddViews.size() - 1).getWidth() + 5);
        } else {
            set.connect(imageView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, clickView.getTop());
            set.connect(imageView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, clickView.getLeft());
            if (map.size() == 0) {
                imageView.setMap(((CodeImageView) clickView).getMap());
            }
            conss.removeView(clickView);
            AddViews.remove(clickView);
        }

        set.constrainWidth(imageView.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainHeight(imageView.getId(), ConstraintSet.WRAP_CONTENT);

        AddViews.add(imageView);
        set.applyTo(conss);
        clearClick();
        hideMenu(false, 2);
        nowShowView = null;
        BigConss.removeView(view);
        codeMap = null;
    }

    //显示插入Code的界面
    private void btnInsertCodeShow(Map<String, Object> map) {
        set1.clone(BigConss);

        final View view = LayoutInflater.from(context).inflate(R.layout.insert_code, null);



        if (map == null) {
            map = new HashMap<>();
        }
        if (nowShowView != null) {
            BigConss.removeView(nowShowView);
        }
        nowShowView = view;

        btnCancelCode = view.findViewById(R.id.btnCancelCode);
        btnSureCode = view.findViewById(R.id.btnSureCode);

        btnDelCode = view.findViewById(R.id.btnDelCode);
        CodeTypeSpinner = view.findViewById(R.id.CodeTypeSpinner);
        CodeSizeSpinner = view.findViewById(R.id.CodeSizeSpinner);
        CodeDirectionSpinner = view.findViewById(R.id.CodeDirectionSpinner);
        CorrectionRatioSpinner = view.findViewById(R.id.CorrectionRatioSpinner);
        ReverseSpinner = view.findViewById(R.id.ReverseSpinner);
        CodeContent1Spinner = view.findViewById(R.id.CodeContent1Spinner);
        CodeContent2Spinner = view.findViewById(R.id.CodeContent2Spinner);
        CodeContent3Spinner = view.findViewById(R.id.CodeContent3Spinner);
        CodeContent4Spinner = view.findViewById(R.id.CodeContent4Spinner);
        CodeContent5Spinner = view.findViewById(R.id.CodeContent5Spinner);
        CodeStyle = view.findViewById(R.id.CodeStyle);
        DelCode = view.findViewById(R.id.DelCode);

        CodeTypeSpinner.setItems("QR", "Datamatrix", "EAN13", "CODE39", "CODE128");
        CodeSizeSpinner.setItems("7", "9", "12", "16", "24", "32");
        CodeDirectionSpinner.setItems("0", "90", "180", "270");
        CorrectionRatioSpinner.setItems("L");
        ReverseSpinner.setItems(getString(R.string.No), getString(R.string.Yes));

        CodeContent1Spinner.setItems(getString(R.string.PleaseChoose), getString(R.string.text), getString(R.string.Clock), getString(R.string.SerialNumber), getString(R.string.serialport), getString(R.string.Changements));
        CodeContent2Spinner.setItems(getString(R.string.PleaseChoose), getString(R.string.text), getString(R.string.Clock), getString(R.string.SerialNumber), getString(R.string.serialport), getString(R.string.Changements));
        CodeContent3Spinner.setItems(getString(R.string.PleaseChoose), getString(R.string.text), getString(R.string.Clock), getString(R.string.SerialNumber), getString(R.string.serialport), getString(R.string.Changements));
        CodeContent4Spinner.setItems(getString(R.string.PleaseChoose), getString(R.string.text), getString(R.string.Clock), getString(R.string.SerialNumber), getString(R.string.serialport), getString(R.string.Changements));
        CodeContent5Spinner.setItems(getString(R.string.PleaseChoose), getString(R.string.text), getString(R.string.Clock), getString(R.string.SerialNumber), getString(R.string.serialport), getString(R.string.Changements));
        CodeTypeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                ChangeCodeType(position);
            }
        });
        final Map<String, Object> finalMap = map;
        CodeContent1Spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position != 0) {
                    CodeInsert(1 + "", view, position, finalMap, CodeTypeSpinner.getSelectedIndex());
                }
            }
        });
        CodeContent2Spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position != 0) {
                    CodeInsert(2 + "", view, position, finalMap, CodeTypeSpinner.getSelectedIndex());
                }
            }
        });
        CodeContent3Spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position != 0) {
                    CodeInsert(3 + "", view, position, finalMap, CodeTypeSpinner.getSelectedIndex());
                }
            }
        });
        CodeContent4Spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position != 0) {
                    CodeInsert(4 + "", view, position, finalMap, CodeTypeSpinner.getSelectedIndex());
                }
            }
        });
        CodeContent5Spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position != 0) {
                    CodeInsert(5 + "", view, position, finalMap, CodeTypeSpinner.getSelectedIndex());
                }
            }
        });

        DelCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCodeStyle(0);
                codeMap = null;
                DelCode.setVisibility(View.INVISIBLE);
            }
        });

        btnCancelCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMenu(false, 2);
                nowShowView = null;
                clearClick();
                codeMap = null;
                BigConss.removeView(view);
            }
        });
        btnSureCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CodeStyle.getText().toString().equals("")) {
                    ToastUtils.ToastShow(getString(R.string.BarcodeEmpty));
                    return;
                }
                SureCode(view, finalMap);
            }
        });

        btnDelCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除文本
                BigConss.removeView(view);
                conss.removeView(clickView);
                hideMenu(false, 2);
                AddViews.remove(clickView);
                nowShowView = null;
                codeMap.clear();
                codeMap = null;
                clearClick();
            }
        });

        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainPercentWidth(view.getId(), 100);
        set1.constrainWidth(view.getId(), BigConss.getWidth());
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);

        clearCodeStyle(0);
    }

    private void SureSpildString(View view) {
        if (clickView == null) {
            if (textTextSource.getText().toString().trim().equals("")) {
                ToastUtils.ToastShow(getString(R.string.DataEmpty));
                return;
            }
            if (TextSourceSpinner.getSelectedIndex() == 0) {
                ToastUtils.ToastShow(getString(R.string.SelectSource));
                return;
            }
            if (fileItem == null && TextSourceSpinner.getSelectedIndex() == 2) {
                ToastUtils.ToastShow(getString(R.string.SelectSource));
                return;
            }
            boolean xx = FontTileTextSource.isChecked();
            boolean yy = FontBoldTextSource.isChecked();
            boolean zz = FontUnderLineTextSource.isChecked();

            TextSourceTextView textSourceTextView = new TextSourceTextView(context);
            textSourceTextView.setId(nowTextSourceId);
            nowTextSourceId++;

            if (FontTypefaceSpinnerTextSource.getSelectedIndex() != 0) {
                // 获取typeface
                Typeface typeFace = Typeface.createFromFile(fontTypeface.get(FontTypefaceSpinnerTextSource.getSelectedIndex()).getPath());
                // 应用字体
                textSourceTextView.setTypefacePath(typeFace, fontTypeface.get(FontTypefaceSpinnerTextSource.getSelectedIndex()).getPath());
            }
            textSourceTextView.setTextLength(Integer.parseInt(textTextSource.getText().toString().trim()));
            textSourceTextView.setSoutceType(TextSourceSpinner.getSelectedIndex());
            if (fileItem != null) {
                textSourceTextView.setUsbPath(Environment.getExternalStorageDirectory() + "/industrial/text/" + fileItem.getName());
            }
            textSourceTextView.setmTextSize(Integer.parseInt((String) FontSizeSpinnerTextSource.getItems().get(FontSizeSpinnerTextSource.getSelectedIndex())));
            textSourceTextView.setFontStyle(yy, xx, zz);
            textSourceTextView.setDirection(FontAngleSpinnerTextSource.getSelectedIndex());
            textSourceTextView.setSpaceletter(FontSpaceSpinnerTextSource.getSelectedIndex());

            textSourceTextView.setOnClickListener(this);
            textSourceTextView.setOnTouchListener(this);

            conss.addView(textSourceTextView);
            if (clickView == null && AddViews.size() == 0) {
                set.connect(textSourceTextView.getId(), ConstraintSet.TOP, brBottomHead.getId(), ConstraintSet.BOTTOM, 0);
                set.connect(textSourceTextView.getId(), ConstraintSet.LEFT, brRightHead.getId(), ConstraintSet.RIGHT, 0);
            } else if (clickView == null && AddViews.size() != 0) {
                set.connect(textSourceTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, AddViews.get(AddViews.size() - 1).getTop());
                set.connect(textSourceTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, AddViews.get(AddViews.size() - 1).getLeft() + AddViews.get(AddViews.size() - 1).getWidth() + 5);
            } else {
                set.connect(textSourceTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, clickView.getTop());
                set.connect(textSourceTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, clickView.getLeft());

                conss.removeView(clickView);
                AddViews.remove(clickView);
            }

            set.constrainWidth(textSourceTextView.getId(), ConstraintSet.WRAP_CONTENT);
            set.constrainHeight(textSourceTextView.getId(), ConstraintSet.WRAP_CONTENT);

            AddViews.add(textSourceTextView);
            set.applyTo(conss);

        }
        //关闭当前的弹窗
        BigConss.removeView(view);
        clearClick();
        //显示菜单
        nowShowView = null;
        hideMenu(false, 2);
    }

    private void btnInsertImgShow() {
        final View view = LayoutInflater.from(context).inflate(R.layout.insert_img, null);
        if (nowShowView != null) {
            BigConss.removeView(nowShowView);
        }
        nowShowView = view;
        btnCancelImg = view.findViewById(R.id.btnCancelImg);
        btnSureImg = view.findViewById(R.id.btnSureImg);
        btnDelImg = view.findViewById(R.id.btnDelImg);


        btnCancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearClick();
                hideMenu(false, 2);
                nowShowView = null;
                BigConss.removeView(view);
            }
        });
        btnSureImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMenu(false, 2);
                SureImg(view);
            }
        });
        btnDelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigConss.removeView(view);
                hideMenu(false, 2);
                conss.removeView(clickView);
                AddViews.remove(clickView);
                nowShowView = null;
                clearClick();
            }
        });

        ImgDireciton = view.findViewById(R.id.ImgDireciton);
        ImgName = view.findViewById(R.id.ImgName);
        ImgScale = view.findViewById(R.id.ImgScale);
        ImgScale.setItems("1x", "2x", "3x", "4x");
        ImgDireciton.setItems("0", "90", "180", "270");
        ImgName.setItems(imgInfoStrings);

        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainWidth(view.getId(), BaseUtils.getDisplayWidth(context) * 7 / 9);
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);
    }

    private void SureImg(View view) {

        ImgImageViw imageView = new ImgImageViw(context);
        if (imgInfos.size() == 0) {
            ToastUtils.ToastShow(getString(R.string.ImportImg));
            return;
        }
        String path = imgInfos.get(ImgName.getSelectedIndex()).getPath();
        Bitmap bitmap = BaseUtils.getBitmapForDisk(path);

        if (ImgScale.getSelectedIndex() == 1) {
            bitmap = BaseUtils.zoomImg(bitmap, 2);
        } else if (ImgScale.getSelectedIndex() == 2) {
            bitmap = BaseUtils.zoomImg(bitmap, 3);
        } else if (ImgScale.getSelectedIndex() == 3) {
            bitmap = BaseUtils.zoomImg(bitmap, 4);
        }
        bitmap = BaseUtils.directionImg(bitmap, ImgDireciton.getSelectedIndex());
        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(this);
        imageView.setOnTouchListener(this);
        imageView.setId(nowImgId++);
        imageView.setPadding(5, 5, 5, 5);
        conss.addView(imageView);
        if (clickView == null && AddViews.size() == 0) {
            set.connect(imageView.getId(), ConstraintSet.TOP, brBottomHead.getId(), ConstraintSet.BOTTOM, 0);
            set.connect(imageView.getId(), ConstraintSet.LEFT, brRightHead.getId(), ConstraintSet.RIGHT, 0);
        } else if (clickView == null && AddViews.size() != 0) {
            set.connect(imageView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, AddViews.get(AddViews.size() - 1).getTop());
            set.connect(imageView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, AddViews.get(AddViews.size() - 1).getLeft() + AddViews.get(AddViews.size() - 1).getWidth() + 5);
        } else {
            set.connect(imageView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, clickView.getTop());
            set.connect(imageView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, clickView.getLeft());

            conss.removeView(clickView);
            AddViews.remove(clickView);
        }

        imageView.setImgDirection(ImgDireciton.getSelectedIndex() + "");
        imageView.setImgNo(ImgName.getSelectedIndex() + "", path);
        imageView.setImgScale(ImgScale.getSelectedIndex() + "");

        set.constrainWidth(imageView.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainHeight(imageView.getId(), ConstraintSet.WRAP_CONTENT);

        AddViews.add(imageView);
        set.applyTo(conss);

        BigConss.removeView(view);
        nowShowView = null;
        hideMenu(false, 2);
        clearClick();

    }

    private void UpdateImg(View v) {
        clickView = v;
        oldClickView = v;
        btnInsertImgShow();
        hideMenu(true, 2);
        clickView.setBackground(getResources().getDrawable(R.drawable.tv_border));
        btnDelImg.setVisibility(View.VISIBLE);

        final ImgImageViw textView = (ImgImageViw) clickView;


        if (Integer.parseInt(textView.getImgNo()) < imgInfoStrings.size()) {
            ImgName.setSelectedIndex(Integer.parseInt(textView.getImgNo()));
        }
        ImgDireciton.setSelectedIndex(Integer.parseInt(textView.getImgDirection()));
        ImgScale.setSelectedIndex(Integer.parseInt(textView.getImgScale()));
        ImgName.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Bitmap bitmap = BaseUtils.getBitmapForDisk(imgInfos.get(position).getPath());
                textView.setImageBitmap(bitmap);
                textView.setImgNo(position + "", imgInfos.get(position).getPath());
                ImgDireciton.setSelectedIndex(0);
                textView.setImgDirection("" + 0);
                textView.setImgScale(0 + "");
                ImgScale.setSelectedIndex(0);
            }
        });
        ImgDireciton.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Bitmap bitmap = BaseUtils.getBitmapForDisk(textView.getPath());
                bitmap = BaseUtils.zoomImg(bitmap, Integer.parseInt(textView.getImgScale()) + 1);
                bitmap = BaseUtils.directionImg(bitmap, position);
                textView.setImageBitmap(bitmap);
                textView.setImgDirection(position + "");
            }
        });
        ImgScale.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Bitmap bitmap = BaseUtils.getBitmapForDisk(textView.getPath());

                bitmap = BaseUtils.zoomImg(bitmap, position + 1);
                bitmap = BaseUtils.directionImg(bitmap, Integer.parseInt(textView.getImgDirection()));

                textView.setImageBitmap(bitmap);
                textView.setImgScale(position + "");
            }
        });

    }

    private void btnInsertClassShow() {
        final View view = LayoutInflater.from(context).inflate(R.layout.insert_class, null);
        if (nowShowView != null) {
            BigConss.removeView(nowShowView);
        }
        nowShowView = view;
        TimeStart1 = view.findViewById(R.id.TimeStart1);
        TimeStart2 = view.findViewById(R.id.TimeStart2);
        TimeStart3 = view.findViewById(R.id.TimeStart3);
        TimeStart4 = view.findViewById(R.id.TimeStart4);
        TimeStart5 = view.findViewById(R.id.TimeStart5);
        TimeEnd1 = view.findViewById(R.id.TimeEnd1);
        TimeEnd2 = view.findViewById(R.id.TimeEnd2);
        TimeEnd3 = view.findViewById(R.id.TimeEnd3);
        TimeEnd4 = view.findViewById(R.id.TimeEnd4);
        TimeEnd5 = view.findViewById(R.id.TimeEnd5);
        ClassNo1 = view.findViewById(R.id.ClassNo1);
        ClassNo2 = view.findViewById(R.id.ClassNo2);
        ClassNo3 = view.findViewById(R.id.ClassNo3);
        ClassNo4 = view.findViewById(R.id.ClassNo4);
        ClassNo5 = view.findViewById(R.id.ClassNo5);

        TimeStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(1, true);
            }
        });
        TimeStart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(2, true);
            }
        });
        TimeStart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(3, true);
            }
        });
        TimeStart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(4, true);
            }
        });
        TimeStart5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(5, true);
            }
        });
        TimeEnd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(1, false);
            }
        });
        TimeEnd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(2, false);
            }
        });
        TimeEnd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(3, false);
            }
        });
        TimeEnd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(4, false);
            }
        });
        TimeEnd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(5, false);
            }
        });

        FontSizeSpinnerClass = view.findViewById(R.id.FontSizeSpinnerClass);
        FontAngleSpinnerClass = view.findViewById(R.id.FontAngleSpinnerClass);
        FontTypefaceSpinnerClass = view.findViewById(R.id.FontTypefaceSpinnerClass);

        FontSizeSpinnerClass.setItems("24", "32", "40", "48", "64", "80", "96", "112", "144");
        FontSizeSpinnerClass.setSelectedIndex(2);
        FontAngleSpinnerClass.setItems("0", "90", "180", "270");
        FontTypefaceSpinnerClass.setItems(fontTypefaceString);

        btnCancelClass = view.findViewById(R.id.btnCancelClass);
        btnSureClass = view.findViewById(R.id.btnSureClass);
        btnDelClass = view.findViewById(R.id.btnDelClass);

        btnCancelClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMenu(false, 2);
                nowShowView = null;
                clearClick();
                BigConss.removeView(view);
            }
        });
        btnSureClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SureClass(view);

            }
        });

        btnDelClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigConss.removeView(view);
                hideMenu(false, 2);
                conss.removeView(clickView);
                nowShowView = null;
                AddViews.remove(clickView);
                clearClick();
            }
        });

        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainPercentWidth(view.getId(), 100);
        set1.constrainWidth(view.getId(), BaseUtils.getDisplayWidth(context) * 7 / 9);
//        set1.constrainWidth(view.getId(), 300);
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);
    }

    private void SureClass(View view) {
        List<ClassItem> classItems = new ArrayList<>();
        getClassItem(classItems);
        if (classItems.size() == 0) {
            ToastUtils.ToastShow(getString(R.string.SelectTmeAndShift));
            return;
        }
        ClassTextView classTextView = new ClassTextView(context);
        if (FontTypefaceSpinnerClass.getSelectedIndex() != 0) {
            // 获取typeface
            Typeface typeFace = Typeface.createFromFile(fontTypeface.get(FontTypefaceSpinnerClass.getSelectedIndex()).getPath());
            // 应用字体
            classTextView.setTypefaces(typeFace, fontTypeface.get(FontTypefaceSpinnerClass.getSelectedIndex()).getPath());
        }

        if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerClass.getSelectedIndex()]) == 90) {
            classTextView.setDirection(1);
        } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerClass.getSelectedIndex()]) == 270) {
            classTextView.setDirection(0);
        } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerClass.getSelectedIndex()]) == 0) {
            classTextView.setDirection(2);
        } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerClass.getSelectedIndex()]) == 180) {
            classTextView.setDirection(3);
        }

        classTextView.setOnClickListener(this);
        classTextView.setOnTouchListener(this);
        classTextView.setTextSize(Integer.parseInt((String) FontSizeSpinnerClass.getItems().get(FontSizeSpinnerClass.getSelectedIndex())));
        classTextView.setClassItems(classItems);
        classTextView.setId(nowClassId++);

        conss.addView(classTextView);
        if (clickView == null && AddViews.size() == 0) {
            set.connect(classTextView.getId(), ConstraintSet.TOP, brBottomHead.getId(), ConstraintSet.BOTTOM, 0);
            set.connect(classTextView.getId(), ConstraintSet.LEFT, brRightHead.getId(), ConstraintSet.RIGHT, 0);
        } else if (clickView == null && AddViews.size() != 0) {
            set.connect(classTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, AddViews.get(AddViews.size() - 1).getTop());
            set.connect(classTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, AddViews.get(AddViews.size() - 1).getLeft() + AddViews.get(AddViews.size() - 1).getWidth() + 5);
        } else {
            set.connect(classTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, clickView.getTop());
            set.connect(classTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, clickView.getLeft());

            conss.removeView(clickView);
            AddViews.remove(clickView);
        }
        set.constrainWidth(classTextView.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainHeight(classTextView.getId(), ConstraintSet.WRAP_CONTENT);

        AddViews.add(classTextView);
        set.applyTo(conss);

        clearClick();
        nowShowView = null;
        BigConss.removeView(view);
        hideMenu(false, 2);
    }

    private void getClassItem(List<ClassItem> classItems) {
        Log.e("tag", "test " + ClassNo1.getText().toString().equals("") + "  " + TimeStart1.getText().equals(getString(R.string.ClickSettings)) + "  " + TimeEnd1.getText().equals(getString(R.string.ClickSettings)));
        if (!TimeStart1.getText().equals(getString(R.string.ClickSettings)) && !TimeEnd1.getText().equals(getString(R.string.ClickSettings)) && !ClassNo1.getText().toString().equals("")) {
            classItems.add(new ClassItem(TimeStart1.getText().toString(), TimeEnd1.getText().toString(), ClassNo1.getText().toString()));
        }
        if (!TimeStart2.getText().equals(getString(R.string.ClickSettings)) && !TimeEnd2.getText().equals(getString(R.string.ClickSettings)) && !ClassNo2.getText().equals("")) {
            classItems.add(new ClassItem(TimeStart2.getText().toString(), TimeEnd2.getText().toString(), ClassNo2.getText().toString()));
        }
        if (!TimeStart3.getText().equals(getString(R.string.ClickSettings)) && !TimeEnd3.getText().equals(getString(R.string.ClickSettings)) && !ClassNo3.getText().equals("")) {
            classItems.add(new ClassItem(TimeStart3.getText().toString(), TimeEnd3.getText().toString(), ClassNo3.getText().toString()));
        }
        if (!TimeStart4.getText().equals(getString(R.string.ClickSettings)) && !TimeEnd4.getText().equals(getString(R.string.ClickSettings)) && !ClassNo4.getText().equals("")) {
            classItems.add(new ClassItem(TimeStart4.getText().toString(), TimeEnd4.getText().toString(), ClassNo4.getText().toString()));
        }
        if (!TimeStart5.getText().equals(getString(R.string.ClickSettings)) && !TimeEnd5.getText().equals(getString(R.string.ClickSettings)) && !ClassNo5.getText().equals("")) {
            classItems.add(new ClassItem(TimeStart5.getText().toString(), TimeEnd5.getText().toString(), ClassNo5.getText().toString()));
        }
    }

    private void UpdateClass(View v) {
        clickView = v;
        oldClickView = v;
        btnInsertClassShow();
        hideMenu(true, 1);
        clickView.setBackground(getResources().getDrawable(R.drawable.tv_border));
        btnDelClass.setVisibility(View.VISIBLE);

        final ClassTextView textView = (ClassTextView) clickView;

        float x = textView.getSize();
        int z = textView.getDirection();

        String[] FontSizes = getResources().getStringArray(R.array.FontSizes);
        int fontsize = 0;
        for (String fz : FontSizes) {
            if (fz.equals((int) x + "")) {
                break;
            } else {
                fontsize++;
            }
        }
        FontSizeSpinnerClass.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                textView.setTextSize(Integer.parseInt((String) FontSizeSpinnerClass.getItems().get(FontSizeSpinnerClass.getSelectedIndex())));
            }
        });
        FontAngleSpinnerClass.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerClass.getSelectedIndex()]) == 90) {
                    textView.setDirection(1);
                } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerClass.getSelectedIndex()]) == 270) {
                    textView.setDirection(0);
                } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerClass.getSelectedIndex()]) == 0) {
                    textView.setDirection(2);
                } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerClass.getSelectedIndex()]) == 180) {
                    textView.setDirection(3);
                }
            }
        });
        FontTypefaceSpinnerClass.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                File file = new File(fontTypeface.get(position).getPath());
                if (file.exists()) {
                    Typeface typeface = Typeface.createFromFile(fontTypeface.get(position).getPath());
                    textView.setTypefaces(typeface, fontTypeface.get(position).getPath());
                } else {
                    textView.setTypefaces(null, "");
                }
            }
        });
        FontTypefaceSpinnerClass.setSelectedIndex(0);
        List<ClassItem> classItems = textView.getClassItems();
        for (int i = 0; i < classItems.size(); i++) {
            ClassItem classItem = classItems.get(i);
            switch (i) {
                case 0:
                    TimeStart1.setText(classItem.getStartTime());
                    TimeEnd1.setText(classItem.getEndTime());
                    ClassNo1.setText(classItem.getClazzText());
                    break;
                case 1:
                    TimeStart2.setText(classItem.getStartTime());
                    TimeEnd2.setText(classItem.getEndTime());
                    ClassNo2.setText(classItem.getClazzText());
                    break;
                case 2:
                    TimeStart3.setText(classItem.getStartTime());
                    TimeEnd3.setText(classItem.getEndTime());
                    ClassNo3.setText(classItem.getClazzText());
                    break;
                case 3:
                    TimeStart4.setText(classItem.getStartTime());
                    TimeEnd4.setText(classItem.getEndTime());
                    ClassNo4.setText(classItem.getClazzText());
                    break;
                case 4:
                    TimeStart5.setText(classItem.getStartTime());
                    TimeEnd5.setText(classItem.getEndTime());
                    ClassNo5.setText(classItem.getClazzText());
                    break;
            }
        }
        FontSizeSpinnerClass.setSelectedIndex(fontsize);
        switch (z) {
            case 0:
                FontAngleSpinnerClass.setSelectedIndex(3);
                break;
            case 1:
                FontAngleSpinnerClass.setSelectedIndex(1);
                break;
            case 2:
                FontAngleSpinnerClass.setSelectedIndex(0);
                break;
            case 3:
                FontAngleSpinnerClass.setSelectedIndex(2);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void UpdateClock(View v) {
        clickView = v;
        btnInsertclockShow();
        hideMenu(true, 1);
        clickView.setBackground(getResources().getDrawable(R.drawable.tv_border));
        btnDelClock.setVisibility(View.VISIBLE);
        ClockTextView textView = (ClockTextView) clickView;
        ClockNoSpinnerClock.setEnabled(false);
        String y1 = textView.getDateStyle();
        String x1 = textView.getTimeStyle();
        int dateNo = dateList.indexOf(y1);
        int timeNo = timeList.indexOf(x1);
        DateStyleSpinnerClock.setSelectedIndex(dateNo == -1 ? 0 : dateNo);
        TimeStyleSpinnerClock.setSelectedIndex(timeNo == -1 ? 0 : timeNo);

        ClockContentSpinnerClock.setSelectedIndex(textView.getClockStyleNo());

        if (textView.getClockStyleNo() == 0) {
            DateStyleSpinnerClock.setEnabled(true);
            TimeStyleSpinnerClock.setEnabled(false);
        } else if (textView.getClockStyleNo() == 1) {
            DateStyleSpinnerClock.setEnabled(true);
            TimeStyleSpinnerClock.setEnabled(true);
        } else if (textView.getClockStyleNo() == 2) {
            DateStyleSpinnerClock.setEnabled(true);
            TimeStyleSpinnerClock.setEnabled(true);
        } else if (textView.getClockStyleNo() == 3) {
            DateStyleSpinnerClock.setEnabled(false);
            TimeStyleSpinnerClock.setEnabled(true);
        }
        DeviationClock.setText(textView.getDeviationDay());

        float x = textView.getSize();
        int z = textView.getDirection();

        String[] FontSizes = getResources().getStringArray(R.array.FontSizes);
        int fontsize = 0;
        for (String fz : FontSizes) {
            if (fz.equals((int) x + "")) {
                break;
            } else {
                fontsize++;
            }
        }

        ClockNoSpinnerClock.setSelectedIndex(textView.getNo());
        FontSizeSpinnerClock.setSelectedIndex(fontsize);
        switch (z) {
            case 0:
                FontAngleSpinnerClock.setSelectedIndex(3);
                break;
            case 1:
                FontAngleSpinnerClock.setSelectedIndex(1);
                break;
            case 2:
                FontAngleSpinnerClock.setSelectedIndex(0);
                break;
            case 3:
                FontAngleSpinnerClock.setSelectedIndex(2);
                break;
        }

        FontTypefaceSpinnerClock.setSelectedIndex(0);

        if (textView.isUnder()) {
            FontUnderLineClock.setChecked(true);
        }
        if (textView.isBold()) {
            FontBoldClock.setChecked(true);
        }
        if (textView.isIt()) {
            FontTileClock.setChecked(true);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void UpdateTv(View v) {
        clickView = v;
        oldClickView = v;
        btnInsertTextShow();
        hideMenu(true, 1);
        clickView.setBackground(getResources().getDrawable(R.drawable.tv_border));
        btnDelText.setVisibility(View.VISIBLE);

        VerticalTextView textView = (VerticalTextView) clickView;
        editTextText.setText(textView.getmText().trim());

        float x = textView.getSize();

        float y = textView.getLetterSpace();
        int z = textView.getDirection();

        String[] FontSizes = getResources().getStringArray(R.array.FontSizes);
        int fontsize = 0;
        for (String fz : FontSizes) {
            if (fz.equals((int) x + "")) {
                break;
            } else {
                fontsize++;
            }
        }
        FontSizeSpinnerText.setSelectedIndex(fontsize);

        switch (z) {
            case 0:
                FontAngleSpinnerText.setSelectedIndex(3);
                break;
            case 1:
                FontAngleSpinnerText.setSelectedIndex(1);
                break;
            case 2:
                FontAngleSpinnerText.setSelectedIndex(0);
                break;
            case 3:
                FontAngleSpinnerText.setSelectedIndex(2);
                break;
        }
        String[] FontSpaces = getResources().getStringArray(R.array.FontSpace);
        int fontspaces = 0;
        for (String fz : FontSpaces) {
            if (fz.equals((int) y + "")) {
                break;
            } else {
                fontspaces++;
            }
        }
        FontSpaceSpinnerText.setSelectedIndex(fontspaces);
        //todo
        FontTypefaceSpinnerText.setSelectedIndex(0);

        if (textView.isUnder()) {
            FontUnderLineText.setChecked(true);
        }
        if (textView.isBold()) {
            FontBoldText.setChecked(true);
        }
        if (textView.isIt()) {
            FontTileText.setChecked(true);
        }
    }

    //显示创建新的内容
    private void btnCreateMsgShow() {
        final View view = LayoutInflater.from(context).inflate(R.layout.insert_info, null);
        final EditText editText = view.findViewById(R.id.editText);
        final MaterialSpinner NozzleCount = view.findViewById(R.id.NozzleCount);
        //限制字符
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if(matcher.find())return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});

        NozzleCount.setItems("1", "2", "3", "4", "5", "6", "7", "8");
        Button btnCancelInfo = view.findViewById(R.id.btnCancelInfo);
        Button btnSureInfo = view.findViewById(R.id.btnSureInfo);
        btnCancelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideMenu(false, 1);
                BigConss.removeView(view);
            }
        });
        btnSureInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xxx = editText.getText().toString().trim();
                if (xxx.equals("")) {
                    ToastUtils.ToastShow(getString(R.string.InformationEmpty));
                    return;
                }
                if (BaseUtils.existForName(xxx)) {
                    ToastUtils.ToastShow(getString(R.string.ReEnterMsgName));
                    return;
                }


                clearNoOrAddNo(4, true);
                clearNoOrAddNoToNo(4, true);
                BigConss.removeView(view);
                PreferenceUtils.getInstance().setDefaultMsg(editText.getText().toString().trim());
                PreferenceUtils.getInstance().setNozzleCount(NozzleCount.getSelectedIndex() + 1);
                hideMenu(false, 1);
                clearView();
                InitLine(NozzleCount.getSelectedIndex() + 1);
            }
        });
        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainPercentWidth(view.getId(), 100);
        set1.constrainWidth(view.getId(), BaseUtils.getDisplayWidth(context) * 7 / 9);
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);
    }

    //显示创建时钟
    private void btnInsertclockShow() {
        final View view = LayoutInflater.from(context).inflate(R.layout.insert_clock, null);
        if (nowShowView != null) {
            BigConss.removeView(nowShowView);
        }
        nowShowView = view;

        btnCancelClock = view.findViewById(R.id.btnCancelClock);
        btnSureClock = view.findViewById(R.id.btnSureClock);
        btnDelClock = view.findViewById(R.id.btnDelClock);

        btnCancelClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowShowView = null;
                clearClick();
                hideMenu(false, 1);
                BigConss.removeView(view);
            }
        });
        btnSureClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SureClock(view);
            }
        });
        btnDelClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigConss.removeView(view);
                hideMenu(false, 1);
                conss.removeView(clickView);
                AddViews.remove(clickView);
                nowShowView = null;
                clearNoOrAddNo(((ClockTextView) clickView).getNo(), true);
                clearClick();
            }
        });
        ClockNoSpinnerClock = view.findViewById(R.id.ClockNoSpinnerClock);
        ClockContentSpinnerClock = view.findViewById(R.id.ClockContentSpinnerClock);
        DateStyleSpinnerClock = view.findViewById(R.id.DateStyleSpinnerClock);
        TimeStyleSpinnerClock = view.findViewById(R.id.TimeStyleSpinnerClock);
        FontSizeSpinnerClock = view.findViewById(R.id.FontSizeSpinnerClock);
        FontAngleSpinnerClock = view.findViewById(R.id.FontAngleSpinnerClock);
        FontTypefaceSpinnerClock = view.findViewById(R.id.FontTypefaceSpinnerClock);
        FontTileClock = view.findViewById(R.id.FontTileClock);
        FontBoldClock = view.findViewById(R.id.FontBoldClock);
        FontUnderLineClock = view.findViewById(R.id.FontUnderLineClock);
        DeviationClock = view.findViewById(R.id.DeviationClock);
        DeviationClock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (clickView != null) {
                    if (s.length() <= 0) {
                        return;
                    }
                    if (clickView instanceof ClockTextView) {
                        ClockTextView verticalTextView = (ClockTextView) clickView;
                        verticalTextView.setDeviationDay(DeviationClock.getText().toString());
                        verticalTextView.upText();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        TimeStyleSpinnerClock.setItems(timeList);
        FontTypefaceSpinnerClock.setItems(fontTypefaceString);
        DateStyleSpinnerClock.setItems(dateList);
        ClockNoSpinnerClock.setItems("1", "2", "3", "4");
        ClockContentSpinnerClock.setItems(getString(R.string.date), getString(R.string.date) + "+" + getString(R.string.time), getString(R.string.time) + "+" + getString(R.string.date), getString(R.string.time));
        FontSizeSpinnerClock.setItems("24", "32", "40", "48", "64", "80", "96", "112", "144");
        FontSizeSpinnerClock.setSelectedIndex(2);
        FontAngleSpinnerClock.setItems("0", "90", "180", "270");

        TimeStyleSpinnerClock.setEnabled(false);

        ClockNoSpinnerClock.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (clickView != null) {
                    DynamicShow(11, position);
                }
            }
        });
        ClockContentSpinnerClock.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position == 0) {
                    DateStyleSpinnerClock.setEnabled(true);
                    TimeStyleSpinnerClock.setEnabled(false);
                } else if (position == 1) {
                    DateStyleSpinnerClock.setEnabled(true);
                    TimeStyleSpinnerClock.setEnabled(true);
                } else if (position == 2) {
                    DateStyleSpinnerClock.setEnabled(true);
                    TimeStyleSpinnerClock.setEnabled(true);
                } else if (position == 3) {
                    DateStyleSpinnerClock.setEnabled(false);
                    TimeStyleSpinnerClock.setEnabled(true);
                }

                if (clickView != null) {
                    DynamicShow(12, position);
                }
            }
        });
        DateStyleSpinnerClock.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (clickView != null) {
                    DynamicShow(13, position);
                }
            }
        });
        TimeStyleSpinnerClock.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (clickView != null) {
                    DynamicShow(14, position);
                }
            }
        });
        FontSizeSpinnerClock.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (clickView != null) {
                    DynamicShow(15, position);
                }
            }
        });
        FontAngleSpinnerClock.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (clickView != null) {
                    DynamicShow(16, position);
                }
            }
        });
        FontTypefaceSpinnerClock.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (clickView != null) {
                    DynamicShow(18, position);
                }
            }
        });

        FontTileClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DynamicShow(5, isChecked);
            }
        });
        FontBoldClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DynamicShow(6, isChecked);
            }
        });
        FontUnderLineClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DynamicShow(7, isChecked);
            }
        });

        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainWidth(view.getId(), BaseUtils.getDisplayWidth(context) * 7 / 9);
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);
    }

    //添加时钟操作
    private void SureClock(View view) {
        if (clickView == null) {
            if (ClockNoExist()) {
                ToastUtils.ToastShow(getString(R.string.NumberExists));
                return;
            }
            clearNoOrAddNo(ClockNoSpinnerClock.getSelectedIndex(), false);
            boolean xx = FontTileClock.isChecked();
            boolean yy = FontBoldClock.isChecked();
            boolean zz = FontUnderLineClock.isChecked();

            ClockTextView InsertTextView = new ClockTextView(context);
            InsertTextView.setId(nowClockId);
            InsertTextView.setTextColor(Color.BLACK);

            if (FontTypefaceSpinnerClock.getSelectedIndex() != 0) {
                // 获取typeface
                Typeface typeFace = Typeface.createFromFile(fontTypeface.get(FontTypefaceSpinnerClock.getSelectedIndex()).getPath());
                // 应用字体
                InsertTextView.setTypefaces(typeFace, fontTypeface.get(FontTypefaceSpinnerClock.getSelectedIndex()).getPath());
            }

            InsertTextView.setDeviationDay(DeviationClock.getText().toString());
            InsertTextView.setNo(ClockNoSpinnerClock.getSelectedIndex());
            InsertTextView.setDateStyle((String) DateStyleSpinnerClock.getItems().get(DateStyleSpinnerClock.getSelectedIndex()));
            InsertTextView.setTimeStyle((String) TimeStyleSpinnerClock.getItems().get(TimeStyleSpinnerClock.getSelectedIndex()));
            InsertTextView.setClockStyleNo(ClockContentSpinnerClock.getSelectedIndex());
            InsertTextView.upText();
            if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerClock.getSelectedIndex()]) == 90) {
                InsertTextView.setDirection(1);
            } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerClock.getSelectedIndex()]) == 270) {
                InsertTextView.setDirection(0);
            } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerClock.getSelectedIndex()]) == 0) {
                InsertTextView.setDirection(2);
            } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerClock.getSelectedIndex()]) == 180) {
                InsertTextView.setDirection(3);
            }
            InsertTextView.setOnClickListener(this);
            InsertTextView.setOnTouchListener(this);
            nowClockId++;
            InsertTextView.setTextSize(Integer.parseInt((String) FontSizeSpinnerClock.getItems().get(FontSizeSpinnerClock.getSelectedIndex())));
            InsertTextView.setFontStyle(yy, xx, zz);

            conss.addView(InsertTextView);
            if (clickView == null && AddViews.size() == 0) {
                set.connect(InsertTextView.getId(), ConstraintSet.TOP, brBottomHead.getId(), ConstraintSet.BOTTOM, 0);
                set.connect(InsertTextView.getId(), ConstraintSet.LEFT, brRightHead.getId(), ConstraintSet.RIGHT, 0);
            } else if (clickView == null && AddViews.size() != 0) {
                set.connect(InsertTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, AddViews.get(AddViews.size() - 1).getTop());
                set.connect(InsertTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, AddViews.get(AddViews.size() - 1).getLeft() + AddViews.get(AddViews.size() - 1).getWidth() + 5);
            } else {
                set.connect(InsertTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, clickView.getTop());
                set.connect(InsertTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, clickView.getLeft());

                conss.removeView(clickView);
                AddViews.remove(clickView);
            }

            set.constrainWidth(InsertTextView.getId(), ConstraintSet.WRAP_CONTENT);
            set.constrainHeight(InsertTextView.getId(), ConstraintSet.WRAP_CONTENT);

            AddViews.add(InsertTextView);
            set.applyTo(conss);

        }
        //关闭当前的弹窗
        BigConss.removeView(view);
        clearClick();
        //显示菜单
        nowShowView = null;
        hideMenu(false, 1);
    }

    //显示创建文本
    private void btnInsertTextShow() {
        final View view = LayoutInflater.from(context).inflate(R.layout.insert_text, null);
        if (nowShowView != null) {
            BigConss.removeView(nowShowView);
        }
        nowShowView = view;

        editTextText = view.findViewById(R.id.editTextText);
        FontSizeSpinnerText = view.findViewById(R.id.FontSizeSpinnerText);
        FontAngleSpinnerText = view.findViewById(R.id.FontAngleSpinnerText);
        FontSpaceSpinnerText = view.findViewById(R.id.FontSpaceSpinnerText);
        FontTypefaceSpinnerText = view.findViewById(R.id.FontTypefaceSpinnerText);
        FontTileText = view.findViewById(R.id.FontTileText);
        FontBoldText = view.findViewById(R.id.FontBoldText);
        FontUnderLineText = view.findViewById(R.id.FontUnderLineText);
        btnCancelText = view.findViewById(R.id.btnCancelText);
        btnSureText = view.findViewById(R.id.btnSureText);
        btnDelText = view.findViewById(R.id.btnDelText);

        FontSizeSpinnerText.setItems("24", "32", "40", "48", "64", "80", "96", "112", "144");
        FontSizeSpinnerText.setSelectedIndex(2);
        FontAngleSpinnerText.setItems("0", "90", "180", "270");
        FontSpaceSpinnerText.setItems("1", "2", "3", "4");
        FontTypefaceSpinnerText.setItems(fontTypefaceString);

        FontSizeSpinnerText.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (clickView != null) {
                    DynamicShow(1, position);
                }
            }
        });
        FontAngleSpinnerText.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (clickView != null) {
                    DynamicShow(2, position);
                }
            }
        });
        FontSpaceSpinnerText.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (clickView != null) {
                    DynamicShow(3, position);
                }
            }
        });
        FontTypefaceSpinnerText.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (clickView != null) {
                    DynamicShow(4, position);
                }
            }
        });

        FontTileText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (clickView != null) {
                    DynamicShow(5, isChecked);
                }
            }
        });
        FontBoldText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (clickView != null) {
                    DynamicShow(6, isChecked);
                }
            }
        });
        FontUnderLineText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (clickView != null) {
                    DynamicShow(7, isChecked);
                }
            }
        });

        editTextText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (clickView != null) {
                    if (s.length() < 0) {
                        return;
                    }
                    VerticalTextView verticalTextView = (VerticalTextView) clickView;

                    //MLog.v("s" + s.toString());
                    verticalTextView.setText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnCancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowShowView = null;
                //清除布局
                BigConss.removeView(view);
                //回到之前的状态
                backInsertText();
                clearClick();
                hideMenu(false, 1);
            }
        });

        btnSureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建文本内容
                SureText(view);
            }
        });

        btnDelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除文本
                BigConss.removeView(view);
                conss.removeView(clickView);
                AddViews.remove(clickView);
                hideMenu(false, 1);
                nowShowView = null;
                clearClick();
            }
        });

        BigConss.addView(view);
        set1.connect(view.getId(), ConstraintSet.BOTTOM, BigConss.getId(), ConstraintSet.BOTTOM, 0);
        set1.connect(view.getId(), ConstraintSet.START, BigConss.getId(), ConstraintSet.START, 0);
        set1.connect(view.getId(), ConstraintSet.END, BigConss.getId(), ConstraintSet.END, 0);

        set1.constrainWidth(view.getId(), BaseUtils.getDisplayWidth(context) * 7 / 9);
        set1.constrainHeight(view.getId(), BaseUtils.dp2px(200));
        set1.applyTo(BigConss);
    }

    //插入文本操作
    private void SureText(View view) {
        MLog.v("TextText:");
        if (clickView == null) {
            String TextText = editTextText.getText().toString().trim();
            MLog.v("TextText:" + TextText);
            if (TextText.equals("")) {
                ToastUtils.ToastShow("请输入文本");
                return;
            }
            boolean xx = FontTileText.isChecked();
            boolean yy = FontBoldText.isChecked();
            boolean zz = FontUnderLineText.isChecked();

            VerticalTextView InsertTextView = new VerticalTextView(context);

            InsertTextView.setId(nowTextViewId);
            InsertTextView.setText(TextText);
            InsertTextView.setTextColor(Color.BLACK);

            // 获取typeface
            File file = new File(fontTypeface.get(FontTypefaceSpinnerText.getSelectedIndex()).getPath());
            Typeface typeFace = null;
            if (file.exists()) {
                typeFace = Typeface.createFromFile(file);
                InsertTextView.setTypefaces(typeFace, fontTypeface.get(FontTypefaceSpinnerText.getSelectedIndex()).getPath());
            } else {
                InsertTextView.setTypefaces(typeFace, "");
            }

            InsertTextView.setLetterSpace(Float.parseFloat(getResources().getStringArray(R.array.FontSpace)[FontSpaceSpinnerText.getSelectedIndex()]));
            if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerText.getSelectedIndex()]) == 90) {
                InsertTextView.setDirection(1);
            } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerText.getSelectedIndex()]) == 270) {
                InsertTextView.setDirection(0);
            } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerText.getSelectedIndex()]) == 0) {
                InsertTextView.setDirection(2);
            } else if (Integer.parseInt(getResources().getStringArray(R.array.FontAngle)[FontAngleSpinnerText.getSelectedIndex()]) == 180) {
                InsertTextView.setDirection(3);
            }
            InsertTextView.setOnClickListener(this);
            InsertTextView.setOnTouchListener(this);
            nowTextViewId++;
            InsertTextView.setTextSize(Integer.parseInt((String) FontSizeSpinnerText.getItems().get(FontSizeSpinnerText.getSelectedIndex())));
            InsertTextView.setFontStyle(yy, xx, zz);
            conss.addView(InsertTextView);
            if (clickView == null && AddViews.size() == 0) {
                set.connect(InsertTextView.getId(), ConstraintSet.TOP, brBottomHead.getId(), ConstraintSet.BOTTOM, 0);
                set.connect(InsertTextView.getId(), ConstraintSet.LEFT, brRightHead.getId(), ConstraintSet.RIGHT, 0);
            } else if (clickView == null && AddViews.size() != 0) {
                set.connect(InsertTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, AddViews.get(AddViews.size() - 1).getTop());
                set.connect(InsertTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, AddViews.get(AddViews.size() - 1).getLeft() + AddViews.get(AddViews.size() - 1).getWidth() + 5);
            } else {
                set.connect(InsertTextView.getId(), ConstraintSet.TOP, conss.getId(), ConstraintSet.TOP, clickView.getTop());
                set.connect(InsertTextView.getId(), ConstraintSet.LEFT, conss.getId(), ConstraintSet.LEFT, clickView.getLeft());

                conss.removeView(clickView);
                AddViews.remove(clickView);
            }

            set.constrainWidth(InsertTextView.getId(), ConstraintSet.WRAP_CONTENT);
            set.constrainHeight(InsertTextView.getId(), ConstraintSet.WRAP_CONTENT);

            AddViews.add(InsertTextView);
            set.applyTo(conss);

        } else {
            String TextText = editTextText.getText().toString().trim();
            MLog.v("TextText:" + TextText);
            if (TextText.equals("")) {
                ToastUtils.ToastShow("请输入文本");
                return;
            }
        }
        //关闭当前的弹窗
        BigConss.removeView(view);
        nowShowView = null;
        //显示菜单
        clearClick();
        hideMenu(false, 1);
    }

}
