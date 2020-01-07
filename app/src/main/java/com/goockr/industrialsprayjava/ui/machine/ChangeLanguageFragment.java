package com.goockr.industrialsprayjava.ui.machine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.goockr.industrialsprayjava.MainActivity;
import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.tools.LanguageUtil;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.goockr.industrialsprayjava.ui.MachineParamarsFragment;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeLanguageFragment extends BaseFragment {


    @BindView(R.id.ChangeLanguageCancel)
    Button ChangeLanguageCancel;
    @BindView(R.id.ChangeLanguageSure)
    Button ChangeLanguageSure;
    Unbinder unbinder;
    @BindView(R.id.ChangeLanguageSpinner)
    MaterialSpinner ChangeLanguageSpinner;

    public ChangeLanguageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_language, container, false);
        unbinder = ButterKnife.bind(this, view);

        ChangeLanguageSpinner.setItems("中文","English");

        if (PreferenceUtils.getInstance().language.equals("en")) {
            ChangeLanguageSpinner.setSelectedIndex(1);
        } else {
            ChangeLanguageSpinner.setSelectedIndex(0);
        }

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ChangeLanguageCancel, R.id.ChangeLanguageSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ChangeLanguageCancel:
                MachineParamarsFragment.getInstance().showHindMain(6);
                break;
            case R.id.ChangeLanguageSure:
                LanguageUtil.changeAppLanguage(getContext(), ChangeLanguageSpinner.getSelectedIndex() == 0 ? Locale.CHINA : Locale.ENGLISH, true);
                //                getActivity().recreate();
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                              // MachineParamarsFragment.getInstance().showHindMain(6);
                break;
        }
    }
}
