package com.goockr.industrialsprayjava.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.tools.SystemUtils;
import com.goockr.industrialsprayjava.ui.servicesFragments.ChangeFragment;
import com.goockr.industrialsprayjava.ui.servicesFragments.MenuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lijinning on 2018/3/12.
 * 服务菜单
 */

public class ServiceMenuFragment extends BaseFragment {

    @BindView(R.id.serviceContent1)
    TextView serviceContent1;
    @BindView(R.id.serviceContent2)
    TextView serviceContent2;
    @BindView(R.id.serviceContent3)
    TextView serviceContent3;
    @BindView(R.id.serviceContent4)
    TextView serviceContent4;
    @BindView(R.id.serviceContent5)
    TextView serviceContent5;
    @BindView(R.id.serviceContent6)
    TextView serviceContent6;
    @BindView(R.id.serviceContent7)
    TextView serviceContent7;
    @BindView(R.id.btn_change)
    Button btnChange;
    @BindView(R.id.btn_menu)
    Button btnMenu;
    @BindView(R.id.serviceBottom)
    LinearLayout serviceBottom;
    @BindView(R.id.guideline5)
    Guideline guideline5;
    @BindView(R.id.fl)
    FrameLayout fl;
    Unbinder unbinder;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.rl)
    ConstraintLayout rl;
    @BindView(R.id.serviceContent8)
    TextView serviceContent8;

    private ChangeFragment changeF;
    private MenuFragment menuF;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    public static ServiceMenuFragment newInstance() {
        Bundle args = new Bundle();
        ServiceMenuFragment homeFragment = new ServiceMenuFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_menu_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);


        initView();


        return view;
    }

    private void initView() {
        changeF = new ChangeFragment();
        menuF = new MenuFragment();
        serviceContent3.setText(""+ SystemUtils.getVerName(getContext()));
    }

    public void finishchange() {
        fl.setVisibility(View.INVISIBLE);
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(changeF);
        transaction.commit();
    }

    public void finishmenu() {
        fl.setVisibility(View.INVISIBLE);
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(menuF);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_change, R.id.btn_menu})
    public void onViewClicked(View view) {
        fl.setVisibility(View.VISIBLE);
        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.btn_change:
                transaction.replace(R.id.rl, changeF);
                break;
            case R.id.btn_menu:
                transaction.replace(R.id.rl, menuF);
                break;
        }
        transaction.commit();
    }
}
