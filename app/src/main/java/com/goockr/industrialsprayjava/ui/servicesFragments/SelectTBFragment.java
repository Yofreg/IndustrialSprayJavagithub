package com.goockr.industrialsprayjava.ui.servicesFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author yofreg
 * @time 2018/5/29 9:45
 * @desc 选择图标
 */
public class SelectTBFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.btn_quxiao)
    Button btnQuxiao;
    @BindView(R.id.btn_queding)
    Button btnQueding;
    Unbinder unbinder;
    @BindView(R.id.sp1)
    MaterialSpinner sp1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_tb_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        sp1.setItems(getString(R.string.MachineType),getString(R.string.ManufacturerIcon),getString(R.string.CustomIcon));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_quxiao:

                break;
            case R.id.btn_queding:

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_quxiao, R.id.btn_queding})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_quxiao:
                Fragment fragment = SelectTBFragment.this.getParentFragment();
                MenuFragment smf = (MenuFragment) fragment;
                smf.finishselect();
                break;
            case R.id.btn_queding:
                break;
        }
    }
}
