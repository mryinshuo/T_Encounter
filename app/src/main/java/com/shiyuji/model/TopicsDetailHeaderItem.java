package com.shiyuji.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shiyuji.R;

public class TopicsDetailHeaderItem extends LinearLayout {

    private Context context;
    private LayoutInflater inflater;

    private boolean isFollowed;
    private boolean isFavorite;

    private ImageView headIV;
    private TextView nameTV;
    private TextView timeTV;
    private TextView visitNumTV;
    private TextView followTV;
    private TextView textTV;
    private ImageView imageIV;
    private TextView likeNumTV;
    private TextView commentNumTV;
    private TextView shareNumTV;
    private ImageButton favoriteIB;

    public TopicsDetailHeaderItem(Context context) {
        super(context);
    }

    public TopicsDetailHeaderItem(final Context context, boolean isFollowed, boolean isFavorite) {
        super(context);
        this.context = context;
        this.isFollowed = isFollowed;
        this.isFavorite = isFavorite;

        inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.channel_topic_detail_header, null);
        addView(view);

        headIV = (ImageView) view.findViewById(R.id.topicDetailHead);
        nameTV = (TextView) view.findViewById(R.id.topicDetailName);
        timeTV = (TextView) view.findViewById(R.id.topicDetailTime);
        visitNumTV = (TextView) view.findViewById(R.id.topicDetailVisitNum);
        followTV = (TextView) view.findViewById(R.id.topicDetailFollow);
        textTV = (TextView) view.findViewById(R.id.topicDetailText);
        imageIV = (ImageView) view.findViewById(R.id.topicDetailImage);
        likeNumTV = (TextView) view.findViewById(R.id.topicDetailLikeNum);
        commentNumTV = (TextView) view.findViewById(R.id.topicDetailCommentNum);
        shareNumTV = (TextView) view.findViewById(R.id.topicDetailShareNum);
        favoriteIB = (ImageButton) view.findViewById(R.id.topicDetailFavorite);

        setFollowed(isFollowed);
        setFavorite(isFavorite);
    }

    public void setHead(int head) {
        headIV.setImageResource(head);
    }

    public void setName(String name) {
        nameTV.setText(name);
    }

    public void setTime(String time) {
        timeTV.setText(time);
    }

    public void setVisitNum(int visitNum) {
        visitNumTV.setText(Integer.toString(visitNum));
    }

    public boolean getFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        if (followed) {
            followTV.setText("已关注");
            followTV.setTextColor(getResources().getColor(R.color.darkGray));
            isFollowed = true;
        } else {
            followTV.setText("+ 关注");
            followTV.setTextColor(getResources().getColor(R.color.colorPrimary));
            isFollowed = false;
        }
    }

    public void setText(String text) {
        textTV.setText(text);
    }

    public void setImage(int image) {
        imageIV.setImageResource(image);
    }

    public void setLikeNum(int likeNum) {
        likeNumTV.setText(Integer.toString(likeNum));
    }

    public void setCommentNum(int commentNum) {
        commentNumTV.setText(Integer.toString(commentNum));
    }

    public void setShareNum(int shareNum) {
        shareNumTV.setText(Integer.toString(shareNum));
    }

    public boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        if (favorite) {
            favoriteIB.setBackgroundResource(R.drawable.xihuanhou);
            isFavorite = true;
        } else {
            favoriteIB.setBackgroundResource(R.drawable.xihuan);
            isFavorite = false;
        }
    }
}
