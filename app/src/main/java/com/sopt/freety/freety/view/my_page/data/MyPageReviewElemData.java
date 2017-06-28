package com.sopt.freety.freety.view.my_page.data;

import com.sopt.freety.freety.R;

import java.util.Random;

/**
 * Created by cmslab on 6/28/17.
 */

public class MyPageReviewElemData {

    public MyPageReviewElemData() {

    }

    private String title;
    private String date;
    private String content;
    private String imageURL;
    private int score;
    private String writer;

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public int getScore() {
        return score;
    }

    public String getWriter() {
        return writer;
    }

    public static String getMockTitle() {
        return "매직셋팅";
    }

    public static String getMockDate() {
        return "2017.06.23";
    }

    public static String getMockContent() {
        return "하 정말 원하던 디자인이 나와버렸어요!! good!";
    }

    public static int getMockScore() {
        return 4;
    }

    public static String getMockWriter() {
        return "김민주";
    }

    public static int getMockResource() {
        Random random = new Random();
        int caseNum = random.nextInt() % 3;
        switch (caseNum) {
            case 0:
                return 0;
            case 1:
                return R.drawable.chat_list_elem;
            case 2:
                return R.drawable.freety_logo;
        }
        return 0;
    }

    public static MyPageReviewElemData getMockElemData() {
        return new MyPageReviewElemData();
    }
}
