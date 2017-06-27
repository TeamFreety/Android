package com.sopt.freety.freety.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.network.NetworkService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KYJ on 2017-06-26.
 */

public class JoinEmailActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    @BindView(R.id.cancelBtn) Button cancelBtn;
    @BindView(R.id.submitBtn) Button submitBtn;
    @BindView(R.id.emailEditText) EditText emailEdit;
    @BindView(R.id.pwdEditText) EditText pwdEdit;
    @BindView(R.id.nameEditText) EditText nameEdit;
    @BindView(R.id.ageEditText) EditText ageEdit;
    @BindView(R.id.belongNameEditText) EditText belongNameEdit;

    ConstraintLayout designerLayout;
    public String belongStr ="";
    public String careerStr ="";
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    CheckBox checkBox7;
    CheckBox checkBox8;
    CheckBox checkBox9;
    int check1=0;
    int check2=0;

    @BindView(R.id.belongTextView) TextView belongTextView;
    @BindView(R.id.belongNameTextView) TextView belongNameTextView;
    @BindView(R.id.careerTextView) TextView careerTextView;


    NetworkService service;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_email);

        designerLayout = (ConstraintLayout)findViewById(R.id.designerLayout);

        checkBox1 = (CheckBox)findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox)findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox)findViewById(R.id.checkbox3);
        checkBox4 = (CheckBox)findViewById(R.id.checkbox4);
        checkBox5 = (CheckBox)findViewById(R.id.checkbox5);
        checkBox6 = (CheckBox)findViewById(R.id.checkbox6);
        checkBox7 = (CheckBox)findViewById(R.id.checkbox7);
        checkBox8 = (CheckBox)findViewById(R.id.checkbox8);
        checkBox9 = (CheckBox)findViewById(R.id.checkbox9);

        checkBox1.setOnCheckedChangeListener(JoinEmailActivity.this);
        checkBox2.setOnCheckedChangeListener(JoinEmailActivity.this);
        checkBox3.setOnCheckedChangeListener(JoinEmailActivity.this);
        checkBox4.setOnCheckedChangeListener(JoinEmailActivity.this);
        checkBox5.setOnCheckedChangeListener(JoinEmailActivity.this);
        checkBox6.setOnCheckedChangeListener(JoinEmailActivity.this);
        checkBox7.setOnCheckedChangeListener(JoinEmailActivity.this);
        checkBox8.setOnCheckedChangeListener(JoinEmailActivity.this);
        checkBox9.setOnCheckedChangeListener(JoinEmailActivity.this);

        Intent intent = getIntent();
        boolean i = intent.getBooleanExtra("case", false);
        if (i) {
            designerLayout.setVisibility(View.GONE);
        }
        else {

        }


        ButterKnife.bind(this);

        //service = AppController.getInstance().getNetworkService();

        mProgressDialog = new ProgressDialog(JoinEmailActivity.this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("회원 가입 중...");
        mProgressDialog.setIndeterminate(true);


    }


    @OnClick({R.id.submitBtn,R.id.cancelBtn})
    public void onButtonClick(View view){
        switch(view.getId()){
            case R.id.cancelBtn:
                super.onBackPressed();
                break;
            case R.id.submitBtn:
                join(view);
                break;
        }
    }

    public void join(View v) {
        if(emailEdit.length() == 0 || pwdEdit.length() == 0 || nameEdit.length() ==0 || ageEdit.length() == 0 ){
            Toast.makeText(getApplicationContext(),"모든 항목을 기입해주세요.", Toast.LENGTH_SHORT).show();
        }
        else{

            mProgressDialog.show();

            RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), emailEdit.getText().toString());
            RequestBody pwd = RequestBody.create(MediaType.parse("multipart/form-data"), pwdEdit.getText().toString());
            RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), nameEdit.getText().toString());
            RequestBody age = RequestBody.create(MediaType.parse("multipart/form-data"), ageEdit.getText().toString());
            RequestBody belong = RequestBody.create(MediaType.parse("multipart/form-data"), belongStr);
            RequestBody belongName = RequestBody.create(MediaType.parse("multipart/form-data"), belongNameEdit.getText().toString());
            RequestBody career = RequestBody.create(MediaType.parse("multipart/form-data"), careerStr);



            Call<JoinResult> requestPersonData = service.registerPersonData(email, pwd, name, age, belong, belongName, career);
            requestPersonData.enqueue(new Callback<JoinResult>() {
                @Override
                public void onResponse(Call<JoinResult> call, Response<JoinResult> response) {
                    if (response.isSuccessful()){

                        if(response.body().message.equals("save")){
                            finish();
                            mProgressDialog.dismiss();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<JoinResult> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                    Log.i("myTag",t.toString());
                    mProgressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        //if문 순서 때문에 2선택 후 3 선택하면 두번째 if문에서 걸려 값이 변하지 않음

            if (checkBox1.isChecked()) {
                if(check1!=1) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    belongStr = checkBox1.getText().toString();
                    check1=1;
                }
            }

            if (checkBox2.isChecked()) {
                if(check1!=1) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    belongStr = checkBox2.getText().toString();
                    check1=1;
                }
            }
            if (checkBox3.isChecked()) {
                if(check1!=1) {
                    checkBox2.setChecked(false);
                    checkBox1.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    belongStr = checkBox3.getText().toString();
                    check1=1;
                }
            }
            if (checkBox4.isChecked()) {
                if(check1!=1) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox1.setChecked(false);
                    checkBox5.setChecked(false);
                    belongStr = checkBox4.getText().toString();
                    check1=1;
                }
            }
            if (checkBox5.isChecked()) {
                if(check1!=1) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox1.setChecked(false);
                    belongStr = checkBox5.getText().toString();
                    check1=1;
                }
            }
            if (checkBox6.isChecked()) {
                if(check2!=1) {
                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    careerStr = checkBox6.getText().toString();
                    check2=1;
                }
            }
            if (checkBox7.isChecked()) {
                if(check2!=1) {
                    checkBox6.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    careerStr = checkBox7.getText().toString();
                    check2=1;
                }
            }
            if (checkBox8.isChecked()) {
                if(check2!=1) {
                    checkBox7.setChecked(false);
                    checkBox6.setChecked(false);
                    checkBox9.setChecked(false);
                    careerStr = checkBox8.getText().toString();
                    check2=1;
                }
            }
            if (checkBox9.isChecked()) {
                if(check2!=1) {
                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox6.setChecked(false);
                    careerStr = checkBox9.getText().toString();
                    check2=1;
                }
            }


    }
}
