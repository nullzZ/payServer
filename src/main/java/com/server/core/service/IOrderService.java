package com.server.core.service;

/**
 * 
 * @author nullzZ
 *
 */
import java.util.List;

import com.server.ChannelEnum;
import com.server.db.model.OrderRecord;

public interface IOrderService {

    public OrderRecord selectOrderRecord(long orderId);

    /**
     * 查询没有发货成功的订单列表
     */
    public List<OrderRecord> selectUnDispathOrderDataList();

    public void loadUnDispathOrder();

    // public int selectOrderCount(long orderId, String channelId);

    // public List<OrderRecord> selectOrder(String serverId, String roleId, long
    // startTime, long endTime);

    /**
     * 
     * @param channelId
     * @param serverId
     * @param orderId
     * @param productId
     * @param productNum
     * @param amount(分)
     * @param roleId
     * @param userId
     * @param createTime
     * @param ext
     *            扩展字段
     * @param orderInfo
     *            订单详情
     * @return
     */
    public OrderRecord createOrder(ChannelEnum channelEnum, String channelId, String serverId, long orderId,
	    String productId, int productNum, int amount, String roleId, String userId, long createTime, String ext,
	    String orderInfo);

    /**
     * 检查有效性
     * 
     * @param order
     * @param channelId
     * @param productId
     * @param amount(分)
     * @return
     */
    public boolean checkOrder(OrderRecord order, String channelId, String productId, int amount, String userId,
	    String roleId, String serverId);

    public boolean offerNewOrder(OrderRecord order);

    public boolean updateOrder(OrderRecord order);

    public void reDispath(OrderRecord order);

    public boolean insertDB(OrderRecord order);

    /**
     * 订单补偿
     * 
     * @param channelEnum
     * @param channelId
     * @param serverId
     * @param roleId
     * @param userId
     * @param amount
     * @return
     */
    public boolean compensateOrder(ChannelEnum channelEnum, String channelId, String serverId, String roleId,
	    String userId, int amount);
}
