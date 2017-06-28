package com.sopt.freety.freety.view.my_page.data;

import com.sopt.freety.freety.R;

import java.util.List;
import java.util.Random;

/**
 * Created by cmslab on 6/27/17.
 */

public class MyPageReviewData {

    private float totalScore;
    private List<MyPageReviewElemData> elemDataList;

    public MyPageReviewData(List<MyPageReviewElemData> elemDataList, float totalScore) {
        this.elemDataList = elemDataList;
        this.totalScore = totalScore;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public List<MyPageReviewElemData> getElemDataList() {
        return elemDataList;
    }

}
