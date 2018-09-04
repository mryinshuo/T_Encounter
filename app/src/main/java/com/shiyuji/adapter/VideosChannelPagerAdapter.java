package com.shiyuji.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.shiyuji.R;
import com.shiyuji.model.IndexItem;

import java.util.ArrayList;
import java.util.List;

public class VideosChannelPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> list;
    private List<String> title;
    private List<IndexItem> officialItems;
    private List<IndexItem> unofficialItems;

    public VideosChannelPagerAdapter(Context context, List<Integer> list, List<String> title) {
        this.context = context;
        this.list = list;
        this.title = title;
    }

    public void init() {
        officialItems = new ArrayList<>();
        officialItems.add(new IndexItem(R.drawable.gongyizheshan, "山西省河曲县 河曲民歌 原生态乡土民歌", R.drawable.quju, "北京曲剧，曲艺单弦牌子曲为主发展而成"));
        officialItems.add(new IndexItem(R.drawable.huangpinglaran, "河南省沁阳市唢呐《九峰情话》、《沁阳春》", R.drawable.diaoban, "最负盛名的传统表演性舞蹈傣族民间舞孔雀舞"));
        officialItems.add(new IndexItem(R.drawable.kunqu, "苏州太仓南码头百戏之祖昆曲", R.drawable.andaiwu, "内蒙古通辽市蒙古族舞蹈活化石安代舞"));
        officialItems.add(new IndexItem(R.drawable.liyuanxi, "闽浙之音、古南戏活化石 梨园戏", R.drawable.chuanju, "中国传统戏曲川剧"));

        unofficialItems = new ArrayList<>();
        unofficialItems.add(new IndexItem(R.drawable.gongyizheshan, "山西省河曲县 河曲民歌 原生态乡土民歌", R.drawable.quju, "北京曲剧，曲艺单弦牌子曲为主发展而成"));
        unofficialItems.add(new IndexItem(R.drawable.huangpinglaran, "河南省沁阳市唢呐《九峰情话》、《沁阳春》", R.drawable.diaoban, "最负盛名的传统表演性舞蹈傣族民间舞孔雀舞"));
        unofficialItems.add(new IndexItem(R.drawable.kunqu, "苏州太仓南码头百戏之祖昆曲", R.drawable.andaiwu, "内蒙古通辽市蒙古族舞蹈活化石安代舞"));
        unofficialItems.add(new IndexItem(R.drawable.liyuanxi, "闽浙之音、古南戏活化石 梨园戏", R.drawable.chuanju, "中国传统戏曲川剧"));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        init();
        int item = list.get(position);
        View view = View.inflate(context, item, null);
        if (item == R.layout.channel_videos_official) {
            ListView officialVideosLV = (ListView) view.findViewById(R.id.officialVideosLV);
            officialVideosLV.setAdapter(new IndexItemAdapter(context, R.layout.activity_index_item, officialItems));
            officialVideosLV.setDivider(null);
        } else if (item == R.layout.channel_videos_unofficial) {
            ListView unofficialVideosLV = (ListView) view.findViewById(R.id.unofficialVideosLV);
            unofficialVideosLV.setAdapter(new IndexItemAdapter(context, R.layout.activity_index_item, unofficialItems));
            unofficialVideosLV.setDivider(null);
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
