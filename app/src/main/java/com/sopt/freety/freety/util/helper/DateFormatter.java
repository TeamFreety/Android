package com.sopt.freety.freety.util.helper;

import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cmslab on 7/3/17.
 */

public class DateFormatter {

    public static String toDateString() {
        return "";
    }

    public static String fromDateStringToYearMonthDay(String date) throws ParseException {
        Date parsedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
        String formatted = new SimpleDateFormat("yyyy.MM.dd").format(parsedDate);
        return formatted;
    }
}
