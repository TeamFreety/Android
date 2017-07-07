package com.sopt.freety.freety.view.my_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.view.my_page.adapter.MyPageModelRecyclerAdapter;
import com.sopt.freety.freety.view.my_page.data.network.MyPageModelGetData;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sopt.freety.freety.view.my_page.adapter.MyPageModelRecyclerAdapter.TYPE_HEADER;

public class DesignerToModelMypageActivity extends AppCompatActivity implements ScrollFeedbackRecyclerView.Callbacks {

    @BindView(R.id.d_to_m_my_page_model_hide_toolbar)
    Toolbar dToMMyPageModelHideToolbar;
    @BindView(R.id.d_to_m_my_page_model_profile)
    ImageView dToMMyPageModelProfile;
    @BindView(R.id.d_to_m_text_my_page_model_name)
    TextView dToMTextMyPageDesignerName;
    @BindView(R.id.m_to_d_my_page_model_collaspsing_relative_layout)
    RelativeLayout mToDMyPageModelCollaspsingRelativeLayout;
    @BindView(R.id.d_to_m_my_page_model_app_bar)
    AppBarLayout dToMMyPageModelAppBar;
    @BindView(R.id.d_to_m_my_page_model_recycler_view)
    ScrollFeedbackRecyclerView dToMMyPageModelRecyclerView;

    private GridLayoutManager layoutManager;
    private MyPageModelRecyclerAdapter adapter;
    private static final float OPACITIY_FACTOR = 1.8f;
    private MyPageModelGetData myPageModelGetData;
    private String memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_to_model_mypage);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        memberId = intent.getStringExtra("memberId");
        dToMMyPageModelAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mToDMyPageModelCollaspsingRelativeLayout.setAlpha(Math.max(1.0f - (OPACITIY_FACTOR * Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange())), 0));
            }
        });
        dToMMyPageModelRecyclerView.setHasFixedSize(true);
        dToMMyPageModelRecyclerView.attachCallbacks(this);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if (position == TYPE_HEADER) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        dToMMyPageModelRecyclerView.setLayoutManager(layoutManager);
    }
    @Override
    public boolean isAppBarCollapsed() {
        final int appBarVisibleHeight = (int) (dToMMyPageModelAppBar.getY() + dToMMyPageModelAppBar.getHeight());
        final int toolbarHeight = dToMMyPageModelHideToolbar.getHeight();
        return (appBarVisibleHeight >= toolbarHeight && appBarVisibleHeight <= toolbarHeight + 45);
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    @Override
    public void setExpanded(boolean expanded) {
        dToMMyPageModelAppBar.setExpanded(expanded, true);
    }


    final NetworkService networkService = AppController.getInstance().getNetworkService();
    private void reload() {
        Call<MyPageModelGetData> call = networkService.getOtherModelMyPage(SharedAccessor.getToken(getApplicationContext()), getIntent().getIntExtra("memberId", 0));
        call.enqueue(new Callback<MyPageModelGetData>() {
            @Override
            public void onResponse(Call<MyPageModelGetData> call, Response<MyPageModelGetData> response) {
                if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                    myPageModelGetData = response.body();
                    Glide.with(getApplicationContext()).load(response.body().getModelPhoto())
                            .override(200, 200).thumbnail(0.2f).bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                            .into(dToMMyPageModelProfile);
                    dToMTextMyPageDesignerName.setText(response.body().getModelName());
                    adapter = new MyPageModelRecyclerAdapter(myPageModelGetData.getMyPageModelHeaderDataList(), myPageModelGetData.getModelPickList(), getApplicationContext());
                    dToMMyPageModelRecyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<MyPageModelGetData> call, Throwable t) {}
        });

    }
}

