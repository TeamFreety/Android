package com.sopt.freety.freety.view.letter.data;

/**
 * Created by cmslab on 6/25/17.
 */

public class LetterRoomData {

    private int otherId;
    private String imageURL;
    private int notifCount;
    private String otherName;
    private String date;
    private String lastMsg;

    public LetterRoomData(final int otherId, final String imageURL, final int numUnRead, final String name, final String date, final String lastMsg) {
        this.otherId = otherId;
        this.imageURL = imageURL;
        this.notifCount = numUnRead;
        this.otherName = name;
        this.date = date;
        this.lastMsg = lastMsg;
    }

    public int getOtherId() {
        return otherId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getNotifCount() {
        return notifCount;
    }

    public String getOtherName() {
        return otherName;
    }

    public String getDate() {
        return date;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setOtherId(int otherId) {
        this.otherId = otherId;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setNotifCount(int notifCount) {
        this.notifCount = notifCount;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }
}
