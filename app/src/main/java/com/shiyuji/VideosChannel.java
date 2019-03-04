package com.shiyuji;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.shiyuji.adapter.VideosChannelPagerAdapter;
import com.shiyuji.model.PopItem;

import java.util.ArrayList;
import java.util.List;

public class VideosChannel extends AppCompatActivity implements View.OnClickListener {

    private List<Integer> pagerList;
    private List<String> titleList;
    private ViewPager channelVideosVP;
    private TextView titlebarTV;
    private Button apply;
    private Button inheritor;

    private ImageView cursor;       //滚动条的动画。
    private int cursorWidth;        //动画的宽度。
    private int Offset;             //动画图片的偏移量。
    private int currIndex = 0;      //当前页码编号

    private TextView videosPopLeft;
    private LinearLayout videosPopRight;
    private List<PopItem> itemList;
    private View darkView;
    private Animation animIn;
    private Animation animOut;
    private PopMenu p1;

    private ImageView videosPopRightIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_videos);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");      // 从频道传回的标题名称
        titlebarTV = (TextView) findViewById(R.id.titlebarSText);
        titlebarTV.setText(title);

        init();
        initCursorPos();
        channelVideosVP = (ViewPager) findViewById(R.id.channelVideosVP);
        VideosChannelPagerAdapter adapter = new VideosChannelPagerAdapter(this, pagerList, titleList);
        channelVideosVP.setAdapter(adapter);
        channelVideosVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int one = Offset * 2 + cursorWidth;     // 页卡1 -> 页卡2 偏移量

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Animation animation = null;
                switch (position) {
                    case 0:
                        animation = new TranslateAnimation(one, 0, 0, 0);
                        break;
                    case 1:
                        animation = new TranslateAnimation(0, one, 0, 0);
                        break;
                }
                currIndex = position;
                animation.setFillAfter(true);   // True:图片停在动画结束位置
                animation.setDuration(250);
                cursor.startAnimation(animation);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        apply = (Button) findViewById(R.id.ChannelVideosApply);
        inheritor = (Button) findViewById(R.id.ChannelVideosInheritor);
        apply.setOnClickListener(this);
        inheritor.setOnClickListener(this);

        initData();
        initView();
        initPop();
        videosPopRightIV = (ImageView) findViewById(R.id.videosPopRightIV);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ChannelVideosInheritor:
                Intent intent1 = new Intent(this, InheritorVideosChannel.class);
                startActivity(intent1);
                break;
            case R.id.ChannelVideosApply:
                Intent intent2 = new Intent(this, ApplyVideosChannel.class);
                startActivity(intent2);
                break;
            case R.id.videosPopRight:
                //背景颜色变暗
                darkView.startAnimation(animIn);
                darkView.setVisibility(View.VISIBLE);

                if (!p1.popupWindow.isShowing()) {
                    v.setSelected(true);
                    //创建后立即呈现
                    p1.popupWindow.showAsDropDown(v, 0, 5);
                    videosPopRightIV.setImageResource(R.drawable.up);
                } else {        //实现第二次点击收回的效果
                    p1.popupWindow.dismiss();
                    //背景颜色变回正常
                    darkView.startAnimation(animOut);
                    darkView.setVisibility(View.GONE);
                    videosPopRightIV.setImageResource(R.drawable.down);
                }
                break;
        }
    }

    public void init() {
        pagerList = new ArrayList<>();
        pagerList.add(R.layout.channel_videos_official);
        pagerList.add(R.layout.channel_videos_unofficial);

        titleList = new ArrayList<>();
        titleList.add("官方");
        titleList.add("非官方");
    }

    private void initCursorPos() {
        List<View> mViews = new ArrayList<>();
        mViews.add(View.inflate(this, pagerList.get(0), null));
        mViews.add(View.inflate(this, pagerList.get(1), null));
        // 初始化动画
        cursor = (ImageView) findViewById(R.id.videosVPTitleCursor);
        cursorWidth = BitmapFactory.decodeResource(getResources(), R.drawable.viewpager_title_cursor).getWidth();   // 获取图片宽度
        //获得屏幕的宽度
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenW = metrics.widthPixels;
        // 计算偏移量
        Offset = (screenW / mViews.size() - cursorWidth) / 2;
        // 设置动画初始位置
        Matrix matrix = new Matrix();
        matrix.postTranslate(Offset, 0);
        cursor.setImageMatrix(matrix);
    }

    private void initView() {
        //三个按钮的初始化
        videosPopLeft = findViewById(R.id.videosPopLeft);
        videosPopRight = findViewById(R.id.videosPopRight);
        videosPopLeft.setOnClickListener(this);
        videosPopRight.setOnClickListener(this);

        //黑色背景的初始化
        animIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_anim);
        animOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_anim);
        darkView = findViewById(R.id.main_darkview);
        darkView.startAnimation(animIn);
        darkView.setVisibility(View.GONE);
    }

    public void initData() {
        itemList = new ArrayList<>();
        itemList.add(new PopItem(1, "南通蓝印花布印染"));
        itemList.add(new PopItem(2, "宜兴紫砂陶制作"));
        itemList.add(new PopItem(3, "界首彩陶烧制"));
        itemList.add(new PopItem(4, "石湾陶塑"));
        itemList.add(new PopItem(5, "景德镇手工制瓷"));
        itemList.add(new PopItem(6, "宋锦织造"));
        itemList.add(new PopItem(7, "苏州缂丝织造"));
        itemList.add(new PopItem(8, "乌泥泾手工棉纺织"));
        itemList.add(new PopItem(9, "黎族传统纺染织绣"));
        itemList.add(new PopItem(10, "维吾尔族花毡、印花布织染"));
        itemList.add(new PopItem(11, "侗族木构建筑营造"));
        itemList.add(new PopItem(12, "玉屏箫笛制作"));
        itemList.add(new PopItem(13, "芜湖铁画锻制"));
        itemList.add(new PopItem(14, "苗族银饰锻制"));
        itemList.add(new PopItem(15, "阿昌族户撒刀锻制"));
        itemList.add(new PopItem(16, "拉萨甲米水磨坊制作"));
        itemList.add(new PopItem(17, "蒙古族勒勒车制作"));
    }

    private void initPop() {

        View view;

        ListView firstListView;
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.bg_filter_down);

        view = LayoutInflater.from(this).inflate(R.layout.channel_videos_pop_listview, null);
        firstListView = view.findViewById(R.id.pop_listview);

        p1 = new PopMenu(this, itemList, WindowManager.LayoutParams.MATCH_PARENT, 540, view, drawable, firstListView);
        p1.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                //背景颜色变回正常
                darkView.startAnimation(animOut);
                darkView.setVisibility(View.GONE);

                //处理数据方式要防止取得的数据是不正常的数据
                if (p1.results[1] != null) {
                    if (p1.results[0] != null) {
                        videosPopLeft.setText(p1.results[0]);
                    }
                    Toast.makeText(VideosChannel.this, p1.results[0], Toast.LENGTH_SHORT).show();
                }
                videosPopRight.setSelected(false);
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            //点击按钮以外的布局时要把可能有的PopupWindow清除
            case MotionEvent.ACTION_UP:
                if (p1.popupWindow.isShowing()) {
                    p1.popupWindow.dismiss();
                }
                break;
            default:
                break;
        }
        return true;
    }
}
