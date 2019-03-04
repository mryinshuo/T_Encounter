package com.shiyuji;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.shiyuji.adapter.MyFollowsItemAdapter;
import com.shiyuji.model.MyFollowsItem;

import java.util.ArrayList;
import java.util.List;

public class MyFollowsActivity extends AppCompatActivity {

    private List<MyFollowsItem> items;
    private ListView myFollowLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follows);

        init();
        myFollowLV = (ListView) findViewById(R.id.myFollowsLV);
        myFollowLV.setAdapter(new MyFollowsItemAdapter(this, R.layout.my_follows_item, items));
        myFollowLV.setDivider(null);
    }

    public void init() {
        items = new ArrayList<>();
        items.add(new MyFollowsItem(R.drawable.huangpinglaran, "时遇记", "遇见文化遇见你", "1200w", "9"));
        items.add(new MyFollowsItem(R.drawable.huangpinglaran, "时遇记", "遇见文化遇见你", "1200w", "9"));
        items.add(new MyFollowsItem(R.drawable.huangpinglaran, "时遇记", "遇见文化遇见你", "1200w", "9"));
        items.add(new MyFollowsItem(R.drawable.huangpinglaran, "时遇记", "遇见文化遇见你", "1200w", "9"));
        items.add(new MyFollowsItem(R.drawable.huangpinglaran, "时遇记", "遇见文化遇见你", "1200w", "9"));
    }
}
