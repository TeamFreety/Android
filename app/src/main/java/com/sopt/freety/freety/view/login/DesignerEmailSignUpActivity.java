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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.util.Consts;
import com.sopt.freety.freety.util.util.FormatChecker;
import com.sopt.freety.freety.view.login.data.DuplicateData;
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

public class DesignerEmailSignUpActivity extends AppCompatActivity implements ScreenClickable{


    @BindView(R.id.sign_up_email_designer_email)
    EditText emailEditText;

    @BindView(R.id.sign_up_email_designer_email_alert)
    TextView emailAlertText;

    @BindView(R.id.sign_up_email_designer_password)
    EditText passwordEditText;

    @BindView(R.id.sign_up_email_designer_password_rep)
    EditText passwordRepEditText;

    @BindView(R.id.sign_up_email_designer_name_edit)
    EditText nameEditText;

    @BindView(R.id.sign_up_email_designer_age_edit)
    EditText ageEditText;

    @BindView(R.id.sign_up_email_designer_belong_spinner)
    Spinner belongSpinner;

    @BindView(R.id.sign_up_email_designer_belong_selected)
    TextView belongSelectedText;

    @BindView(R.id.sign_up_email_designer_belong_name_edit)
    EditText belongNameEditText;

    @BindView(R.id.sign_up_email_designer_career_spinner)
    Spinner careerSpinner;

    @BindView(R.id.sign_up_email_designer_career_selected)
    TextView careerSelectedText;

    @BindViews({R.id.sign_up_email_designer_auth_checkbox1, R.id.sign_up_email_designer_auth_checkbox2})
    List<CheckBox> checkBoxList;

    @BindView(R.id.sign_up_email_designer_finish_btn)
    TextView finishBtn;

    @BindView(R.id.sign_up_email_designer_address_rep_btn)
    Button addressRepBtn;

    @OnClick(R.id.sign_up_email_designer_back_btn)
    public void onBackBtn() {
        onBackPressed();
    }

    private boolean isAddressRepititionPassed;
    private NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#f1f1f1"));
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_designer_email_sign_up);
        ButterKnife.bind(this);
        networkService = AppController.getInstance().getNetworkService();
        initCheckBoxList();
        initSpinner(belongSpinner, R.array.belong, true);
        initSpinner(careerSpinner, R.array.career, false);
        finishBtn.setClickable(false);
        isAddressRepititionPassed = false;
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

    private void initSpinner(final Spinner spinner, int resources, final boolean isBelong) {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, resources,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spiner_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isBelong) {
                    String belong = (String)parent.getItemAtPosition(position);
                    belongSelectedText.setText(belong);
                } else {
                    String career = (String)parent.getItemAtPosition(position);
                    careerSelectedText.setText(career);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @OnClick(R.id.sign_up_email_designer_finish_btn)
    public void onFinishBtn() {
        if (isClearFormat()) {
            final SignUpData signUpData = new SignUpData.Builder(nameEditText.getText().toString(), Integer.parseInt(ageEditText.getText().toString()))
                    .setMemberEmail(emailEditText.getText().toString())
                    .setMemberPassword(passwordEditText.getText().toString())
                    .setMemberBelong(belongSelectedText.getText().toString())
                    .setMemberBelongName(belongNameEditText.getText().toString())
                    .setMemberCareer(careerSelectedText.getText().toString())
                    .build();

            final Call<SignUpResultData> requestSignUpData = networkService.registerDesignerData(signUpData);
            requestSignUpData.enqueue(new Callback<SignUpResultData>() {
                @Override
                public void onResponse(Call<SignUpResultData> call, Response<SignUpResultData> response) {
                    if (response.isSuccessful()) {
                        final SignUpResultData resultData = response.body();
                        if (resultData.getMessage().equals("signup success")) {
                            SharedPreferences pref = getSharedPreferences(Consts.PREF_KEY, MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString(Consts.PREF_TOKEN, resultData.getMemberToken());
                            editor.putString(Consts.PREF_POSITION, resultData.getPosition());
                            editor.apply();
                            editor.commit();
                            AppController.getInstance().resetPageStack();
                            startActivity(new Intent(DesignerEmailSignUpActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(DesignerEmailSignUpActivity.this, "메세지 내용이 뭔가 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SignUpResultData> call, Throwable t) {

                }
            });
        }
    }

    @OnClick(R.id.sign_up_email_designer_belong_btn)
    public void onBelongBtn() {
        belongSpinner.performClick();
    }

    @OnClick(R.id.sign_up_email_designer_career_btn)
    public void onCareerBtn() {
        careerSpinner.performClick();
    }

    private boolean isClearFormat() {
        String nameString = nameEditText.getText().toString();
        String ageString = ageEditText.getText().toString();

        if (!FormatChecker.isEmailFormat(emailEditText.getText().toString())) {
            Toast.makeText(this, "이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isAddressRepititionPassed) {
            Toast.makeText(this, "이메일 중복확인을 확인하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!passwordEditText.getText().toString().equals(passwordRepEditText.getText().toString())) {
            Toast.makeText(this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // check 이름
        if (!FormatChecker.isStringRanged(nameString, 2, 4)) {
            Toast.makeText(this, "이름을 정확히 입력하였는지 확인하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // check 나이
        if (!FormatChecker.isDecimal(ageString) || !FormatChecker.isStringRanged(ageString, 2, 2)) {
            Toast.makeText(this, "나이를 정확히 입력하였는지 확인하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 소속 및 경력 선택 여부
        if (belongSelectedText.getText().toString().length() == 0|| careerSelectedText.getText().toString().length() == 0) {
            Toast.makeText(this, "소속 및 경력을 정확히 입력하였는지 확인하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onScreenClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ageEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(nameEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(belongNameEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(emailEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordRepEditText.getWindowToken(), 0);
    }

    @OnClick(R.id.sign_up_email_designer_address_rep_btn)
    public void onRepititionCheck() {
        Call<DuplicateData> requestDuplicate = networkService.checkDuplicate(emailEditText.getText().toString());
        requestDuplicate.enqueue(new Callback<DuplicateData>() {
            @Override
            public void onResponse(Call<DuplicateData> call, Response<DuplicateData> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("duplicated")) {
                        emailAlertText.setText("이메일이 중복됩니다. 다시 입력해주세요.");
                        isAddressRepititionPassed = false;
                    } else if (response.body().getMessage().equals("no duplication")) {
                        emailAlertText.setText("사용가능한 이메일입니다.");
                        isAddressRepititionPassed = true;
                    } else {
                        throw new RuntimeException("unexpected message");
                    }
                }
            }

            @Override
            public void onFailure(Call<DuplicateData> call, Throwable t) {

            }
        });
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
