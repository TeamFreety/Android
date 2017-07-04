package com.sopt.freety.freety.view.search;


import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.data.PostListData;
import com.sopt.freety.freety.data.PostListResultData;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.custom.ItemOffsetDecoration;
import com.sopt.freety.freety.view.search.adapter.SearchRecyclerAdapter;
import com.sopt.freety.freety.view.wirte.WriteActivity;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sopt.freety.freety.R.id.fabtn_search_to_write;

/**
 * Created by cmslab on 6/26/17.
 */

public class SearchFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    public static final int DETAIL_SEARCH_CODE = 7777;
    @BindView(R.id.rv_search)
    RecyclerView mRecyclerView;

    @BindView(fabtn_search_to_write)
    FloatingActionButton mFloatingActionButton;

    @OnClick(R.id.btn_search_nearest)
    public void onDistanceBtn() {
        new TedPermission(getContext())
                .setPermissionListener(getPermissionListener())
                .setRationaleConfirmText("확인")
                .setRationaleMessage("\"Freety\"의 다음 작업을 허용하시겠습니까? 이 기기의 위치에 액세스하기")
                .setDeniedMessage("거부하시면 볼수 없는데...")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }

    @BindView(R.id.btn_search_nearest)
    Button nearestBtn;

    @OnClick(R.id.btn_search_latest)
    public void onLatestBtn() {
        updateLatestSortVersion();
    }

    @OnClick({R.id.search_filter_btn1, R.id.search_filter_btn2})
    public void onFilterBtn() {
        Intent intent = new Intent(getActivity(), FilteredSearchActivity.class);
        getActivity().startActivityForResult(intent, DETAIL_SEARCH_CODE);
        getActivity().overridePendingTransition(R.anim.screen_slide_up, R.anim.screen_slide_stop);
        AppController.getInstance().pushPageStack();
    }

    @OnClick(R.id.search_back_btn)
    public void onBackBtn() {
        getActivity().onBackPressed();
    }

    private SearchRecyclerAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private NetworkService networkService;
    private GoogleApiClient googleApiClient;

    public SearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                getActivity().startActivity(intent);
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.search_image_offset));

        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        adapter = new SearchRecyclerAdapter(getContext(), Collections.<PostListData>emptyList());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dy < 0 && mFloatingActionButton.isShown()) {
                    mFloatingActionButton.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    mFloatingActionButton.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        if (googleApiClient == null) {
            googleApiClient = buildGoogleApiClient();
        }
        nearestBtn.setEnabled(false);

        updateLatestSortVersion();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DETAIL_SEARCH_CODE) {
            /*
            Call<PostListResultData> call = networkService.getFilteredData();
            call.enqueue(new Callback<PostListResultData>() {
                @Override
                public void onResponse(Call<PostListResultData> call, Response<PostListResultData> response) {
                    if (response.isSuccessful() && response.body().getMessage().equals("")) {
                        Log.i("SearchFragment", "onResponse: " + response.body().getPostList().size());
                        adapter.updatePostListData(response.body().getPostList());
                    }
                }

                @Override
                public void onFailure(Call<PostListResultData> call, Throwable t) {

                }
            });
            */
        }
    }

    private void updateLatestSortVersion() {
        if (networkService == null) {
            networkService = AppController.getInstance().getNetworkService();
        }

        Call<PostListResultData> call = networkService.getSearchLatestData();
        call.enqueue(new Callback<PostListResultData>() {
            @Override
            public void onResponse(Call<PostListResultData> call, Response<PostListResultData> response) {
                if (response.isSuccessful() && response.body().getMessage().equals("successfully load LATEST post list data")) {
                    Log.i("SearchFragment", "onResponse: " + response.body().getPostList().size());
                    adapter.updatePostListData(response.body().getPostList());
                }
            }
            @Override
            public void onFailure(Call<PostListResultData> call, Throwable t) {
            }
        });
    }

    private void updateNearestSortVersion(double lat, double lng) {
        if (networkService == null) {
            networkService = AppController.getInstance().getNetworkService();
        }

        Call<PostListResultData> call = networkService.getSearchNearestData(lat, lng);
        call.enqueue(new Callback<PostListResultData>() {
            @Override
            public void onResponse(Call<PostListResultData> call, Response<PostListResultData> response) {
                if (response.isSuccessful() && response.body().getMessage().equals("successfully load NEAREST post list data")) {
                    Log.i("SearchFragment", "onResponse: " + response.body().getPostList().size());
                    adapter.updatePostListData(response.body().getPostList());
                }
            }
            @Override
            public void onFailure(Call<PostListResultData> call, Throwable t) {
            }
        });
    }

    @SuppressWarnings("MissingPermission")
    private PermissionListener getPermissionListener() {
        return new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Location currLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                updateNearestSortVersion(currLocation.getLatitude(), currLocation.getLongitude());
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            }
        };
    }

    protected synchronized GoogleApiClient buildGoogleApiClient() {
        return new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        nearestBtn.setEnabled(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onPause() {
        super.onDestroy();
        nearestBtn.setEnabled(false);
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }

}
