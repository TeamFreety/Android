package com.sopt.freety.freety.view.letter.data;

import com.sopt.freety.freety.R;

/**
 * Created by cmslab on 6/25/17.
 */

public class LetterListData {

    private final String imageURL;
    private final int notifCount;
    private final String otherId;
    private final String date;
    private final String lastMsg;

    public LetterListData(final String imageURL, final int numUnRead, final String name, final String date, final String lastMsg) {
        this.imageURL = imageURL;
        this.notifCount = numUnRead;
        this.otherId = name;
        this.date = date;
        this.lastMsg = lastMsg;
    }

    public String getImageURL() {
        return imageURL;
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
