package com.goockr.industrialsprayjava.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.bean.FileItem;

import java.util.List;

public class FileAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<FileItem> fileItems;

    public FileAdapter(Context context, List<FileItem> fileItems) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.fileItems = fileItems;
    }

    @Override
    public int getCount() {
        return fileItems.size();
    }

    @Override
    public Object getItem(int position) {
        return fileItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.import_typeface_item, null);
        FileItem fileItem = fileItems.get(position);
        Log.e("tag", fileItem.getName()+"  "+fileItem.getFilePath());
        final CheckBox checkBox = view.findViewById(R.id.isChose);
        checkBox.setChecked(fileItems.get(position).isCheck());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fileItems.get(position).setCheck(isChecked);
            }
        });
        TextView CreateTimeTextView = view.findViewById(R.id.CreateTime);
        TextView UpdateTime = view.findViewById(R.id.UpdateTime);

        CreateTimeTextView.setText(fileItem.getFileSize());
        UpdateTime.setText(fileItem.getUpdateTime());
        checkBox.setText(fileItem.getName());
        return view;
    }
}
