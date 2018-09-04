package com.shiyuji.model;

public class ContactSettingItem {      // ContactSettings中对话框的实体类
    private int head;                       // 头像id
    private String text;                    // 消息内容
    private boolean isUser;                 // 消息方向

    public ContactSettingItem(int head, String text, boolean isUser) {
        this.head = head;
        this.text = text;
        this.isUser = isUser;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }
}
