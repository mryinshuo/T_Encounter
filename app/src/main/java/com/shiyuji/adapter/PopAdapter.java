package com.shiyuji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiyuji.R;
import com.shiyuji.model.PopItem;

import java.util.List;

public class PopAdapter extends BaseAdapter {
    private Context mContext;
    private List<PopItem> mList;
    private int selectedPosition;

    public PopAdapter(Context mContext, List<PopItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    public int getCount() {
        return mList.size();
    }


    public Object getItem(int position) {
        return mList.get(position);
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.channel_videos_pop_item, null);
            holder.tv_item = convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_item.setText(mList.get(position).getContent());

        if (position == selectedPosition) {
            convertView.setActivated(true);
        } else {
            convertView.setActivated(false);
        }

        return convertView;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        this.notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
    }

    private class ViewHolder {
        TextView tv_item;
    }
}
