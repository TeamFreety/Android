package com.sopt.freety.freety.view.home;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.view.home.adapter.HomeViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/26/17.
 */

public class HomeFragment extends Fragment{

    @BindView(R.id.home_tab)
    TabLayout tabLayout;

    @BindView(R.id.home_view_pager)
    ViewPagerEx viewPager;

    @BindView(R.id.home_app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.home_collaspsing_relative_layout)
    RelativeLayout collapsingRelativeLayout;

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
        return view;
    }
}
