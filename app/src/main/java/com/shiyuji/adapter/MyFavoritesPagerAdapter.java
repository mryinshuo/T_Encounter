package com.shiyuji.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.shiyuji.R;
import com.shiyuji.model.MyTopicsItem;
import com.shiyuji.model.MyVideosItem;

import java.util.ArrayList;
import java.util.List;

public class MyFavoritesPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> list;
    private List<String> title;
    private List<MyVideosItem> videoItems;
    private List<MyTopicsItem> topicItems;

    public MyFavoritesPagerAdapter(Context context, List<Integer> list, List<String> title) {
        this.context = context;
        this.list = list;
        this.title = title;
    }

    public void init() {
        videoItems = new ArrayList<>();
        videoItems.add(new MyVideosItem(R.drawable.huangpinglaran, "视频介绍", "10", "20", "30"));
        videoItems.add(new MyVideosItem(R.drawable.huangpinglaran, "视频介绍", "10", "20", "30"));
        videoItems.add(new MyVideosItem(R.drawable.huangpinglaran, "视频介绍", "10", "20", "30"));
        videoItems.add(new MyVideosItem(R.drawable.huangpinglaran, "视频介绍", "10", "20", "30"));
        videoItems.add(new MyVideosItem(R.drawable.huangpinglaran, "视频介绍", "10", "20", "30"));

        topicItems = new ArrayList<>();
        topicItems.add(new MyTopicsItem("逗逗43组", "新人问一下，我国民族音乐中，四二，四三，四四拍又叫什么？(｡・`ω´･)", 0, "10", "20", "30"));
        topicItems.add(new MyTopicsItem("丨锴", "求《小刀会序曲》总谱!很喜欢这个曲子啊！！！", R.drawable.p1, "11", "21", "31"));
        topicItems.add(new MyTopicsItem("cool_gao", "古筝《如是》", R.drawable.g1, "12", "22", "32"));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        init();
        int item = list.get(position);
        View view = View.inflate(context, item, null);
        if (item == R.layout.my_favorites_videos) {
            ListView videosLV = (ListView) view.findViewById(R.id.myFavoritesVideosLV);
            videosLV.setAdapter(new MyVideosItemAdapter(context, R.layout.my_videos_item, videoItems));
            videosLV.setDivider(null);
        } else if (item == R.layout.my_favorites_topics) {
            ListView topicsLV = (ListView) view.findViewById(R.id.myFavoritesTopicsLV);
            topicsLV.setAdapter(new MyTopicsItemAdapter(context, R.layout.my_topics_item, topicItems));
            topicsLV.setDivider(null);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
