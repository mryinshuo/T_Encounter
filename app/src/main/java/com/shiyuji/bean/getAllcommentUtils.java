package com.shiyuji.bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiyuji.Application.MyApplication;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class getAllcommentUtils {

    static public ArrayList<Comment> getConnect(int id){
        //线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);
        getAllcommentUtils.MyCallable myCallable;
        myCallable = new getAllcommentUtils.MyCallable();
        myCallable.setId(id);
        Future<ArrayList<Comment>> submit = executor.submit(myCallable);
        ArrayList<Comment> comments=null;
        try {
            //第一步：创建线程
            comments = submit.get();
            /* NetUtils.getList();*/
            return comments;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static  public class MyCallable implements Callable<ArrayList<Comment>>{
        int id;
        String target;

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
        @Override
        public ArrayList<Comment> call() throws Exception {
            ArrayList<Comment> list ;
            String urlPath=null;
            String content=null;
                urlPath = MyApplication.getMYURL()+"video/getAllComment.action";
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("vid",id);  //参数put到json串里
                content=String.valueOf(jsonObject);  //json串转string类型
            final URL url;
            url=new URL(urlPath);
            Log.d("ResponseCode", "getConnect函数aaa：");
            HttpURLConnection conn=(HttpURLConnection) url.openConnection(); //开启连接
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("ser-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type","application/json");
            Log.d("ResponseCode", "start os");
            if (content!=null) {
                OutputStream os = conn.getOutputStream();
                os.write(content.getBytes()); //字符串写进二进流
                os.close();
                int code=conn.getResponseCode();
                Log.d("ResponseCode", "获取code:"+code);
            }

            InputStream inputStream=conn.getInputStream();
            // 调用自己写的NetUtils() 将流转成string类型
            String json= NetUtils.readString(inputStream);
            Gson gson=new Gson();  //引用谷歌的json包
            list=gson.fromJson(json, new TypeToken<ArrayList<Comment>>(){}.getType()); //谷歌的解析json的方法
            return list;
        }
    }

}

