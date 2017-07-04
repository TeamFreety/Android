package com.sopt.freety.freety.view.letter.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 7/5/17.
 */

public class LetterViewRightHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.letter_right_name)
    TextView nameText;

    @BindView(R.id.letter_right_image)
    ImageView image;

    @BindView(R.id.letter_right_date)
    TextView dateText;

    @BindView(R.id.letter_right_content)
    TextView contentText;

    public LetterViewRightHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getNameText() {
        return nameText;
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getDateText() {
        return dateText;
    }

    public TextView getContentText() {
        return contentText;
    }
}
