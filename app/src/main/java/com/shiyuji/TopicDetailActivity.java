package com.shiyuji;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shiyuji.adapter.CommentItemAdapter;
import com.shiyuji.model.CommentItem;
import com.shiyuji.model.TopicsDetailHeaderItem;

import java.util.ArrayList;
import java.util.List;

public class TopicDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView topicDetailLV;
    private List<CommentItem> list;
    private TopicsDetailHeaderItem header;

    private LinearLayout titlebar;
    private LinearLayout titlebarPrev;
    private TextView followTV;
    private ImageButton favoriteIB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_topic_detail);

        titlebar = (LinearLayout) findViewById(R.id.titlebar);
//        titlebar.setBackgroundResource(R.drawable.bg);
        titlebarPrev = (LinearLayout) findViewById(R.id.titlebarPrev);
        titlebarPrev.setBackgroundResource(0);

        topicDetailLV = (ListView) findViewById(R.id.topicDetailLV);
        init();
        topicDetailLV.setAdapter(new CommentItemAdapter(this, R.layout.comment_item, list));
        header = new TopicsDetailHeaderItem(this, true, true);
        initHeader(header);
        topicDetailLV.addHeaderView(header);

        followTV = (TextView) findViewById(R.id.topicDetailFollow);
        favoriteIB = (ImageButton) findViewById(R.id.topicDetailFavorite);
        followTV.setOnClickListener(this);
        favoriteIB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topicDetailFollow:
                header.setFollowed(!header.getFollowed());
                break;
            case R.id.topicDetailFavorite:
                header.setFavorite(!header.getFavorite());
                break;
        }
    }

    public void init() {
        list = new ArrayList();
        list.add(new CommentItem(R.drawable.he, "hello", "1", "2小时前", true, "我是1楼", true, 25));
        list.add(new CommentItem(R.drawable.he, "hello", "1", "2小时前", true, "我是1楼", true, 25));
        list.add(new CommentItem(R.drawable.he, "hello", "1", "2小时前", true, "我是1楼", true, 25));
        list.add(new CommentItem(R.drawable.he, "hello", "1", "2小时前", true, "我是1楼", true, 25));
    }

    public void initHeader(TopicsDetailHeaderItem header) {
        header.setHead(R.drawable.hello);
        header.setName("Linadus");
        header.setTime("2小时前");
        header.setVisitNum(1802);
        header.setText("咏叹了人间离苦、别绪、思念、期盼！");
        header.setLikeNum(100);
        header.setCommentNum(200);
        header.setShareNum(300);
    }
}
