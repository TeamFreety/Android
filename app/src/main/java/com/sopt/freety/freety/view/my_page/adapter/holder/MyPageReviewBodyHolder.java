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

public class MyPageReviewBodyHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.my_page_review_body_title) TextView titleTextView;
    @BindView(R.id.my_page_review_body_date) TextView dateTextView;
    @BindView(R.id.my_page_review_body_image) ImageView imageView;
    @BindView(R.id.my_page_review_body_content) TextView contentTextView;
    @BindView(R.id.my_page_review_body_score1) ImageView scoreView1;
    @BindView(R.id.my_page_review_body_score2) ImageView scoreView2;
    @BindView(R.id.my_page_review_body_score3) ImageView scoreView3;
    @BindView(R.id.my_page_review_body_score4) ImageView scoreView4;
    @BindView(R.id.my_page_review_body_score5) ImageView scoreView5;
    @BindView(R.id.my_page_review_body_num_score) TextView scoreNumTextView;
    @BindView(R.id.my_page_review_body_writer) TextView writerTextView;

    public MyPageReviewBodyHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public TextView getDateTextView() {
        return dateTextView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getContentTextView() {
        return contentTextView;
    }

    public TextView getScoreNumTextView() {
        return scoreNumTextView;
    }

    public TextView getWriterTextView() {
        return writerTextView;
    }

    public List<ImageView> getScoreImageList() {
        final List<ImageView> scoreViewList = new ArrayList<>();
        scoreViewList.add(scoreView1);
        scoreViewList.add(scoreView2);
        scoreViewList.add(scoreView3);
        scoreViewList.add(scoreView4);
        scoreViewList.add(scoreView5);
        return scoreViewList;
    }
}
