package com.sopt.freety.freety.view.login.data;

/**
 * Created by USER on 2017-07-05.
 */

public class SNSLoginRequestData {

    private String memberFacebookCode;
    private String memberKakaoCode;

    public String getMemberFacebookCode() {
        return memberFacebookCode;
    }

    public void setMemberFacebookCode(String memberFacebookCode) {
        this.memberFacebookCode = memberFacebookCode;
    }

    public String getMemberKakaoCode() {
        return memberKakaoCode;
    }

    public void setMemberKakaoCode(String memberKakaoCode) {
        this.memberKakaoCode = memberKakaoCode;
    }

    public SNSLoginRequestData(String memberFacebookCode, String memberKakaoCode) {
        this.memberFacebookCode = memberFacebookCode;
        this.memberKakaoCode = memberKakaoCode;

    }
}