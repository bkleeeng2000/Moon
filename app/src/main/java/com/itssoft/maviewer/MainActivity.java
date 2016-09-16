package com.itssoft.maviewer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Source source;
    private URL URL;
    private int locate;

    ListViewAdapter adapter;


    ArrayList<ListData> mListData = new ArrayList<>();


    Bitmap bitmap;
    String baseURL = "http://cfile9.uf.tistory.com/image/2126EC49563E11E6370FF8";
    String URL_PRIMARY = "http://marumaru.in";
    String PLUS_URL = "/?c=26&p=1";
//    String URL_PRIMARY = "http://www.dongbaek.hs.kr";
//    String PLUS_URL = "/main.php?menugrp=030100&master=bbs&act=list&master_sid=7";


    private ConnectivityManager cManager;
    private NetworkInfo mobile;
    private NetworkInfo wifi;

    String url = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView listView;

        //Adapter 생성
        adapter = new ListViewAdapter();

        //리스트뷰 참조/Adapter달기
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        url = URL_PRIMARY + PLUS_URL;

        if (isInternetCon()) { //false 반환시 if 문안의 로직 실행
            Toast.makeText(MainActivity.this, "인터넷에 연결되지않아 불러오기를 중단합니다.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            try {
                process();
                adapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }

        //======
        //Thread
        //======
        /*Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    ImageLoad(url);
                } catch (IOException ex) {
                }
            }
        };
        mThread.start();
        try {
            mThread.join();
//            adapter.addItem(bitmap, "Text");
        } catch (InterruptedException ex) {

        }*/

        listView.setOnItemClickListener(mItemClickListener);

    }


    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);
            ListViewAdapter adapter = (ListViewAdapter) parent.getAdapter();

            String titleStr = item.getTitle();
            Bitmap iconDrawble = item.getIcon();

            Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_LONG).show();
            Intent intentSubActivity = new Intent(MainActivity.this, Viewer.class);
            startActivity(intentSubActivity);


        }
    };


    public void ImageLoad(String a) throws IOException {//throws는 예외를 ImageLoad를 호출하는 메소드에게 넘김

        Handler Progress = new Handler(Looper.getMainLooper()); //네트워크 쓰레드와 별개로 따로 핸들러를 이용하여 쓰레드를 생성한다.

        Progress.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog = ProgressDialog.show(MainActivity.this, "", "게시판 정보를 가져오는중 입니다.");
            }
        }, 0);

        try {
            URL = new URL(url);
            InputStream html = URL.openStream();
            source = new Source(new InputStreamReader(html, "utf-8")); //소스를 UTF-8 인코딩으로 불러온다.
            source.fullSequentialParse(); //순차적으로 구문분석
        } catch (Exception e) {
            Log.d("ERROR", e + "");
        }

        List<StartTag> tabletags = source.getAllStartTags(HTMLElementName.DIV); // DIV 타입의 모든 태그들을 불러온다.

        for (int arrnum = 0; arrnum < tabletags.size(); arrnum++) { //DIV 모든 태그중 bbsContent 태그가 몇번째임을 구한다.
            if (tabletags.get(arrnum).toString().equals("<div id=\"boardList\">")) {
//                    if (tabletags.get(arrnum).toString().equals("<div class=\"bbsContent\">")) {
                locate = arrnum; //DIV 클래스가 bbsContent 면 arrnum 값을 BBSlocate 로 몇번째인지 저장한다.
                Log.d("BBSLOCATES", arrnum + ""); //arrnum 로깅
                break;
            }
        }


//                Element BBS_DIV = (Element) source.getAllElements(HTMLElementName.DIV).get(locate); //BBSlocate 번째 의 DIV 를 모두 가져온다.

        Element MR_DIV = (Element) source.getAllElements(HTMLElementName.DIV).get(locate); //BBSlocate 번째 의 DIV 를 모두 가져온다.
        Element MR_FORM = (Element) MR_DIV.getAllElements(HTMLElementName.FORM).get(0);
        Element MR_TABLE = (Element) MR_FORM.getAllElements(HTMLElementName.TABLE).get(0); //테이블 접속
        Element MR_TBODY = (Element) MR_TABLE.getAllElements(HTMLElementName.TBODY).get(0); //데이터가 있는 TBODY 에 접속


//                Element BBS_TABLE = (Element) MR_DIV.getAllElements(HTMLElementName.TABLE).get(0); //테이블 접속
//                Element BBS_TBODY = (Element) BBS_TABLE.getAllElements(HTMLElementName.TBODY).get(0); //데이터가 있는 TBODY 에 접속


        for (int C_TR = 4; C_TR < MR_TBODY.getAllElements(HTMLElementName.TR).size(); C_TR++) { //여기서는 이제부터 게시된 게시물 데이터를 불러와 게시판 인터페이스를 구성할 것이다.

            // 소스의 효율성을 위해서는 for 문을 사용하는것이 좋지만 , 이해를 돕기위해 소스를 일부로 늘려 두었다.

            try {
                Element MR_TR = (Element) MR_TBODY.getAllElements(HTMLElementName.TR).get(C_TR); //TR 접속

                Element MR_TD = (Element) MR_TR.getAllElements(HTMLElementName.TD).get(1); //타입 을 불러온다.

                Element MR_info = (Element) MR_TD.getAllElements(HTMLElementName.A).get(0); //URL(herf) TITLE(title) 을 담은 정보를 불러온다.
                String MR_url = MR_info.getAttributeValue("href"); //a 태그의 herf 는 BCS_url 로 선언

//                        Element IMGURL_info = (Element) MR_info.getAllElements(HTMLElementName.DIV).get(0);

//                        PLUS_URL = IMGURL_info.getAttributeValue("style").substring(21);





                        /*Element BC_a = (Element) BC_info.getAllElements(HTMLElementName.A).get(0); //BC_info 안의 a 태그를 가져온다.
                        String BCS_url = BC_a.getAttributeValue("href"); //a 태그의 herf 는 BCS_url 로 선언
                        String BCS_title = BC_a.getAttributeValue("title"); //a 태그의 title 은 BCS_title 로 선언

                        Element BC_writer = (Element) BBS_TR.getAllElements(HTMLElementName.TD).get(3); //글쓴이를 불러온다.
                        Element BC_date = (Element) BBS_TR.getAllElements(HTMLElementName.TD).get(4); // 날짜를 불러온다.

                        String BCS_type = BC_TD.getContent().toString(); // 타입값을 담은 엘레먼트의 컨텐츠를 문자열로 변환시켜 가져온다.
                        String BCS_writer = BC_writer.getContent().toString(); // 작성자값을 담은 엘레먼트의 컨텐츠를 문자열로 변환시켜 가져온다.
                        String BCS_date = BC_date.getContent().toString(); // 작성일자값을 담은 엘레먼트의 컨텐츠를 문자열로 변환시켜 가져온다.*/

                ImageLoad(URL_PRIMARY + MR_url);
                adapter.addItem(bitmap, "123");


            } catch (Exception e) {
                Log.d("BCSERROR", e + "");
            }
        }
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged(); //모든 작업이 끝나면 리스트 갱신
                progressDialog.dismiss(); //모든 작업이 끝나면 다이어로그 종료
            }
        }, 0);


    }

}.start();


        }
        }
