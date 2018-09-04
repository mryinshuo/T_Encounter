package com.shiyuji.model;

public class MyVideosItem {
    private int image;
    private String text;
    private String playNum;
    private String likeNum;
    private String commentNum;

    public MyVideosItem(int image, String text, String playNum, String likeNum, String commentNum) {
        this.image = image;
        this.text = text;
        this.playNum = playNum;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlayNum() {
        return playNum;
    }

    public void setPlayNum(String playNum) {
        this.playNum = playNum;
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
}
