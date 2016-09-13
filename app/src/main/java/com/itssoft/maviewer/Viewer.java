package com.itssoft.maviewer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Toast;

/**
 * Created by 채호 on 2016-09-13.
 */
//===========================================================
//************************Viewer 화면************************
//===========================================================
public class Viewer extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestWindowFeature(Window.FEATURE_NO_TITLE);


        Window win = getWindow();
        win.setContentView(R.layout.viewer);//window에 viewer_under을 깐다.

        LayoutInflater inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linear = (LinearLayout)inflater.inflate(R.layout.viewer_over,null);

        LinearLayout.LayoutParams paramslinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
        win.addContentView(linear,paramslinear);

        //
        //버튼 처리
        //
        Button space_0 = (Button)findViewById(R.id.space_0);
        space_0.setOnClickListener(mOnClickListener);

        Button space_1 = (Button) findViewById(R.id.space_1);
        space_1.setOnClickListener(mOnClickListener);

        Button space_2 = (Button)findViewById(R.id.space_2);
        space_2.setOnClickListener(mOnClickListener);

    }//onCreate end

    Space.OnClickListener mOnClickListener = new View.OnClickListener(){
      public void onClick(View v){
          switch (v.getId()){
              case R.id.space_0 : Toast.makeText(getApplicationContext(),"Left",Toast.LENGTH_LONG).show();
                  break;
              case R.id.space_1 : Toast.makeText(getApplicationContext(),"Center",Toast.LENGTH_LONG).show();
                  break;
              case R.id.space_2 : Toast.makeText(getApplicationContext(),"Right",Toast.LENGTH_LONG).show();
                  break;
          }
      }
    };
}
