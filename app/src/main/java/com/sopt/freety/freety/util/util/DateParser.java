package com.sopt.freety.freety.util.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cmslab on 7/4/17.
 */

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateParser {

    /**
     * For use with java.util.Date
     */
    private static final String TAG = "DateParser";
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
            Log.i("Letter", "toPrettyFormat: " + date);
            DateParser timeStampFormatter = new DateParser();
            Date parsedDate = DateParser.from(date);
            String formatted = timeStampFormatter.format(parsedDate);
            return formatted;
        } catch (ParseException e) {
            return "";
        }
    }

    public static String to() {
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(tz);
        String formattedString = df.format(new Date());

        Date korDate;
        try {
            korDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(formattedString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        int year = korDate.getYear() + 1900;
        int month = korDate.getMonth() + 1;
        int day = korDate.getDate();
        int hour = korDate.getHours();
        int minute = korDate.getMinutes();
        int seconds = korDate.getSeconds();

        String monthString = String.valueOf(month);
        if (month  < 10) {
            monthString = "0" + monthString;
        }

        String dayString = String.valueOf(day);
        if (day < 10) {
            dayString = "0" + dayString;
        }

        String hourString = String.valueOf(hour);
        if (hour < 10) {
            hourString = "0" + hourString;
        }

        String minuteString = String.valueOf(minute);
        if (minute < 10) {
            minuteString = "0" + minuteString;
        }

        String secondString = String.valueOf(seconds);
        if (seconds < 10) {
            secondString = "0" + secondString;
        }
        Log.i(TAG, "to: " + String.format("%d-%s-%sT%s:%s:%s", year,
                monthString, dayString, hourString, minuteString, secondString));
        return String.format("%d-%s-%sT%s:%s:%s", year,
                monthString, dayString, hourString, minuteString, secondString);
    }

    public static String toDateTimeFormat(String date) {
        String[] splits = date.split(" ");
        String year = splits[0].substring(0, splits[0].length() - 1);
        String month = splits[1].substring(0, splits[1].length() - 1);
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = splits[2].substring(0, splits[2].length() - 1);
        if (day.length() == 1) {
            day = "0" + day;
        }
        int hourOffset = splits[3].equals("오후") ? 12 : 0;
        String hour = splits[4].substring(0, splits[4].length() - 1);
        String formattedHour = String.valueOf(Integer.parseInt(hour) + hourOffset);
        if (formattedHour.length() == 1) {
            formattedHour = "0" + formattedHour;
        }
        Log.i(TAG, "toDateTimeFormat: " + String.format("%s-%s-%s'T'%s:00:00", year, month, day, formattedHour));
        return String.format("%s-%s-%sT%s:00:00", year, month, day, formattedHour);
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
