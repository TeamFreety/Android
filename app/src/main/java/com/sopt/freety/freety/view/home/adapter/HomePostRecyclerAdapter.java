package com.sopt.freety.freety.view.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.home.adapter.holder.HomePostHolder;
import com.sopt.freety.freety.view.home.data.HomePostData;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPagePostHolder;
import com.sopt.freety.freety.view.my_page.data.MyPagePostData;

import java.util.List;


public class HomePostRecyclerAdapter extends RecyclerView.Adapter<HomePostHolder> {

    private List<HomePostData> homePostDataList;

    public HomePostRecyclerAdapter(final List<HomePostData> homePostDataList) {
        this.homePostDataList = homePostDataList;
    }

    @Override
    public HomePostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_post_elem, parent, false);
        return new HomePostHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(HomePostHolder holder, int position) {
        holder.getPostImage().setImageResource(homePostDataList.get(position).getMockSource());
        holder.getAddressText().setText(homePostDataList.get(position).getMockAddress());
        holder.getStyleText().setText(homePostDataList.get(position).getMockStyle());
    }

    @Override
    public int getItemCount() {
        return homePostDataList.size();
    }
}
