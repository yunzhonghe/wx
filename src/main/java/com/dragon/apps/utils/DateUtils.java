package com.dragon.apps.utils;

import java.sql.Timestamp;
import java.util.Calendar;

public class DateUtils {
	public static long getCurrentTimeSeconds(){
		return System.currentTimeMillis()/1000;
	}
	
	/**
	 * 获得指定时间的之前一个星期的时间
	 * @param now
	 * @return
	 */
	public static Timestamp getFixedBeforeOneWeekDay(Timestamp now){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(now.getTime());
		cal.add(Calendar.WEEK_OF_MONTH, -1);
		return new Timestamp(cal.getTimeInMillis());
	}
	
	
	/**
	 * 获得当前时间的之前一个星期的时间
	 * @param now
	 * @return
	 */
	public static Timestamp getNowBeforeOneWeekDay(){
		Calendar cal = Calendar.getInstance();		
		cal.add(Calendar.WEEK_OF_MONTH, -1);
		return new Timestamp(cal.getTimeInMillis());
	}	
}
