package com.sopt.freety.freety.view.my_page.data;

import com.sopt.freety.freety.R;

import java.util.Random;

/**
 * Created by cmslab on 6/28/17.
 */

public class MyPageReviewElemData {

    private String title;
    private String date;
    private String content;
    private String imageURL;
    private float score;
    private String writer;

    public MyPageReviewElemData(String title, String date,
                                String content, String imageURL,
                                float score, String writer) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.imageURL = imageURL;
        this.score = score;
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getImageURL() {
        return imageURL;
    }

    public float getScore() {
        return score;
    }

    public String getWriter() {
        return writer;
    }
}
