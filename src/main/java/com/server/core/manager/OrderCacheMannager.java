package com.server.core.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.server.db.model.OrderRecord;

/**
 * 订单Manager
 * 
 * 维护订单数据
 * 
 * @author nullzZ
 * 
 */
public class OrderCacheMannager {

    /**
     * 新订单队列
     */
    private static BlockingQueue<OrderRecord> newOrderDataQueue = new LinkedBlockingQueue<OrderRecord>();
    /**
     * 重新发货队列
     */
    private static BlockingQueue<OrderRecord> reDispathOrderDataQueue = new LinkedBlockingQueue<OrderRecord>();
    /**
     * 即将发货订单
     * 
     * 下次补发时间距离当前小于1分钟
     */
    private static BlockingQueue<OrderRecord> soonDispathOrderQueue = new LinkedBlockingQueue<OrderRecord>();

    public static BlockingQueue<OrderRecord> getNewOrderDataQueue() {
	return newOrderDataQueue;
    }

    public static BlockingQueue<OrderRecord> getReDispathOrderDataQueue() {
	return reDispathOrderDataQueue;
    }

    public static BlockingQueue<OrderRecord> getSoonDispathOrderQueue() {
	return soonDispathOrderQueue;
    }

}
