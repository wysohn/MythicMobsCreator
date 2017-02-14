package com.naver.wysohn2002.mythicmobcreator.util;

public class NumberUtil {
	public static Number toNumber(String num){
		if(num == null || num.length() < 1)
			return null;
		
		if(num.contains(".")){
			return Double.parseDouble(num);
		}else{
			return Integer.parseInt(num);
		}
	}
}
