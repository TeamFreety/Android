package com.sopt.freety.freety.view.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KYJ on 2017-06-28.
 */

public class HomeContentsViewPagerAdapter extends PagerAdapter {

    private final static int PAGE_COUNT = 5;
    private Context context;
    private int tabCount;
    private List<String> imageURLList;
    private List<Integer> imageMockList = new ArrayList<>();

    public HomeContentsViewPagerAdapter(Context context, int tabCount, List<String> imageURLList) {
        this.context = context;
        this.tabCount = tabCount;
        this.imageURLList = imageURLList;
        this.imageMockList = new ArrayList<>();

        imageMockList.add(R.drawable.empty_star);
        imageMockList.add(R.drawable.half_star);
        imageMockList.add(R.drawable.empty_star);
        imageMockList.add(R.drawable.half_star);
        imageMockList.add(R.drawable.star);

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // TODO Auto-generated method stub

        final int realPos = position % (PAGE_COUNT);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.fragment_home_contents, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.home_contents_image_view);
        Glide.with(context).load(imageMockList.get(realPos)).override(164,187).centerCrop().thumbnail(0.001f).into(imageView);

        ((ViewPager)container).addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 1000;
    }

    public int getRealCount() {
        return tabCount;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub

        return view == ((View)object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }

}