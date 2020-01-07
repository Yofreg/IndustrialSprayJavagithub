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
import android.widget.EditText;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSONObject;
import com.goockr.industrialsprayjava.MainActivity;
import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.bean.PrintItem;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.goockr.industrialsprayjava.tools.ToastUtils;
import com.goockr.industrialsprayjava.ui.PrintParamarsFragment;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Print8Fragment extends BaseFragment {


    @BindView(R.id.PrintDelayed)
    EditText PrintDelayed;
    @BindView(R.id.HighLevel)
    RadioButton HighLevel;
    @BindView(R.id.LowLevel)
    RadioButton LowLevel;
    @BindView(R.id.PrintCancel1)
    Button PrintCancel1;
    @BindView(R.id.PrintSure1)
    Button PrintSure1;
    Unbinder unbinder;
    @BindView(R.id.TriggerPrintSpinner)
    MaterialSpinner TriggerPrintSpinner;
    @BindView(R.id.MuzzleSpinner)
    MaterialSpinner MuzzleSpinner;
    @BindView(R.id.MuzzleInterval)
    MaterialSpinner MuzzleInterval;
    @BindView(R.id.MuzzleRow)
    MaterialSpinner MuzzleRow;
    @BindView(R.id.ReservePrintSpinner)
    MaterialSpinner ReservePrintSpinner;
    @BindView(R.id.UpsidePrint)
    MaterialSpinner UpsidePrint;
    @BindView(R.id.ReservePrintSpinner20)
    MaterialSpinner ReservePrintSpinner20;
    @BindView(R.id.PrintDelayed30)
    EditText PrintDelayed30;

    public Print8Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_print8, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();

        return view;
    }

    private void initView() {
        JSONObject object = JSONObject.parseObject(PreferenceUtils.getInstance().print8);
        TriggerPrintSpinner.setItems(getString(R.string.Hosts));

        MuzzleSpinner.setItems(getString(R.string.LeftColumn), getString(R.string.RighColumn));
        MuzzleInterval.setItems(getString(R.string.off), "1", "5", "10", "20", "30", "40", "50", "60");
        MuzzleRow.setItems("1", "2", "3", "4", "5");
        ReservePrintSpinner.setItems(getString(R.string.off), getString(R.string.on));
        UpsidePrint.setItems(getString(R.string.off), getString(R.string.on));
        ReservePrintSpinner20.setItems(8.5, 9.0, 10.0, 10.8);

        if (object != null) {
            double DYDY1 = object.getDouble("dianya");  //打印电压
            int a = (int) (DYDY1 * 10);
            int XPJX = object.getInteger("muzzleInterval");//闲喷间隙
            int XPLS = object.getInteger("muzzleCount");//闲喷列数
            int PZXZ = object.getInteger("muzzleChose");//喷嘴选择
            int CFFS = object.getInteger("muzzleLevel");//喷嘴触发方式
            int reversal = object.getInteger("reversal"); //水平
            int overturn = object.getInteger("overturn"); //垂直

            PrintDelayed.setText(object.getInteger("printDelay") + "");

            MuzzleSpinner.setSelectedIndex(PZXZ);
            switch (XPJX) {
                case 1:
                    MuzzleInterval.setSelectedIndex(1);
                    break;
                case 5:
                    MuzzleInterval.setSelectedIndex(2);
                    break;
                case 10:
                    MuzzleInterval.setSelectedIndex(3);
                    break;
                case 20:
                    MuzzleInterval.setSelectedIndex(4);
                    break;
                case 30:
                    MuzzleInterval.setSelectedIndex(5);
                    break;
                case 40:
                    MuzzleInterval.setSelectedIndex(6);
                    break;
                case 50:
                    MuzzleInterval.setSelectedIndex(7);
                    break;
                case 60:
                    MuzzleInterval.setSelectedIndex(8);
                    break;
            }

            MuzzleRow.setSelectedIndex(XPLS - 1);
            ReservePrintSpinner.setSelectedIndex(reversal);
            UpsidePrint.setSelectedIndex(overturn);
            switch (a) {
                case 85:
                    ReservePrintSpinner20.setSelectedIndex(0);
                    break;
                case 90:
                    ReservePrintSpinner20.setSelectedIndex(1);
                    break;
                case 100:
                    ReservePrintSpinner20.setSelectedIndex(2);
                    break;
                case 108:
                    ReservePrintSpinner20.setSelectedIndex(3);
                    break;

            }
        } else {
            PrintDelayed.setText("10");
            ReservePrintSpinner20.setSelectedIndex(1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.PrintCancel1, R.id.PrintSure1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.PrintCancel1:
                ToastUtils.ToastShow(getString(R.string.SettingsSaved));
                PrintParamarsFragment.getInstance().showHindMain(8);
                break;
            case R.id.PrintSure1:
                if (TextUtils.isEmpty(PrintDelayed.getText().toString())) {
                    ToastUtils.ToastShow(getString(R.string.PrintBlank));
                    return;
                }
                ShowAlert();
                break;
        }
    }


    private void ShowAlert() {
        new AlertDialog.Builder(getContext()).setTitle(getString(R.string.prompt)).setMessage(getString(R.string.NeedSave))
                .setNegativeButton(getString(R.string.Cancel), null).setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SavePrintSetting();
                ToastUtils.ToastShow(getString(R.string.SaveSuccessfully));
                PrintParamarsFragment.getInstance().showHindMain(8);
            }
        }).show();
    }

    private void SavePrintSetting() {
        PrintItem printItem = new PrintItem(Integer.parseInt(PrintDelayed.getText().toString()), MuzzleSpinner.getSelectedIndex(), (MuzzleInterval.getSelectedIndex() == 0 ? 0 : Integer.parseInt((String) MuzzleInterval.getItems().get(MuzzleInterval.getSelectedIndex()))),
                MuzzleRow.getSelectedIndex() + 1, ReservePrintSpinner.getSelectedIndex(), UpsidePrint.getSelectedIndex(), (HighLevel.isChecked() ? 0 : 1), (Double) ReservePrintSpinner20.getItems().get(ReservePrintSpinner20.getSelectedIndex()));
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(printItem);
        PreferenceUtils.getInstance().setPrint8(jsonObject.toJSONString());

        MainActivity.getInstance().sendPZCS(8);
    }
}
