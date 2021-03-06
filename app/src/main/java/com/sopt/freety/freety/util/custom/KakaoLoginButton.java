package com.sopt.freety.freety.util.custom;

/**
 * Created by KYJ on 2017-07-02.
 */
/**
 * Copyright 2014 Daum Kakao Corp.
 *
 * Redistribution and modification in source or binary forms are not permitted without specific prior written permission. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.kakao.util.helper.StoryProtocol;
import com.kakao.util.helper.TalkProtocol;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.util.helper.StoryProtocol;
import com.kakao.util.helper.TalkProtocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Do not remove blow import line
import com.kakao.usermgmt.R;

/**
 * 로그인 버튼
 * </br>
 * 로그인 layout에 {@link com.kakao.usermgmt.LoginButton}을 선언하여 사용한다.
 * @author MJ
 */
public class KakaoLoginButton extends LoginButton {

    private int resourceId;

    public KakaoLoginButton(Context context) {
        super(context);
    }

    public KakaoLoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KakaoLoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
            Session.getCurrentSession().open(AuthType.KAKAO_ACCOUNT, (Activity)getContext());
        }
    }


    /**
     * 로그인 버튼 클릭시 세션을 오픈하도록 설정한다.
     */

 /*   @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
       // resourceId = getResources().getIdentifier(R.layout.);
       // kakao_layout = (RelativeLayout)findViewById(R.layout.);
       // inflate(getContent(),resourceId, this);
       // inflate(getContent(), R.layout.kakao_login_layout, this);
        setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 카톡 또는 카스가 존재하면 옵션을 보여주고, 존재하지 않으면 바로 직접 로그인창.


                //final List<AuthType> authTypes = getAuthTypes();
                //if(authTypes.size() == 1){
                    Session.getCurrentSession().open(AuthType.KAKAO_TALK, (Activity) getContent());
               // } else {
               //     Log.e("err","Kakao-Login not exist");
                    //onClickLoginButton(authTypes);
               // }
            }
        });
    }

    private List<AuthType> getAuthTypes() {
        final List<AuthType> availableAuthTypes = new ArrayList<AuthType>();
        if(TalkProtocol.existCapriLoginActivityInTalk(getContent(), Session.getCurrentSession().isProjectLogin())){
            availableAuthTypes.add(AuthType.KAKAO_TALK);
            availableAuthTypes.add(AuthType.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN);
        }
        if(StoryProtocol.existCapriLoginActivityInStory(getContent(), Session.getCurrentSession().isProjectLogin())){
            availableAuthTypes.add(AuthType.KAKAO_STORY);
        }
        availableAuthTypes.add(AuthType.KAKAO_ACCOUNT);

        final AuthType[] selectedAuthTypes = Session.getCurrentSession().getAuthTypes();
        availableAuthTypes.retainAll(Arrays.asList(selectedAuthTypes));

        // 개발자가 설정한 것과 available 한 타입이 없다면 직접계정 입력이 뜨도록 한다.
        if(availableAuthTypes.size() == 0){
            availableAuthTypes.add(AuthType.KAKAO_ACCOUNT);
        }
        return availableAuthTypes;
    }*/

   /* private void onClickLoginButton(final List<AuthType> authTypes){
        final List<com.kakao.usermgmt.LoginButton.Item> itemList = new ArrayList<com.kakao.usermgmt.LoginButton.Item>();
        if(authTypes.contains(AuthType.KAKAO_TALK)) {
            itemList.add(new com.kakao.usermgmt.LoginButton.Item(com.kakao.usermgmt.R.string.com_kakao_kakaotalk_account, com.kakao.usermgmt.R.drawable.kakaotalk_icon, AuthType.KAKAO_TALK));
        }
        if(authTypes.contains(AuthType.KAKAO_STORY)) {
            itemList.add(new com.kakao.usermgmt.LoginButton.Item(com.kakao.usermgmt.R.string.com_kakao_kakaostory_account, com.kakao.usermgmt.R.drawable.kakaostory_icon, AuthType.KAKAO_STORY));
        }
        if(authTypes.contains(AuthType.KAKAO_ACCOUNT)){
            itemList.add(new com.kakao.usermgmt.LoginButton.Item(com.kakao.usermgmt.R.string.com_kakao_other_kakaoaccount, com.kakao.usermgmt.R.drawable.kakaoaccount_icon, AuthType.KAKAO_ACCOUNT));
        }
        itemList.add(new com.kakao.usermgmt.LoginButton.Item(com.kakao.usermgmt.R.string.com_kakao_account_cancel, 0, null)); //no icon for this one

        final com.kakao.usermgmt.LoginButton.Item[] items = itemList.toArray(new com.kakao.usermgmt.LoginButton.Item[itemList.size()]);

        final ListAdapter adapter = new ArrayAdapter<com.kakao.usermgmt.LoginButton.Item>(
                getContent(),
                android.R.layout.select_dialog_item,
                android.R.id.text1, items){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView)v.findViewById(android.R.id.text1);

                tv.setText(items[position].textId);
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(15);
                tv.setGravity(Gravity.CENTER);
                if(position == itemList.size() -1) {
                    tv.setBackgroundResource(com.kakao.usermgmt.R.drawable.kakao_cancel_button_background);
                } else {
                    tv.setBackgroundResource(com.kakao.usermgmt.R.drawable.kakao_account_button_background);
                }
                tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0);

                int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
                tv.setCompoundDrawablePadding(dp5);

                return v;
            }
        };


        new AlertDialog.Builder(getContent())
                .updateData(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int position) {
                        final AuthType authType = items[position].authType;
                        if (authType != null) {
                            Session.getCurrentSession().open(authType, (Activity) getContent());
                        }

                        dialog.dismiss();
                    }
                }).create().show();

    }

    private static class Item {
        public final int textId;
        public final int icon;
        public final AuthType authType;
        public Item(final int textId, final Integer icon, final AuthType authType) {
            this.textId = textId;
            this.icon = icon;
            this.authType = authType;
        }
    }*/

}
