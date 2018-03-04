package com.ibm.cdl.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateUtils extends org.apache.commons.lang.time.DateUtils {
	private DateUtils() {
		super();
	}

	/**
	 * 根据指定格式 格式化日期
	 * @param date
	 * @param parsePatterns
	 * @return
	 */
	public static String parseDate(Date date, String parsePatterns) {
		SimpleDateFormat sdf = new SimpleDateFormat(parsePatterns);
		return sdf.format(date);
	}
	
	public static Timestamp paseTimestamp(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt=null;
		try {
			dt = sdf.parse(time);
		} catch (ParseException e) {
			dt=new Date();
			e.printStackTrace();
		}
		return new Timestamp(dt.getTime());
	}

	public static List<String> dayX(String dateParam) throws ParseException {
		List<String> strList = new ArrayList<String>();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(dateParam);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		for(int i=0; i<15;i++){
			String day = "";
			rightNow.add(Calendar.DAY_OF_YEAR,-1);
			day =sdf.format(rightNow.getTime());
			strList.add(day);
		}
		return strList;
	}

	public static List<String> monthX(String dateParem){
		List<String> strList = new ArrayList<String>();
		return strList;
	}

	public static String parseDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static String parseSqlDate(Timestamp time) {
		if(time!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(new Date(time.getTime()));
		}else{
			return "";
		}
	}
	
	public static String parseSqlDates(Timestamp time) {
		if(time!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.format(new Date(time.getTime()));
		}else{
			return "";
		}
	}
	
	public static String parseSqlDatesByS(Timestamp time) {
		if(time!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(new Date(time.getTime()));
		}else{
			return "";
		}
	}
	
 
	/**
	 * 指定日期的开始时刻
	 * 
	 * 返回日期的格式
	 * 	yyyy-MM-dd 00:00:00
	 */
	public static Date getBeginOfDay(Date date)
	{
		Calendar today = Calendar.getInstance();
		today.setTime(date);
		return truncate(today, Calendar.DATE).getTime();
	}
	
	/**
	 * 指定日期的结束时刻
	 * 
	 * 返回日期的格式
	 * 	yyyy-MM-dd 23:59:59
	 */
	public static Date getEndOfDay(Date date)
	{
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTime(date);
		tomorrow.add(Calendar.DATE, 1);
		tomorrow = truncate(tomorrow, Calendar.DATE);
		tomorrow.add(Calendar.MILLISECOND, -1);
		return tomorrow.getTime();
	}
	 
	/**
	 * 指定日期的月份开始时刻
	 * 
	 * @return
	 * 	yyyy-MM-01 00:00:00
	 */
	public static Date getBeginOfMonth(Date date)
	{
		 
		Calendar today = Calendar.getInstance();
		today.setTime(date);
		return truncate(today, Calendar.MONTH).getTime();
	}
	/**
	 * 指定日期的月份结束时刻
	 *  
	 * 	yyyy-MM-dd 23:59:59
	 * dd 取值28,30,31
	 */
	public static Date getEndOfMonth(Date date)
	{
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTime(date);
		tomorrow.add(Calendar.MONTH, 1);
		tomorrow = truncate(tomorrow, Calendar.MONTH);
		tomorrow.add(Calendar.SECOND, -1);
		return tomorrow.getTime();
	}
	
	
	public static Timestamp getNowTime(){
		return new Timestamp(System.currentTimeMillis());
	}

	public static String parseNotSecondDate(Date date) {
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.format(date);
		}else{
			return "";
		}
	}
	
	public static Date getCurrentDate() {
		Calendar date = Calendar.getInstance();
		int day = date.get(Calendar.DAY_OF_MONTH);
		int month = date.get(Calendar.MONTH) + 1;
		int year = date.get(Calendar.YEAR);
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		Date currdate=null;
		try {
			currdate = dateFormat1.parse(year+"-"+month+"-"+day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currdate;
	}
	
	public static int getCurrentYear() {
		Calendar date = Calendar.getInstance();
		return date.get(Calendar.YEAR);
	}
	
	/**
	 * 默认格式化(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String str, String parsePatterns) throws ParseException {
		return DateUtils.parseDate(str, new String[] { parsePatterns });
	}
	/**
	 * 获取周几
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK)-1;
	}
	/**
	 * 获取上月的时间
	 * @param date
	 * @return
	 */
	public static Date getLastDate(Date date) {  
		
		Calendar cal = Calendar.getInstance();        
		cal.setTime(date);          
		cal.add(Calendar.MONTH, -1);     
		
        return cal.getTime();  
     } 
	
	
	public static void main(String[] args) throws ParseException {
		String day = "2016-06-20";
		List<String> strList = dayX(day);
		for(String s:strList){
			System.out.println(s);
		}
	}
}
