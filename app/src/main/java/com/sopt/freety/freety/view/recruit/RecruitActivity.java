package com.sopt.freety.freety.view.recruit;

import android.support.annotation.BoolRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.recruit.adapter.RecruitViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecruitActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.recruit_image_view_pager)
    ViewPager imageViewPager;

    @BindView(R.id.recruit_profile)
    ImageView profileView;

    @BindView(R.id.recruit_makeup_list)
    ListView listView;

    @BindViews({R.id.recruit_type_check1, R.id.recruit_type_check2, R.id.recruit_type_check3, R.id.recruit_type_check4})
    List<CheckBox> typeCheckBoxList;


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
        profileView.setImageResource(R.drawable.chat_list_elem);

        List<String> exampleList = Arrays.asList("시술내역11111", "시시 시수수수술ㄹ루 룰내역2222", "시술내역3", "abcdefghijklmnopqrstuvwxyz", "시술!!");
        initListView(exampleList);
        initCheckBoxList(0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng seoul = new LatLng(37.56, 126.97);
        googleMap.addMarker(new MarkerOptions().position(seoul).title("서울").snippet("한국의 수도"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    private void initListView(final List<String> itemList) {
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, R.layout.recruit_popup_search_result_list, itemList);
        listView.setAdapter(listAdapter);
        listView.setDivider(null);
        listView.setEnabled(false);
        listView.getLayoutParams().height = 50 + 40 * itemList.size();
        listView.requestLayout();
    }

    private void initCheckBoxList(int position) {
        if (position > typeCheckBoxList.size() - 1) {
            throw new RuntimeException(new IndexOutOfBoundsException());
        }
        typeCheckBoxList.get(position).setChecked(true);
        for (CheckBox checkBox : typeCheckBoxList) {
            checkBox.setEnabled(false);
        }
    }


    @OnClick(R.id.recruit_pick_btn)
    public void onPickBtnClick(ToggleButton toggleBtn) {
        Log.i("tempTag", "onPickBtnClick: " + toggleBtn.toString());
        if (toggleBtn.isChecked()) {
            toggleBtn.setBackgroundResource(R.drawable.empty_star);
        } else {
            toggleBtn.setBackgroundResource(R.drawable.star);
        }
    }
}
