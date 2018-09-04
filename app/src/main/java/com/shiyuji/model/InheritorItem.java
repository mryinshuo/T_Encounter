package com.shiyuji.model;

public class InheritorItem {
    private int head;
    private String realName;
    private String name;
    private String text;

    public InheritorItem(int head, String realName, String name, String text) {
        this.head = head;
        this.realName = realName;
        this.name = name;
        this.text = text;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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
}
