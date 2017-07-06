package com.sopt.freety.freety.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KYJ on 2017-06-26.
 */

public class SelectMemberTypeActivity extends AppCompatActivity {

    @BindView(R.id.modelCaseBtn) Button modelCaseBtn;
    @BindView(R.id.designerCaseBtn) Button designerCaseBtn;

    private String loginCase;
    private String kUserId;
    private String fUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_join_case);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        loginCase = intent.getStringExtra("login case");
        kUserId = intent.getStringExtra("kuserId");
        fUserId = intent.getStringExtra("fUserId");
        Toast.makeText(this, "카카오아이디"+kUserId, Toast.LENGTH_SHORT).show();

    }

    @OnClick({R.id.modelCaseBtn, R.id.designerCaseBtn})
    public void onButtonClick(final View view){
        Animation btnAnim = AnimationUtils.loadAnimation(SelectMemberTypeActivity.this, R.anim.join_btn_anim);
        btnAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent;
                switch(view.getId()) {
                    case R.id.modelCaseBtn:
                        if(loginCase.equals("email")){
                            intent = new Intent(getApplicationContext(), ModelEmailSignUpActivity.class);
                        } else {
                            intent = new Intent(getApplicationContext(), ModelSNSSignUpActivity.class);
                            intent.putExtra("kUserId", kUserId);
                            intent.putExtra("fUserId", fUserId);
                        }
                        break;
                    case R.id.designerCaseBtn:
                        if(loginCase.equals("email")) {
                            intent = new Intent(getApplicationContext(), DesignerEmailSignUpActivity.class);
                        } else{
                            intent = new Intent(getApplicationContext(), DesignerSNSSignUpActivity.class);
                            intent.putExtra("kUserId", kUserId);
                            intent.putExtra("fUserId", fUserId);
                        }
                        break;
                    default:
                        throw new RuntimeException("Unexpected type button is selected");
                }
                AppController.getInstance().pushPageStack();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(btnAnim);
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
