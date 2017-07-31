package com.sopt.freety.freety.view.letter.data;

import io.realm.RealmObject;

/**
 * This is a letter model class for use in letter service of this app.
 * Note that it must be used to only Realm DB service.
 */
public class RealmLetter extends RealmObject {

    /**
     * An identifier of the recipient of the letter.
     */
    private int otherId;

    /**
     * A content of the letter.
     */
    private String content;

    /**
     * A time of the letter was delivered.
     */
    private String date;

    /**
     * The name of the recipient of the letter.
     */
    private String otherName;

    /**
     * Whether the owner of {@link RealmLetter} is user himself or not.
     */
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

    @Override
    public String toString() {
        return "otherId : " + otherId + ", content : " + content + ", isMyMsg " + String.valueOf(isMyMsg) + ", date" + date;
    }
}
