package com.sopt.freety.freety.util.helper;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by cmslab on 7/2/17.
 */

public class ImageSwicherHelper {

    public static void doChangeAnimation(final Context context,
                                         final ImageView image,
                                         final int outAnim,
                                         final int inAnim,
                                         final int changeToResource) {
        final Animation fadeInAnim = AnimationUtils.loadAnimation(context, inAnim);
        final Animation fadeOutAnim = AnimationUtils.loadAnimation(context, outAnim);

        fadeOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                image.setImageResource(changeToResource);
                image.startAnimation(fadeInAnim);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        image.startAnimation(fadeOutAnim);

    }
}
