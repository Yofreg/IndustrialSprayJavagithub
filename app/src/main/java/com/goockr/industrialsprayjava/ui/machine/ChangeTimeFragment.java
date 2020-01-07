package com.goockr.industrialsprayjava.ui.machine;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Trace;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.tools.roots;
import com.goockr.industrialsprayjava.ui.MachineParamarsFragment;
import com.goockr.industrialsprayjava.view.MyDatePickerDialog;
import com.goockr.industrialsprayjava.view.MyTimepickerDialog;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeTimeFragment extends BaseFragment {


    @BindView(R.id.ChangeDay)
    TextView ChangeDay;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.ChangeTime)
    TextView ChangeTime;
    @BindView(R.id.ChangeLanguageCancel)
    Button ChangeLanguageCancel;
    @BindView(R.id.ChangeLanguageSure)
    Button ChangeLanguageSure;
    Unbinder unbinder;

    private Context context;
    private SimpleDateFormat df1, df2;

    public ChangeTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_time, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();
        df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ChangeDay.setText(df1.format(new Date(System.currentTimeMillis())));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ChangeDay, R.id.tv3, R.id.ChangeTime, R.id.ChangeLanguageCancel, R.id.ChangeLanguageSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ChangeDay:
            case R.id.tv3:
//                new MyDatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        showInText(true, year, month + 1, dayOfMonth);
//                    }
//                }, 2018, 0, 1).show();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 1234);
                break;
            case R.id.ChangeTime:
                new MyTimepickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, new MyTimepickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        showInText(false, hourOfDay, minute, 0);
                    }
                }, 12, 0, true).show();

                break;
            case R.id.ChangeLanguageCancel:
                MachineParamarsFragment.getInstance().showHindMain(7);
                break;
            case R.id.ChangeLanguageSure:

               try {
                    String time = ChangeDay.getText().toString() + " " + ChangeTime.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    long t = sdf.parse(time).getTime();
                    SystemClock.setCurrentTimeMillis(t);
                    MachineParamarsFragment.getInstance().showHindMain(7);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1234) {
            Log.e("tag", "");
        }
    }

    private void showInText(boolean isDay, int YearOrHour, int MonthOrMin, int DayOrSecond) {
        String str1 = new DecimalFormat("00").format(YearOrHour);
        String str2 = new DecimalFormat("00").format(MonthOrMin);
        String str3 = new DecimalFormat("00").format(DayOrSecond);

        if (isDay) {
            ChangeDay.setText(str1 + "-" + str2 + "-" + str3);
        } else {
            ChangeTime.setText(str1 + ":" + str2);
        }
    }

}
