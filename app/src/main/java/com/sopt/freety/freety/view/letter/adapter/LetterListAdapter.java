package com.sopt.freety.freety.view.letter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.letter.adapter.holder.LetterListViewHolder;
import com.sopt.freety.freety.view.letter.data.LetterListData;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by cmslab on 6/25/17.
 */

public class LetterListAdapter extends RecyclerView.Adapter<LetterListViewHolder> {

    private RecyclerView recyclerView;
    private Context context;
    private List<LetterListData> chatListDatas;


    public LetterListAdapter(Context context, List<LetterListData> chatListDatas) {
        this.context = context;
        this.chatListDatas = chatListDatas;
    }

    public void setAdapter(List<LetterListData> chatListDatas) {
        this.chatListDatas = chatListDatas;
        notifyDataSetChanged();
    }

    @Override
    public LetterListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_body, parent, false);
        final LetterListViewHolder chatListViewHolder = new LetterListViewHolder(view);
        return chatListViewHolder;
    }

    @Override
    public void onBindViewHolder(LetterListViewHolder holder, int position) {
        holder.getName().setText(chatListDatas.get(position).getOtherId());
        Glide.with(context).load(chatListDatas.get(position).getImageURL())
                .thumbnail(0.3f)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(holder.getImage());
        holder.getLastMsg().setText(chatListDatas.get(position).getLastMsg());
        holder.getDate().setText(chatListDatas.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return chatListDatas != null ? chatListDatas.size() : 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public void removeDataElem(View view) {
        int itemPosition = recyclerView.getChildLayoutPosition(view);
        chatListDatas.remove(itemPosition);
        notifyDataSetChanged();
    }
}
