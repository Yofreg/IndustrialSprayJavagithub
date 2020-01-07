package com.goockr.industrialsprayjava.ui.machine;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.goockr.industrialsprayjava.tools.ToastUtils;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MachineMainFragment extends BaseFragment {

    @BindView(R.id.MachineSoundSwitch)
    MaterialSpinner MachineSoundSwitch;
    Unbinder unbinder;
    @BindView(R.id.LengthShowInMM)
    RadioButton LengthShowInMM;
    @BindView(R.id.HighLevel)
    RadioButton HighLevel;
    @BindView(R.id.LengthShowInInch)
    RadioButton LengthShowInInch;
    @BindView(R.id.LengthGroup)
    RadioGroup LengthGroup;
    @BindView(R.id.LowLevel)
    RadioButton LowLevel;
    @BindView(R.id.LevelGroup)
    RadioGroup LevelGroup;

    private View view;

    public MachineMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_machine_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        MachineSoundSwitch.setItems(getString(R.string.off),getString(R.string.on));
        if (PreferenceUtils.getInstance().isSound) {
            MachineSoundSwitch.setSelectedIndex(1);
        }
        if (!PreferenceUtils.getInstance().isMM) {
            LengthShowInInch.setChecked(true);
        }
        if (!PreferenceUtils.getInstance().isHighLevel) {
            LowLevel.setChecked(true);
        }
    }

    public void saveSetting() {
        new AlertDialog.Builder(getContext()).setTitle(getString(R.string.prompt)).setMessage(getString(R.string.NeedSave))
                .setNegativeButton(getString(R.string.Cancel), null).setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (LengthShowInMM.isChecked()) {
                    PreferenceUtils.getInstance().setMM(true);
                } else {
                    PreferenceUtils.getInstance().setMM(false);
                }

                if (HighLevel.isChecked()) {
                    PreferenceUtils.getInstance().setHighLevel(true);
                } else {
                    PreferenceUtils.getInstance().setHighLevel(false);
                }

                if (MachineSoundSwitch.getSelectedIndex() == 0) {
                    PreferenceUtils.getInstance().setSound(false);
                } else {
                    PreferenceUtils.getInstance().setSound(true);
                }
                ToastUtils.ToastShow(getString(R.string.SaveSuccessfully));
            }
        }).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
