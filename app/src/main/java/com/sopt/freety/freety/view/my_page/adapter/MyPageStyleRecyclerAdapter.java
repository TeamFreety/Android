package com.sopt.freety.freety.view.my_page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.util.EditTextUtils;
import com.sopt.freety.freety.view.my_page.MyPageDesignerPortfolioFragment;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageStyleBodyHolder;
import com.sopt.freety.freety.view.my_page.adapter.holder.MyPageStyleHeaderHolder;
import com.sopt.freety.freety.view.my_page.data.MyPageStyleBodyData;

import java.util.List;


import static android.content.Context.INPUT_METHOD_SERVICE;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageStyleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static int TYPE_HEADER = 0;
    private String careerString;

    private List<MyPageStyleBodyData> myPageStyleBodyDataList;
    private Context context;

    private MyPageDesignerPortfolioFragment parentFragment;
 


    public MyPageStyleRecyclerAdapter(final String careerString, final List<MyPageStyleBodyData> myPageStyleBodyDataList,
                                      final Context context, final MyPageDesignerPortfolioFragment parentFragment) {
        this.careerString = careerString;
        this.myPageStyleBodyDataList = myPageStyleBodyDataList;
        this.context = context;
        this.parentFragment = parentFragment;
    }

    public MyPageStyleRecyclerAdapter(final String careerString,
                                      final List<MyPageStyleBodyData> myPageStyleBodyDataList,
                                      final Context context) {
        this(careerString, myPageStyleBodyDataList, context, null);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView;
        if (viewType == TYPE_HEADER) {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_designer_my_page_portfolio_header, parent, false);
            ButterKnife.bind(this, inflatedView);
            return new MyPageStyleHeaderHolder(inflatedView);
        } else {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_designer_my_page_portfolio_body, parent, false);
            return new MyPageStyleBodyHolder(inflatedView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyPageStyleHeaderHolder) {
            final MyPageStyleHeaderHolder castedHolder = (MyPageStyleHeaderHolder) holder;
            castedHolder.getCareerText().setText(careerString);
            EditTextUtils.setUseableEditText(castedHolder.getCareerText(), false);
            if (parentFragment != null) {
                castedHolder.getCareerEditBtn().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (castedHolder.getCareerText().isFocused()) {
                            parentFragment.onRegisterPortfolioStatus();
                            EditTextUtils.setUseableEditText(castedHolder.getCareerText(), false);
                            Log.i("StyleRecyler", "onClick: is focused");
                        } else {
                            Log.i("StyleRecyler", "onClick: do focus");
                            EditTextUtils.setUseableEditText(castedHolder.getCareerText(), true);
                            castedHolder.getCareerText().requestFocus();
                            castedHolder.getCareerText().setSelection(castedHolder.getCareerText().length());
                            InputMethodManager lManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                            lManager.showSoftInput(castedHolder.getCareerText(), 0);
                        }
                    }
                });
            } else {
                castedHolder.getCareerEditBtn().setVisibility(View.INVISIBLE);
                castedHolder.getCareerText().setHint("");
            }
        } else {
            MyPageStyleBodyHolder castedHolder = (MyPageStyleBodyHolder) holder;
            if (myPageStyleBodyDataList.size() == 0) {
                Glide.with(context).load(R.drawable.placeholder_photo)
                        .override(180, 180).centerCrop().fitCenter()
                        .into(castedHolder.getStyleImage());
            } else {
                Glide.with(context).load(myPageStyleBodyDataList.get(position - 1))
                        .override(256, 256).centerCrop().fitCenter()
                        .into(castedHolder.getStyleImage());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return Math.min(myPageStyleBodyDataList.size() + 2, 2);
    }
}
