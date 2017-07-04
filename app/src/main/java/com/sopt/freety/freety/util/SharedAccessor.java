package com.sopt.freety.freety.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cmslab on 7/2/17.
 */

public class SharedAccessor {

    public static String getToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Consts.PREF_KEY, Context.MODE_PRIVATE);
        return pref.getString(Consts.PREF_TOKEN, "");
    }

    public static boolean isDesigner(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Consts.PREF_KEY, Context.MODE_PRIVATE);
        String type = pref.getString(Consts.PREF_POSITION, "");

        if (type.equals("")) {
            throw new RuntimeException("Position is not set yet");
        }
        return type.equals("designer");
    }
}
