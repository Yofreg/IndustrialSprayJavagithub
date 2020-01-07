package com.goockr.industrialsprayjava.ui.machine;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.tools.SaveJsonToMemory;
import com.goockr.industrialsprayjava.tools.ToastUtils;
import com.goockr.industrialsprayjava.ui.MachineParamarsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeCodeFragment extends BaseFragment {


    @BindView(R.id.MachineCancel)
    Button MachineCancel;
    @BindView(R.id.MachineSure)
    Button MachineSure;
    @BindView(R.id.MachineDel)
    Button MachineDel;
    @BindView(R.id.MachineAdd)
    Button MachineAdd;
    @BindView(R.id.SecondBt)
    Button SecondBt;
    @BindView(R.id.MinBt)
    Button MinBt;
    @BindView(R.id.TimeBt)
    Button TimeBt;
    @BindView(R.id.Split1)
    Button Split1;
    @BindView(R.id.Split2)
    Button Split2;
    @BindView(R.id.Split3)
    Button Split3;
    @BindView(R.id.Split4)
    Button Split4;
    @BindView(R.id.TimeStyle)
    TextView TimeStyle;
    @BindView(R.id.DelTime)
    ImageButton DelTime;
    @BindView(R.id.TimeStyleListView)
    ListView TimeStyleListView;
    Unbinder unbinder;

    private View view;
    private int ChoseTime = 0, ChoseMin = 0, ChoseSecond = 0;
    private SaveJsonToMemory saveJsonToMemory;
    private TimeStyleAdapter timeStyleAdapter;
    private List<String> timeStyleString;
    private Context context;
    private int nowPosition = -1;

    public TimeCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_time_code, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveJsonToMemory = new SaveJsonToMemory();
        context = getContext();
        init();

        return view;
    }

    private void init() {
        timeStyleString = new ArrayList<>();
        timeStyleAdapter = new TimeStyleAdapter(timeStyleString, context);
        TimeStyleListView.setAdapter(timeStyleAdapter);
        TimeStyleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nowPosition = position;
                timeStyleAdapter.setCurrentItem(position);
                timeStyleAdapter.setClick(true);
                timeStyleAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.MachineCancel, R.id.MachineSure, R.id.MachineDel, R.id.MachineAdd, R.id.SecondBt, R.id.MinBt, R.id.TimeBt, R.id.Split1, R.id.Split2, R.id.Split3, R.id.Split4, R.id.TimeStyle, R.id.DelTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.MachineCancel:
                HideThis();
                clearDateStyle();
                break;
            case R.id.MachineSure:
                ChangeListView(0);
                break;
            case R.id.MachineDel:
                ChangeListView(1);
                break;
            case R.id.MachineAdd:
                ChangeListView(2);
                break;
            case R.id.SecondBt:
                SecondChose(1);
                break;
            case R.id.MinBt:
                MinChose(1);
                break;
            case R.id.TimeBt:
                TimeChose(1);
                break;
            case R.id.Split1:
                AddSplit(1);
                break;
            case R.id.Split2:
                AddSplit(2);
                break;
            case R.id.Split3:
                AddSplit(3);
                break;
            case R.id.Split4:
                AddSplit(4);
                break;
            case R.id.DelTime:
                clearDateStyle();
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            LoadJsonToMemory();
        }
    }

    private void LoadJsonToMemory() {
        String dateCode = saveJsonToMemory.LoadTimeCodeToMemory();
        timeStyleString.clear();
        JSONArray CodeAr = JSONArray.parseArray(dateCode);
        if (CodeAr == null) {
            return;
        }
        for (int i = 0; i < CodeAr.size(); i++) {
            JSONObject jsonObject = CodeAr.getJSONObject(i);
            timeStyleString.add(jsonObject.getString("timeStyle"));
        }
        timeStyleAdapter.notifyDataSetChanged();
    }

    private void ChangeListView(int i) {
        switch (i) {
            case 0:
                SaveTimeStyle();
                break;
            case 1:
                delTimeStyle();
                break;
            case 2:
                addTimeStyle();
                break;
        }
    }

    private void SaveTimeStyle() {
        JSONArray dateStyleArray = new JSONArray();
        for (int i = 0; i < timeStyleString.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("timeStyle", timeStyleString.get(i));
            dateStyleArray.add(jsonObject);
        }
        saveJsonToMemory.SaveTimeCode(dateStyleArray.toString());
        ToastUtils.ToastShow(getString(R.string.SaveSuccessfully));
        TimeStyle.setText("");
        clearDateStyle();
        HideThis();
    }

    private void addTimeStyle() {
        String xx = TimeStyle.getText().toString().trim();
        if (xx.equals("")) {
            ToastUtils.ToastShow(getString(R.string.SelectTime));
            return;
        } else {
            timeStyleString.add(xx);
            timeStyleAdapter.notifyDataSetChanged();
            clearDateStyle();
        }
    }

    private void delTimeStyle() {
        if (nowPosition == -1) {
            ToastUtils.ToastShow(getString(R.string.SelectDelTme));
            return;
        } else {
            timeStyleString.remove(nowPosition);
            timeStyleAdapter.notifyDataSetChanged();
            nowPosition = -1;
            timeStyleAdapter.setClick(false);
        }
    }

    private void HideThis() {
        MachineParamarsFragment.getInstance().showHindMain(2);
    }

    private void clearDateStyle() {
        TimeStyle.setText("");
        DelTime.setVisibility(View.INVISIBLE);
//        TimeChose(0);
//        MinChose(0);
//        SecondChose(0);
    }

    private void AddSplit(int i) {
        switch (i) {
            case 1:
                TimeStyle.setText(TimeStyle.getText() + "/");
                break;
            case 2:
                TimeStyle.setText(TimeStyle.getText() + "-");
                break;
            case 3:
                TimeStyle.setText(TimeStyle.getText() + ".");
                break;
            case 4:
                break;
        }
    }

    private void TimeChose(int i) {
        DelTime.setVisibility(View.VISIBLE);
        TimeStyle.setText(TimeStyle.getText() + "HH");
    }

    private void TimeChoseOld(int i) {
        if (DelTime.getVisibility() == View.INVISIBLE && i != 0) {
            DelTime.setVisibility(View.VISIBLE);
        }
        TimeBt.setBackground(getResources().getDrawable(R.drawable.btn_check_bg));
        if (i == 1) {
            TimeBt.setBackground(getResources().getDrawable(R.drawable.btn_check_chose));
        }
        if (ChoseTime == i) {
            return;
        }
        if (i != 0)
            TimeStyle.setText(TimeStyle.getText() + "HH");
        ChoseTime = i;
    }

    private void MinChose(int i) {
        DelTime.setVisibility(View.VISIBLE);
        TimeStyle.setText(TimeStyle.getText() + "mm");
    }

    private void MinChoseOld(int i) {
        if (DelTime.getVisibility() == View.INVISIBLE && i != 0) {
            DelTime.setVisibility(View.VISIBLE);
        }
        MinBt.setBackground(getResources().getDrawable(R.drawable.btn_check_bg));
        if (i == 1) {
            MinBt.setBackground(getResources().getDrawable(R.drawable.btn_check_chose));
        }
        if (ChoseMin == i) {
            return;
        }
        if (i != 0) TimeStyle.setText(TimeStyle.getText() + "mm");

        ChoseMin = i;
    }

    private void SecondChose(int i) {
        DelTime.setVisibility(View.VISIBLE);
        TimeStyle.setText(TimeStyle.getText() + "ss");
    }

    private void SecondChoseOld(int i) {
        if (DelTime.getVisibility() == View.INVISIBLE && i != 0) {
            DelTime.setVisibility(View.VISIBLE);
        }
        SecondBt.setBackground(getResources().getDrawable(R.drawable.btn_check_bg));
        if (i == 1) {
            SecondBt.setBackground(getResources().getDrawable(R.drawable.btn_check_chose));
        }
        if (i == ChoseSecond) {
            return;
        }
        if (i != 0) TimeStyle.setText(TimeStyle.getText() + "ss");
        ChoseSecond = i;
    }

    private class TimeStyleAdapter extends BaseAdapter {

        private List<String> DateStyleString;
        private Context context;
        private LayoutInflater inflater;
        private int mCurrentItem = 0;
        private boolean isClick = false;

        public TimeStyleAdapter(List<String> dateStyleString, Context context) {
            DateStyleString = dateStyleString;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return DateStyleString.size();
        }

        @Override
        public Object getItem(int position) {
            return DateStyleString.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.tv_90, null);
            TextView textView = (TextView) view;
            textView.setText((position + 1) + "." + DateStyleString.get(position));
            if (mCurrentItem == position && isClick) {
                textView.setBackgroundColor(Color.parseColor("#ee2245A7"));
            } else {
                textView.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            return view;
        }

        public void setCurrentItem(int currentItem) {
            this.mCurrentItem = currentItem;
        }

        public void setClick(boolean click) {
            this.isClick = click;
        }
    }
}
