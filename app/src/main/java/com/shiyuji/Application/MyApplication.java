package com.shiyuji.Application;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.HashMap;

public class MyApplication extends LitePalApplication {
    private static MyApplication mApp;
    private static Context context;
    static public String phone;
    static public String headUrl;
    static public String userName;
    static public String ADRESS;
    private static String MYURL="http://39.105.45.66/Shiyuji_war/";
    public HashMap<String,String> hashMapInfo = new HashMap<>();
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        context = getApplicationContext();
        MobSDK.init(this);
    }
    public static MyApplication getInstance(){
            return mApp;
    }
    public static Context getContext(){
        return context;
    }
    public static String getMYURL(){
        return MYURL;
    }


}
