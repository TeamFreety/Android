package com.sopt.freety.freety.view.my_page.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sopt.freety.freety.view.my_page.MyPagePostFragment;
import com.sopt.freety.freety.view.my_page.MyPageReviewFragment;
import com.sopt.freety.freety.view.my_page.MyPageStyleFragment;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int INDEX_POST_LIST = 0;
    private static final int INDEX_STYLE = 1;
    private static final int INDEX_REVIEW = 2;
    private final int tabCount;

    public MyPageViewPagerAdapter(final FragmentManager fm, final int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case INDEX_POST_LIST:
                return new MyPagePostFragment();
            case INDEX_STYLE:
                return new MyPageStyleFragment();
            case INDEX_REVIEW:
                return new MyPageReviewFragment();
            default:
                throw new RuntimeException("There is unexpected position");
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}