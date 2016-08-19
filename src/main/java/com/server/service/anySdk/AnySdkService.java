package com.server.service.anySdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.server.Config;
import com.server.util.AnySdkPayNotify;

/**
 * 
 * @author nullzZ
 *
 */
@Service
public class AnySdkService implements IAnySdkService {
    private static final Logger logger = Logger.getLogger(AnySdkService.class);

    @Override
    public boolean checkSign(String data, String originSign) {
	AnySdkPayNotify paynotify = new AnySdkPayNotify();
	/**
	 * AnySDK分配的 PrivateKey
	 * 
	 * 正式使用时记得用AnySDK分配的正式的PrivateKey
	 */
	paynotify.setPrivateKey(Config.PrivateKey);
	if (!paynotify.checkSign(data, originSign)) {
	    logger.info("[验证签名失败]" + data);
	    return false;
	}
	logger.info("[验证签名成功]" + data);
	return true;
    }

    /**
     * 将参数名从小到大排序，结果如：adfd,bcdr,bff,zx
     * 
     * @param List<String>
     *            paramNames
     */
    private void sortParamNames(List<String> paramNames) {
	Collections.sort(paramNames, new Comparator<String>() {
	    public int compare(String str1, String str2) {
		return str1.compareTo(str2);
	    }
	});
    }

    public String getValues(HttpServletRequest request) {
	Enumeration<String> requestParams = request.getParameterNames();// 获得所有的参数名
	List<String> params = new ArrayList<String>();
	while (requestParams.hasMoreElements()) {
	    params.add((String) requestParams.nextElement());
	}
	sortParamNames(params);// 将参数名从小到大排序，结果如：adfd,bcdr,bff,zx
	String paramValues = "";
	for (String param : params) {// 拼接参数值
	    // if (param.equals("sign")) {
	    // originSign = request.getParameter(param);
	    // continue;
	    // }
	    String paramValue = request.getParameter(param);
	    if (paramValue != null) {
		paramValues += paramValue;
	    }
	}

	return paramValues;
    }
}
