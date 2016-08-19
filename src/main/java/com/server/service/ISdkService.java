package com.server.service;

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
    public boolean checkGProductId(String productId, String amount);
}
