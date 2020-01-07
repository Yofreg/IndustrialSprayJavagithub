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
import com.goockr.industrialsprayjava.ui.printManager.MainPrintFragment;
import com.goockr.industrialsprayjava.ui.printManager.Print1Fragment;
import com.goockr.industrialsprayjava.ui.printManager.Print2Fragment;
import com.goockr.industrialsprayjava.ui.printManager.Print3Fragment;
import com.goockr.industrialsprayjava.ui.printManager.Print4Fragment;
import com.goockr.industrialsprayjava.ui.printManager.Print5Fragment;
import com.goockr.industrialsprayjava.ui.printManager.Print6Fragment;
import com.goockr.industrialsprayjava.ui.printManager.Print7Fragment;
import com.goockr.industrialsprayjava.ui.printManager.Print8Fragment;
import com.goockr.pmj.utils.MLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by lijinning on 2018/3/12.
 * 打印参数
 */

public class PrintParamarsFragment extends BaseFragment {

    @BindView(R.id.PrintFragment)
    FrameLayout PrintFragment;
    @BindView(R.id.SaveSetting)
    Button SaveSetting;
    @BindView(R.id.Print1)
    Button Print1;
    @BindView(R.id.Print2)
    Button Print2;
    @BindView(R.id.Print3)
    Button Print3;
    @BindView(R.id.Print4)
    Button Print4;
    @BindView(R.id.Print5)
    Button Print5;
    @BindView(R.id.ibStart)
    ImageButton ibStart;
    @BindView(R.id.homeLlControl)
    LinearLayout homeLlControl;
    @BindView(R.id.ibStart1)
    ImageButton ibStart1;
    @BindView(R.id.Print6)
    Button Print6;
    @BindView(R.id.Print7)
    Button Print7;
    @BindView(R.id.Print8)
    Button Print8;
    @BindView(R.id.homeLlControl2)
    LinearLayout homeLlControl2;
    Unbinder unbinder;

    public static PrintParamarsFragment newInstance() {
        Bundle args = new Bundle();
        PrintParamarsFragment homeFragment = new PrintParamarsFragment();
        homeFragment.setArguments(args);
        instance = homeFragment;
        return homeFragment;
    }

    private List<SupportFragment> fragments;
    private MainPrintFragment mainPrintFragment;
    private Print1Fragment print1Fragment;
    private Print2Fragment print2Fragment;
    private Print3Fragment print3Fragment;
    private Print4Fragment print4Fragment;
    private Print5Fragment print5Fragment;
    private Print6Fragment print6Fragment;
    private Print7Fragment print7Fragment;
    private Print8Fragment print8Fragment;
    int FIRST = 0;
    int SECOND = 1;
    int THIRD = 2;
    int FOURTH = 3;
    int FIVE = 4;
    int SIX = 5;
    int SEVEN = 6;
    int EIGHT = 7;
    int NINE = 8;
    private static PrintParamarsFragment instance;
    private int currentPotion = 0;

    public static PrintParamarsFragment getInstance() {
        if (instance == null) {
            instance = new PrintParamarsFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_print_paramars, container, false);
        unbinder = ButterKnife.bind(this, view);


        initView(view);
        return view;

    }

    private void initView(View view) {
        mainPrintFragment = new MainPrintFragment();
        print1Fragment = new Print1Fragment();
        print2Fragment = new Print2Fragment();
        print3Fragment = new Print3Fragment();
        print4Fragment = new Print4Fragment();
        print5Fragment = new Print5Fragment();
        print6Fragment = new Print6Fragment();
        print7Fragment = new Print7Fragment();
        print8Fragment = new Print8Fragment();

        fragments = new ArrayList<SupportFragment>();

        MainPrintFragment firstFragment = findFragment(MainPrintFragment.class);
        if (firstFragment == null) {
            fragments.add(FIRST, mainPrintFragment);
            fragments.add(SECOND, print1Fragment);
            fragments.add(THIRD, print2Fragment);
            fragments.add(FOURTH, print3Fragment);
            fragments.add(FIVE, print4Fragment);
            fragments.add(SIX, print5Fragment);
            fragments.add(SEVEN, print6Fragment);
            fragments.add(EIGHT, print7Fragment);
            fragments.add(NINE, print8Fragment);

            loadMultipleRootFragment(R.id.PrintFragment, FIRST,
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
            fragments.add(SECOND, findFragment(Print1Fragment.class));
            fragments.add(THIRD, findFragment(Print2Fragment.class));
            fragments.add(FOURTH, findFragment(Print3Fragment.class));
            fragments.add(FIVE, findFragment(Print4Fragment.class));
            fragments.add(SIX, findFragment(Print5Fragment.class));
            fragments.add(SEVEN, findFragment(Print6Fragment.class));
            fragments.add(EIGHT, findFragment(Print7Fragment.class));
            fragments.add(NINE, findFragment(Print8Fragment.class));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.SaveSetting, R.id.Print1, R.id.Print2, R.id.Print3, R.id.Print4, R.id.Print5, R.id.ibStart, R.id.ibStart1, R.id.Print6, R.id.Print7, R.id.Print8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.SaveSetting:
                mainPrintFragment.saveSetting();
                break;
            case R.id.Print1:
                showHind(1);
                break;
            case R.id.Print2:
                showHind(2);
                break;
            case R.id.Print3:
                showHind(3);
                break;
            case R.id.Print4:
                showHind(4);
                break;
            case R.id.Print5:
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
            case R.id.Print6:
                showHind(6);
                break;
            case R.id.Print7:
                showHind(7);
                break;
            case R.id.Print8:
                showHind(8);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            MLog.v("aaa");
            mainPrintFragment.ShowNo();
        } else {
            showHind();
        }
    }

    public void showHindMain(int index) {
        //主页
        homeLlControl.setVisibility(View.VISIBLE);
        showHideFragment(fragments.get(0), fragments.get(index));
        currentPotion = 0;
    }
    public void showHindMain1(int index) {
        //主页
        homeLlControl2.setVisibility(View.VISIBLE);
        showHideFragment(fragments.get(0), fragments.get(index));
        currentPotion = 0;
    }
    private void showHind(int index) {
        //主页
        if (currentPotion != index) {
            homeLlControl.setVisibility(View.GONE);
            homeLlControl2.setVisibility(View.GONE);
            showHideFragment(fragments.get(index), fragments.get(currentPotion));
            currentPotion = index;
        }
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
