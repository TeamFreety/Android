package com.sopt.freety.freety.view.my_page.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sopt.freety.freety.view.my_page.MyPageDesignerPostFragment;
import com.sopt.freety.freety.view.my_page.MyPageDesignerReviewFragment;
import com.sopt.freety.freety.view.my_page.MyPageDesignerStyleFragment;

/**
 * Created by cmslab on 7/6/17.
 */

public class MyPageOtherViewPager extends FragmentStatePagerAdapter {

    private static final int INDEX_POST_LIST = 0;
    private static final int INDEX_STYLE = 1;
    private static final int INDEX_REVIEW = 2;

    public MyPageOtherViewPager(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case INDEX_POST_LIST:
                MyPageDesignerPostFragment myPagePostFragment = new MyPageDesignerPostFragment();
                myPagePostFragment.setMine(false);
                return myPagePostFragment;
            case INDEX_STYLE:
                MyPageDesignerStyleFragment myPageStyleFragment = new MyPageDesignerStyleFragment();
                myPageStyleFragment.setMine(false);
                return myPageStyleFragment;
            case INDEX_REVIEW:
                MyPageDesignerReviewFragment myPageReviewFragment = new MyPageDesignerReviewFragment();
                myPageReviewFragment.setMine(false);
                return myPageReviewFragment;
            default:
                throw new RuntimeException("There is unexpected position");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
