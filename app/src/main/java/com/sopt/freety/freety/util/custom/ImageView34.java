package com.sopt.freety.freety.util.custom;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by cmslab on 6/27/17.
 */

public class ImageView34 extends android.support.v7.widget.AppCompatImageView {
    public ImageView34(Context context) {
        super(context);
    }

    public ImageView34(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageView34(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * Fix the aspect ratio of the {@link ImageView} to 3:4.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) Math.ceil((float) width * (4/ 3f));
        setMeasuredDimension(width, height);
    }
}
