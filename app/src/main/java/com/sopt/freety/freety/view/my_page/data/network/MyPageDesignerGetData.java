package com.sopt.freety.freety.view.my_page.data.network;

import com.sopt.freety.freety.util.util.DateParser;
import com.sopt.freety.freety.view.my_page.data.MyPagePostData;
import com.sopt.freety.freety.view.my_page.data.MyPageReviewData;
import com.sopt.freety.freety.view.my_page.data.MyPageReviewElemData;
import com.sopt.freety.freety.view.my_page.data.MyPageStyleBodyData;
import com.sopt.freety.freety.view.my_page.data.MyPageStyleHeaderData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmslab on 7/2/17.
 */

public class MyPageDesignerGetData {

    private String message;
    private DesignerInfo designerInfo;
    private List<DesignerPostInfo> designerPostList;
    private List<PortFolioPhoto> designerPFPhoto;
    private List<DesignerCommentPost> designerCommentPostList;
    private String designerMsg;
    private String designerCareerText;

    public String getMessage() {
        return message;
    }

    private class DesignerInfo {
        private String memberPhoto;
        private String statusMsg;
        private String memberName;
        private float agvScore;

        public String getMemberPhoto() {
            return memberPhoto;
        }

        public String getStatusMsg() {
            return statusMsg;
        }

        public String getMemberName() {
            return memberName;
        }

        public float getAgvScore() {
            return agvScore;
        }
    }

    private class DesignerPostInfo {
        private int postId;
        private String noticePhoto;
        private String servicePlace;
        private String postTitle;

        public int getPostId() {
            return postId;
        }

        public String getNoticePhoto() {
            return noticePhoto;
        }

        public String getServicePlace() {
            return servicePlace;
        }

        public String getPostTitle() {
            return postTitle;
        }
    }

    private class PortFolioPhoto {
        private String PFPhoto;

        public String getPhotoURL() {
            return PFPhoto;
        }
    }

    private class DesignerCommentPost {
        private String writerName;
        private String title;
        private String content;
        private String commentPhoto;
        private float score;
        private String writtenTime;

        public MyPageReviewElemData getMyPageReviewElemData() throws ParseException {
            String parsedDate = DateParser.toPrettyFormat(writtenTime);
            return new MyPageReviewElemData(title, parsedDate, content, commentPhoto, score, writerName);
        }
    }

    public String getDesignerImageURL() {
        return designerInfo.getMemberPhoto();
    }

    public String getDesignerName() {
        return designerInfo.getMemberName();
    }

    public String getDesignerStatusMsg() {
        return designerInfo.getStatusMsg();
    }

    public List<MyPagePostData> getMyPagePostDataList() {
        List<MyPagePostData> postDataList = new ArrayList<>();
        for (DesignerPostInfo designerPostInfo : designerPostList) {
            int postId = designerPostInfo.getPostId();
            String imageURL = designerPostInfo.getNoticePhoto();
            String address = designerPostInfo.getServicePlace();
            String postTitle = designerPostInfo.getPostTitle();
            MyPagePostData info = new MyPagePostData(postId, imageURL, address, postTitle);
            postDataList.add(info);
        }
        return postDataList;
    }

    public List<MyPageStyleBodyData> getMyStyleBodyDataList() {
        List<MyPageStyleBodyData> list = new ArrayList<>();
        for (PortFolioPhoto portFolioPhoto : designerPFPhoto) {
            list.add(new MyPageStyleBodyData(portFolioPhoto.getPhotoURL()));
        }
        return list;
    }

    public MyPageStyleHeaderData getMyPageStyleHeaderData() {
        return new MyPageStyleHeaderData(designerCareerText);
    }

    public MyPageReviewData getMyPageReviewData() throws ParseException {
        List<MyPageReviewElemData> myPageReviewElemDataList = new ArrayList<>();
        for (DesignerCommentPost designerCommentPost : designerCommentPostList) {
            myPageReviewElemDataList.add(designerCommentPost.getMyPageReviewElemData());
        }
        return new MyPageReviewData(myPageReviewElemDataList, designerInfo.getAgvScore());
    }

}
