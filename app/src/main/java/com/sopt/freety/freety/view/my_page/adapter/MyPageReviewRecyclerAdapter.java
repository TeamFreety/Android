package com.sopt.freety.freety.view.my_page.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageReviewBodyHolder;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageReviewHeaderHolder;
import com.sopt.freety.freety.view.my_page.data.MyPageReviewData;
import com.sopt.freety.freety.view.my_page.data.MyPageReviewElemData;

import static android.view.View.GONE;
import static com.sopt.freety.freety.view.my_page.adapter.MyPageStyleRecyclerAdapter.TYPE_HEADER;

/**
 * Created by cmslab on 6/27/17.
 */

public class MyPageReviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MyPageReviewData data;
    public MyPageReviewRecyclerAdapter(final MyPageReviewData data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView;
        if (viewType == TYPE_HEADER) {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_page_review_header, parent, false);
            return new MyPageReviewHeaderHolder(inflatedView);
        } else {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_page_review_body, parent, false);
            return new MyPageReviewBodyHolder(inflatedView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyPageReviewHeaderHolder) {
            float score = data.getTotalScore();
            MyPageReviewHeaderHolder castedHolder = (MyPageReviewHeaderHolder) holder;
            castedHolder.getTotalTextView().setText(String.valueOf(score));
            for (int index = 0; index < castedHolder.getScoreImageList().size(); index++) {
                castedHolder.getScoreImageList().get(index).setImageResource(getStarType(index, score));
            }
        } else {
            final MyPageReviewBodyHolder castedHolder = (MyPageReviewBodyHolder) holder;
            final MyPageReviewElemData currentData;
            castedHolder.getTitleTextView().setText(MyPageReviewElemData.getMockTitle());
            castedHolder.getDateTextView().setText(MyPageReviewElemData.getMockDate());
            castedHolder.getContentTextView().setText(MyPageReviewElemData.getMockContent());
            castedHolder.getWriterTextView().setText(MyPageReviewElemData.getMockWriter());

            int imageResource = MyPageReviewElemData.getMockResource();
            if (imageResource != 0) {
                castedHolder.getImageView().setImageResource(imageResource);
            } else {
                castedHolder.getImageView().setVisibility(GONE);
            }

            int score = MyPageReviewElemData.getMockScore();
            castedHolder.getScoreNumTextView().setText(String.valueOf(score) + ".0");
            for (int index = 0; index < castedHolder.getScoreImageList().size(); index++) {
                castedHolder.getScoreImageList().get(index).setImageResource(getStarType(index, score));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.getElemDataList().size() + 1;
    }

    private static int getStarType(int index, float score) {
        final int numFullStar = (int)score;
        float revision = score - numFullStar;
        if (index <= numFullStar - 1) {
            return R.drawable.star;
        } else if (index == numFullStar) {
            if (revision < 0.25f) {
                return R.drawable.empty_star;
            } else if (revision >= 0.25f && revision <= 0.75f) {
                return R.drawable.half_star;
            } else {
                return R.drawable.star;
            }
        } else {
            return R.drawable.empty_star;
        }
    }


}
