package com.shiyuji;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shiyuji.adapter.TrendsItemAdapter;
import com.shiyuji.model.TrendsItem;

import java.util.ArrayList;
import java.util.List;

public class TrendsActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout titlebar;
    private LinearLayout titlebarPrev;
    private ListView trendsLV;
    private LinearLayout index;
    private LinearLayout channel;
    private SwipeRefreshLayout trendsSRL;
    private List<TrendsItem> items;

    public void init() {
        items = new ArrayList<>();
        items.add(new TrendsItem(R.drawable.xiaotupian, "暮雨寒鸦", "2018年5月18日", false, "今天亲眼看见了孟津剪纸，真是不枉此行。剪纸里面的人物形象生动，惟妙惟肖。分享一张剪纸照片，这个剪纸的名称为《红楼群芳图》。", R.drawable.xiaotupian, true, "1", "2", "3"));
        items.add(new TrendsItem(R.drawable.xiaotupian, "暮雨寒鸦", "2018年5月18日", false, "今天亲眼看见了孟津剪纸，真是不枉此行。剪纸里面的人物形象生动，惟妙惟肖。分享一张剪纸照片，这个剪纸的名称为《红楼群芳图》。", R.drawable.xiaotupian, true, "1", "2", "3"));
        items.add(new TrendsItem(R.drawable.xiaotupian, "暮雨寒鸦", "2018年5月17日", false, "今天亲眼看见了孟津剪纸，真是不枉此行。剪纸里面的人物形象生动，惟妙惟肖。分享一张剪纸照片，这个剪纸的名称为《红楼群芳图》。", R.drawable.xiaotupian, true, "1", "2", "3"));
        items.add(new TrendsItem(R.drawable.xiaotupian, "暮雨寒鸦", "2018年5月16日", false, "今天亲眼看见了孟津剪纸，真是不枉此行。剪纸里面的人物形象生动，惟妙惟肖。分享一张剪纸照片，这个剪纸的名称为《红楼群芳图》。", R.drawable.xiaotupian, true, "1", "2", "3"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);

        titlebar = (LinearLayout) findViewById(R.id.titlebar);
        titlebarPrev = (LinearLayout) findViewById(R.id.titlebarPrev);
        titlebarPrev.setOnClickListener(this);
//        titlebar.setBackgroundResource(R.drawable.dingbu);
//        titlebarPrev.setBackgroundResource(0);
        titlebarPrev.removeViewAt(0);

        index = (LinearLayout) findViewById(R.id.trendsIndex);
        channel = (LinearLayout) findViewById(R.id.trendsChannel);

        index.setOnClickListener(this);
        channel.setOnClickListener(this);

        init();
        trendsLV = (ListView) findViewById(R.id.trendsLV);
        trendsLV.setAdapter(new TrendsItemAdapter(this, R.layout.trends_item, items));
        trendsLV.addHeaderView(View.inflate(this, R.layout.trends_header, null));
        trendsLV.setDividerHeight(10);

        trendsSRL = (SwipeRefreshLayout) findViewById(R.id.trendsSRL);
        trendsSRL.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        trendsSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                trendsSRL.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        trendsSRL.setRefreshing(false);
                    }
                }, 800);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebarPrev:
                break;
            case R.id.trendsIndex:
                Intent intent1 = new Intent(this, IndexActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.trendsChannel:
                Intent intent2 = new Intent(this, ChannelActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }

    public void onBackPressed() {       // 当触摸返回键时
        AlertDialog.Builder checkExit = new AlertDialog.Builder(this);      // 创建对话框
        checkExit.setMessage("退出程序？");
        checkExit.setCancelable(true);
        checkExit.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();                                                       // 选“是”则结束当前Activity
            }
        });
        checkExit.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}      // 选“否”对话框消失
        });
        checkExit.show();       // 弹出对话框
    }
}
