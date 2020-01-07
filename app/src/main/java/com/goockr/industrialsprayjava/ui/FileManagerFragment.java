package com.goockr.industrialsprayjava.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.ui.fileManager.FileMainFragment;
import com.goockr.industrialsprayjava.ui.fileManager.FontTypefaceFragment;
import com.goockr.industrialsprayjava.ui.fileManager.ImgFragment;
import com.goockr.industrialsprayjava.ui.fileManager.MsgFragment;
import com.goockr.industrialsprayjava.ui.fileManager.OtherFragment;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by lijinning on 2018/3/12.
 * 文件管理
 */

public class FileManagerFragment extends BaseFragment {


    private FileMainFragment fileMainFragment;
    private MsgFragment infoFragment;
    private ImgFragment imgFragment;
    private FontTypefaceFragment fontTypefaceFragment;
    private OtherFragment otherFragment;
    private List<SupportFragment> fragments;

    int FIRST = 0;
    int SECOND = 1;
    int THIRD = 2;
    int FOURTH = 3;
    int FIVE = 4;

    private int currentPotion = 0;
    private static FileManagerFragment instance;

    public static FileManagerFragment newInstance() {
        Bundle args = new Bundle();
        FileManagerFragment homeFragment = new FileManagerFragment();
        homeFragment.setArguments(args);
        instance = homeFragment;
        return homeFragment;
    }

    public static FileManagerFragment getInstance() {
        if (instance == null) {
            instance = new FileManagerFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_manager, container, false);

        initView();

        return view;

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            showHind(0);
        }
    }

    private void initView() {
        fileMainFragment = new FileMainFragment();
        infoFragment = new MsgFragment();
        imgFragment = new ImgFragment();
        fontTypefaceFragment = new FontTypefaceFragment();
        otherFragment = new OtherFragment();

        fragments = new ArrayList<>();
        FileMainFragment firstFragment = findFragment(FileMainFragment.class);
        if (firstFragment == null) {
            fragments.add(FIRST, fileMainFragment);
            fragments.add(SECOND, infoFragment);
            fragments.add(THIRD, imgFragment);
            fragments.add(FOURTH, fontTypefaceFragment);
            fragments.add(FIVE, otherFragment);

            loadMultipleRootFragment(R.id.fileMain, FIRST,
                    fragments.get(FIRST),
                    fragments.get(SECOND),
                    fragments.get(THIRD),
                    fragments.get(FOURTH),
                    fragments.get(FIVE));
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            fragments.add(FIRST, firstFragment);
            fragments.add(SECOND, findFragment(MsgFragment.class));
            fragments.add(THIRD, findFragment(ImgFragment.class));
            fragments.add(FOURTH, findFragment(FontTypefaceFragment.class));
            fragments.add(FIVE, findFragment(OtherFragment.class));
        }
    }

    public void showHindMain(int index) {
        showHideFragment(fragments.get(0), fragments.get(index));
        currentPotion = 0;
    }

    public void showHind(int index) {
        //主页
        if (currentPotion != index) {
            showHideFragment(fragments.get(index), fragments.get(currentPotion));
            currentPotion = index;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
