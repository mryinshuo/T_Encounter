package com.shiyuji;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Titlebar extends LinearLayout {

    private TextView titlebarText;
    private LinearLayout titlebarPrev;
    private String text;
    public void setTitle(String title) {
        titlebarText.setText(title);
    }
    public Titlebar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.titlebar, this);     // 调用inflate()方法将布局实例化为View对象

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Titlebar);    // 使用TypedArray获取自定义属性集合

        titlebarText = (TextView) findViewById(R.id.titlebarText);
        titlebarPrev = (LinearLayout) findViewById(R.id.titlebarPrev);
        text = typedArray.getString(R.styleable.Titlebar_titlebarText);     // 获得属性titlebarText的值

        if (text != null) {
            titlebarText.setText(text);         // 将titlebarText的值赋给该TextView的text
        }
        titlebarPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();      // 调用getContext()获取当前上下文，转为Activity后调用finish()出栈
            }
        });

        typedArray.recycle();       // 使用TypedArray后必须调用recycle()将其释放，避免内存泄漏
    }
}
