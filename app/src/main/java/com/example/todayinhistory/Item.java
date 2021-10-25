package com.example.todayinhistory;

public class Item {
    private String text;
    private String detail;
    private String title;

    public Item(String text, String detail,String title) {
        this.text = text;
        this.detail = detail;
        this.title = title;
    }

    public String gettext() {
        return text;
    }

    public void settext(String text) {
        this.text = text;
    }

    public String getdetail() {
        return detail;
    }

    public void setdetail(String detail) {
        this.detail = detail;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }
}
