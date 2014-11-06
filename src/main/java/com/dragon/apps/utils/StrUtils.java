package com.dragon.apps.utils;

public class StrUtils {
	public static final char QUOTE_MARK = '\'';
	public static boolean isEmpty(String str){
		return str==null || "".equals(str);
	}
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	public static String checkRemoveQuoteMark(String sourceStr){
		if(sourceStr.indexOf(QUOTE_MARK)>-1){
			return sourceStr.replaceAll(QUOTE_MARK+"", "");
		}
		return sourceStr;
	}
	
	public static void main(String[] args) {
		System.out.println(checkRemoveQuoteMark("abc'd''f"));
	}
}
