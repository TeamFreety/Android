package com.sopt.freety.freety.view.my_page;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.util.util.EditTextUtils;
import com.sopt.freety.freety.view.my_page.adapter.MyPageOtherViewPager;
import com.sopt.freety.freety.view.my_page.adapter.MyPageViewPagerAdapter;
import com.sopt.freety.freety.view.my_page.data.MyPagePostData;
import com.sopt.freety.freety.view.my_page.data.MyPageReviewData;
import com.sopt.freety.freety.view.my_page.data.MyPageStyleBodyData;
import com.sopt.freety.freety.view.my_page.data.MyPageStyleHeaderData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageDesignerGetData;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelToDesignerMypageActivity extends AppCompatActivity implements ScrollFeedbackRecyclerView.Callbacks{


    private static final String TAG = "OtherMyPageActivity";
    private static final float OPACITY_FACTOR = 1.8f;

    @BindView(R.id.img_m_to_d_mypage_designer_profile)
    ImageView imgMToDMypageDesignerProfile;

    @BindView(R.id.text_m_to_d_my_page_designer_name)
    TextView textMToDMyPageDesignerName;

    @BindView(R.id.edit_m_to_d_my_page_designer_status)
    EditText editMToDMyPageDesignerStatus;

    @BindView(R.id.my_page_m_to_d_tab)
    TabLayout myPageMToDTab;
    @BindView(R.id.m_to_d_my_page_app_bar)
    AppBarLayout mToDMyPageAppBar;

    @BindView(R.id.my_page_m_to_d_view_pager)
    ViewPagerEx myPageMToDViewPager;
    @BindView(R.id.m_to_d_mypage_designer_collapsing_bar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.btn_m_to_d_my_page_chat)
    ImageButton btnMToDMyPageChat;

    @BindView(R.id.m_to_d_my_page_hide_toolbar)
    Toolbar mToDMyPageHideToolbar;

    private NetworkService networkService;
    private MyPageDesignerGetData myPageDesignerGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_to_designer_mypage);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.chat_list_elem)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(imgMToDMypageDesignerProfile);
        networkService = AppController.getInstance().getNetworkService();
        myPageMToDTab.addTab(myPageMToDTab.newTab().setText("글목록"));
        myPageMToDTab.addTab(myPageMToDTab.newTab().setText("포트폴리오"));
        myPageMToDTab.addTab(myPageMToDTab.newTab().setText("후기"));
        myPageMToDTab.setTabGravity(TabLayout.GRAVITY_FILL);
        myPageMToDTab.setTabTextColors(Color.parseColor("#95989A"), Color.parseColor("#000000"));
        myPageMToDTab.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        myPageMToDTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myPageMToDViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        mToDMyPageAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsingToolbarLayout.setAlpha(Math.max(1.0f - (OPACITY_FACTOR * Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange())), 0));
            }
        });

        if (SharedAccessor.isDesigner(getApplicationContext())) {
            btnMToDMyPageChat.setEnabled(false);
            btnMToDMyPageChat.setVisibility(View.INVISIBLE);
        }
        EditTextUtils.setUseableEditText(editMToDMyPageDesignerStatus, false);
        reload();
    }

    @Override
    public boolean isAppBarCollapsed() {
        final int appBarVisibleHeight = (int) (mToDMyPageAppBar.getY() + mToDMyPageAppBar.getHeight());
        final int toolbarHeight = mToDMyPageHideToolbar.getHeight();
        return (appBarVisibleHeight >= toolbarHeight && appBarVisibleHeight <= toolbarHeight + 25);
    }

    @Override
    public void setExpanded (boolean expanded){
        mToDMyPageAppBar.setExpanded(expanded, true);
    }

    public List<MyPagePostData> getPostDataList() {
        return myPageDesignerGetData.getMyPagePostDataList();
    }

    public List<MyPageStyleBodyData> getStyleBodyDataList() {
        return myPageDesignerGetData.getMyStyleBodyDataList();
    }

    public MyPageStyleHeaderData getStyleHeaderData() {
        return myPageDesignerGetData.getMyPageStyleHeaderData();
    }

    public MyPageReviewData getMyPageReviewData() {
        return myPageDesignerGetData.getMyPageReviewData();
    }

    private void reload() {
        Call<MyPageDesignerGetData> call = networkService.getOtherDesignerMyPage(SharedAccessor.getToken(getApplicationContext()), getIntent().getIntExtra("memberId", 0));
        call.enqueue(new Callback<MyPageDesignerGetData>() {
            @Override
            public void onResponse(Call<MyPageDesignerGetData> call, Response<MyPageDesignerGetData> response) {
                Log.i(TAG, "onResponse: " + response.body().getDesignerImageURL());
                if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                    Log.i(TAG, "onResponse: " + response.body().getDesignerImageURL());
                    myPageDesignerGetData = response.body();
                    Glide.with(getApplicationContext()).load(response.body().getDesignerImageURL())
                            .bitmapTransform(new CropCircleTransformation(ModelToDesignerMypageActivity.this)).into(imgMToDMypageDesignerProfile);
                    textMToDMyPageDesignerName.setText(response.body().getDesignerName());
                    editMToDMyPageDesignerStatus.setText(response.body().getDesignerStatusMsg());
                    myPageMToDViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(myPageMToDTab));
                    MyPageOtherViewPager pagerAdapter = new MyPageOtherViewPager(getSupportFragmentManager());
                    myPageMToDViewPager.setAdapter(pagerAdapter);
                    myPageMToDViewPager.setOffscreenPageLimit(2);
                    myPageMToDViewPager.setCurrentItem(0);
                }
            }
            @Override
            public void onFailure(Call<MyPageDesignerGetData> call, Throwable t) {
                Log.i(TAG, "onResponse: failure");
            }
        });
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

    public int getMemberId() {
        return getIntent().getIntExtra("memberId", 0);
    }
}