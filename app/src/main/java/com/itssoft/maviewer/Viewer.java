package com.itssoft.maviewer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.LinearLayout;

/**
 * Created by 채호 on 2016-09-13.
 */
//===================
//****Viewer 화면****
//===================
public class Viewer extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Window win = getWindow();
        win.setContentView(R.layout.viewer);//window에 viewer_under을 깐다.

        LayoutInflater inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linear = (LinearLayout)inflater.inflate(R.layout.viewer_over,null);

        LinearLayout.LayoutParams paramslinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
        win.addContentView(linear,paramslinear);

    }
}
