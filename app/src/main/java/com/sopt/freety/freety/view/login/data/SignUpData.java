package com.sopt.freety.freety.view.login.data;

/**
 * Created by cmslab on 6/30/17.
 */

public class SignUpData {

    private final String memberEmail;
    private final String memberPassword;
    private final String memberName;
    private final int memberAge;
    private final String memberBelong;
    private final String memberBelongName;
    private final String memberCareer;
    private final String memberKakaoCode;
    private final String memberFacebookCode;

    private SignUpData(Builder builder) {
        this.memberEmail = builder.getMemberEmail();
        this.memberPassword = builder.getMemberPassword();
        this.memberName = builder.getMemberName();
        this.memberAge = builder.getMemberAge();
        this.memberBelong = builder.getMemberBelong();
        this.memberBelongName = builder.getMemberBelongName();
        this.memberCareer = builder.getMemberCareer();
        this.memberKakaoCode = builder.getMemberKakaoCode();
        this.memberFacebookCode = builder.getMemberFacebookCode();
    }


    public static class Builder {

        // 무조건 있어야 하는 것들
        private final String memberName;
        private final int memberAge;

        private String memberEmail;
        private String memberPassword;
        private String memberBelong;
        private String memberBelongName;
        private String memberCareer;
        private String memberKakaoCode;
        private String memberFacebookCode;


        public Builder(final String memberName, final int memberAge) {
            this.memberName = memberName;
            this.memberAge = memberAge;
        }

        public Builder setMemberEmail(final String memberEmail) {
            this.memberEmail = memberEmail;
            return this;
        }

        public Builder setMemberPassword(final String memberPassword) {
            this.memberPassword = memberPassword;
            return this;
        }

        public Builder setMemberBelong(final String memberBelong) {
            this.memberBelong = memberBelong;
            return this;
        }

        public Builder setMemberBelongName(final String memberBelongName) {
            this.memberBelongName = memberBelongName;
            return this;
        }

        public Builder setMemberCareer(String memberCareer) {
            this.memberCareer = memberCareer;
            return this;
        }

        public Builder setMemberKakaoCode(String memberKakaoCode) {
            this.memberKakaoCode = memberKakaoCode;
            return this;
        }

        public Builder setMemberFacebookCode(String memberFacebookCode) {
            this.memberFacebookCode = memberFacebookCode;
            return this;
        }

        public SignUpData build() {
            return new SignUpData(this);
        }

        private String getMemberEmail() {
            return memberEmail;
        }

        private  String getMemberPassword() {
            return memberPassword;
        }

        private String getMemberName() {
            return memberName;
        }

        private int getMemberAge() {
            return memberAge;
        }

        private String getMemberBelong() {
            return memberBelong;
        }

        private String getMemberCareer() {
            return memberCareer;
        }

        private String getMemberBelongName() {
            return memberBelongName;
        }

        private String getMemberKakaoCode() {
            return memberKakaoCode;
        }

        private String getMemberFacebookCode() {
            return memberFacebookCode;
        }
    }
}
