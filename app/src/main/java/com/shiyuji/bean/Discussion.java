package com.shiyuji.bean;

public class Discussion {
    private int id;
    private String cateId;
    private String uid;
    private String title;
    private String intro;
    private String time;
    private String comment_number;
    private String praise_number;
    private String forward_number;
    private String  imgUrl;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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


    public String getForward_number() {
        return forward_number;
    }

    public void setForward_number(String forward_number) {
        this.forward_number = forward_number;
    }
}

