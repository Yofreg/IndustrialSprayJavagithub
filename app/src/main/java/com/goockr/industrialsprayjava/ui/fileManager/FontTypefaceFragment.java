package com.goockr.industrialsprayjava.ui.fileManager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.adapter.FileAdapter;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.bean.FileItem;
import com.goockr.industrialsprayjava.tools.BaseUtils;
import com.goockr.industrialsprayjava.tools.FileUtils;
import com.goockr.industrialsprayjava.tools.GoockrLog;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.goockr.industrialsprayjava.tools.ThreadUtils;
import com.goockr.industrialsprayjava.tools.ToastUtils;
import com.goockr.industrialsprayjava.ui.FileManagerFragment;
import com.leon.lfilepickerlibrary.LFilePicker;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class FontTypefaceFragment extends BaseFragment {


    @BindView(R.id.FontTypefaceListView)
    ListView FontTypefaceListView;
    @BindView(R.id.FontTypeFaceCancel)
    Button FontTypeFaceCancel;
    @BindView(R.id.FontTypeFaceImport)
    Button FontTypeFaceImport;
    @BindView(R.id.FontTypeFaceExport)
    Button FontTypeFaceExport;
    @BindView(R.id.FontTypeFaceDel)
    Button FontTypeFaceDel;
    Unbinder unbinder;
    @BindView(R.id.Conss)
    ConstraintLayout Conss;
    @BindView(R.id.tvvv)
    LinearLayout tvvv;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;

    private Context context;
    private FileAdapter fontTypefaceAdapter;
    private FileAdapter fontTypefaceImportAdapter;
    private List<FileItem> fontTyprfaces;
    private List<FileItem> fontTyprfacesImport;
    private ConstraintSet set;
    private SimpleDateFormat dateFormat;
    private DecimalFormat df;

    private String typefacePath = "";

    private PromptDialog promptDialog;
    private int REQUESTCODE_FROM_ACTIVITY = 1000;
    private SimpleDateFormat df1 = new SimpleDateFormat("MM-dd HH-mm-ss");
    private DecimalFormat df11 = new DecimalFormat("0.00");

    public FontTypefaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_font_typeface, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();
        set = new ConstraintSet();
        set.clone(Conss);

        initView();

        return view;
    }

    private void initView() {
        fontTyprfaces = new ArrayList<>();
        fontTyprfacesImport = new ArrayList<>();
        fontTypefaceAdapter = new FileAdapter(context, fontTyprfaces);
        FontTypefaceListView.setAdapter(fontTypefaceAdapter);

        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        df = new DecimalFormat("0.00");

        promptDialog = new PromptDialog(getActivity());

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            traverseFolder1(BaseUtils.TypefacePath);
        }
    }

    private void traverseFolder1(String path) {
        fontTyprfaces.clear();
        File file = new File(path);
        if (!file.exists()) file.mkdirs();
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                ToastUtils.ToastShow(getString(R.string.FolderEmpty));
                return;
            } else {
                for (File file2 : files) {
                    if (file2.getName().toLowerCase().contains(".ttf")||file2.getName().toLowerCase().contains(".fon")) {
                        fontTyprfaces.add(new FileItem(file2.getName(), dateFormat.format(file2.lastModified()), file2.getPath(), df.format(file2.length() / 1000.0) + "k"));
                    }
                }
            }
        }
        if (fontTyprfaces.size() == 0) {
            ToastUtils.ToastShow(getString(R.string.NotFindFont));
            PreferenceUtils.getInstance().setTypefaceCount(fontTyprfaces.size() + "");
            fontTypefaceAdapter.notifyDataSetChanged();
            return;
        }
        PreferenceUtils.getInstance().setTypefaceCount(fontTyprfaces.size() + "");
        fontTypefaceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.FontTypeFaceCancel, R.id.FontTypeFaceImport, R.id.FontTypeFaceExport, R.id.FontTypeFaceDel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.FontTypeFaceCancel:
                FileManagerFragment.getInstance().showHind(0);
                break;
            case R.id.FontTypeFaceImport:
                if (Integer.parseInt(PreferenceUtils.getInstance().typefaceCount) > 30) {
                    ToastUtils.ToastShow(getString(R.string.FontLimit));
                    return;
                }
                ShowImportView();
                break;
            case R.id.FontTypeFaceExport:
                Export();
                break;
            case R.id.FontTypeFaceDel:
                deleFile();
                break;
        }
    }

    private void deleFile() {
        List<FileItem> fileItems = new ArrayList<>();
        for(int i=0;i<fontTyprfaces.size();i++) {
            if (fontTyprfaces.get(i).isCheck()) {
                fileItems.add(fontTyprfaces.get(i));
            }
        }
        if (fileItems.size() == 0) {
            ToastUtils.ToastShow(getString(R.string.ChoseDelFile));
            return;
        }
        promptDialog.showLoading(getString(R.string.WaitForDel));
        FileUtils.getInstance(context).deleteFile(fileItems).setFileOperateCallback(new FileUtils.FileOperateCallback() {
            @Override
            public void onSuccess() {
                ToastUtils.ToastShow(getString(R.string.FileDel));
                promptDialog.dismiss();
                traverseFolder1(BaseUtils.TypefacePath);
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    private void Export() {
        File file = new File(BaseUtils.ImportPath);
        if (!file.exists()) {
            ToastUtils.ToastShow(getString(R.string.UdiskDetected));
            return;
        }
        promptDialog.showLoading(getString(R.string.WairForExport));
        FileUtils.getInstance(context).copyFileToSD(fontTyprfaces, "typeface").setFileOperateCallback(new FileUtils.FileOperateCallback() {
            @Override
            public void onSuccess() {
                promptDialog.dismiss();
                ToastUtils.ToastShow(getString(R.string.FileExportOk));
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    private void ShowImportView() {
        File file = new File(BaseUtils.ImportPath);
        if (!file.exists()) {
            ToastUtils.ToastShow(getString(R.string.UdiskDetected));
            return;
        }

        new LFilePicker()
                .withSupportFragment(FontTypefaceFragment.this)
                .withBackgroundColor("#868686")
                .withMutilyMode(true)
                .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                .withFileFilter(new String[]{".ttf",".fon"})
                .withStartPath("/mnt/usb_storage/USB_DISK1/udisk0/")//指定初始显示路径
                .withIsGreater(false)//过滤文件大小 小于指定大小的文件
                .withFileSize(1000 * 1024*20)//指定文件大小为500K
                .start();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESTCODE_FROM_ACTIVITY) {
            List<String> list = data.getStringArrayListExtra("paths");
            for(int i=0;i<list.size();i++) {
                File file = new File(list.get(i));
                FileItem fileItem = new FileItem(file.getName(), df1.format(file.lastModified()), file.getPath(), df11.format(file.length() / 1000.0) + "k");
                fileItem.setCheck(true);
                fontTyprfacesImport.add(fileItem);
            }
            copyTypeface();
        }
    }


    private void copyTypeface() {
        promptDialog.showLoading(getString(R.string.WaitForCopy));
        FileUtils.getInstance(context).copyAssetsToSD(fontTyprfacesImport, "typeface").setFileOperateCallback(new FileUtils.FileOperateCallback() {
            @Override
            public void onSuccess() {
                promptDialog.dismiss();
                ToastUtils.ToastShow(getString(R.string.FileCopyOk));
                traverseFolder1(BaseUtils.TypefacePath);
            }

            @Override
            public void onFailed(String error) {
                GoockrLog.LogInfo("onFailed");
            }
        });
    }
}
