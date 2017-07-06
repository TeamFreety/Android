package com.sopt.freety.freety.view.my_page.data;

/**
 * Created by cmslab on 6/27/17.
 */

public class MyPagePickData {

    public String imageURL;
    public String address;
    public String title;

    public MyPagePickData(String imageURL, String address, String title) {
        this.imageURL = imageURL;
        this.address = address;
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getAddress() {
        return address;
    }

    public String getTitle() {
        return title;
    }

}
