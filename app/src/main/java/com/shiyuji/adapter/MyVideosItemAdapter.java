package com.shiyuji.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiyuji.R;
import com.shiyuji.model.MyVideosItem;

import java.util.List;

public class MyVideosItemAdapter extends ArrayAdapter {
    private final int resourceId;
    private LayoutInflater inflater;

    public MyVideosItemAdapter(@NonNull Context context, int resource, List<MyVideosItem> items) {
        super(context, resource, items);
        resourceId = resource;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyVideosItem item = (MyVideosItem) getItem(position);
        View view = inflater.inflate(resourceId, null);

        ((ImageView) view.findViewById(R.id.myVideoIV)).setImageResource(item.getImage());
        ((TextView) view.findViewById(R.id.myVideoTV)).setText(item.getText());
        ((TextView) view.findViewById(R.id.myVideoPlayNum)).setText(item.getPlayNum());
        ((TextView) view.findViewById(R.id.myVideoLikeNum)).setText(item.getLikeNum());
        ((TextView) view.findViewById(R.id.myVideoCommentNum)).setText(item.getCommentNum());

        return view;
    }
}
