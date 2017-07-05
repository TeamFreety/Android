package com.sopt.freety.freety.view.my_page;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.sopt.freety.freety.R;

import butterknife.ButterKnife;

public class MyPageReviewPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_review_popup);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        getWindow().getAttributes().width = (int)(0.9f * display.getWidth());
        getWindow().getAttributes().height = (int)(0.9f * display.getHeight());
        ButterKnife.bind(this);
    }
}
