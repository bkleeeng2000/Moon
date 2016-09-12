package com.itssoft.maviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Bitmap bitmap;
    String baseURL = "http://cfile9.uf.tistory.com/image/2126EC49563E11E6370FF8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView;
        ListViewAdapter adapter;

        //Adapter 생성
        adapter = new ListViewAdapter();

        //리스트뷰 참조/Adapter달기
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        //======
        //Thread
        //======
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    ImageLoad();
                } catch (IOException ex) {
                }
            }
        };
        mThread.start();
        try {
            mThread.join();
            adapter.addItem(bitmap, "Test");
        } catch (InterruptedException ex) {

        }

        listView.setOnItemClickListener(mItemClickListener);

    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

            String titleStr = item.getTitle();
            Bitmap iconDrawble = item.getIcon();
        }
    };




    public void ImageLoad() throws IOException {//throws는 예외를 ImageLoad를 호출하는 메소드에게 넘김
        URL url = new URL(baseURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.connect();

        InputStream is = conn.getInputStream();
        bitmap = BitmapFactory.decodeStream(is);
    }
}
