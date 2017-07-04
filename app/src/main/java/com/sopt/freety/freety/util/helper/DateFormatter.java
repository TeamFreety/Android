package com.sopt.freety.freety.util.helper;

import com.sopt.freety.freety.util.custom.ScrollFeedbackRecyclerView;
import com.sopt.freety.freety.util.util.TimeStampFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cmslab on 7/3/17.
 */

public class DateFormatter {

    public static String fromDateStringToYearMonthDay(String date) throws ParseException {
        TimeStampFormatter timeStampFormatter = new TimeStampFormatter();
        Date parsedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
        String formatted = timeStampFormatter.format(parsedDate);
        return formatted;
    }
}
