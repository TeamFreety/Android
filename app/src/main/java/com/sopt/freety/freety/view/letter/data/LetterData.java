package com.sopt.freety.freety.view.letter.data;

/**
 * Created by cmslab on 7/5/17.
 */

public class LetterData {

    private String name;
    private String imageURL;
    private String context;
    private String date;
    private boolean isMine;

    public LetterData(String name, String imageURL, String context, String date, boolean isMine) {
        this.name = name;
        this.imageURL = imageURL;
        this.context = context;
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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
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
