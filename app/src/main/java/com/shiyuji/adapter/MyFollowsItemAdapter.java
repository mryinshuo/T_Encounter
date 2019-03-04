package com.shiyuji.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiyuji.R;
import com.shiyuji.model.MyFollowsItem;

import java.util.List;

public class MyFollowsItemAdapter extends ArrayAdapter {
    private final int resourceId;
    private LayoutInflater inflater;

    public MyFollowsItemAdapter(@NonNull Context context, int resource, List<MyFollowsItem> items) {
        super(context, resource, items);
        resourceId = resource;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyFollowsItem item = (MyFollowsItem) getItem(position);
        View view = inflater.inflate(resourceId, null);

        ((ImageView) view.findViewById(R.id.myFollowIV)).setImageResource(item.getImage());
        ((TextView) view.findViewById(R.id.myFollowName)).setText(item.getName());
        ((TextView) view.findViewById(R.id.myFollowText)).setText(item.getText());
        ((TextView) view.findViewById(R.id.myFollowFanNum)).setText(item.getFanNum());
        ((TextView) view.findViewById(R.id.myFollowFollowNum)).setText(item.getFollowNum());

        Button unfollow = (Button) view.findViewById(R.id.myFollowButton);
        unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFollowsItem innerItem = (MyFollowsItem) getItem(position);
                if (innerItem.isFollowed()) {
                    ((Button) v).setText("+ 关注");
                    innerItem.setFollowed(false);
                } else {
                    ((Button) v).setText("取消关注");
                    innerItem.setFollowed(true);
                }
            }
        });

        return view;
    }
}
