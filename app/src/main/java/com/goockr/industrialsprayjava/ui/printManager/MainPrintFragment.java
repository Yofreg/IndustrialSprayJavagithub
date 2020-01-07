package com.goockr.industrialsprayjava.ui.printManager;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.goockr.industrialsprayjava.MainActivity;
import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.bean.PrintNo;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.goockr.industrialsprayjava.tools.ToastUtils;
import com.goockr.pmj.utils.MLog;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPrintFragment extends BaseFragment implements MaterialSpinner.OnItemSelectedListener {


    @BindView(R.id.PrintMainFontLetterStyle)
    MaterialSpinner PrintMainFontLetterStyle;
    @BindView(R.id.StyleSetting)
    EditText StyleSetting;
    @BindView(R.id.RepeatPrintSpinner)
    MaterialSpinner RepeatPrintSpinner;
    @BindView(R.id.RepeatPrintCount)
    EditText RepeatPrintCount;
    @BindView(R.id.UseCountShowSpinner)
    MaterialSpinner UseCountShowSpinner;
    @BindView(R.id.RepeatPrintInterval)
    EditText RepeatPrintInterval;
    @BindView(R.id.UsePrintOverSpinner)
    MaterialSpinner UsePrintOverSpinner;
    @BindView(R.id.PrintNum1)
    EditText PrintNum1;
    @BindView(R.id.Restoration1)
    Button Restoration1;
    @BindView(R.id.Update1)
    Button Update1;
    @BindView(R.id.PrintNum2)
    EditText PrintNum2;
    @BindView(R.id.Restoration2)
    Button Restoration2;
    @BindView(R.id.Update2)
    Button Update2;
    @BindView(R.id.PrintNum3)
    EditText PrintNum3;
    @BindView(R.id.Restoration3)
    Button Restoration3;
    @BindView(R.id.Update3)
    Button Update3;
    @BindView(R.id.PrintNum4)
    EditText PrintNum4;
    @BindView(R.id.Restoration4)
    Button Restoration4;
    @BindView(R.id.Update4)
    Button Update4;
    Unbinder unbinder;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv9)
    TextView tv9;
    @BindView(R.id.tv10)
    TextView tv10;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv11)
    TextView tv11;
    @BindView(R.id.tv12)
    TextView tv12;
    @BindView(R.id.tv13)
    TextView tv13;
    @BindView(R.id.tv14)
    TextView tv14;
    @BindView(R.id.cb)
    CheckBox cb;

    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String limitNo1;
    private String limitNo2;
    private String limitNo3;
    private String limitNo4;
    private String limitNo5;
    private String limitNo6;
    private String limitNo7;
    private String limitNo8;
    private boolean isReset1 = false;
    private boolean isReset2 = false;
    private boolean isReset3 = false;
    private boolean isReset4 = false;


    public MainPrintFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_print, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();

        return view;
    }

    private void initView() {
        PrintMainFontLetterStyle.setItems(getString(R.string.internal), getString(R.string.external));
        RepeatPrintSpinner.setItems(getString(R.string.off), getString(R.string.on));
        UseCountShowSpinner.setItems(getString(R.string.Yes));
        UsePrintOverSpinner.setItems(getString(R.string.No),getString(R.string.Yes));

        PrintMainFontLetterStyle.setOnItemSelectedListener(this);
        RepeatPrintSpinner.setOnItemSelectedListener(this);
        //ShowNo();
        DynamicShow();
        DynamicShow1();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        MLog.v("阿拉啦啦啦啦");
        ShowNo();
        if (!hidden) {
            DynamicShow();
            DynamicShow1();
        }
    }

    public void ShowNo() {
        MLog.v("执行");
        if (PreferenceUtils.getInstance().no1.equals("")) {
            tv11.setVisibility(View.INVISIBLE);
            PrintNum1.setVisibility(View.INVISIBLE);
            Restoration1.setVisibility(View.INVISIBLE);
            Update1.setVisibility(View.INVISIBLE);
        } else {
            tv11.setVisibility(View.VISIBLE);
            PrintNum1.setVisibility(View.VISIBLE);
            Restoration1.setVisibility(View.VISIBLE);
            Update1.setVisibility(View.VISIBLE);

            JSONObject jsonObject = JSONObject.parseObject(PreferenceUtils.getInstance().no1);
            JSONObject object = jsonObject.getJSONObject("PrintNo");
            text1 = object.getString("value");
            limitNo1 = object.getString("limit1");
            limitNo2 = object.getString("limit2");
            PrintNum1.setText(text1);
        }

        if (PreferenceUtils.getInstance().no2.equals("")) {
            tv12.setVisibility(View.INVISIBLE);
            PrintNum2.setVisibility(View.INVISIBLE);
            Restoration2.setVisibility(View.INVISIBLE);
            Update2.setVisibility(View.INVISIBLE);
        } else {
            tv12.setVisibility(View.VISIBLE);
            PrintNum2.setVisibility(View.VISIBLE);
            Restoration2.setVisibility(View.VISIBLE);
            Update2.setVisibility(View.VISIBLE);

            JSONObject jsonObject = JSONObject.parseObject(PreferenceUtils.getInstance().no2);
            JSONObject object = jsonObject.getJSONObject("PrintNo");
            text2 = object.getString("value");
            limitNo3 = object.getString("limit1");
            limitNo4 = object.getString("limit2");
            PrintNum2.setText(text2);
        }

        if (PreferenceUtils.getInstance().no3.equals("")) {
            tv13.setVisibility(View.INVISIBLE);
            PrintNum3.setVisibility(View.INVISIBLE);
            Restoration3.setVisibility(View.INVISIBLE);
            Update3.setVisibility(View.INVISIBLE);
        } else {
            tv13.setVisibility(View.VISIBLE);
            PrintNum3.setVisibility(View.VISIBLE);
            Restoration3.setVisibility(View.VISIBLE);
            Update3.setVisibility(View.VISIBLE);

            JSONObject jsonObject = JSONObject.parseObject(PreferenceUtils.getInstance().no3);
            JSONObject object = jsonObject.getJSONObject("PrintNo");
            text3 = object.getString("value");
            limitNo5 = object.getString("limit1");
            limitNo6 = object.getString("limit2");
            PrintNum3.setText(text3);
        }

        if (PreferenceUtils.getInstance().no4.equals("")) {
            tv14.setVisibility(View.INVISIBLE);
            PrintNum4.setVisibility(View.INVISIBLE);
            Restoration4.setVisibility(View.INVISIBLE);
            Update4.setVisibility(View.INVISIBLE);
        } else {
            tv14.setVisibility(View.VISIBLE);
            PrintNum4.setVisibility(View.VISIBLE);
            Restoration4.setVisibility(View.VISIBLE);
            Update4.setVisibility(View.VISIBLE);
            JSONObject jsonObject = JSONObject.parseObject(PreferenceUtils.getInstance().no4);
            JSONObject object = jsonObject.getJSONObject("PrintNo");
            text4 = object.getString("value");
            limitNo7 = object.getString("limit1");
            limitNo8 = object.getString("limit2");
            PrintNum4.setText(text4);
        }

    }

    @OnClick({R.id.Restoration1, R.id.Update1, R.id.Restoration2, R.id.Update2, R.id.Restoration3, R.id.Update3, R.id.Restoration4, R.id.Update4})
    public void onViewClicked(View view) {
        PrintNo printNo;
        JSONObject object;
        switch (view.getId()) {
            case R.id.Restoration1:
                PrintNum1.setText(limitNo1);
                object = new JSONObject();
                printNo = new PrintNo(limitNo1, limitNo1, limitNo2);
                object.put("PrintNo", printNo);
                PreferenceUtils.getInstance().setNo1(object.toJSONString());
                break;
            case R.id.Update1:
                ToastUtils.ToastShow(getString(R.string.Updatesuccess));
                object = new JSONObject();
                printNo = new PrintNo(PrintNum1.getText().toString(), limitNo1, limitNo2);
                object.put("PrintNo", printNo);
                PreferenceUtils.getInstance().setNo1(object.toJSONString());
                break;
            case R.id.Restoration2:
                PrintNum2.setText(limitNo3);
                object = new JSONObject();
                printNo = new PrintNo(limitNo3, limitNo3, limitNo4);
                object.put("PrintNo", printNo);
                PreferenceUtils.getInstance().setNo2(object.toJSONString());
                break;
            case R.id.Update2:
                ToastUtils.ToastShow(getString(R.string.Updatesuccess));
                object = new JSONObject();
                printNo = new PrintNo(PrintNum2.getText().toString(), limitNo3, limitNo4);
                object.put("PrintNo", printNo);
                PreferenceUtils.getInstance().setNo2(object.toJSONString());
                break;
            case R.id.Restoration3:
                PrintNum3.setText(limitNo5);
                object = new JSONObject();
                printNo = new PrintNo(limitNo5, limitNo5, limitNo6);
                object.put("PrintNo", printNo);
                PreferenceUtils.getInstance().setNo3(object.toJSONString());
                break;
            case R.id.Update3:
                ToastUtils.ToastShow(getString(R.string.Updatesuccess));
                object = new JSONObject();
                printNo = new PrintNo(PrintNum3.getText().toString(), limitNo5, limitNo6);
                object.put("PrintNo", printNo);
                PreferenceUtils.getInstance().setNo3(object.toJSONString());
                break;
            case R.id.Restoration4:
                PrintNum4.setText(limitNo7);
                object = new JSONObject();
                printNo = new PrintNo(limitNo7, limitNo7, limitNo8);
                object.put("PrintNo", printNo);
                PreferenceUtils.getInstance().setNo4(object.toJSONString());
                break;
            case R.id.Update4:
                ToastUtils.ToastShow(getString(R.string.Updatesuccess));
                object = new JSONObject();
                printNo = new PrintNo(PrintNum4.getText().toString(), limitNo7, limitNo8);
                object.put("PrintNo", printNo);
                PreferenceUtils.getInstance().setNo4(object.toJSONString());
                break;
        }
    }

    private void ChangeNo(int i, boolean b) {

    }

    private void DynamicShow_1() {
        StyleSetting.setText(PreferenceUtils.getInstance().styleSettingCount+"");
        if (PrintMainFontLetterStyle.getSelectedIndex() == 0) {
            tv4.setVisibility(View.GONE);
            tv5.setVisibility(View.GONE);

            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
        } else {
            tv4.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);

            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
        }
    }

    private void DynamicShow() {
        StyleSetting.setText(PreferenceUtils.getInstance().styleSettingCount+"");
        PrintMainFontLetterStyle.setSelectedIndex(PreferenceUtils.getInstance().printMainFontLetterStyle);
        if (PrintMainFontLetterStyle.getSelectedIndex() == 0) {
            tv4.setVisibility(View.GONE);
            tv5.setVisibility(View.GONE);

            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
        } else {
            tv4.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);

            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        switch (view.getId()) {
            case R.id.PrintMainFontLetterStyle:
                DynamicShow_1();
                break;
            case R.id.RepeatPrintSpinner:
                DynamicShow1_1();
                break;
        }
    }


    private void DynamicShow1_1() {
        RepeatPrintCount.setText(PreferenceUtils.getInstance().repeatCount + "");
        RepeatPrintInterval.setText(PreferenceUtils.getInstance().repeatDelay + "");

        if (RepeatPrintSpinner.getSelectedIndex() == 1) {
            tv7.setVisibility(View.VISIBLE);
            RepeatPrintCount.setVisibility(View.VISIBLE);
            tv9.setVisibility(View.VISIBLE);
            RepeatPrintInterval.setVisibility(View.VISIBLE);

            tv8.setVisibility(View.VISIBLE);
            tv10.setVisibility(View.VISIBLE);
            UseCountShowSpinner.setVisibility(View.VISIBLE);
            UsePrintOverSpinner.setVisibility(View.VISIBLE);
        } else {
            tv7.setVisibility(View.GONE);
            RepeatPrintCount.setVisibility(View.GONE);
            tv9.setVisibility(View.GONE);
            RepeatPrintInterval.setVisibility(View.GONE);

            tv8.setVisibility(View.GONE);
            tv10.setVisibility(View.GONE);
            UseCountShowSpinner.setVisibility(View.GONE);
            UsePrintOverSpinner.setVisibility(View.GONE);
        }
    }

    private void DynamicShow1() {
        RepeatPrintSpinner.setSelectedIndex(PreferenceUtils.getInstance().repeatPrint);
        RepeatPrintCount.setText(PreferenceUtils.getInstance().repeatCount + "");
        RepeatPrintInterval.setText(PreferenceUtils.getInstance().repeatDelay + "");
        UsePrintOverSpinner.setSelectedIndex(PreferenceUtils.getInstance().userEndSign);

        if (RepeatPrintSpinner.getSelectedIndex() == 1) {
            tv7.setVisibility(View.VISIBLE);
            RepeatPrintCount.setVisibility(View.VISIBLE);
            tv9.setVisibility(View.VISIBLE);
            RepeatPrintInterval.setVisibility(View.VISIBLE);

            tv8.setVisibility(View.VISIBLE);
            tv10.setVisibility(View.VISIBLE);
            UseCountShowSpinner.setVisibility(View.VISIBLE);
            UsePrintOverSpinner.setVisibility(View.VISIBLE);
        } else {
            tv7.setVisibility(View.GONE);
            RepeatPrintCount.setVisibility(View.GONE);
            tv9.setVisibility(View.GONE);
            RepeatPrintInterval.setVisibility(View.GONE);

            tv8.setVisibility(View.GONE);
            tv10.setVisibility(View.GONE);
            UseCountShowSpinner.setVisibility(View.GONE);
            UsePrintOverSpinner.setVisibility(View.GONE);
        }
    }

    private boolean checkRepeat(String x) {
        if (TextUtils.isEmpty(x)) {
            return false;
        }
        if (Integer.parseInt(x) < 1) {
            return false;
        }
        return true;
    }

    private boolean checkStyleSetting(String x) {
        if (TextUtils.isEmpty(x)) {
            return false;
        }
        if (Integer.parseInt(x) < 1) {
            return false;
        }
        if (PrintMainFontLetterStyle.getSelectedIndex() == 1 && Integer.parseInt(x) > 32) {
            return false;
        }
        return true;
    }

    public void saveSetting() {
        if (!checkStyleSetting(StyleSetting.getText().toString().trim())) {
            ToastUtils.ToastShow(getString(R.string.Wordwidth));
            return;
        }

        if (!(checkRepeat(RepeatPrintCount.getText().toString())&&checkRepeat(RepeatPrintInterval.getText().toString()))) {
            ToastUtils.ToastShow(getString(R.string.RepeatError));
            return;
        }


        new AlertDialog.Builder(getContext()).setTitle(getString(R.string.prompt)).setMessage(getString(R.string.NeedSave))
                .setNegativeButton(getString(R.string.Cancel), null).setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                PreferenceUtils.getInstance().setPrintMainFontLetterStyle(PrintMainFontLetterStyle.getSelectedIndex());
                PreferenceUtils.getInstance().setStyleSettingCount(Integer.parseInt(StyleSetting.getText().toString()));
                PreferenceUtils.getInstance().setRepeatPrint(RepeatPrintSpinner.getSelectedIndex());
                PreferenceUtils.getInstance().setRepeatCount(Integer.parseInt(RepeatPrintCount.getText().toString()));
                PreferenceUtils.getInstance().setRepeatDelay(Integer.parseInt(RepeatPrintInterval.getText().toString()));
                PreferenceUtils.getInstance().setUserEndSign(UsePrintOverSpinner.getSelectedIndex());

                MainActivity.cb = cb.isChecked();
                ToastUtils.ToastShow(getString(R.string.SaveSuccessfully));
               
            }
        }).show();
    }
}
