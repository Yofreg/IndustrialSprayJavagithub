package com.goockr.industrialsprayjava.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.goockr.industrialsprayjava.ui.InfoEditFragment;

public class Horizon extends HorizontalScrollView {

    public Horizon(Context context) {
        super(context);
    }

    public Horizon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (InfoEditFragment.flag == -1) {
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }
}
