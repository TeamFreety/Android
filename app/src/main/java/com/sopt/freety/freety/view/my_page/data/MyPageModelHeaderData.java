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
    public int imageSource;

  public int getImageSource() {
      return imageSource;
  }

  public static int getMockSource() {
      Random random = new Random();
      return random.nextInt() % 2 == 0 ? R.drawable.chat_list_elem : R.drawable.freety_logo;
  }

    public static MyPageModelHeaderData getMockData() {
        return new MyPageModelHeaderData();
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(java.lang.String imageURL) {
        this.imageURL = imageURL;
    }
}
