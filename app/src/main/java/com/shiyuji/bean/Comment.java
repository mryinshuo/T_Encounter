package com.shiyuji.bean;

public class Comment {
    private int id;
    private int vid;
    private String uid;
    private String content;
    private String time;
    private String comment_number;
    private String praise_number;
    private String love_number;
    private String forward_number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment_number() {
        return comment_number;
    }

    public void setComment_number(String comment_number) {
        this.comment_number = comment_number;
    }

    public String getPraise_number() {
        return praise_number;
    }

    public void setPraise_number(String praise_number) {
        this.praise_number = praise_number;
    }

    public String getLove_number() {
        return love_number;
    }

    public void setLove_number(String love_number) {
        this.love_number = love_number;
    }

    public String getForward_number() {
        return forward_number;
    }

    public void setForward_number(String forward_number) {
        this.forward_number = forward_number;
    }
}
