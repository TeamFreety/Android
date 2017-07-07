package com.sopt.freety.freety.view.letter.data;

/**
 * Created by cmslab on 7/6/17.
 */

public class PushRequestData {

    private int memberId;
    private String content;
    private String serviceDate;

    public PushRequestData(int memberId, String content, String serviceDate) {
        this.memberId = memberId;
        this.content = content;
        this.serviceDate = serviceDate;
    }
}
