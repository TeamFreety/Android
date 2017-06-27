package com.sopt.freety.freety.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by cmslab on 6/27/17.
 */

public class PostLinearLayout extends RelativeLayout{

    public PostLinearLayout(Context context) {
        super(context);
    }

    public PostLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PostLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PostLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("MeasureSpec", "width : " + String.valueOf(MeasureSpec.getSize(widthMeasureSpec)) + ", height : " + String.valueOf(MeasureSpec.getSize(heightMeasureSpec)));
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (int)(MeasureSpec.getSize(widthMeasureSpec) * (4 / 3f)));

    }
}
