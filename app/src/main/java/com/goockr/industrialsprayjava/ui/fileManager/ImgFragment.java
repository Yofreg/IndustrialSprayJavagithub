package com.goockr.industrialsprayjava.ui.fileManager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.adapter.FileAdapter;
import com.goockr.industrialsprayjava.base.BaseFragment;
import com.goockr.industrialsprayjava.bean.FileItem;
import com.goockr.industrialsprayjava.tools.BaseUtils;
import com.goockr.industrialsprayjava.tools.FileUtils;
import com.goockr.industrialsprayjava.tools.GoockrLog;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;
import com.goockr.industrialsprayjava.tools.ToastUtils;
import com.goockr.industrialsprayjava.ui.FileManagerFragment;
import com.leon.lfilepickerlibrary.LFilePicker;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class ImgFragment extends BaseFragment {


    @BindView(R.id.FontTypefaceListView)
    ListView FontTypefaceListView;
    @BindView(R.id.ImgCancel)
    Button ImgCancel;
    @BindView(R.id.ImgImport)
    Button ImgImport;
    @BindView(R.id.ImgExport)
    Button ImgExport;
    @BindView(R.id.ImgDel)
    Button ImgDel;
    @BindView(R.id.tvvv)
    LinearLayout tvvv;
    @BindView(R.id.Conss)
    ConstraintLayout Conss;
    Unbinder unbinder;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;

    public ImgFragment() {
        // Required empty public constructor
    }


    private Context context;
    private FileAdapter imgInfoAdapter;
    private FileAdapter imgInfoAdapterImports;
    private List<FileItem> imgInfoItems;
    private List<FileItem> imgInfoItemImports;
    private ConstraintSet set;
    private SimpleDateFormat dateFormat;
    private DecimalFormat df;
    private PromptDialog promptDialog;
    private int REQUESTCODE_FROM_ACTIVITY = 1000;
    private SimpleDateFormat df1 = new SimpleDateFormat("MM-dd HH-mm-ss");
    private DecimalFormat df11 = new DecimalFormat("0.00");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_img, container, false);
        unbinder = ButterKnife.bind(this, view);

        context = getContext();
        set = new ConstraintSet();
        set.clone(Conss);

        initView();


        return view;
    }

    private void initView() {
        imgInfoItems = new ArrayList<>();
        imgInfoItemImports = new ArrayList<>();
        imgInfoAdapter = new FileAdapter(context, imgInfoItems);
        FontTypefaceListView.setAdapter(imgInfoAdapter);

        imgInfoAdapterImports = new FileAdapter(context, imgInfoItemImports);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        df = new DecimalFormat("0.00");

        promptDialog = new PromptDialog(getActivity());


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            traverseFolder1(BaseUtils.ImgInfoPath);
        }
    }

    private void traverseFolder1(String path) {
        File file = new File(path);
        imgInfoItems.clear();
        if (!file.exists()) file.mkdirs();
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            if (files.length == 0) {
                ToastUtils.ToastShow(getString(R.string.FolderEmpty));
                return;
            } else for (File file2 : files) {
                if (file2.getName().toLowerCase().contains(".bmp")) {
                    imgInfoItems.add(new FileItem(file2.getName(), dateFormat.format(file2.lastModified()), file2.getPath(), df.format(file2.length() / 1000.0) + "k"));
                }
            }
        }
        if (imgInfoItems.size() == 0) {
            ToastUtils.ToastShow(getString(R.string.NoPattern));
            PreferenceUtils.getInstance().setImgCount(imgInfoItems.size() + "");
            imgInfoAdapter.notifyDataSetChanged();
            return;
        }
        PreferenceUtils.getInstance().setImgCount(imgInfoItems.size() + "");
        imgInfoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ImgCancel, R.id.ImgExport, R.id.ImgDel, R.id.ImgImport})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ImgCancel:
                FileManagerFragment.getInstance().showHind(0);
                break;
            case R.id.ImgImport:
                if (Integer.parseInt(PreferenceUtils.getInstance().ImgCount) > 45) {
                    ToastUtils.ToastShow(getString(R.string.PatternLimit));
                    return;
                }
                ShowImportView();
                break;
            case R.id.ImgExport:
                Export();
                break;
            case R.id.ImgDel:
                deleFile();
                break;
        }
    }

    private void deleFile() {
        List<FileItem> fileItems = new ArrayList<>();
        for (int i = 0; i < imgInfoItems.size(); i++) {
            if (imgInfoItems.get(i).isCheck()) {
                fileItems.add(imgInfoItems.get(i));
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
                traverseFolder1(BaseUtils.ImgInfoPath);
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
        FileUtils.getInstance(context).copyFileToSD(imgInfoItems, "img").setFileOperateCallback(new FileUtils.FileOperateCallback() {
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
                .withSupportFragment(ImgFragment.this)
                .withBackgroundColor("#868686")
                .withMutilyMode(true)
                .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                .withFileFilter(new String[]{".bmp"})
                .withStartPath("/mnt/usb_storage/USB_DISK1/udisk0/")//指定初始显示路径
                .withIsGreater(false)//过滤文件大小 小于指定大小的文件
                .withFileSize(1000 * 1024*20)//指定文件大小为1m*大小
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
                imgInfoItemImports.add(fileItem);
            }
            copyImg();
        }
    }


    private void copyImg() {
        promptDialog.showLoading(getString(R.string.WaitForCopy));
        FileUtils.getInstance(context).copyAssetsToSD(imgInfoItemImports, "img").setFileOperateCallback(new FileUtils.FileOperateCallback() {
            @Override
            public void onSuccess() {
                promptDialog.dismiss();
                ToastUtils.ToastShow(getString(R.string.FileCopyOk));
                traverseFolder1(BaseUtils.ImgInfoPath);
            }

            @Override
            public void onFailed(String error) {
                GoockrLog.LogInfo("onFailed");
            }
        });
    }


}
