package com.sopt.freety.freety.view.my_page.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPagePostHolder;
import com.sopt.freety.freety.view.my_page.data.MyPagePostData;

import java.util.List;

/**
 * Created by cmslab on 6/27/17.
 */

public class MyPagePostRecyclerAdapter extends RecyclerView.Adapter<MyPagePostHolder> {

    private List<MyPagePostData> myPagePostDataList;

    public MyPagePostRecyclerAdapter(final List<MyPagePostData> myPagePostDataList) {
        this.myPagePostDataList = myPagePostDataList;
    }

    @Override
    public MyPagePostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_my_page_post_elem, parent, false);
        return new MyPagePostHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(MyPagePostHolder holder, int position) {
        holder.getPostImage().setImageResource(myPagePostDataList.get(position).getMockSource());
        holder.getAddressText().setText(myPagePostDataList.get(position).getMockAddress());
        holder.getStyleText().setText(myPagePostDataList.get(position).getMockStyle());
    }

    @Override
    public int getItemCount() {
        return myPagePostDataList.size();
    }
}
