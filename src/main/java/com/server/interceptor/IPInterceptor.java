package com.server.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.server.core.util.HttpUtil;

public class IPInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
	String ip = HttpUtil.getIp(request);
	if (ip.equals("127.0.0.1")) {
	    return true;
	} else {
	    return false;
	}
    }
}
