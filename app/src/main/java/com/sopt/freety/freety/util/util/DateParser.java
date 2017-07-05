package com.sopt.freety.freety.util.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cmslab on 7/4/17.
 */

import java.util.concurrent.TimeUnit;

public class DateParser {

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

    public static Date from(String date) throws ParseException {
        Date parsedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
        return parsedDate;
    }

    public static String toPrettyFormat(String date) {
        try {
            DateParser timeStampFormatter = new DateParser();
            Date parsedDate = DateParser.from(date);
            String formatted = timeStampFormatter.format(parsedDate);
            return formatted;
        } catch (ParseException e) {
            return "";
        }
    }

    public static String toDateTimeFormat(String date) {
        String[] splits = date.split(" ");
        String year = splits[0].substring(0, splits[0].length());
        String month = splits[1].substring(0, splits[1].length());
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = splits[2].substring(0, splits[2].length());
        if (day.length() == 1) {
            day = "0" + day;
        }
        int hourOffset = splits[3].equals("오후") ? 12 : 0;
        String hour = splits[4].substring(0, splits[4].length());
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        return String.format("%s-%s-%s %s:00:00", year, month, date, hour);
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
