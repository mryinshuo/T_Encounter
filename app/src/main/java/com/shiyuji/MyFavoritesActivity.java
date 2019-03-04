package com.shiyuji;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.shiyuji.adapter.MyFavoritesPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFavoritesActivity extends AppCompatActivity {

    private List<Integer> pagerList;
    private List<String> titleList;
    private ViewPager myFavoritesVP;

    private ImageView cursor;       //滚动条的动画。
    private int cursorWidth;        //动画的宽度。
    private int Offset;             //动画图片的偏移量。
    private int currIndex = 0;      //当前页码编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);

        init();
        initCursorPos();
        myFavoritesVP = (ViewPager) findViewById(R.id.myFavoritesVP);
        MyFavoritesPagerAdapter adapter = new MyFavoritesPagerAdapter(this, pagerList, titleList);
        myFavoritesVP.setAdapter(adapter);
        myFavoritesVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
    }

    public void init() {
        pagerList = new ArrayList<>();
        pagerList.add(R.layout.my_favorites_videos);
        pagerList.add(R.layout.my_favorites_topics);

        titleList = new ArrayList<>();
        titleList.add("视频");
        titleList.add("讨论");
    }

    private void initCursorPos() {
        List<View> mViews = new ArrayList<>();
        mViews.add(View.inflate(this, pagerList.get(0), null));
        mViews.add(View.inflate(this, pagerList.get(1), null));
        // 初始化动画
        cursor = (ImageView) findViewById(R.id.myFavoritesVPTitleCursor);
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
}
