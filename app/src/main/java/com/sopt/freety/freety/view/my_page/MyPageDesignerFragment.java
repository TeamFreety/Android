package com.sopt.freety.freety.view.my_page;


import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.sopt.freety.freety.util.custom.ViewPagerEx;
import com.sopt.freety.freety.util.util.EditTextUtils;
import com.sopt.freety.freety.view.my_page.adapter.MyPageViewPagerAdapter;
import com.sopt.freety.freety.view.my_page.data.MyPagePostData;
import com.sopt.freety.freety.view.my_page.data.MyPageReviewData;
import com.sopt.freety.freety.view.my_page.data.MyPageStyleBodyData;
import com.sopt.freety.freety.view.my_page.data.MyPageStyleHeaderData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageDesignerGetData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageStatusUpdateRequestData;
import com.sopt.freety.freety.view.property.ScreenClickable;
import com.yongbeam.y_photopicker.util.photopicker.utils.YPhotoPickerIntent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageDesignerFragment extends Fragment implements ScrollFeedbackRecyclerView.Callbacks, ScreenClickable{

    private static final String TAG = "MyPageDesigner";
    @BindView(R.id.my_page_profile)
    ImageView profileImage;

    @BindView(R.id.my_page_profile_change)
    ImageButton profileChangeBtn;

    @BindView(R.id.text_my_page_designer_name)
    TextView designerNameText;

    @BindView(R.id.edit_my_page_designer_status)
    EditText designerStatusTextView;

    @BindView(R.id.my_page_tab)
    TabLayout tabLayout;

    @BindView(R.id.my_page_view_pager)
    ViewPagerEx viewPager;

    @BindView(R.id.my_page_app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.my_page_collaspsing_relative_layout)
    RelativeLayout collapsingRelativeLayout;

    @OnClick(R.id.my_page_app_bar)
    public void onAppbarScreenClick(View v) {
        onScreenClick(v);
    }

    @BindView(R.id.my_page_hide_toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_my_page_chat)
    ImageButton chatButton;

    @BindView(R.id.btn_my_page_status_edit)
    ImageButton statusEditButton;

    @OnClick(R.id.btn_my_page_status_edit)
    public void onClickEditBtn() {
        EditTextUtils.setUseableEditText(designerStatusTextView, true);
        designerStatusTextView.requestFocus();
        designerStatusTextView.setSelection(designerStatusTextView.length());
        InputMethodManager lManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        lManager.showSoftInput(designerStatusTextView, 0);
    }

    private static final float OPACITY_FACTOR = 1.8f;
    private NetworkService networkService;
    private MyPageDesignerGetData myPageDesignerGetData;

    public MyPageDesignerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_designer_my_page, container, false);
        ButterKnife.bind(this, view);
        Glide.with(this).load(R.drawable.chat_list_elem)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(profileImage);

        tabLayout.addTab(tabLayout.newTab().setText("글목록"));
        tabLayout.addTab(tabLayout.newTab().setText("포트폴리오"));
        tabLayout.addTab(tabLayout.newTab().setText("후기"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(Color.parseColor("#95989A"), Color.parseColor("#000000"));
        tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        networkService = AppController.getInstance().getNetworkService();
        reload();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsingRelativeLayout.setAlpha(Math.max(1.0f - (OPACITY_FACTOR * Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange())), 0));
            }
        });


        EditTextUtils.setUseableEditText(designerStatusTextView, false);
        return view;
    }

    @Override
    public boolean isAppBarCollapsed() {
        final int appBarVisibleHeight = (int) (appBarLayout.getY() + appBarLayout.getHeight());
        final int toolbarHeight = toolbar.getHeight();
        return (appBarVisibleHeight >= toolbarHeight && appBarVisibleHeight <= toolbarHeight + 25);
    }

    @Override
    public void setExpanded(boolean expanded) {
        appBarLayout.setExpanded(expanded, true);
    }

    public List<MyPagePostData> getPostDataList() {
        return myPageDesignerGetData.getMyPagePostDataList();
    }

    public List<MyPageStyleBodyData> getStyleBodyDataList() {
        return myPageDesignerGetData.getMyStyleBodyDataList();
    }

    public MyPageStyleHeaderData getMyPageStyleHeaderData() {
        return myPageDesignerGetData.getMyPageStyleHeaderData();
    }

    public MyPageReviewData getMyPageReviewData() {
        return myPageDesignerGetData.getMyPageReviewData();
    }

    @Override
    public void onScreenClick(View v) {
        if (designerStatusTextView.isFocused()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(designerStatusTextView.getWindowToken(), 0);
            EditTextUtils.setUseableEditText(designerStatusTextView, false);
            Call<OnlyMsgResultData> call = networkService.getOkMsg(SharedAccessor.getToken(getContext()),
                    new MyPageStatusUpdateRequestData(designerStatusTextView.getText().toString()));
            call.enqueue(new Callback<OnlyMsgResultData>() {
                @Override
                public void onResponse(Call<OnlyMsgResultData> call, Response<OnlyMsgResultData> response) {
                    if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                        Toast.makeText(getContext(), "적용완료", Toast.LENGTH_SHORT).show();
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

    private void reload() {
        Call<MyPageDesignerGetData> call = networkService.getMyPageInDesignerAccount(SharedAccessor.getToken(getContext()));
        call.enqueue(new Callback<MyPageDesignerGetData>() {
            @Override
            public void onResponse(Call<MyPageDesignerGetData> call, Response<MyPageDesignerGetData> response) {

                if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                    Log.i(TAG, "onResponse: " + response.body().getDesignerName());
                    myPageDesignerGetData = response.body();
                    Glide.with(getContext()).load(response.body().getDesignerImageURL())
                            .override(200, 200).thumbnail(0.3f).bitmapTransform(new CropCircleTransformation(getContext()))
                            .into(profileImage);
                    designerNameText.setText(response.body().getDesignerName());
                    designerStatusTextView.setText(response.body().getDesignerStatusMsg());

                    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                    PagerAdapter pagerAdapter = new MyPageViewPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
                    viewPager.setAdapter(pagerAdapter);
                    viewPager.setOffscreenPageLimit(2);
                    viewPager.setCurrentItem(0);
                }
            }

            @Override
            public void onFailure(Call<MyPageDesignerGetData> call, Throwable t) {
            }
        });
    }

    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            YPhotoPickerIntent intent = new YPhotoPickerIntent(getActivity());
            intent.setMaxSelectCount(1);
            intent.setSelectCheckBox(true);
            intent.setMaxGrideItemCount(3);
            getActivity().startActivityForResult(intent, Consts.DESIGNER_PROFILE_PHOTO_CODE);
        }
        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
        }
    };

    @OnClick(R.id.my_page_profile_change)
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
        switch (requestCode) {
            case Consts.DESIGNER_PROFILE_PHOTO_CODE:
                File file = new File(path);
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), fileBody);

                Call<OnlyMsgResultData> photoCall = networkService.getOkMsgFromProfile(SharedAccessor.getToken(getActivity()),
                        body);
                photoCall.enqueue(new Callback<OnlyMsgResultData>() {
                    @Override
                    public void onResponse(Call<OnlyMsgResultData> call, Response<OnlyMsgResultData> response) {
                        Log.i("MyPage", "onResponse: " + response.raw());
                        Log.i("MyPage", "onResponse: " + response.body().getMessage());
                        if (response.isSuccessful() && response.body().getMessage().equals("ok")) {
                            Glide.with(getContext()).load(path).
                                    override(200, 200).thumbnail(0.3f).bitmapTransform(new CropCircleTransformation(getContext()))
                                    .into(profileImage);
                        }
                    }

                    @Override
                    public void onFailure(Call<OnlyMsgResultData> call, Throwable t) {
                    }
                });

                break;
            default:
                throw new RuntimeException("there is unexpected request code.");
        }
    }
}
