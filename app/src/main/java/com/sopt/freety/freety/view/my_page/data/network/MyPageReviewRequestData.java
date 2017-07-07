package com.sopt.freety.freety.view.my_page.data.network;

/**
 * Created by cmslab on 7/5/17.
 */

public class MyPageReviewRequestData {

    private final int memberId;
    private final int score;
    private final String title;
    private final String content;

    private MyPageReviewRequestData(Builder builder){
        this.memberId = builder.getMemberId();
        this.score = builder.getScore();
        this.title = builder.getTitle();
        this.content = builder.getContent();
    }

    public static class Builder{

        private final int memberId;
        private final int score;
        private final String title;
        private final String content;

        public Builder(final int memberId, final int score, final String title, final String content) {
            this.memberId = memberId;
            this.score = score;
            this.title = title;
            this.content = content;
        }

        public int getMemberId() {
            return memberId;
        }

        public int getScore() {
            return score;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }


        public MyPageReviewRequestData build(){ return new MyPageReviewRequestData(this); }
    }
}
