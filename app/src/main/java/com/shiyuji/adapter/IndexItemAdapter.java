package com.shiyuji.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiyuji.R;
import com.shiyuji.bean.GetImg;
import com.shiyuji.model.IndexItem;
import com.shiyuji.VideoDetailActivity;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class IndexItemAdapter extends ArrayAdapter {
    private final int resourceId;
    private Context context;
    private LayoutInflater inflater;
    private ImageView image1;
    private ImageView image2;
    private TextView text1;
    private TextView text2;
    private TextView id1;
    private TextView id2;


    public IndexItemAdapter(@NonNull Context context, int resource, List<IndexItem> items) {
        super(context, resource, items);
        resourceId = resource;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        IndexItem item = (IndexItem) getItem(position);
        System.out.println("myposition:"+position);
        @SuppressLint("ViewHolder") View view = inflater.inflate(resourceId, null);

        image1 = (ImageView) view.findViewById(R.id.indexIV1);
        image2 = (ImageView) view.findViewById(R.id.indexIV2);
        text1 = (TextView) view.findViewById(R.id.indexTV1);
        text2 = (TextView) view.findViewById(R.id.indexTV2);
        id1 = (TextView) view.findViewById(R.id.id1);
        id2 = (TextView) view.findViewById(R.id.id2);
       /* image1.setImageResource(item.getImage1());
        image2.setImageResource(item.getImage2());*/
if (item.getImageUrl1()!=null){
        image1.setImageBitmap(item.getImageUrl1());
        image2.setImageBitmap(item.getImageUrl2());
        id1.setText(item.getId1()+"");
        id2.setText(item.getId2()+"");
}else {
    image1.setImageResource(item.getImageId1());
    image2.setImageResource(item.getImageId2());
}
        String str1 = item.getText1().length() < 10 ? item.getText1() : item.getText1().substring(0, 11) + "...";
        String str2 = item.getText2().length() < 10 ? item.getText2() : item.getText2().substring(0, 11) + "...";
        text1.setText(str1);
        text2.setText(str2);

        view.findViewById(R.id.CV1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView id1 = (TextView) v.findViewById(R.id.id1);
                Intent intent1 = new Intent(context, VideoDetailActivity.class);
                intent1.putExtra("id",id1.getText());
                context.startActivity(intent1);
            }
        });
        view.findViewById(R.id.CV2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView id2 = (TextView) v.findViewById(R.id.id2);
                Intent intent2 = new Intent(context, VideoDetailActivity.class);
                intent2.putExtra("id",id2.getText());
                context.startActivity(intent2);
            }
        });

        return view;

    }
}
