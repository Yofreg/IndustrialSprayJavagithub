package com.goockr.industrialsprayjava.ui.machine;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.goockr.industrialsprayjava.MainActivity;
import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.bean.InkpotInfoItem;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
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
public class ConsumableInfoFragment extends BaseFragment {


    @BindView(R.id.ConsumableInfoCancel)
    Button ConsumableInfoCancel;
    @BindView(R.id.ConsumableInfoSure)
    Button ConsumableInfoSure;
    Unbinder unbinder;
    @BindView(R.id.InkpotListView)
    RecyclerView InkpotListView;

    public ConsumableInfoFragment() {
        // Required empty public constructor
    }

    private InkpotAdapter inkpotAdapter;
    private List<InkpotInfoItem> inkpotInfoItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consumable_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();


        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            updateInk();
        }
    }

    private void initView() {
        inkpotInfoItems = new ArrayList<>();
        int spanCount = 4; // 只显示一行
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        InkpotListView.setLayoutManager(layoutManager);
        inkpotAdapter = new InkpotAdapter(inkpotInfoItems);
        InkpotListView.setAdapter(inkpotAdapter);

        MainActivity.getInstance().getInkInfo(2);
    }

    public void updateInk() {
        inkpotInfoItems.clear();
        String inkBox1 = PreferenceUtils.getInstance().inkBox1;
        String inkBox2 = PreferenceUtils.getInstance().inkBox2;
        String inkBox3 = PreferenceUtils.getInstance().inkBox3;
        String inkBox4 = PreferenceUtils.getInstance().inkBox4;
        String inkBox5 = PreferenceUtils.getInstance().inkBox5;
        String inkBox6 = PreferenceUtils.getInstance().inkBox6;
        String inkBox7 = PreferenceUtils.getInstance().inkBox7;
        String inkBox8 = PreferenceUtils.getInstance().inkBox8;
        if (!TextUtils.isEmpty(inkBox1)) {
            JSONObject object =  JSONObject.parseObject(inkBox1);
            inkpotInfoItems.add(new InkpotInfoItem(object.getString("inkPotNo"), object.getString("inkpot1"), object.getString("inkpot2"), object.getString("inkpot3"), object.getString("inkpot4"), object.getString("inkpot5")));
        }
        if (!TextUtils.isEmpty(inkBox2)) {
            JSONObject object =  JSONObject.parseObject(inkBox2);
            inkpotInfoItems.add(new InkpotInfoItem(object.getString("inkPotNo"), object.getString("inkpot1"), object.getString("inkpot2"), object.getString("inkpot3"), object.getString("inkpot4"), object.getString("inkpot5")));
        }
        if (!TextUtils.isEmpty(inkBox3)) {
            JSONObject object =  JSONObject.parseObject(inkBox3);
            inkpotInfoItems.add(new InkpotInfoItem(object.getString("inkPotNo"), object.getString("inkpot1"), object.getString("inkpot2"), object.getString("inkpot3"), object.getString("inkpot4"), object.getString("inkpot5")));
        }
        if (!TextUtils.isEmpty(inkBox4)) {
            JSONObject object =  JSONObject.parseObject(inkBox4);
            inkpotInfoItems.add(new InkpotInfoItem(object.getString("inkPotNo"), object.getString("inkpot1"), object.getString("inkpot2"), object.getString("inkpot3"), object.getString("inkpot4"), object.getString("inkpot5")));
        }
        if (!TextUtils.isEmpty(inkBox5)) {
            JSONObject object =  JSONObject.parseObject(inkBox5);
            inkpotInfoItems.add(new InkpotInfoItem(object.getString("inkPotNo"), object.getString("inkpot1"), object.getString("inkpot2"), object.getString("inkpot3"), object.getString("inkpot4"), object.getString("inkpot5")));
        }
        if (!TextUtils.isEmpty(inkBox6)) {
            JSONObject object =  JSONObject.parseObject(inkBox6);
            inkpotInfoItems.add(new InkpotInfoItem(object.getString("inkPotNo"), object.getString("inkpot1"), object.getString("inkpot2"), object.getString("inkpot3"), object.getString("inkpot4"), object.getString("inkpot5")));
        }
        if (!TextUtils.isEmpty(inkBox7)) {
            JSONObject object =  JSONObject.parseObject(inkBox7);
            inkpotInfoItems.add(new InkpotInfoItem(object.getString("inkPotNo"), object.getString("inkpot1"), object.getString("inkpot2"), object.getString("inkpot3"), object.getString("inkpot4"), object.getString("inkpot5")));
        }
        if (!TextUtils.isEmpty(inkBox8)) {
            JSONObject object =  JSONObject.parseObject(inkBox8);
            inkpotInfoItems.add(new InkpotInfoItem(object.getString("inkPotNo"), object.getString("inkpot1"), object.getString("inkpot2"), object.getString("inkpot3"), object.getString("inkpot4"), object.getString("inkpot5")));
        }
        if (inkpotInfoItems.size() == 0) {
            ToastUtils.ToastShow(getString(R.string.NoCartridge));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ConsumableInfoCancel, R.id.ConsumableInfoSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ConsumableInfoCancel:
                MachineParamarsFragment.getInstance().showHindMain(5);
                break;
            case R.id.ConsumableInfoSure:
                MachineParamarsFragment.getInstance().showHindMain(5);
                break;
        }
    }


    private class InkpotAdapter extends RecyclerView.Adapter<InkpotAdapter.MyHolder> {
        private List<InkpotInfoItem> inkpotInfoItems;

        public InkpotAdapter(List<InkpotInfoItem> inkpotInfoItems) {
            this.inkpotInfoItems = inkpotInfoItems;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inkpot_item, null);
            MyHolder myHolder = new MyHolder(view);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            InkpotInfoItem inkpotInfoItem = inkpotInfoItems.get(position);
            holder.InkpotNo.setText(getString(R.string.Jet) + inkpotInfoItem.getInkPotNo());
            holder.Inkpot1.setText(getString(R.string.InkModel) + inkpotInfoItem.getInkpot1());
            holder.Inkpot2.setText(getString(R.string.InkCode) + inkpotInfoItem.getInkpot2());
            holder.Inkpot3.setText(getString(R.string.InkVolume)+ inkpotInfoItem.getInkpot3() + " %");
            holder.Inkpot4.setText(getString(R.string.ProductionDate)+inkpotInfoItem.getInkpot4());
            holder.Inkpot5.setText(getString(R.string.EffectiveDate) + inkpotInfoItem.getInkpot5());
        }

        @Override
        public int getItemCount() {
            return inkpotInfoItems.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView InkpotNo;
            TextView Inkpot1, Inkpot2, Inkpot3, Inkpot4, Inkpot5;

            public MyHolder(View itemView) {
                super(itemView);
                InkpotNo = itemView.findViewById(R.id.InkpotNo);
                Inkpot1 = itemView.findViewById(R.id.InkpotInfo1);
                Inkpot2 = itemView.findViewById(R.id.InkpotInfo2);
                Inkpot3 = itemView.findViewById(R.id.InkpotInfo3);
                Inkpot4 = itemView.findViewById(R.id.InkpotInfo4);
                Inkpot5 = itemView.findViewById(R.id.InkpotInfo5);
            }
        }


    }

}
