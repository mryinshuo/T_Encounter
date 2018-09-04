package com.shiyuji.model;

public class MyTopicsItem {
    private String title;
    private String text;
    private int image;
    private String likeNum;
    private String commentNum;
    private String shareNum;

    public MyTopicsItem(String title, String text, int image, String likeNum, String commentNum, String shareNum) {
        this.title = title;
        this.text = text;
        this.image = image;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.shareNum = shareNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getShareNum() {
        return shareNum;
    }

    public void setShareNum(String shareNum) {
        this.shareNum = shareNum;
    }
}
