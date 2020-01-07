package com.goockr.industrialsprayjava.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by yeyu on 2018/1/10.
 */

public class BaseActivity extends MyBaseActivity {
    protected String ACTION_EXIT = "com.mole.xt_android.exit";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //开始不弹输入框
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        registerReceiver(exitBroadCastReceiver, new IntentFilter(ACTION_EXIT));
//        StatusBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(exitBroadCastReceiver);
    }

    private BroadcastReceiver exitBroadCastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_EXIT)) {
                ((Activity) context).finish();
                return;
            }
        }
    };

    protected void EnterInterfaceAndFinish(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);

    }

}
