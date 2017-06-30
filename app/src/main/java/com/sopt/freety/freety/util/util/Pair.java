package com.sopt.freety.freety.util.util;

/**
 * Created by cmslab on 6/30/17.
 */

public class Pair<LV, RV> {

    private LV left;
    private RV right;

    public Pair(LV leftValue, RV rightValue) {
        this.left = leftValue;
        this.right = rightValue;
    }

    public Pair() {
        left = null;
        right = null;
    }

    public LV getLeft() {
        return left;
    }

    public RV getRight() {
        return right;
    }

    public void setLeft(LV left) {
        this.left = left;
    }

    public void setRight(RV right) {
        this.right = right;
    }
}
