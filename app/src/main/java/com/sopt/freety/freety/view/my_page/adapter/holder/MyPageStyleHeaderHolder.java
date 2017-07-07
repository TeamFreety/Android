package com.sopt.freety.freety.view.my_page.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageStyleHeaderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.my_page_style_career_edit_btn)
    ImageButton careerEditBtn;

    @BindView(R.id.edit_my_page_port_career)
    EditText careerText;
    public MyPageStyleHeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ImageButton getCareerEditBtn() {
        return careerEditBtn;
    }

    public EditText getCareerText() {
        return careerText;
    }
}
