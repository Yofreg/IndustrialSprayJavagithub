package com.goockr.industrialsprayjava.graphics.InnerRulers;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

/**
 * 内部尺子抽象类
 */

public class InnerRuler extends View {
    protected BooheeRuler mParent;
    protected Paint mSmallScalePaint, mBigScalePaint, mTextPaint;
    //最大刻度数
    protected int mMaxLength = 0;
    //一格大刻度多少格小刻度
    protected int mCount = 10;
    //提前刻画量
    protected int mDrawOffset;
    public boolean isMM;
    public InnerRuler(Context context, BooheeRuler booheeRuler,boolean isMM) {
        super(context);
        mParent = booheeRuler;
        this.isMM = isMM;
        init();
    }

    public void init(){
        mMaxLength = mParent.getMaxScale() - mParent.getMinScale();
        mCount = mParent.getCount();
        mDrawOffset = mCount * mParent.getInterval() / 2;
        initPaints();
    }

    //初始化画笔
    private void initPaints() {
        mSmallScalePaint = new Paint();
        mSmallScalePaint.setStrokeWidth(mParent.getSmallScaleWidth());
        mSmallScalePaint.setColor(mParent.getScaleColor());
        mSmallScalePaint.setStrokeCap(Paint.Cap.ROUND);

        mBigScalePaint = new Paint();
        mBigScalePaint.setColor(mParent.getScaleColor());
        mBigScalePaint.setStrokeWidth(mParent.getBigScaleWidth());

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mParent.getTextColor());
        mTextPaint.setTextSize(mParent.getTextSize());
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

}
