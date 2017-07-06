package com.sopt.freety.freety.view.my_page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageReviewBodyHolder;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageReviewHeaderHolder;
import com.sopt.freety.freety.view.my_page.data.MyPagePostData;
import com.sopt.freety.freety.view.my_page.data.MyPageReviewData;
import com.sopt.freety.freety.view.my_page.data.MyPageReviewElemData;

import java.util.List;

import static android.view.View.GONE;
import static com.sopt.freety.freety.view.my_page.adapter.MyPageStyleRecyclerAdapter.TYPE_HEADER;

/**
 * Created by cmslab on 6/27/17.
 */

public class MyPageReviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private MyPageReviewData data;
    public MyPageReviewRecyclerAdapter(final Context context, final MyPageReviewData data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView;
        if (viewType == TYPE_HEADER) {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_designer_my_page_review_header, parent, false);
            return new MyPageReviewHeaderHolder(inflatedView);
        } else {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_designer_my_page_review_body, parent, false);
            return new MyPageReviewBodyHolder(inflatedView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyPageReviewHeaderHolder) {
            float score = data.getTotalScore();
            MyPageReviewHeaderHolder castedHolder = (MyPageReviewHeaderHolder) holder;
            score = Math.round(score * 10) / 10f;
            castedHolder.getTotalTextView().setText(String.valueOf(score));
            for (int index = 0; index < castedHolder.getScoreImageList().size(); index++) {
                castedHolder.getScoreImageList().get(index).setImageResource(getStarType(index, score));
            }
        } else {
            final MyPageReviewBodyHolder castedHolder = (MyPageReviewBodyHolder) holder;
            final MyPageReviewElemData currentData = data.getElemDataList().get(position - 1);
            castedHolder.getTitleTextView().setText(currentData.getTitle());
            castedHolder.getDateTextView().setText(currentData.getDate());
            castedHolder.getContentTextView().setText(currentData.getContent());
            castedHolder.getWriterTextView().setText(currentData.getWriter());
            castedHolder.getScoreNumTextView().setText(String.valueOf(currentData.getScore()));
            for (int index = 0; index < castedHolder.getScoreImageList().size(); index++) {
                castedHolder.getScoreImageList().get(index).setImageResource(getStarType(index, currentData.getScore()));
            }

            if (currentData.getImageURL() != null) {
                Glide.with(context).load(currentData.getImageURL()).
                        override(200, 200).thumbnail(0.3f).into(castedHolder.getImageView());
            } else {
                castedHolder.getImageView().setVisibility(GONE);
            }
        }
    }

    public void updateMyPageReviewData(MyPageReviewData data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public MyPageReviewData getData(){ return data;}

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
