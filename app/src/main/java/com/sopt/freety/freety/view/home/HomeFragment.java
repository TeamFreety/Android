package com.sopt.freety.freety.view.home;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

    public static class PostType {
        public static final int NEW = 0;
        public static final int PERM = 1;
        public static final int DYE = 2;
        public static final int CUT = 3;
        public static final int ETC = 4;
    }

    private final static int PAGE_COUNT = 5;

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

    @BindView(R.id.indicatorTab) TabLayout indicatorTabLayout;

    private int currPageCount = 100;
    private Timer swipeTimer;

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
        viewPager.setCurrentItem(currPageCount);

        // contents view pager
        for (int count = 0; count < PAGE_COUNT; count++) {
            indicatorTabLayout.addTab(indicatorTabLayout.newTab());
        }

        LinearLayout tabStrip = ((LinearLayout) indicatorTabLayout.getChildAt(0));
        for (int i = 0; i <  tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

        startTimer();
        final HomeContentsViewPagerAdapter homeContentsViewPagerAdapter = new HomeContentsViewPagerAdapter(getActivity(), indicatorTabLayout.getTabCount(), Collections.<String>emptyList());
        contentsViewPager.setAdapter(homeContentsViewPagerAdapter);
        contentsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                Log.i("HomeFragment", "onPageSelected: ");
                currPageCount = position;
                int realPos = position % 5;
                TabLayout.Tab currIndicator = indicatorTabLayout.getTabAt(realPos);
                currIndicator.select();
                startTimer();
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

    private void startTimer() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                contentsViewPager.setCurrentItem(currPageCount + 1, true);
            }
        };
        if (swipeTimer != null) {
            swipeTimer.cancel();
            swipeTimer.purge();
        }

        swipeTimer = new Timer();

        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);
    }

}
