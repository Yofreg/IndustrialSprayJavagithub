package com.goockr.industrialsprayjava.ui.machine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.ui.MachineParamarsFragment;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommSettingFragment extends BaseFragment {


    @BindView(R.id.CommSettingCancel)
    Button CommSettingCancel;
    @BindView(R.id.CommSettingSure)
    Button CommSettingSure;
    Unbinder unbinder;
    @BindView(R.id.GorgeSpinner)
    MaterialSpinner GorgeSpinner;
    @BindView(R.id.BaudRateSpinner)
    MaterialSpinner BaudRateSpinner;
    @BindView(R.id.DataBitSpinner)
    MaterialSpinner DataBitSpinner;
    @BindView(R.id.ParityBitSpinner)
    MaterialSpinner ParityBitSpinner;
    @BindView(R.id.StopBitSpinner)
    MaterialSpinner StopBitSpinner;
    @BindView(R.id.IpAddredd)
    EditText IpAddredd;
    @BindView(R.id.SunnetMask)
    EditText SunnetMask;
    @BindView(R.id.GateWay)
    EditText GateWay;
    @BindView(R.id.TcpPort)
    EditText TcpPort;

    public CommSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comm_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        GorgeSpinner.setItems("COM1","COM2","COM3","COM4","COM5","COM6","COM7");
        BaudRateSpinner.setItems("110","300","600","1200","2400","4800","9600","14400","19200","38400","56000","57600","115200");
        DataBitSpinner.setItems("5","6","7","8");
        ParityBitSpinner.setItems("None","Odd","Even","MFlag","Space");
        StopBitSpinner.setItems("1","1.5","2");


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.CommSettingCancel, R.id.CommSettingSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.CommSettingCancel:
                MachineParamarsFragment.getInstance().showHindMain(3);
                break;
            case R.id.CommSettingSure:
                MachineParamarsFragment.getInstance().showHindMain(3);
                break;
        }
    }
}
