package com.sopt.freety.freety.view.recruit.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;

import java.util.List;

/**
 * Created by cmslab on 6/29/17.
 */

public class RecruitViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<String> imageURLList;

    public RecruitViewPagerAdapter(Context context, List<String> imageURLList) {
        this.context = context;
        this.imageURLList = imageURLList;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        int realPos = position % imageURLList.size();
        View itemView = LayoutInflater.from(context).inflate(R.layout.image_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_item);
        Glide.with(context).load(imageURLList.get(realPos)).override(360, 360).thumbnail(0.3f).into(imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return 2000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
