package com.sopt.freety.freety.view.recruit;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.helper.ImageSwicherHelper;
import com.sopt.freety.freety.view.recruit.adapter.RecruitViewPagerAdapter;
import com.sopt.freety.freety.view.recruit.data.PostDetailRequestData;
import com.sopt.freety.freety.view.recruit.data.PostDetailResultData;

import java.text.ParseException;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecruitActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.recruit_image_view_pager)
    ViewPager imageViewPager;

    @BindView(R.id.recruit_profile)
    ImageView profileImage;

    @BindView(R.id.recruit_title)
    TextView profileTitleText;

    @BindView(R.id.recruit_name)
    TextView profileNameText;

    @BindView(R.id.recruit_pick_btn)
    ToggleButton pickBtn;

    @BindView(R.id.recruit_hair_type)
    TextView hairTypeText;

    @BindView(R.id.recruit_hair_price)
    TextView hairPriceText;

    @BindView(R.id.recruit_hair_date)
    TextView hairDateText;

    @BindView(R.id.recruit_info)
    TextView hairInfoText;

    @BindView(R.id.recruit_pick_heart)
    ImageView pickHeartImage;

    @BindView(R.id.recruit_address)
    TextView addressText;

    @BindView(R.id.recruit_belong_name)
    TextView belongNameText;

    @OnClick(R.id.recruit_pick_btn)
    public void onPickBtnClick(ToggleButton toggleBtn) {

        if (toggleBtn.isChecked()) {
            ImageSwicherHelper.doChangeAnimation(this, pickHeartImage,
                    R.anim.heart_fade_out, R.anim.heart_fade_in, R.drawable.recruit_heart_gradient_single);
            toggleBtn.setText("22");
        } else {
            ImageSwicherHelper.doChangeAnimation(this, pickHeartImage,
                    R.anim.heart_fade_out, R.anim.heart_fade_in, R.drawable.recruit_heart_empty_single);
            toggleBtn.setText("21");
        }
    }

    @BindView(R.id.recruit_letter_btn)
    Button letterBtn;

    private NetworkService networkService;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);
        ButterKnife.bind(this);

        final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.recruit_map);
        mapFragment.getMapAsync(this);
        networkService = AppController.getInstance().getNetworkService();
        initRecruitInfo();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (latLng == null) {
            return;
        }
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        googleMap.getUiSettings().setAllGesturesEnabled(false);
    }

    private void initRecruitInfo() {
        PostDetailRequestData data = new PostDetailRequestData(this, getIntent().getIntExtra("postId", 0));
        Call<PostDetailResultData> requestData = networkService.getPostDetailData(data.getMember_token(), data.getPostId());
        requestData.enqueue(new Callback<PostDetailResultData>() {
            @Override
            public void onResponse(Call<PostDetailResultData> call, Response<PostDetailResultData> response) {
                if (response.isSuccessful()) {
                    PostDetailResultData result = response.body();
                    final PagerAdapter adapter = new RecruitViewPagerAdapter(RecruitActivity.this, result.getImageList());
                    imageViewPager.setAdapter(adapter);
                    imageViewPager.setCurrentItem(1000);
                    Glide.with(RecruitActivity.this).load(result.getWriterImageURL()).thumbnail(0.3f).into(profileImage);
                    profileTitleText.setText(result.getTitle());
                    profileNameText.setText(result.getWriterName());
                    pickBtn.setText(String.valueOf(result.getPickCount()));
                    if (result.isPicked()) {
                        pickBtn.setChecked(true);
                    }

                    hairTypeText.setText(result.getHairType());
                    hairPriceText.setText(result.getPrice());
                    try {
                        hairDateText.setText(result.getDate());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    latLng = result.getLatLng();
                    hairInfoText.setText(result.getContent());
                    addressText.setText(result.getAddress());
                    belongNameText.setText(result.getWriterBelongName());
                }
            }

            @Override
            public void onFailure(Call<PostDetailResultData> call, Throwable t) {
            }
        });

    }

}
