package com.sopt.freety.freety.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.custom.ItemOffsetDecoration;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.view.my_page.adapter.MyPagePostRecyclerAdapter;
import com.sopt.freety.freety.view.my_page.data.MyPagePostData;

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


    @BindView(R.id.home_post_recycler_view)
    RecyclerView recyclerView;

    private ViewPagerEx viewPager;

    private GridLayoutManager layoutManager;
    private MyPagePostRecyclerAdapter adapter;

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
        final List<MyPagePostData> mockDataList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            mockDataList.add(MyPagePostData.getMockData());
        }
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyPagePostRecyclerAdapter(mockDataList);
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
