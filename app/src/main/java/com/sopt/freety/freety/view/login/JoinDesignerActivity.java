package com.sopt.freety.freety.view.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.NetworkService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by KYJ on 2017-06-26.
 */

public class JoinDesignerActivity extends AppCompatActivity{

    @BindView(R.id.cancelBtn) Button cancelBtn;
    @BindView(R.id.submitBtn) Button submitBtn;
    @BindView(R.id.emailEditText) EditText emailEdit;
    @BindView(R.id.pwdEditText) EditText pwdEdit;
    @BindView(R.id.nameEditText) EditText nameEdit;
    @BindView(R.id.ageEditText) EditText ageEdit;
    @BindView(R.id.belongNameEditText) EditText belongNameEdit;

    NetworkService service;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_email_designer);

        ButterKnife.bind(this);

        service = AppController.getInstance().getNetworkService();

        mProgressDialog = new ProgressDialog(JoinDesignerActivity.this);
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
        if(emailEdit.length() == 0 || pwdEdit.length() == 0 || nameEdit.length() ==0 || ageEdit.length() == 0 || belongNameEdit.length() == 0 ){
            Toast.makeText(getApplicationContext(),"모든 항목을 기입해주세요.", Toast.LENGTH_SHORT).show();
        }
        else{

            mProgressDialog.show();

            RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), emailEdit.getText().toString());
            RequestBody pwd = RequestBody.create(MediaType.parse("multipart/form-data"), pwdEdit.getText().toString());
            RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), nameEdit.getText().toString());
            RequestBody age = RequestBody.create(MediaType.parse("multipart/form-data"), ageEdit.getText().toString());
            RequestBody belong = RequestBody.create(MediaType.parse("multipart/form-data"), belongEdit.getText().toString());
            RequestBody belongName = RequestBody.create(MediaType.parse("multipart/form-data"), belongNameEdit.getText().toString());
            RequestBody career = RequestBody.create(MediaType.parse("multipart/form-data"), careerEdit.getText().toString());


            MultipartBody.Part body;



                    /*
                    통신부는 따로 정리해드리겠습니다.
                    이번에는 post 메소드 입니다. body(이미지),writer,title,content 를 넘깁니다.
                     파일과 텍스트를 함께 넘길 때는 multipart를 사용합니다.
                     */
            Call<RegisterResult> requestImgNotice = service.registerImgNotice(email, pwd, name, age, belong, belongName, career);
            requestImgNotice.enqueue(new Callback<RegisterResult>() {
                @Override
                public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
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
                public void onFailure(Call<RegisterResult> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                    Log.i("myTag",t.toString());
                    mProgressDialog.dismiss();
                }
            });
        }
    }
}
