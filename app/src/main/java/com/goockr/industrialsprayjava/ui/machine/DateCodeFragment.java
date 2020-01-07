package com.goockr.industrialsprayjava.ui.machine;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.tools.GoockrLog;
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
public class DateCodeFragment extends BaseFragment {

    @BindView(R.id.tvvv)
    TextView tvvv;
    @BindView(R.id.MachineCancel)
    Button MachineCancel;
    @BindView(R.id.MachineSure)
    Button MachineSure;
    @BindView(R.id.MachineDel)
    Button MachineDel;
    @BindView(R.id.MachineAdd)
    Button MachineAdd;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.Day1Bt)
    Button Day1Bt;
    @BindView(R.id.Day2Bt)
    Button Day2Bt;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.Week1Bt)
    Button Week1Bt;
    @BindView(R.id.Week2Bt)
    Button Week2Bt;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.Month1Bt)
    Button Month1Bt;
    @BindView(R.id.Month2Bt)
    Button Month2Bt;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.Yeat1Bt)
    Button Yeat1Bt;
    @BindView(R.id.Yeat2Bt)
    Button Yeat2Bt;
    @BindView(R.id.Yeat3Bt)
    Button Yeat3Bt;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.Split1)
    Button Split1;
    @BindView(R.id.Split2)
    Button Split2;
    @BindView(R.id.Split3)
    Button Split3;
    @BindView(R.id.Split4)
    Button Split4;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.DateStyle)
    TextView DateStyle;
    @BindView(R.id.DateStyleListView)
    ListView DateStyleListView;
    Unbinder unbinder;
    @BindView(R.id.DelDate)
    ImageButton DelDate;

    private View view;
    private int ChoseYear = 0, ChoseMonth = 0, ChoseWeek = 0, ChoseDay = 0;
    private DateStyleAdapter dateStyleAdapter;
    private List<String> dateStyleString;
    private Context context;
    private int nowPosition = -1;

    private SaveJsonToMemory saveJsonToMemory;

    public DateCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_date_code, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();
        saveJsonToMemory = new SaveJsonToMemory();

        init();
        return view;
    }

    private void init() {
        dateStyleString = new ArrayList<>();
        dateStyleAdapter = new DateStyleAdapter(dateStyleString, context);
        DateStyleListView.setAdapter(dateStyleAdapter);
        DateStyleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nowPosition = position;
                dateStyleAdapter.setCurrentItem(position);
                dateStyleAdapter.setClick(true);
                dateStyleAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Split4, R.id.DelDate, R.id.MachineCancel, R.id.MachineSure, R.id.MachineDel, R.id.MachineAdd, R.id.Day1Bt, R.id.Day2Bt, R.id.Week1Bt, R.id.Week2Bt, R.id.Month1Bt, R.id.Month2Bt, R.id.Yeat1Bt, R.id.Yeat2Bt, R.id.Yeat3Bt, R.id.Split1, R.id.Split2, R.id.Split3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.MachineCancel:
                MachineParamarsFragment.getInstance().showHindMain(1);
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
            case R.id.Day1Bt:
                DayChose(1);
                break;
            case R.id.Day2Bt:
                DayChose(2);
                break;
            case R.id.Week1Bt:
                WeekChose(1);
                break;
            case R.id.Week2Bt:
                WeekChose(2);
                break;
            case R.id.Month1Bt:
                MonthChose(1);
                break;
            case R.id.Month2Bt:
                MonthChose(2);
                break;
            case R.id.Yeat1Bt:
                YearChose(1);
                break;
            case R.id.Yeat2Bt:
                YearChose(2);
                break;
            case R.id.Yeat3Bt:
                YearChose(3);
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
            case R.id.DelDate:
                //清除日期格式
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
        String dateCode = saveJsonToMemory.LoadDateCodeToMemory();
        dateStyleString.clear();
        JSONArray CodeAr = JSONArray.parseArray(dateCode);
        if (CodeAr == null) {
            return;
        }
        for (int i = 0; i < CodeAr.size(); i++) {
            JSONObject jsonObject = CodeAr.getJSONObject(i);
            dateStyleString.add(jsonObject.getString("dateStyle"));

        }
        dateStyleAdapter.notifyDataSetChanged();
    }

    private void ChangeListView(int i) {
        switch (i) {
            case 0:
                SaveDateStyle();
                break;
            case 1:
                delDateStyle();
                break;
            case 2:
                addDateStyle();
                break;
        }
    }

    private void SaveDateStyle() {
        JSONArray dateStyleArray = new JSONArray();

        for (int i = 0; i < dateStyleString.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dateStyle", dateStyleString.get(i));
            dateStyleArray.add(jsonObject);
        }
        saveJsonToMemory.SaveDateCode(dateStyleArray.toString());

        ToastUtils.ToastShow(getString(R.string.SaveSuccessfully));
        DateStyle.setText("");
        clearDateStyle();
        MachineParamarsFragment.getInstance().showHindMain(1);

    }

    private void addDateStyle() {
        String xx = DateStyle.getText().toString().trim();
        if (xx.equals("")) {
            ToastUtils.ToastShow(getString(R.string.selectDateFormat));
            return;
        } else {
            dateStyleString.add(xx);
            dateStyleAdapter.notifyDataSetChanged();
            clearDateStyle();
        }
    }

    private void delDateStyle() {
        if (nowPosition == -1) {
            ToastUtils.ToastShow(getString(R.string.SelectDelDateFormat));
            return;
        } else {
            dateStyleString.remove(nowPosition);
            dateStyleAdapter.notifyDataSetChanged();
            nowPosition = -1;
            dateStyleAdapter.setClick(false);
        }
    }


    private class DateStyleAdapter extends BaseAdapter {

        private List<String> DateStyleString;
        private Context context;
        private LayoutInflater inflater;
        private int mCurrentItem = 0;
        private boolean isClick = false;

        public DateStyleAdapter(List<String> dateStyleString, Context context) {
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

    private void clearDateStyle() {
        DateStyle.setText("");
        DelDate.setVisibility(View.INVISIBLE);
//        YearChose(0);
//        MonthChose(0);
//        WeekChose(0);
//        DayChose(0);
    }

    private void AddSplit(int i) {
        switch (i) {
            case 1:
                DateStyle.setText(DateStyle.getText() + "/");
                break;
            case 2:
                DateStyle.setText(DateStyle.getText() + "-");
                break;
            case 3:
                DateStyle.setText(DateStyle.getText() + ".");
                break;
            case 4:
                break;
        }
    }

    private void YearChose(int i) {
        DelDate.setVisibility(View.VISIBLE);
        if (i == 1) {
            DateStyle.setText(DateStyle.getText() + "YYYY");
        } else if (i == 2) {
            DateStyle.setText(DateStyle.getText() + "yy");
        } else if (i == 3) {
            DateStyle.setText(DateStyle.getText() + "JulianYear");
        }
    }

    private void YearChoseOld(int i) {
        if (DelDate.getVisibility() == View.INVISIBLE && i != 0) {
            DelDate.setVisibility(View.VISIBLE);
        }
        Yeat1Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_bg));
        Yeat2Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_bg));
        Yeat3Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_bg));
        if (i == 1) {
            Yeat1Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_chose));
        } else if (i == 2) {
            Yeat2Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_chose));
        } else if (i == 3) {
            Yeat3Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_chose));
        }

        if (ChoseYear == 0) {
            if (i == 1) {
                DateStyle.setText(DateStyle.getText() + "YYYY");
            } else if (i == 2) {
                DateStyle.setText(DateStyle.getText() + "YY");
            } else if (i == 3) {
                DateStyle.setText(DateStyle.getText() + "JulianYear");
            }
        } else {
            String xx = DateStyle.getText().toString().trim();

            if (ChoseYear == 1) {
                xx = xx.replace("YYYY", "");
            } else if (ChoseYear == 2) {
                xx = xx.replace("YY", "");
            } else if (ChoseYear == 3) {
                xx = xx.replace("JulianYear", "");
            }
            if (i == 1) {
                DateStyle.setText(xx + "YYYY");
            } else if (i == 2) {
                DateStyle.setText(xx + "YY");
            } else if (i == 3) {
                DateStyle.setText(xx + "JulianYear");
            }
        }
        ChoseYear = i;
    }

    private void MonthChose(int i) {
        DelDate.setVisibility(View.VISIBLE);
        if (i == 1) {
            DateStyle.setText(DateStyle.getText() + "MM");
        } else if (i == 2) {
            DateStyle.setText(DateStyle.getText() + "Month");
        }

    }

    private void MonthChoseold(int i) {
        if (DelDate.getVisibility() == View.INVISIBLE && i != 0) {
            DelDate.setVisibility(View.VISIBLE);
        }
        Month1Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_bg));
        Month2Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_bg));
        if (i == 1) {
            Month1Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_chose));
        } else if (i == 2) {
            Month2Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_chose));
        }

        if (ChoseMonth == 0) {
            if (i == 1) {
                DateStyle.setText(DateStyle.getText() + "MM");
            } else if (i == 2) {
                DateStyle.setText(DateStyle.getText() + "Month");
            }
        } else {
            String xx = DateStyle.getText().toString().trim();
            if (ChoseMonth == 1) {
                xx = xx.replace("MM", "");
            } else if (ChoseMonth == 2) {
                xx = xx.replace("Month", "");
            }

            if (i == 1) {
                DateStyle.setText(xx + "MM");
            } else if (i == 2) {
                DateStyle.setText(xx + "Month");
            }
        }
        ChoseMonth = i;
    }

    private void WeekChose(int i) {
        DelDate.setVisibility(View.VISIBLE);
        if (i == 1) {
            DateStyle.setText(DateStyle.getText() + "WN");
        } else if (i == 2) {
            DateStyle.setText(DateStyle.getText() + "Week");
        }
    }

    private void WeekChoseOld(int i) {
        if (DelDate.getVisibility() == View.INVISIBLE && i != 0) {
            DelDate.setVisibility(View.VISIBLE);
        }
        Week1Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_bg));
        Week2Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_bg));
        if (i == 1) {
            Week1Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_chose));
        } else if (i == 2) {
            Week2Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_chose));
        }

        if (ChoseWeek == 0) {
            if (i == 1) {
                DateStyle.setText(DateStyle.getText() + "WN");
            } else if (i == 2) {
                DateStyle.setText(DateStyle.getText() + "Week");
            }
        } else {
            String xx = DateStyle.getText().toString().trim();
            if (ChoseWeek == 1) {
                xx = xx.replace("WN", "");
            } else if (ChoseWeek == 2) {
                xx = xx.replace("Week", "");
            }

            if (i == 1) {
                DateStyle.setText(xx + "WN");
            } else if (i == 2) {
                DateStyle.setText(xx + "Week");
            }
        }
        ChoseWeek = i;

    }

    private void DayChose(int i) {
        DelDate.setVisibility(View.VISIBLE);
        if (i == 1) {
            DateStyle.setText(DateStyle.getText() + "dd");
        } else if (i == 2) {
            DateStyle.setText(DateStyle.getText() + "JulianDay");
        }
    }

    private void DayChoseold(int i) {
        if (DelDate.getVisibility() == View.INVISIBLE && i != 0) {
            DelDate.setVisibility(View.VISIBLE);
        }
        Day1Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_bg));
        Day2Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_bg));
        if (i == 1) {
            Day1Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_chose));
        } else if (i == 2) {
            Day2Bt.setBackground(getResources().getDrawable(R.drawable.btn_check_chose));
        }

        if (ChoseDay == 0) {
            if (i == 1) {
                DateStyle.setText(DateStyle.getText() + "DD");
            } else if (i == 2) {
                DateStyle.setText(DateStyle.getText() + "JulianDay");
            }
        } else {
            String xx = DateStyle.getText().toString().trim();
            if (ChoseDay == 1) {
                xx = xx.replace("DD", "");
            } else if (ChoseDay == 2) {
                xx = xx.replace("JulianDay", "");
            }

            if (i == 1) {
                DateStyle.setText(xx + "DD");
            } else if (i == 2) {
                DateStyle.setText(xx + "JulianDay");
            }
        }
        ChoseDay = i;
    }


}
