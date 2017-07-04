package com.sopt.freety.freety.view.letter.data;

import io.realm.RealmObject;

/**
 * Created by cmslab on 7/4/17.
 */

public class RealmLetter extends RealmObject {

    private String content;
    private String date;
    private boolean isMyMsg;
    private boolean isPending;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isMyMsg() {
        return isMyMsg;
    }

    public void setMyMsg(boolean myMsg) {
        isMyMsg = myMsg;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }
}