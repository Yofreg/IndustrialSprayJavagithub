package com.goockr.industrialsprayjava.bean;

import com.goockr.industrialsprayjava.tools.BaseUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CodeBean {

    public static class CodeText {
        String str;
        String typeCode="CodeText";

        public String getTypeCode() {
            return typeCode;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public CodeText(String str) {
            this.str = str;
        }
    }

    public static class CodeClock {
        String contentStyle="0";
        String dayStyle="yyyy-MM-d";
        String timeStyle="HH:mm:ss";
        String deviation="";
        String typeCode="CodeClock";

        public String getTypeCode() {
            return typeCode;
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

        public String getDeviation() {
            return deviation;
        }

        public void setDeviation(String deviation) {
            this.deviation = deviation;
        }

        public CodeClock(String contentStyle, String dayStyle, String timeStyle, String deviation) {

            this.contentStyle = contentStyle;
            this.dayStyle = dayStyle;
            this.timeStyle = timeStyle;
            this.deviation = deviation;
        }

        public String getClockString() {
            String clockString = "";
            switch (contentStyle) {
                case "0":
                    clockString = getDateFormatString();
                    break;
                case "1":
                    clockString = getDateFormatString() + " " + getTimeFormatString();
                    break;
                case "2":
                    clockString = getTimeFormatString() + " " + getDateFormatString();
                    break;
                case "3":
                    clockString = getTimeFormatString();
                    break;
            }
            return clockString;
        }

        private String getTimeFormatString() {
            String returnTime = "";
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat df = new SimpleDateFormat(timeStyle);
            returnTime = df.format(date);
            return returnTime;
        }

        private String getDateFormatString() {
            String returnDate = "";
            String dayStyle1 = dayStyle;
            long x = System.currentTimeMillis();
            if (!deviation.equals("")) {
                x += Long.parseLong(deviation) * 24 * 60 * 60 * 1000;
            }
            Date date = new Date(x);

            dayStyle1 = dayStyle1.replace("JulianYear", "≠≠≠≠");
            dayStyle1 = dayStyle1.replace("JulianDay", "≡≡≡≡");
            dayStyle1 = dayStyle1.replace("Month", "MMMM");
            dayStyle1 = dayStyle1.replace("WN", "w");
            dayStyle1 = dayStyle1.replace("Week", "E");//具体要根据情况来修改
            dayStyle1 = dayStyle1.replace("YYYY", "yyyy");

            SimpleDateFormat df = new SimpleDateFormat(dayStyle1, Locale.ENGLISH);
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy");
            returnDate = df.format(date);
            String year = df1.format(date);

            returnDate = returnDate.replace("≠≠≠≠", (Integer.parseInt(year)%10)+"");
            returnDate = returnDate.replace("≡≡≡≡", BaseUtils.dateToJuLian(date)+"");
            return returnDate;
        }


    }

    public static class CodeNo {
        String nowValue;
        int repeatCount;
        int lengthAdd;
        int limitNo1;
        int limitNo2;
        boolean isPreZero;
        private int nowPrint = 0;

        public int getNowPrint() {
            return nowPrint;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(String typeCode) {
            this.typeCode = typeCode;
        }

        String typeCode="CodeNo";

        private static boolean isFirst = true;

        public String getNowValue() {
            return nowValue;
        }

        public void setNowValue(String nowValue) {
            this.nowValue = nowValue;
        }

        public int getRepeatCount() {
            return repeatCount;
        }

        public void setRepeatCount(int repeatCount) {
            this.repeatCount = repeatCount;
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
        }

        public int getLimitNo2() {
            return limitNo2;
        }

        public void setLimitNo2(int limitNo2) {
            this.limitNo2 = limitNo2;
        }

        public boolean isPreZero() {
            return isPreZero;
        }

        public void setPreZero(boolean preZero) {
            isPreZero = preZero;
        }

        public CodeNo(String nowValue, int repeatCount, int lengthAdd, int limitNo1, int limitNo2, boolean isPreZero) {

            this.nowValue = nowValue;
            this.repeatCount = repeatCount;
            this.lengthAdd = lengthAdd;
            this.limitNo1 = limitNo1;
            this.limitNo2 = limitNo2;
            this.isPreZero = isPreZero;
        }

        public String updateText() {
            String text = "";
            if (isFirst && nowPrint < (repeatCount + 1)) {
                isFirst = !isFirst;
                return isPreZero(nowValue);
            }
            if (nowPrint < repeatCount) {
                text = nowValue;
                nowPrint++;
            } else {
                nowPrint = 0;
                int x = Integer.parseInt(nowValue) + lengthAdd;
                x = x > limitNo2 ? limitNo1 : x;
                text = x + "";
            }
            nowValue = isPreZero(text);
            return nowValue;
        }

        private String isPreZero(String text) {
            if (isPreZero) {
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


    }

    public static class CodeSource {
        private String mText = "********************************************************************";
        private String mText1 = "********************************************************************";
        int length;
        int type;
        String path="";
        private StringBuilder sourceText;
        private String[] splidText;
        private int NowPrint = 0;
        String typeCode="CodeSource";

        public String getTypeCode() {
            return typeCode;
        }

        public CodeSource(int length, int type) {
            this.length = length;
            this.type = type;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
            sourceText = new StringBuilder();
            File f = new File(path);
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

        public String upText() {
            if (splidText== null) {
                return returnTextForLength(mText);
            }
            if (NowPrint > splidText.length-1) {
                return "";
            }
            mText = returnTextForLength(splidText[NowPrint]);
            NowPrint++;
            return mText;
        }
        private String returnTextForLength(String mText1) {
            String text = "";
            if (length > mText1.length()) {
                text = mText1.substring(0, mText1.length());
            } else {
                text = mText1.substring(0, length);
            }
            return text;
        }

    }

    public static class CodeClazz {
        List<ClassItem> classItems;
        String typeCode="CodeClazz";

        public String getTypeCode() {
            return typeCode;
        }
        public CodeClazz(List<ClassItem> classItems) {
            this.classItems = classItems;
        }

        public List<ClassItem> getClassItems() {

            return classItems;
        }

        public void setClassItems(List<ClassItem> classItems) {
            this.classItems = classItems;
        }

        public String updateText() {
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
    }
}
