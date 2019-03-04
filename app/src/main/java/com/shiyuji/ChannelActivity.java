package com.shiyuji;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChannelActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout titlebarSPrev;
    private LinearLayout literature;
    private LinearLayout music;
    private LinearLayout dancing;
    private LinearLayout theatre;
    private LinearLayout quyi;
    private LinearLayout acrobatics;
    private LinearLayout art;
    private LinearLayout tradition;
    private LinearLayout medicine;
    private LinearLayout folk;
    private LinearLayout channelVideos;
    private LinearLayout channelTopics;
    private LinearLayout choose;
    private LinearLayout index;
    private LinearLayout trends;
    private LinearLayout selected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        titlebarSPrev = (LinearLayout) findViewById(R.id.titlebarSPrev);
        literature = (LinearLayout) findViewById(R.id.literature);
        music = (LinearLayout) findViewById(R.id.music);
        dancing = (LinearLayout) findViewById(R.id.dancing);
        theatre = (LinearLayout) findViewById(R.id.theatre);
        quyi = (LinearLayout) findViewById(R.id.quyi);
        acrobatics = (LinearLayout) findViewById(R.id.acrobatics);
        art = (LinearLayout) findViewById(R.id.art);
        tradition = (LinearLayout) findViewById(R.id.tradition);
        medicine = (LinearLayout) findViewById(R.id.medicine);
        folk = (LinearLayout) findViewById(R.id.folk);
        channelTopics = (LinearLayout) findViewById(R.id.channelTopics);
        channelVideos = (LinearLayout) findViewById(R.id.channelVideos);
        choose = (LinearLayout) findViewById(R.id.channelChooseLL);
        index = (LinearLayout) findViewById(R.id.channelIndex);
        trends = (LinearLayout) findViewById(R.id.channelTrends);

        titlebarSPrev.setOnClickListener(this);
        literature.setOnClickListener(this);
        music.setOnClickListener(this);
        dancing.setOnClickListener(this);
        theatre.setOnClickListener(this);
        quyi.setOnClickListener(this);
        acrobatics.setOnClickListener(this);
        art.setOnClickListener(this);
        tradition.setOnClickListener(this);
        medicine.setOnClickListener(this);
        folk.setOnClickListener(this);
        channelTopics.setOnClickListener(this);
        channelVideos.setOnClickListener(this);
        index.setOnClickListener(this);
        trends.setOnClickListener(this);

        titlebarSPrev.removeViewAt(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebarPrev:
                break;
            case R.id.literature:
            case R.id.music:
            case R.id.dancing:
            case R.id.theatre:
            case R.id.quyi:
            case R.id.acrobatics:
            case R.id.art:
            case R.id.tradition:
            case R.id.medicine:
            case R.id.folk:
                ImageView itemIV = view.findViewById(R.id.channelItemIV);
                if (selected == null) {
                    itemIV.setSelected(!itemIV.isSelected());
                    selected = (LinearLayout) view;
                    choose.setVisibility(View.VISIBLE);
                } else if (view == selected) {
                    itemIV.setSelected(!itemIV.isSelected());
                    selected = null;
                    choose.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.channelTopics:
                if (selected != null) {
                    int icon = (int) ((ImageView) selected.findViewById(R.id.channelItemIV)).getTag();
                    CharSequence title = ((TextView) selected.findViewById(R.id.channelItemTV)).getText();
                    Intent intent = new Intent(ChannelActivity.this, TopicsChannel.class);
                    intent.putExtra("icon", icon);
                    intent.putExtra("title", title);
                    startActivity(intent);
                    selected.findViewById(R.id.channelItemIV).setSelected(false);
                    selected = null;
                    choose.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.channelVideos:
                if (selected != null) {
                    CharSequence title = ((TextView) selected.findViewById(R.id.channelItemTV)).getText();
                    Intent intent = new Intent(ChannelActivity.this, VideosChannel.class);
                    intent.putExtra("title", title);
                    startActivity(intent);
                    selected.findViewById(R.id.channelItemIV).setSelected(false);
                    selected = null;
                    choose.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.channelIndex:
                Intent intent1 = new Intent(ChannelActivity.this, IndexActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.channelTrends:
                Intent intent2 = new Intent(ChannelActivity.this, TrendsActivity.class);
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
            public void onClick(DialogInterface dialogInterface, int i) {
            }      // 选“否”对话框消失
        });
        checkExit.show();       // 弹出对话框
    }
}