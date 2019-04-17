package com.shiyuji;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiyuji.Application.MyApplication;
import com.shiyuji.ExitApp.BackPress;
import com.shiyuji.adapter.IndexPagerAdapter;
import com.shiyuji.bean.ACache;
import com.shiyuji.bean.GetImg;
import com.shiyuji.bean.User;
import com.shiyuji.bean.Video;
import com.shiyuji.model.IndexItem;
import com.shiyuji.myUtils.ImageBitmap;
import com.shiyuji.myUtils.changeUser;
import com.shiyuji.myUtils.getPersonMes;
import com.shiyuji.myUtils.iteratorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static android.support.constraint.Constraints.TAG;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "IndexActivity";
    public static final int UPDATE_VIDEO = 1;
    private static List<Integer> page;
    private static List<String> title;
    private static ViewPager indexVP;
    private ImageView indexDrawer;
    private LinearLayout channel;
    private LinearLayout trends;
    private FloatingActionButton fab;
    private ImageView message;
    private ImageView headImage;
    private TextView recommendTV;
    private TextView liveTV;
    private TextView nearbyTV;
    private Bitmap imageBitmap;
    static private User user ;
    private TextView headSignature;
    private TextView headName;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private SwipeRefreshLayout indexSRL;
    View view=null;
    final String pointUrl = "user/updateUser.action";
    private List<IndexItem>  recommendItems;//首页加载页面
    private ImageView cursor;       //滚动条的动画。
    private int cursorWidth;        //动画的宽度。
    private int Offset;             //动画图片的偏移量。
    private int currIndex = 0;      //当前页码编号
    public static int ISACACHE=1;
    private ACache acache=null;//缓存框架
    private  ArrayList<Video> videos=null;
    public LocationClient mLocationClient;//定位
    private static IndexPagerAdapter adapter ;
   @SuppressLint("HandlerLeak")
  static public Handler handler = new Handler(){
      public void handleMessage(Message msg){
          switch (msg.what){
              case UPDATE_VIDEO:
                  Log.d(TAG, "myhander: LocationActivity");

                          IndexActivity.adapter.notifyDataSetChanged();
                          indexVP.setAdapter(adapter);
                  break;
                      default:
                  break;
          }
      }
    };

private IndexPagerAdapter indexPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_drawer);
        Log.d(TAG, "onCreate: LocationActivity");
        acache=ACache.get(MyApplication.getContext());//创建ACache组件
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new IndexActivity.MyLocationListener());
        indexDrawer = (CircleImageView) findViewById(R.id.indexDrawerIV);

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
        /*initSwipeRefresh();*/
        user=getPersonMes.getConnect(MyApplication.phone);

if (user==null){
    user = new User();
    user.setPhone(MyApplication.phone);
    user.setName("传承者");
    user.setHeadUrl("logo.png");
    changeUser.init(user,pointUrl);
    MyApplication.headUrl="logo.png";
    MyApplication.userName="传承者";
}
        assert user != null;
        if(user.getHeadUrl()!=null){
            MyApplication.headUrl=user.getHeadUrl();
        }else {
            user.setPhone(MyApplication.phone);
            user.setHeadUrl("logo.png");
            changeUser.init(user,pointUrl);
            MyApplication.headUrl="logo.png";
        }
        assert user != null;
        if(user.getName()!=null){
            MyApplication.headUrl=user.getName();
        }else {
            user.setPhone(MyApplication.phone);
            user.setName("传承者");
            changeUser.init(user,pointUrl);
            MyApplication.userName="传承者";
        }
        init();
        init1();//初始化第一页

        initCursorPos();

        adapter =new IndexPagerAdapter(this, page, title,recommendItems) ;
        indexVP = (ViewPager) findViewById(R.id.indexVP);
        indexVP.setAdapter(adapter);
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
        //System.out.println("phone:"+MyApplication.phone+" getHeadUrl:"+user.getHeadUrl());
        if(user!=null){
            String imgUrl = user.getHeadUrl();

            imageBitmap = ImageBitmap.getImageBitmap("headImage/"+imgUrl);
            indexDrawer.setImageBitmap(imageBitmap);
        }else{
            indexDrawer.setImageResource(R.drawable.logo);
        }
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
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);//侧栏
                drawer.openDrawer(Gravity.START);
                user = getPersonMes.getConnect(MyApplication.phone);///
                headImage = (ImageView) findViewById(R.id.headImage);
                headName = (TextView) findViewById(R.id.headName);
                headSignature = (TextView) findViewById(R.id.headSignature);

                System.out.println("imageBitmap:"+imageBitmap);
                headName.setText(user.getName());
                headSignature.setText(user.getSignature());
                headImage.setImageBitmap(imageBitmap);
                headImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      Intent intent =  new Intent(IndexActivity.this, InfoSettings.class);
                      startActivity(intent);
                    }
                });
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
     *   定位
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
        public void onReceiveLocation(final BDLocation bdLocation) {

            MyApplication.ADRESS=bdLocation.getCity();
            Log.d(TAG, "地点："+bdLocation.getCity());
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                Log.d(TAG, "定位方式：GPS");
            }
            if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                Log.d(TAG, "定位方式：网络");
            }
        }
    }


        public void init1() {

           iteratorUtils iteratorUtils = new iteratorUtils();
           String cacheData=acache.getAsString("recommendItems");//从缓存中取数据*/
        recommendItems = new ArrayList<IndexItem>();
       if(cacheData!=null&&ISACACHE==1){//如果缓存中有，就不访问网络
            Log.d(TAG, "init1: 缓存");
            recommendItems.clear();
            Gson gson=new Gson();  //引用谷歌的json包
            videos=gson.fromJson(cacheData, new TypeToken<List<Video>>(){}.getType()); //谷歌的解析json的方法
            ListIterator<Video> iterator = videos.listIterator();
            iteratorUtils.GetIterator(iterator,recommendItems);
        }
       else if (cacheData==null || ISACACHE==0) {
            Log.d(TAG, "init1: 非缓存");
            recommendItems.clear();
            videos = GetImg.getConnect(null);
            if (videos == null) {

            } else {
                ListIterator<Video> iterator = videos.listIterator();
                iteratorUtils.GetIterator(iterator,recommendItems);
                ISACACHE = 1;
            }
        }

    }

}
