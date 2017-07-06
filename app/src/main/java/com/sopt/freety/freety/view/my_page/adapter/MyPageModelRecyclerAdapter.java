package com.sopt.freety.freety.view.my_page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.Consts;
import com.sopt.freety.freety.view.my_page.MyPageModelFragment;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageModelHeaderHolder;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPagePickHolder;
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
    private MyPageModelFragment fragment;

    public MyPageModelRecyclerAdapter(final List<MyPageModelHeaderData> myPageModelHeaderDataList,
                                      final List<MyPagePickData> myPagePickDataList,
                                      final Context context,
                                      final MyPageModelFragment fragment) {

        this.context = context;
        this.fragment = fragment;
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

            myPageModelHeaderHolder.getFrontBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {fragment.registerPhoto(Consts.MODEL_PICTURE_1_CODE);}
            });
            myPageModelHeaderHolder.getBackBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {fragment.registerPhoto(Consts.MODEL_PICTURE_2_CODE);}
            });
            myPageModelHeaderHolder.getSideBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {fragment.registerPhoto(Consts.MODEL_PICTURE_3_CODE);}
            });
        } else {
            MyPagePickHolder castedHolder = (MyPagePickHolder) holder;
            castedHolder.getTitleText().setText(myPagePickDataList.get(position - 1).getTitle());
            castedHolder.getAddressText().setText(myPagePickDataList.get(position - 1).getAddress());
            Glide.with(context).load(myPageModelHeaderDataList.get(position -1 ).getImageURL()).override(200, 200).thumbnail(0.3f).into(castedHolder.getPostImage());
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
        notifyDataSetChanged();
    }
}
