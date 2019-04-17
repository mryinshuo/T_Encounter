package com.shiyuji.adapter;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiyuji.R;
import com.shiyuji.model.CommentItem;

import java.util.List;

public class CommentItemAdapter extends ArrayAdapter {

    private static final String TAG = "CommentItemAdapter";
    private static ViewHolder viewHolder;
    private final int resourceId;
    private Context context;

    public CommentItemAdapter(Context context, int resourceId, List<CommentItem> items) {
        super(context, resourceId, items);
        this.context = context;
        this.resourceId = resourceId;
    }

    private class ViewHolder {
        private LinearLayout CommentLikeLL;
        private TextView follow;
        private ImageView CommentHeadIV;
        private TextView CommentNameTV;
        private TextView CommentIdTV;
        private TextView CommentTimeTV;
        private TextView CommentTextTV;
        private ImageView CommentLikeIV;
        private TextView CommentLikeNumTV;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        viewHolder = null;

        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment_item, null);

            viewHolder = new ViewHolder();
            viewHolder.CommentLikeLL = (LinearLayout) convertView.findViewById(R.id.CommentLikeLL);
            viewHolder.follow = (TextView) convertView.findViewById(R.id.CommentFollowTV);
            viewHolder.CommentHeadIV = (ImageView) convertView.findViewById(R.id.CommentHeadIV);
            viewHolder.CommentNameTV = (TextView) convertView.findViewById(R.id.CommentNameTV);
            viewHolder.CommentIdTV = (TextView) convertView.findViewById(R.id.CommentIdTV);
            viewHolder.CommentTimeTV = (TextView) convertView.findViewById(R.id.CommentTimeTV);
            viewHolder.CommentTextTV = (TextView) convertView.findViewById(R.id.CommentTextTV);
            viewHolder.CommentLikeIV = (ImageView) convertView.findViewById(R.id.CommentLikeIV);
            viewHolder.CommentLikeNumTV = (TextView) convertView.findViewById(R.id.CommentLikeNumTV);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CommentItem item = (CommentItem) getItem(i);
        viewHolder.CommentHeadIV.setImageBitmap(item.getImageUrl1());
        viewHolder.CommentNameTV.setText(item.getName());
        viewHolder.CommentIdTV.setText("#" + item.getId());
        viewHolder.CommentTimeTV.setText(item.getTime());
        viewHolder.CommentTextTV.setText(item.getText());
        viewHolder.CommentLikeNumTV.setText(Integer.toString(item.getLikeNum()));
        viewHolder.CommentLikeIV.setImageResource(item.isLiked() ? R.drawable.dianzanhou : R.drawable.dianzan);

        viewHolder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentItem item = (CommentItem) getItem(i);
                item.setFollowed(!item.isFollowed());
                changeFollow(i, v);
            }
        });
        viewHolder.CommentLikeLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentItem item = (CommentItem) getItem(i);
                item.setLiked(!item.isLiked());
                ((ImageView) v.findViewById(R.id.CommentLikeIV)).setImageResource(item.isLiked() ? R.drawable.dianzanhou : R.drawable.dianzan);
            }
        });

        return convertView;
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
}