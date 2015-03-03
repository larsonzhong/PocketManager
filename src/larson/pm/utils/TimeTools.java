package larson.pm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTools {
	/**
	 * 比较这个时间是否这一周
	 * 
	 * @param datestr
	 * @return
	 * @throws ParseException
	 */
	public static boolean isThisWeek(String datestr) throws ParseException {
		Date date = parseStr2Date(datestr);
		// 现在时间
		Calendar curr_calendar = Calendar.getInstance();
		curr_calendar.setTimeInMillis(System.currentTimeMillis());
		// 传入的时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());

		System.out.println("现在是周" + curr_calendar.get(Calendar.WEEK_OF_YEAR)
				+ "这周传入的" + calendar.get(Calendar.WEEK_OF_YEAR));

		if ((curr_calendar.get(Calendar.WEEK_OF_YEAR) == calendar
				.get(Calendar.WEEK_OF_YEAR))
				&& (curr_calendar.get(Calendar.YEAR) == calendar
						.get(Calendar.YEAR))) {
			return true;
		} else
			return false;
	}

	public static boolean isThisDay(String datestr) throws ParseException {
		Date date = parseStr2Date(datestr);
		Date date_cur = new Date();
		long time = date.getTime() - date_cur.getTime();
		System.out.println(time + "-----------" + date_cur + "----" + date);
		if (time > 0 && time < 24 * 3600 * 1000) {
			return true;
		} else
			return false;
	}

	/**
	 * 是否是这个月
	 * 
	 * @param datestr
	 * @return
	 * @throws ParseException
	 */
	public static boolean isThisMonth(String datestr) throws ParseException {
		Date date = parseStr2Date(datestr);
		Date date_cur = new Date(System.currentTimeMillis());
		if (date.getMonth() == date_cur.getMonth()) {
			return true;
		} else
			return false;
	}

	/**
	 * 判断是否三个月内
	 * 
	 * @param datestr
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	public static boolean isThreeMonth(String datestr) throws ParseException {
		Date date = parseStr2Date(datestr);
		Date date_cur = new Date(System.currentTimeMillis());
		if ((Math.abs(date.getMonth() - date_cur.getMonth())) <= 3)
			return true;
		else
			return false;
	}

	/**
	 * 传入时间字符串转换为日期格式
	 * 
	 * @param datestr
	 * @return
	 * @throws ParseException
	 */
	private static Date parseStr2Date(String datestr) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = df.parse(datestr);
		return date;
	}

	/**
	 * 获取三个时间中最近的时间
	 * 
	 * @param step1LimitTime
	 * @param step2LimitTime
	 * @param step3LimitTime
	 * @return
	 * @throws ParseException
	 */
	public static CharSequence getRecentTime(String step1LimitTime,
			String step2LimitTime, String step3LimitTime) throws ParseException {
		long a = parseStr2Date(step1LimitTime).getTime();
		long b = parseStr2Date(step1LimitTime).getTime();
		long c = parseStr2Date(step1LimitTime).getTime();
		long max = a;

		if (b > max)
			max = b;
		if (c > max)
			max = c;
		if (a == max)
			return "阶段一：" + parseDate2Str(max);
		else if (b == max)
			return "阶段二：" + parseDate2Str(max);
		else
			return "阶段三：" + parseDate2Str(max);
	}

	/**
	 * 把时间转换为字符串
	 * 
	 * @param max
	 * @return
	 */
	private static CharSequence parseDate2Str(long max) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		return df.format(new Date(max));
	}

	/**
	 * 传入的时间是否已经过去
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static boolean isNotDepressed(String time) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date date = df.parse(time);
		Date date_cur = new Date();
		int hour = date.getHours();
		int cur_hour = date_cur.getHours();
		int minute = date.getMinutes();
		int minute_cur = date_cur.getMinutes();
		System.out.println(hour + "-------" + cur_hour + "-----" + minute
				+ "----" + minute_cur);
		return (hour >= cur_hour && minute >= minute_cur) ? true : false;
	}

	/**
	 * 获取传入时间所在月份
	 * 
	 * @param time
	 * @return 所在月份
	 * @throws ParseException
	 */
	public static int getMonthValue(String time) throws ParseException {
		Date date = parseStr2Date(time);
		return date.getMonth();
	}

}
