package com.goockr.industrialsprayjava.ui.servicesFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.tools.EncryptForStr;
import com.goockr.industrialsprayjava.tools.ToastUtils;
import com.goockr.industrialsprayjava.ui.ServiceMenuFragment;

/**
 * @author yofreg
 * @time 2018/5/28 17:41
 * @desc 高级菜单
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener {

    private EditText et1;
    private Button btn_quxiao, btn_queding, btn_quxiao2, btn_service, btn_select, btn_config;
    private RelativeLayout rl1, rl2;
    private FrameLayout fl;

    private ServiceBJFragment serviceBJF;
    private SelectTBFragment selectTBF;
    private ConfigIDFragment configIDF;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        btn_config.setOnClickListener(this);
        btn_quxiao.setOnClickListener(this);
        btn_queding.setOnClickListener(this);
        btn_quxiao2.setOnClickListener(this);
        btn_service.setOnClickListener(this);
        btn_select.setOnClickListener(this);

        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        initUI(view);

        return view;
    }

    private void initUI(View view) {
        et1 = view.findViewById(R.id.et1);
        btn_quxiao = view.findViewById(R.id.btn_quxiao);
        btn_queding = view.findViewById(R.id.btn_queding);
        btn_quxiao2 = view.findViewById(R.id.btn_quxiao2);
        btn_service = view.findViewById(R.id.btn_service);
        btn_select = view.findViewById(R.id.btn_select);
        btn_config = view.findViewById(R.id.btn_config);
        rl1 = view.findViewById(R.id.rl1);
        rl2 = view.findViewById(R.id.rl2);
        fl = view.findViewById(R.id.fl);

        serviceBJF = new ServiceBJFragment();
        selectTBF = new SelectTBFragment();
        configIDF = new ConfigIDFragment();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_quxiao:
                Fragment fragment = MenuFragment.this.getParentFragment();
                ServiceMenuFragment smf = (ServiceMenuFragment) fragment;
                smf.finishmenu();
                return;
            case R.id.btn_queding:
                String xx = et1.getText().toString().trim();
                if (!xx.equals(EncryptForStr.getStr(5))) {
                    ToastUtils.ToastShow(getString(R.string.ErrorPwd));
                    return;
                }
                rl1.setVisibility(View.INVISIBLE);
                rl2.setVisibility(View.VISIBLE);
                return;
            case R.id.btn_quxiao2:
                Fragment fragment1 = MenuFragment.this.getParentFragment();
                ServiceMenuFragment smf1 = (ServiceMenuFragment) fragment1;
                smf1.finishmenu();

                return;
        }
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fl.setVisibility(View.VISIBLE);
        switch (v.getId()) {
            case R.id.btn_service:
                transaction.replace(R.id.fl, serviceBJF);
                break;
            case R.id.btn_select:
                transaction.replace(R.id.fl, selectTBF);
                break;
            case R.id.btn_config:
                //Log.v("xxxxxf", "点配置了");
                transaction.replace(R.id.fl, configIDF);
                break;
        }
        transaction.commit();
    }

    public void finishservice() {
        rl2.setVisibility(View.VISIBLE);
        fl.setVisibility(View.INVISIBLE);
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(serviceBJF);
        transaction.commit();
    }

    public void finishselect() {
        rl2.setVisibility(View.VISIBLE);
        fl.setVisibility(View.INVISIBLE);
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(selectTBF);
        transaction.commit();
    }

    public void finishconfig() {
        rl2.setVisibility(View.VISIBLE);
        fl.setVisibility(View.INVISIBLE);
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(configIDF);
        transaction.commit();
    }


}
