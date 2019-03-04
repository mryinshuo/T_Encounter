package com.shiyuji;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.shiyuji.adapter.InheritorItemAdapter;
import com.shiyuji.model.InheritorItem;

import java.util.ArrayList;
import java.util.List;

public class InheritorVideosChannel extends AppCompatActivity {

    private List<InheritorItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_videos_inheritor);

        init();
        ListView inheritorLV = (ListView) findViewById(R.id.inheritorLV);
        inheritorLV.setAdapter(new InheritorItemAdapter(this, R.layout.inheritor_item, items));
    }

    public void init() {
        items = new ArrayList<>();
        items.add(new InheritorItem(R.drawable.user_head, "尹烁", "时遇记", "宋锦织造传承人"));
        items.add(new InheritorItem(R.drawable.user_head, "尹烁", "时遇记", "宋锦织造传承人"));
        items.add(new InheritorItem(R.drawable.user_head, "尹烁", "时遇记", "宋锦织造传承人"));
        items.add(new InheritorItem(R.drawable.user_head, "尹烁", "时遇记", "宋锦织造传承人"));
        items.add(new InheritorItem(R.drawable.user_head, "尹烁", "时遇记", "宋锦织造传承人"));
    }
}
