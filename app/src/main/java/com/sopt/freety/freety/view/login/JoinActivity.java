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

    private String loginCase = "";
    private String userId = "";
    private String userName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_join_case);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        loginCase = intent.getStringExtra("login case");
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");

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
                        if(loginCase.equals("email")){
                        Intent intent = new Intent(getApplicationContext(),ModelEmailSignUpActivity.class);
                        startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(),ModelSNSSignUpActivity.class);
                            intent.putExtra("userId",userId);
                            intent.putExtra("userName",userName);
                            startActivity(intent);
                        }
                        break;
                    case R.id.designerCaseBtn:
                        if(loginCase.equals("email")) {
                            Intent intent = new Intent(getApplicationContext(), DesignerEmailSignUpActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(),DesignerSNSSignUpActivity.class);
                            intent.putExtra("userId",userId);
                            intent.putExtra("userName",userName);
                            startActivity(intent);
                        }
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
