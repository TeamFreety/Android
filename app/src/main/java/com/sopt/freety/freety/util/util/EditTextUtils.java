package com.sopt.freety.freety.util.util;

import android.graphics.Color;
import android.widget.EditText;

/**
 * Created by cmslab on 7/3/17.
 */

public class EditTextUtils {

    public static void setUseableEditText(EditText et, boolean useable) {
        et.setClickable(useable);
        et.setEnabled(useable);
        et.setFocusable(useable);
        et.setFocusableInTouchMode(useable);

        if (useable) {
            et.setBackgroundColor(Color.parseColor("#ffffffff"));
        } else {
            et.setBackgroundColor(Color.parseColor("#00000000"));
        }
    }
}
