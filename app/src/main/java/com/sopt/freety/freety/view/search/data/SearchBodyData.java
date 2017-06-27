package com.sopt.freety.freety.view.search.data;

/**
 * Created by USER on 2017-06-27.
 */

public class SearchBodyData {

    private String imageURL;

    public String getImageURL() {
        return imageURL;
    }

    public static SearchBodyData getMockData() {
        return new SearchBodyData();
    }
}
