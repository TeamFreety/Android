package com.sopt.freety.freety.view.login;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.facebook.login.widget.LoginButton;
import com.kakao.auth.AuthType;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.custom.KakaoLoginButton;
import com.sopt.freety.freety.view.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KYJ on 2017-06-27.
 */

public class FirstLoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private SessionCallback callback;

    private String userId;
    private String userName;

    // view
    private Button facebookBtn;
    //private Button kakaoBtn;
/*    private Button emailBtn;
    private Button skipBtn;
    private TextView joinTextView;*/



private int rId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_first_login);

      //  int rId = getResources().getIdentifier("kakao_layout", );



        UserManagement.requestLogout(new LogoutResponseCallback() {

            @Override
            public void onCompleteLogout() {
                //로그아웃 성공 후
            }
        });

        //callback = new SessionCallback();
       // Session.getCurrentSession().addCallback(callback);
        callbackManager = CallbackManager.Factory.create();

        ButterKnife.bind(this);

        facebookBtn = (Button)findViewById(R.id.facebookBtn);
        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginManager - 요청된 읽기 또는 게시 권한으로 로그인 절차를 시작합니다.
                LoginManager.getInstance().logInWithReadPermissions(FirstLoginActivity.this,
                        Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.e("onSuccess", "onSuccess");
                                GraphRequest request = GraphRequest.newMeRequest(
                                        loginResult.getAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(
                                                    JSONObject object,
                                                    GraphResponse response) {

                                               // Log.v("LoginActivity", response.toString());
                                                try {
                                                    userId = object.getString("email");
                                                    userName = object.getString("name");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,name,email,gender, birthday");
                                request.setParameters(parameters);
                                request.executeAsync();

                                //Toast.makeText(getApplicationContext(),"facebook success",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(FirstLoginActivity.this, JoinActivity.class);
                                intent.putExtra("login case","facebook");
                                intent.putExtra("userId",userId);
                                intent.putExtra("userName",userName);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancel() {
                                Log.e("onCancel", "onCancel");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.e("onError", "onError " + exception.getLocalizedMessage());
                            }
                        });
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //간편로그인시 호출 ,없으면 간편로그인시 로그인 성공화면으로 넘어가지 않음
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            UserManagement.requestMe(new MeResponseCallback() {

                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;
                    Logger.d(message);

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                }

                @Override
                public void onNotSignedUp() {
                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    userId = String.valueOf(userProfile.getId());
                    userName = userProfile.getNickname();

                    Log.e("UserProfile", userProfile.toString());
                    Toast.makeText(getApplicationContext(),userId,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(FirstLoginActivity.this, JoinActivity.class);
                    intent.putExtra("login case","kakao");
                    intent.putExtra("userId",userId);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                    finish();
                }
            });

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("err", ""+exception);
        }
    }



    @OnClick({R.id.emailBtn, R.id.text_skip, R.id.kakaoBtn})

    public void onClick(View view){
        switch(view.getId()){
            case R.id.emailBtn:
                Intent intent = new Intent(getApplicationContext(),EmailLoginActivity.class);
                //intent.putExtra("login case","email");
                startActivity(intent);
                break;
            case R.id.text_skip:
            Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent2);
            break;
            case R.id.kakaoBtn:
                isKakaoLogin();
                break;
        }
    }

    private void isKakaoLogin(){
        callback = new SessionCallback();
        com.kakao.auth.Session.getCurrentSession().addCallback(callback);
        com.kakao.auth.Session.getCurrentSession().checkAndImplicitOpen();
        com.kakao.auth.Session.getCurrentSession().open(AuthType.KAKAO_TALK,FirstLoginActivity.this);
    }

}
