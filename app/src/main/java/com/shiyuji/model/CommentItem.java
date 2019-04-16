package com.shiyuji.model;

import android.graphics.Bitmap;

import com.shiyuji.bean.GetImg;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CommentItem {
    private Bitmap imageUrl1;
    private String name;
    private String id;
    private String time;
    private boolean isFollowed;
    private String text;
    private boolean isLiked;
    private int likeNum;
    ExecutorService executor = Executors.newFixedThreadPool(10);
    public CommentItem(String imageUrl1, String name, String id, String time, boolean isFollowed, String text, boolean isLiked, int likeNum) {

        GetImg myCallable1 = new GetImg(imageUrl1);
        Future<Bitmap> submit1 = executor.submit(myCallable1);
        try {
            this.imageUrl1 =submit1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        this.name = name;
        this.id = id;
        this.time = time;
        this.isFollowed = isFollowed;
        this.text = text;
        this.isLiked = isLiked;
        this.likeNum = likeNum;
    }

    public Bitmap getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(Bitmap imageUrl1) {
        this.imageUrl1 = imageUrl1;
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
