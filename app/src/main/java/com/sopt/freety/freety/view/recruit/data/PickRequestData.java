package com.sopt.freety.freety.view.recruit.data;

/**
 * Created by cmslab on 7/2/17.
 */

public class PickRequestData {

    private int postId;
    private int taskType;

    public PickRequestData(int postId, boolean isChecked) {
        this.postId = postId;
        this.taskType = isChecked ? 0 : 1;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }
}
