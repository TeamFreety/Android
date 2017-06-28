package com.sopt.freety.freety.view.search.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by USER on 2017-06-27.
 */

public class SearchHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.search_image)
    ImageView searchImage;

    @BindView(R.id.search_address)
    TextView searchAddress;

    @BindView(R.id.search_style)
    TextView searchStyle;



    public SearchHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ImageView getSearchImage() {
        return searchImage;
    }

    public TextView getSearchAddress() {
        return searchAddress;
    }

    public TextView getSearchStyle() {
        return searchStyle;
    }
}
