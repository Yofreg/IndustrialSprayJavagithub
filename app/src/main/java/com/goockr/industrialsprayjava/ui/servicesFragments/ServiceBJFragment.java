package com.goockr.industrialsprayjava.ui.servicesFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Guideline;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author yofreg
 * @time 2018/5/29 9:44
 * @desc 服务报警
 */
public class ServiceBJFragment extends BaseFragment {

    @BindView(R.id.sp2)
    MaterialSpinner sp2;
    @BindView(R.id.sp4)
    MaterialSpinner sp4;
    Unbinder unbinder;
    @BindView(R.id.btn_quxiao)
    Button btnQuxiao;
    @BindView(R.id.btn_queding)
    Button btnQueding;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.guideline7)
    Guideline guideline7;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_bj_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initUI();


        return view;
    }

    private void initUI() {
        sp2.setItems(getString(R.string.on), getString(R.string.off),getString(R.string.Downtime));
        sp2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position == 1 || position == 2) {
                    sp4.setVisibility(View.INVISIBLE);
                    textView6.setVisibility(View.INVISIBLE);
                } else {
                    sp4.setVisibility(View.VISIBLE);
                    textView6.setVisibility(View.VISIBLE);
                }
            }
        });
        sp4.setItems("10", "20", "30", "60", "180", "360", "720");
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
                Fragment fragment = ServiceBJFragment.this.getParentFragment();
                MenuFragment smf = (MenuFragment) fragment;
                smf.finishservice();
                break;
            case R.id.btn_queding:
                SettingAlert();

                break;
        }
    }

    private void SettingAlert() {
        if (sp4.getSelectedIndex() == 1 || sp4.getSelectedIndex() == 2) {
        }
    }
}
