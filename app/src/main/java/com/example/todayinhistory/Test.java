package com.example.todayinhistory;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Test implements Runnable {

    private Handler handler;

    private String TAG = "MainActivity_Test";
    int day ;
    SharedPreferences sp;
    ArrayList<Item> listItem;

    public Test( SharedPreferences sp){
        this.sp = sp;
    }
    @Override
    public void run() {
        if(sp.getInt("day", day) != day) {
            Log.i(TAG, "run: Thread");
            day = sp.getInt("day", day);
            Log.i(TAG, "run: day"+day);
            try {
                String url = "http://today.911cha.com/history_"+day+".html";
                Document doc = Jsoup.connect(url).get();
                Elements ps = doc.getElementsByTag("p");
                Element p = ps.get(0);
                Elements as = p.getElementsByTag("a");
                Log.i(TAG, "run: as"+as.size());
                listItem = new ArrayList<Item>();
                for (int i=0;i<as.size();i=i+3) {
                    String str1 = as.get(i).text()+as.get(i+1).text()+"\n"+as.get(i+2).text();
                    String str2 = as.get(i+2).attr("href").toString();
                    Item item = new Item(str1,str2);
                    listItem.add(item);
                }
                Log.i(TAG, "run: listItem"+listItem.size());
                Message msg = handler.obtainMessage(1,listItem);
                handler.sendMessage(msg);
                Log.i(TAG, "run: sending msg!");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
