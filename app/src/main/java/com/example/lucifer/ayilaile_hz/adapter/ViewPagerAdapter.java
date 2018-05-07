package com.example.lucifer.ayilaile_hz.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by quan on 2017/3/24.
 */

public class ViewPagerAdapter extends PagerAdapter {
    public ArrayList<ImageView> imageViewList;

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    // 指定复用的判断逻辑, 固定写法
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // container: 容器: ViewPager
        // position: 当前要显示条目的位置 0 -> 4

        int newPosition = position % imageViewList.size();

        ImageView imageView = imageViewList.get(newPosition);
        // a. 把View对象添加到container中
        container.addView(imageView);
        // b. 把View对象返回给框架, 适配器
        return imageView; // 必须重写, 否则报异常
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
