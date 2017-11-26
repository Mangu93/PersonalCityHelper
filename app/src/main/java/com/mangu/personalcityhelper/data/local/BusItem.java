package com.mangu.personalcityhelper.data.local;

public class BusItem {
    private String mLine;
    private String mLineInfo;
    private String mId;
    BusItem() {
        super();
    }
    public BusItem(String mLine, String mLineInfo) {
        super();
        this.mLine = mLine;
        this.mLineInfo = mLineInfo;
    }
    public BusItem(String mLine, String mLineInfo, String mId) {
        super();
        this.mLine = mLine;
        this.mLineInfo = mLineInfo;
        this.mId = mId;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmLine() {
        return mLine;
    }

    public void setmLine(String mLine) {
        this.mLine = mLine;
    }

    public String getmLineInfo() {
        return mLineInfo;
    }

    public void setmLineInfo(String mLineInfo) {
        this.mLineInfo = mLineInfo;
    }
}
