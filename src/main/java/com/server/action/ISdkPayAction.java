package com.server.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author nullzZ
 * 
 */
public interface ISdkPayAction {

    /**
     * 客户端回调
     * 
     * @param request
     * @param response
     */
    public void clientReCall(HttpServletRequest request, HttpServletResponse response);

    /**
     * 第三方SDK回调
     * 
     * @param request
     * @param response
     */
    public void reCall(HttpServletRequest request, HttpServletResponse response);
}
