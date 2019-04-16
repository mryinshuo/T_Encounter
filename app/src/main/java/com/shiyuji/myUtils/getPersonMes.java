package com.shiyuji.myUtils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiyuji.Application.MyApplication;
import com.shiyuji.bean.Comment;
import com.shiyuji.bean.NetUtils;
import com.shiyuji.bean.User;
import com.shiyuji.bean.getAllcommentUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class getPersonMes {
    static public User getConnect(String phone){
        //线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);
        getPersonMes.MyCallable myCallable;
        myCallable = new getPersonMes.MyCallable();
        myCallable.setPhone(phone);
        Future<User> submit = executor.submit(myCallable);
        User PersonMes=null;
        try {
            //第一步：创建线程
            PersonMes = submit.get();
            /* NetUtils.getList();*/
            return PersonMes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static  public class MyCallable implements Callable<User> {
        String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public User call() throws Exception {
            String urlPath=null;
            String content=null;
            urlPath = MyApplication.getMYURL()+"user/getUser.action";
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("phone",phone);  //参数put到json串里
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

            OutputStream os = conn.getOutputStream();
            os.write(content.getBytes()); //字符串写进二进流
            os.close();
            int code=conn.getResponseCode();
            Log.d("ResponseCode", "获取code:"+code);
            InputStream inputStream=conn.getInputStream();
            // 调用自己写的NetUtils() 将流转成string类型
            String json= NetUtils.readString(inputStream);
            Gson gson=new Gson();  //引用谷歌的json包
            User user= gson.fromJson(json, new TypeToken<User>(){}.getType()); //谷歌的解析json的方法
            Log.d("ResponseCode", "headUrl:"+json);
            return user;
        }
    }
}
