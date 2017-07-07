package com.sopt.freety.freety.view.letter;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.adapter.RecyclerViewOnItemClickListener;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.view.letter.adapter.LetterListAdapter;
import com.sopt.freety.freety.view.letter.data.LetterListResultData;
import com.sopt.freety.freety.view.letter.data.LetterRoomData;
import com.sopt.freety.freety.view.letter.data.RPerson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cmslab on 6/26/17.
 */

public class LetterListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.chat_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.chat_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private static final String TAG = "LetterListFragment";
    private LetterListAdapter letterListAdapter;
    private Realm realm;
    public LetterListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getContext(), recyclerView, new RecyclerViewOnItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int receiverId = letterListAdapter.getItem(position).getOtherId();
                Intent intent = new Intent(getActivity(), LetterActivity.class);
                intent.putExtra("memberId", receiverId);
                intent.putExtra("memberURL", letterListAdapter.getItem(position).getImageURL());
                AppController.getInstance().pushPageStack();
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        }));
        realm = Realm.getDefaultInstance();
        refreshLayout.setOnRefreshListener(this);
        letterListAdapter = new LetterListAdapter(getContext(), Collections.<LetterRoomData>emptyList());
        recyclerView.setAdapter(letterListAdapter);
        return view;
    }

    @Override
    public void onRefresh() {
        update();
    }

    private void update() {

        RealmResults<RPerson> persons = realm.where(RPerson.class).findAll();
        Log.i(TAG, "update: local persons size : " + persons.size());
        final List<LetterRoomData> letterRoomDataList = new ArrayList<>();

        for (RPerson person : persons) {
            letterRoomDataList.add(person.getLetterRoomData());
        }

        Call<LetterListResultData> call = AppController.getInstance().getNetworkService().getLetterListDatas(SharedAccessor.getToken(getContext()));
        call.enqueue(new Callback<LetterListResultData>() {
            @Override
            public void onResponse(Call<LetterListResultData> call, Response<LetterListResultData> response) {
                Log.i(TAG, "onResponse: message : " + response.body().getMessage());
                Log.i(TAG, "onResponse: " + response.raw());
                Log.i(TAG, "onResponse: " + response.body().getRoomList().size());
                if (response.isSuccessful() && response.body().getMessage().equals("success loading message list")) {
                    updateLetterList(letterRoomDataList, response.body().getRoomList());
                    Collections.sort(letterRoomDataList, new Comparator<LetterRoomData>() {
                        @Override
                        public int compare(LetterRoomData o1, LetterRoomData o2) {
                            return o1.getDate().compareTo(o2.getDate());
                        }
                    });
                }
                letterListAdapter.updateData(letterRoomDataList);
                refreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<LetterListResultData> call, Throwable t) {
                Toast.makeText(getActivity(), "전송 실패", Toast.LENGTH_SHORT).show();
                letterListAdapter.updateData(letterRoomDataList);

                refreshLayout.setRefreshing(false);
            }
        });
    }

    private static void updateLetterList(List<LetterRoomData> oldList, List<LetterRoomData> updateList) {
        for (LetterRoomData newData : updateList) {
            boolean isExist = false;
            for (LetterRoomData oldData : oldList) {
                if (newData.getOtherId() == oldData.getOtherId()) {
                    oldData.setNotifCount(newData.getNotifCount());
                    oldData.setDate(newData.getDate());
                    oldData.setLastMsg(newData.getLastMsg());
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                oldList.add(newData);
            }
        }
        Log.i(TAG, "update: size " + oldList.size());
    }
    
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        update();
    }
}
