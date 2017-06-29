package com.sopt.freety.freety.view.recruit.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sopt.freety.freety.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmslab on 6/29/17.
 */

public class RecruitViewPagerAdapter extends PagerAdapter {

    private static final int MAX_VIEW_COUNT = 5;
    private Context context;
    private List<String> imageURLList;
    private List<Integer> imageMockList = new ArrayList<>();

    public RecruitViewPagerAdapter(Context context, List<String> imageURLList) {
        this.context = context;
        this.imageURLList = imageURLList;
        this.imageMockList = new ArrayList<>();
        imageMockList.add(R.drawable.chat_list_elem);
        imageMockList.add(R.drawable.freety_logo);
        imageMockList.add(R.drawable.chat_list_elem);
        imageMockList.add(R.drawable.freety_logo);
        imageMockList.add(R.drawable.chat_list_elem);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        int realPos = position % MAX_VIEW_COUNT;
        View itemView = LayoutInflater.from(context).inflate(R.layout.image_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_item);
        imageView.setImageResource(imageMockList.get(realPos));
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
