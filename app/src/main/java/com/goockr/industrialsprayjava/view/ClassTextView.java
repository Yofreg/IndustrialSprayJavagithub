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
import android.view.View;

import com.goockr.industrialsprayjava.R;
import com.goockr.industrialsprayjava.bean.ClassItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//班次代码
public class ClassTextView extends android.support.v7.widget.AppCompatTextView {

    private TextPaint mTextPaint;
    private String mText;
    Rect text_bounds = new Rect();
    final static int DEFAULT_TEXT_SIZE = 15;
    final static int DEFAULT_TEXT_COLOR = 0xFF000000;
    private int direction;
    private int size;
    private String typefacePath="";

    private Paint.FontMetrics fontMetrics;

    private List<ClassItem> classItems;

    public String getTypefacePath() {
        return typefacePath;
    }

    public void setTypefaces(Typeface tf, String typefacePath) {
        if (typefacePath.equals("")) {
            mTextPaint.setTypeface(Typeface.DEFAULT);
        } else {
            mTextPaint.setTypeface(tf);
        }
        this.typefacePath = typefacePath;
        requestLayout();
       // invalidate();
    }

    public ClassTextView(Context context) {
        super(context);
        init();
    }

    public ClassTextView(Context context, AttributeSet attrs) {
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
       // invalidate();
    }

    public List<ClassItem> getClassItems() {
        return classItems;
    }

    public void setClassItems(List<ClassItem> classItems) {
        this.classItems = classItems;
        mText = updateText();
        requestLayout();
       // invalidate();
    }

    public void upText() {
        mText = updateText();
        requestLayout();
      //  invalidate();
    }

    private String updateText() {
        try {
            for (int i = 0; i < classItems.size(); i++) {
                SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
                String now = sf.format(new Date());
                Date nowTime = new SimpleDateFormat("HH:mm").parse(now);
                Date startTime = new SimpleDateFormat("HH:mm").parse(classItems.get(i).getStartTime());
                Date endTime = new SimpleDateFormat("HH:mm").parse(classItems.get(i).getEndTime());
                if (isEffectiveDate(nowTime, startTime, endTime)) {
                    return classItems.get(i).getClazzText();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "NUL";
    }

    public boolean isEffectiveDate(Date nowTime, Date startTime,
                                   Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    private final void init() {
        classItems = new ArrayList<>();

        mTextPaint = new TextPaint();

        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(DEFAULT_TEXT_SIZE);
        mTextPaint.setColor(DEFAULT_TEXT_COLOR);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setTextSize(int size) {
        mTextPaint.setTextSize(size);
        this.size = size;
        requestLayout();
      //  invalidate();
    }

    public void setTextColor(int color) {
        mTextPaint.setColor(color);
       invalidate();
    }

    public void setDirection(int direction) {
        this.direction = direction;
        requestLayout();
     //   invalidate();
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
    }
}
