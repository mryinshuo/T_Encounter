package com.shiyuji.ExitApp;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.shiyuji.Application.MyApplication;

public class BackPress {
    public static void BackPressed(final Activity activity) {       // 当触摸返回键时
        AlertDialog.Builder checkExit = new AlertDialog.Builder(MyApplication.getContext());      // 创建对话框
        checkExit.setMessage("退出程序？");
        checkExit.setCancelable(true);
        checkExit.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();                                                       // 选“是”则结束当前Activity
            }
        });
        checkExit.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}      // 选“否”对话框消失
        });
        checkExit.show();       // 弹出对话框
    }
}
