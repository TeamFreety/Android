package com.sopt.freety.freety.view.firebase.chat.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cmslab on 6/26/17.
 */

public class ChatListFirebaseData {

    public int roomId;
    public String myId;
    public String otherId;
    public int isNew;
    public String lastMsg;

    public ChatListFirebaseData() {

    }

    public ChatListFirebaseData(int roomId, String myId, String otherId, int notifCount, String lastMsg) {
        this.roomId = roomId;
        this.myId = myId;
        this.otherId = otherId;
        this.isNew = notifCount;
        this.lastMsg = lastMsg;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public Map<String, Object> toMap(int roomId, String myId, String otherId,
                                     int notifCount, String lastMsg) {
        Map<String, Object> map = new HashMap<>();
        map.put("roomId", roomId);
        map.put("myId", myId);
        map.put("otherId", otherId);
        map.put("isNew", notifCount);
        map.put("lastMsg", lastMsg);

        return map;
    }

}
