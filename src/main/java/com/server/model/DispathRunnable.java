package com.server.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import com.server.ChannelEnum;
import com.server.Config;
import com.server.SpringContextUtil;
import com.server.db.model.OrderRecord;
import com.server.service.impl.OrderService;

/**
 * 
 * @author nullzZ
 *
 */
public class DispathRunnable implements Runnable {
    private static Logger logger = Logger.getLogger(DispathRunnable.class);

    private OrderRecord order;
    private IDispathHandle handle;

    public DispathRunnable(OrderRecord order, IDispathHandle handle) {
	this.order = order;
	this.handle = handle;
    }

    @Override
    public void run() {
	if (this.order != null) {
	    OrderService orderService = (OrderService) SpringContextUtil.getBean("orderService");
	    try {
		String serverUrl = Config.getServerHost(order.getSdkChannel(), order.getChannelId(),
			order.getServerId());
		if (serverUrl == null) {
		    logger.error("发货失败",
			    new RuntimeException("订单[" + order.getOrderId() + "]的区[" + order.getServerId() + "]不存在"));
		    return;
		}

		if (handle.sendServer(order)) {
		    order.setDispatchState(DispatchStateEnum.FINISH.getType());
		    order.setDispatchTime(System.currentTimeMillis());
		} else {
		    orderService.reDispath(order);
		}
	    } catch (Exception e) {
		orderService.reDispath(order);
	    }
	    orderService.updateOrder(order);
	}
    }
}
