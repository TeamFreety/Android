package com.sopt.freety.freety.view.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KYJ on 2017-06-26.
 */

public class EmailLoginActivity extends AppCompatActivity{

    SharedPreferences pref;
    String email;
    String pwd;


    @BindView(R.id.edit_login_email) EditText emailEditText;
    @BindView(R.id.edit_login_pwd) EditText pwdEditText;

    @BindView(R.id.text_email_login_to_sign_up) TextView textView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        ButterKnife.bind(this);

        SpannableString content = new SpannableString("이메일로 회원가입하기");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);


    }

    private boolean loginValidation(String email, String password) {
        if (pref.getString("email", "").equals(email) && pref.getString("비밀번호", "").equals(password)) {
            // login success
            return true;
        } else {
            if (pref.getString("email", "").equals(null)) {
                // 없는 이메일
                Toast.makeText(EmailLoginActivity.this, "존재하지 않는 이메일입니다.", Toast.LENGTH_LONG).show();
                return false;
            } else {
                // login failed
                return false;
            }
        }
    }



    @OnClick({R.id.submit_btn,R.id.back_btn,R.id.text_email_login_to_sign_up})
    public void onButtonClick(View view){
        switch(view.getId()){
            case R.id.back_btn:
                super.onBackPressed();
                break;

            case R.id.text_email_login_to_sign_up:

                Intent intent2 = new Intent(getApplicationContext(),JoinActivity.class);
                intent2.putExtra("login case","email");
                startActivity(intent2);

                break;

            case R.id.submit_btn:
                email = emailEditText.getText().toString();
                pwd = pwdEditText.getText().toString();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
        }

    }
}
