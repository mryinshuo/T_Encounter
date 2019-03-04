package com.shiyuji;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shiyuji.adapter.SettingItemAdapter;
import com.shiyuji.model.SettingItem;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<SettingItem> itemList = new ArrayList<>();     // 定义一个ArrayList存放所有要添加的item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initItems();                                                                                            // 生成ListView内容
        SettingItemAdapter adapter = new SettingItemAdapter(this, R.layout.settings_item, itemList);    // 声明adapter
        ListView itemsLV = (ListView) findViewById(R.id.settingsLV);                                            // 获得该ListView
        itemsLV.setAdapter(adapter);                                                                            // 设置adapter
        itemsLV.setOnItemClickListener(this);                                                                   // 监听item的click事件
    }

    private void initItems() {
        SettingItem divider = new SettingItem(true);

        itemList.add(new SettingItem("个人信息", null, new Intent(this, InfoSettings.class)));
        itemList.add(new SettingItem("通知设置", null, new Intent(this, NoticeSettings.class)));
        itemList.add(divider);
        itemList.add(new SettingItem("常见问题", null, new Intent(this, QuestionSettings.class)));
        itemList.add(new SettingItem("联系客服", null, new Intent(this, ContactSettings.class)));
        itemList.add(new SettingItem("意见反馈", null, new Intent(this, FeedbackSettings.class)));
        itemList.add(new SettingItem("关于我们", null, new Intent(this, AboutSettings.class)));
        itemList.add(divider);
        itemList.add(new SettingItem("夜间模式", true, null));
        itemList.add(new SettingItem("退出登录", null, null));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (itemList.get(i).getIntent() != null) {          // 若该item有intent
            startActivity(itemList.get(i).getIntent());     // 传递该intent
        }
        if (i == itemList.size() - 1) {                                             // 若该item是itemList中的最后一个
            AlertDialog.Builder checkExit = new AlertDialog.Builder(this);      // 创建对话框
            checkExit.setMessage("退出登陆？");
            checkExit.setCancelable(true);
            checkExit.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences();
                    Intent intent = new Intent(Settings.this, LoginActivity.class);
                    startActivity(intent);
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
}
