package com.sopt.freety.freety.view.letter.data;

import java.text.ParseException;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Required;

/**
 * Created by cmslab on 7/4/17.
 */

public class RPerson extends RealmObject {

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

    public LetterRoomData getLetterRoomData() {
        RealmLetter letter = letterList.get(letterList.size() - 1);
        return new LetterRoomData(memberId, imageURL, 0, memberName, letter.getDate(), letter.getContent());
    }
}

