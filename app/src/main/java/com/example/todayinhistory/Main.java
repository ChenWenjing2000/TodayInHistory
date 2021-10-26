package com.example.todayinhistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.AdapterView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Main extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_);

        ViewPager2 viewPager = findViewById(R.id.viewPage2);
        MyPageAdapter pageAdapter = new MyPageAdapter(this);
        viewPager.setAdapter(pageAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("OBJECT " + (position + 1))
        ).attach();

        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //设置tab标题
                if(position == 0){
                    tab.setText("History");
                }else if(position == 1){
                    tab.setText("Collection");
                }else{
                    tab.setText("Records");
                }
            }
        });
        mediator.attach();
    }

    public static class DBHelper extends SQLiteOpenHelper {

        private static final int VERSION = 1;
        private static final String DB_NAME = "todayinhistory.db";
        public static final String TB_NAME_1 = "collection";
        public static final String TB_NAME_2 = "records";


        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }
        public DBHelper(Context context) {
            super(context,DB_NAME,null,VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+TB_NAME_1+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,TEXT TEXT,TITLE TEXT,HREF TEXT)");
            db.execSQL("CREATE TABLE "+TB_NAME_2+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,TEXT TEXT,TITLE TEXT,HREF TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
        }

    }
}