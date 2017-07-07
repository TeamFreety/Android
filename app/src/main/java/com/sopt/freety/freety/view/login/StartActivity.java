package com.sopt.freety.freety.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.data.OnlyMsgResultData;
import com.sopt.freety.freety.util.Consts;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.util.HashKeyChecker;
import com.sopt.freety.freety.view.login.data.SNSLoginRequestData;
import com.sopt.freety.freety.view.login.data.SNSLoginResultData;
import com.sopt.freety.freety.view.main.MainActivity;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KYJ on 2017-06-27.
 */

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";
    private CallbackManager callbackManager;
    private SessionCallback kakaoCallback;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.sdkInitialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        ButterKnife.bind(this);
        HashKeyChecker.checkHashKey(this);
        callbackManager = CallbackManager.Factory.create();
        kakaoCallback = new SessionCallback();
        com.kakao.auth.Session.getCurrentSession().addCallback(kakaoCallback);
        tryAutoLogin();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    Toast.makeText(StartActivity.this, "인터넷 연결을 확인하세요.", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.i(TAG, "onSessionClosed: ");
                }
                @Override
                public void onNotSignedUp() {
                    Log.i(TAG, "onNotSignedUp: ");
                }
                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    //userName = userProfile.getNickname();
                    final String kakaoId = String.valueOf(userProfile.getId());
                    Log.i(TAG, "onSuccess: ");
                    Call<SNSLoginResultData> call = AppController.getInstance().getNetworkService().snslogin(new SNSLoginRequestData(null, kakaoId));
                    call.enqueue(new Callback<SNSLoginResultData>() {
                        @Override
                        public void onResponse(Call<SNSLoginResultData> call, Response<SNSLoginResultData> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getMessage().equals("SNS login success")) {
                                    response.body().registerToken(StartActivity.this);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    AppController.getInstance().pushPageStack();
                                    startActivity(intent);
                                } else if (response.body().getMessage().equals("no information about SNS account")) {
                                    Intent intent = new Intent(StartActivity.this, SelectMemberTypeActivity.class);
                                    intent.putExtra("login case","kakao");
                                    Log.i("Kakao", "onResponse: " + kakaoId);
                                    intent.putExtra("kUserId", kakaoId);
                                    AppController.getInstance().pushPageStack();
                                    startActivity(intent);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<SNSLoginResultData> call, Throwable t) {
                            Log.i(TAG, "onFailure: ");
                        }
                    });
                    finish();
                }
            });
        }
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("err", "" + exception);
        }
    }

    @OnClick({R.id.emailBtn, R.id.kakaoBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emailBtn:
                Intent intent = new Intent(getApplicationContext(), EmailLoginActivity.class);
                AppController.getInstance().pushPageStack();
                startActivity(intent);
                overridePendingTransition(R.anim.screen_slide_up, R.anim.screen_slide_stop);
                break;
            case R.id.kakaoBtn:
                new KakaoLoginControl(this).call();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        int result = AppController.getInstance().popPageStack();
        if (result == 0) {
            Toast.makeText(this, "한 번 더 터치하시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (result < 0) {
            ActivityCompat.finishAffinity(this);
        } else {
            super.onBackPressed();
        }
    }


    private void tryAutoLogin() {
        Call<OnlyMsgResultData> autoLoginCall = AppController.getInstance().getNetworkService().auto(SharedAccessor.getToken(StartActivity.this));
        autoLoginCall.enqueue(new Callback<OnlyMsgResultData>() {
            @Override
            public void onResponse(Call<OnlyMsgResultData> call, Response<OnlyMsgResultData> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("token validated")) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        AppController.getInstance().resetPageStack();
                        startActivity(intent);
                    } else if (response.body().getMessage().equals("invalid token")) {
                    }
                }
            }

            @Override
            public void onFailure(Call<OnlyMsgResultData> call, Throwable t) {
              //  Toast.makeText(StartActivity.this, "데이터 로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.facebookBtn)
    public void onLoginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(StartActivity.this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {

                        Call<SNSLoginResultData> call = AppController.getInstance().getNetworkService()
                                .snslogin(new SNSLoginRequestData(loginResult.getAccessToken().getUserId(), null));
                        call.enqueue(new Callback<SNSLoginResultData>() {
                            @Override
                            public void onResponse(Call<SNSLoginResultData> call, Response<SNSLoginResultData> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getMessage().equals("SNS login success")) {
                                        response.body().registerToken(StartActivity.this);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        AppController.getInstance().resetPageStack();
                                        startActivity(intent);
                                    } else if (response.body().getMessage().equals("no information about SNS account")) {
                                        Intent intent = new Intent(getApplicationContext(), SelectMemberTypeActivity.class);
                                        intent.putExtra("login case","facebook");
                                        intent.putExtra(Consts.FACEBOOK_ID_KEY, loginResult.getAccessToken().getUserId());
                                        AppController.getInstance().resetPageStack();
                                        startActivity(intent);
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<SNSLoginResultData> call, Throwable t) {
                            }
                        });
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
}
