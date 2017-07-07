package com.sopt.freety.freety.view.search;


import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.adapter.RecyclerViewOnItemClickListener;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.data.PostListData;
import com.sopt.freety.freety.data.PostListResultData;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.Consts;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.custom.ItemOffsetDecoration;
import com.sopt.freety.freety.view.recruit.RecruitActivity;
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

public class SearchFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private static final String TAG = "SearchFragment";
    @BindView(R.id.rv_search)
    RecyclerView mRecyclerView;

    @BindView(fabtn_search_to_write)
    FloatingActionButton writeFloatingButton;

    @BindView(R.id.btn_search_nearest)
    Button nearestBtn;

    @BindView(R.id.btn_search_latest)
    Button latestBtn;

    @OnClick(R.id.btn_search_nearest)
    public void onDistanceBtn() {
        new TedPermission(getContext())
                .setPermissionListener(getPermissionListener())
                .setRationaleConfirmText("확인")
                .setRationaleMessage("\"Freety\"의 다음 작업을 허용하시겠습니까? 이 기기의 위치에 액세스하기")
                .setDeniedMessage("거부하시면 볼수 없는데...")
                .setPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                .check();

        nearestBtn.setBackgroundResource(R.drawable.search_latest_btn);
        nearestBtn.setTextColor(getResources().getColor(R.color.colorBtnClick));
        latestBtn.setBackgroundResource(R.drawable.search_nearest_btn);
        latestBtn.setTextColor(getResources().getColor(R.color.colorBtnUnClick));
    }

    @OnClick(R.id.btn_search_latest)
    public void onLatestBtn() {
        googleApiClient.disconnect();
        updateLatestSortVersion();

        latestBtn.setBackgroundResource(R.drawable.search_latest_btn);
        latestBtn.setTextColor(getResources().getColor(R.color.colorBtnClick));
        nearestBtn.setBackgroundResource(R.drawable.search_nearest_btn);
        nearestBtn.setTextColor(getResources().getColor(R.color.colorBtnUnClick));
    }


    @OnClick({R.id.img_search_filter, R.id.search_filter_btn2})
    public void onFilterBtn() {
        Intent intent = new Intent(getActivity(), FilteredSearchActivity.class);
        getActivity().startActivityForResult(intent, Consts.DETAIL_SEARCH_CODE);
        getActivity().overridePendingTransition(R.anim.screen_slide_up, R.anim.screen_slide_stop);
        AppController.getInstance().pushPageStack();
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
        hideIfModelWindow();
        latestBtn.setBackgroundResource(R.drawable.search_latest_btn);
        latestBtn.setTextColor(getResources().getColor(R.color.colorBtnClick));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.search_image_offset));
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new SearchRecyclerAdapter(getContext(), Collections.<PostListData>emptyList());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getContext(), mRecyclerView,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent postDetailIntent = new Intent(getContext(), RecruitActivity.class);
                        postDetailIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        postDetailIntent.putExtra("postId", adapter.getPostDataList().get(position).getPostId());
                        AppController.getInstance().pushPageStack();
                        startActivity(postDetailIntent);
                    }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        }));

        if (googleApiClient == null) {
            googleApiClient = buildGoogleApiClient();
        }
        updateLatestSortVersion();
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Consts.DETAIL_SEARCH_CODE) {
            Call<PostListResultData> call = networkService.getFilteredData(data.getIntExtra("typeDye", 0),
                    data.getIntExtra("typePerm", 0),
                    data.getIntExtra("typeCut", 0),
                    data.getIntExtra("typeEct", 0),
                    data.getIntExtra("least_price", 0),
                    data.getIntExtra("high_price", 300000),
                    data.getIntExtra("career", 0),
                    data.getStringExtra("least_date"),
                    data.getStringExtra("high_date"),
                    data.getStringExtra("sigugun"));
            call.enqueue(new Callback<PostListResultData>() {
                @Override
                public void onResponse(Call<PostListResultData> call, Response<PostListResultData> response) {
                    if (response.isSuccessful() && response.body().getMessage().equals("successfully load post list data")) {
                        Log.i("SearchFragment", "onResponse: " + response.body().getPostList().size());
                        adapter.updatePostListData(response.body().getPostList());
                    } else {
                        Toast.makeText(getActivity(), "응답은 잘 왔지만 메세지가 안맞음.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<PostListResultData> call, Throwable t) {
                    Toast.makeText(getActivity(), "데이터 로딩 실패.", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (requestCode == Consts.WRITE_REQUEST) {
            updateLatestSortVersion();
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

    private PermissionListener getPermissionListener() {
        return new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Log.i(TAG, "onPermissionGranted: granted");
                googleApiClient.connect();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            }
        };
    }

    protected synchronized GoogleApiClient buildGoogleApiClient() {
        return new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected: onConnected");
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(120000);
        locationRequest.setFastestInterval(30000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed: fail");
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.disconnect();
    }

    private void hideIfModelWindow() {
        if (SharedAccessor.isDesigner(getContext())) {
            writeFloatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WriteActivity.class);
                    AppController.getInstance().pushPageStack();
                    getActivity().startActivity(intent);
                }
            });
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0 || dy < 0 && writeFloatingButton.isShown()) {
                        writeFloatingButton.hide();
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        writeFloatingButton.show();
                    }
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        } else {
            CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) writeFloatingButton.getLayoutParams();
            p.setAnchorId(View.NO_ID);
            writeFloatingButton.setLayoutParams(p);
            writeFloatingButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged: " + location);
        updateNearestSortVersion(location.getLatitude(), location.getLongitude());
    }
}
