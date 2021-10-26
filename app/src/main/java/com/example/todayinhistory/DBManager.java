package com.example.todayinhistory;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private Main.DBHelper dbHelper;
    private String TB_NAME_1;
    private String TB_NAME_2;

    public DBManager(Context context) {
        dbHelper = new Main.DBHelper(context);
        TB_NAME_1 = Main.DBHelper.TB_NAME_1;
        TB_NAME_2 = Main.DBHelper.TB_NAME_2;
    }

    public void addc(Item item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("text", item.getText());
        values.put("title", item.getTitle());
        values.put("href", item.getHref());
        db.insert(TB_NAME_1, null, values);
        db.close();
    }

    public void addr(Item item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("text", item.getText());
        values.put("title", item.getTitle());
        values.put("href", item.getHref());
        db.insert(TB_NAME_2, null, values);
        db.close();
    }

    public void deletec(String href){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TB_NAME_1, "HREF=?", new String[]{href});
        db.close();
    }

    @SuppressLint("Range")
    public Item findById(String href){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TB_NAME_1, null, "HREF=?", new String[]{href}, null, null, null);
        Item rateItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            rateItem = new Item(null,null,null);
            rateItem.setText(cursor.getString(cursor.getColumnIndex("TEXT")));
            rateItem.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
            cursor.close();
        }
        db.close();
        return rateItem;
    }

    @SuppressLint("Range")
    public ArrayList<Item> listcAll(){
        ArrayList<Item> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TB_NAME_1, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<Item>();
            while(cursor.moveToNext()){
                Item item = new Item(null,null,null);
                item.setText(cursor.getString(cursor.getColumnIndex("TEXT")));
                item.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
                item.setHref(cursor.getString(cursor.getColumnIndex("HREF")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;
    }

    @SuppressLint("Range")
    public ArrayList<Item> listrAll(){
        ArrayList<Item> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TB_NAME_2, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<Item>();
            while(cursor.moveToNext()){
                Item item = new Item(null,null,null);
                item.setText(cursor.getString(cursor.getColumnIndex("TEXT")));
                item.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
                item.setHref(cursor.getString(cursor.getColumnIndex("HREF")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;

    }

}
