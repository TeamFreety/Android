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
import com.sopt.freety.freety.util.custom.ItemOffsetDecoration;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.view.my_page.adapter.MyPageStyleRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.sopt.freety.freety.view.my_page.adapter.MyPageStyleRecyclerAdapter.TYPE_HEADER;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageDesignerPortfolioFragment extends Fragment {

    @BindView(R.id.my_page_style_recyeler_view)
    ScrollFeedbackRecyclerView recyclerView;

    private GridLayoutManager layoutManager;
    private MyPageStyleRecyclerAdapter adapter;
    private ViewPagerEx viewPager;
    private boolean isMine = true;

    @BindView(R.id.fabtn_designer_portfolio_to_top)
    FloatingActionButton topFabtn;

    public MyPageDesignerPortfolioFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_designer_my_page_portfolio, container, false);
        ButterKnife.bind(this, view);
        viewPager = (ViewPagerEx) container;
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.my_page_style_offset));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    topFabtn.show();
                    super.onScrollStateChanged(recyclerView, newState);
                    switch (newState) {
                        case SCROLL_STATE_DRAGGING:
                            viewPager.setPagingEnabled(false);
                            break;
                        case SCROLL_STATE_IDLE:
                            viewPager.setPagingEnabled(true);
                            break;
                    }
                }
            }
                @Override
                public void onScrolled (RecyclerView recyclerView,int dx, int dy){
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0 || dy < 0 && topFabtn.isShown()) {
                        topFabtn.hide();
                    }
                }

        });

        layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if (position == TYPE_HEADER) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });

        recyclerView.setLayoutManager(layoutManager);

        if (isMine) {
            initByFragment();
        } else {
            initByActivity();
        }
        return view;
    }

    public void initByFragment() {
        MyPageDesignerFragment myPageFragment = (MyPageDesignerFragment) getParentFragment();
        adapter = new MyPageStyleRecyclerAdapter(myPageFragment.getMyPageStyleHeaderData().getCareerString(),
                myPageFragment.getStyleBodyDataList(), getContext());
        recyclerView.attachCallbacks(myPageFragment);
        recyclerView.setAdapter(adapter);
    }

    public void initByActivity() {
        ModelToDesignerMypageActivity parent = (ModelToDesignerMypageActivity) getActivity();
        adapter = new MyPageStyleRecyclerAdapter(parent.getStyleHeaderData().getCareerString(), parent.getStyleBodyDataList(), getContext());
        recyclerView.attachCallbacks(parent);
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
