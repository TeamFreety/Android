package com.sopt.freety.freety.view.letter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.sopt.freety.freety.view.letter.data.LetterData;

import java.util.List;

/**
 * Created by cmslab on 7/5/17.
 */

public class LetterRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_LEFT = 0;
    private static final int TYPE_RIGHT = 1;

    private List<LetterData> letterDataList;

    public LetterRecyclerAdapter(final List<LetterData> letterDataList) {
        this.letterDataList = letterDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView;
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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
}
