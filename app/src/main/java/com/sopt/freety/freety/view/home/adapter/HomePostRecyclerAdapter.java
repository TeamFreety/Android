package com.sopt.freety.freety.view.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.home.adapter.holder.HomePostHolder;
import com.sopt.freety.freety.data.PostListData;

import java.util.List;


public class HomePostRecyclerAdapter extends RecyclerView.Adapter<HomePostHolder> {

    private List<PostListData> postDataList;
    private final Context context;

    public HomePostRecyclerAdapter(final Context context, final List<PostListData> postDataList) {
        this.postDataList = postDataList;
        this.context = context;
    }

    @Override
    public HomePostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_post_elem, parent, false);
        return new HomePostHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(HomePostHolder holder, int position) {
        Glide.with(context).load(postDataList.get(position).getImageURL()).override(164,187).centerCrop().thumbnail(0.001f).into(holder.getPostImage());
        holder.getAddressText().setText(postDataList.get(position).getAddress());
        holder.getTitleText().setText(postDataList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return postDataList.size();
    }

    public void updatePostDataList(List<PostListData> postDataList) {
        this.postDataList = postDataList;
        notifyDataSetChanged();
    }

    public List<PostListData> getPostDataList() {
        return postDataList;
    }
}
