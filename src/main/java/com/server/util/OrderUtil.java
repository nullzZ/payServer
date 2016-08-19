package com.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author nullzZ
 */
public class OrderUtil {

    private static AtomicInteger count = new AtomicInteger();
    private static int max = 10000;

    /**
     * 生成订单---年(2)+月(2)+日(2)+时(2)+分(2)+秒(2)=12 + 4位自增</br>
     * 1秒钟不产生最大订单数10000
     * 
     * @param channelId
     * @param serverId
     * @return
     */
    public static long buildOrderId() {
	StringBuilder key = new StringBuilder();
	key.append(new SimpleDateFormat("yyMMddHHmmss").format(new Date()).toString());
	key.append(count.getAndIncrement());
	if (count.get() >= max) {
	    count.set(0);
	}
	return Long.parseLong(key.toString());
    }

    public static void main(String[] args) {
	for (int i = 0; i < 10000; i++) {
	    System.out.println(buildOrderId());
	}

    }
}
