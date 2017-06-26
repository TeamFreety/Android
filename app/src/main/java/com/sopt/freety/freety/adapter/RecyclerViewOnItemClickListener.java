package com.sopt.freety.freety.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView.*;

/**
 * Created by cmslab on 6/25/17.
 */

public class RecyclerViewOnItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private OnItemClickListener onItemClickListener;
    private GestureDetector gestureDetector;

    public RecyclerViewOnItemClickListener(Context context,
                                           final RecyclerView recyclerView,
                                           final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && onItemClickListener != null) {
                    onItemClickListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && onItemClickListener != null && gestureDetector.onTouchEvent(e)) {
            onItemClickListener.onItemClick(childView, rv.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
        void onItemLongClick(View v, int position);
    }
}
