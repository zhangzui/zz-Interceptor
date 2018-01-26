package com.zz.schedule.interceptor.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 吴海旭
 */
public class TimeUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeUtils.class);
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String COMMON_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String yyMMddHHmmss = "yyMMddHHmmss";

	public static long betweenMills(Date fromDate, Date toDate) {
		return toDate.getTime() - fromDate.getTime();
	}

	public static Date parseDate(String format, String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateStr);
	}

	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(COMMON_FORMAT);
		return sdf.format(date);
	}

	public static String formatDate(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String formatDateNow() {
		SimpleDateFormat sdf = new SimpleDateFormat(yyyyMMddHHmmss);
		return sdf.format(new Date());
	}

	public static String formatDateTodayDay() {
		SimpleDateFormat sdf = new SimpleDateFormat(yyyyMMdd);
		return sdf.format(new Date());
	}

	public static String formatDateLimitInHours(Date date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, hours * -1);
		return formatDate(yyyy_MM_dd, cal.getTime());
	}

	/**
	 * 根据时间条件构造Date对象
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getDate(int year, int month, int day) {
		return getDate(year, month, day, 0, 0, 0);
	}

	/**
	 * 根据时间条件构造Date对象
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, hour, minute, second);
		return calendar.getTime();
	}

	/**
	 * 获得最新账单日期（已出）
	 * 
	 * @param statementDay
	 * @return
	 */
	public static Date getLastestBilledStatementDate(int statementDay) {
		Date current = new Date();
		Date statementDate = getStatementDate(statementDay);
		if (current.before(statementDate)) {
			return minusMonth(statementDate, 1);
		}
		return statementDate;
	}
	
	
	/**
	 * 获取即将出账的账单日
	 * 
	 * @param statementDay
	 * @return
	 */
	public static Date getBillingStatementDate(int statementDay) {
		Date cur = getCurDate();
		Date statementDate = getStatementDate(statementDay);
		if (cur.after(statementDate) || cur.equals(statementDate)) {
			return addMonth(statementDate, 1);
		}
		return statementDate;
	}

	/**
	 * 当月出账日
	 * 
	 * @param statementDay
	 * @return
	 */
	public static Date getStatementDate(int statementDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		String result = String.format("%s-%02d-%02d", year, month, statementDay);
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	

	/**
	 * 上个账单日
	 * 
	 * @param
	 * @return
	 */
	public static Date getPreStatementDate(int statementDay) {
		Date currentBillStatementDate = getLastestBilledStatementDate(statementDay);
		return minusMonth(currentBillStatementDate, 1);
	}

	/**
	 * 下个账单
	 * @param statementDay
	 * @return
	 */
	public static Date getNextStatementDate(int statementDay) {
		Date currentBillStatementDate = getLastestBilledStatementDate(statementDay);
		return addMonth(currentBillStatementDate, 1);
	}
	
	
	public static Date getPlanOrderStatementDate(int statementDate, Date planDate){
		Calendar c = Calendar.getInstance();
		c.setTime(planDate);
		int day=getDay(planDate);
		int month = 0;
		if(day<statementDate){
			month = c.get(Calendar.MONTH) + 1;
		}else {
			month = c.get(Calendar.MONTH) + 2;
		}
		int year = c.get(Calendar.YEAR);
		String result = String.format("%s-%02d-%02d", year, month, statementDate);
		Date date = null;
		try {
			date = parseDate(yyyy_MM_dd, result);
		} catch (ParseException e) {
			LOGGER.error("parse date error", e);
		}
		return date;
	}
	
	
	/**
	 * 根据账单日获取还款日(相对于账单日来说)---测试跨年情况
	 * @param statementDay
	 * @param repaymentDay
	 * @return
	 */
	public static Date getRepaymentDate(Date statementDay, int repaymentDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(statementDay);
		int month = 0;
		//还款日在账单日之后
		int day=getDay(statementDay);
		if(day<repaymentDay){
			month = c.get(Calendar.MONTH) + 1;
		}else {
			month = c.get(Calendar.MONTH) + 2;
		}
		int year = c.get(Calendar.YEAR);
		String result = String.format("%s-%02d-%02d", year, month, repaymentDay);
		Date date = null;
		try {
			date = parseDate(yyyy_MM_dd, result);
		} catch (ParseException e) {
			LOGGER.error("parse date error", e);
		}
		return date;
	}
	
	public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        return day;
    }



	public static Date minusDays(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(5, c.get(5) - day);
		return c.getTime();
	}
	
	public static Date addDays(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(5, c.get(5) + day);
		return c.getTime();
	}
	
	/**
	 * add month to date
	 * @param date
	 * @param i number of month
	 * @return
	 */
	public static Date addMonth(Date date, int i) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, i);
		return c.getTime();
	}
	
	/**
	 * minus month to date
	 * @param date
	 * @param i number of month
	 * @return
	 */
	public static Date minusMonth(Date date, int i) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -i);
		return c.getTime();
	}
	
	/**
	 * 添加1s
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date addSeconds(Date date, int seconds) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);    
	    calendar.add(Calendar.SECOND, seconds);
	    return calendar.getTime();    
	}    
	
	
	/**
	 * 添加1s
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date minusSeconds(Date date, int seconds) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);    
	    calendar.add(Calendar.SECOND, -seconds);
	    return calendar.getTime();    
	}    
	
	/**
	 * 添加小时
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date addHours(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);    
	    calendar.add(Calendar.HOUR, hours);
	    return calendar.getTime();    
	}    
	
	public static Date minusHours(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);    
	    calendar.add(Calendar.HOUR, -hours);
	    return calendar.getTime();    
	}    

	public static Date getCurDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 0);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(calendar.getTime());
		Date date = null;
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static int getIntToday() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DATE);
		return day;
	}

	public static Date getCurTime() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	
	public static String getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		String dateString = formatter.format(c.getTime());
		return dateString;
	}

	/**
	 * 相差几天
	 * 
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static int diffDay(java.util.Date firstDate, java.util.Date lastDate) {
		try {
			firstDate = parseDate(yyyyMMdd,formatDate(yyyyMMdd, firstDate));
			lastDate = parseDate(yyyyMMdd,formatDate(yyyyMMdd, lastDate));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		int day = (int) ((firstDate.getTime() - lastDate.getTime()) / 1000L / 86400L);
		return day;
	}


	public static int diffMonth(java.util.Date firstDate, Date lastDate) {
		if (firstDate.after(lastDate)) {
			java.util.Date t = firstDate;
			firstDate = lastDate;
			lastDate = t;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(firstDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(lastDate);
		Calendar temp = Calendar.getInstance();
		temp.setTime(lastDate);
		temp.add(5, 1);

		int year = endCalendar.get(1) - startCalendar.get(1);
		int month = endCalendar.get(2) - startCalendar.get(2);

		if ((startCalendar.get(5) == 1) && (temp.get(5) == 1)) {
			return year * 12 + month + 1;
		}
		if ((startCalendar.get(5) != 1) && (temp.get(5) == 1)) {
			return year * 12 + month;
		}
		if ((startCalendar.get(5) == 1) && (temp.get(5) != 1)) {
			return year * 12 + month;
		}
		return year * 12 + month - 1 < 0 ? 0 : year * 12 + month;
	}

	/**
	 * 根据时间戳获取score值
	 * @param timestamp
	 * @return
	 */
	public static double getDoubleFromTimestamp(long timestamp) {
		String str = String.valueOf(timestamp);
		return Double.valueOf(str.substring(0,6).concat(".").concat(str.substring(6,13)));
	}
	
	

	public static void main(String[] args) {
		System.out.println(TimeUtils.getPlanOrderStatementDate(27, new Date()));
	}
}
