package com.sopt.freety.freety.util.util;

import java.util.Date;

/**
 * Created by cmslab on 7/4/17.
 */

import java.util.concurrent.TimeUnit;

public class TimeStampFormatter {

    /**
     * For use with java.util.Date
     */
    public String format(Date timestamp) {
        long millisFromNow = getMillisFromNow(timestamp);

        long minutesFromNow = TimeUnit.MILLISECONDS.toMinutes(millisFromNow);
        if (minutesFromNow < 1) {
            return "방금 전";
        }
        long hoursFromNow = TimeUnit.MILLISECONDS.toHours(millisFromNow);
        if (hoursFromNow < 1) {
            return formatMinutes(minutesFromNow);
        }
        long daysFromNow = TimeUnit.MILLISECONDS.toDays(millisFromNow);
        if (daysFromNow < 1) {
            return formatHours(hoursFromNow);
        }
        long weeksFromNow = TimeUnit.MILLISECONDS.toDays(millisFromNow) / 7;
        if (weeksFromNow < 1) {
            return formatDays(daysFromNow);
        }

        return String.format("%d.%d.%d", timestamp.getYear(), timestamp.getMonth(), timestamp.getDay());
    }

    private long getMillisFromNow(Date commentedAt) {
        long commentedAtMillis = commentedAt.getTime();
        long nowMillis = System.currentTimeMillis();
        return nowMillis - commentedAtMillis;
    }

    private String formatMinutes(long minutes) {
        return minutes + "분 전";
    }

    private String formatHours(long hours) {
        return hours + "시간 전";
    }

    private String formatDays(long days) {
        return days + "일 전";
    }

}