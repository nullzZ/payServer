package com.server.core.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.server.ChannelEnum;
import com.server.core.model.OrderStateEnum;
import com.server.core.service.ISdkService;
import com.server.core.util.UqIdUtil;
import com.server.db.model.OrderRecord;

/**
 * @author nullzZ
 * 
 */
public abstract class AbsSdkService implements ISdkService {

    private static final Logger logger = Logger.getLogger(AbsSdkService.class);
    @Resource
    private OrderService orderService;

    @Override
    public long clientReCall(ChannelEnum channelEnum, String channelId, String serverId, String productId,
	    int productNum, int amount, String roleId, String userId, String ext, String orderInfo) {

	long orderId = UqIdUtil.buildUqId();// 生成订单ID
	boolean checkRet = this.checkGProductId(productId, amount, channelId, serverId);
	if (!checkRet) {
	    logger.error("[订单异常][产品id,或金额异常]orderId:" + orderId + "|productId:" + productId + "|amount" + amount);
	    return -1;
	}

	OrderRecord order = orderService.createOrder(channelEnum, channelId, serverId, orderId, productId, 1, amount,
		roleId, userId, System.currentTimeMillis(), ext, orderInfo);// 创建订单
	if (order == null) {
	    logger.error("[创建订单失败]" + orderId);
	    return -1;
	}
	return orderId;
    }

    @Override
    public boolean dispatchOrder(long orderId, String channelId, String productId, int amount, String userId,
	    String roleId, String serverId, String orderInfo) {
	OrderRecord order = orderService.selectOrderRecord(orderId);
	if (null == order) {
	    logger.error("[订单异常][订单不存在]orderId:" + orderId);
	    return false;
	}

	order.setOrderInfo(orderInfo);// 保存一下详细数据
	if (!orderService.checkOrder(order, channelId, productId, amount, userId, roleId, serverId)) {
	    String payDes = "[订单异常][产品id,或金额异常]orderId:" + order.getOrderId() + "|productId:" + productId + "|amount"
		    + amount;
	    order.setPaydes(payDes);
	    orderService.updateOrder(order);
	    logger.error(payDes);
	    return false;
	}
	order.setOrderState(OrderStateEnum.SUCCESS.getType());
	if (orderService.updateOrder(order)) {
	    orderService.offerNewOrder(order);
	} else {
	    return false;
	}

	return true;
    }
}
