package com.goockr.industrialsprayjava.ui.machine;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.goockr.industrialsprayjava.tools.ToastUtils;
import com.goockr.industrialsprayjava.ui.MachineParamarsFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductCountFragment extends BaseFragment {


    @BindView(R.id.ProductCountCancel)
    Button ProductCountCancel;
    @BindView(R.id.ProductCountSure)
    Button ProductCountSure;
    @BindView(R.id.ProductCountReset)
    Button ProductCountReset;
    @BindView(R.id.ProductCountAllReset)
    Button ProductCountAllReset;
    Unbinder unbinder;
    @BindView(R.id.DetectionCount)
    TextView DetectionCount;
    @BindView(R.id.PrintCount)
    TextView PrintCount;
    @BindView(R.id.ResetTime)
    TextView ResetTime;
    @BindView(R.id.AllDetectionCount)
    TextView AllDetectionCount;
    @BindView(R.id.AllPrintCount)
    TextView AllPrintCount;
    @BindView(R.id.AllResetTime)
    TextView AllResetTime;

    public ProductCountFragment() {
        // Required empty public constructor
    }

    private SimpleDateFormat df;
    private Date date;
    private int detectionCount, printCount, allDetectionCount, allPrintCount;
    private String resetTime, allResetTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_count, container, false);
        unbinder = ButterKnife.bind(this, view);
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        initView();

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initView();
        }
    }

    private void initView() {
        detectionCount = PreferenceUtils.getInstance().detectionCount;
        printCount = PreferenceUtils.getInstance().printCount;
        resetTime = PreferenceUtils.getInstance().resetTime;

        allDetectionCount = PreferenceUtils.getInstance().allDetectionCount;
        allPrintCount = PreferenceUtils.getInstance().allPrintCount;
        allResetTime = PreferenceUtils.getInstance().allResetTime;

        DetectionCount.setText(detectionCount+"");
        PrintCount.setText(printCount+"");
        ResetTime.setText(resetTime);

        AllDetectionCount.setText(allDetectionCount+"");
        AllPrintCount.setText(allPrintCount+"");
        AllResetTime.setText(allResetTime);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ProductCountCancel, R.id.ProductCountSure, R.id.ProductCountReset, R.id.ProductCountAllReset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ProductCountCancel:
                MachineParamarsFragment.getInstance().showHindMain(4);
                break;
            case R.id.ProductCountSure:
                MachineParamarsFragment.getInstance().showHindMain(4);
                break;
            case R.id.ProductCountReset:
                ResetCount();
                break;
            case R.id.ProductCountAllReset:
                ResetCountAll();
                break;
        }
    }

    private void ResetCount() {
        new AlertDialog.Builder(getContext()).setTitle(getString(R.string.prompt)).setMessage(getString(R.string.PrintoutsReset))
                .setNegativeButton(getString(R.string.Cancel), null).setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                date = new Date(System.currentTimeMillis());

                DetectionCount.setText(0 + "");
                PrintCount.setText(0+"");
                ResetTime.setText(df.format(date));
                PreferenceUtils.getInstance().setRestart(df.format(date));

                ToastUtils.ToastShow(getString(R.string.SuccessfulReset));
            }
        }).show();
    }

    private void ResetCountAll() {
        new AlertDialog.Builder(getContext()).setTitle(getString(R.string.prompt)).setMessage(getString(R.string.AllPrintsReset))
                .setNegativeButton(getString(R.string.Cancel), null).setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                date = new Date(System.currentTimeMillis());
                AllDetectionCount.setText(0 + "");
                AllPrintCount.setText(0+"");
                AllResetTime.setText(df.format(date));
                PreferenceUtils.getInstance().setAllRestart(df.format(date));

                ToastUtils.ToastShow(getString(R.string.FullResetSuccessful));
            }
        }).show();
    }

}
