package com.goockr.industrialsprayjava.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.goockr.industrialsprayjava.R;

//文本显示
public class VerticalTextView extends android.support.v7.widget.AppCompatTextView {

    private TextPaint mTextPaint;
    private String mText;
    Rect text_bounds = new Rect();
    final static int DEFAULT_TEXT_SIZE = 15;
    final static int DEFAULT_TEXT_COLOR = 0xFF000000;
    private int direction;

    private int size;
    private boolean isUnder, isIt, isBold;
    private float letterSpace;
    private Paint linePaint;
    private int lineSize = 2;
    private String typefacePath = "";
    private Paint.FontMetrics fontMetrics;

    public VerticalTextView(Context context) {
        super(context);
        init();
    }

    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.verticaltextview);
        CharSequence s = a.getString(R.styleable.verticaltextview_text);
        if (s != null)
            mText = s.toString();
        int textSize = a.getDimensionPixelOffset(R.styleable.verticaltextview_textSize, DEFAULT_TEXT_SIZE);
        if (textSize > 0)
            mTextPaint.setTextSize(textSize);

        mTextPaint.setColor(a.getColor(R.styleable.verticaltextview_textColor, DEFAULT_TEXT_COLOR));
        direction = a.getInt(R.styleable.verticaltextview_direction, 0);
        a.recycle();

        requestLayout();
        //invalidate();
    }

    public String getTypefacePath() {
        return typefacePath;
    }

    public void setTypefaces(Typeface tf, String filePath) {
        if (filePath.equals("")) {
            mTextPaint.setTypeface(Typeface.SANS_SERIF);
        } else {
            mTextPaint.setTypeface(tf);
        }
        typefacePath = filePath;
        requestLayout();
       // invalidate();
    }

    private final void init() {
        mTextPaint = new TextPaint();
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(DEFAULT_TEXT_COLOR);
        linePaint.setStrokeWidth(2);
        linePaint.setStyle(Paint.Style.STROKE);

        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(DEFAULT_TEXT_SIZE);
        mTextPaint.setColor(DEFAULT_TEXT_COLOR);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    public void setText(String text) {
        mText = text;
        requestLayout();
       // invalidate();
    }

    public void setTextSize(int size) {
        mTextPaint.setTextSize(size);
        linePaint.setStrokeWidth(size / 10);
        this.size = size;
        requestLayout();
        //invalidate();
    }

    public void setTextColor(int color) {
        mTextPaint.setColor(color);
        invalidate();
    }

    public void setDirection(int direction) {
        this.direction = direction;
        requestLayout();
        //invalidate();
    }

    public void setUnder(boolean under) {
        isUnder = under;
        if (isUnder) {
            mTextPaint.setUnderlineText(true);
        } else {
            mTextPaint.setUnderlineText(false);
        }
        requestLayout();
       // invalidate();
    }

    public void setIt(boolean it) {
        isIt = it;
        if (isIt) {
            mTextPaint.setTextSkewX(-0.5f);
        } else {
            mTextPaint.setTextSkewX(0);
        }
        requestLayout();
        //invalidate();
    }

    public void setBold(boolean bold) {
        isBold = bold;
        if (isBold) {
            mTextPaint.setFakeBoldText(true);
        } else {
            mTextPaint.setFakeBoldText(false);
        }
        requestLayout();
       // invalidate();
    }

    public void setFontStyle(boolean isBold, boolean isIt, boolean isUnder) {
        this.isBold = isBold;
        this.isIt = isIt;
        this.isUnder = isUnder;
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
       // invalidate();
    }

    public boolean isUnder() {
        return isUnder;
    }

    public boolean isIt() {
        return isIt;
    }

    public boolean isBold() {
        return isBold;
    }

    public int getDirection() {
        return direction;
    }

    public float getLetterSpace() {
        return letterSpace;
    }

    public String getmText() {
        return mText;
    }

    public int getSize() {
        return size;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setLetterSpace(float letterSpace) {
        this.letterSpace = letterSpace;
        mTextPaint.setLetterSpacing(letterSpace);
        requestLayout();
        //invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mTextPaint.getTextBounds(mText, 0, mText.length(), text_bounds);
        fontMetrics = mTextPaint.getFontMetrics();
        float y = fontMetrics.leading + fontMetrics.descent + Math.abs(fontMetrics.ascent);
        if (direction == 0 || direction == 1) {
            setMeasuredDimension((int) y + 10, measureHeight(widthMeasureSpec) + 20);
        } else {
            setMeasuredDimension(measureHeight(widthMeasureSpec) + 20, (int) y + 10);
        }
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
//            result = text_bounds.height() + getPaddingLeft() + getPaddingRight();
            result = text_bounds.height();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
//            result = text_bounds.width() + getPaddingTop() + getPaddingBottom();
            result = text_bounds.width();
            if (specMode == MeasureSpec.AT_MOST) {
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

        if (direction == 0) {
            path.moveTo(startX + fontMetrics.descent, startY);
            path.lineTo(startX + fontMetrics.descent, stopY);
            pathLine.moveTo(startX, startY + 5);
            pathLine.lineTo(startX, stopY - 5);
        } else if (direction == 1) {
            path.moveTo(stopX - fontMetrics.descent, stopY);
            path.lineTo(stopX - fontMetrics.descent, startY);
            pathLine.moveTo(stopX, stopY - 5);
            pathLine.lineTo(stopX, startY + 5);
        } else if (direction == 2) {
            path.moveTo(startX, stopY - fontMetrics.descent);
            path.lineTo(stopX, stopY - fontMetrics.descent);
            pathLine.moveTo(startX + 5, stopY);
            pathLine.lineTo(stopX - 5, stopY);
        } else if (direction == 3) {
            path.moveTo(stopX, startY + fontMetrics.descent);
            path.lineTo(startX, startY + fontMetrics.descent);
            pathLine.moveTo(stopX - 5, startY);
            pathLine.lineTo(startX + 5, startY);
        }
        canvas.drawTextOnPath(mText, path, 0, 0, mTextPaint);
        if (isUnder) {
            canvas.drawPath(pathLine, linePaint);
        }
    }

    public void upText() {
        requestLayout();
       // invalidate();
    }
}
