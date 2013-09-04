package com.hck.player.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StringUtils {
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
	public final static String EMPTY = "";

	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 * @return 例如2011-3-24
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_DATE_PATTERN);
	}

	/**
	 * 获取当前时间 格式为yyyy-MM-dd 例如2011-07-08
	 * 
	 * @return
	 */
	public static String getDate() {
		return formatDate(new Date(), DEFAULT_DATE_PATTERN);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getDateTime() {
		return formatDate(new Date(), DEFAULT_DATETIME_PATTERN);
	}

	/**
	 * 格式化日期时间字符串
	 * 
	 * @param date
	 * @return 例如2011-11-30 16:06:54
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, DEFAULT_DATETIME_PATTERN);
	}

	public static String join(final ArrayList<String> array, String separator) {
		StringBuffer result = new StringBuffer();
		if (array != null && array.size() > 0) {
			for (String str : array) {
				result.append(str);
				result.append(separator);
			}
			result.delete(result.length() - 1, result.length());
		}
		return result.toString();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	//	public static String trim(String str) {
	//		if (IsUtil.isNullOrEmpty(str)) {
	//			return "";
	//		}
	//		return str.trim();
	//	}
	//
	//	/** 将中文转换成unicode编码 */
	//	public static String gbEncoding(final String gbString) {
	//		char[] utfBytes = gbString.toCharArray();
	//		String unicodeBytes = "";
	//		for (char utfByte : utfBytes) {
	//			String hexB = Integer.toHexString(utfByte);
	//			if (hexB.length() <= 2) {
	//				hexB = "00" + hexB;
	//			}
	//			unicodeBytes = unicodeBytes + "\\u" + hexB;
//		}
	//		//System.out.println("unicodeBytes is: " + unicodeBytes);  
	//		return unicodeBytes;
	//	}
	//
	//	/** 将unicode编码转换成中�?*/
	//	public static String decodeUnicode(final String dataStr) {
	//		int start = 0;
	//		int end = 0;
	//		final StringBuffer buffer = new StringBuffer();
	//		while (start > -1) {
	//			end = dataStr.indexOf("\\u", start + 2);
//			String charStr = "";
	//			if (end == -1) {
	//				charStr = dataStr.substring(start + 2, dataStr.length());
	//			} else {
	//				charStr = dataStr.substring(start + 2, end);
	//			}
	//			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串�?  
	//			buffer.append(new Character(letter).toString());
	//			start = end;
	//		}
	//		//System.out.println(buffer.toString());
	//		return buffer.toString();
	//	}

}
