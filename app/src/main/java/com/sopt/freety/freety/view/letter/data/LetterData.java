package com.sopt.freety.freety.view.letter.data;

import io.realm.Realm;

/**
 * Created by cmslab on 7/5/17.
 */

public class LetterData {

    private String name;
    private String imageURL;
    private String content;
    private String date;
    private boolean isMine;

    public LetterData(String name, String imageURL, String content, String date, boolean isMine) {
        this.name = name;
        this.imageURL = imageURL;
        this.content = content;
        this.date = date;
        this.isMine = isMine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
