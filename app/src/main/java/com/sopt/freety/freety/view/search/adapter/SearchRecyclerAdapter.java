package com.sopt.freety.freety.view.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.search.adapter.holder.SearchHolder;
import com.sopt.freety.freety.view.search.data.SearchBodyData;

import java.util.List;

/**
 * Created by USER on 2017-06-27.
 */

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchHolder> {

    private List<SearchBodyData> searchBodyDatas;
    private final Context context;

    public SearchRecyclerAdapter(final Context context, final List<SearchBodyData> searchBodyDatas) {
        this.searchBodyDatas = searchBodyDatas;
        this.context = context;
    }


    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_search_body, parent, false);
        return new SearchHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        Glide.with(context).load(searchBodyDatas.get(position).getMockSources()).override(164, 187).centerCrop().thumbnail(0.001f).into(holder.getSearchImage());
        holder.getSearchAddress().setText(searchBodyDatas.get(position).getMockAddresss());
        holder.getSearchStyle().setText(searchBodyDatas.get(position).getMockStyles());

    }

    @Override
    public int getItemCount() {
        return searchBodyDatas.size();
    }
}
