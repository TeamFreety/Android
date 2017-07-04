package com.sopt.freety.freety.view.my_page.data.network;

import java.util.List;

/**
 * Created by cmslab on 7/2/17.
 */

public class MyPageModelGetData {
    private String message;
    private ModelInfo modelInfo;
    private String modelPhoto1;
    private String modelPhoto2;
    private String modelPhoto3;
    private List<PickPostInfo> modelPickList;

    private class ModelInfo {
        private String memberName;
        private String memberPhoto;
    }

    private class PickPostInfo {
        private int postId;
        private String postImg;
        private String title;
        private String place;
    }

}
