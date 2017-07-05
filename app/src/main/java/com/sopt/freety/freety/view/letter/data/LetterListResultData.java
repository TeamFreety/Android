package com.sopt.freety.freety.view.letter.data;

import com.sopt.freety.freety.util.util.DateParser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmslab on 7/5/17.
 */

public class LetterListResultData {

    private String message;
    private List<LetterRoomList> roomList;

    private class LetterRoomList {
        private LetterSenderInfo senderInfo;
        private List<LetterMessageData> messageList;

        public LetterSenderInfo getSenderInfo() {
            return senderInfo;
        }

        public List<LetterMessageData> getMessageList() {
            return messageList;
        }
    }

    private class LetterSenderInfo {
        private int senderId;
        private String senderName;
        private String senderPhoto;

        public int getSenderId() {
            return senderId;
        }

        public String getSenderName() {
            return senderName;
        }

        public String getSenderPhoto() {
            return senderPhoto;
        }
    }

    private class LetterMessageData {
        private String messageContent;
        private String messageDate;

        public String getContent() {
            return messageContent;
        }

        public String getDate() {
            return messageDate;
        }
    }

    public String getMessage() {
        return message;
    }

    public List<LetterRoomData> getRoomList() {
        List<LetterRoomData> result = new ArrayList<>();
        for (LetterRoomList room : roomList) {
            int otherId = room.getSenderInfo().getSenderId();
            String otherName = room.getSenderInfo().getSenderName();
            String imageURL = room.getSenderInfo().getSenderPhoto();
            int notifCount = room.getMessageList().size();
            String lastMsg = room.getMessageList().get(notifCount - 1).getContent();
            String date = DateParser.toPrettyFormat(room.getMessageList().get(notifCount - 1).getDate());
            result.add(new LetterRoomData(otherId, imageURL, notifCount, otherName, date, lastMsg));
        }
        return result;
    }

    public List<LetterData> getLetterDataList() {
        List<LetterData> result = new ArrayList<>();
        int messageSize = roomList.get(0).getMessageList().size();
        for (int i = 0; i < messageSize; i++) {
            String name = roomList.get(0).getSenderInfo().getSenderName();
            String imageURL = roomList.get(0).getSenderInfo().getSenderPhoto();
            String content = roomList.get(0).getMessageList().get(i).getContent();
            String date = DateParser.toPrettyFormat(roomList.get(0).getMessageList().get(i).getDate());
            boolean isMine = false;
            result.add(new LetterData(name, imageURL, content, date, isMine));
        }
        return result;
    }

    public String getMemberName() {
        return roomList.get(0).getSenderInfo().getSenderName();
    }

    public String getImageURL() {
        return roomList.get(0).getSenderInfo().getSenderPhoto();
    }

    public int getMemberId() {
        return roomList.get(0).getSenderInfo().getSenderId();
    }

}
