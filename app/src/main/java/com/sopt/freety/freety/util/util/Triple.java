package com.sopt.freety.freety.util.util;

/**
 * Created by USER on 2017-07-02.
 */

public class Triple<FV, SV, TV> {


    private FV first;
    private SV second;
    private TV third;

    public Triple(FV first, SV second, TV third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static <FV, SV, TV> Triple of(FV first, SV second, TV third) {
        return new Triple<>(first, second, third);
    }

    public FV getFirst() {
        return first;
    }

    public SV getSecond() {
        return second;
    }

    public TV getThird() {
        return third;
    }

    public void setFirst(FV first) {
        this.first = first;
    }

    public void setSecond(SV second) {
        this.second = second;
    }

    public void setThird(TV third) {
        this.third = third;
    }
}
