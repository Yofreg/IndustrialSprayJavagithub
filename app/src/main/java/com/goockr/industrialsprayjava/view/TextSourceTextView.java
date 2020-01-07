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

import com.goockr.pmj.utils.MLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

//串口数据
public class TextSourceTextView extends android.support.v7.widget.AppCompatTextView {

    private TextPaint mTextPaint;
    Rect text_bounds = new Rect();
    final static int DEFAULT_TEXT_SIZE = 15;
    final static int DEFAULT_TEXT_COLOR = 0xFF000000;
    private Paint linePaint;

    private int direction;
    private int mTextSize;
    private String typefacePath = "";
    private int spaceletter;
    private Paint.FontMetrics fontMetrics;
    private String mText = "********************************************************************";
    private String mText1 = "********************************************************************";
    private boolean isIt, isUnderline, isBolder;
    private int textLength;
    private int soutceType;
    private String usbPath;
    private StringBuilder sourceText;
    private String[] splidText;
    private int NowPrint = 0;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
        requestLayout();
        //invalidate();
    }

    public int getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        linePaint.setStrokeWidth(mTextSize / 10);
        mTextPaint.setTextSize(mTextSize);
        requestLayout();
       // invalidate();
    }

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
       // invalidate();
    }

    public int getSpaceletter() {
        return spaceletter;
    }

    public void setSpaceletter(int spaceletter) {
        this.spaceletter = spaceletter;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTextPaint.setLetterSpacing(spaceletter);
            requestLayout();
            //invalidate();
        }
    }

    public String getmText() {
        return mText;
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
        //invalidate();
    }

    public boolean isUnderline() {
        return isUnderline;
    }

    public void setUnderline(boolean underline) {
        isUnderline = underline;
        requestLayout();
        //invalidate();
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
       // invalidate();
    }

    public int getTextLength() {
        return textLength;
    }

    public void setTextLength(int textLength) {
        this.textLength = textLength;
        mText = returnTextForLength(mText1);
        requestLayout();
        //invalidate();
    }

    private String returnTextForLength(String mText1) {
        String text = "";
        if (textLength > mText1.length()) {
            text = mText1.substring(0, mText1.length());
        } else {
            text = mText1.substring(0, textLength);
        }
        return text;
    }

    public int getSoutceType() {
        return soutceType;
    }

    public void setSoutceType(int soutceType) {
        this.soutceType = soutceType;
    }

    public String getUsbPath() {
        return usbPath;
    }

    public void setUsbPath(String usbPath) {
        this.usbPath = usbPath;
        sourceText = new StringBuilder();
        MLog.v("usbPath" + usbPath);
        if (usbPath == null) {
            mText = "文件未找到";
            return;
        }
        File f = new File(usbPath);
        if (!f.exists()) {
            mText = "文件未找到";
            return;
        }
        try {
            InputStream instream = new FileInputStream(f);
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                while ((line = buffreader.readLine()) != null) {
                    sourceText.append(line + "\n");
                }
                String x = sourceText.toString();
                splidText = x.split("\n");

                buffreader.close();
                instream.close();
                inputreader.close();

            }
        } catch (Exception e) {
            // TODO: handle exception
        }


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
        //invalidate();
    }

    public TextSourceTextView(Context context) {
        super(context);
        init();
    }

    public TextSourceTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
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

    public boolean upText() {
        if (splidText == null) {
            mText = returnTextForLength(mText);
        } else if (NowPrint > splidText.length - 1) {
            return false;
        } else {
            mText = returnTextForLength(splidText[NowPrint]);
        }
        NowPrint++;
        requestLayout();
        //invalidate();
        return true;
    }

    public int getNowPrint() {
        return NowPrint;
    }

    public void setNowPrint(int nowPrint) {
        NowPrint = nowPrint;
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
}
