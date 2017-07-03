package com.sopt.freety.freety.view.chat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.chat.adapter.ChatListAdapter;
import com.sopt.freety.freety.view.chat.data.ChatListData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/26/17.
 */

public class ChatListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.chat_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.chat_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private ChatListAdapter chatListAdapter;

    public ChatListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this, view);

        final List<ChatListData> mockDataList = new ArrayList<>(Arrays.asList(new ChatListData(), new ChatListData(),
                new ChatListData(), new ChatListData(), new ChatListData()));
        chatListAdapter = new ChatListAdapter(getContext(), mockDataList);

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatListAdapter);
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }
}
