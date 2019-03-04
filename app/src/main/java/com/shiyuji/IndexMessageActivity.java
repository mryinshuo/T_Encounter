package com.shiyuji;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shiyuji.adapter.IndexMessageItemAdapter;
import com.shiyuji.model.IndexMessageItem;

import java.util.ArrayList;
import java.util.List;

public class IndexMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView messageLV;
    private List<IndexMessageItem> items;
    private LinearLayout comment;
    private LinearLayout like;
    private LinearLayout notification;
    private LinearLayout divider;
    private TextView chatTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_message);

        init();
        messageLV = (ListView) findViewById(R.id.indexMessageLV);
        View header = getLayoutInflater().inflate(R.layout.activity_index_message_header, null);
        messageLV.addHeaderView(header);
        messageLV.setAdapter(new IndexMessageItemAdapter(this, R.layout.index_message_item, items));

        comment = (LinearLayout) findViewById(R.id.indexMessageComment);
        like = (LinearLayout) findViewById(R.id.indexMessageLike);
        notification = (LinearLayout) findViewById(R.id.indexMessageNotification);
        divider = (LinearLayout) findViewById(R.id.indexMessageDivider);
        chatTitle = (TextView) findViewById(R.id.indexMessageChatTitle);
        comment.setOnClickListener(this);
        like.setOnClickListener(this);
        notification.setOnClickListener(this);
        divider.setOnClickListener(this);
        chatTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.indexMessageComment:
                break;
            case R.id.indexMessageLike:
                break;
            case R.id.indexMessageNotification:
                break;
            case R.id.indexMessageDivider:
                break;
            case R.id.indexMessageChatTitle:
                break;
        }
    }

    public void init() {
        items = new ArrayList<>();
        items.add(new IndexMessageItem(R.drawable.user_head, "时遇记", "遇见文化遇见你", "2018/8/17"));
        items.add(new IndexMessageItem(R.drawable.user_head, "时遇记", "遇见文化遇见你", "2018/8/14"));
        items.add(new IndexMessageItem(R.drawable.user_head, "时遇记", "遇见文化遇见你", "2018/8/11"));
        items.add(new IndexMessageItem(R.drawable.user_head, "时遇记", "遇见文化遇见你", "2018/8/10"));
    }
}
