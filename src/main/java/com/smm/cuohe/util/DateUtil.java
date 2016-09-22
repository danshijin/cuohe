package com.smm.cuohe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author zengshihua
 * 
 */
public class DateUtil {

	// 格式化当前系统日期
	public static String getDateWidthFormat(String format) {
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd HH:mm:ss";
		}

		SimpleDateFormat dateFm = new SimpleDateFormat(format);
		String dateTime = dateFm.format(new java.util.Date());
		return dateTime;
	}

	// 格式化日期
	public static String doFormatDate(Date date, String format) {
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd HH:mm:ss";
		}

		if (date == null) {
			date = new Date();
		}

		SimpleDateFormat dateFm = new SimpleDateFormat(format);
		String dateTime = dateFm.format(date);
		return dateTime;
	}

	// 转化时间字符串为日期
	public static Date doSFormatDate(String dateStr, String format) {
		if (dateStr.equals("")) {
			return null;
		}
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd HH:mm:ss";
		}

		SimpleDateFormat dateFm = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFm.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String doFormatDate(Date oldDate, Date newDate) {
		Calendar oldCal = Calendar.getInstance();
		Calendar newCal = Calendar.getInstance();
		oldCal.setTime(oldDate);
		newCal.setTime(newDate);
		if (oldCal.equals(newCal)) {
			return "";
		} else if (oldCal.before(newCal)) {
			return "";
		} else if (oldCal.after(newCal)) {
			return "";
		} else {
			return "";
		}

	}

	public static int getMinute() {
		Calendar calendar = Calendar.getInstance();
		int minute = calendar.get(Calendar.MINUTE);
		return minute;
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.getDateWidthFormat(""));
	}

	/**
	 * 一天的开始时间 00:00:00
	 * 
	 * @discription
	 * @author Nancy/张楠
	 * @created 2015年9月14日 上午11:09:11
	 * @return
	 */
	public static Date startOfTodDay(String date) {
		Date startDate = DateUtil.doSFormatDate(date, "yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 一天的结束时间 23:59:59
	 * 
	 * @discription
	 * @author Nancy/张楠
	 * @created 2015年9月14日 上午11:09:26
	 * @return
	 */
	public static Date endOfTodDay(String date) {
		Date endDate = DateUtil.doSFormatDate(date, "yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);

		return calendar.getTime();
	}

	/**
	 * 返回当前日期
	 * */
	public String currentDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		return str;
	}

	/**
	 * 时间转 天小时秒
	 * @param intervalTime
	 * @return
	 */
	public static String getIntervalUpdateTime(long intervalTime) {
		StringBuilder result = new StringBuilder();
		long interval = intervalTime / 1000;
		final long day = 24 * 60 * 60;
		final long hour = 60 * 60;
		final long minute = 60;
		int detailDay = 0;
		int detailHour = 0;
		int detailMinute = 0;
		int detailSecond = 0;
		if (interval >= day) {
			detailDay = (int) (interval / day);
			interval = interval - detailDay * day;
		}
		if (interval >= hour) {
			detailHour = (int) (interval / hour);
			interval = interval - hour * detailHour;
		}
		if (interval >= minute) {
			detailMinute = (int) (interval / minute);
			interval = interval - detailMinute * minute;
		}
		if (interval > 0) {
			detailSecond = (int) interval;
		}
		result.setLength(0);
		if (detailDay > 0) {
			result.append(detailDay);
			result.append("天");
		}
		if (detailHour > 0) {
			result.append(detailHour);
			result.append("小时");
		}
		if (detailMinute > 0) {
			result.append(detailMinute);
			result.append("分");
		}
		if (detailSecond > 0) {
			result.append(detailSecond);
			result.append("秒");
		}
		return result.toString();
	}


}
