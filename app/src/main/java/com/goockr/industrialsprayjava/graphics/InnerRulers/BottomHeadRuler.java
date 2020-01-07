package com.goockr.industrialsprayjava.graphics.InnerRulers;

import android.content.Context;
import android.graphics.Canvas;

/**
 * 头向下的尺子
 */

public class BottomHeadRuler extends InnerRuler {
    public BottomHeadRuler(Context context, BooheeRuler booheeRuler,boolean isMM) {
        super(context, booheeRuler,isMM);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas,isMM);

    }

    //画刻度和字
    private void drawScale(Canvas canvas,boolean isMM) {
        float start = (getScrollX() - mDrawOffset) / mParent.getInterval() + mParent.getMinScale();
        float end = (getScrollX() + canvas.getWidth() + mDrawOffset) / mParent.getInterval() + mParent.getMinScale();
        int height = canvas.getHeight();
        for (float i = start; i <= end; i++) {
            float locationX = (i - mParent.getMinScale()) * mParent.getInterval() + 17;

            if (i >= mParent.getMinScale() && i <= mParent.getMaxScale()) {
                if (i % mCount == 0) {
                    canvas.drawLine(locationX, height - mParent.getBigScaleLength(), locationX, height, mBigScalePaint);
                    if (i == 0) {
                        canvas.drawText(RulerStringUtil.resultValueOf(isMM,i, mParent.getFactor()), locationX + 10, height - mParent.getTextMarginHead()-5, mTextPaint);
                    } else {
                        canvas.drawText(RulerStringUtil.resultValueOf(isMM,i, mParent.getFactor()), locationX + 8, height - mParent.getTextMarginHead(), mTextPaint);
                    }
                } else {
                    if (i % 5 == 0){
                        canvas.drawLine(locationX, height - mParent.getSmallScaleLength()-5, locationX, height, mSmallScalePaint);
                    }else{
                        canvas.drawLine(locationX, height - mParent.getSmallScaleLength(), locationX, height, mSmallScalePaint);
                    }
                }
            }
        }

    }

}
