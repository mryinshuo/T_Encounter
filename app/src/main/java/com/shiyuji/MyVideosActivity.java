package com.shiyuji;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.shiyuji.adapter.MyVideosItemAdapter;
import com.shiyuji.model.MyVideosItem;

import java.util.ArrayList;
import java.util.List;

public class MyVideosActivity extends AppCompatActivity {

    private List<MyVideosItem> items;
    private ListView myVideoLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_videos);

        init();
        myVideoLV = (ListView) findViewById(R.id.myVideosLV);
        myVideoLV.setAdapter(new MyVideosItemAdapter(this, R.layout.my_videos_item, items));
        myVideoLV.setDivider(null);
    }

    public void init() {
        items = new ArrayList<>();
        items.add(new MyVideosItem(R.drawable.huangpinglaran, "视频介绍", "10", "20", "30"));
        items.add(new MyVideosItem(R.drawable.huangpinglaran, "视频介绍", "10", "20", "30"));
        items.add(new MyVideosItem(R.drawable.huangpinglaran, "视频介绍", "10", "20", "30"));
        items.add(new MyVideosItem(R.drawable.huangpinglaran, "视频介绍", "10", "20", "30"));
        items.add(new MyVideosItem(R.drawable.huangpinglaran, "视频介绍", "10", "20", "30"));
    }
}
