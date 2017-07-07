package com.sopt.freety.freety.view.my_page.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.view.my_page.MyPageDesignerPortfolioFragment;
import com.sopt.freety.freety.view.my_page.MyPageDesignerPostFragment;
import com.sopt.freety.freety.view.my_page.MyPageDesignerReviewFragment;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int INDEX_POST_LIST = 0;
    private static final int INDEX_STYLE = 1;
    private static final int INDEX_REVIEW = 2;
    private final int tabCount;

    private int currType;
    private NetworkService networkService;

    public MyPageViewPagerAdapter(final FragmentManager fm, final int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case INDEX_POST_LIST:
                MyPageDesignerPostFragment myPagePostFragment = new MyPageDesignerPostFragment();
                myPagePostFragment.setMine(true);
                return myPagePostFragment;
            case INDEX_STYLE:
                MyPageDesignerPortfolioFragment myPageStyleFragment = new MyPageDesignerPortfolioFragment();
                myPageStyleFragment.setMine(true);
                return myPageStyleFragment;
            case INDEX_REVIEW:
                MyPageDesignerReviewFragment myPageReviewFragment = new MyPageDesignerReviewFragment();
                myPageReviewFragment.setMine(true);
                return myPageReviewFragment;
            default:
                throw new RuntimeException("There is unexpected position");
        }
    }


    @Override
    public int getCount() {
        return tabCount;
    }
}
