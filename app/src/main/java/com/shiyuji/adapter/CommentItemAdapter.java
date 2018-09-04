package com.shiyuji.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiyuji.R;
import com.shiyuji.model.CommentItem;

import java.util.List;

public class CommentItemAdapter extends ArrayAdapter {

    private final int resourceId;           // 当前视图id
    private Context context;

    private TextView follow;
    private ImageView favorite;

    public CommentItemAdapter(@NonNull Context context, int resourceId, List<CommentItem> items) {
        super(context, resourceId, items);
        this.context = context;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CommentItem item = (CommentItem) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        follow = (TextView) view.findViewById(R.id.CommentFollowTV);

        ((ImageView) view.findViewById(R.id.CommentHeadIV)).setImageResource(item.getHead());
        ((TextView) view.findViewById(R.id.CommentNameTV)).setText(item.getName());
        ((TextView) view.findViewById(R.id.CommentIdTV)).setText("#" + item.getId());
        ((TextView) view.findViewById(R.id.CommentTimeTV)).setText(item.getTime());
        changeFollow(position, view.findViewById(R.id.CommentFollowTV));
        ((TextView) view.findViewById(R.id.CommentTextTV)).setText(item.getText());
        changeLike(position, view.findViewById(R.id.CommentLikeIV));
        ((TextView) view.findViewById(R.id.CommentLikeNumTV)).setText(Integer.toString(item.getLikeNum()));

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CommentItem) getItem(position)).setFollowed(!((CommentItem) getItem(position)).isFollowed());
                changeFollow(position, v);
            }
        });
        LinearLayout likeLL = (LinearLayout) view.findViewById(R.id.CommentLikeLL);
        likeLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CommentItem) getItem(position)).setLiked(!((CommentItem) getItem(position)).isLiked());
                changeLike(position, v);
            }
        });

        return view;
    }

    public void changeFollow(int i, View view) {
        CommentItem item = (CommentItem) getItem(i);
        TextView v = (TextView) view;
        if (item.isFollowed()) {
            v.setText("已关注");
            v.setTextColor(context.getResources().getColor(R.color.darkGray));
        } else {
            v.setText("+ 关注");
            v.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }

    public void changeLike(int i, View view) {
        CommentItem item = (CommentItem) getItem(i);
        ImageView v = view.findViewById(R.id.CommentLikeIV);
        if (item.isLiked()) {
            v.setImageResource(R.drawable.dianzanhou);
        } else {
            v.setImageResource(R.drawable.dianzan);
        }
    }
}
