package com.sopt.freety.freety.view.my_page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageStyleBodyHolder;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageStyleHeaderHolder;
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

    public MyPageStyleRecyclerAdapter(final MyPageStyleHeaderData headerData,
                                      final List<MyPageStyleBodyData> myPageStyleBodyDataList,
                                      final Context context) {
        this.careerString = headerData.getCareerString();
        this.myPageStyleBodyDataList = myPageStyleBodyDataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView;
        if (viewType == TYPE_HEADER) {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_page_style_header, parent, false);
            return new MyPageStyleHeaderHolder(inflatedView);
        } else {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_page_style_body, parent, false);
            return new MyPageStyleBodyHolder(inflatedView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyPageStyleHeaderHolder) {
            MyPageStyleHeaderHolder castedHolder = (MyPageStyleHeaderHolder) holder;
            castedHolder.getCareerText().setText(careerString);
            //TODO: implement pop up to edit text
        } else {
            MyPageStyleBodyHolder castedHolder = (MyPageStyleBodyHolder) holder;
            Glide.with(context).load(R.drawable.chat_list_elem).override(200, 200).centerCrop().fitCenter().into(castedHolder.getStyleImage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return myPageStyleBodyDataList.size() + 1;
    }
}
