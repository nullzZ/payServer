package com.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.server.Config;
import com.server.db.model.OrderRecord;
import com.server.manager.OrderCacheMannager;
import com.server.model.DispathRunnable;
import com.server.service.IDispatchService;

/**
 * 
 * @author nullzZ
 *
 */
@Service
public class DispatchService implements IDispatchService {

    private static Logger logger = Logger.getLogger(DispatchService.class);

    private ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(Config.THREAD_POOL_SIZE,
	    new ThreadFactory() {
		AtomicLong id = new AtomicLong();

		@Override
		public Thread newThread(Runnable r) {
		    Thread t = new Thread(r, "补发货调度线程-" + id.getAndIncrement());
		    return t;

		}
	    });

    private ExecutorService cachedThreadPool = new ThreadPoolExecutor(0, Config.THREAD_POOL_SIZE, 60L, TimeUnit.SECONDS,
	    new SynchronousQueue<Runnable>(), new ThreadFactory() {
		AtomicLong id = new AtomicLong();

		@Override
		public Thread newThread(Runnable r) {
		    Thread t = new Thread(r, "发货处理线程-" + id.getAndIncrement());
		    return t;

		}
	    });

    /**
     * 停止服务
     */
    @Override
    public void destroy() {
	scheduledThreadPool.shutdown();
	cachedThreadPool.shutdown();
    }

    /**
     * 启动服务
     */
    @Override
    public void run() {
	logger.info("发货服务[" + DispatchService.class.getName() + "]开始启动");
	Thread t = new Thread(new Runnable() {
	    @Override
	    public void run() {
		logger.info("发货调度线程 启动");
		while (true) {
		    try {
			OrderRecord newOrder = OrderCacheMannager.getNewOrderDataQueue().take();
			if (newOrder != null) {
			    /** 如果线程池关闭,则停止发货 */
			    if (!cachedThreadPool.isShutdown()) {
				dispath(newOrder);
			    } else {
				logger.info("发货线程池[cachedThreadPool]停止");
				return;
			    }
			}
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    }
	}, "发货调度线程");
	t.start();

	scheduledThreadPool.scheduleWithFixedDelay(new Runnable() {
	    @Override
	    public void run() {
		checkSoonDispatchQueue();
	    }
	}, 5, 10, TimeUnit.SECONDS);

	scheduledThreadPool.scheduleWithFixedDelay(new Runnable() {
	    @Override
	    public void run() {
		checkReDispatchQueue();
	    }
	}, 60, 60, TimeUnit.SECONDS);
	logger.info("发货服务[" + DispatchService.class.getName() + "]启动完成");
    }

    private List<OrderRecord> getSoonDispathOrderList() {
	List<OrderRecord> orderList = new ArrayList<>();
	int size = OrderCacheMannager.getSoonDispathOrderQueue().size();
	for (int i = 0; i < size; i++) {
	    OrderRecord poll = OrderCacheMannager.getSoonDispathOrderQueue().poll();
	    if (poll != null) {
		orderList.add(poll);
	    }
	}
	return orderList;

    }

    private void checkSoonDispatchQueue() {
	logger.debug("检查即将发货订单");
	List<OrderRecord> soonDispathOrderList = getSoonDispathOrderList();
	if (soonDispathOrderList.isEmpty()) {
	    logger.debug("无即将发货订单");
	    return;
	}
	// 发货的订单数
	int dispatchCount = 0;
	// 即将发货的订单数
	int soonCount = 0;
	// 无效的订单数
	int invalidCount = 0;
	long now = System.currentTimeMillis();
	for (OrderRecord orderData : soonDispathOrderList) {
	    if (orderData != null) {
		if (now >= orderData.getNextDispatchTime()) {
		    dispath(orderData);
		    dispatchCount++;
		} else {
		    OrderCacheMannager.getSoonDispathOrderQueue().offer(orderData);
		    soonCount++;
		}
	    } else {
		invalidCount++;
	    }
	}
	logger.info("检查即将发货订单完成\n总订单[" + soonDispathOrderList.size() + "]\n发货[" + dispatchCount + "]\n等待发货[" + soonCount
		+ "]\n无效订单[" + invalidCount + "]");
    }

    private List<OrderRecord> getReDispathOrderList() {
	List<OrderRecord> orderList = new ArrayList<>();
	int size = OrderCacheMannager.getReDispathOrderDataQueue().size();
	for (int i = 0; i < size; i++) {
	    OrderRecord poll = OrderCacheMannager.getReDispathOrderDataQueue().poll();
	    if (poll != null) {
		orderList.add(poll);
	    }
	}
	return orderList;
    }

    private void checkReDispatchQueue() {
	logger.debug("检查重新发货订单");
	long now = System.currentTimeMillis();
	List<OrderRecord> reDispathOrderList = getReDispathOrderList();
	if (reDispathOrderList.isEmpty()) {
	    logger.debug("无重新发货订单");
	    return;
	}
	// 即将发货的订单数
	int soonCount = 0;
	// 重新发货的订单数
	int redispatchCount = 0;
	// 无效的订单数
	int invalidCount = 0;
	for (OrderRecord orderData : reDispathOrderList) {
	    if (orderData != null) {
		if (orderData.getNextDispatchTime() - now <= Config.SOON_TIME * 1000) {
		    OrderCacheMannager.getSoonDispathOrderQueue().offer(orderData);
		    soonCount++;
		} else {
		    OrderCacheMannager.getReDispathOrderDataQueue().offer(orderData);
		    redispatchCount++;
		}
	    } else {
		invalidCount++;

	    }
	}
	logger.info("检查重新发货订单完成\n总订单[" + reDispathOrderList.size() + "]\n即将发货[" + soonCount + "]\n等待发货["
		+ redispatchCount + "]\n无效订单[" + invalidCount + "]");
    }

    /**
     * 发货
     */
    @Override
    public void dispath(OrderRecord orderData) {
	if (orderData != null) {
	    cachedThreadPool.submit(new DispathRunnable(orderData));
	}
    }

}
