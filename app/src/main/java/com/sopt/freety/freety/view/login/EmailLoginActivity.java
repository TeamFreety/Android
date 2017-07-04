package com.sopt.freety.freety.view.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.view.login.data.LoginRequestData;
import com.sopt.freety.freety.view.login.data.LoginResultData;
import com.sopt.freety.freety.view.main.MainActivity;
import com.sopt.freety.freety.view.property.ScreenClickable;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KYJ on 2017-06-26.
 */

public class EmailLoginActivity extends AppCompatActivity implements ScreenClickable {

    SharedPreferences pref;
    String email;
    String pwd;

    @BindView(R.id.edit_login_email) EditText emailEditText;
    @BindView(R.id.edit_login_pwd) EditText pwdEditText;
    @BindView(R.id.text_email_login_to_sign_up) TextView textView;
    @BindViews({R.id.edit_login_email_layout, R.id.edit_login_pwd_layout})
    List<LinearLayout> layoutList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_email_login);
        ButterKnife.bind(this);

        SpannableString content = new SpannableString("이메일로 회원가입하기");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
        emailEditText.requestFocus();
    }

    @OnClick({R.id.submit_btn, R.id.email_login_back_btn, R.id.text_email_login_to_sign_up})
    public void onButtonClick(View view){
        switch(view.getId()){
            case R.id.email_login_back_btn:
                onBackPressed();
                break;

            case R.id.text_email_login_to_sign_up:
                Intent intent2 = new Intent(getApplicationContext(), SelectMemberTypeActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent2.putExtra("login case","email");
                startActivity(intent2);
                AppController.getInstance().pushPageStack();
                break;

            case R.id.submit_btn:
                email = emailEditText.getText().toString();
                pwd = pwdEditText.getText().toString();
                //TODO: implement login network
                Call<LoginResultData> call = AppController.getInstance().getNetworkService().login(new LoginRequestData(email, pwd));
                call.enqueue(new Callback<LoginResultData>() {
                    @Override
                    public void onResponse(Call<LoginResultData> call, Response<LoginResultData> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getMessage().equals("login success")) {
                                response.body().registerToken(EmailLoginActivity.this);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                AppController.getInstance().resetPageStack();
                                startActivity(intent);
                            } else if (response.body().getMessage().equals("no information about the account")) {
                                Toast.makeText(EmailLoginActivity.this, "아이디나 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResultData> call, Throwable t) {
                    }
                });

                break;
        }
    }

    @Override
    public void onScreenClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(pwdEditText.getWindowToken(), 0);
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
