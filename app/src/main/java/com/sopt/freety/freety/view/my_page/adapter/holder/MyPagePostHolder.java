package com.sopt.freety.freety.view.my_page.adapter.holder;

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

public class MyPagePostHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.my_page_post_image)
    ImageView postImage;

    @BindView(R.id.my_page_post_address)
    TextView addressText;

    @BindView(R.id.my_page_post_style)
    TextView styleText;

    public MyPagePostHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ImageView getPostImage() {
        return postImage;
    }

    public TextView getAddressText() {
        return addressText;
    }

    public TextView getStyleText() {
        return styleText;
    }
}
