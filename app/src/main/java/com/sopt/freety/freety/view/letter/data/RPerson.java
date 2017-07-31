package com.sopt.freety.freety.view.letter.data;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * This is a person model class for use in letter service of this app.
 * The person in this user's DB will be created if there is at least one contact with the user.
 * Note that it must be used to only Realm DB service.
 */

public class RPerson extends RealmObject {

    /**
     * An identifier of the member.
     */
    private int memberId;

    /**
     * A name of the member.
     */
    private String memberName;

    /**
     * Image URL of the member profile.
     */
    private String imageURL;

    /**
     * List of {@link RealmLetter} about this person.
     * Both the letter sent by the user and the letter received by the user
     * can be added to this list.
     */
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

    public void setLetterList(RealmList<RealmLetter> letterList) {
        this.letterList = letterList;
    }

    public LetterRoomData getLetterRoomData() {
        RealmLetter letter = letterList.get(letterList.size() - 1);
        return new LetterRoomData(memberId, imageURL, 0, memberName, letter.getDate(), letter.getContent());
    }
}

