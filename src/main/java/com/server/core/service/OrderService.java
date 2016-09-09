package com.server.core.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.server.ChannelEnum;
import com.server.core.Config;
import com.server.core.manager.OrderCacheMannager;
import com.server.core.model.DispatchStateEnum;
import com.server.core.model.OrderStateEnum;
import com.server.db.dao.OrderRecordMapper;
import com.server.db.model.OrderRecord;
import com.server.db.model.OrderRecordExample;

/**
 * 
 * @author nullzZ
 *
 */
@Service
public class OrderService implements IOrderService {

    private static final Logger logger = Logger.getLogger(OrderService.class);
    @Resource
    private OrderRecordMapper orderRecordMapper;

    @Override
    public OrderRecord selectOrderRecord(long orderId) {
	OrderRecord order = OrderCacheMannager.getTemporaryOrder(orderId);
	if (order == null) {
	    order = orderRecordMapper.selectByPrimaryKey(orderId);
	}
	return order;
    }

    @Override
    public List<OrderRecord> selectUnDispathOrderDataList() {
	OrderRecordExample example = new OrderRecordExample();
	example.createCriteria().andDispatchStateEqualTo(DispatchStateEnum.READY.getType())
		.andOrderStateEqualTo(OrderStateEnum.SUCCESS.getType());
	return orderRecordMapper.selectByExample(example);
    }

    /**
     * 加载没有发货成功的订单
     */
    @Override
    public void loadUnDispathOrder() {
	List<OrderRecord> unDispathOrderDataList = selectUnDispathOrderDataList();
	for (OrderRecord data : unDispathOrderDataList) {
	    OrderCacheMannager.getReDispathOrderDataQueue().offer(data);
	}

	logger.info("[加载未成功发货的订单][个数:" + OrderCacheMannager.getReDispathOrderDataQueue().size() + "]");
    }

    // @Override
    // public int selectOrderCount(long orderId, String channelId) {
    // OrderRecordExample example = new OrderRecordExample();
    // example.createCriteria().andOrderIdEqualTo(orderId).andChannelIdEqualTo(channelId);
    // return orderRecordMapper.countByExample(example);
    // }

    // @Override
    // public List<OrderRecord> selectOrder(String serverId, String roleId, long
    // startTime, long endTime) {
    // OrderRecordExample example = new OrderRecordExample();
    // example.createCriteria().andServerIdEqualTo(serverId).andRoleIdEqualTo(roleId).andCreateTimeBetween(startTime,
    // endTime);
    // return orderRecordMapper.selectByExample(example);
    // }

    @Override
    public OrderRecord createOrder(ChannelEnum channelEnum, String channelId, String serverId, long orderId,
	    String productId, int productNum, int amount, String roleId, String userId, long createTime, String ext,
	    String orderInfo) {
	OrderRecord order = new OrderRecord();
	order.setSdkChannel(channelEnum.value);
	order.setChannelId(channelId);
	order.setCreateTime(createTime);
	order.setServerId(serverId);
	order.setOrderId(orderId);
	order.setProductId(productId);
	order.setProductNum(productNum);
	order.setProductAmount(amount);
	order.setOrderExt(ext);
	order.setRoleId(roleId);
	order.setUserId(userId);
	order.setOrderInfo(orderInfo);

	order.setDispatchState(0);
	order.setDispatchCount(0);
	order.setOrderState(DispatchStateEnum.READY.getType());

	if (!this.insertDB(order)) {
	    return null;
	} else {
	    OrderCacheMannager.addTemporaryOrder(order);
	}
	return order;
    }

    @Override
    public boolean insertDB(OrderRecord order) {
	return orderRecordMapper.insertSelective(order) > 0;
    }

    /**
     * 添加一个订单任务
     * 
     * @param order
     * @return
     */
    @Override
    public boolean offerNewOrder(OrderRecord order) {
	OrderCacheMannager.getNewOrderDataQueue().offer(order);
	logger.info("[发布订单]orderId:" + order.getOrderId());
	return false;
    }

    /**
     * 更新订单
     * 
     * @param order
     * @return
     */
    @Override
    public boolean updateOrder(OrderRecord order) {
	int ret = orderRecordMapper.updateByPrimaryKeySelective(order);
	if (ret > 0) {
	    return true;
	} else {
	    logger.error("[更新订单][异常]order:" + order.getOrderId());
	}
	return false;
    }

    @Override
    public void reDispath(OrderRecord order) {
	if (order != null) {
	    if (order.getDispatchCount() > Config.DispatchCount) {
		order.setDispatchState(DispatchStateEnum.FAIL.getType());// 彻底失败了
	    } else {
		order.setDispatchState(DispatchStateEnum.READY.getType());
		order.setDispatchCount(order.getDispatchCount() + 1);
		long d = System.currentTimeMillis() + Config.SOON_TIME;
		order.setNextDispatchTime(d);
		OrderCacheMannager.getReDispathOrderDataQueue().offer(order);
	    }
	}
    }

    @Override
    public boolean checkOrder(OrderRecord order, String channelId, String productId, int amount, String userId,
	    String roleId, String serverId) {
	return order.getChannelId().equals(channelId) && order.getProductId().equals(productId)
		&& order.getProductAmount() == amount && order.getUserId().equals(userId)
		&& order.getRoleId().equals(roleId) && order.getServerId().equals(serverId);
    }

}
