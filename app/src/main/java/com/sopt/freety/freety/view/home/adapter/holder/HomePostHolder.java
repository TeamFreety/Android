package com.sopt.freety.freety.view.home.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/27/17.
 */

public class HomePostHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.home_post_image)
    ImageView postImage;

    @BindView(R.id.home_post_address)
    TextView addressText;

    @BindView(R.id.home_post_style)
    TextView titleText;

    public HomePostHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ImageView getPostImage() {
        return postImage;
    }

    public TextView getAddressText() {
        return addressText;
    }

    public TextView getTitleText() {
        return titleText;
    }
}
