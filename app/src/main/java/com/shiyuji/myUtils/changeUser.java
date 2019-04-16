package com.shiyuji.myUtils;

import android.util.Log;

import com.shiyuji.Application.MyApplication;
import com.shiyuji.bean.User;
import com.shiyuji.bean.Video;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class changeUser {

    public static void init(User user,String pointUrl){
        String urlPath= MyApplication.getMYURL()+pointUrl;
        final URL url;
        Log.d("ResponseCode", "init函数");
        int id=1;
        try {
            url=new URL(urlPath);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("phone",user.getPhone());
            jsonObject.put("sex", user.getSex());
            jsonObject.put("birthday",user.getBirthday());
            jsonObject.put("name", user.getName());
            jsonObject.put("signature",user.getSignature());
            //  Log.d("ResponseCodephone", "myphone："+uid);
            final String content=String.valueOf(jsonObject);  //json串转string类型

            new Thread(new Runnable() {
                @Override
                public void run(){
                    try {
                        HttpURLConnection conn=(HttpURLConnection) url.openConnection(); //开启连接
                        conn.setConnectTimeout(5000);
                        Log.d("ResponseCode", "start conn");
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("ser-Agent", "Fiddler");
                        conn.setRequestProperty("Content-Type","application/json");
                        //写输出流，将要转的参数写入流里
                        Log.d("ResponseCode", "start os");
                        OutputStream os=conn.getOutputStream();
                        os.write(content.getBytes()); //字符串写进二进流
                        os.close();
                        int code=conn.getResponseCode();
                        Log.d("ResponseCode", "获取code:"+code);
                      /*  Intent it=new Intent(EditVideo.this, IndexActivity.class);
                        startActivity(it);*/
                    }  catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
            Log.d("ResponseCode", "连接成功");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
