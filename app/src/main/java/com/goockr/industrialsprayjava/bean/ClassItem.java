package com.goockr.industrialsprayjava.bean;

public class ClassItem {
    private String startTime;
    private String endTime;
    private String clazzText;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getClazzText() {
        return clazzText;
    }

    public void setClazzText(String clazzText) {
        this.clazzText = clazzText;
    }

    public ClassItem(String startTime, String endTime, String clazzText) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.clazzText = clazzText;
    }
}
