package com.sopt.freety.freety.view.recruit;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.sopt.freety.freety.view.login.data.SignUpData;
import com.sopt.freety.freety.view.recruit.adapter.RecruitViewPagerAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class RecruitActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.recruit_image_view_pager)
    ViewPager imageViewPager;

    @BindView(R.id.recruit_profile)
    ImageView profileImage;

    @BindView(R.id.recruit_name)
    TextView profileNameText;

    @BindView(R.id.recruit_belong_name)
    TextView profileBelongNameText;

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

    @OnClick(R.id.recruit_pick_btn)
    public void onPickBtnClick(ToggleButton toggleBtn) {
        if (toggleBtn.isChecked()) {
            toggleBtn.setBackgroundResource(R.drawable.recruit_gradient_heart_btn);
            toggleBtn.setText("22");
        } else {
            toggleBtn.setBackgroundResource(R.drawable.recruit_empty_heart_btn);
            toggleBtn.setText("21");
        }
    }

    @BindView(R.id.recruit_letter_btn)
    Button letterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);
        ButterKnife.bind(this);

        final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.recruit_map);
        mapFragment.getMapAsync(this);

        final PagerAdapter adapter = new RecruitViewPagerAdapter(this, Collections.<String>emptyList());
        imageViewPager.setAdapter(adapter);
        imageViewPager.setCurrentItem(5000);
        profileImage.setImageResource(R.drawable.chat_list_elem);
        Glide.with(this).load(R.drawable.chat_list_elem)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(profileImage);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng seoul = new LatLng(37.56, 126.97);
        googleMap.addMarker(new MarkerOptions().position(seoul).title("서울").snippet("한국의 수도"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        googleMap.getUiSettings().setAllGesturesEnabled(false);
    }

}
