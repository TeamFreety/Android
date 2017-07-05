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

        public String getMemberName() {
            return memberName;
        }

        public String getMemberPhoto() {
            return memberPhoto;
        }
    }

    private class PickPostInfo {
        private int postId;
        private String postImg;
        private String title;
        private String place;
    }

    public String getMessage() {
        return message;
    }

    public ModelInfo getModelInfo() {
        return modelInfo;
    }

    public String getModelPhoto1() {
        return modelPhoto1;
    }

    public String getModelPhoto2() {
        return modelPhoto2;
    }

    public String getModelPhoto3() {
        return modelPhoto3;
    }

    public List<PickPostInfo> getModelPickList() {
        return modelPickList;
    }

    public String getModelPhoto() {
        return modelInfo.getMemberPhoto();
    }
    public String getModelName() {
        return modelInfo.getMemberName();
    }

}
