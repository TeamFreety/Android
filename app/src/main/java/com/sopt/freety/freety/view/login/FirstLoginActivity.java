package com.sopt.freety.freety.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.main.MainActivity;

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

    // view
    private Button facebookBtn;
    private Button kakaoBtn;
    private Button emailBtn;
    private Button skipBtn;
    private TextView joinTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_first_login);
        callbackManager = CallbackManager.Factory.create();

        ButterKnife.bind(this);

        Button skipBtn = (Button)findViewById(R.id.skipBtn);
        Button emailBtn = (Button)findViewById(R.id.emailBtn);
        TextView joinTextView = (TextView)findViewById(R.id.joinTextView);


        LoginButton facebookBtn = (LoginButton) findViewById(R.id.facebookBtn);
        facebookBtn.setReadPermissions(Arrays.asList("public_profile", "email"));
        facebookBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result",object.toString());
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr",error.toString());
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick({R.id.emailBtn,R.id.skipBtn,R.id.joinTextView})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.emailBtn:
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.skipBtn:
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.joinTextView:
                Intent intent3 = new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
