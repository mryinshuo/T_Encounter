package com.shiyuji.model;

public class IndexMessageItem {
    private int head;
    private String name;
    private String text;
    private String time;

    public IndexMessageItem(int head, String name, String text, String time) {
        this.head = head;
        this.name = name;
        this.text = text;
        this.time = time;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
