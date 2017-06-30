package com.sopt.freety.freety.view.home;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.view.home.adapter.HomeContentsViewPagerAdapter;
import com.sopt.freety.freety.view.home.adapter.HomeViewPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/26/17.
 */

public class HomeFragment extends Fragment implements ScrollFeedbackRecyclerView.Callbacks{

    private int CURRENT_PAGE = 5000;
    private int PRE_PAGE;
    private final static int PAGE_COUNT = 5;
    private Timer swipeTimer;

    @BindView(R.id.home_tab)
    TabLayout tabLayout;

    @BindView(R.id.home_view_pager)
    ViewPagerEx viewPager;

    @BindView(R.id.home_app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.home_collaspsing_relative_layout)
    RelativeLayout collapsingRelativeLayout;

    @BindView(R.id.home_hide_toolbar)
    Toolbar toolbar;

    @BindView(R.id.home_contents_view_pager) ViewPager contentsViewPager;

    @BindView(R.id.indicator)
    CirclePageIndicator indicator;


    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        tabLayout.addTab(tabLayout.newTab().setText("전체"));
        tabLayout.addTab(tabLayout.newTab().setText("펌"));
        tabLayout.addTab(tabLayout.newTab().setText("염색"));
        tabLayout.addTab(tabLayout.newTab().setText("커트"));
        tabLayout.addTab(tabLayout.newTab().setText("기타"));
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
        PagerAdapter pagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(0);

        // contents view pager





        final HomeContentsViewPagerAdapter homeContentsViewPagerAdapter = new HomeContentsViewPagerAdapter(getActivity(), Collections.<String>emptyList());
        contentsViewPager.setAdapter(homeContentsViewPagerAdapter);
        contentsViewPager.setCurrentItem(CURRENT_PAGE);
       // indicator.findViewById(R.id.indicator);
        //indicator.setViewPager(contentsViewPager);

       // contentsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
               /*if (CURRENT_PAGE == PAGE_COUNT) {
                    CURRENT_PAGE = 0;
                }*/
                contentsViewPager.setCurrentItem(CURRENT_PAGE++, true);
                //PRE_PAGE = CURRENT_PAGE-1;
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 5000);
        return view;
    }


    @Override
    public boolean isAppBarCollapsed() {
        final int appBarVisibleHeight = (int) (appBarLayout.getY() + appBarLayout.getHeight());
        final int toolbarHeight = toolbar.getHeight();
        return (appBarVisibleHeight >= toolbarHeight && appBarVisibleHeight <= toolbarHeight + 25);
    }

    @Override
    public void setExpanded(boolean expanded) { appBarLayout.setExpanded(expanded, true); }
}
