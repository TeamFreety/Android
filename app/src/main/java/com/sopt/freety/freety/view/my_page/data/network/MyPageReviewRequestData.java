package com.sopt.freety.freety.view.my_page.data.network;

/**
 * Created by cmslab on 7/5/17.
 */

public class MyPageReviewRequestData {

    private String memberId;
    private int score;
    private String title;
    private String content;

    public MyPageReviewRequestData(String memberId, int score, String title, String content) {
        this.memberId = memberId;
        this.score = score;
        this.title = title;
        this.content = content;
    }
}
