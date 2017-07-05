package com.sopt.freety.freety.view.my_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.data.PostListResultData;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.custom.ItemOffsetDecoration;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.view.my_page.adapter.MyPagePostRecyclerAdapter;
import com.sopt.freety.freety.view.my_page.adapter.MyPageViewPagerAdapter;
import com.sopt.freety.freety.view.my_page.data.network.MyPageDesignerGetData;

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

public class MyPagePostFragment extends Fragment {


    @BindView(R.id.my_page_pick_list_recycler_view)
    ScrollFeedbackRecyclerView recyclerView;

    private ViewPagerEx viewPager;

    private GridLayoutManager layoutManager;
    private MyPagePostRecyclerAdapter adapter;
    private NetworkService networkService;
    private MyPageDesignerGetData myPageDesignerGetData;

    private MyPageDesignerFragment myPageFragment;


    public MyPagePostFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewPager = (ViewPagerEx) container;
        View view = inflater.inflate(R.layout.fragment_my_page_post, container, false);
        ButterKnife.bind(this, view);


        myPageFragment = (MyPageDesignerFragment) getParentFragment();
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.my_page_post_offset));
        recyclerView.attachCallbacks(getParentFragment());
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        Log.i("test", "onCreateView: " + myPageFragment.getPostDataList().size());
        adapter = new MyPagePostRecyclerAdapter(getContext(), myPageFragment.getPostDataList());
        recyclerView.setAdapter(adapter);
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

        networkService = AppController.getInstance().getNetworkService();

        return view;
    }

    public void initMyPagePostFragment() {
        if (networkService == null) {
            networkService = AppController.getInstance().getNetworkService();
        }
        getMyPagePostData();
    }
    private void getMyPagePostData() {
        Call<MyPageDesignerGetData> call = networkService.getMyPageDesigner(SharedAccessor.getToken(getContext()));
        call.enqueue(new Callback<MyPageDesignerGetData>() {
            @Override
            public void onResponse(Call<MyPageDesignerGetData> call, Response<MyPageDesignerGetData> response) {
                if (response.isSuccessful() && response.body().getMessage().equals("successfully load post list data")) {
                    Log.i("MyPagePostFragment", "onResponse: " + response.body().getMyPagePostDataList());
                    adapter.updatePostDataList(response.body().getMyPagePostDataList());
                }
            }
            @Override
            public void onFailure(Call<MyPageDesignerGetData> call, Throwable t) {
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (recyclerView != null) {
                recyclerView.getLayoutManager().scrollToPosition(0);
            }
        }
    }
}
