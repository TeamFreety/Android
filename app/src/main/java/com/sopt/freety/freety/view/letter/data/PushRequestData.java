package com.sopt.freety.freety.view.letter.data;

/**
 * Created by cmslab on 7/6/17.
 */

public class PushRequestData {

    private int memberId;
    private String content;
    private String sentDate;

    public PushRequestData(int memberId, String content, String sentDate) {
        this.memberId = memberId;
        this.content = content;
        this.sentDate = sentDate;
    }
}
