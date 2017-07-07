package com.sopt.freety.freety.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
        String type = pref.getString(Consts.PREF_POSITION, Consts.TYPE_MODEL);

        if (type.equals("")) {
            throw new RuntimeException("Position is not set yet");
        }
        return type.equals("designer");
    }

    public static String getImageURL(Context context) {

        SharedPreferences pref = context.getSharedPreferences(Consts.PREF_KEY, Context.MODE_PRIVATE);
        String url = pref.getString(Consts.PREF_IMAGE, "");
        return url;
    }

    public static String getName(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Consts.PREF_KEY, Context.MODE_PRIVATE);
        String url = pref.getString(Consts.PREF_NAME, "나");
        return url;
    }

    public static void reset(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Consts.PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Consts.PREF_TOKEN, "");
        editor.putString(Consts.PREF_POSITION, "");
        editor.apply();
        editor.commit();
    }

    public static void register(Context context, String token, String postiton, String name) {
        SharedPreferences pref = context.getSharedPreferences(Consts.PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Consts.PREF_TOKEN, token);
        editor.putString(Consts.PREF_POSITION, postiton);
        editor.putString(Consts.PREF_NAME, name);
        editor.apply();
        editor.commit();
    }

    public static void registerURL(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences(Consts.PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Consts.PREF_IMAGE, url);
        editor.apply();
        editor.commit();
    }
}

