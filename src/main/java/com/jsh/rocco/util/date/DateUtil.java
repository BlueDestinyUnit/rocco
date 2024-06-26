package com.jsh.rocco.util.date;

import org.springframework.stereotype.Component;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	public Date createPFormat(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
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

	public LocalDate localParse(String dateStr){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDate.parse(dateStr, formatter);
	}

}
