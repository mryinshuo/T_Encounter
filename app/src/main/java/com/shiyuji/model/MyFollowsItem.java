package com.shiyuji.model;

public class MyFollowsItem {
    private int image;
    private String name;
    private String text;
    private String fanNum;
    private String followNum;
    private boolean isFollowed = true;

    public MyFollowsItem(int image, String name, String text, String fanNum, String followNum) {
        this.image = image;
        this.name = name;
        this.text = text;
        this.fanNum = fanNum;
        this.followNum = followNum;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    public String getFanNum() {
        return fanNum;
    }

    public void setFanNum(String fanNum) {
        this.fanNum = fanNum;
    }

    public String getFollowNum() {
        return followNum;
    }

    public void setFollowNum(String followNum) {
        this.followNum = followNum;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }
}
