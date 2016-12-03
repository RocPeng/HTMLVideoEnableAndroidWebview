package com.roc.helloapp.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	public static String getStringFromTD(long td) {
		if (String.valueOf(td).length() < 13) {
			long currentDate = td * 1000;
			return getChatTime(currentDate);
		} else {
			return getChatTime(td);
		}
	}

	@SuppressLint("SimpleDateFormat")
	public static String getChatTime(long timesamp) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date today = new Date(System.currentTimeMillis());
		Date otherDay = new Date(timesamp);
		int temp = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));
		switch (temp) {
		case 0:
			result = "" + getHourAndMin(timesamp);
			break;
		case 1:
			result = "昨天 " + getHourAndMin(timesamp);
			break;

		default:
			result = getTime(timesamp);
			break;
		}
		return result;
	}

	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
		return format.format(new Date(time));
	}

	public static String getHourAndMin(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		Date nowDay = new Date(time);
		int temp = 12 - Integer.parseInt(sdf.format(nowDay));
		SimpleDateFormat format;
		if (temp > 0) {
			format = new SimpleDateFormat("HH:mm");
		} else {
			format = new SimpleDateFormat("HH:mm");
		}
		return format.format(new Date(time));
	}

	/**
	 * UTC日期时间
	 * 
	 * @return
	 */
	public static long getCurrentUtcTime() {
		Date l_datetime = new Date();
		return l_datetime.getTime();
	}

	/**
	 * 将date格式的时间转换为2015-11-11-17:24形式
	 * 
	 * @param current
	 * @return
	 */
	public static String getDateTime(Date current) {
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
		String currentTime = timeFormat.format(current);
		return currentTime;
	}

	/**
	 * 将utc时间转换为2015-11-11 17:24形式
	 * 
	 * @param
	 * @return
	 */
	public static String utc2Time(long remindUTCTime) {
		Date remindDate = new Date(remindUTCTime);
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String currentTime = timeFormat.format(remindDate);
		return currentTime;
	}

	/**
	 * 实现将2015-11-11-17:24这样的时间拆分成年 月 日 时 分 秒,并赋值给calendar
	 * 
	 * @param initDateTime
	 *            初始日期时间值 字符串型
	 * @return Calendar
	 */
	public static Calendar getCalendarByInintData(String initDateTime) {
		Calendar calendar = Calendar.getInstance();
		// 将初始日期时间 拆分成yyyy-MM-dd-HH:mm

		String[] temp = null;
		temp = initDateTime.split("-");
		String yearStr = temp[0];
		String monthStr = temp[1];
		String dayStr = temp[2];
		String hourAndMin = temp[3];

		String[] tempp = null;
		tempp = hourAndMin.split(":");
		String hourStr = tempp[0];
		String minuteStr = tempp[1];

		int currentYear = Integer.valueOf(yearStr.trim()).intValue();
		int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
		int currentDay = Integer.valueOf(dayStr.trim()).intValue();
		int currentHour = Integer.valueOf(hourStr.trim()).intValue();
		int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();

		calendar.set(currentYear, currentMonth, currentDay, currentHour,
				currentMinute);
		return calendar;
	}

	/**
	 * 实现将09:00 18:00这样的时间组装成当前日期的utc时间
	 * 
	 * @param initDateTime
	 *            初始日期时间值 字符串型
	 * @return Calendar
	 */
	public static Long getLocationTime(String initDateTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		String date = str + "-" + initDateTime;
		long dateTime = TimeUtil.getCalendarByInintData(date).getTime()
				.getTime();
		return dateTime;
	}

	/**
	 * 根据通讯录扩展字段的生日字段:形如1994-05-25来判断今天是否是生日前三天
	 * 
	 */
	public static boolean judgeIsBirthDay(String bornTime) {
		// 先将当前时间和生日时间转换为今年的第几天
		Date currentDate = new Date();
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentTime = timeFormat.format(currentDate);
		int birthNum = getDayNumber(bornTime);
		int currentNum = getDayNumber(currentTime);
		// 判断当年有多少天
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int allDay = 365;
		if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
			allDay++;
		}
		int subDay = (birthNum - currentNum + allDay) % allDay;
		// 判断当前日期是否是生日前三天
		if (subDay <= 3) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将1987-11-11这样的出生日期转换为一年中的第几天
	 * 
	 */
	public static int getDayNumber(String dayTime) {
		// 年，月，日 拆分开来,并且取年份为当前年份
		Calendar calendar = Calendar.getInstance();
		String[] temp = null;
		temp = dayTime.split("-");
		String yearStr = temp[0];
		String monthStr = temp[1];
		String dayStr = temp[2];
		int year = calendar.get(Calendar.YEAR);
		int month = Integer.valueOf(monthStr.trim()).intValue() - 1;
		int day = Integer.valueOf(dayStr.trim()).intValue();
		// 计算是一年中的第几天
		int sum = 0;
		int leap;
		/* 先计算某月以前月份的总天数 */
		switch (month) {
		case 1:
			sum = 0;
			break;
		case 2:
			sum = 31;
			break;
		case 3:
			sum = 59;
			break;
		case 4:
			sum = 90;
			break;
		case 5:
			sum = 120;
			break;
		case 6:
			sum = 151;
			break;
		case 7:
			sum = 181;
			break;
		case 8:
			sum = 212;
			break;
		case 9:
			sum = 243;
			break;
		case 10:
			sum = 273;
			break;
		case 11:
			sum = 304;
			break;
		case 12:
			sum = 334;
			break;
		default:
			break;
		}
		sum = sum + day; /* 再加上某天的天数 */
		if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))/* 判断是不是闰年 */
			leap = 1;
		else
			leap = 0;
		if (leap == 1 && month > 2)/* 如果是闰年且月份大于2,总天数应该加一天 */
			sum++;
		return sum;
	}
}
