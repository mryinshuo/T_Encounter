package com.shiyuji;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shiyuji.adapter.SettingItemAdapter;
import com.shiyuji.model.SettingItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InfoSettings extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "InfoSettings";
    private List<SettingItem> itemList = new ArrayList<>();     // 定义一个ArrayList存放所有要添加的item
    private Calendar calendar;
    private int year, month, day;
    private TextView itemDetailTV;
    private int choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_info);

        initItems();                                                                                            // 生成ListView内容
        SettingItemAdapter adapter = new SettingItemAdapter(this, R.layout.settings_item, itemList);    // 声明adapter
        ListView itemsLV = (ListView) findViewById(R.id.settingsInfoLV);                                        // 获得该ListView
        itemsLV.setAdapter(adapter);                                                                            // 设置adapter
        itemsLV.setOnItemClickListener(this);                                                                   // 监听item的click事件

        getDate();
    }

    private void initItems() {
        itemList.add(new SettingItem("头像", R.drawable.user_head, null));
        itemList.add(new SettingItem("账号", "Admin", null));
        itemList.add(new SettingItem("性别", "男", null));
        itemList.add(new SettingItem("生日", "2018 - 7 - 17", null));
    }

    private void getDate() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        itemDetailTV = view.findViewById(R.id.itemDetailTV);
        if (itemList.get(i).getText().equals("头像")) {
            Toast.makeText(this, "现在还改不了", Toast.LENGTH_SHORT).show();
        }

        if (itemList.get(i).getText().equals("性别")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请选择性别：");
            final String[] sex = {"男", "女"};
            builder.setSingleChoiceItems(sex, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    choice = which;
                }
            });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    itemDetailTV.setText(sex[choice]);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            });
            builder.show();
        }

        if (itemList.get(i).getText().equals("生日")) {
            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int year, int month, int day) {
                    itemDetailTV.setText(year + " - " + (month + 1) + " - " + day);
                }
            };
            DatePickerDialog dialog = new DatePickerDialog(this, 0, listener, year, month, day);
            dialog.show();
        }
    }
}
