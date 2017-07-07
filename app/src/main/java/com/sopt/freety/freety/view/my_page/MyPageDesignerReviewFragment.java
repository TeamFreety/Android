package com.sopt.freety.freety.view.my_page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.view.login.SelectMemberTypeActivity;
import com.sopt.freety.freety.view.my_page.adapter.MyPageReviewRecyclerAdapter;
import com.sopt.freety.freety.view.my_page.data.MyPageReviewData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageDesignerGetData;

import java.text.ParseException;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageDesignerReviewFragment extends Fragment {

    @BindView(R.id.my_page_review_recycler_view)
    ScrollFeedbackRecyclerView recyclerView;

    @BindView(R.id.fabtn_review_to_review)
    FloatingActionButton reviewBtn;

    private LinearLayoutManager layoutManager;
    private MyPageReviewRecyclerAdapter adapter;
    private ViewPagerEx viewPager;
    private boolean isMine;



    public MyPageDesignerReviewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_designer_my_page_review, container, false);
        ButterKnife.bind(this, view);
        viewPager = (ViewPagerEx) container;
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch(newState) {
                    case SCROLL_STATE_DRAGGING:
                        viewPager.setPagingEnabled(false);
                        break;
                    case SCROLL_STATE_IDLE:
                        viewPager.setPagingEnabled(true);
                        break;
                }
            }
        });
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));

        if (isMine) {
            initByFragment();
        } else {
            initByActivity();
        }

        return view;
    }

    // 자기마이페이지
    public void initByFragment() {
        if (SharedAccessor.isDesigner(getContext())) {
            reviewBtn.setEnabled(false);
            reviewBtn.setVisibility(View.INVISIBLE);
        }
        MyPageDesignerFragment myPageFragment = (MyPageDesignerFragment) getParentFragment();
        adapter = new MyPageReviewRecyclerAdapter(getContext(), myPageFragment.getMyPageReviewData());
        recyclerView.attachCallbacks(getParentFragment());
        recyclerView.setAdapter(adapter);
    }

    // 남이 접근한 마이페이지
    public void initByActivity() {
        if (SharedAccessor.isDesigner(getContext())) {
            reviewBtn.setEnabled(false);
            reviewBtn.setVisibility(View.INVISIBLE);
        }
        ModelToDesignerMypageActivity parent = (ModelToDesignerMypageActivity) getActivity();
        adapter = new MyPageReviewRecyclerAdapter(getContext(), parent.getMyPageReviewData());
        recyclerView.attachCallbacks(getActivity());
        recyclerView.setAdapter(adapter);
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    @OnClick(R.id.fabtn_review_to_review)
    public void onClickReviewBtn(){
       /* Intent intent = new Intent(getActivity(), MyPageReviewPopupActivity.class);
        intent.putExtra("memberId", ((ModelToDesignerMypageActivity) getActivity()).getMemberId());
        Toast.makeText(getApplicationContext(),"memberid : "+((ModelToDesignerMypageActivity) getActivity()).getMemberId(),Toast.LENGTH_SHORT).show();
        */
       Intent intent = new Intent(getApplicationContext(),MyPageReviewPopupActivity.class);
        //intent.putExtra("memberId",
        getActivity().startActivity(intent);
    }
}
