package com.sopt.freety.freety.view.firebase.chat.data;

/**
 * Created by cmslab on 6/25/17.
 */

public class ChatData {
    private int roomId;
    public String senderId;
    public String receiverId;
    public String message;
    public String time;

    public ChatData() {

    }
    public ChatData(int roomId, String senderId, String receiverId, String message, String time) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.time = time;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

}
