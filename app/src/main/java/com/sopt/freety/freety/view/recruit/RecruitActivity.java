package com.sopt.freety.freety.view.recruit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.helper.ImageSwicherHelper;
import com.sopt.freety.freety.view.letter.LetterActivity;
import com.sopt.freety.freety.view.my_page.ModelToDesignerMypageActivity;
import com.sopt.freety.freety.view.recruit.adapter.RecruitViewPagerAdapter;
import com.sopt.freety.freety.view.recruit.data.PickRequestData;
import com.sopt.freety.freety.view.recruit.data.PickResultData;
import com.sopt.freety.freety.view.recruit.data.PostDetailRequestData;
import com.sopt.freety.freety.view.recruit.data.PostDetailResultData;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecruitActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "RecruitActivity";

    @BindView(R.id.recruit_image_view_pager)
    ViewPager imageViewPager;

    @BindView(R.id.recruit_profile)
    ImageView profileImage;

    @OnClick({R.id.recruit_profile, R.id.recruit_title, R.id.recruit_name})
    public void onProfileClick(View view){
        Intent intent = new Intent(RecruitActivity.this, ModelToDesignerMypageActivity.class);
        intent.putExtra("memberId", memberId);
        Toast.makeText(getApplicationContext(),"memberId : "+memberId, Toast.LENGTH_SHORT).show();
        AppController.getInstance().pushPageStack();
        startActivity(intent);
    }

    @BindView(R.id.recruit_title)
    TextView profileTitleText;

    @BindView(R.id.recruit_name)
    TextView profileNameText;

    @BindView(R.id.recruit_pick_btn)
    Button pickBtn;

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
    public void onPickBtnClick(final Button button) {

        if (isPicked) {
            int currPickNumber = Integer.parseInt(button.getText().toString());
            ImageSwicherHelper.doChangeAnimation(this, pickHeartImage,
                    R.anim.heart_fade_out, R.anim.heart_fade_in, R.drawable.recruit_heart_empty_single);
            button.setText(String.valueOf(currPickNumber - 1));

        } else {
            int currPickNumber = Integer.parseInt(button.getText().toString());
            ImageSwicherHelper.doChangeAnimation(this, pickHeartImage,
                    R.anim.heart_fade_out, R.anim.heart_fade_in, R.drawable.recruit_heart_gradient_single);
            button.setText(String.valueOf(currPickNumber + 1));
        }

        button.setEnabled(false);
        int postId = getIntent().getIntExtra("postId", 0);
        Call<PickResultData> pickResultDataCall = networkService.pick(SharedAccessor.getToken(RecruitActivity.this), new PickRequestData(postId, isPicked));
        pickResultDataCall.enqueue(new Callback<PickResultData>() {
            @Override
            public void onResponse(Call<PickResultData> call, Response<PickResultData> response) {
                if (response.isSuccessful()) {
                    String resultMsg = response.body().getMessage();
                    Log.i(TAG, "onResponse: " + resultMsg);
                    isPicked = !isPicked;
                    button.setEnabled(true);
                }
            }
            @Override
            public void onFailure(Call<PickResultData> call, Throwable t) {
                Log.i(TAG, "onResponse: " + "on failed");
            }
        });
    }

    @BindView(R.id.recruit_letter_btn)
    Button letterBtn;

    @OnClick(R.id.recruit_letter_btn)
    public void onLetterBtn() {
        AppController.getInstance().pushPageStack();
        Intent intent = new Intent(RecruitActivity.this, LetterActivity.class);
        intent.putExtra("memberId", memberId);
        intent.putExtra("memberName", profileNameText.getText().toString());
        intent.putExtra("memberImageURL", memberImageURL);
        startActivity(intent);
    }

    @OnClick(R.id.recruit_back_btn)
    public void onBackBtn() {
        onBackPressed();
    }

    private NetworkService networkService;
    private LatLng latLng;

    /**
     * This is boolean value for {@link #pickBtn}
     */
    private boolean isPicked;
    private int memberId;
    private String memberImageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);
        ButterKnife.bind(this);
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
        PostDetailRequestData data = new PostDetailRequestData(getIntent().getIntExtra("postId", 0));
        Call<PostDetailResultData> requestData = networkService.getPostDetailData(SharedAccessor.getToken(RecruitActivity.this), data.getPostId());
        requestData.enqueue(new Callback<PostDetailResultData>() {
            @Override
            public void onResponse(Call<PostDetailResultData> call, Response<PostDetailResultData> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.raw());
                    PostDetailResultData result = response.body();
                    final PagerAdapter adapter = new RecruitViewPagerAdapter(RecruitActivity.this, result.getImageList());
                    memberId = result.getMemberId();
                    imageViewPager.setAdapter(adapter);
                    imageViewPager.setCurrentItem(1000);
                    memberImageURL = result.getWriterImageURL();
                    Glide.with(RecruitActivity.this).load(result.getWriterImageURL())
                            .thumbnail(0.3f).bitmapTransform(new CropCircleTransformation(RecruitActivity.this))
                            .into(profileImage);
                    profileTitleText.setText(result.getTitle());
                    profileNameText.setText(result.getWriterName());
                    if (result.isPicked()) {
                        isPicked = true;
                        Log.i(TAG, "onResponse: " + result.isPicked());
                        pickBtn.setText(String.valueOf(result.getPickCount()));
                        pickHeartImage.setImageResource(R.drawable.recruit_heart_gradient_single);
                    } else {
                        isPicked = false;
                        Log.i(TAG, "onResponse: " + result.getPickCount());
                        pickBtn.setText(String.valueOf(result.getPickCount()));
                        pickHeartImage.setImageResource(R.drawable.recruit_heart_empty_single);
                    }

                    hairTypeText.setText(result.getHairType());
                    hairPriceText.setText(result.getPrice());
                    try {
                        hairDateText.setText(result.getDate());
                    } catch (ParseException e) {
                        hairDateText.setText("시간을 설정하지 않았습니다.");
                    }
                    latLng = result.getLatLng();
                    hairInfoText.setText(result.getContent());
                    Log.i(TAG, "onResponse: " + result.getAddress());
                    addressText.setText(result.getAddress());
                    belongNameText.setText(result.getWriterBelongName());

                    final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.recruit_map);
                    mapFragment.getMapAsync(RecruitActivity.this);
                }
            }

            @Override
            public void onFailure(Call<PostDetailResultData> call, Throwable t) {
                Toast.makeText(RecruitActivity.this, "데이터 로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        int result = AppController.getInstance().popPageStack();
        if (result == 0) {
            Toast.makeText(this, "한 번 더 터치하시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }  else if (result < 0) {
            ActivityCompat.finishAffinity(this);
        } else {
            super.onBackPressed();
        }
    }


}
