package com.sopt.freety.freety.view.letter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.util.DateParser;
import com.sopt.freety.freety.view.letter.adapter.holder.LetterViewLeftHolder;
import com.sopt.freety.freety.view.letter.adapter.holder.LetterViewRightHolder;
import com.sopt.freety.freety.view.letter.data.LetterData;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by cmslab on 7/5/17.
 */

public class LetterRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_LEFT = 0;
    private static final int TYPE_RIGHT = 1;

    private Context context;
    private List<LetterData> letterDataList;

    public LetterRecyclerAdapter(Context context, final List<LetterData> letterDataList) {
        this.context = context;
        this.letterDataList = letterDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView;
        if (viewType == TYPE_LEFT) {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_letter_left, parent, false);
            return new LetterViewLeftHolder(inflatedView);
        } else {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_letter_right, parent, false);
            return new LetterViewRightHolder(inflatedView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LetterViewLeftHolder) {
            LetterViewLeftHolder castedHolder = (LetterViewLeftHolder) holder;
            if (letterDataList.get(position).getImageURL() != null) {
                Glide.with(context).load(letterDataList.get(position).getImageURL()).thumbnail(0.3f)
                        .bitmapTransform(new CropCircleTransformation(context)).into(castedHolder.getImage());
            } else {
                Glide.with(context).load(R.drawable.placeholder_photo).thumbnail(0.3f)
                        .bitmapTransform(new CropCircleTransformation(context)).into(castedHolder.getImage());
            }
            castedHolder.getNameText().setText(letterDataList.get(position).getName());
            castedHolder.getDateText().setText(DateParser.toPrettyFormat(letterDataList.get(position).getDate()));
            castedHolder.getContentText().setText(letterDataList.get(position).getContent());
        } else {
            LetterViewRightHolder castedHolder = (LetterViewRightHolder) holder;
            if (letterDataList.get(position).getImageURL() != null) {
                Glide.with(context).load(letterDataList.get(position).getImageURL()).thumbnail(0.3f)
                        .bitmapTransform(new CropCircleTransformation(context)).into(castedHolder.getImage());
            } else {
                Glide.with(context).load(R.drawable.placeholder_photo).thumbnail(0.3f)
                        .bitmapTransform(new CropCircleTransformation(context)).into(castedHolder.getImage());
            }
            castedHolder.getNameText().setText(letterDataList.get(position).getName());
            castedHolder.getDateText().setText(DateParser.toPrettyFormat(letterDataList.get(position).getDate()));
            castedHolder.getContentText().setText(letterDataList.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return letterDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (letterDataList.get(position).isMine()) {
            return TYPE_RIGHT;
        } else {
            return TYPE_LEFT;
        }
    }

    public void updateLetterDataList(List<LetterData> dataList) {
        this.letterDataList = dataList;
        notifyDataSetChanged();
    }
}
