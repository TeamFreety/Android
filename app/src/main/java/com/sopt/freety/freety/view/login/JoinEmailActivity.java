package com.sopt.freety.freety.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

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

public class JoinEmailActivity extends AppCompatActivity {

    @BindView(R.id.cancelBtn) Button cancelBtn;
    @BindView(R.id.submitBtn) Button submitBtn;
    @BindView(R.id.emailEditText) EditText emailEdit;
    @BindView(R.id.pwdEditText) EditText pwdEdit;
    @BindView(R.id.nameEditText) EditText nameEdit;
    @BindView(R.id.ageEditText) EditText ageEdit;
    @BindView(R.id.belongNameEditText) EditText belongNameEdit;
    @BindView(R.id.checkbox1) CheckBox checkBox1;
    @BindView(R.id.checkbox2) CheckBox checkBox2;
    @BindView(R.id.checkbox3) CheckBox checkBox3;
    @BindView(R.id.checkbox4) CheckBox checkBox4;
    @BindView(R.id.checkbox5) CheckBox checkBox5;
    @BindView(R.id.checkbox6) CheckBox checkBox6;
    @BindView(R.id.checkbox7) CheckBox checkBox7;
    @BindView(R.id.checkbox8) CheckBox checkBox8;
    @BindView(R.id.checkbox9) CheckBox checkBox9;

    private ConstraintLayout designerLayout;
    private String belongStr ="";
    private String careerStr ="";

    List<CompoundButton> buttonList = new ArrayList<>();
    List<CompoundButton> buttonList2 = new ArrayList<>();


    NetworkService service;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_email);

        designerLayout = (ConstraintLayout)findViewById(R.id.designerLayout);

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

    @OnClick({R.id.checkbox1,R.id.checkbox2, R.id.checkbox3, R.id.checkbox4, R.id.checkbox5})
    public void onCheckboxClick2(CompoundButton buttonView){
        if(buttonView.isChecked()) {
            buttonList.add(buttonView);
        }
        for (CompoundButton button : buttonList) {
            button.setChecked(false);
        }
        buttonView.setChecked(true);
        belongStr = buttonView.getText().toString();
    }

    @OnClick({R.id.checkbox6,R.id.checkbox7, R.id.checkbox8, R.id.checkbox9})
    public void onCheckboxClick(CompoundButton buttonView){
        if(buttonView.isChecked()) {
            buttonList2.add(buttonView);
        }
        for (CompoundButton button : buttonList2) {
            button.setChecked(false);
        }
        buttonView.setChecked(true);
        careerStr = buttonView.getText().toString();
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


}
