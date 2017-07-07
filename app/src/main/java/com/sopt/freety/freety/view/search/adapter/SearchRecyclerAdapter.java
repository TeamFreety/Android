package com.sopt.freety.freety.view.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.data.PostListData;
import com.sopt.freety.freety.view.search.adapter.holder.SearchHolder;

import java.util.List;

/**
 * Created by USER on 2017-06-27.
 */

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchHolder> {

    private List<PostListData> postDataList;
    private final Context context;

    public SearchRecyclerAdapter(final Context context, final List<PostListData> postListDataList) {
        this.postDataList = postListDataList;
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
        Glide.with(context).load(postDataList.get(position).getImageURL()).override(500, 500).thumbnail(0.1f).into(holder.getSearchImage());
        holder.getSearchAddress().setText(postDataList.get(position).getAddress());
        holder.getSearchStyle().setText(postDataList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return postDataList.size();
    }

    public void updatePostListData(List<PostListData> postDataList) {
        this.postDataList = postDataList;
        notifyDataSetChanged();
    }

    public List<PostListData> getPostDataList() {
        return postDataList;
    }
}
