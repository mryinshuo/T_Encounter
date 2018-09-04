package com.shiyuji;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitlebarSearch extends LinearLayout {
    private TextView titlebarSText;
    private LinearLayout titlebarSPrev;
    private LinearLayout titlebarSSearch;
    private String text;

    public TitlebarSearch(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.titlebar_search, this);     // 调用inflate()方法将布局实例化为View对象

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitlebarSearch);    // 使用TypedArray获取自定义属性集合

        titlebarSText = (TextView) findViewById(R.id.titlebarSText);
        titlebarSPrev = (LinearLayout) findViewById(R.id.titlebarSPrev);
        titlebarSSearch = (LinearLayout) findViewById(R.id.titlebarSSearch);
        text = typedArray.getString(R.styleable.TitlebarSearch_titlebarSText);     // 获得属性titlebarText的值

        if (text != null) {
            titlebarSText.setText(text);         // 将titlebarText的值赋给该TextView的text
        }
        titlebarSPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();      // 调用getContext()获取当前上下文，转为Activity后调用finish()出栈
            }
        });
        titlebarSSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        typedArray.recycle();       // 使用TypedArray后必须调用recycle()将其释放，避免内存泄漏
    }
}
