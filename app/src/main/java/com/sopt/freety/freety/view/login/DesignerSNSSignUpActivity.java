package com.sopt.freety.freety.view.login;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.util.util.FormatChecker;
import com.sopt.freety.freety.util.util.Pair;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DesignerSNSSignUpActivity extends AppCompatActivity implements LoginInterface {

    @BindView(R.id.sign_up_sns_designer_name_edit)
    EditText nameEditText;

    @BindView(R.id.sign_up_sns_designer_age_edit)
    EditText ageEditText;

    @BindView(R.id.sign_up_sns_designer_belong_spinner)
    Spinner belongSpinner;

    @BindView(R.id.sign_up_sns_designer_belong_selected)
    TextView belongSelectedText;

    @BindView(R.id.sign_up_sns_designer_belong_name_edit)
    EditText belongNameEditText;

    @BindView(R.id.sign_up_sns_designer_career_spinner)
    Spinner careerSpinner;

    @BindView(R.id.sign_up_sns_designer_career_selected)
    TextView careerSelectedText;

    @BindViews({R.id.sign_up_sns_designer_auth_checkbox1, R.id.sign_up_sns_designer_auth_checkbox2})
    List<CheckBox> checkBoxList;

    @BindView(R.id.sign_up_sns_designer_finish_btn)
    TextView finishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#f1f1f1"));
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_designer_sns_sign_up);
        ButterKnife.bind(this);
        initCheckBoxList();
        initSpinner(belongSpinner, R.array.belong, true);
        initSpinner(careerSpinner, R.array.career, false);
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

    @OnClick(R.id.sign_up_sns_designer_finish_btn)
    public void onFinishBtn() {
        if (isClearFormat()) {
            Toast.makeText(this, "통과하셨습니다! 통신구현 ㄱ", Toast.LENGTH_SHORT).show();
        } else {
        }
    }

    @OnClick(R.id.sign_up_sns_designer_belong_btn)
    public void onBelongBtn() {
        belongSpinner.performClick();
    }

    @OnClick(R.id.sign_up_sns_designer_career_btn)
    public void onCareerBtn() {
        careerSpinner.performClick();
    }

    private boolean isClearFormat() {
        String nameString = nameEditText.getText().toString();
        String ageString = ageEditText.getText().toString();

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

    public void onScreenClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ageEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(nameEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(belongNameEditText.getWindowToken(), 0);
    }

    @Override
    public void onBackBtn() {
        onBackPressed();
    }
}