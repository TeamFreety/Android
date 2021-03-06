package com.sopt.freety.freety.view.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.Consts;
import com.sopt.freety.freety.util.SharedAccessor;
import com.sopt.freety.freety.util.util.FormatChecker;
import com.sopt.freety.freety.view.login.data.SignUpData;
import com.sopt.freety.freety.view.login.data.SignUpResultData;
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

public class ModelSNSSignUpActivity extends AppCompatActivity implements ScreenClickable {

    @BindView(R.id.sign_up_sns_model_name_edit)
    EditText nameEditText;

    @BindView(R.id.sign_up_sns_model_age_edit)
    EditText ageEditText;

    @BindViews({R.id.sign_up_sns_model_auth_checkbox1, R.id.sign_up_sns_model_auth_checkbox2})
    List<CheckBox> checkBoxList;

    @BindView(R.id.sign_up_sns_model_finish_btn)
    TextView finishBtn;

    @OnClick(R.id.sign_up_sns_model_back_btn)
    public void onBackBtn() {
        onBackPressed();
    }

    private NetworkService networkService;
    private String kUserId;
    private String fUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        kUserId = intent.getStringExtra("kUserId");
        fUserId = intent.getStringExtra("fUserId");
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#f1f1f1"));
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_model_sns_sign_up);
        ButterKnife.bind(this);
        networkService = AppController.getInstance().getNetworkService();
        initCheckBoxList();
        finishBtn.setClickable(false);
    }

    private void setFinishBtnActive(boolean isReady) {

        if (!isReady) {
            finishBtn.setBackgroundColor(Color.parseColor("#b7b7b7"));
            finishBtn.setClickable(false);
        } else {
            finishBtn.setBackgroundColor(Color.parseColor("#ADF1E0"));
            finishBtn.setClickable(true);
        }
    }
    private void initCheckBoxList() {
        for (final CheckBox checkBox : checkBoxList) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkBoxList.get(0).isChecked() && checkBoxList.get(1).isChecked()) {
                        setFinishBtnActive(true);
                    } else {
                        setFinishBtnActive(false);
                    }
                }
            });
        }
    }
    @OnClick(R.id.sign_up_sns_model_finish_btn)
    public void onClick() {
        if (isClearFormat()) {
            final SignUpData signUpData = new SignUpData.Builder(nameEditText.getText().toString(), Integer.parseInt(ageEditText.getText().toString()))
                    .setMemberFacebookCode(fUserId)
                    .setMemberKakaoCode(kUserId)
                    .build();

            final Call<SignUpResultData> requestSNSSignUpData = networkService.registerSNSModelData(signUpData);
            requestSNSSignUpData.enqueue(new Callback<SignUpResultData>() {
                @Override
                public void onResponse(Call<SignUpResultData> call, Response<SignUpResultData> response) {
                    if (response.isSuccessful()) {
                        final SignUpResultData resultData = response.body();
                        if (resultData.getMessage().equals("signup success")) {
                            SharedAccessor.register(ModelSNSSignUpActivity.this, resultData.getMemberToken(), resultData.getPosition(), nameEditText.getText().toString());
                            AppController.getInstance().resetPageStack();
                            startActivity(new Intent(ModelSNSSignUpActivity.this, MainActivity.class));
                        } else if(resultData.getMessage().equals("signup failure")){
                            if(resultData.getDetail().equals("duplicated sns code")) {
                                Toast.makeText(ModelSNSSignUpActivity.this, "이미 있는 계정", Toast.LENGTH_SHORT).show();
                            }else if(resultData.getDetail().equals("while making token")){
                                Toast.makeText(ModelSNSSignUpActivity.this, "토큰 발급 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<SignUpResultData> call, Throwable t) {}
            });
        } else {
        }
    }

    private boolean isClearFormat() {
        String nameString = nameEditText.getText().toString();
        String ageString = ageEditText.getText().toString();
        if (!FormatChecker.isStringRanged(nameString, 2, 4)) {
            Toast.makeText(this, "이름을 정확히 입력하였는지 확인하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!FormatChecker.isDecimal(ageString) || !FormatChecker.isStringRanged(ageString, 2, 2)) {
            Toast.makeText(this, "나이를 정확히 입력하였는지 확인하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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

    @Override
    public void onScreenClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ageEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(nameEditText.getWindowToken(), 0);
    }
}
