package com.shiyuji;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.shiyuji.adapter.MyTopicsItemAdapter;
import com.shiyuji.model.MyTopicsItem;

import java.util.ArrayList;
import java.util.List;

public class MyTopicsActivity extends AppCompatActivity {

    private List<MyTopicsItem> items;
    private ListView myTopicsLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_topics);

        init();
        myTopicsLV = (ListView) findViewById(R.id.myTopicsLV);
        myTopicsLV.setAdapter(new MyTopicsItemAdapter(this, R.layout.my_topics_item, items));
        myTopicsLV.setDivider(null);
    }

    private void init() {
        items = new ArrayList<>();
        items.add(new MyTopicsItem("逗逗43组", "新人问一下，我国民族音乐中，四二，四三，四四拍又叫什么？(｡・`ω´･)", 0, "10", "20", "30"));
        items.add(new MyTopicsItem("丨锴", "求《小刀会序曲》总谱!很喜欢这个曲子啊！！！", R.drawable.p1, "11", "21", "31"));
        items.add(new MyTopicsItem("cool_gao", "古筝《如是》", R.drawable.g1, "12", "22", "32"));
    }
}
