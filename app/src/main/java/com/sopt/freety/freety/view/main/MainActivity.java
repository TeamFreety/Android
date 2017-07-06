package com.sopt.freety.freety.view.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.Consts;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.view.home.HomeFragment;
import com.sopt.freety.freety.view.letter.LetterListFragment;
import com.sopt.freety.freety.view.my_page.MyPageDesignerFragment;
import com.sopt.freety.freety.view.my_page.MyPageModelFragment;
import com.sopt.freety.freety.view.search.SearchFragment;
import com.yongbeam.y_photopicker.util.photopicker.PhotoPickerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public static final int RESULT_SUCCESS = 200;
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
                        replaceFragment(new LetterListFragment(), new Bundle(), "recruit");
                        break;
                    case R.id.action_my_page:

                        if (SharedAccessor.isDesigner(MainActivity.this))
                            replaceFragment(new MyPageDesignerFragment(), new Bundle(), "my_page_designer");
                        else
                            replaceFragment(new MyPageModelFragment(), new Bundle(), "my_page_model");
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
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == Consts.DETAIL_SEARCH_CODE || requestCode == Consts.WRITE_REQUEST) && resultCode == RESULT_OK) {
            SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("search");
            searchFragment.onActivityResult(requestCode, 0, data);
        }
        /*else if (requestCode == Consts.MODEL_PICTURE_1_CODE){
            if (resultCode == RESULT_OK) {
                MyPageModelFragment myPageModelFragment = (MyPageModelFragment) getSupportFragmentManager().findFragmentByTag("my_page_model");
                String imagePath = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS).get(0);
                myPageModelFragment.onPictureRegistered(Consts.MODEL_PICTURE_1_CODE, imagePath);
            }
        }else if (requestCode == Consts.MODEL_PICTURE_2_CODE){
            if (resultCode == RESULT_OK) {
                MyPageModelFragment myPageModelFragment = (MyPageModelFragment) getSupportFragmentManager().findFragmentByTag("my_page_model");
                String imagePath = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS).get(0);
                myPageModelFragment.onPictureRegistered(Consts.MODEL_PICTURE_2_CODE, imagePath);
            }
        }else if (requestCode == Consts.MODEL_PICTURE_3_CODE){
            if (resultCode == RESULT_OK) {
                MyPageModelFragment myPageModelFragment = (MyPageModelFragment) getSupportFragmentManager().findFragmentByTag("my_page_model");
                String imagePath = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS).get(0);
                myPageModelFragment.onPictureRegistered(Consts.MODEL_PICTURE_3_CODE, imagePath);
            }
        }*/
        else if (requestCode == Consts.MODEL_PROFILE_PHOTO_CODE){
            if (resultCode == RESULT_OK) {
                MyPageModelFragment myPageModelFragment = (MyPageModelFragment) getSupportFragmentManager().findFragmentByTag("my_page_model");
                String imagePath = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS).get(0);
                myPageModelFragment.onPictureRegistered(Consts.MODEL_PROFILE_PHOTO_CODE, imagePath);
            }
        }else if (requestCode == Consts.DESIGNER_PROFILE_PHOTO_CODE){
            if (resultCode == RESULT_OK) {
                MyPageDesignerFragment myPageDesignerFragment = (MyPageDesignerFragment) getSupportFragmentManager().findFragmentByTag("my_page_designer");
                String imagePath = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS).get(0);
                myPageDesignerFragment.onPictureRegistered(Consts.DESIGNER_PROFILE_PHOTO_CODE, imagePath);
            }
        }
    }
}
