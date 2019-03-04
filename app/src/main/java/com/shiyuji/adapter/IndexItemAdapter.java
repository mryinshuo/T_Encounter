package com.shiyuji.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiyuji.R;
import com.shiyuji.model.IndexItem;
import com.shiyuji.VideoDetailActivity;

import java.util.List;

public class IndexItemAdapter extends ArrayAdapter {
    private final int resourceId;
    private Context context;
    private LayoutInflater inflater;
    private ImageView image1;
    private ImageView image2;
    private TextView text1;
    private TextView text2;

    public IndexItemAdapter(@NonNull Context context, int resource, List<IndexItem> items) {
        super(context, resource, items);
        resourceId = resource;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        IndexItem item = (IndexItem) getItem(position);
        View view = inflater.inflate(resourceId, null);

        image1 = (ImageView) view.findViewById(R.id.indexIV1);
        image2 = (ImageView) view.findViewById(R.id.indexIV2);
        text1 = (TextView) view.findViewById(R.id.indexTV1);
        text2 = (TextView) view.findViewById(R.id.indexTV2);

        image1.setImageResource(item.getImage1());
        image2.setImageResource(item.getImage2());

        String str1 = item.getText1().length() < 21 ? item.getText1() : item.getText1().substring(0, 21) + "...";
        String str2 = item.getText2().length() < 21 ? item.getText2() : item.getText2().substring(0, 21) + "...";

        text1.setText(str1);
        text2.setText(str2);

        view.findViewById(R.id.CV1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, VideoDetailActivity.class);
                context.startActivity(intent1);
            }
        });
        view.findViewById(R.id.CV2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(context, VideoDetailActivity.class);
                context.startActivity(intent2);
            }
        });

        return view;

//        class ViewHolder {
//            ImageView img1;
//            TextView text1;
//            ImageView img2;
//            TextView text2;
//        }
//
//        ViewHolder viewHolder;
//        if(convertView == null) {
//            convertView = inflater.inflate(R.layout.activity_index_item, parent, false);
//            viewHolder = new ViewHolder();
//            viewHolder.img1 = (ImageView) convertView.findViewById(R.id.indexIV1);
//            viewHolder.text1 = (TextView) convertView.findViewById(R.id.indexTV1);
//            viewHolder.img2 = (ImageView) convertView.findViewById(R.id.indexIV2);
//            viewHolder.text2 = (TextView) convertView.findViewById(R.id.indexTV2);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder)convertView.getTag();
//        }
//        IndexItem item = items.get(position);
//        viewHolder.img1.setImageResource(item.getImage1());
//        viewHolder.text1.setText(item.getText1());
//        viewHolder.img2.setImageResource(item.getImage2());
//        viewHolder.text2.setText(item.getText2());
//        return convertView;
    }
}
