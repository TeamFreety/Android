package com.sopt.freety.freety.view.search;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.custom.ItemOffsetDecoration;
import com.sopt.freety.freety.view.search.adapter.SearchRecyclerAdapter;
import com.sopt.freety.freety.view.search.data.SearchBodyData;
import com.sopt.freety.freety.view.wirte.WriteActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sopt.freety.freety.R.id.fabtn_search_to_write;

/**
 * Created by cmslab on 6/26/17.
 */

public class SearchFragment extends Fragment {

    @BindView(R.id.btn_search_detail)
    Button detailSearchBtn;
    @BindView(R.id.btn_search_distance)
    Button distanceSearchBtn;
    @BindView(R.id.btn_search_recent)
    Button recentSearchBtn;
    @BindView(R.id.rv_search)
    RecyclerView mRecyclerView;
    @BindView(fabtn_search_to_write)
    FloatingActionButton mFloatingActionButton;

    private SearchRecyclerAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<SearchBodyData> searchBodyDatas;


    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        detailSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailSearchActivity.class);
                getActivity().startActivity(intent);

            }
        });

       mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                getActivity().startActivity(intent);

            }
        });


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.search_image_offset));

        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        final List<SearchBodyData> mockDataList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            mockDataList.add(SearchBodyData.getMockDatas());
        }

        adapter = new SearchRecyclerAdapter(getContext(), mockDataList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dy < 0 && mFloatingActionButton.isShown()) {
                    mFloatingActionButton.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    mFloatingActionButton.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        return view;


    }


}
