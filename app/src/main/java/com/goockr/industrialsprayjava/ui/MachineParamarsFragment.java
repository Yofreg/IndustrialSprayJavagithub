package com.goockr.industrialsprayjava.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.ui.machine.ChangeLanguageFragment;
import com.goockr.industrialsprayjava.ui.machine.ChangeTimeFragment;
import com.goockr.industrialsprayjava.ui.machine.CommSettingFragment;
import com.goockr.industrialsprayjava.ui.machine.ConsumableInfoFragment;
import com.goockr.industrialsprayjava.ui.machine.DateCodeFragment;
import com.goockr.industrialsprayjava.ui.machine.MachineMainFragment;
import com.goockr.industrialsprayjava.ui.machine.ProductCountFragment;
import com.goockr.industrialsprayjava.ui.machine.ScreenCalibrationFragment;
import com.goockr.industrialsprayjava.ui.machine.TimeCodeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by lijinning on 2018/3/12.
 * 机器参数
 */

public class MachineParamarsFragment extends BaseFragment {

    @BindView(R.id.MachineFragment)
    FrameLayout MachineFragment;
    @BindView(R.id.SaveSetting)
    Button SaveSetting;
    @BindView(R.id.DateCode)
    Button DateCode;
    @BindView(R.id.TimeCode)
    Button TimeCode;
    @BindView(R.id.CommSetting)
    Button CommSetting;
    @BindView(R.id.ProductCount)
    Button ProductCount;
    @BindView(R.id.ConsumableInfo)
    Button ConsumableInfo;
    @BindView(R.id.ibStart)
    ImageButton ibStart;
    @BindView(R.id.homeLlControl)
    LinearLayout homeLlControl;
    @BindView(R.id.ibStart1)
    ImageButton ibStart1;
    @BindView(R.id.ChangeLanguage)
    Button ChangeLanguage;
    @BindView(R.id.ChangeTime)
    Button ChangeTime;
    @BindView(R.id.ScreenCalibration)
    Button ScreenCalibration;
    @BindView(R.id.homeLlControl2)
    LinearLayout homeLlControl2;
    Unbinder unbinder;

    private MachineMainFragment machineMainFragment;
    private DateCodeFragment dateCodeFragment;
    private TimeCodeFragment timeCodeFragment;
    private CommSettingFragment commSettingFragment;
    private ProductCountFragment productCountFragment;
    private ConsumableInfoFragment consumableInfoFragment;
    private ChangeLanguageFragment changeLanguageFragment;
    private ChangeTimeFragment changeTimeFragment;
    private ScreenCalibrationFragment screenCalibrationFragment;

    private List<SupportFragment> fragments;
    int FIRST = 0;
    int SECOND = 1;
    int THIRD = 2;
    int FOURTH = 3;
    int FIVE = 4;
    int SIX = 5;
    int SEVEN = 6;
    int EIGHT = 7;
    int NINE = 8;
    private int currentPotion = 0;
    private static MachineParamarsFragment instance;
    public static MachineParamarsFragment newInstance() {
        Bundle args = new Bundle();
        MachineParamarsFragment homeFragment = new MachineParamarsFragment();
        homeFragment.setArguments(args);
        instance = homeFragment;
        return homeFragment;
    }

    public static MachineParamarsFragment getInstance() {
        if (instance == null) {
            instance = new MachineParamarsFragment();
        }
        return instance;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machine_paramars, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        machineMainFragment = new MachineMainFragment();
        dateCodeFragment = new DateCodeFragment();
        timeCodeFragment = new TimeCodeFragment();
        commSettingFragment = new CommSettingFragment();
        productCountFragment = new ProductCountFragment();
        consumableInfoFragment = new ConsumableInfoFragment();
        changeLanguageFragment = new ChangeLanguageFragment();
        changeTimeFragment = new ChangeTimeFragment();
        screenCalibrationFragment = new ScreenCalibrationFragment();

        fragments = new ArrayList<SupportFragment>();

        MachineMainFragment firstFragment = findFragment(MachineMainFragment.class);
        if (firstFragment == null) {
            fragments.add(FIRST, machineMainFragment);
            fragments.add(SECOND, dateCodeFragment);
            fragments.add(THIRD, timeCodeFragment);
            fragments.add(FOURTH, commSettingFragment);
            fragments.add(FIVE, productCountFragment);
            fragments.add(SIX, consumableInfoFragment);
            fragments.add(SEVEN, changeLanguageFragment);
            fragments.add(EIGHT, changeTimeFragment);
            fragments.add(NINE, screenCalibrationFragment);

            loadMultipleRootFragment(R.id.MachineFragment, FIRST,
                    fragments.get(FIRST),
                    fragments.get(SECOND),
                    fragments.get(THIRD),
                    fragments.get(FOURTH),
                    fragments.get(FIVE),
                    fragments.get(SIX), fragments.get(SEVEN), fragments.get(EIGHT), fragments.get(NINE));
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            fragments.add(FIRST, firstFragment);
            fragments.add(SECOND, findFragment(DateCodeFragment.class));
            fragments.add(THIRD, findFragment(TimeCodeFragment.class));
            fragments.add(FOURTH, findFragment(CommSettingFragment.class));
            fragments.add(FIVE, findFragment(ProductCountFragment.class));
            fragments.add(SIX, findFragment(ConsumableInfoFragment.class));
            fragments.add(SEVEN, findFragment(ChangeLanguageFragment.class));
            fragments.add(EIGHT, findFragment(ChangeTimeFragment.class));
            fragments.add(NINE, findFragment(ScreenCalibrationFragment.class));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.ChangeLanguage, R.id.SaveSetting, R.id.DateCode, R.id.TimeCode, R.id.CommSetting, R.id.ProductCount, R.id.ConsumableInfo, R.id.ibStart, R.id.ibStart1, R.id.ChangeTime, R.id.ScreenCalibration})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.SaveSetting:
                machineMainFragment.saveSetting();
                break;
            case R.id.DateCode:
                //日期代码
                showHind(1);
                break;
            case R.id.TimeCode:
                //时间代码
                showHind(2);
                break;
            case R.id.CommSetting:
                //通讯设置
                showHind(3);
                break;
            case R.id.ProductCount:
                //产品计数
                showHind(4);
                break;
            case R.id.ConsumableInfo:
                //耗材信息
                showHind(5);
                break;
            case R.id.ibStart:
                homeLlControl.setVisibility(View.INVISIBLE);
                homeLlControl2.setVisibility(View.VISIBLE);
                break;
            case R.id.ibStart1:
                homeLlControl.setVisibility(View.VISIBLE);
                homeLlControl2.setVisibility(View.INVISIBLE);
                break;
            case R.id.ChangeLanguage:
                //更改语言
                showHind(6);
                break;
            case R.id.ChangeTime:
                //修改时间
                showHind(7);
                break;
            case R.id.ScreenCalibration:
                //屏幕校准
                showHind(8);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            showHind();
        }
    }

    public void showHindMain(int index) {
        //主页
        homeLlControl.setVisibility(View.VISIBLE);
        showHideFragment(fragments.get(0), fragments.get(index));
        currentPotion = 0;
    }
//    public void showHindMain1(int index) {
//        //主页
//        homeLlControl2.setVisibility(View.VISIBLE);
//        showHideFragment(fragments.get(0), fragments.get(index));
//        currentPotion = 0;
//    }
    private void showHind(int index) {
        //主页
        if (currentPotion != index) {
            homeLlControl.setVisibility(View.GONE);
            homeLlControl2.setVisibility(View.GONE);
            showHideFragment(fragments.get(index), fragments.get(currentPotion));
            currentPotion = index;
        }
    }

    public void getInkInfo() {
        consumableInfoFragment.updateInk();
    }

    private void showHind() {
        //主页
        if (currentPotion != 0) {
            homeLlControl.setVisibility(View.VISIBLE);
            showHideFragment(fragments.get(0), fragments.get(currentPotion));
            currentPotion = 0;
        }
    }

}
