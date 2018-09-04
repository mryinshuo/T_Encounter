package com.shiyuji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.shiyuji.adapter.PopAdapter;
import com.shiyuji.model.PopItem;

import java.util.ArrayList;
import java.util.List;

public class PopMenu {
    private Context mContext;

    //popupWindow可以在activity中被调用
    public PopupWindow popupWindow;

    public String[] results = new String[2];

    private List<List<PopItem>> popList = new ArrayList<>();

    private ListView firstListView;

    private PopAdapter firstAdapter;

    public static final String FINISHED_FLAG = "FINISHED_POP";

    List<PopItem> itemList;

    public PopMenu(Context mContext, final List<PopItem> itemList, int width, int height, View view, Drawable drawable, ListView ... listViews) {

        this.mContext = mContext;
        this.itemList = itemList;


        if (listViews.length == 0) {
            Toast.makeText(mContext, "数据不存在，请检查数据", Toast.LENGTH_SHORT).show();
            return;
        }
        if (listViews.length >= 1) {
            this.firstListView = listViews[0];
        }

        popListInit(popList, itemList);

        initOnePop();

        //初始化PopupWindow
        popupWindow = new PopupWindow(view, width, height, true);
        popupWindow.setFocusable(false);
        popupWindow.setBackgroundDrawable(drawable);


    }

    private void initOnePop() {

        //Listview与适配器的初始化
        firstAdapter = new PopAdapter(mContext, popList.get(0));
        firstListView.setAdapter(firstAdapter);

        // 设置ListView点击事件监听

        firstListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                firstAdapter.setSelectedPosition(position);
                // 一级菜单的选择可以直接通过itemList设置结果放入result[0]中
                // results[0] = popList.get(0).get(position).getContent();
                results[0] = itemList.get(position).getContent();
                results[1] = FINISHED_FLAG;
                // 选择完后关闭popup窗口
                popupWindow.dismiss();
            }
        });
    }

    public static void popListInit(List<List<PopItem>> popList, List<PopItem> itemList){
        popList.add(new ArrayList<PopItem>());
        List<List<Integer>> mList = new ArrayList<>();

        for(PopItem item :itemList){
            mList.add(new ArrayList<Integer>());
        }

        for(int i = 0 ; i < mList.size() ; i++) {
            popList.add(new ArrayList<PopItem>());
            PopItem item = itemList.get(i);
            popList.get(0).add(item);

        }
    }
}
