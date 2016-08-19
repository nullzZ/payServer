package com.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author nullzZ
 */
public class UqIdUtil {

    private static AtomicInteger count = new AtomicInteger();
    private static int max = 10000;

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
	int num = count.getAndIncrement();
	String s = String.format("%04d", num);
	key.append(s);
	if (count.get() >= max) {
	    count.set(0);
	}
	return Long.parseLong(key.toString());
    }

    public static void main(String[] args) {
	for (int i = 0; i <= 100; i++) {
	    System.out.println(buildUqId());
	}

    }
}
