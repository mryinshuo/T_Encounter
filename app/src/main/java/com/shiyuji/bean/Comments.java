package com.shiyuji.bean;

import android.content.Intent;
import android.util.Log;

import com.shiyuji.Application.MyApplication;
import com.shiyuji.EditVideo;
import com.shiyuji.IndexActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Comments {
    static public void addComment(String uid,int vid,String content){

//  192.168.3.138 这个ip地址是电脑Ipv4 地址 /20170112 是服务端的项目名称  /login/toJsonMain 是@RequestMapping的地址
        String urlPath= MyApplication.getMYURL()+"video/addComment.action";
        //    String urlPath="http://192.168.42.207:8080/20170112/login/toJsonMain.action"; 这个是实体机(手机)的端口
        final URL url;
        Log.d("addComment", "addComment函数");
        try {
            url=new URL(urlPath);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("vid",vid);
            jsonObject.put("uid",uid);  //参数put到json串里
            jsonObject.put("content",content);
            /*params.put("image", encodedString);*/
            Log.d("addComment", "addComment：");
            //JSONObject Authorization =new JSONObject();
            //   Authorization.put("po类名",jsonObject 即po的字段)

            final String Allcontent=String.valueOf(jsonObject);  //json串转string类型

            new Thread(new Runnable() {
                @Override
                public void run(){
                    try {
                        HttpURLConnection conn=(HttpURLConnection) url.openConnection(); //开启连接
                        conn.setConnectTimeout(5000);
                        Log.d("addComment", "start conn");
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("ser-Agent", "Fiddler");
                        conn.setRequestProperty("Content-Type","application/json");
                        //写输出流，将要转的参数写入流里
                        Log.d("addComment", "start os");
                        OutputStream os=conn.getOutputStream();
                        os.write(Allcontent.getBytes()); //字符串写进二进流
                        os.close();
                        int code=conn.getResponseCode();
                        Log.d("addComment", "获取code:"+code);

                    }  catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
            Log.d("addComment", "连接成功");

        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
