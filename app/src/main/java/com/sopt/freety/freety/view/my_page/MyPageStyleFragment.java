package com.sopt.freety.freety.view.my_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.ItemOffsetDecoration;
import com.sopt.freety.freety.util.ViewPagerEx;
import com.sopt.freety.freety.view.my_page.adapter.MyPageStyleRecyclerAdapter;
import com.sopt.freety.freety.view.my_page.data.MyPageStyleHeaderData;
import com.sopt.freety.freety.view.my_page.data.MyPageStylebodyData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.sopt.freety.freety.view.my_page.adapter.MyPageStyleRecyclerAdapter.TYPE_HEADER;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageStyleFragment extends Fragment {

    @BindView(R.id.my_page_style_recyeler_view)
    RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private MyPageStyleRecyclerAdapter adapter;
    private ViewPagerEx viewPager;

    public MyPageStyleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page_style, container, false);
        ButterKnife.bind(this, view);
        viewPager = (ViewPagerEx) container;
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.my_page_style_offset));
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
        final List<MyPageStylebodyData> mockDatas = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            mockDatas.add(MyPageStylebodyData.getMockData());
        }
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyPageStyleRecyclerAdapter(MyPageStyleHeaderData.tempCareerList, mockDatas, getContext());
        recyclerView.setAdapter(adapter);
        return view;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            //Log.i("MyPageStyleFragment", "setUserVisibleHint: ok");

            if (recyclerView != null) {
                recyclerView.getLayoutManager().scrollToPosition(0);
            }
        }
    }

}