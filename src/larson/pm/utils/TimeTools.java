package larson.pm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTools {
	/**
	 * �Ƚ����ʱ���Ƿ���һ��
	 * 
	 * @param datestr
	 * @return
	 * @throws ParseException
	 */
	public static boolean isThisWeek(String datestr) throws ParseException {
		Date date = parseStr2Date(datestr);
		// ����ʱ��
		Calendar curr_calendar = Calendar.getInstance();
		curr_calendar.setTimeInMillis(System.currentTimeMillis());
		// �����ʱ��
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());

		System.out.println("��������" + curr_calendar.get(Calendar.WEEK_OF_YEAR)
				+ "���ܴ����" + calendar.get(Calendar.WEEK_OF_YEAR));

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
	 * �Ƿ��������
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
	 * �ж��Ƿ���������
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
	 * ����ʱ���ַ���ת��Ϊ���ڸ�ʽ
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
	 * ��ȡ����ʱ���������ʱ��
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
			return "�׶�һ��" + parseDate2Str(max);
		else if (b == max)
			return "�׶ζ���" + parseDate2Str(max);
		else
			return "�׶�����" + parseDate2Str(max);
	}

	/**
	 * ��ʱ��ת��Ϊ�ַ���
	 * 
	 * @param max
	 * @return
	 */
	private static CharSequence parseDate2Str(long max) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy��MM��dd��");
		return df.format(new Date(max));
	}

	/**
	 * �����ʱ���Ƿ��Ѿ���ȥ
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
	 * ��ȡ����ʱ�������·�
	 * 
	 * @param time
	 * @return �����·�
	 * @throws ParseException
	 */
	public static int getMonthValue(String time) throws ParseException {
		Date date = parseStr2Date(time);
		return date.getMonth();
	}

}
