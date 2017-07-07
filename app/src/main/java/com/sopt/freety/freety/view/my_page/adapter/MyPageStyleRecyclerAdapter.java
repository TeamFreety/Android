package com.sopt.freety.freety.view.my_page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageStyleBodyHolder;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageStyleHeaderHolder;
import com.sopt.freety.freety.view.my_page.data.MyPagePostData;
import com.sopt.freety.freety.view.my_page.data.MyPageStyleBodyData;
import com.sopt.freety.freety.view.my_page.data.MyPageStyleHeaderData;

import java.util.List;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageStyleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_HEADER_HEIGHT = 60;
    public static int TYPE_HEADER = 0;
    private String careerString;

    private List<MyPageStyleBodyData> myPageStyleBodyDataList;
    private Context context;
 

    public MyPageStyleRecyclerAdapter(final String careerString,
                                      final List<MyPageStyleBodyData> myPageStyleBodyDataList,
                                      final Context context) {
        this.careerString = careerString;
        this.myPageStyleBodyDataList = myPageStyleBodyDataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView;
        if (viewType == TYPE_HEADER) {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_designer_my_page_portfolio_header, parent, false);
            return new MyPageStyleHeaderHolder(inflatedView);
        } else {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_designer_my_page_portfolio_body, parent, false);
            return new MyPageStyleBodyHolder(inflatedView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyPageStyleHeaderHolder) {
            MyPageStyleHeaderHolder castedHolder = (MyPageStyleHeaderHolder) holder;
            castedHolder.getCareerText().setText(careerString);
        } else {
            MyPageStyleBodyHolder castedHolder = (MyPageStyleBodyHolder) holder;
            if (myPageStyleBodyDataList.size() == 0) {
                Glide.with(context).load(R.drawable.placeholder_photo)
                        .override(180, 180).centerCrop().fitCenter()
                        .into(castedHolder.getStyleImage());
            } else {
                Glide.with(context).load(myPageStyleBodyDataList.get(position - 1))
                        .override(256, 256).centerCrop().fitCenter()
                        .into(castedHolder.getStyleImage());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateMyPageStyleHeaderData(String careerString) {
        this.careerString = careerString;
        notifyDataSetChanged();
    }
    public String getMyPageStyleHeaderData() {
        return careerString;
    }

    public void updateMyPageStyleBodyData(List<MyPageStyleBodyData> myPageStyleBodyDataList) {
        this.myPageStyleBodyDataList = myPageStyleBodyDataList;
        notifyDataSetChanged();
    }
    public List<MyPageStyleBodyData> getMyPageStyleBodyData(){return myPageStyleBodyDataList;}

    @Override
    public int getItemCount() {
        return Math.min(myPageStyleBodyDataList.size() + 2, 2);
    }
}
