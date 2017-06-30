package com.sopt.freety.freety.view.chat.data;

import com.sopt.freety.freety.R;
import com.sopt.freety.freety.view.firebase.chat.data.ChatListFirebaseData;

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

    public ChatListData(final int roomId, final int imgSource,
                        final int numUnRead, final String name,
                        final String date, final String lastMsg) {
        this.roomId = roomId;
        this.imgSource = imgSource;
        this.notifCount = numUnRead;
        this.otherId = name;
        this.date = date;
        this.lastMsg = lastMsg;
    }

    public ChatListData() {
        this.roomId = 1;
        this.imgSource = R.drawable.chat_list_elem;
        this.notifCount = 3;
        this.otherId = "정병진";
        this.date = "2017.06.28";
        this.lastMsg = "아 앱잼 진짜 힘들다....";
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
