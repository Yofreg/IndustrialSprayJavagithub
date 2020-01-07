package com.goockr.industrialsprayjava.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.goockr.industrialsprayjava.bean.CodeBean;

import java.util.Map;

//二维码
public class CodeImageView extends android.support.v7.widget.AppCompatImageView {

    private String codeStyle;
    private String codeSize;
    private String codeDirection;
    private String codeCorrection;
    private String codeReverse;
    private String codeContent;
    private Map map;
    public String getCodeStyle() {
        return codeStyle;
    }

    public void setCodeStyle(String codeStyle) {
        this.codeStyle = codeStyle;
    }

    public String getCodeSize() {
        return codeSize;
    }

    public void setCodeSize(String codeSize) {
        this.codeSize = codeSize;
    }

    public String getCodeDirection() {
        return codeDirection;
    }

    public void setCodeDirection(String codeDirection) {
        this.codeDirection = codeDirection;
    }

    public String getCodeCorrection() {
        return codeCorrection;
    }

    public void setCodeCorrection(String codeCorrection) {
        this.codeCorrection = codeCorrection;
    }

    public String getCodeReverse() {
        return codeReverse;
    }

    public void setCodeReverse(String codeReverse) {
        this.codeReverse = codeReverse;
    }

    public String getCodeContent() {
        return codeContent;
    }

    public void setCodeContent(String codeContent) {
        this.codeContent = codeContent;
    }

    public CodeImageView(Context context) {
        super(context);
    }

    public CodeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String upDataStr() {
        String text = "";
        for(int i=1;i<=map.size();i++) {
            if (getStringForCode(map.get(i + "")).equals("")) {
                return "";
            }
            text += getStringForCode(map.get(i+""));
        }
        codeContent = text;
        return text;
    }

    private String getStringForCode(Object o) {
        String x = "";
        if (o instanceof CodeBean.CodeText) {
            x = ((CodeBean.CodeText) o).getStr();
        } else if (o instanceof CodeBean.CodeClock) {
            CodeBean.CodeClock clock = (CodeBean.CodeClock) o;
            x = clock.getClockString();
        } else if (o instanceof CodeBean.CodeNo) {
            CodeBean.CodeNo codeNo = (CodeBean.CodeNo) o;
            x = codeNo.updateText();
        } else if (o instanceof CodeBean.CodeSource) {
            x = ((CodeBean.CodeSource) o).upText();
            if (x.equals("")) {
                return "";
            }
        } else if (o instanceof CodeBean.CodeClazz) {
            x = ((CodeBean.CodeClazz) o).updateText();
        }
        return x;
    }






}
