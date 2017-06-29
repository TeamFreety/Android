package com.sopt.freety.freety.view.chat.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/25/17.
 */

public class ChatListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.chat_list_elem_image) public ImageView vhImage;
    @BindView(R.id.chat_list_elem_name) public TextView vhName;
    @BindView(R.id.chat_list_elem_date) public TextView vhDate;
    @BindView(R.id.chat_list_elem_last) public TextView vhLastMsg;

    public ChatListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ImageView getImage() {
        return vhImage;
    }

    public TextView getName() {
        return vhName;
    }

    public TextView getDate() {
        return vhDate;
    }

    public TextView getLastMsg() {
        return vhLastMsg;
    }
}
