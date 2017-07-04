package com.sopt.freety.freety.view.letter.data;

import com.sopt.freety.freety.util.util.DateParser;

import java.text.ParseException;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by cmslab on 7/4/17.
 */

public class RealmPerson extends RealmObject {

    private int memberId;
    private String memberName;
    private String imageURL;
    private RealmList<RealmLetter> letterList;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public RealmList<RealmLetter> getLetterList() {
        return letterList;
    }

    public LetterListData getLetterListData() throws ParseException {
        int unReadCount = 0;
        String date = "";
        String lastMsg = "";
        int letterListSize = letterList.size();
        DateParser formatter = new DateParser();
        for (int index = 0; index < letterListSize; index++) {
            RealmLetter letter = letterList.get(index);
            if (letter.isPending()) {
                unReadCount++;
            }

            if (index == letterListSize - 1) {
                date = DateParser.toYearMonthDay(letter.getDate());
                lastMsg = letter.getContent();
            }
        }
        return new LetterListData(imageURL, unReadCount, memberName, date, lastMsg);
    }
}

