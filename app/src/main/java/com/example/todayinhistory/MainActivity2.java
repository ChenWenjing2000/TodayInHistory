package com.example.todayinhistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity{

    private String TAG = "MainActivity2";
    String text;
    String title;
    String url;
    String moredetail;
    Handler handler;
    TextView detailtext;
    TextView detailtitle;
    TextView detailtitle2;
    ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent more = getIntent();
        text = more.getStringExtra("text");
        title = more.getStringExtra("title");
        url = more.getStringExtra("detail");
        detailtext = findViewById(R.id.detailtext);
        detailtitle = findViewById(R.id.detailtitle);
        detailtitle2 = findViewById(R.id.detailtitle2);
        progressBar2 = findViewById(R.id.progressBar2);

        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg2) {
                if(msg2.what == 2) {
                    moredetail = (String) msg2.obj;
                    detailtitle.setText(text);
                    detailtitle2.setText(title);
                    detailtext.setMovementMethod(ScrollingMovementMethod.getInstance());
                    String[] lineArr = moredetail.split("<br>");
                    String newline="";
                    for (int j = 0; j < lineArr.length; j++)
                    {
                        if (j<lineArr.length)
                        {
                            newline=newline+lineArr[j]+"\n";
                        }
                        else
                            newline=newline+lineArr[j];
                    }
                    detailtext.setText(newline);
                    progressBar2.setVisibility(View.GONE);
                }
                super.handleMessage(msg2);
            }
        };

        Test2 test2 = new Test2(url);
        test2.setHandler(handler);
        new Thread(test2).start();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        super.onOptionsItemSelected(item);
        return true;
    }

}