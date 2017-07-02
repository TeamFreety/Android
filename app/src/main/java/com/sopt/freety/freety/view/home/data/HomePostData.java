package com.sopt.freety.freety.view.home.data;

import com.sopt.freety.freety.R;

import java.util.Random;

/**
 * Created by cmslab on 6/27/17.
 */

public class HomePostData {

    public String imageURL;
    public int imageSource;
    public String address;
    public String style;

    public String getImageURL() {
        return imageURL;
    }

    public int getImageSource() {
        return imageSource;
    }

    public String getAddress() {
        return address;
    }

    public String getStyle() {
        return style;
    }

    public static int getMockSource() {
        Random random = new Random();
        return random.nextInt() % 2 == 0 ? R.drawable.chat_list_elem : R.drawable.freety_logo;
    }

    public static HomePostData getMockData() {
        return new HomePostData();
    }

    public static String getMockAddress() {
        Random random = new Random();
        return random.nextInt() % 2 == 0 ? "서울" : "오사카";
    }

    public static String getMockStyle() {
        Random random = new Random();
        return random.nextInt() % 2 == 0 ? "스핀스왈로 펌" : "앞머리 웨이브";
    }

}
