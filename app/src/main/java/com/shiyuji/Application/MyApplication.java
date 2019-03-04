package com.shiyuji.Application;

import android.content.Context;

import com.mob.MobSDK;

import org.litepal.LitePalApplication;

import java.util.HashMap;

public class MyApplication extends LitePalApplication {
    private static MyApplication mApp;
    private static Context context;
    public HashMap<String, String> hashMapInfo = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        context = getApplicationContext();
        MobSDK.init(this);
    }

    public static MyApplication getInstance() {
        return mApp;
    }

    public static Context getContext() {
        return context;
    }
}
