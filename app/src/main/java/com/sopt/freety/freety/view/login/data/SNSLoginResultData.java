package com.sopt.freety.freety.view.login.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.sopt.freety.freety.util.Consts;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 2017-07-05.
 */

public class SNSLoginResultData {
    private String message;
    private String member_token;
    private String position;

    public String getMessage() {
        return message;
    }
    public String getPosition() { return position; }

    public void registerToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Consts.PREF_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Consts.PREF_TOKEN, member_token);
        //TODO: resolve this
        if (position == null) {
            position = "model";
        }
        editor.putString(Consts.PREF_POSITION, position);
        editor.apply();
        editor.commit();
    }
}
