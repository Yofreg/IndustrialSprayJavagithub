package com.goockr.industrialsprayjava.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

/**
 * @author yofreg
 * @time 2018/7/2 11:33
 * @desc activity父类
 */
public class MyBaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {

        super.onStart();
        //实现隐藏虚拟键
        final Window window = getWindow();
        showSystemUI(window);

        window.getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                showSystemUI(window);
            }
        });

    }

    private void showSystemUI(Window window) {
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;

        window.getDecorView().setSystemUiVisibility(uiOptions);
    }
}
