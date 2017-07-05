package com.sopt.freety.freety.view.letter.data;

import com.sopt.freety.freety.util.util.DateParser;

import java.text.ParseException;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

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

    public LetterRoomData getLetterRoomData(Realm realm, RealmPerson person) throws ParseException {
        RealmResults<RealmLetter> realmLetters = realm.where(RealmLetter.class).equalTo("letterList.otherId", person.getMemberId()).findAll();
        int letterListSize = realmLetters.size();
        if (letterListSize > 0) {
            RealmLetter letter = realmLetters.get(letterListSize - 1);
            String lastMsg = letter.getContent();
            return new LetterRoomData(memberId, imageURL, 0, memberName, letter.getDate(), lastMsg);
        } else {
            return null;
        }
    }
}

