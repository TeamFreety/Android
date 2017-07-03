package com.sopt.freety.freety.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.adapter.RecyclerViewOnItemClickListener;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.custom.ItemOffsetDecoration;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.view.home.adapter.HomePostRecyclerAdapter;
import com.sopt.freety.freety.data.PostListData;
import com.sopt.freety.freety.data.PostListResultData;
import com.sopt.freety.freety.view.recruit.RecruitActivity;

import java.util.Collections;

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

public class HomePostFragment extends Fragment {

    @BindView(R.id.fabtn_home_to_top)
    FloatingActionButton topFabtn;
    @BindView(R.id.home_post_recycler_view)
    ScrollFeedbackRecyclerView recyclerView;

    private ViewPagerEx viewPager;

    private GridLayoutManager layoutManager;
    private HomePostRecyclerAdapter adapter;
    private int currPostType;
    private NetworkService networkService;

    public HomePostFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewPager = (ViewPagerEx) container;
        View view = inflater.inflate(R.layout.fragment_home_post, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.my_page_post_offset));
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.attachCallbacks(getParentFragment());
        adapter = new HomePostRecyclerAdapter(getContext(), Collections.<PostListData>emptyList());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getActivity(), recyclerView, new RecyclerViewOnItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent postDetailIntent = new Intent(getContext(), RecruitActivity.class);
                postDetailIntent.putExtra("postId", adapter.getPostDataList().get(position).getPostId());
                startActivity(postDetailIntent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
            }
        }));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dy < 0 && topFabtn.isShown()) {
                   topFabtn.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                   topFabtn.show();
                }
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

    topFabtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            recyclerView.smoothScrollToPosition( 0);
        }
    });

        return view;
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

    public void initPostFragment(int type) {
        currPostType = type;
        if (networkService == null) {
            networkService = AppController.getInstance().getNetworkService();
        }
        getPostData();
    }

    private void getPostData() {
        Call<PostListResultData> call = networkService.getHomePostData(currPostType);
        call.enqueue(new Callback<PostListResultData>() {
            @Override
            public void onResponse(Call<PostListResultData> call, Response<PostListResultData> response) {
                if (response.isSuccessful() && response.body().getMessage().equals("successfully load post list data")) {
                    Log.i("HomePostFragment", "onResponse: " + response.body().getPostList().size());
                    adapter.updatePostDataList(response.body().getPostList());
                }
            }
            @Override
            public void onFailure(Call<PostListResultData> call, Throwable t) {
            }
        });
    }
}
