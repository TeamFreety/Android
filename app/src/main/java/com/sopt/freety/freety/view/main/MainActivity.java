package com.sopt.freety.freety.view.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.BottomNavigationViewHelper;
import com.sopt.freety.freety.view.fragment1.Fragment1;
import com.sopt.freety.freety.view.fragment2.Fragment2;
import com.sopt.freety.freety.view.fragment3.Fragment3;
import com.sopt.freety.freety.view.fragment4.Fragment4;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_bottom_nav)
    BottomNavigationViewEx bottomNavigationView;
    @BindView(R.id.main_fragment_content)
    FrameLayout fragmentContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        bottomNavigationView.setTextVisibility(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.action_home:
                        replaceFragment(new Fragment1(), new Bundle(), "home");
                        break;
                    case R.id.action_search:
                        replaceFragment(new Fragment2(), new Bundle(), "search");
                        break;
                    case R.id.action_recruit:
                        replaceFragment(new Fragment3(), new Bundle(), "recruit");
                        break;
                    case R.id.action_my_page:
                        replaceFragment(new Fragment4(), new Bundle(), "my_page");
                        break;
                }
                return true;
            }
        });


        if (savedInstanceState == null) {
            initFragment(new Fragment1(), new Bundle(), "first");
        }

    }

    public void initFragment(Fragment fragment, Bundle bundle, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        fragment.setArguments(bundle);
        transaction.add(R.id.main_fragment_content, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void replaceFragment(Fragment fragment, Bundle bundle, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        fragment.setArguments(bundle);
        transaction.replace(R.id.main_fragment_content, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
