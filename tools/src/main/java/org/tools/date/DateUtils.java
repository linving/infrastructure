package org.tools.date;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	private static final long MILLIS_IN_A_SECOND = 1000;

	private static final long SECONDS_IN_A_MINUTE = 60;

	private static final int MONTHS_IN_A_YEAR = 12;

	/**
	 * 获得指定日期之后一段时期的日期。例如某日期之后3天的日期等
	 * 
	 * @param origDate
	 *            基准日期
	 * @param amount
	 *            时间数量
	 * @param timeUnit
	 *            时间单位，如年、月、日等。用Calendar中的常量代表 天用DATE常量即可
	 * @return {@link Date}
	 */
	public static final Date dateAfter(Date origDate, int amount, int timeUnit) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(origDate);
		calendar.add(timeUnit, amount);
		return calendar.getTime();
	}

	/**
	 * 获得指定日期之前一段时期的日期。例如某日期之前3天的日期等。
	 * 
	 * @param origDate
	 *            基准日期
	 * @param amount
	 *            时间数量
	 * @param timeUnit
	 *            时间单位，如年、月、日等。用Calendar中的常量代表 天用DATE常量即可
	 * @return {@link Date}
	 */
	public static final Date dateBefore(Date origDate, int amount, int timeUnit) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(timeUnit, -amount);
		return calendar.getTime();
	}

	/**
	 * 根据年月日构建日期对象。注意月份是从1开始计数的，即month为1代表1月份。
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月。注意1代表1月份，依此类推。
	 * @param day
	 *            日
	 * @return {@link Date}
	 */
	public static Date date(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 计算两个日期（不包括时间）之间相差的周年数
	 * 
	 * @param date1
	 *            开始时间
	 * @param date2
	 *            结束时间
	 * @return {@link Integer}
	 */
	public static int getYearDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new InvalidParameterException("date1 and date2 cannot be null!");
		}
		if (date1.after(date2)) {
			throw new InvalidParameterException("date1 cannot be after date2!");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH);
		int day1 = calendar.get(Calendar.DATE);

		calendar.setTime(date2);
		int year2 = calendar.get(Calendar.YEAR);
		int month2 = calendar.get(Calendar.MONTH);
		int day2 = calendar.get(Calendar.DATE);

		int result = year2 - year1;
		if (month2 < month1) {
			result--;
		} else if (month2 == month1 && day2 < day1) {
			result--;
		}
		return result;
	}

	/**
	 * 计算两个日期（不包括时间）之间相差的整月数
	 * 
	 * @param date1
	 *            开始时间
	 * @param date2
	 *            结束时间
	 * @return {@link Integer}
	 */
	public static int getMonthDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new InvalidParameterException("date1 and date2 cannot be null!");
		}
		if (date1.after(date2)) {
			throw new InvalidParameterException("date1 cannot be after date2!");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH);
		int day1 = calendar.get(Calendar.DATE);

		calendar.setTime(date2);
		int year2 = calendar.get(Calendar.YEAR);
		int month2 = calendar.get(Calendar.MONTH);
		int day2 = calendar.get(Calendar.DATE);

		int months = 0;
		if (day2 >= day1) {
			months = month2 - month1;
		} else {
			months = month2 - month1 - 1;
		}
		return (year2 - year1) * MONTHS_IN_A_YEAR + months;
	}

	/**
	 * 统计两个日期之间包含的天数。包含date1，但不包含date2
	 * 
	 * @param date1
	 *            开始时间
	 * @param date2
	 *            结束时间
	 * @return {@link Integer}
	 * @throws ParseException
	 */
	public static int getDayDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new InvalidParameterException("date1 and date2 cannot be null!");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date smdate;
		try {
			smdate = sdf.parse(sdf.format(date1));
			Date bdate = sdf.parse(sdf.format(date2));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 计算time2比time1晚多少分钟，忽略日期部分
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int getMinuteDiffByTime(Date time1, Date time2) {
		long startMil = 0;
		long endMil = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time1);
		calendar.set(1900, 1, 1);
		startMil = calendar.getTimeInMillis();
		calendar.setTime(time2);
		calendar.set(1900, 1, 1);
		endMil = calendar.getTimeInMillis();
		return (int) ((endMil - startMil) / MILLIS_IN_A_SECOND / SECONDS_IN_A_MINUTE);
	}

	/**
	 * 计算时间是否是同一天
	 * 
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static boolean areSameDay(Date dateA, Date dateB) {
		Calendar calDateA = Calendar.getInstance();
		calDateA.setTime(dateA);

		Calendar calDateB = Calendar.getInstance();
		calDateB.setTime(dateB);

		return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
				&& calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
				&& calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @Title: getCurrYearLast
	 * @Description: 获取某年最后一天
	 * @param date
	 * @return Date
	 */
	public static Date getCurrYearLast(Date date) {
		Calendar currCal = Calendar.getInstance();
		currCal.setTime(date);
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	private static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return currYearLast;
	}
}