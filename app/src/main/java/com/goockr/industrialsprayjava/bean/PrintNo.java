package com.goockr.industrialsprayjava.bean;

public class PrintNo {
    String no;
    String value;
    String limit1;
    String limit2;
    boolean isResrt;

    public PrintNo(String no, String value, String limit1, String limit2, boolean isResrt) {
        this.no = no;
        this.value = value;
        this.limit1 = limit1;
        this.limit2 = limit2;
        this.isResrt = isResrt;
    }

    public PrintNo(String value, String limit1, String limit2) {
        this.value = value;
        this.limit1 = limit1;
        this.limit2 = limit2;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLimit1() {
        return limit1;
    }

    public void setLimit1(String limit1) {
        this.limit1 = limit1;
    }

    public String getLimit2() {
        return limit2;
    }

    public void setLimit2(String limit2) {
        this.limit2 = limit2;
    }

    public boolean isResrt() {
        return isResrt;
    }

    public void setResrt(boolean resrt) {
        isResrt = resrt;
    }
}
