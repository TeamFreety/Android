package com.sopt.freety.freety.view.my_page.data.network;

import com.sopt.freety.freety.view.my_page.data.MyPageModelHeaderData;
import com.sopt.freety.freety.view.my_page.data.MyPagePickData;

import java.util.ArrayList;
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

        public int getPostId() {
            return postId;
        }

        public String getPostImg() {
            return postImg;
        }

        public String getTitle() {
            return title;
        }

        public String getPlace() {
            return place;
        }
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

    public List<MyPagePickData> getModelPickList() {
        List<MyPagePickData> list = new ArrayList<>();
        for (PickPostInfo pickPostInfo : modelPickList) {
            list.add(new MyPagePickData(pickPostInfo.getPostImg(), pickPostInfo.getPlace(), pickPostInfo.getTitle()));
        }
        return list;
    }

    public String getModelPhoto() {
        return modelInfo.getMemberPhoto();
    }
    public String getModelName() {
        return modelInfo.getMemberName();
    }

    public List<MyPageModelHeaderData> getMyPageModelHeaderDataList() {
        List<MyPageModelHeaderData> list = new ArrayList<>(3);
        list.add(new MyPageModelHeaderData(modelPhoto1));
        list.add(new MyPageModelHeaderData(modelPhoto2));
        list.add(new MyPageModelHeaderData(modelPhoto3));
        return list;
    }

}
