package com.sopt.freety.freety.view.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.custom.ItemOffsetDecoration;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.view.home.adapter.HomePostRecyclerAdapter;
import com.sopt.freety.freety.view.home.data.HomePostData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public HomePostFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewPager = (ViewPagerEx) container;
        View view = inflater.inflate(R.layout.fragment_home_post, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.home_post_offset));
        recyclerView.attachCallbacks(getParentFragment());
        layoutManager = new GridLayoutManager(getContext(), 2);
        final List<HomePostData> mockDataList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            mockDataList.add(HomePostData.getMockData());
        }
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomePostRecyclerAdapter((Activity)getContext(),mockDataList);
        recyclerView.setAdapter(adapter);
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

                if (newState == RecyclerView.SCROLL_STATE_IDLE)
             {
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
            //Log.i("MyPagePostFragment", "setUserVisibleHint: ok");
            if (recyclerView != null) {
                recyclerView.getLayoutManager().scrollToPosition(0);
            }
        }
    }
}
