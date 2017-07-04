package com.sopt.freety.freety.view.my_page.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageModelHeaderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.front_picture) ImageView frontPicture;
    @BindView(R.id.back_picture) ImageView backPicture;
    @BindView(R.id.side_picture) ImageView sidePicture;

    public MyPageModelHeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public ImageView getFrontPicture() {
        return frontPicture;
    }

    public ImageView getBackPicture() {
        return backPicture;
    }

    public ImageView getSidePicture() {
        return sidePicture;
    }

}
