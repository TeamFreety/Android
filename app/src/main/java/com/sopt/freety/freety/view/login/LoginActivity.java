package com.sopt.freety.freety.view.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KYJ on 2017-06-26.
 */

public class LoginActivity extends AppCompatActivity{

    SharedPreferences pref;
    String email;
    String pwd;


    @BindView(R.id.cancelBtn) Button cancelBtn;
    @BindView(R.id.submitBtn) Button submitBtn;
    @BindView(R.id.emailEditText) EditText emailEditText;
    @BindView(R.id.pwdEditText) EditText pwdEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private boolean loginValidation(String email, String password) {
        if (pref.getString("email", "").equals(email) && pref.getString("비밀번호", "").equals(password)) {
            // login success
            return true;
        } else {
            if (pref.getString("email", "").equals(null)) {
                // 없는 이메일
                Toast.makeText(LoginActivity.this, "존재하지 않는 이메일입니다.", Toast.LENGTH_LONG).show();
                return false;
            } else {
                // login failed
                return false;
            }
        }
    }



    @OnClick({R.id.submitBtn,R.id.cancelBtn})
    public void onButtonClick(View view){
        switch(view.getId()){
            case R.id.cancelBtn:
                super.onBackPressed();
                break;
            case R.id.submitBtn:

                email = emailEditText.getText().toString();
                pwd = pwdEditText.getText().toString();



                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);

        }

    }
}
