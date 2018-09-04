package com.shiyuji;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shiyuji.ExitApp.BackPress;

public class ChannelActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout titlebar;
    private LinearLayout titlebarPrev;
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

        titlebar = (LinearLayout) findViewById(R.id.titlebar);
        titlebarPrev = (LinearLayout) findViewById(R.id.titlebarPrev);
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

        titlebarPrev.setOnClickListener(this);
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

//        titlebar.setBackgroundResource(R.drawable.dingbu);
//        titlebarPrev.setBackgroundResource(0);
        titlebarPrev.removeViewAt(0);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
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
                if (selected == null) {
                    ((ImageView) view.findViewById(R.id.traditionIV)).setImageResource(R.drawable.tradition_after);
                    selected = (LinearLayout) view;
                    choose.setVisibility(View.VISIBLE);
                } else if (view == selected) {
                    ((ImageView) view.findViewById(R.id.traditionIV)).setImageResource(R.drawable.tradition);
                    selected = null;
                    choose.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.channelTopics:
                if (selected != null) {
                    Intent intent = new Intent(ChannelActivity.this, TopicsChannel.class);
                    intent.putExtra("title", "传统技艺");
                    startActivity(intent);
                    ((ImageView) selected.findViewById(R.id.traditionIV)).setImageResource(R.drawable.tradition);
                    selected = null;
                    choose.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.channelVideos:
                if (selected != null) {
                    Intent intent = new Intent(ChannelActivity.this, VideosChannel.class);
                    intent.putExtra("title", "传统技艺");
                    startActivity(intent);
                    ((ImageView) selected.findViewById(R.id.traditionIV)).setImageResource(R.drawable.tradition);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BackPress.BackPressed(this);
    }
}