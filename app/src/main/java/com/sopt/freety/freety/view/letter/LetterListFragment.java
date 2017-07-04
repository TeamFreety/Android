package com.sopt.freety.freety.view.letter;


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
import com.sopt.freety.freety.view.letter.adapter.LetterListAdapter;
import com.sopt.freety.freety.view.letter.data.LetterRoomData;
import com.sopt.freety.freety.view.letter.data.RealmPerson;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by cmslab on 6/26/17.
 */

public class LetterListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.chat_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.chat_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private LetterListAdapter chatListAdapter;
    private Realm realm;
    public LetterListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this, view);

        realm = Realm.getDefaultInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RealmResults<RealmPerson> persons = realm.where(RealmPerson.class).findAll();
        List<LetterRoomData> letterListDataRoom = new ArrayList<>();
        for (RealmPerson person : persons) {
            try {
                letterListDataRoom.add(person.getLetterListData());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        chatListAdapter = new LetterListAdapter(getContext(), letterListDataRoom);
        recyclerView.setAdapter(chatListAdapter);
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }
}
