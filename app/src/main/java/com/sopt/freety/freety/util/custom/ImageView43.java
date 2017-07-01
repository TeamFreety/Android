package com.sopt.freety.freety.util.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by cmslab on 6/27/17.
 */

public class ImageView43 extends android.support.v7.widget.AppCompatImageView {
    public ImageView43(Context context) {
        super(context);
    }

    public ImageView43(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageView43(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) Math.ceil((float) width * (3/ 5f));
        setMeasuredDimension(width, height);
    }
}
