package com.shiyuji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shiyuji.Application.MyApplication;
import com.shiyuji.adapter.CommentItemAdapter;
import com.shiyuji.bean.Comment;
import com.shiyuji.bean.Comments;
import com.shiyuji.bean.GetImg;
import com.shiyuji.bean.InputMethodUtils;
import com.shiyuji.bean.User;
import com.shiyuji.bean.Video;
import com.shiyuji.bean.getAllcommentUtils;
import com.shiyuji.model.CommentItem;
import com.shiyuji.model.VideoDetailHeaderItem;
import com.shiyuji.myUtils.getPersonMes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.shiyuji.myUtils.getPersonMes.getConnect;

public class VideoDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView videoDetail;
    private List<CommentItem> list;
    private ImageView videoDetailHead;
    private VideoDetailHeaderItem header;
    private TextView followTV;
    private ImageView favoriteIV;
    private ImageView issueComment;
    private LinearLayout commentLL;
    private EditText videoDetailET;
    private LinearLayout likeLL;
    private ImageView likeIV;
    private ImageView prev;
    private boolean liked = false;
    private String id=null;
    static public int vid;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_detail);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        vid= Integer.parseInt(id);
        videoDetail = (ListView) findViewById(R.id.videoDetailLV);
        init();
        videoDetail.setAdapter(new CommentItemAdapter(this, R.layout.comment_item, list));
        header = new VideoDetailHeaderItem(this, true, true, true);

        System.out.println("myid"+id);


        try {
            initHeader(header,id);
            videoDetail.addHeaderView(header);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        videoDetailHead = (ImageView) findViewById(R.id.videoDetailHead);
        followTV = (TextView) findViewById(R.id.videoDetailFollow);
        favoriteIV = (ImageView) findViewById(R.id.videoDetailFavorite);
        commentLL = (LinearLayout) findViewById(R.id.videoDetailCommentLL);
        videoDetailET = (EditText) findViewById(R.id.videoDetailET);
        likeLL = (LinearLayout) findViewById(R.id.videoDetailLikeLL);

       /* issueComment = (ImageView) findViewById(R.id.issueComment);*/
        issueComment = (ImageView) findViewById(R.id.issueComment);
        likeIV = (ImageView) findViewById(R.id.videoDetailLikeIV);
        prev = (ImageView) findViewById(R.id.videoDetailPrev);
        videoDetailHead.setOnClickListener(this);
        followTV.setOnClickListener(this);
        issueComment.setOnClickListener(this);
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
            case R.id.issueComment:

                Editable text = videoDetailET.getText();
                String phone =  MyApplication.phone;
                Comments.addComment(phone,vid,text.toString());//发表评论
                Toast.makeText(getApplicationContext(), "发表成功",
                        Toast.LENGTH_LONG).show();
                InputMethodUtils.showOrHide(this);
                videoDetailET.setText("");//清空
                init();
                videoDetail = (ListView) findViewById(R.id.videoDetailLV);
                videoDetail.setAdapter(new CommentItemAdapter(this, R.layout.comment_item, list));
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
        int n = 0;
        list = new ArrayList<>();
        String headUrl;
        System.out.println("vid:"+vid);
        ArrayList<Comment> comments = getAllcommentUtils.getConnect(vid);
        if (comments!=null){
            for (Comment comment :comments) {
                n++;
                User connect = getConnect(comment.getUid());
                if (connect!=null) {
                    if (connect.getHeadUrl() != null) {
                        headUrl =connect.getHeadUrl();
                        list.add(new CommentItem("headImage/"+headUrl, connect.getName(), n + "", comment.getTime(), true, comment.getContent(), true, 10));
                    }else {
                        header.setHead("headImage/logo.png");//头像
                    }

                }else{
                    list.add(new CommentItem("headImage/logo.png", "匿名用户", n + "", comment.getTime(), true, comment.getContent(), true, 10));
                }
            }
        }else{
            list.add(new CommentItem("1.png", "山西某不知名网友", "4", "8-7", true, "蕴含有丰富的美学意味。", true, 20));
            list.add(new CommentItem("1.png", "家里有只小旺仔", "3", "8-7", true, "河曲民歌吟唱内容丰富，涵盖社会生活的各个层面。", true, 30));
            list.add(new CommentItem("1.png", "呆酱", "2", "8-7", true, "一个上下句就揭示出一种深邃的感情状况或描绘出一种逼真的生活画面。", true, 40));
            list.add(new CommentItem("1.png", "李三岁", "1", "8-7", true, "极具文化神韵的传统经典原生态乡土民歌", true, 50));
        }
       /* list.add(new CommentItem(R.drawable.chuanju, "山西某不知名网友", "4", "8-7", true, "蕴含有丰富的美学意味。", true, 20));
        list.add(new CommentItem(R.drawable.pic3, "家里有只小旺仔", "3", "8-7", true, "河曲民歌吟唱内容丰富，涵盖社会生活的各个层面。", true, 30));
        list.add(new CommentItem(R.drawable.kongquewu, "呆酱", "2", "8-7", true, "一个上下句就揭示出一种深邃的感情状况或描绘出一种逼真的生活画面。", true, 40));
        list.add(new CommentItem(R.drawable.kunqu, "李三岁", "1", "8-7", true, "极具文化神韵的传统经典原生态乡土民歌", true, 50));*/
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initHeader(VideoDetailHeaderItem header, String id) throws ParseException {
        vid= Integer.parseInt(id);
        ArrayList<Video> video;
        video = GetImg.getConnect(id);
        Date d = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateNowStr = sdf.parse(sdf.format(d));

       /* long daysDiff = ChronoUnit.DAYS.between(startDate, endDate);*/
        if (video!=null) {
            for (Video video1 : video) {
                User connect = getConnect(video1.getUid());
                int days = (int) ((dateNowStr.getTime() - sdf.parse(video1.getTime()).getTime()) / (1000 * 60 * 60 * 24));
                header.setPreview(video1.getUrl());
                if (connect.getHeadUrl() != null) {
                    header.setHead("headImage/"+connect.getHeadUrl());//头像
                }else {
                    header.setHead("headImage/logo.png");//头像
                }
                header.setName(video1.getTitle());
                header.setTime(days + "天前");
                header.setVisitNum(1802);
                header.setText(video1.getIntro());
                header.setLikeNum(10);
                header.setCommentNum(20);
                header.setShareNum(30);
            }
        }else {
            header.setHead("1.png");
            header.setName("请检查网络");
            header.setTime( "***天前");
            header.setVisitNum(1802);
            header.setText("网络不可用");
            header.setLikeNum(10);
            header.setCommentNum(20);
            header.setShareNum(30);
        }
    }
}
