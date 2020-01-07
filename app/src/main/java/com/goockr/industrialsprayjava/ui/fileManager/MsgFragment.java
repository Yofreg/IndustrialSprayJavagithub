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

import com.goockr.industrialsprayjava.MainActivity;
import com.goockr.industrialsprayjava.MyApplication;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MsgFragment extends BaseFragment {


    @BindView(R.id.MsgListView)
    ListView MsgListView;
    @BindView(R.id.ImgCancel)
    Button ImgCancel;
    @BindView(R.id.ChoseMsg)
    Button ChoseMsg;
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

    public MsgFragment() {
        // Required empty public constructor
    }

    private Context context;
    private String MsgPath = MyApplication.getContext().getFilesDir().toString() + "/";
    private FileAdapter msgInfoAdapter;
    private FileAdapter msgInfoAdapterImports;
    private List<FileItem> msgInfoItems;
    private List<FileItem> msgInfoItemsImports;
    private SimpleDateFormat dateFormat;
    private DecimalFormat df;
    private ConstraintSet set;
    private PromptDialog promptDialog;
    private int REQUESTCODE_FROM_ACTIVITY = 1000;
    private SimpleDateFormat df1 = new SimpleDateFormat("MM-dd HH-mm-ss");
    private DecimalFormat df11 = new DecimalFormat("0.00");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        context = getContext();
        set = new ConstraintSet();
        set.clone(Conss);

        initView();
        return view;
    }

    private void initView() {
        msgInfoItems = new ArrayList<>();
        msgInfoItemsImports = new ArrayList<>();
        msgInfoAdapter = new FileAdapter(context, msgInfoItems);
        MsgListView.setAdapter(msgInfoAdapter);
        msgInfoAdapterImports = new FileAdapter(context, msgInfoItemsImports);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        promptDialog = new PromptDialog(getActivity());
        df = new DecimalFormat("0.00");
        traverseFolder1(MsgPath);
    }

    private void traverseFolder1(String path) {
        File file = new File(path);
        msgInfoItems.clear();
        if (!file.exists()) file.mkdirs();
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.getName().contains(".msg")) {
                    msgInfoItems.add(new FileItem(file2.getName(), dateFormat.format(file2.lastModified()), file2.getPath(), df.format(file2.length() / 1000.0) + "k"));
                }
            }
        }
        if (msgInfoItems.size() == 0) {
            ToastUtils.ToastShow(getString(R.string.InformationFound));
            PreferenceUtils.getInstance().setMsgCount(msgInfoItems.size() + "");
            msgInfoAdapter.notifyDataSetChanged();
            return;
        }
        PreferenceUtils.getInstance().setMsgCount(msgInfoItems.size() + "");
        msgInfoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            traverseFolder1(MsgPath);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ImgCancel, R.id.ChoseMsg, R.id.ImgImport, R.id.ImgExport, R.id.ImgDel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ImgCancel:
                FileManagerFragment.getInstance().showHind(0);
                break;
            case R.id.ChoseMsg:
                ChoseMsgShow();
                break;
            case R.id.ImgImport:
                if (Integer.parseInt(PreferenceUtils.getInstance().msgCount) > 200) {
                    ToastUtils.ToastShow(getString(R.string.InformationLimit));
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

    private void ChoseMsgShow() {
        int x = -1;
        int y = 0;
        for (int i = 0; i < msgInfoItems.size(); i++) {
            if (msgInfoItems.get(i).isCheck()) {
                x = i;
                y++;
            }
        }
        if (y > 1) {
            ToastUtils.ToastShow(getString(R.string.ChooseOne));
            return;
        }
        if (x == -1) {
            ToastUtils.ToastShow(getString(R.string.SelectMsgShow));
            return;
        }
        String[] xx = msgInfoItems.get(x).getName().split("\\.");
        PreferenceUtils.getInstance().setDefaultMsg(xx[0]);
        MainActivity.getInstance().Chose(1);
    }

    private void deleFile() {
        List<FileItem> fileItems = new ArrayList<>();
        for(int i=0;i<msgInfoItems.size();i++) {
            if (msgInfoItems.get(i).isCheck()) {
                fileItems.add(msgInfoItems.get(i));
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
                traverseFolder1(BaseUtils.codePath);
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


        FileUtils.getInstance(context).copyFileToSD(msgInfoItems, "codeimg").setFileOperateCallback(new FileUtils.FileOperateCallback() {
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
                .withSupportFragment(MsgFragment.this)
                .withBackgroundColor("#868686")
                .withMutilyMode(true)
                .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                .withFileFilter(new String[]{".msg"})
                .withStartPath("/mnt/usb_storage/USB_DISK1/udisk0/")//指定初始显示路径
                .withIsGreater(false)//过滤文件大小 小于指定大小的文件
                .withFileSize(500 * 1024)//指定文件大小为500K
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
                msgInfoItemsImports.add(fileItem);
            }
            copyCodeImg();
        }
    }

    private void readForSdCard() {
        File file = new File(BaseUtils.CodeImportPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                ToastUtils.ToastShow("创建文件夹失败,请手动在U盘创建 /industrial/codeimg 路径");
                return;
            }
        }
        traverseFolder2(BaseUtils.CodeImportPath);
        if (msgInfoItemsImports.size() == 0) {
            ToastUtils.ToastShow("未找到信息文件,请将字体文件放入到 /industrial/codeimg 文件夹下");
            return;
        }
        msgInfoAdapterImports.notifyDataSetChanged();
    }

    public void traverseFolder2(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                ToastUtils.ToastShow("文件夹是空的");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.getName().toLowerCase().contains(".msg")) {
                        msgInfoItemsImports.add(new FileItem(file2.getName(), dateFormat.format(file2.lastModified()), file2.getPath(), df.format(file2.length() / 1000.0) + "k"));
                    }
                }
            }
        } else {
            ToastUtils.ToastShow("/industrial/codeimg 文件夹不存在");
        }
    }

    private void copyCodeImg() {
        promptDialog.showLoading(getString(R.string.WaitForCopy));
        FileUtils.getInstance(context).copyMsgToSD(msgInfoItemsImports, "codeimg").setFileOperateCallback(new FileUtils.FileOperateCallback() {
            @Override
            public void onSuccess() {
                promptDialog.dismiss();
                ToastUtils.ToastShow(getString(R.string.FileCopyOk));
                traverseFolder1(BaseUtils.codePath);
            }

            @Override
            public void onFailed(String error) {
                GoockrLog.LogInfo("onFailed");
            }
        });
    }
}
