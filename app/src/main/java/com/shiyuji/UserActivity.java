package com.shiyuji;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.shiyuji.adapter.UserItemAdapter;
import com.shiyuji.model.TrendsItem;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private ListView userLV;
    private List<TrendsItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userLV = (ListView) findViewById(R.id.userLV);

        init();
        userLV.setAdapter(new UserItemAdapter(this, R.layout.trends_item, items));
        userLV.addHeaderView(View.inflate(this, R.layout.activity_user_header, null));
    }

    public void init() {
        items = new ArrayList<>();
        items.add(new TrendsItem(R.drawable.xiaotupian, "暮雨寒鸦", "2018年5月18日", false, "今天亲眼看见了孟津剪纸，真是不枉此行。剪纸里面的人物形象生动，惟妙惟肖。分享一张剪纸照片，这个剪纸的名称为《红楼群芳图》。", R.drawable.xiaotupian, true, "1", "2", "3"));
        items.add(new TrendsItem(R.drawable.xiaotupian, "暮雨寒鸦", "2018年5月18日", false, "今天亲眼看见了孟津剪纸，真是不枉此行。剪纸里面的人物形象生动，惟妙惟肖。分享一张剪纸照片，这个剪纸的名称为《红楼群芳图》。", R.drawable.xiaotupian, true, "1", "2", "3"));
        items.add(new TrendsItem(R.drawable.xiaotupian, "暮雨寒鸦", "2018年5月17日", false, "今天亲眼看见了孟津剪纸，真是不枉此行。剪纸里面的人物形象生动，惟妙惟肖。分享一张剪纸照片，这个剪纸的名称为《红楼群芳图》。", R.drawable.xiaotupian, true, "1", "2", "3"));
        items.add(new TrendsItem(R.drawable.xiaotupian, "暮雨寒鸦", "2018年5月16日", false, "今天亲眼看见了孟津剪纸，真是不枉此行。剪纸里面的人物形象生动，惟妙惟肖。分享一张剪纸照片，这个剪纸的名称为《红楼群芳图》。", R.drawable.xiaotupian, true, "1", "2", "3"));
    }
}
