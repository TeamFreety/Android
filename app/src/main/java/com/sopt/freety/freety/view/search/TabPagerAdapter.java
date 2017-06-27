package com.sopt.freety.freety.view.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by USER on 2017-06-26.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
            SearchFragment searchFragment = new SearchFragment();
            return  searchFragment;

            case 1:
            TabPracticeFragment tabPracticeFragment = new TabPracticeFragment();
                return tabPracticeFragment;

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
