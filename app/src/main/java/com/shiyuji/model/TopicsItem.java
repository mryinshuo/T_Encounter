package com.shiyuji.model;

public class TopicsItem {
    private String title;
    private String content;
    private int img;
    private String likeNum;
    private String commentNum;
    private String shareNum;

    public TopicsItem(String title, String content, int img, String likeNum, String commentNum, String shareNum) {
        this.title = title;
        this.content = content;
        this.img = img;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
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
