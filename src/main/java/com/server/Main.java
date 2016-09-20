package com.server;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.server.core.manager.DispathHandleManager;
import com.server.core.service.impl.DispatchService;
import com.server.core.service.impl.OrderService;
import com.server.core.service.impl.ServerSercice;
import com.server.sdkImpl.anySdk.AnySdkDispathHandle;

/**
 * 
 * @author nullzZ
 *
 */
@Service
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    @Resource
    private OrderService orderService;
    @Resource
    private DispatchService dispatchService;
    @Resource
    private ServerSercice serverSercice;

    @PostConstruct
    public void init() {
	try {
	    serverSercice.load();
	    orderService.loadUnDispathOrder();
	    dispatchService.run();

	    DispathHandleManager.getInstance().register(ChannelEnum.ANY_SDK, new AnySdkDispathHandle());
	    logger.info("[启动]充值服务器启动成功-------------!");
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(0);
	}
    }

    @PreDestroy
    public void stop() {
	dispatchService.destroy();
    }

}
