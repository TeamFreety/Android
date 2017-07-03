package com.sopt.freety.freety.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KYJ on 2017-06-26.
 */

public class JoinActivity extends AppCompatActivity {

    @BindView(R.id.modelCaseBtn) Button modelCaseBtn;
    @BindView(R.id.designerCaseBtn) Button designerCaseBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_join_case);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.modelCaseBtn,R.id.designerCaseBtn})
    public void onButtonClick(final View view){
        Animation btnAnim = AnimationUtils.loadAnimation(JoinActivity.this, R.anim.join_btn_anim);
        btnAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                switch(view.getId()) {
                    case R.id.modelCaseBtn:
                        Intent intent = new Intent(getApplicationContext(),SignUpEmailActivity.class);
                        intent.putExtra("case", true) ;
                        startActivity(intent);
                        break;
                    case R.id.designerCaseBtn:
                        Intent intent2 = new Intent(getApplicationContext(),SignUpEmailActivity.class);
                        intent2.putExtra("case", false) ;
                        startActivity(intent2);
                        break;
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(btnAnim);
    }
}
