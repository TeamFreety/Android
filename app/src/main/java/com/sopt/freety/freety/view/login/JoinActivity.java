package com.sopt.freety.freety.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sopt.freety.freety.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KYJ on 2017-06-26.
 */

public class JoinActivity extends AppCompatActivity{

    @BindView(R.id.modelCaseBtn) Button modelCaseBtn;
    @BindView(R.id.designerCaseBtn) Button designerCaseBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_case);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.modelCaseBtn,R.id.designerCaseBtn})
    public void onButtonClick(View view){
        switch(view.getId()){
            case R.id.modelCaseBtn:
                Intent intent = new Intent(getApplicationContext(),JoinEmailActivity.class);
                intent.putExtra("case", true) ;
                startActivity(intent);
                break;
            case R.id.designerCaseBtn:
                Intent intent2 = new Intent(getApplicationContext(),JoinEmailActivity.class);
                intent2.putExtra("case", false) ;
                startActivity(intent2);
                break;
        }
    }
}
