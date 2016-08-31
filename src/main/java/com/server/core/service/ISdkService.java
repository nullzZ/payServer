package com.server.core.service;

import com.server.ChannelEnum;

/**
 * 
 * @author nullzZ
 *
 */
public interface ISdkService {
    /**
     * 去游戏服务器验证procteId和金额
     * 
     * @param data
     * @param originSign
     * @return
     */
    public boolean checkGProductId(String productId, int amount,String channelId,String serverId);

    /**
     * 客户端回调获取订单
     * 
     * @param channelEnum
     * @param channelId
     * @param serverId
     * @param productId
     * @param productNum
     * @param amount(分)
     * @param roleId
     * @param userId
     * @param ext
     * @param orderInfo
     * @return
     */
    public boolean clientReCall(ChannelEnum channelEnum, String channelId, String serverId, String productId,
	    int productNum, int amount, String roleId, String userId, String ext, String orderInfo);

    /**
     * 发货
     * 
     * @param orderId
     * @param channelId
     * @param productId
     * @param amount
     * @param userId
     * @param roleId
     * @param serverId
     * @param orderInfo
     * @return
     */
    public boolean dispatchOrder(long orderId, String channelId, String productId, int amount, String userId,
	    String roleId, String serverId, String orderInfo);
}
