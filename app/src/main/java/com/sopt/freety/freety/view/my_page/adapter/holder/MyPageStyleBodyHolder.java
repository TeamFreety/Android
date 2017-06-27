package com.sopt.freety.freety.view.my_page.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageStyleBodyHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.my_page_style_image)
    ImageView styleImage;

    public MyPageStyleBodyHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ImageView getStyleImage() {
        return styleImage;
    }

}
