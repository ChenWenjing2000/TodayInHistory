package com.example.todayinhistory;

public class Item {
    private String text;
    private String title;
    private String href;

    public Item(String text, String title,String href) {
        this.text = text;
        this.title = title;
        this.href = href;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
