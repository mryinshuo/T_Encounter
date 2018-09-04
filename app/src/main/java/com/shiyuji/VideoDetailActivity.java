package com.shiyuji;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shiyuji.adapter.CommentItemAdapter;
import com.shiyuji.model.CommentItem;
import com.shiyuji.model.VideoDetailHeaderItem;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView videoDetail;
    private List<CommentItem> list;
    private ImageView videoDetailHead;
    private VideoDetailHeaderItem header;
    private TextView followTV;
    private ImageView favoriteIV;
    private LinearLayout commentLL;
    private EditText videoDetailET;
    private LinearLayout likeLL;
    private ImageView likeIV;
    private ImageView prev;
    private boolean liked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_detail);

        videoDetail = (ListView) findViewById(R.id.videoDetailLV);
        init();
        videoDetail.setAdapter(new CommentItemAdapter(this, R.layout.comment_item, list));
        header = new VideoDetailHeaderItem(this, true, true, true);
        initHeader(header);
        videoDetail.addHeaderView(header);

        videoDetailHead = (ImageView) findViewById(R.id.videoDetailHead);
        followTV = (TextView) findViewById(R.id.videoDetailFollow);
        favoriteIV = (ImageView) findViewById(R.id.videoDetailFavorite);
        commentLL = (LinearLayout) findViewById(R.id.videoDetailCommentLL);
        videoDetailET = (EditText) findViewById(R.id.videoDetailET);
        likeLL = (LinearLayout) findViewById(R.id.videoDetailLikeLL);
        likeIV = (ImageView) findViewById(R.id.videoDetailLikeIV);
        prev = (ImageView) findViewById(R.id.videoDetailPrev);
        videoDetailHead.setOnClickListener(this);
        followTV.setOnClickListener(this);
        favoriteIV.setOnClickListener(this);
        commentLL.setOnClickListener(this);
        likeLL.setOnClickListener(this);
        prev.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoDetailHead:
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.videoDetailFollow:
                header.setFollowed(!header.isFollowed());
                break;
            case R.id.videoDetailLikeLL:
                header.setLiked(!header.isLiked());
                break;
            case R.id.videoDetailFavorite:
                header.setFavorite(!header.isFavorite());
                break;
            case R.id.videoDetailCommentLL:
                videoDetailET.setFocusable(true);
                videoDetailET.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(videoDetailET, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.videoDetailPrev:
                finish();
                break;
        }
    }

    public void init() {
        list = new ArrayList<>();
        list.add(new CommentItem(R.drawable.yangge, "落日余晖", "5", "8-10", true, "咏叹了人间离苦、别绪、思念、期盼！", true, 10));
        list.add(new CommentItem(R.drawable.chuanju, "山西某不知名网友", "4", "8-7", true, "蕴含有丰富的美学意味。", true, 20));
        list.add(new CommentItem(R.drawable.pic3, "家里有只小旺仔", "3", "8-7", true, "河曲民歌吟唱内容丰富，涵盖社会生活的各个层面。", true, 30));
        list.add(new CommentItem(R.drawable.kongquewu, "呆酱", "2", "8-7", true, "一个上下句就揭示出一种深邃的感情状况或描绘出一种逼真的生活画面。", true, 40));
        list.add(new CommentItem(R.drawable.kunqu, "李三岁", "1", "8-7", true, "极具文化神韵的传统经典原生态乡土民歌", true, 50));
    }

    public void initHeader(VideoDetailHeaderItem header) {
        header.setHead(R.drawable.huangpinglaran);
        header.setName("南通蓝印花布印染");
        header.setTime("2小时前");
        header.setVisitNum(1802);
        header.setText("南通蓝印花布传统印染技艺遍及南通地区各县，长久以来流传不衰，成为最具代表性的传统手工艺品之一。");
        header.setLikeNum(10);
        header.setCommentNum(20);
        header.setShareNum(30);
    }
}
