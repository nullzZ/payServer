package com.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nullzZ
 */
public class UqIdUtil {

    private static int count = 0;
    private static int max = 10000;
    private static Object lock = new Object();

    /**
     * 生成id---年(2)+月(2)+日(2)+时(2)+分(2)+秒(2)=12 + 4位自增</br>
     * 1秒钟不产生最大订单数10000
     * 
     * @param channelId
     * @param serverId
     * @return
     */
    public static long buildUqId() {
	StringBuilder key = new StringBuilder();
	key.append(new SimpleDateFormat("yyMMddHHmmss").format(new Date()).toString());
	synchronized (lock) {
	    String s = String.format("%04d", count);
	    key.append(s);
	    if (count >= max) {
		count = 0;
	    }
	    count++;
	}
	return Long.parseLong(key.toString());
    }

    public static void main(String[] args) {
	for (int i = 0; i <= 100; i++) {
	    System.out.println(buildUqId());
	}

    }
}
