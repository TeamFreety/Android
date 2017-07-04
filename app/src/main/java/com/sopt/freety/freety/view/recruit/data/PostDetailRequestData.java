package com.sopt.freety.freety.view.recruit.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.sopt.freety.freety.util.Consts;

/**
 * Created by cmslab on 7/2/17.
 */

public class PostDetailRequestData {

    private int postId;

    public PostDetailRequestData(int postId) {
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
    }

}
