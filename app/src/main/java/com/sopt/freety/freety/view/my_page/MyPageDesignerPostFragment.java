package com.sopt.freety.freety.view.my_page;

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
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.custom.ItemOffsetDecoration;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.view.my_page.adapter.MyPagePostRecyclerAdapter;
import com.sopt.freety.freety.view.my_page.data.network.MyPageDesignerGetData;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageDesignerPostFragment extends Fragment {


    @BindView(R.id.my_page_pick_list_recycler_view)
    ScrollFeedbackRecyclerView recyclerView;

    @BindView(R.id.fabtn_designer_post_to_top)
    FloatingActionButton topFabtn;

    private ViewPagerEx viewPager;
    private boolean isMine = true;

    private GridLayoutManager layoutManager;
    private MyPagePostRecyclerAdapter adapter;
    private NetworkService networkService;
    private MyPageDesignerGetData myPageDesignerGetData;
    public MyPageDesignerPostFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewPager = (ViewPagerEx) container;
        View view = inflater.inflate(R.layout.fragment_designer_my_page_post, container, false);
        ButterKnife.bind(this, view);
        networkService = AppController.getInstance().getNetworkService();
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.my_page_post_offset));
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dy < 0 && topFabtn.isShown()) {
                    topFabtn.hide();
                }

            }
        });

        topFabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        if (isMine) {
            initByFragment();
        } else {
            initByActivity();
        }

        return view;
    }

    public void initByFragment() {
        MyPageDesignerFragment myPageFragment = (MyPageDesignerFragment) getParentFragment();
        recyclerView.attachCallbacks(getParentFragment());
        adapter = new MyPagePostRecyclerAdapter(getContext(), myPageFragment.getPostDataList());
        recyclerView.setAdapter(adapter);
    }

    public void initByActivity() {
        ModelToDesignerMypageActivity parent = (ModelToDesignerMypageActivity) getActivity();
        recyclerView.attachCallbacks(getActivity());
        adapter = new MyPagePostRecyclerAdapter(getContext(), parent.getPostDataList());
        recyclerView.setAdapter(adapter);
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

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }
}
