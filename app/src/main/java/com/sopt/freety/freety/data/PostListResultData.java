package com.sopt.freety.freety.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmslab on 7/3/17.
 */

public class PostListResultData {

    private String message;
    private PostListData result;

    private class PostListData {
        private int postSize;
        private List<PostInfo> posts;

        public List<PostInfo> getPosts() {
            return posts;
        }
    }

    private class PostInfo {
        private PostLocationInfo postLocationInfo;
        private int postId;
        private String postImg;
        private String postTitle;

        public int getPostId() {
            return postId;
        }

        public String getPostImg() {
            return postImg;
        }

        public String getPostTitle() {
            return postTitle;
        }

        public PostLocationInfo getLoacation() {
            return postLocationInfo;
        }
    }

    private class PostLocationInfo {
        private String fullAddress;
        private String sido;
        private String sigugun;
        private String dong;
        private String detail;

        public String getAddress() {
            return sigugun;
        }
    }

    public String getMessage() {
        return message;
    }

    public List<com.sopt.freety.freety.data.PostListData> getPostList() {
        List<com.sopt.freety.freety.data.PostListData> postList = new ArrayList<>();
        for (PostInfo postInfo : result.getPosts()) {
            int postId = postInfo.getPostId();
            String imageURL = postInfo.getPostImg();
            String title = postInfo.getPostTitle();
            String address = postInfo.getLoacation().getAddress();
            postList.add(new com.sopt.freety.freety.data.PostListData(postId, imageURL, address, title));
        }
        return postList;
    }
}
