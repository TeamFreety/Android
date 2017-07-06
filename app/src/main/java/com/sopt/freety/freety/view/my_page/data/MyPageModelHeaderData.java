package com.sopt.freety.freety.view.my_page.data;

import android.media.Image;

import com.sopt.freety.freety.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by cmslab on 6/26/17.
 */

public class MyPageModelHeaderData{

    public String imageURL;

    public MyPageModelHeaderData(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(java.lang.String imageURL) {
        this.imageURL = imageURL;
    }
}
