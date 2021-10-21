package com.example.todayinhistory;

public class Item {
    private String text;
    private String detail;

    public Item(String text, String detail) {
        this.text = text;
        this.detail = detail;
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
}
