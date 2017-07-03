package com.sopt.freety.freety.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cmslab on 7/2/17.
 */

public class SharedAccessor {

    public static String getToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Consts.PREF_KEY, Context.MODE_PRIVATE);
        /*
        return pref.getString(Consts.PREF_TOKEN, "");
        */
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6NDIsImlhdCI6MTQ5OTA4MjEyMiwiZXhwIjoxNDk5MTY4NTIyfQ.NjF7MqFj7D1L7DYPEAb2IFMYoQWazD8RqudWEU2D7Jo";
    }

    public static boolean isDesigner(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Consts.PREF_KEY, Context.MODE_PRIVATE);
        String type = pref.getString(Consts.PREF_POSITION, Consts.TYPE_MODEL);

        if (type.equals("")) {
            throw new RuntimeException("Position is not set yet");
        }
        return true;
    }
}
