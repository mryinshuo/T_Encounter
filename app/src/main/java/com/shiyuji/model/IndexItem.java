package com.shiyuji.model;

import android.graphics.Bitmap;

import com.shiyuji.bean.GetImg;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IndexItem {
    private Bitmap imageUrl1;
    private String text1;
    private Bitmap imageUrl2;
    private String text2;
    private int id1;
    private int id2;
    private int imageId1;
    private int imageId2;
    //线程池
    ExecutorService executor = Executors.newFixedThreadPool(10);

    public IndexItem(String imageUrl1, String text1, int id1,String imageUrl2, String text2,int id2) {
        GetImg myCallable1 = new GetImg(imageUrl1);
        GetImg myCallable2 = new GetImg(imageUrl2);
        Future<Bitmap> submit1 = executor.submit(myCallable1);
        Future<Bitmap> submit2 = executor.submit(myCallable2);
        try {
            this.imageUrl1 =submit1.get();
            this.imageUrl2 =submit2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        this.text1 = text1;
        this.text2 = text2;
        this.id1=id1;
        this.id2=id2;
    }
    public IndexItem(int imageId1, String text1, int imageId2, String text2) {
        this.imageId1=imageId1;
        this.imageId2=imageId2;
        this.text1 = text1;
        this.text2 = text2;
    }
    public int getId1() {
        return id1;
    }

    public int getImageId1() {
        return imageId1;
    }

    public void setImageId1(int imageId1) {
        this.imageId1 = imageId1;
    }

    public int getImageId2() {
        return imageId2;
    }

    public void setImageId2(int imageId2) {
        this.imageId2 = imageId2;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public Bitmap getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(Bitmap imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public void setImageUrl2(Bitmap imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public Bitmap getImageUrl2() {
        return imageUrl2;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}
