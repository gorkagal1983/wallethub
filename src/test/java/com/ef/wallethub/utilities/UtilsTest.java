package com.ef.wallethub.utilities;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UtilsTest {

    @Test
    public void addHoursShouldAddOneHour() {
        String startDate = "2017-01-01.15:00:00";

        try {
            Date _startDate = Utils.parseDate(startDate, "yyyy-MM-dd.HH:mm:ss");
            Date result = Utils.addHours(_startDate, 1);

            String _result = Utils.formatDate(result, "yyyy-MM-dd.HH:mm:ss");

            assertEquals(_result, "2017-01-01.16:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void addDaysShouldAddTwoDays() {
        String startDate = "2017-01-01.15:00:00";

        try {
            Date _startDate = Utils.parseDate(startDate, "yyyy-MM-dd.HH:mm:ss");
            Date result = Utils.addDays(_startDate, 2);

            String _result = Utils.formatDate(result, "yyyy-MM-dd.HH:mm:ss");

            assertEquals(_result, "2017-01-03.15:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void parseDateShouldReturnDate() {
        String startDate = "2017-01-01.15:34:12.123";

        try {
            Date _startDate = Utils.parseDate(startDate, "yyyy-MM-dd.HH:mm:ss");
            assertNotNull(_startDate);

            String _result = Utils.formatDate(_startDate, "yyyy-MM-dd.HH:mm:ss");
            assertEquals(_result, "2017-01-01.15:34:12");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
