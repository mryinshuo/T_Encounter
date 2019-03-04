package com.shiyuji.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiyuji.R;
import com.shiyuji.model.TopicsItem;

import java.util.List;

public class TopicsItemAdapter extends ArrayAdapter {

    private final int resourceId;           // 当前视图id

    public TopicsItemAdapter(Context context, int textViewResourceId, List<TopicsItem> items) {
        super(context, textViewResourceId, items);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TopicsItem item = (TopicsItem) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        ((TextView) view.findViewById(R.id.topicsTitle)).setText(item.getTitle());
        ((TextView) view.findViewById(R.id.topicsContent)).setText(item.getContent());
        ImageView topicsImg = (ImageView) view.findViewById(R.id.topicsImg);
        if (item.getImg() != 0) {
            topicsImg.setImageResource(item.getImg());
        } else {
            ((LinearLayout) view.findViewById(R.id.topicsLL)).removeView(topicsImg);
        }
        ((TextView) view.findViewById(R.id.topicsLikeNum)).setText(item.getLikeNum());
        ((TextView) view.findViewById(R.id.topicsCommentNum)).setText(item.getCommentNum());
        ((TextView) view.findViewById(R.id.topicsShareNum)).setText(item.getShareNum());

        return view;
    }
}
