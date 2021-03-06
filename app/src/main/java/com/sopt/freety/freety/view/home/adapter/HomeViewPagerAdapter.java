package com.sopt.freety.freety.view.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sopt.freety.freety.view.home.HomeFragment;
import com.sopt.freety.freety.view.home.HomePostFragment;

/**
 * Created by cmslab on 6/26/17.
 */

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int INDEX_ALL = 0;
    private static final int INDEX_PERM = 1;
    private static final int INDEX_DYE = 2;
    private static final int INDEX_CUT = 3;
    private static final int INDEX_ETC = 4;
    private final int tabCount;

    public HomeViewPagerAdapter(final FragmentManager fm, final int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }


    // 각 프레그먼트가 아예 다르지 X

    @Override
    public Fragment getItem(int position) {
        HomePostFragment homePostFragment;
        switch(position) {
            case INDEX_ALL:
                homePostFragment = new HomePostFragment();
                homePostFragment.   initPostFragment(HomeFragment.PostType.NEW);
                return homePostFragment;
            case INDEX_PERM:
                homePostFragment = new HomePostFragment();
                homePostFragment.initPostFragment(HomeFragment.PostType.PERM);
                return homePostFragment;
            case INDEX_DYE:
                homePostFragment = new HomePostFragment();
                homePostFragment.initPostFragment(HomeFragment.PostType.DYE);
                return homePostFragment;
            case INDEX_CUT:
                homePostFragment = new HomePostFragment();
                homePostFragment.initPostFragment(HomeFragment.PostType.CUT);
                return homePostFragment;
            case INDEX_ETC:
                homePostFragment = new HomePostFragment();
                homePostFragment.initPostFragment(HomeFragment.PostType.ETC);
                return homePostFragment;
            default:
                throw new RuntimeException("There is unexpected position");
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
