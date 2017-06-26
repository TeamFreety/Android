package com.sopt.freety.freety.view.firebase.chat.data;

/**
 * Created by cmslab on 6/25/17.
 */

public class ChatListData {

    private final int roomId;
    private final int imgSource;
    private final int notifCount;
    private final String otherId;
    private final String date;
    private final String lastMsg;

    public ChatListData(final int roomId, final int imsSource,
                        final int numUnRead, final String name,
                        final String date, final String lastMsg) {
        this.roomId = roomId;
        this.imgSource = imsSource;
        this.notifCount = numUnRead;
        this.otherId = name;
        this.date = date;
        this.lastMsg = lastMsg;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getImgSource() {
        return imgSource;
    }

    public int getNotifCount() {
        return notifCount;
    }

    public String getOtherId() {
        return otherId;
    }

    public String getDate() {
        return date;
    }

    public String getLastMsg() {
        return lastMsg;
    }
}
