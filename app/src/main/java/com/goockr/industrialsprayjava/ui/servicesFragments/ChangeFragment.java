package com.goockr.industrialsprayjava.ui.servicesFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Guideline;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.goockr.industrialsprayjava.tools.ToastUtils;
import com.goockr.industrialsprayjava.ui.ServiceMenuFragment;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author yofreg
 * @time 2018/5/28 17:39
 * @desc 更改密码
 */
public class ChangeFragment extends BaseFragment {

    @BindView(R.id.PwdSoinner)
    MaterialSpinner PwdSoinner;
    @BindView(R.id.newPwd)
    EditText newPwd;
    @BindView(R.id.newPwd1)
    EditText newPwd1;
    @BindView(R.id.btn_quxiao)
    Button btnQuxiao;
    @BindView(R.id.btn_queding)
    Button btnQueding;
    @BindView(R.id.guideline6)
    Guideline guideline6;
    Unbinder unbinder;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initUI(view);

        return view;
    }

    private void initUI(View view) {
        PwdSoinner.setItems(getString(R.string.on), getString(R.string.off));
        if (PreferenceUtils.getInstance().isPwd) {
            PwdSoinner.setSelectedIndex(0);
            textView3.setVisibility(View.VISIBLE);
            newPwd.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.VISIBLE);
            newPwd1.setVisibility(View.VISIBLE);

        } else {
            PwdSoinner.setSelectedIndex(1);
            textView3.setVisibility(View.INVISIBLE);
            newPwd.setVisibility(View.INVISIBLE);
            textView4.setVisibility(View.INVISIBLE);
            newPwd1.setVisibility(View.INVISIBLE);
        }
        PwdSoinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position == 0) {
                    textView3.setVisibility(View.VISIBLE);
                    newPwd.setVisibility(View.VISIBLE);
                    textView4.setVisibility(View.VISIBLE);
                    newPwd1.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    textView3.setVisibility(View.INVISIBLE);
                    newPwd.setVisibility(View.INVISIBLE);
                    textView4.setVisibility(View.INVISIBLE);
                    newPwd1.setVisibility(View.INVISIBLE);
                }
            }
        });
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
                Fragment fragment = ChangeFragment.this.getParentFragment();
                ServiceMenuFragment smf = (ServiceMenuFragment) fragment;
                smf.finishchange();
                break;
            case R.id.btn_queding:
                SettingPwd();
                break;
        }
    }

    private void SettingPwd() {
        if (PwdSoinner.getSelectedIndex() == 1) {
            PreferenceUtils.getInstance().setPwd(false);
        } else if (PwdSoinner.getSelectedIndex() == 0) {
            String pwd1 = newPwd.getText().toString().trim();
            String pwd2 = newPwd1.getText().toString().trim();
            if (TextUtils.isEmpty(pwd2) || TextUtils.isEmpty(pwd1)) {
                ToastUtils.ToastShow(getString(R.string.PwdNotBlank));
                return;
            } else if (!pwd1.equals(pwd2)) {
                ToastUtils.ToastShow(getString(R.string.PasswordDifferent));
                return;
            } else {
                PreferenceUtils.getInstance().setPwd(true);
                PreferenceUtils.getInstance().setPwd(pwd1);
            }
        }

        Fragment fragment = ChangeFragment.this.getParentFragment();
        ServiceMenuFragment smf = (ServiceMenuFragment) fragment;
        smf.finishchange();
    }
}
