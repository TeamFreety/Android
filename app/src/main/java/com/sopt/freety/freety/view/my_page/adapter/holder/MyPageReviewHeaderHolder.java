package com.sopt.freety.freety.view.my_page.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sopt.freety.freety.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmslab on 6/27/17.
 */

public class MyPageReviewHeaderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.my_page_review_header_total)
    TextView totalTextView;

    @BindView(R.id.my_page_review_header_score1)
    ImageView scoreView1;

    @BindView(R.id.my_page_review_header_score2)
    ImageView scoreView2;

    @BindView(R.id.my_page_review_header_score3)
    ImageView scoreView3;

    @BindView(R.id.my_page_review_header_score4)
    ImageView scoreView4;

    @BindView(R.id.my_page_review_header_score5)
    ImageView scoreView5;

    @BindView(R.id.my_page_review_count_text)
    TextView reviewCountText;

    public MyPageReviewHeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getTotalTextView() {
        return totalTextView;
    }

    public List<ImageView> getScoreImageList() {
        List<ImageView> scoreImageList = new ArrayList<>(5);
        scoreImageList.add(scoreView1);
        scoreImageList.add(scoreView2);
        scoreImageList.add(scoreView3);
        scoreImageList.add(scoreView4);
        scoreImageList.add(scoreView5);
        return scoreImageList;
    }

    public TextView getReviewCountText() {
        return reviewCountText;
    }

}
