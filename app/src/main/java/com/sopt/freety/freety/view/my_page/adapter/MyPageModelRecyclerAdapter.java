package com.sopt.freety.freety.view.my_page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageModelBodyHolder;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageModelHeaderHolder;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPagePickHolder;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageStyleBodyHolder;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageStyleHeaderHolder;
import com.sopt.freety.freety.view.my_page.data.MyPageModelHeaderData;
import com.sopt.freety.freety.view.my_page.data.MyPagePickData;

import java.util.List;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageModelRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_HEADER_HEIGHT = 60;
    public static int TYPE_HEADER = 0;
    private List<MyPageModelHeaderData> myPageModelHeaderDataList;  //Header
    private List<MyPagePickData> myPagePickDataList;    //recyclerView
    private Context context;

    public MyPageModelRecyclerAdapter(final List<MyPageModelHeaderData> myPageModelHeaderDataList,
                                      final List<MyPagePickData> myPagePickDataList,
                                      final Context context) {
        this.context = context;
        this.myPagePickDataList = myPagePickDataList;
        this.myPageModelHeaderDataList = myPageModelHeaderDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView;
        if (viewType == TYPE_HEADER) {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_page_model_header, parent, false);
            return new MyPageModelHeaderHolder(inflatedView);
        } else {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_page_pick_elem, parent, false);
            return new MyPagePickHolder(inflatedView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyPageModelHeaderHolder) {
            MyPageModelHeaderHolder myPageModelHeaderHolder = (MyPageModelHeaderHolder) holder;
            Glide.with(context).load(myPageModelHeaderDataList.get(0).getImageURL())
                    .override(200, 200).thumbnail(0.2f).into(myPageModelHeaderHolder.getFrontPicture());
            Glide.with(context).load(myPageModelHeaderDataList.get(1).getImageURL())
                    .override(200, 200).thumbnail(0.2f).into(myPageModelHeaderHolder.getBackPicture());
            Glide.with(context).load(myPageModelHeaderDataList.get(2).getImageURL())
                    .override(200, 200).thumbnail(0.2f).into(myPageModelHeaderHolder.getSidePicture());
        } else {

            MyPagePickHolder castedHolder = (MyPagePickHolder) holder;
            Glide.with(context).load(myPagePickDataList.get(position - 1).getMockSource()).override(164,187).centerCrop().thumbnail(0.001f).into(castedHolder.getPostImage());
            castedHolder.getAddressText().setText(myPagePickDataList.get(position - 1).getMockAddress());
            castedHolder.getStyleText().setText(myPagePickDataList.get(position - 1).getMockStyle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return myPagePickDataList.size() + 1;
    }

    public void updatePicture(int position, String imageURL) {
        myPageModelHeaderDataList.get(position).setImageURL(imageURL);
        // network
    }
}
