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
import com.shiyuji.model.MyTopicsItem;

import java.util.List;

public class MyTopicsItemAdapter extends ArrayAdapter {
    private final int resourceId;
    private LayoutInflater inflater;

    public MyTopicsItemAdapter(@NonNull Context context, int resource, List<MyTopicsItem> items) {
        super(context, resource, items);
        resourceId = resource;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyTopicsItem item = (MyTopicsItem) getItem(position);
        View view = inflater.inflate(resourceId, null);

        ((TextView) view.findViewById(R.id.myTopicsTitle)).setText(item.getTitle());
        ((TextView) view.findViewById(R.id.myTopicsText)).setText(item.getText());
        if (item.getImage() != 0) {
            ((ImageView) view.findViewById(R.id.myTopicsIV)).setImageResource(item.getImage());
        } else {
            LinearLayout myTopics = (LinearLayout) view.findViewById(R.id.myTopicsLL);
            ImageView myTopicsIV = (ImageView) view.findViewById(R.id.myTopicsIV);
            myTopics.removeView(myTopicsIV);
        }
        ((TextView) view.findViewById(R.id.myTopicsLikeNum)).setText(item.getLikeNum());
        ((TextView) view.findViewById(R.id.myTopicsCommentNum)).setText(item.getCommentNum());
        ((TextView) view.findViewById(R.id.myTopicsShareNum)).setText(item.getShareNum());

        return view;
    }
}
