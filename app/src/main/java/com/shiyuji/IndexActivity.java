package com.shiyuji;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.shiyuji.ExitApp.BackPress;
import com.shiyuji.adapter.IndexPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "IndexActivity";
    private List<Integer> page;
    private List<String> title;
    private ViewPager indexVP;
    private ImageView indexDrawer;
    private LinearLayout channel;
    private LinearLayout trends;
    private FloatingActionButton fab;
    private ImageView message;
    private TextView recommendTV;
    private TextView liveTV;
    private TextView nearbyTV;

    private ImageView cursor;       //滚动条的动画。
    private int cursorWidth;        //动画的宽度。
    private int Offset;             //动画图片的偏移量。
    private int currIndex = 0;      //当前页码编号

    public LocationClient mLocationClient;//定位


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_drawer);
        Log.d(TAG, "onCreate: LocationActivity");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//定位

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new IndexActivity.MyLocationListener());

        indexDrawer = (ImageView) findViewById(R.id.indexDrawerIV);
        channel = (LinearLayout) findViewById(R.id.indexChannel);
        trends = (LinearLayout) findViewById(R.id.indexTrends);
        fab = (FloatingActionButton) findViewById(R.id.indexFAB);
        message = (ImageView) findViewById(R.id.indexMessage);
        recommendTV = (TextView) findViewById(R.id.indexRecommend);
        liveTV = (TextView) findViewById(R.id.indexLive);
        nearbyTV = (TextView) findViewById(R.id.indexNearby);

        channel.setOnClickListener(this);
        trends.setOnClickListener(this);
        indexDrawer.setOnClickListener(this);
        fab.setOnClickListener(this);
        message.setOnClickListener(this);
        recommendTV.setOnClickListener(this);
        liveTV.setOnClickListener(this);
        nearbyTV.setOnClickListener(this);

        init();
        initCursorPos();
        indexVP = (ViewPager) findViewById(R.id.indexVP);
        indexVP.setAdapter(new IndexPagerAdapter(this, page, title));
        indexVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int one = Offset * 2 + cursorWidth;     // 页卡1 -> 页卡2 偏移量
            int two = one * 2;                      // 页卡1 -> 页卡3 偏移量

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            /*
            通过事件的监听，实现动画的切换
             */
            @Override
            public void onPageSelected(int position) {
                Animation animation = null;
                switch (position) {
                    case 0:
                        if (currIndex == 1) {
                            animation = new TranslateAnimation(one, 0, 0, 0);
                        } else if (currIndex == 2) {
                            animation = new TranslateAnimation(two, 0, 0, 0);
                        }
                        break;
                    case 1:
                        if (currIndex == 0) {
                            animation = new TranslateAnimation(0, one, 0, 0);
                        } else if (currIndex == 2) {
                            animation = new TranslateAnimation(two, one, 0, 0);
                        }
                        break;
                    case 2:
                        if (currIndex == 0) {
                            animation = new TranslateAnimation(0, two, 0, 0);
                        } else if (currIndex == 1) {
                            animation = new TranslateAnimation(one, two, 0, 0);
                        }
                        break;
                }
                currIndex = position;
                animation.setFillAfter(true);       // True:图片停在动画结束位置
                animation.setDuration(250);
                cursor.startAnimation(animation);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 地图");

/**
 * 获取地图权限
 */
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(IndexActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(IndexActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(IndexActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(IndexActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    public void init() {
        page = new ArrayList<>();
        page.add(R.layout.activity_index_recommend);
        page.add(R.layout.activity_index_live);
        page.add(R.layout.activity_index_nearby);

        title = new ArrayList<>();
        title.add("推荐");
        title.add("直播");
        title.add("附近");
    }

    /**
     * 当触摸返回键时
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BackPress.BackPressed(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.indexChannel:
                Intent channel = new Intent(this, ChannelActivity.class);
                startActivity(channel);
                finish();
                break;
            case R.id.indexTrends:
                Intent trends = new Intent(this, TrendsActivity.class);
                startActivity(trends);
                finish();
                break;
            case R.id.indexDrawerIV:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.indexFAB:
                Intent edit = new Intent(this, EditVideo.class);
                startActivity(edit);
                break;
            case R.id.indexMessage:
                Intent message = new Intent(this, IndexMessageActivity.class);
                startActivity(message);
                break;
            case R.id.indexRecommend:
                indexVP.setCurrentItem(0);
                break;
            case R.id.indexLive:
                indexVP.setCurrentItem(1);
                break;
            case R.id.indexNearby:
                indexVP.setCurrentItem(2);
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_follows:
                Intent follows = new Intent(this, MyFollowsActivity.class);
                startActivity(follows);
                break;
            case R.id.nav_favorites:
                Intent favorites = new Intent(this, MyFavoritesActivity.class);
                startActivity(favorites);
                break;
            case R.id.nav_topics:
                Intent topics = new Intent(this, MyTopicsActivity.class);
                startActivity(topics);
                break;
            case R.id.nav_videos:
                Intent videos = new Intent(this, MyVideosActivity.class);
                startActivity(videos);
                break;
            case R.id.nav_settings:
                Intent settings = new Intent(this, Settings.class);
                startActivity(settings);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;   // return true:点击后图标和文字变色，且不会变会原颜色
    }

    private void initCursorPos() {
        List<View> mViews = new ArrayList<>();
        mViews.add(View.inflate(this, page.get(0), null));
        mViews.add(View.inflate(this, page.get(1), null));
        mViews.add(View.inflate(this, page.get(2), null));
        // 初始化动画
        cursor = (ImageView) findViewById(R.id.indexVPTitleCursor);
        cursorWidth = BitmapFactory.decodeResource(getResources(), R.drawable.viewpager_title_cursor).getWidth();// 获取图片宽度
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

    /**
     * 定位
     */
    /**
     * 开始定位
     */
    private void requestLocation() {
        initLocation();
        mLocationClient.start();

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setCoorType("bd09ll");
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "同意权限才可以使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            //Lbs.setText("beijing");

            Log.d(TAG, "getLocationID: " + bdLocation.getCity());
            Log.d(TAG, "getLocationID: " + bdLocation.getCity());
            Log.d(TAG, "getLocationID: " + bdLocation.getLocationID());
            Log.d(TAG, "getCityCode: " + bdLocation.getCityCode());
            Log.d(TAG, "getLongitude: " + bdLocation.getLatitude());
            Log.d(TAG, "getLongitude: " + bdLocation.getLongitude());
            Log.d(TAG, "getAdCode: " + bdLocation.getAdCode());
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                Log.d(TAG, "定位方式：GPS");
            }
            if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                Log.d(TAG, "定位方式：网络");
            }
        }
    }
}
