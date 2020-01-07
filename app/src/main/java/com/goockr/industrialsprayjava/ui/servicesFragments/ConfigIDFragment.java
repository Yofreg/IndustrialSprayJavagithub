package com.goockr.industrialsprayjava.ui.servicesFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;

/**
 * @author yofreg
 * @time 2018/5/29 9:47
 * @desc 配置id
 */
public class ConfigIDFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_1, tv_2, tv_3;
    private EditText et_config;
    private Button btn_quxiao, btn_queding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.config_id_fragment, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        tv_1 = view.findViewById(R.id.tv_1);
        tv_2 = view.findViewById(R.id.tv_2);
        tv_3 = view.findViewById(R.id.tv_3);
        et_config = view.findViewById(R.id.et_config);
        btn_quxiao = view.findViewById(R.id.btn_quxiao);
        btn_queding = view.findViewById(R.id.btn_queding);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        btn_quxiao.setOnClickListener(this);
        btn_queding.setOnClickListener(this);

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_quxiao:
                Fragment fragment = ConfigIDFragment.this.getParentFragment();
                MenuFragment smf = (MenuFragment) fragment;
                smf.finishconfig();
                break;
            case R.id.btn_queding:

                break;
        }
    }
}
