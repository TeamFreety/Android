package com.sopt.freety.freety.view.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.chat.adapter.holder.ChatListViewHolder;
import com.sopt.freety.freety.view.chat.data.ChatListData;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static java.security.AccessController.getContext;

/**
 * Created by cmslab on 6/25/17.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListViewHolder> {

    private RecyclerView recyclerView;
    private Context context;
    private List<ChatListData> chatListDatas;


    public ChatListAdapter(Context context, List<ChatListData> chatListDatas) {
        this.context = context;
        this.chatListDatas = chatListDatas;
    }

    public void setAdapter(List<ChatListData> chatListDatas) {
        this.chatListDatas = chatListDatas;
        notifyDataSetChanged();
    }

    @Override
    public ChatListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_body, parent, false);
        final ChatListViewHolder chatListViewHolder = new ChatListViewHolder(view);
        return chatListViewHolder;
    }

    @Override
    public void onBindViewHolder(ChatListViewHolder holder, int position) {
        holder.getName().setText(chatListDatas.get(position).getOtherId());
        Glide.with(context).load(chatListDatas.get(position).getImgSource())
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
