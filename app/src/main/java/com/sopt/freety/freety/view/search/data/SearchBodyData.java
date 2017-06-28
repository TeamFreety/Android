package com.sopt.freety.freety.view.search.data;

import com.sopt.freety.freety.R;

import java.util.Random;

/**
 * Created by USER on 2017-06-27.
 */

public class SearchBodyData {

    private int img_search_hair;
    private String address;
    private String style;

       public int getImg_search_hair() {
        return img_search_hair;
    }

    public String getAddress() {
        return address;
    }

    public String getStyle() {
        return style;
    }

    public static int getMockSources() {
        Random random = new Random();
        return random.nextInt() % 2 == 0 ? R.drawable.chat_list_elem : R.drawable.freety_logo;
    }

    public static SearchBodyData getMockDatas() {
        return new SearchBodyData();
    }

    public static String getMockAddresss() {
        Random random = new Random();
        return random.nextInt() % 2 == 0 ? "서울" : "부산";
    }

    public static String getMockStyles() {
        Random random = new Random();
        return random.nextInt() % 2 == 0 ? "울프컷" : "울프샤기컷";
    }
}
