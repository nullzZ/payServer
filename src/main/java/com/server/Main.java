package com.server;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.server.service.impl.DispatchService;
import com.server.service.impl.OrderService;
import com.server.service.impl.ProductService;

/**
 * 
 * @author nullzZ
 *
 */
@Service
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    @Resource
    private ProductService productService;
    @Resource
    private OrderService orderService;
    @Resource
    private DispatchService dispatchService;

    @PostConstruct
    public void init() {
	try {
	    Config.loadServers();
	    productService.load();
	    orderService.loadUnDispathOrder();
	    orderService.loadOrderCount();
	    dispatchService.run();
	    logger.info("[启动]充值服务器启动成功-------------!");
	} catch (ConfigurationException e) {
	    e.printStackTrace();
	    System.exit(0);
	}
    }

}
