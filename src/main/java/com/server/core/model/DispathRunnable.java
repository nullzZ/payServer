package com.server.core.model;

import org.apache.log4j.Logger;

import com.server.core.manager.ServerManager;
import com.server.core.service.impl.OrderService;
import com.server.core.util.SpringContextUtil;
import com.server.db.model.OrderRecord;

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
		ServerRecord server = ServerManager.getInstance().get(order.getChannelId(), order.getServerId());
		// String serverUrl =
		// Config.getServerHost(order.getSdkChannel(),
		// order.getChannelId(),
		// order.getServerId());
		// if (serverUrl == null) {
		// logger.error("发货失败",
		// new RuntimeException("订单[" + order.getOrderId() + "]的区[" +
		// order.getServerId() + "]不存在"));
		// return;
		// }

		if (server == null) {
		    logger.error("发货失败",
			    new RuntimeException("订单[" + order.getOrderId() + "]的区[" + order.getServerId() + "]不存在"));
		    return;
		}

		if (handle.sendServer(order)) {
		    order.setDispatchState(DispatchStateEnum.FINISH.getType());
		    order.setDispatchTime(System.currentTimeMillis());
		} else {
		    logger.error("[发货失败]" + order.getOrderId());
		    orderService.reDispath(order);
		}
	    } catch (Exception e) {
		logger.error("[发货异常]" + order.getOrderId(), e);
		orderService.reDispath(order);
	    }
	    orderService.updateOrder(order);
	}
    }
}
