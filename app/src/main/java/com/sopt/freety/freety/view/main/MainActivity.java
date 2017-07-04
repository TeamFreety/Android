package com.sopt.freety.freety.view.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.view.chat.ChatListFragment;
import com.sopt.freety.freety.view.my_page.MyPageDesignerFragment;
import com.sopt.freety.freety.view.search.SearchFragment;
import com.sopt.freety.freety.view.home.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sopt.freety.freety.view.search.SearchFragment.DETAIL_SEARCH_CODE;

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
                        replaceFragment(new HomeFragment(), new Bundle(), "home");
                        break;
                    case R.id.action_search:
                        replaceFragment(new SearchFragment(), new Bundle(), "search");
                        break;
                    case R.id.action_recruit:
                        replaceFragment(new ChatListFragment(), new Bundle(), "recruit");
                        break;
                    case R.id.action_my_page:
                        replaceFragment(new MyPageDesignerFragment(), new Bundle(), "my_page");
                        break;
                }
                AppController.getInstance().pushPageStack();
                return true;
            }
        });

        if (savedInstanceState == null) {
            initFragment(new HomeFragment(), new Bundle(), "first");
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

    @Override
    public void onBackPressed() {
        int result = AppController.getInstance().popPageStack();
        if (result == 0) {
            Toast.makeText(this, "한 번 더 터치하시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }  else if (result < 0) {
            ActivityCompat.finishAffinity(this);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DETAIL_SEARCH_CODE) {
            SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("search");
            searchFragment.onActivityResult(requestCode, 0, data);
        }
    }
}
