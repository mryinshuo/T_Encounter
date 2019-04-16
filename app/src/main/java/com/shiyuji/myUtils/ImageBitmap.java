package com.shiyuji.myUtils;

import android.graphics.Bitmap;

import com.shiyuji.bean.GetImg;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ImageBitmap {

    public static Bitmap getImageBitmap(String imageUrl){
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Bitmap imageBitmap = null;
        GetImg myCallable1 = new GetImg(imageUrl);
        Future<Bitmap> submit1 = executor.submit(myCallable1);

        try {
            imageBitmap =submit1.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return imageBitmap;
    }
}
