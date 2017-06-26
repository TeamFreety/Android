package com.sopt.freety.freety.view.mypage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyPageActivity extends AppCompatActivity {

    @BindView(R.id.my_page_profile)
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.chat_list_elem)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .into(profileImage);

    }
}
