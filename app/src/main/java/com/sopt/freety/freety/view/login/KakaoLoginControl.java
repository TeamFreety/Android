package com.sopt.freety.freety.view.login;

import android.app.Activity;
import android.content.Context;

import com.facebook.login.widget.LoginButton;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by cmslab on 7/6/17.
 */

public class KakaoLoginControl extends LoginButton {

    public KakaoLoginControl(Context context) {
        super(context);
    }

    public void call() {
        try {
            Method methodGetAuthTypes = LoginButton.class.getDeclaredMethod("getAuthTypes");
            methodGetAuthTypes.setAccessible(true);
            final List<AuthType> authTypes = (List<AuthType>) methodGetAuthTypes.invoke(this);
            if(authTypes.size() == 1){
                Session.getCurrentSession().open(authTypes.get(0), (Activity) getContext());
            } else {
                Method methodOnClickLoginButton = LoginButton.class.getDeclaredMethod("onClickLoginButton",List.class);
                methodOnClickLoginButton.setAccessible(true);
                methodOnClickLoginButton.invoke(this,authTypes);
            }
        } catch (Exception e) {
            Session.getCurrentSession().open(AuthType.KAKAO_ACCOUNT, (Activity) getContext());
        }
    }
}
