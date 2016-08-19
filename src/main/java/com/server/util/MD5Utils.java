package com.server.util;

import java.security.MessageDigest;

/**
 * MD5加密算法
 * 
 * @author nullzZ
 * 
 */
public class MD5Utils {
    /**
     * 字符对照表
     */
    public static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
	    'f' };

    /**
     * 讲字符串加密的算法
     * 
     * @param s
     * @return
     */
    public final static String encrypt(String s) {
	try {
	    byte[] btInput = s.getBytes();
	    // 获得MD5摘要算法的 MessageDigest 对象
	    MessageDigest mdInst = MessageDigest.getInstance("MD5");
	    // 使用指定的字节更新摘要
	    mdInst.update(btInput);
	    // 获得密文
	    byte[] md = mdInst.digest();
	    // 把密文转换成十六进制的字符串形式
	    int j = md.length;
	    // System.out.println(j);
	    char str[] = new char[j * 2];
	    int k = 0;
	    // String test = "";
	    for (int i = 0; i < j; i++) {
		byte byte0 = md[i];
		// test += Integer.toHexString(byte0) + "|";
		str[k++] = hexDigits[byte0 >> 4 & 0xf];
		str[k++] = hexDigits[byte0 & 0xf];
	    }
	    // System.out.println(test);
	    return new String(str);
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public static void main(String[] args) {
	System.out.println(MD5Utils.encrypt("helloworld"));
    }
}
