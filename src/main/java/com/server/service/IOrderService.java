package com.server.service;

/**
 * 
 * @author nullzZ
 *
 */
import java.util.List;

import com.server.db.model.OrderRecord;

public interface IOrderService {

    public void loadOrderCount();

    public OrderRecord selectOrderRecord(long orderId);

    /**
     * 查询没有发货成功的订单列表
     */
    public List<OrderRecord> selectUnDispathOrderDataList();

    public void loadUnDispathOrder();

    public int selectOrderCount(long orderId, String channelId);

    public List<OrderRecord> selectOrder(String serverId, String roleId, long startTime, long endTime);

    /**
     * 创建订单
     * 
     * @param channelId
     * @param serverId
     * @param orderId
     * @param productId
     * @param roleId
     * @param userId
     * @param createTime
     * @param ext
     *            扩展字段
     * @param orderInfo
     *            订单详情
     * @return
     */
    public OrderRecord createOrder(String channelId, String serverId, long orderId, String productId, int productNum,
	    String roleId, String userId, long createTime, String ext, String orderInfo);

    public boolean offerNewOrder(OrderRecord order);

    public boolean updateOrder(OrderRecord order);

    public void reDispath(OrderRecord order);

    public boolean insertDB(OrderRecord order);
}
