package com.sopt.freety.freety.view.recruit.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.sopt.freety.freety.util.Consts;

/**
 * Created by cmslab on 7/2/17.
 */

public class PostDetailRequestData {

    private int postId;
    private String member_token;

    public PostDetailRequestData(Context context, int postId) {
        SharedPreferences pref = context.getSharedPreferences(Consts.PREF_KEY, Context.MODE_PRIVATE);
        this.postId = postId;
        this.member_token = pref.getString(Consts.PREF_TOKEN, "");
    }

    public int getPostId() {
        return postId;
    }

    public String getMember_token() {
        return member_token;
    }


}
