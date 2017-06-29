package com.sopt.freety.freety.view.home;


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

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/26/17.
 */

public class HomeFragment extends Fragment implements ScrollFeedbackRecyclerView.Callbacks{

    private int CURRENT_PAGE = 5000;
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

    @BindView(R.id.page_mark_1)
    ImageView pageMark1;
    @BindView(R.id.page_mark_2)
    ImageView pageMark2;
    @BindView(R.id.page_mark_3)
    ImageView pageMark3;
    @BindView(R.id.page_mark_4)
    ImageView pageMark4;
    @BindView(R.id.page_mark_5)
    ImageView pageMark5;


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
        contentsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (CURRENT_PAGE == PAGE_COUNT) {
                    CURRENT_PAGE = 0;
                }
                contentsViewPager.setCurrentItem(CURRENT_PAGE++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 5000);

        contentsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
//이전 페이지에 해당하는 페이지 표시 이미지 변경
                mPageMark.getChildAt(mPrevPosition).setBackgroundResource(R.drawable.page_not);

                //현재 페이지에 해당하는 페이지 표시 이미지 변경
                mPageMark.getChildAt(position).setBackgroundResource(R.drawable.page_select);
                mPrevPosition = position;                //이전 포지션 값을 현재로 변경
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
    public void setExpanded(boolean expanded) { appBarLayout.setExpanded(expanded, true); }
}
