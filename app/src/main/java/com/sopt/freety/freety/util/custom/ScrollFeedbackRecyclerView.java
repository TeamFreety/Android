package com.sopt.freety.freety.util.custom;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by cmslab on 6/28/17.
 */

public class ScrollFeedbackRecyclerView extends RecyclerView{

    private WeakReference<ScrollCallbacks> mCallbacks;

    public ScrollFeedbackRecyclerView(Context context) {
        super(context);
    }

    public ScrollFeedbackRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*If the first completely visible item in the RecyclerView is at
    index 0, then we're at the top of the list, so we want the AppBar to expand
    **if the AppBar is also collapsed** (otherwise the AppBar will constantly
    attempt to expand).
    */
    @Override
    public void onScrolled(int dx, int dy) {
        if (dy < 0) {
            if (getLayoutManager() instanceof LinearLayoutManager) {
                if (((LinearLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0) {
                    if (mCallbacks.get().isAppBarCollapsed()) {
                        mCallbacks.get().setExpanded(true);
                    }
                }

            } else if (getLayoutManager() instanceof GridLayoutManager) {
                if (((GridLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0) {
                    Log.e(getClass().getSimpleName(), "index 0 visible");
                    if (mCallbacks.get().isAppBarCollapsed()) {
                        mCallbacks.get().setExpanded(true);
                    }
                }
            }
        }
        super.onScrolled(dx, dy);
    }

    /* the findFirstCompletelyVisibleItem() method is only available with
    LinearLayoutManager and its subclasses, so test for it when setting the
    LayoutManager
    */
    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
    }

    public void attachCallbacks(Context context) {

        try {
            mCallbacks = new WeakReference<>((ScrollCallbacks)context);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " +
                    "ScrollFeedbackRecyclerView.ScrollCallbacks");
        }

    }

    public void attachCallbacks(Fragment fragment) {
        try {
            mCallbacks = new WeakReference<>((ScrollCallbacks)fragment);
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString() + " must implement " +
                    "ScrollFeedbackRecyclerView.ScrollCallbacks");
        }
    }


    /**
     * Necessary to interact with the AppBarLayout in the hosting Activity or Fragment
     */
    public interface ScrollCallbacks {
        boolean isAppBarCollapsed();
        void setExpanded(boolean expanded);
    }
}
