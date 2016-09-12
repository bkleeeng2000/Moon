package com.itssoft.maviewer;

import android.graphics.Bitmap;

/**
 * Created by 채호 on 2016-09-12.
 */
public class ListViewItem {
    private Bitmap iconDrawble;
    private String titlestr;

    //*******************************
    //아이콘과 타이틀을 가져오는 부분
    //*******************************
    public void setIcon(Bitmap icon) {
        iconDrawble = icon;
    }

    public void setTitle(String title) {
        titlestr = title;
    }

    //*******************************
    //아이콘과 타이틀을 넣는 부분
    //*******************************
    public Bitmap getIcon() {
        return this.iconDrawble;
    }

    public String getTitle() {
        return this.titlestr;
    }
}
