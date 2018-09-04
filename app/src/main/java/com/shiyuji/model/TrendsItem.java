package com.shiyuji.model;

public class TrendsItem {
    private int head;
    private String name;
    private String time;
    private boolean isFollowed;
    private String content;
    private int image;
    private boolean isLiked;
    private String likeNum;
    private String commentNum;
    private String shareNum;

    public TrendsItem(int head, String name, String time, boolean isFollowed, String content, int image, boolean isLiked, String likeNum, String commentNum, String shareNum) {
        this.head = head;
        this.name = name;
        this.time = time;
        this.isFollowed = isFollowed;
        this.content = content;
        this.image = image;
        this.isLiked = isLiked;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.shareNum = shareNum;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
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
