package com.ef.wallethub.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    /**
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr, String dateFormat) throws Exception {
        Date date = null;

        if (dateStr != null) {
            date = new SimpleDateFormat(dateFormat).parse(dateStr);
        }

        return date;
    }

    /**
     * @param date
     * @return
     * @throws Exception
     */
    public static String formatDate(Date date, String dateFormat) throws Exception {
        String dateStr = null;

        if (date != null) {
            dateStr = new SimpleDateFormat(dateFormat).format(date);
        }

        return dateStr;
    }

    /**
     * @param startDate
     * @param hours
     * @return
     * @throws Exception
     */
    public static Date addHours(Date startDate, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    /**
     * @param startDate
     * @param days
     * @return
     */
    public static Date addDays(Date startDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
    
}
