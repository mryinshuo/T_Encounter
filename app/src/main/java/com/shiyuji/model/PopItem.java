package com.shiyuji.model;

public class PopItem {
    private int id;
    private String content;

    public PopItem(int id,String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
