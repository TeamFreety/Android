package com.sopt.freety.freety.view.my_page.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.data.PostListResultData;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.view.my_page.MyPagePostFragment;
import com.sopt.freety.freety.view.my_page.MyPageReviewFragment;
import com.sopt.freety.freety.view.my_page.MyPageStyleFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                MyPagePostFragment myPagePostFragment = new MyPagePostFragment();
                myPagePostFragment.initMyPagePostFragment();
                return myPagePostFragment;
            case INDEX_STYLE:
                MyPageStyleFragment myPageStyleFragment = new MyPageStyleFragment();
                myPageStyleFragment.initMyPageStyleFragment();
                return myPageStyleFragment;
            case INDEX_REVIEW:
                MyPageReviewFragment myPageReviewFragment = new MyPageReviewFragment();
                myPageReviewFragment.initMyPageReviewFragment();
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
