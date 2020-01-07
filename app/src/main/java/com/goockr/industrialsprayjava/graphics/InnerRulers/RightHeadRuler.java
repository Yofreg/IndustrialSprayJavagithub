package com.goockr.industrialsprayjava.graphics.InnerRulers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * 头向→的尺子
 */
public class RightHeadRuler extends InnerRuler {

    public RightHeadRuler(Context context, BooheeRuler booheeRuler,boolean isMM) {
        super(context, booheeRuler,isMM);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas,isMM);
    }

    //画刻度和字
    private void drawScale(Canvas canvas,boolean isMM) {
        Path path=new Path();
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        float start = (getScrollY() - mDrawOffset) / mParent.getInterval() + mParent.getMinScale();
        float end = (getScrollY() + height + mDrawOffset) / mParent.getInterval() + mParent.getMinScale();
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        for (float i = start; i <= end; i++){
            float locationY = (i - mParent.getMinScale()) * mParent.getInterval()+18;

            if (i >= mParent.getMinScale() && i <= mParent.getMaxScale()) {
                if (i % mCount == 0) {
                    canvas.drawLine(width - mParent.getBigScaleLength(), locationY, width, locationY, mBigScalePaint);
                    if (i==0){
                        path.lineTo(width -30,locationY + mParent.getTextSize() / 2+14);
                        canvas.drawTextOnPath(RulerStringUtil.resultValueOf(isMM,i,mParent.getFactor()),path,0, -5, mTextPaint);
                    }else if (i<100){
                        path.lineTo(width - 30,locationY + mParent.getTextSize() / 2+5);
                        canvas.drawTextOnPath(RulerStringUtil.resultValueOf(isMM,i,mParent.getFactor()),path,0, -8, mTextPaint);
                    }else{
                        path.lineTo(width - 30,locationY + mParent.getTextSize() / 2+8);
                        canvas.drawTextOnPath(RulerStringUtil.resultValueOf(isMM,i,mParent.getFactor()),path,2, -8, mTextPaint);
                    }

                } else {
                    if (i % 5 == 0){
                        canvas.drawLine(width - mParent.getSmallScaleLength()-6, locationY, width, locationY, mSmallScalePaint);
                    }else{
                        canvas.drawLine(width - mParent.getSmallScaleLength(), locationY, width, locationY, mSmallScalePaint);
                    }

                }
            }
        }
    }


}
