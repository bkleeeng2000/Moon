package com.itssoft.maviewer;

/**
 * Created by 채호 on 2016-09-14.
 */
public class ListData { // 데이터를 받는 클래스

    public String mType;
    public String mTitle;
    public String mUrl;
    public String mWriter;
    public String mDate;


    public ListData() {


    }

    public ListData(String mType, String mTitle, String mUrl, String mWriter, String mDate) { //데이터를 받는 클래스 메서드
        this.mType = mType;
        this.mTitle = mTitle;
        this.mUrl = mUrl;
        this.mWriter = mWriter;
        this.mDate = mDate;

    }

}