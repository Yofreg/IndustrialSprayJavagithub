package com.goockr.industrialsprayjava.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PrintItem implements Parcelable{
    int triggerPrintChose;
    int printDelay;
    int muzzleChose;
    int muzzleInterval;
    int muzzleCount;
    int reversal;
    int overturn;
    int muzzleLevel;

    private double dianya;
    private int maikuang;

    public double getDianya() {
        return dianya;
    }

    public void setDianya(int dianya) {
        this.dianya = dianya;
    }

    public int getMaikuang() {
        return maikuang;
    }

    public void setMaikuang(int maikuang) {
        this.maikuang = maikuang;
    }

    public int getTriggerPrintChose() {
        return triggerPrintChose;
    }

    public void setTriggerPrintChose(int triggerPrintChose) {
        this.triggerPrintChose = triggerPrintChose;
    }

    public int getPrintDelay() {
        return printDelay;
    }

    public void setPrintDelay(int printDelay) {
        this.printDelay = printDelay;
    }

    public int getMuzzleChose() {
        return muzzleChose;
    }

    public void setMuzzleChose(int muzzleChose) {
        this.muzzleChose = muzzleChose;
    }

    public int getMuzzleInterval() {
        return muzzleInterval;
    }

    public void setMuzzleInterval(int muzzleInterval) {
        this.muzzleInterval = muzzleInterval;
    }

    public int getMuzzleCount() {
        return muzzleCount;
    }

    public void setMuzzleCount(int muzzleCount) {
        this.muzzleCount = muzzleCount;
    }

    public int getReversal() {
        return reversal;
    }

    public void setReversal(int reversal) {
        this.reversal = reversal;
    }

    public int getOverturn() {
        return overturn;
    }

    public void setOverturn(int overturn) {
        this.overturn = overturn;
    }

    public int getMuzzleLevel() {
        return muzzleLevel;
    }

    public void setMuzzleLevel(int muzzleLevel) {
        this.muzzleLevel = muzzleLevel;
    }

    public PrintItem(int printDelay, int muzzleChose, int muzzleInterval, int muzzleCount, int reversal, int overturn, int muzzleLevel, double dianya) {
        this.printDelay = printDelay;
        this.muzzleChose = muzzleChose;
        this.muzzleInterval = muzzleInterval;

        this.muzzleCount = muzzleCount;
        this.reversal = reversal;
        this.overturn = overturn;
        this.muzzleLevel = muzzleLevel;

        this.dianya = dianya;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(triggerPrintChose);
        dest.writeInt(printDelay);
        dest.writeInt(muzzleChose);
        dest.writeInt(muzzleInterval);
        dest.writeInt(muzzleCount);
        dest.writeInt(reversal);
        dest.writeInt(overturn);
        dest.writeInt(muzzleLevel);

    }


    protected PrintItem(Parcel in) {
        triggerPrintChose = in.readInt();
        printDelay = in.readInt();
        muzzleChose = in.readInt();
        muzzleInterval = in.readInt();
        muzzleCount = in.readInt();
        reversal = in.readInt();
        overturn = in.readInt();
        muzzleLevel = in.readInt();
        dianya = in.readInt();
    }

    public static final Creator<PrintItem> CREATOR = new Creator<PrintItem>() {
        @Override
        public PrintItem createFromParcel(Parcel in) {
            return new PrintItem(in);
        }

        @Override
        public PrintItem[] newArray(int size) {
            return new PrintItem[size];
        }
    };
}
