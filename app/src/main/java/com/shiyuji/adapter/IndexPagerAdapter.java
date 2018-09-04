package com.shiyuji.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.shiyuji.Application.MyApplication;
import com.shiyuji.IndexActivity;
import com.shiyuji.R;
import com.shiyuji.model.IndexItem;
import com.shiyuji.model.TrendsItem;

import java.util.ArrayList;
import java.util.List;

public class IndexPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> list;
    private List<String> title;
    private List<IndexItem> recommendItems;
    private List<IndexItem> liveItems;
    private List<TrendsItem> trendsItems;
    private SwipeRefreshLayout recommendSRL;
    private SwipeRefreshLayout liveSRL;
    private SwipeRefreshLayout nearbySRL;
    private EditText Lbs;//定位地点
    public IndexPagerAdapter(Context context, List<Integer> list, List<String> title) {
        this.context = context;
        this.list = list;
        this.title = title;
    }

    public void init() {
        recommendItems = new ArrayList<>();
        recommendItems.add(new IndexItem(R.drawable.gongyizheshan, "山西省河曲县 河曲民歌 原生态乡土民歌", R.drawable.quju, "北京曲剧，曲艺单弦牌子曲为主发展而成"));
        recommendItems.add(new IndexItem(R.drawable.huangpinglaran, "河南省沁阳市唢呐《九峰情话》、《沁阳春》", R.drawable.diaoban, "最负盛名的传统表演性舞蹈傣族民间舞孔雀舞"));
        recommendItems.add(new IndexItem(R.drawable.kunqu, "苏州太仓南码头百戏之祖昆曲", R.drawable.andaiwu, "内蒙古通辽市蒙古族舞蹈活化石安代舞"));
        recommendItems.add(new IndexItem(R.drawable.liyuanxi, "闽浙之音、古南戏活化石 梨园戏", R.drawable.chuanju, "中国传统戏曲川剧"));

        liveItems = new ArrayList<>();
        liveItems.add(new IndexItem(R.drawable.daoqingxi, "陕西关中道情戏正在直播...", R.drawable.zhuangju, "壮剧壮剧正在直播..."));
        liveItems.add(new IndexItem(R.drawable.buyixi, "兴义布依戏正在直播...", R.drawable.nonglewu, "东北朝鲜族农乐舞正在直播..."));
        liveItems.add(new IndexItem(R.drawable.huagu, "山西省翼城花鼓正在直播...", R.drawable.puningyingge, "广东省普宁英歌正在直播..."));
        liveItems.add(new IndexItem(R.drawable.shiwu, "舞狮子表演正在直播...", R.drawable.yangge, "秧歌舞正在直播..."));

        trendsItems = new ArrayList<>();
        trendsItems.add(new TrendsItem(R.drawable.chuanju, "豫酱", "今天20:03", false, "豫剧起源于中原（河南），是中国五大戏曲剧种之一、中国第一大地方剧种，在浙江各地也广为流传。", R.drawable.yuju, true, "10", "11", "12"));
        trendsItems.add(new TrendsItem(R.drawable.guqinyishu, "诗琴", "今天20:02", false, "古琴艺术是继昆曲之后被列入“人类口头与非物质遗产”的第二个中国文化门类。琴棋书画，曾是中国古代文人引以为傲的四项技能，也是四种艺术。", R.drawable.guqinyishu, true, "20", "21", "22"));
        trendsItems.add(new TrendsItem(R.drawable.kunqu, "豫乡情", "今天20:01", true, "龙舞，也称“舞龙”，民间又叫“耍龙”、“耍龙灯”或“舞龙灯”，中国传统民间舞蹈之一，在全国各地广泛分布，其形式品种的多样，是任何其他民间舞都无法比拟的。", R.drawable.wulong, true, "30", "31", "32"));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        init();
        int item = list.get(position);
        View view = View.inflate(context, item, null);
        if (item == R.layout.activity_index_recommend) {
            ListView recommendLV = (ListView) view.findViewById(R.id.indexLV);
            recommendLV.setAdapter(new IndexItemAdapter(context, R.layout.activity_index_item, recommendItems));
            recommendLV.setDivider(null);

            recommendSRL = (SwipeRefreshLayout) view.findViewById(R.id.recommendSRL);
            recommendSRL.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
            recommendSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    recommendSRL.setRefreshing(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recommendSRL.setRefreshing(false);
                        }
                    }, 800);
                }
            });
        } else if (item == R.layout.activity_index_live) {
            ListView liveLV = (ListView) view.findViewById(R.id.liveLV);
            liveLV.setAdapter(new IndexItemAdapter(context, R.layout.activity_index_item, liveItems));
            liveLV.setDivider(null);

            liveSRL = (SwipeRefreshLayout) view.findViewById(R.id.liveSRL);
            liveSRL.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
            liveSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    liveSRL.setRefreshing(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            liveSRL.setRefreshing(false);
                        }
                    }, 800);
                }
            });
        } else if (item == R.layout.activity_index_nearby) {


            ListView nearbyLV = (ListView) view.findViewById(R.id.nearbyLV);
            nearbyLV.setAdapter(new NearbyItemAdapter(context, R.layout.trends_item, trendsItems));
            nearbyLV.addHeaderView(View.inflate(context, R.layout.activity_nearby_header, null));
            nearbyLV.setDividerHeight(10);

            nearbySRL = (SwipeRefreshLayout) view.findViewById(R.id.nearbySRL);
            nearbySRL.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
            nearbySRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    nearbySRL.setRefreshing(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nearbySRL.setRefreshing(false);
                        }
                    }, 800);
                }
            });
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
