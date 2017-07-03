package com.sopt.freety.freety.data;

import com.sopt.freety.freety.R;

import java.util.Random;

/**
 * Created by cmslab on 6/27/17.
 */

public class PostListData {

    public int postId;
    public String imageURL;
    public String address;
    public String title;

    public PostListData(int postId, String imageURL, String address, String title) {
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
