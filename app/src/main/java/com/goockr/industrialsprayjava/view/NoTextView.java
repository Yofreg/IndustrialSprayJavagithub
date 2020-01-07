package com.goockr.industrialsprayjava.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.goockr.industrialsprayjava.bean.PrintNo;
import com.goockr.industrialsprayjava.tools.PreferenceUtils;

//序号
public class NoTextView extends android.support.v7.widget.AppCompatTextView {

    private TextPaint mTextPaint;
    Rect text_bounds = new Rect();
    final static int DEFAULT_TEXT_SIZE = 15;
    final static int DEFAULT_TEXT_COLOR = 0xFF000000;
    private Paint linePaint;

    private int direction;
    private int mTextSize;
    private String typefacePath = "";
    private int spaceletter;
    private int no;
    private String mText="";
    private int repectCount;
    private int lengthAdd;
    private int limitNo1, limitNo2;
    private boolean isProPositionZero, isIt, isUnderline, isBolder;

    private Paint.FontMetrics fontMetrics;
    private int nowPrint = 0;
    private boolean isFirst = true;

    public String getTypefacePath() {
        return typefacePath;
    }

    public void setTypefacePath(Typeface tf, String typefacePath) {
        if (typefacePath.equals("")) {
            mTextPaint.setTypeface(Typeface.DEFAULT);
        } else {
            mTextPaint.setTypeface(tf);
        }
        this.typefacePath = typefacePath;
        requestLayout();
        invalidate();
    }



    private String updateText() {
        String text = "";
        if (isFirst && nowPrint < (repectCount + 1)) {
            isFirst = !isFirst;
            return isPreZero(mText);
        }
        if (nowPrint < repectCount) {
            text = mText;
            nowPrint++;
        } else {
            nowPrint = 0;
            int x = Integer.parseInt(mText) + lengthAdd;
            x = x > limitNo2 ? limitNo1 : x;
            text = x + "";
        }
        return isPreZero(text);
    }

    private String isPreZero(String text) {
        if (isProPositionZero) {
            String zero = "";
            for (int i = text.length(); i < (limitNo2 + "").length(); i++) {
                zero += "0";
            }
            text = zero + text;
        } else {
            text = Integer.parseInt(text) + "";
        }
        return text;
    }

    public int getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        linePaint.setStrokeWidth(mTextSize / 10);
        mTextPaint.setTextSize(mTextSize);
        requestLayout();
        invalidate();
    }

    public int getSpaceletter() {
        return spaceletter;
    }

    public void setSpaceletter(int spaceletter) {
        this.spaceletter = spaceletter;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTextPaint.setLetterSpacing(spaceletter);
            requestLayout();
            invalidate();
        }
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setmText(String mText) {
        this.mText = isPreZero(mText);
        requestLayout();
        invalidate();
    }

    public int getRepectCount() {
        return repectCount;
    }

    public void setRepectCount(int repectCount) {
        this.repectCount = repectCount;
    }

    public int getLengthAdd() {
        return lengthAdd;
    }

    public void setLengthAdd(int lengthAdd) {
        this.lengthAdd = lengthAdd;
    }

    public int getLimitNo1() {
        return limitNo1;
    }

    public void setLimitNo1(int limitNo1) {
        this.limitNo1 = limitNo1;
        if (limitNo1 > Integer.parseInt(mText)) {
            mText = limitNo1 + "";
        }
        mText = isPreZero(mText);
        requestLayout();
        invalidate();
    }

    public int getLimitNo2() {
        return limitNo2;
    }

    public void setLimitNo2(int limitNo2) {
        this.limitNo2 = limitNo2;
        if (limitNo2 < Integer.parseInt(mText)) {
            mText = limitNo2 + "";
        }
        mText = isPreZero(mText);
        requestLayout();
        invalidate();
    }

    public boolean isProPositionZero() {
        return isProPositionZero;
    }

    public void setProPositionZero(boolean proPositionZero) {
        isProPositionZero = proPositionZero;
        mText = isPreZero(mText);
        requestLayout();
        invalidate();
    }

    public boolean isIt() {
        return isIt;
    }

    public void setIt(boolean it) {
        isIt = it;
        if (isIt) {
            mTextPaint.setTextSkewX(-0.5f);
        } else {
            mTextPaint.setTextSkewX(0);
        }
        requestLayout();
        invalidate();
    }

    public boolean isUnderline() {
        return isUnderline;
    }

    public void setUnderline(boolean underline) {
        isUnderline = underline;
        if (underline) {
            mTextPaint.setUnderlineText(true);
        } else {
            mTextPaint.setUnderlineText(false);
        }
        requestLayout();
        invalidate();
    }

    public void setFontStyle(boolean isBold, boolean isIt, boolean isUnder) {
        this.isBolder = isBold;
        this.isIt = isIt;
        this.isUnderline = isUnder;
        if (isBold) {
            mTextPaint.setFakeBoldText(true);
        }
        if (isUnder) {
            mTextPaint.setUnderlineText(true);
        }
        if (isIt) {
            mTextPaint.setTextSkewX(-0.5f);
        }
        requestLayout();
        invalidate();
    }

    public boolean isBolder() {
        return isBolder;
    }

    public void setBolder(boolean bolder) {
        isBolder = bolder;
        if (bolder) {
            mTextPaint.setFakeBoldText(true);
        } else {
            mTextPaint.setFakeBoldText(false);
        }
        requestLayout();
        invalidate();
    }

    public int getDirection() {
        return direction;
    }

    public String getmText() {
        return mText;
    }

    public void setDirection(int direction) {
        this.direction = direction;
        requestLayout();
        invalidate();
    }

    private final void init() {

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(DEFAULT_TEXT_COLOR);
        linePaint.setStrokeWidth(2);
        linePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(DEFAULT_TEXT_SIZE);
        mTextPaint.setColor(DEFAULT_TEXT_COLOR);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setTextColor(int color) {
        mTextPaint.setColor(color);
        invalidate();
    }

    public NoTextView(Context context) {
        super(context);
        init();
    }

    public NoTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mTextPaint.getTextBounds(mText, 0, mText.length(), text_bounds);
        fontMetrics = mTextPaint.getFontMetrics();
        float y = fontMetrics.leading + fontMetrics.descent + Math.abs(fontMetrics.ascent);
        if (direction == 3 || direction == 1) {
            setMeasuredDimension((int) y + 10, measureHeight(widthMeasureSpec) + 20);
        } else {
            setMeasuredDimension(measureHeight(widthMeasureSpec) + 20, (int) y + 10);
        }
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            //            result = text_bounds.height() + getPaddingLeft() + getPaddingRight();
            result = text_bounds.height();
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            //            result = text_bounds.width() + getPaddingTop() + getPaddingBottom();
            result = text_bounds.width();
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int startX = 0;
        int startY = 0;
        int stopY = getHeight();
        int stopX = getWidth();
        Path path = new Path();
        Path pathLine = new Path();
        if (direction == 3) {
            path.moveTo(startX + fontMetrics.descent, startY);
            path.lineTo(startX + fontMetrics.descent, stopY);
            pathLine.moveTo(startX, startY + 5);
            pathLine.lineTo(startX, stopY - 5);
        } else if (direction == 1) {
            path.moveTo(stopX - fontMetrics.descent, stopY);
            path.lineTo(stopX - fontMetrics.descent, startY);
            pathLine.moveTo(stopX, stopY - 5);
            pathLine.lineTo(stopX, startY + 5);
        } else if (direction == 0) {
            path.moveTo(startX, stopY - fontMetrics.descent);
            path.lineTo(stopX, stopY - fontMetrics.descent);
            pathLine.moveTo(startX + 5, stopY);
            pathLine.lineTo(stopX - 5, stopY);
        } else if (direction == 2) {
            path.moveTo(stopX, startY + fontMetrics.descent);
            path.lineTo(startX, startY + fontMetrics.descent);
            pathLine.moveTo(stopX - 5, startY);
            pathLine.lineTo(startX + 5, startY);
        }
        canvas.drawTextOnPath(mText, path, 0, 0, mTextPaint);
        if (isUnderline) {
            canvas.drawPath(pathLine, linePaint);
        }
    }
    public void PrintText() {
        mText = updateText();

        JSONObject object = new JSONObject();
        PrintNo printNo = new PrintNo(no+"", mText, limitNo1+"", limitNo2+"", false);
        object.put("PrintNo", printNo);
        if (no == 0) {
            PreferenceUtils.getInstance().setNo1(object.toJSONString());
        } else if (no == 1) {
            PreferenceUtils.getInstance().setNo2(object.toJSONString());
        } else if (no == 2) {
            PreferenceUtils.getInstance().setNo3(object.toJSONString());
        } else if (no == 3) {
            PreferenceUtils.getInstance().setNo4(object.toJSONString());
        }

        requestLayout();
        invalidate();
    }
}
