package fretty.sopt.com.freety.itemdata;

/**
 * Created by USER on 2017-06-25.
 */

public class ItemDataPerm {

    public int img_perm_hair;
    public String text_perm_local;
    public String text_perm_name;

    public ItemDataPerm(int img_perm_hair, String text_perm_local, String text_perm_name){
        this.img_perm_hair = img_perm_hair;
        this.text_perm_local = text_perm_local;
        this.text_perm_name = text_perm_name;
    }

    public int getImg_perm_hair(){
        return img_perm_hair;
    }

    public String getText_perm_local(){
        return text_perm_local;
    }

    public String getText_perm_name(){
        return text_perm_name;
    }

}
