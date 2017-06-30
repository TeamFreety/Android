package com.sopt.freety.freety.view.home.data;

import com.sopt.freety.freety.R;

import java.util.Random;

/**
 * Created by KYJ on 2017-06-28.
 */

public class HomeContentsData {

    public String imageURL;
    public int imageSource;

    public String getImageURL() {
        return imageURL;
    }

    public int getImageSource() {
        return imageSource;
    }

    public static HomeContentsData getMockData() {
        return new HomeContentsData();
    }

    public static int getMockImageSource() {
        Random random = new Random();
        return random.nextInt() % 2 == 0 ? R.drawable.chat_list_elem : R.drawable.freety_logo;
    }

     public static String getMockURL() {
        Random random = new Random();
        return random.nextInt() % 2 == 0 ? "http://m.naver.com" : "http://m.google.com";
    }
}
