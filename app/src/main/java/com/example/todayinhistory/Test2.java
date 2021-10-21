package com.example.todayinhistory;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Test2 implements Runnable{

    private String TAG = "MainActivity_Test2";
    Handler handler;
    String url;

    public Test2( String url){
        this.url = url;
    }

    @Override
    public void run() {
        try {
            Log.i(TAG, "run: "+url);
            Document doc = Jsoup.connect("http://today.911cha.com/"+url).get();
            Elements p = doc.getElementsByTag("p");
            String moredetail = p.get(0).html();
            Log.i(TAG, "run: "+moredetail);

            Message msg2 = handler.obtainMessage(2,moredetail);
            handler.sendMessage(msg2);
            Log.i(TAG, "run: sending msg2!");

        }catch (IOException e) {
        e.printStackTrace();
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
