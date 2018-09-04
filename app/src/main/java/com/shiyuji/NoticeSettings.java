package com.shiyuji;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.shiyuji.adapter.SettingItemAdapter;
import com.shiyuji.model.SettingItem;

import java.util.ArrayList;
import java.util.List;

public class NoticeSettings extends AppCompatActivity {

    private List<SettingItem> itemList = new ArrayList<>();     // 定义一个ArrayList存放所有要添加的item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_notice);

        initItems();                                                                                            // 生成ListView内容
        SettingItemAdapter adapter = new SettingItemAdapter(this, R.layout.settings_item, itemList);    // 声明adapter
        ListView itemsLV = (ListView) findViewById(R.id.settingsNoticeLV);                                      // 获得该ListView
        itemsLV.setAdapter(adapter);                                                                            // 设置adapter
    }

    private void initItems() {
        itemList.add(new SettingItem("有新视频时", true, null));
        itemList.add(new SettingItem("有新私信时", true, null));
        itemList.add(new SettingItem("有人关注我时", true, null));
    }
}
