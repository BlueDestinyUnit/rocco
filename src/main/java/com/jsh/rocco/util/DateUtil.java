package com.jsh.rocco.util;

import org.springframework.stereotype.Component;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtil {
	public Date parseDate(int year) {
		String dateString = String.valueOf(year);
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		ParsePosition position = new ParsePosition(0);
		return format.parse(dateString, position);
	}
	
	public Date parseDateStringToDate(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition position = new ParsePosition(0);
		return format.parse(dateStr, position);
	}

	public Date parseDateStringWithFormat(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition position = new ParsePosition(0);
		return format.parse(dateStr, position);
	}

	public String getFormatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
}
