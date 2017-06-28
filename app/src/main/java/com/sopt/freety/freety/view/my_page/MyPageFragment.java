package com.sopt.freety.freety.view.my_page;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.view.my_page.adapter.MyPageViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageFragment extends Fragment implements ScrollFeedbackRecyclerView.Callbacks{

    @BindView(R.id.my_page_profile)
    ImageView profileImage;

    @BindView(R.id.my_page_tab)
    TabLayout tabLayout;

    @BindView(R.id.my_page_view_pager)
    ViewPagerEx viewPager;

    @BindView(R.id.my_page_app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.my_page_collaspsing_relative_layout)
    RelativeLayout collapsingRelativeLayout;

    @BindView(R.id.my_page_hide_toolbar)
    Toolbar toolbar;

    private static final float OPACITIY_FACTOR = 1.8f;

    public MyPageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        ButterKnife.bind(this, view);
        Glide.with(this).load(R.drawable.chat_list_elem)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(profileImage);

        tabLayout.addTab(tabLayout.newTab().setText("글목록"));
        tabLayout.addTab(tabLayout.newTab().setText("스타일"));
        tabLayout.addTab(tabLayout.newTab().setText("후기"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        PagerAdapter pagerAdapter = new MyPageViewPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsingRelativeLayout.setAlpha(Math.max(1.0f - (OPACITIY_FACTOR * Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange())), 0));
            }
        });

        return view;
    }

    @Override
    public boolean isAppBarCollapsed() {
        final int appBarVisibleHeight = (int) (appBarLayout.getY() + appBarLayout.getHeight());
        final int toolbarHeight = toolbar.getHeight();
        return (appBarVisibleHeight >= toolbarHeight && appBarVisibleHeight <= toolbarHeight + 25);
    }

    @Override
    public void setExpanded(boolean expanded) {
        appBarLayout.setExpanded(expanded, true);
    }
}
