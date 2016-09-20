package com.server.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.server.Config;
import com.server.core.util.HttpUtil;

public class IPInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
	String ip = HttpUtil.getIp(request);
	if (Config.SafeIps.contains(ip)) {
	    return true;
	} else {
	    return false;
	}
    }
}
