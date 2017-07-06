package com.sopt.freety.freety.view.my_page;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;

import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.custom.ItemOffsetDecoration;

import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.view.my_page.adapter.MyPageModelRecyclerAdapter;
import com.sopt.freety.freety.view.my_page.adapter.MyPageViewPagerAdapter;
import com.sopt.freety.freety.view.my_page.data.MyPageModelHeaderData;
import com.sopt.freety.freety.view.my_page.data.MyPagePickData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageDesignerGetData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageModelGetData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sopt.freety.freety.view.my_page.adapter.MyPageModelRecyclerAdapter.TYPE_HEADER;


/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageModelFragment extends Fragment implements ScrollFeedbackRecyclerView.Callbacks{

    @BindView(R.id.my_page_model_profile)
    ImageView profileImage;

    @BindView(R.id.my_page_model_recycler_view)
    ScrollFeedbackRecyclerView recyclerView;

    @BindView(R.id.my_page_model_app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.my_page_model_collaspsing_relative_layout)
    RelativeLayout collapsingRelativeLayout;

    @BindView(R.id.my_page_model_hide_toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_my_page_model_name)
    TextView modelNameText;

    private GridLayoutManager layoutManager;
    private MyPageModelRecyclerAdapter adapter;
    private NetworkService networkService;
    private MyPageModelGetData myPageModelGetData;


    private static final float OPACITIY_FACTOR = 1.8f;

    public MyPageModelFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_page_model, container, false);
        ButterKnife.bind(this, view);
        Glide.with(this).load(R.drawable.chat_list_elem)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(profileImage);

        networkService = AppController.getInstance().getNetworkService();
        reload();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsingRelativeLayout.setAlpha(Math.max(1.0f - (OPACITIY_FACTOR * Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange())), 0));
            }
        });

        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.my_page_post_offset));
        recyclerView.attachCallbacks(this);

        layoutManager = new GridLayoutManager(getContext(), 2);
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
        final List<MyPageModelHeaderData> mockDataList1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mockDataList1.add(MyPageModelHeaderData.getMockData());
        }
        final List<MyPagePickData> mockDataList2 = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            mockDataList2.add(MyPagePickData.getMockData());
        }
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyPageModelRecyclerAdapter(mockDataList1, mockDataList2, getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public boolean isAppBarCollapsed() {
        final int appBarVisibleHeight = (int) (appBarLayout.getY() + appBarLayout.getHeight());
        final int toolbarHeight = toolbar.getHeight();
        return (appBarVisibleHeight >= toolbarHeight && appBarVisibleHeight <= toolbarHeight + 45);
    }

    @Override
    public void setExpanded(boolean expanded) {
        appBarLayout.setExpanded(expanded, true);
    }

    private void reload() {
        Call<MyPageModelGetData> call = networkService.getMyPageModel(SharedAccessor.getToken(getContext()));
        call.enqueue(new Callback<MyPageModelGetData>() {
            @Override
            public void onResponse(Call<MyPageModelGetData> call, Response<MyPageModelGetData> response) {
                if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                    myPageModelGetData = response.body();
                    Glide.with(getContext()).load(response.body().getModelPhoto()).into(profileImage);
                    modelNameText.setText(response.body().getModelName());
                }
            }

            @Override
            public void onFailure(Call<MyPageModelGetData> call, Throwable t) {
            }
        });
    }
}
