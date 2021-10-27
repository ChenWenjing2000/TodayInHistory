package com.example.todayinhistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.AndroidException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

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
        url = more.getStringExtra("href");
        detailtext = findViewById(R.id.detailtext);
        detailtitle = findViewById(R.id.detailtitle);
        detailtitle2 = findViewById(R.id.detailtitle2);
        progressBar2 = findViewById(R.id.progressBar2);

        DBManager dbManager = new DBManager(this);
        Item item1 = new Item(text, title, url);
        dbManager.addr(item1);

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg2) {
                if (msg2.what == 2) {
                    moredetail = (String) msg2.obj;
                    detailtitle.setText(text);
                    detailtitle2.setText(title);
                    detailtext.setMovementMethod(ScrollingMovementMethod.getInstance());
                    String[] lineArr = moredetail.split("<br>");
                    String newline = "";
                    for (int j = 0; j < lineArr.length; j++) {
                        if (j < lineArr.length) {
                            newline = newline + lineArr[j] + "\n";
                        } else
                            newline = newline + lineArr[j];
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        DBManager dbManager = new DBManager(this);
        final MenuItem item = menu.findItem(R.id.collect);
        if (dbManager.findById(url) == null) {
            item.setIcon(getResources().getDrawable(android.R.drawable.btn_star_big_off));
        } else {
            item.setIcon(getResources().getDrawable(android.R.drawable.btn_star_big_on));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DBManager dbManager = new DBManager(this);
        Item item1 = new Item(text, title, url);
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.collect) {
            if (dbManager.findById(url) == null) {
                dbManager.addc(item1);
                item.setIcon(getResources().getDrawable(android.R.drawable.btn_star_big_on));
            } else {
                dbManager.deletec(url);
                item.setIcon(getResources().getDrawable(android.R.drawable.btn_star_big_off));
            }
        }
        super.onOptionsItemSelected(item);
        return true;
    }
}

