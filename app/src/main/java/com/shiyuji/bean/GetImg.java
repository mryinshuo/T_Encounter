package com.shiyuji.bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiyuji.Application.MyApplication;

import org.json.JSONObject;

import java.io.File;
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

public class GetImg  implements Callable<Bitmap> {
    private String imgUrl;
   static private ACache acache=ACache.get(MyApplication.getContext());//创建ACache组件;//缓存框架
    public GetImg(String imgUrl){
        this.imgUrl = imgUrl;
    }
    public GetImg(){

    }
        @Override
        public Bitmap call() throws Exception {

            try {
                String path = MyApplication.getMYURL()+"upload/"+imgUrl;
                //2:把网址封装为一个URL对象
                URL url = new URL(path);
                //3:获取客户端和服务器的连接对象，此时还没有建立连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //4:初始化连接对象
                conn.setRequestMethod("GET");
                //设置连接超时
                conn.setConnectTimeout(8000);
                //设置读取超时
                conn.setReadTimeout(8000);
                //5:发生请求，与服务器建立连接
                conn.connect();
                //如果响应码为200，说明请求成功
                if(conn.getResponseCode() == 200)
                {
                    //获取服务器响应头中的流
                    InputStream is = conn.getInputStream();
                    //读取流里的数据，构建成bitmap位图
                    Bitmap bm = BitmapFactory.decodeStream(is);

                    return ThumbnailUtils.extractThumbnail(bm, 180, 100, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    static public ArrayList<Video> getConnect(String id){
        //线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);
        MyCallable   myCallable;
        if (id!=null){
            myCallable = new MyCallable();
            myCallable.setId(id);
        }else {
            myCallable = new MyCallable();
        }
        Future<ArrayList<Video>> submit = executor.submit(myCallable);
        ArrayList<Video> videos=null;
        try {
            //第一步：创建线程
            videos = submit.get();
            /* NetUtils.getList();*/
            return videos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static  public class MyCallable implements Callable<ArrayList<Video>>{
        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
        @Override
        public ArrayList<Video> call() throws Exception {
            ArrayList<Video> list ;
            String urlPath=null;
            String content=null;
            if (id!=null){
                urlPath = MyApplication.getMYURL()+"video/getOne.action";
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("vid",id);  //参数put到json串里
                content=String.valueOf(jsonObject);  //json串转string类型
            }else {
                urlPath = MyApplication.getMYURL()+"video/getAll.action";
            }
            final URL url;
            url=new URL(urlPath);
            Log.d("ResponseCode", "getConnect函数aaa：");
            HttpURLConnection conn=(HttpURLConnection) url.openConnection(); //开启连接
            conn.setConnectTimeout(3000);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("ser-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type","application/json");
            Log.d("ResponseCode", "start os");
            if (content!=null) {////如果不为空，还需要向服务器传ID
                OutputStream os = conn.getOutputStream();
                os.write(content.getBytes()); //字符串写进二进流
                os.close();
            }
            /*conn.connect();
            int code=conn.getResponseCode();
            Log.d("ResponseCode", "获取code:"+code);*/
            InputStream inputStream=conn.getInputStream();

            // 调用自己写的NetUtils() 将流转成string类型
            String json= NetUtils.readString(inputStream);
            if (id==null){
                acache.put("recommendItems", json, 60*60);//将数据存入缓存中，有效时间设置为1小时
            }
            Gson gson=new Gson();  //引用谷歌的json包
            list=gson.fromJson(json, new TypeToken<List<Video>>(){}.getType()); //谷歌的解析json的方法
            return list;
        }
    }

    }



