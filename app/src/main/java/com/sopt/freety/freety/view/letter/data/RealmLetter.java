package com.sopt.freety.freety.view.letter.data;

import io.realm.RealmObject;

/**
 * Created by cmslab on 7/4/17.
 */
public class RealmLetter extends RealmObject {

    private int otherId;
    private String content;
    private String date;
    private String otherName;
    private boolean isMyMsg;

    public int getOtherId() {
        return otherId;
    }

    public void setOtherId(int otherId) {
        this.otherId = otherId;
    }

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

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public boolean isMyMsg() {
        return isMyMsg;
    }

    public void setMyMsg(boolean myMsg) {
        isMyMsg = myMsg;
    }

    public LetterData getLetterData(String imageURL) {
        return new LetterData(otherName, imageURL, content, date, isMyMsg);
    }
}
