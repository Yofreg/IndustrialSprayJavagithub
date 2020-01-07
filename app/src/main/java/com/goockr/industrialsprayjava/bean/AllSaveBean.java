package com.goockr.industrialsprayjava.bean;

import java.util.List;
import java.util.Map;

public class AllSaveBean {

    public static class TextBase {
        private String str;
        private String size;
        private String typeface;
        private String direction;
        private String top;
        private String left;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getTypeface() {
            return typeface;
        }

        public void setTypeface(String typeface) {
            this.typeface = typeface;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public TextBase(String str, String size, String typeface,
                 String direction, String top, String left) {
            super();
            this.str = str;
            this.size = size;
            this.typeface = typeface;
            this.direction = direction;
            this.top = top;
            this.left = left;
        }

    }

    public static class TextInfo extends TextBase {

        private String superscript;
        private String italic;
        private String bolder;
        private String underline;

        public String getSuperscript() {
            return superscript;
        }

        public void setSuperscript(String superscript) {
            this.superscript = superscript;
        }

        public String getItalic() {
            return italic;
        }

        public void setItalic(String italic) {
            this.italic = italic;
        }

        public String getBolder() {
            return bolder;
        }

        public void setBolder(String bolder) {
            this.bolder = bolder;
        }

        public String getUnderline() {
            return underline;
        }

        public void setUnderline(String underline) {
            this.underline = underline;
        }

        public TextInfo(String str, String size, String typeface,
                        String direction, String top, String left, String superscript,
                        String italic, String bolder, String underline) {
            super(str, size, typeface, direction, top, left);
            this.superscript = superscript;
            this.italic = italic;
            this.bolder = bolder;
            this.underline = underline;
        }

    }

    public static class ClockInfo extends TextBase {
        private String no;
        private String contentStyle;
        private String dayStyle;
        private String timeStyle;
        private String superScript;
        private String it;
        private String bolder;
        private String underline;
        private String deviation;

        public String getDeviation() {
            return deviation;
        }

        public void setDeviation(String deviation) {
            this.deviation = deviation;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getContentStyle() {
            return contentStyle;
        }

        public void setContentStyle(String contentStyle) {
            this.contentStyle = contentStyle;
        }

        public String getDayStyle() {
            return dayStyle;
        }

        public void setDayStyle(String dayStyle) {
            this.dayStyle = dayStyle;
        }

        public String getTimeStyle() {
            return timeStyle;
        }

        public void setTimeStyle(String timeStyle) {
            this.timeStyle = timeStyle;
        }

        public String getSuperScript() {
            return superScript;
        }

        public void setSuperScript(String superScript) {
            this.superScript = superScript;
        }

        public String getIt() {
            return it;
        }

        public void setIt(String it) {
            this.it = it;
        }

        public String getBolder() {
            return bolder;
        }

        public void setBolder(String bolder) {
            this.bolder = bolder;
        }

        public String getUnderline() {
            return underline;
        }

        public void setUnderline(String underline) {
            this.underline = underline;
        }

        public ClockInfo(String str, String size, String typeface,
                         String direction, String top, String left, String no,
                         String it, String bolder, String underline, String deviation, String contentStyle, String dayStyle, String timeStyle) {
            super(str, size, typeface, direction, top, left);
            this.no = no;
            this.it = it;
            this.bolder = bolder;
            this.underline = underline;
            this.deviation = deviation;
            this.contentStyle = contentStyle;
            this.dayStyle = dayStyle;
            this.timeStyle = timeStyle;
        }


    }

    public static class ImgInfo {
        private String imgNo;
        private String imgScale;
        private String imgDirection;
        private String top;
        private String left;

        public ImgInfo(String imgNo, String imgScale, String imgDirection, String top, String left) {
            this.imgNo = imgNo;
            this.imgScale = imgScale;
            this.imgDirection = imgDirection;
            this.top = top;
            this.left = left;
        }

        public String getImgNo() {

            return imgNo;
        }

        public void setImgNo(String imgNo) {
            this.imgNo = imgNo;
        }

        public String getImgScale() {
            return imgScale;
        }

        public void setImgScale(String imgScale) {
            this.imgScale = imgScale;
        }

        public String getImgDirection() {
            return imgDirection;
        }

        public void setImgDirection(String imgDirection) {
            this.imgDirection = imgDirection;
        }

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }
    }

    public static class CodeInfo {
        private String codeStyle;
        private String codeSize;
        private String codeDirection;
        private String codeCorrection;
        private String codeReversal;
        private String codeContent="";
        private String top, left;
        private Map map;

        public Map getMap() {
            return map;
        }

        public void setMap(Map map) {
            this.map = map;
        }

        public CodeInfo(String codeStyle, String codeSize, String codeDirection, String codeCorrection, String codeReversal, String codeContent, String top, String left, Map map) {

            this.codeStyle = codeStyle;
            this.codeSize = codeSize;
            this.codeDirection = codeDirection;
            this.codeCorrection = codeCorrection;
            this.codeReversal = codeReversal;
            this.codeContent = codeContent;
            this.top = top;
            this.left = left;
            this.map = map;
        }

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

        public String getCodeReversal() {
            return codeReversal;
        }

        public void setCodeReversal(String codeReversal) {
            this.codeReversal = codeReversal;
        }

        public String getCodeContent() {
            return codeContent;
        }

        public void setCodeContent(String codeContent) {
            this.codeContent = codeContent;
        }

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }
    }

    public static class ClassInfo extends TextBase {
        private List<ClassItem> classItems;

        public ClassInfo(String str, String size, String typeface, String direction, String top, String left, List<ClassItem> classItems) {
            super(str, size, typeface, direction, top, left);
            this.classItems = classItems;
        }

        public List<ClassItem> getClassItems() {

            return classItems;
        }

        public void setClassItems(List<ClassItem> classItems) {
            this.classItems = classItems;
        }
    }

    public static class NoInfo extends TextInfo {
        private String no;
        private String repeatCount;
        private String lengthAdd;
        private String limitNo1;
        private String limitNo2;
        private String proPositionZero;

        public NoInfo(String str, String size, String typeface, String direction, String top, String left, String superscript, String italic, String bolder, String underline, String no, String repeatCount, String lengthAdd, String limitNo1, String limitNo2, String proPositionZero) {
            super(str, size, typeface, direction, top, left, superscript, italic, bolder, underline);
            this.no = no;
            this.repeatCount = repeatCount;
            this.lengthAdd = lengthAdd;
            this.limitNo1 = limitNo1;
            this.limitNo2 = limitNo2;
            this.proPositionZero = proPositionZero;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getRepeatCount() {
            return repeatCount;
        }

        public void setRepeatCount(String repeatCount) {
            this.repeatCount = repeatCount;
        }

        public String getLengthAdd() {
            return lengthAdd;
        }

        public void setLengthAdd(String lengthAdd) {
            this.lengthAdd = lengthAdd;
        }

        public String getLimitNo1() {
            return limitNo1;
        }

        public void setLimitNo1(String limitNo1) {
            this.limitNo1 = limitNo1;
        }

        public String getLimitNo2() {
            return limitNo2;
        }

        public void setLimitNo2(String limitNo2) {
            this.limitNo2 = limitNo2;
        }

        public String getProPositionZero() {
            return proPositionZero;
        }

        public void setProPositionZero(String proPositionZero) {
            this.proPositionZero = proPositionZero;
        }
    }

    public static class TextSourceInfo extends TextInfo {
        private int textLength;
        private int soutceType;
        private String usbPath;
        private int nowPrint = 0;

        public int getTextLength() {
            return textLength;
        }

        public void setTextLength(int textLength) {
            this.textLength = textLength;
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
        }

        public int getNowPrint() {
            return nowPrint;
        }

        public void setNowPrint(int nowPrint) {
            this.nowPrint = nowPrint;
        }

        public TextSourceInfo(String str, String size, String typeface, String direction, String top, String left, String superscript, String italic, String bolder,
                              String underline, int textLength, int soutceType, String usbPath, int nowPrint) {
            super(str, size, typeface, direction, top, left, superscript, italic, bolder, underline);
            this.textLength = textLength;
            this.soutceType = soutceType;
            this.usbPath = usbPath;
            this.nowPrint = nowPrint;
        }
    }


}
