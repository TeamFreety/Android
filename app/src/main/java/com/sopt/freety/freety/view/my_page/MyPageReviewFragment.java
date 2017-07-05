package com.sopt.freety.freety.view.my_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.view.my_page.adapter.MyPageReviewRecyclerAdapter;
import com.sopt.freety.freety.view.my_page.data.network.MyPageDesignerGetData;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageReviewFragment extends Fragment {

    @BindView(R.id.my_page_review_recycler_view)
    ScrollFeedbackRecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private MyPageReviewRecyclerAdapter adapter;
    private ViewPagerEx viewPager;
    private NetworkService networkService;
    private MyPageDesignerGetData myPageDesignerGetData;

    private MyPageDesignerFragment myPageFragment;


    public MyPageReviewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page_review, container, false);
        ButterKnife.bind(this, view);
        viewPager = (ViewPagerEx) container;
        recyclerView.setHasFixedSize(true);
        recyclerView.attachCallbacks(getParentFragment());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch(newState) {
                    case SCROLL_STATE_DRAGGING:
                        viewPager.setPagingEnabled(false);
                        break;
                    case SCROLL_STATE_IDLE:
                        viewPager.setPagingEnabled(true);
                        break;
                }
            }
        });
        layoutManager = new LinearLayoutManager(getContext());

        myPageFragment = (MyPageDesignerFragment) getParentFragment();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));
        try {
            adapter = new MyPageReviewRecyclerAdapter(getContext(), myPageFragment.getMyPageReviewData());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        recyclerView.setAdapter(adapter);

        networkService = AppController.getInstance().getNetworkService();

        return view;
    }


    public void initMyPageReviewFragment() {
        if (networkService == null) {
            networkService = AppController.getInstance().getNetworkService();
        }
        getMyPageReviewData();
    }
    private void getMyPageReviewData() {
        Call<MyPageDesignerGetData> call = networkService.getMyPageDesigner(SharedAccessor.getToken(getContext()));
        call.enqueue(new Callback<MyPageDesignerGetData>() {
            @Override
            public void onResponse(Call<MyPageDesignerGetData> call, Response<MyPageDesignerGetData> response) {
                if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                    try {
                        adapter.updateMyPageReviewData(response.body().getMyPageReviewData());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<MyPageDesignerGetData> call, Throwable t) {
            }
        });
    }
}
