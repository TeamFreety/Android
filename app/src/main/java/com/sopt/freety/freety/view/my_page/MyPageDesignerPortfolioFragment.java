package com.sopt.freety.freety.view.my_page;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;



import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.data.OnlyMsgResultData;

import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.custom.ItemOffsetDecoration;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.util.util.EditTextUtils;
import com.sopt.freety.freety.view.my_page.adapter.MyPageStyleRecyclerAdapter;
import com.sopt.freety.freety.view.my_page.data.network.MyPageStatusUpdateRequestData;
import com.sopt.freety.freety.view.property.ScreenClickable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.sopt.freety.freety.view.my_page.adapter.MyPageStyleRecyclerAdapter.TYPE_HEADER;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageDesignerPortfolioFragment extends Fragment implements ScreenClickable {

    @BindView(R.id.my_page_style_recyeler_view)
    ScrollFeedbackRecyclerView recyclerView;

    private GridLayoutManager layoutManager;
    private MyPageStyleRecyclerAdapter adapter;
    private ViewPagerEx viewPager;
    private boolean isMine = true;

    @BindView(R.id.fabtn_designer_portfolio_to_top)
    FloatingActionButton topFabtn;

    @BindView(R.id.edit_my_page_port_career)
    EditText careerEdit;


    @OnClick(R.id.my_page_style_career_edit_btn)
    public void onEditBtn() {
        EditTextUtils.setUseableEditText(careerEdit, true);
        careerEdit.requestFocus();
        careerEdit.setSelection(careerEdit.length());
        InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
        lManager.showSoftInput(careerEdit, 0);
    }

    public MyPageDesignerPortfolioFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_designer_my_page_portfolio, container, false);
        ButterKnife.bind(this, view);
        viewPager = (ViewPagerEx) container;
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.my_page_style_offset));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    topFabtn.show();
                    super.onScrollStateChanged(recyclerView, newState);
                    switch (newState) {
                        case SCROLL_STATE_DRAGGING:
                            viewPager.setPagingEnabled(false);
                            break;
                        case SCROLL_STATE_IDLE:
                            viewPager.setPagingEnabled(true);
                            break;
                    }
                }
            }
            @Override
            public void onScrolled (RecyclerView recyclerView,int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dy < 0 && topFabtn.isShown()) {
                    topFabtn.hide();
                }
            }
        });

        topFabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });

        layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if (position == TYPE_HEADER) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });

        recyclerView.setLayoutManager(layoutManager);

        if (isMine) {
            initByFragment();
        } else {
            initByActivity();
        }

        if (!isMine) {
            careerEdit.setVisibility(View.INVISIBLE);
        }

        careerEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                   onRegisterPortfolioStatus();
                }
                return true;
            }
        });
        return view;
    }

    //자기 마이페이지
    public void initByFragment() {
        MyPageDesignerFragment myPageFragment = (MyPageDesignerFragment) getParentFragment();
        adapter = new MyPageStyleRecyclerAdapter(myPageFragment.getMyPageStyleHeaderData().getCareerString(),
                myPageFragment.getStyleBodyDataList(), getContext());
        recyclerView.attachCallbacks(myPageFragment);
        recyclerView.setAdapter(adapter);
    }

    public void initByActivity() {
        ModelToDesignerMypageActivity parent = (ModelToDesignerMypageActivity) getActivity();
        adapter = new MyPageStyleRecyclerAdapter(parent.getStyleHeaderData().getCareerString(), parent.getStyleBodyDataList(), getContext());
        recyclerView.attachCallbacks(parent);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (recyclerView != null) {
                recyclerView.getLayoutManager().scrollToPosition(0);
            }
        }
    }

    public void onRegisterPortfolioStatus() {
        if (careerEdit.isFocused()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(careerEdit.getWindowToken(), 0);
            EditTextUtils.setUseableEditText(careerEdit, false);
            Call<OnlyMsgResultData> call = AppController.getInstance().getNetworkService().getOkMsg(SharedAccessor.getToken(getContext()),
                    new MyPageStatusUpdateRequestData(careerEdit.getText().toString()));
            call.enqueue(new Callback<OnlyMsgResultData>() {
                @Override
                public void onResponse(Call<OnlyMsgResultData> call, Response<OnlyMsgResultData> response) {
                    if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                        Toast.makeText(getContext(), "상태 메세지가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "네트워크 연결이 좋지 않아 적용이 되지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<OnlyMsgResultData> call, Throwable t) {
                    Toast.makeText(getContext(), "on failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }


    @Override
    public void onScreenClick(View v) {
        onRegisterPortfolioStatus();
    }

}
