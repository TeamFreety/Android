package com.sopt.freety.freety.view.login;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.util.FormatChecker;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModelSNSSignUpActivity extends AppCompatActivity implements LoginInterface {

    @BindView(R.id.sign_up_sns_model_name_edit)
    EditText nameEditText;

    @BindView(R.id.sign_up_sns_model_age_edit)
    EditText ageEditText;

    @BindViews({R.id.sign_up_sns_model_auth_checkbox1, R.id.sign_up_sns_model_auth_checkbox2})
    List<CheckBox> checkBoxList;

    @BindView(R.id.sign_up_sns_model_finish_btn)
    TextView finishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#f1f1f1"));
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_model_sns_sign_up);
        ButterKnife.bind(this);
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
            Toast.makeText(this, "통과하셨습니다! 통신구현 ㄱ", Toast.LENGTH_SHORT).show();
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
    public void onBackBtn() {
        onBackPressed();
    }
}
