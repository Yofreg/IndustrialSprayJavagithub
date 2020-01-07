package com.goockr.industrialsprayjava.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.tools.BaseUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//时钟显示
public class ClockTextView extends android.support.v7.widget.AppCompatTextView {

    private TextPaint mTextPaint;
    private String mText;
    Rect text_bounds = new Rect();
    final static int DEFAULT_TEXT_SIZE = 15;
    final static int DEFAULT_TEXT_COLOR = 0xFF000000;
    private int direction;

    private int size;
    private boolean isUnder, isIt, isBold;
    private Paint linePaint;
    private int lineSize = 2;
    private Paint.FontMetrics fontMetrics;
    private int no;
    private String dateStyle = "";
    private String timeStyle = "";
    private int clockStyleNo;

    private String deviationDay = "";
    private String typefacePath = "";

    public String getDeviationDay() {
        return deviationDay;
    }

    public void setDeviationDay(String deviationDay) {
        this.deviationDay = deviationDay;
    }

    public int getClockStyleNo() {
        return clockStyleNo;
    }

    public void setClockStyleNo(int clockNo) {
        this.clockStyleNo = clockNo;
    }

    public String getDateStyle() {
        return dateStyle;
    }

    public void setDateStyle(String dateStyle) {
        this.dateStyle = dateStyle;
    }

    public String getTimeStyle() {
        return timeStyle;
    }

    public void setTimeStyle(String timeStyle) {
        this.timeStyle = timeStyle;
    }

    public void setTypefaces(Typeface tf, String filePath) {
        if (filePath.equals("")) {
            mTextPaint.setTypeface(Typeface.DEFAULT);
        } else {
            mTextPaint.setTypeface(tf);
        }
        typefacePath = filePath;
        requestLayout();
        //invalidate();
    }

    public ClockTextView(Context context) {
        super(context);
        init();
    }


    public ClockTextView(Context context, AttributeSet attrs) {
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

    public String getTypefacePath() {
        return typefacePath;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setText(String text) {
        mText = text;
        requestLayout();
        //invalidate();
    }

    public void upText() {
        mText = getClockString();
        requestLayout();
       // invalidate();
    }

    private String getClockString() {
        String clockString = "";
        switch (clockStyleNo) {
            case 0:
                clockString = getDateFormatString();
                break;
            case 1:
                clockString = getDateFormatString() + " " + getTimeFormatString();
                break;
            case 2:
                clockString = getTimeFormatString() + " " + getDateFormatString();
                break;
            case 3:
                clockString = getTimeFormatString();
                break;
        }
        return clockString;
    }

    private String getTimeFormatString() {
        if (timeStyle.equals("")) {
            timeStyle = "HH:mm:ss";
        }
        String returnTime = "";
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat(timeStyle);
        returnTime = df.format(date);
        return returnTime;
    }

    private String getDateFormatString() {
        String returnDate = "";
        if (dateStyle.equals("")) {
            dateStyle = "yyyy-MM-dd";
        }
        String dateStyleNew = dateStyle;
        long x = System.currentTimeMillis();
        if (!deviationDay.equals("")) {
            x += Long.parseLong(deviationDay) * 24 * 60 * 60 * 1000;
        }
        Date date = new Date(x);
        dateStyleNew = dateStyleNew.replace("JulianYear", "≠≠≠≠");
        dateStyleNew = dateStyleNew.replace("JulianDay", "≡≡≡≡");
        dateStyleNew = dateStyleNew.replace("Month", "MMMM");
        dateStyleNew = dateStyleNew.replace("WN", "w");
        dateStyleNew = dateStyleNew.replace("Week", "E");
        dateStyleNew = dateStyleNew.replace("YYYY", "yyyy");
        SimpleDateFormat df = new SimpleDateFormat(dateStyleNew, Locale.ENGLISH);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy");
        returnDate = df.format(date);
        String year = df1.format(date);
        returnDate = returnDate.replace("≠≠≠≠", (Integer.parseInt(year)%10)+"");
        returnDate = returnDate.replace("≡≡≡≡", BaseUtils.dateToJuLian(date)+"");
        return returnDate;
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
      //  invalidate();
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
        //invalidate();
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


    public String getmText() {
        return mText;
    }

    public int getSize() {
        return size;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mTextPaint.getTextBounds(mText, 0, mText.length(), text_bounds);
        fontMetrics = mTextPaint.getFontMetrics();
        float y = fontMetrics.leading + fontMetrics.descent + Math.abs(fontMetrics.ascent);
        if (direction == 0 || direction == 1) {
            setMeasuredDimension((int) y + 1, measureHeight(widthMeasureSpec) + 10);
        } else {
            setMeasuredDimension(measureHeight(widthMeasureSpec) + 10, (int) y + 1);
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
}
