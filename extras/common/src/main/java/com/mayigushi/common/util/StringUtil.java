package com.mayigushi.common.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName      StringUtil.java
 * @Description    StringUtil
 * @author         tangzhifei
 * @version        V1.0  
 * @Date           2015年8月8日 下午5:38:27
 */
public class StringUtil {
	
	public static final String EMPTY_STRING = ""; // 空串
	
	public static final String BLANK_SPACE = " "; // 空格
	
	public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

	public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static boolean isEquals(String actual, String expected) {
        return ObjectUtil.isEquals(actual, expected);
    }

    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }
    
    /**
	 * 去除字符串左侧空格
	 * @param source
	 * @return
	 */
    public static String trimLeft(String source) {
		if (isEmpty(source)) {
			return EMPTY_STRING;
		} else {
			int index = 0;
			for (int i = 0; i < source.length(); i++) {
				if (Character.isWhitespace(source.charAt(i))) {
					index = i + 1;
				} else {
					break;
				}
			}
			return index != 0 ? source.substring(index) : source;
		}
	}

    /**
     * 获取字符长度，中文2其他1
     * @param str
     * @return
     */
    public static final int getCharacterLength(String str){
        if(TextUtils.isEmpty(str)){
            return 0;
        }
        int length = 0;
        for(int i = 0 ; i < str.length(); i++){
            if(isChinese(str.charAt(i))){
                length += 2;
            }else{
                length += 1;
            }
        }
        return length;
    }
    /**
     * 是否是汉字
     *
     * @param c
     * @return
     */
    private static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                ||ub== Character.UnicodeBlock.PRIVATE_USE_AREA) {
            return true;
        }
        return false;
    }

    /**
     * 判定输入汉字 英文 数字
     * @param string
     * @return
     */
    public static boolean isChineseLetterAndDigit(String string) {
        return isStringMatch(string,"[\u2E80-\uFE4FA-Za-z0-9]+");
    }

    public static boolean isStringMatch(String string,String reg){
        Pattern pattern = Pattern.compile(reg);
        Matcher isNum = pattern.matcher(string);
        return isNum.matches();
    }

    public static String filterMoney(String money) {
        return !StringUtil.isEmpty(money) ? money.replaceAll(",", ".") : EMPTY_STRING;
    }

}
