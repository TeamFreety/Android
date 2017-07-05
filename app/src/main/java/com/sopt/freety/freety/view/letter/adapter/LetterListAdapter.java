package com.sopt.freety.freety.view.letter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.util.DateParser;
import com.sopt.freety.freety.view.letter.adapter.holder.LetterRoomViewHolder;
import com.sopt.freety.freety.view.letter.data.LetterRoomData;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by cmslab on 6/25/17.
 */

public class LetterListAdapter extends RecyclerView.Adapter<LetterRoomViewHolder> {

    private RecyclerView recyclerView;
    private Context context;
    private List<LetterRoomData> roomDataList;


    public LetterListAdapter(Context context, List<LetterRoomData> letterRoomDatas) {
        this.context = context;
        this.roomDataList = letterRoomDatas;
    }

    public void updateData(List<LetterRoomData> chatListDatas) {
        this.roomDataList = chatListDatas;
        notifyDataSetChanged();
    }

    @Override
    public LetterRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_body, parent, false);
        final LetterRoomViewHolder roomViewHolder = new LetterRoomViewHolder(view);
        return roomViewHolder;
    }

    @Override
    public void onBindViewHolder(LetterRoomViewHolder holder, int position) {
        holder.getName().setText(roomDataList.get(position).getOtherName());
        if (!roomDataList.get(position).getImageURL().equals("")) {
            Glide.with(context).load(roomDataList.get(position).getImageURL())
                    .thumbnail(0.3f)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.getImage());
        } else {
            Glide.with(context).load(R.drawable.placeholder_photo)
                    .thumbnail(0.3f)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.getImage());
        }
        holder.getLastMsg().setText(roomDataList.get(position).getLastMsg());
        holder.getDate().setText(DateParser.toPrettyFormat(roomDataList.get(position).getDate()));
        holder.getPendingImage().setText(String.valueOf(roomDataList.get(position).getNotifCount()));
        if (roomDataList.get(position).getNotifCount() == 0) {
            holder.getPendingImage().setBackgroundResource(0);
            holder.getPendingImage().setText("");
        } else {
            holder.getPendingImage().setBackgroundResource(R.drawable.letter_pending_image);
        }
    }

    @Override
    public int getItemCount() {
        return roomDataList != null ? roomDataList.size() : 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public LetterRoomData getItem(int position) {
        return roomDataList.get(position);
    }

}
