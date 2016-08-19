package com.server.service;

/**
 * 
 * @author nullzZ
 *
 */
import com.server.db.model.OrderRecord;

public interface IDispatchService {
    public void destroy();

    public void run();

    public void dispath(OrderRecord orderData);
}
