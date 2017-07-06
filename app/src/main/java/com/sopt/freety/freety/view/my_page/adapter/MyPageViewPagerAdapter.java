package com.sopt.freety.freety.view.my_page.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.data.PostListResultData;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.view.my_page.MyPageDesignerPostFragment;
import com.sopt.freety.freety.view.my_page.MyPageDesignerReviewFragment;
import com.sopt.freety.freety.view.my_page.MyPageDesignerStyleFragment;

import com.sopt.freety.freety.view.my_page.MyPageDesignerPostFragment;
import com.sopt.freety.freety.view.my_page.MyPageDesignerReviewFragment;
import com.sopt.freety.freety.view.my_page.MyPageDesignerStyleFragment;


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
                MyPageDesignerPostFragment myPagePostFragment = new MyPageDesignerPostFragment();
                myPagePostFragment.initMyPageDesignerPostFragment();
                return myPagePostFragment;
            case INDEX_STYLE:
                MyPageDesignerStyleFragment myPageStyleFragment = new MyPageDesignerStyleFragment();
                myPageStyleFragment.initMyPageDesignerStyleFragment();
                return myPageStyleFragment;
            case INDEX_REVIEW:
                MyPageDesignerReviewFragment myPageReviewFragment = new MyPageDesignerReviewFragment();
                myPageReviewFragment.initMyPageDesignerReviewFragment();
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
