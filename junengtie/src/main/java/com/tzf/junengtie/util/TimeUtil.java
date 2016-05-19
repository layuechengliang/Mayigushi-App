package com.tzf.junengtie.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @ClassName      TimeUtil.java
 * @Description    TimeUtil
 * @author         tangzhifei
 * @version        V1.0  
 * @Date           2015年8月17日 下午12:03:36
 */
public class TimeUtil {

	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

	public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}
	
	public static String getTime(long timeInMillis, String format) {
		return getTime(timeInMillis, new SimpleDateFormat(format, Locale.CHINA));
	}

	public static String getTime(long timeInMillis) {
		return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
	}

	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}

	public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
		return getTime(getCurrentTimeInLong(), dateFormat);
	}

	public static long getTime(String date, String format) {
		if (StringUtil.isEmpty(date) || StringUtil.isEmpty(format)) {
			return 0;
		}

		try {
			return new SimpleDateFormat(format, Locale.CHINA).parse(date).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

}
