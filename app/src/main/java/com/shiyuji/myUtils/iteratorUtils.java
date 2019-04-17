package com.shiyuji.myUtils;

import com.shiyuji.bean.Video;
import com.shiyuji.model.IndexItem;

import java.util.List;
import java.util.ListIterator;

public class iteratorUtils {
    String url1=null;
    String text1=null;
    String url2=null;
    String text2=null;
    int id1=10;
    int id2=10;
    int counter = 0;
    public void GetIterator(ListIterator iterator, List<IndexItem> recommendItems){
        while (iterator.hasNext()){
            Video video= (Video) iterator.next();
            if (counter==0){
                url1=video.getUrl();
                text1 = video.getIntro();
                id1=video.getVid();
                counter++;
            }else {
                url2=video.getUrl();
                text2 = video.getIntro();
                id2=video.getVid();
                System.out.println("ididurl: id1:"+url1+"+id2:"+url2);
                recommendItems.add(new IndexItem(url1,text1,id1,url2,text2,id2));
                counter = 0;
            }
        }
        /*开始循环*/
/*        while(iterator.hasPrevious()){
            Video video= (Video) iterator.previous();
            if (counter==0){
                url1=video.getUrl();
                text1 = video.getIntro();
                id1=video.getVid();
                counter++;
            }else {
                url2=video.getUrl();
                text2 = video.getIntro();
                id2=video.getVid();
                System.out.println("ididurl: id1:"+url1+"+id2:"+url2);
                recommendItems.add(new IndexItem(url1,text1,id1,url2,text2,id2));
                counter = 0;
            }
        }*/
        /*结束循环*/
    }
}
