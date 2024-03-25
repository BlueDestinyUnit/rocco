package com.jsh.rocco.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date parseDate(int year) {
		String dateString = String.valueOf(year);
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		ParsePosition position = new ParsePosition(0);
		return format.parse(dateString, position);
	}
	
	public static Date parseDate(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition position = new ParsePosition(0);
		return format.parse(dateStr, position);
	}
	public static String getFormatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
}
