package com.sopt.freety.freety.view.my_page.data;

import com.sopt.freety.freety.R;

import java.util.Random;

/**
 * Created by cmslab on 6/27/17.
 */

public class MyPagePostData {

    private int postId;
    private String imageURL;
    private String address;
    private String title;

    public MyPagePostData(int postId, String imageURL, String address, String title) {
        this.postId = postId;
        this.imageURL = imageURL;
        this.address = address;
        this.title = title;
    }

    public int getPostId() {
        return postId;
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
