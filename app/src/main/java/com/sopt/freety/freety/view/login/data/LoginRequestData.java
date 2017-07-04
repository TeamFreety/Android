package com.sopt.freety.freety.view.login.data;

/**
 * Created by USER on 2017-07-05.
 */

public class LoginRequestData {

    private String memberEmail;
    private String memberPassword;

    public LoginRequestData(String memberEmail, String memberPassword) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }
}
