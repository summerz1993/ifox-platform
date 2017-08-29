package com.ifox.platform.utility.datetime;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by zhangye on 2017/7/22.
 */
public class DateTimeUtilTest {

    @Test
    public void testGetCurrentDateAsString() {
        String currentDateAsString = DateTimeUtil.getCurrentDateAsString(null);
        System.out.println("currentDateAsString:" + currentDateAsString);
    }

    @Test
    public void testTimestampConverter(){
        String unixTimestamp = "1500715426";
        LocalDateTime parse = LocalDateTime.parse(unixTimestamp, DateTimeFormatter.BASIC_ISO_DATE);

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = parse.atZone(zoneId);
        Date from = Date.from(zonedDateTime.toInstant());

//        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        System.out.println(from);
    }

    @Test
    public void testGetThreeOclockAMOfTheNextDay() {
        Date threeOclockAMOfTheNextDay = DateTimeUtil.getThreeOclockAMOfTheNextDay();
        System.out.println(threeOclockAMOfTheNextDay);
    }
}
