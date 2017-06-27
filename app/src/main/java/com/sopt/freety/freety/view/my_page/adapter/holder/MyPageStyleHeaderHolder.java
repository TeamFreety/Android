package com.sopt.freety.freety.view.my_page.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageStyleHeaderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.my_page_style_career_edit_btn)
    Button careerEditBtn;

    @BindView(R.id.my_page_style_career_list_view)
    ListView careerListView;
    public MyPageStyleHeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        careerListView.setDivider(null);
        careerListView.setEnabled(false);
    }

    public Button getCareerEditBtn() {
        return careerEditBtn;
    }

    public ListView getCareerListView() {
        return careerListView;
    }
}
