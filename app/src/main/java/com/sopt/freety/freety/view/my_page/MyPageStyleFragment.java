package com.sopt.freety.freety.view.my_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.custom.ItemOffsetDecoration;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.view.my_page.adapter.MyPageStyleRecyclerAdapter;
import com.sopt.freety.freety.view.my_page.data.MyPageStyleHeaderData;
import com.sopt.freety.freety.view.my_page.data.MyPageStyleBodyData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageDesignerGetData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.sopt.freety.freety.view.my_page.adapter.MyPageStyleRecyclerAdapter.TYPE_HEADER;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageStyleFragment extends Fragment {

    @BindView(R.id.my_page_style_recyeler_view)
    ScrollFeedbackRecyclerView recyclerView;

    private GridLayoutManager layoutManager;
    private MyPageStyleRecyclerAdapter adapter;
    private ViewPagerEx viewPager;
    private MyPageDesignerFragment myPageFragment;
    private NetworkService networkService;

    private MyPageDesignerGetData myPageDesignerGetData;

    public MyPageStyleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page_style, container, false);
        ButterKnife.bind(this, view);
        myPageFragment = (MyPageDesignerFragment) getParentFragment();
        viewPager = (ViewPagerEx) container;
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.my_page_style_offset));
        recyclerView.attachCallbacks(getParentFragment());
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
        adapter = new MyPageStyleRecyclerAdapter(getHeaderData(), getBodyData(), getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void initMyPageStyleFragment() {
        if (networkService == null) {
            networkService = AppController.getInstance().getNetworkService();
        }
        getMyPageStyleData();
        //getMyPageStyleBodyData();
    }
    private void getMyPageStyleData() {
        Call<MyPageDesignerGetData> call = networkService.getMyPageDesigner(SharedAccessor.getToken(getContext()));
        call.enqueue(new Callback<MyPageDesignerGetData>() {
            @Override
            public void onResponse(Call<MyPageDesignerGetData> call, Response<MyPageDesignerGetData> response) {
                if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                    //TO DO : Style Header
                    adapter.updateMyPageStyleHeaderData(response.body().getDesignerCareerText());

                    //adapter.updateMyPageStyleHeaderData(response.body().getMyPageStyleHeaderData());
                    adapter.updateMyPageStyleBodyData(response.body().getMyStyleBodyDataList());
                }
            }
            @Override
            public void onFailure(Call<MyPageDesignerGetData> call, Throwable t) {
            }
        });
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

    private List<MyPageStyleBodyData> getBodyData() {
        return myPageFragment.getStyleBodyDataList();
    }

    private String getHeaderData() {
        return myPageFragment.getStyleHeaderData();
    }
}
