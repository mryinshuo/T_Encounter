package com.shiyuji;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChannelItem extends LinearLayout {
    private ImageView channelItemIV;
    private TextView channelItemTV;
    private TextView channelItemIdTV;
    private int image;
    private String text;
    private String cateid;
    public ChannelItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.channel_item, this);     // 调用inflate()方法将布局实例化为View对象

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChannelItem);    // 使用TypedArray获取自定义属性集合

        channelItemIV = (ImageView) findViewById(R.id.channelItemIV);
        channelItemTV = (TextView) findViewById(R.id.channelItemTV);
        channelItemIdTV = (TextView) findViewById(R.id.channelItemIdTV);
        image = typedArray.getResourceId(R.styleable.ChannelItem_channelItemImage, -1);
        text = typedArray.getString(R.styleable.ChannelItem_channelItemText);     // 获得属性titlebarText的值
         cateid = typedArray.getString(R.styleable.ChannelItem_channelItemIdText);     // 获得属性idText的值
        if (image > 0) {
            channelItemIV.setImageResource(image);
            channelItemIV.setTag(image);
        }
        if (text != null) {
            channelItemTV.setText(text);         // 将titlebarText的值赋给该TextView的text
        }
        if (cateid != null) {
            channelItemIdTV.setText(cateid);         // 将titlebarText的值赋给该TextView的text
        }
        typedArray.recycle();       // 使用TypedArray后必须调用recycle()将其释放，避免内存泄漏
    }
}
