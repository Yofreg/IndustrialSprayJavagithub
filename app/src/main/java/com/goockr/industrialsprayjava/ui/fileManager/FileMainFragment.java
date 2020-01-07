package com.goockr.industrialsprayjava.ui.fileManager;


import android.os.Bundle;
import android.support.constraint.Guideline;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.goockr.industrialsprayjava.ui.FileManagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FileMainFragment extends BaseFragment {


    @BindView(R.id.InfoNowCount)
    TextView InfoNowCount;
    @BindView(R.id.InfoMaxCount)
    TextView InfoMaxCount;
    @BindView(R.id.ImgNowCount)
    TextView ImgNowCount;
    @BindView(R.id.ImgMaxCount)
    TextView ImgMaxCount;
    @BindView(R.id.FontNowCount)
    TextView FontNowCount;
    @BindView(R.id.FontMaxCount)
    TextView FontMaxCount;
    @BindView(R.id.OtherNowCount)
    TextView OtherNowCount;
    @BindView(R.id.OtherMaxCount)
    TextView OtherMaxCount;
    @BindView(R.id.MyTablayout)
    TableLayout MyTablayout;
    @BindView(R.id.BtInfo)
    Button BtInfo;
    @BindView(R.id.BtImg)
    Button BtImg;
    @BindView(R.id.BtTypeFace)
    Button BtTypeFace;
    @BindView(R.id.BtOther)
    Button BtOther;
    @BindView(R.id.guideline)
    Guideline guideline;
    Unbinder unbinder;

    private static FileMainFragment instance;

    public FileMainFragment() {
        // Required empty public constructor
    }

    public static FileMainFragment getInstance() {
        if (instance == null) {
            instance = new FileMainFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_file_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;
    }

    private void initView() {
        FontNowCount.setText(PreferenceUtils.getInstance().typefaceCount);
        ImgNowCount.setText(PreferenceUtils.getInstance().ImgCount);
        InfoNowCount.setText(PreferenceUtils.getInstance().msgCount);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.BtInfo, R.id.BtImg, R.id.BtTypeFace, R.id.BtOther})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.BtInfo:
                FileManagerFragment.getInstance().showHind(1);
                break;
            case R.id.BtImg:
                FileManagerFragment.getInstance().showHind(2);
                break;
            case R.id.BtTypeFace:
                FileManagerFragment.getInstance().showHind(3);
                break;
            case R.id.BtOther:
                FileManagerFragment.getInstance().showHind(4);
                break;
        }
    }
}
