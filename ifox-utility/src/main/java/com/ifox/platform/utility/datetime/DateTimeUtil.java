package com.ifox.platform.utility.datetime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Yeager
 *
 * 日期工具类
 * java8 java.time包实现
 */
public class DateTimeUtil {

	public static final String DEFAULT_DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取当前日期时间字符串
	 * @param formatter 格式化
	 * @return String
	 */
	public static String getCurrentDateTimeAsString(DateTimeFormatter formatter) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		if (null == formatter) {
			formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMATTER);
		}
		return currentDateTime.format(formatter);
	}

	/**
	 * 获取当前日期时间
	 * @param formatter 格式化
	 * @return LocalDateTime
	 */
	public static LocalDateTime getCurrentLocalDateTime(DateTimeFormatter formatter) {
		String text = getCurrentDateTimeAsString(null);
		if (null == formatter) {
			formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMATTER);
		}
		return LocalDateTime.parse(text, formatter);
	}

	/**
	 * 获取当前日期时间
	 * @param formatter 格式化
	 * @return Date
	 */
	public static Date getCurrentDate(DateTimeFormatter formatter) {
		LocalDateTime parse = getCurrentLocalDateTime(formatter);
		return convertLocalDateTimeToDate(parse);
	}

	/**
	 * 转换java8的LocalDateTime为Date对象
	 * @param localDateTime java8
	 * @return Date
	 */
	public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime){
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
		return Date.from(zonedDateTime.toInstant());
	}

	/**
	 * 在当前时间加秒数
	 * @param second 秒(可为负数)
	 * @return 日期
	 */
	public static Date plusSecondBaseOnCurrentDate(int second) {
		LocalDateTime currentLocalDateTime = getCurrentLocalDateTime(null);
		LocalDateTime plusSeconds = currentLocalDateTime.plusSeconds(second);
		return convertLocalDateTimeToDate(plusSeconds);
	}

    /**
     * 在当前时间加分钟
     * @param min 分钟(可为负数)
     * @return 日期
     */
    public static Date plusMinBaseOnCurrentDate(int min) {
        LocalDateTime currentLocalDateTime = getCurrentLocalDateTime(null);
        LocalDateTime plusMin = currentLocalDateTime.plusMinutes(min);
        return convertLocalDateTimeToDate(plusMin);
    }

    /**
     * 在当前时间加小时
     * @param hour 小时(可为负数)
     * @return 日期
     */
    public static Date plusHourBaseOnCurrentDate(int hour) {
        LocalDateTime currentLocalDateTime = getCurrentLocalDateTime(null);
        LocalDateTime plusHours = currentLocalDateTime.plusHours(hour);
        return convertLocalDateTimeToDate(plusHours);
    }

}
