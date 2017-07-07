package com.sopt.freety.freety.view.my_page;


import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.data.OnlyMsgResultData;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.Consts;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.view.my_page.adapter.MyPageModelRecyclerAdapter;
import com.sopt.freety.freety.view.my_page.data.network.MyPageModelGetData;
import com.yongbeam.y_photopicker.util.photopicker.utils.YPhotoPickerIntent;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sopt.freety.freety.view.my_page.adapter.MyPageModelRecyclerAdapter.TYPE_HEADER;


/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageModelFragment extends Fragment implements ScrollFeedbackRecyclerView.Callbacks{

    @BindView(R.id.my_page_model_profile)
    ImageView profileImage;

    @BindView(R.id.my_page_model_recycler_view)
    ScrollFeedbackRecyclerView recyclerView;

    @BindView(R.id.my_page_model_app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.my_page_model_collaspsing_relative_layout)
    RelativeLayout collapsingRelativeLayout;

    @BindView(R.id.my_page_model_hide_toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_my_page_model_name)
    TextView modelNameText;

    private GridLayoutManager layoutManager;
    private MyPageModelRecyclerAdapter adapter;
    private NetworkService networkService;
    private MyPageModelGetData myPageModelGetData;
    private static final float OPACITIY_FACTOR = 1.8f;
    private int currRequestCode;

    public MyPageModelFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_page_model, container, false);
        ButterKnife.bind(this, view);
        Glide.with(this).load(R.drawable.chat_list_elem)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(profileImage);

        networkService = AppController.getInstance().getNetworkService();
        reload();
        recyclerView.setHasFixedSize(true);
        recyclerView.attachCallbacks(this);
        layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if (position == TYPE_HEADER) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

    @Override
    public boolean isAppBarCollapsed() {
        final int appBarVisibleHeight = (int) (appBarLayout.getY() + appBarLayout.getHeight());
        final int toolbarHeight = toolbar.getHeight();
        return (appBarVisibleHeight >= toolbarHeight && appBarVisibleHeight <= toolbarHeight + 45);
    }

    @Override
    public void setExpanded(boolean expanded) {
        appBarLayout.setExpanded(expanded, true);
    }

    private void reload() {
        Call<MyPageModelGetData> call = networkService.getMyPageInModelAccount(SharedAccessor.getToken(getContext()));
        call.enqueue(new Callback<MyPageModelGetData>() {
            @Override
            public void onResponse(Call<MyPageModelGetData> call, Response<MyPageModelGetData> response) {
                if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                    myPageModelGetData = response.body();
                    Glide.with(getContext()).load(response.body().getModelPhoto())
                            .override(200, 200).thumbnail(0.2f).bitmapTransform(new CropCircleTransformation(getContext()))
                            .into(profileImage);
                    modelNameText.setText(response.body().getModelName());
                    adapter = new MyPageModelRecyclerAdapter(myPageModelGetData.getMyPageModelHeaderDataList(), myPageModelGetData.getModelPickList(), getContext(), MyPageModelFragment.this);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<MyPageModelGetData> call, Throwable t) {}
        });
    }

    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            YPhotoPickerIntent intent = new YPhotoPickerIntent(getActivity());
            intent.setMaxSelectCount(1);
            intent.setSelectCheckBox(true);
            intent.setMaxGrideItemCount(3);
            getActivity().startActivityForResult(intent, Consts.MODEL_PROFILE_PHOTO_CODE);
        }
        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
        }
    };

    public void registerPhoto(int requestCode) {
        currRequestCode = requestCode;
        new TedPermission(getContext())
                .setPermissionListener(modelHairPermissionListener)
                .setRationaleConfirmText("확인")
                .setRationaleMessage("\"Freety\"의 다음 작업을 허용하시겠습니까? 이 기기의 외부 저장소에 액세스하기")
                .setDeniedMessage("거부하시면 볼수 없는데...")
                .setPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
                .check();

    }

    private PermissionListener modelHairPermissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            YPhotoPickerIntent intent = new YPhotoPickerIntent(getActivity());
            intent.setMaxSelectCount(1);
            intent.setSelectCheckBox(true);
            intent.setMaxGrideItemCount(3);
            getActivity().startActivityForResult(intent, currRequestCode);
        }
        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {}
    };

    @OnClick(R.id.my_page_model_profile_edit)
    public void onPictureBtn() {
        new TedPermission(getContext())
                .setPermissionListener(permissionListener)
                .setRationaleConfirmText("확인")
                .setRationaleMessage("\"Freety\"의 다음 작업을 허용하시겠습니까? 이 기기의 외부 저장소에 액세스하기")
                .setDeniedMessage("거부하시면 볼수 없는데...")
                .setPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
                .check();
    }

    public void onPictureRegistered(int requestCode, final String path) {
        final NetworkService networkService = AppController.getInstance().getNetworkService();
        File file = new File(path);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), fileBody);
        switch (requestCode) {
            case Consts.MODEL_PROFILE_PHOTO_CODE:

                Call<OnlyMsgResultData> photoCall = networkService.getOkMsgFromProfile(SharedAccessor.getToken(getActivity()),
                        body);
                photoCall.enqueue(new Callback<OnlyMsgResultData>() {
                    @Override
                    public void onResponse(Call<OnlyMsgResultData> call, Response<OnlyMsgResultData> response) {
                        Log.i("modelProfileUpload : ", response.raw().toString());
                        if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                            Glide.with(getContext()).load(path)
                                    .bitmapTransform(new CropCircleTransformation(getContext())).override(200, 200).thumbnail(0.2f)
                                    .into(profileImage);
                            SharedAccessor.registerURL(getContext(), path);
                            Log.i("modelProfileUpload : ","success" );

                        } else {
                            Log.i("modelProfileUpload : ", "fail" );
                        }

                    }

                    @Override
                    public void onFailure(Call<OnlyMsgResultData> call, Throwable t) {

                        Log.i("modelProfileUpload : ", "on failure" );

                    }
                });

                break;
            case Consts.MODEL_PICTURE_1_CODE:
                adapter.updatePicture(0, path);
                // 사진 보이게 등록하기.
                Call<OnlyMsgResultData> photoCall1 = networkService.uploadModelPhoto1(SharedAccessor.getToken(getActivity()),
                        body);
                photoCall1.enqueue(new Callback<OnlyMsgResultData>() {
                    @Override
                    public void onResponse(Call<OnlyMsgResultData> call, Response<OnlyMsgResultData> response) {
                        if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                            Log.i("modelPhoto1Upload : ","success" );
                            Toast.makeText(getContext(), "사진 업로드 완료.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<OnlyMsgResultData> call, Throwable t) {}
                });

                break;
            case Consts.MODEL_PICTURE_2_CODE:
                adapter.updatePicture(1, path);
                // 사진 보이게 등록하기.
                Call<OnlyMsgResultData> photoCall2 = networkService.uploadModelPhoto2(SharedAccessor.getToken(getActivity()),
                        body);
                photoCall2.enqueue(new Callback<OnlyMsgResultData>() {
                    @Override
                    public void onResponse(Call<OnlyMsgResultData> call, Response<OnlyMsgResultData> response) {
                        if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                            Log.i("modelPhoto2Upload","success" );
                            Toast.makeText(getContext(), "사진 업로드 완료.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OnlyMsgResultData> call, Throwable t) {}
                });
                break;
            case Consts.MODEL_PICTURE_3_CODE:
                adapter.updatePicture(2, path);
                // 사진 보이게 등록하기.
                Call<OnlyMsgResultData> photoCall3 = networkService.uploadModelPhoto3(SharedAccessor.getToken(getActivity()),
                        body);
                photoCall3.enqueue(new Callback<OnlyMsgResultData>() {
                    @Override
                    public void onResponse(Call<OnlyMsgResultData> call, Response<OnlyMsgResultData> response) {
                        if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                            Log.i("modelPhoto3Upload","success" );
                            Toast.makeText(getContext(), "사진 업로드 완료.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<OnlyMsgResultData> call, Throwable t) {}
                });
                break;
            default:
                throw new RuntimeException("there is unexpected request code.");
        }
    }

}
