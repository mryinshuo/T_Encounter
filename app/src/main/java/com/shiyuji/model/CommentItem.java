package com.shiyuji.model;

public class CommentItem {
    private int head;
    private String name;
    private String id;
    private String time;
    private boolean isFollowed;
    private String text;
    private boolean isLiked;
    private int likeNum;

    public CommentItem(int head, String name, String id, String time, boolean isFollowed, String text, boolean isLiked, int likeNum) {
        this.head = head;
        this.name = name;
        this.id = id;
        this.time = time;
        this.isFollowed = isFollowed;
        this.text = text;
        this.isLiked = isLiked;
        this.likeNum = likeNum;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }
}
