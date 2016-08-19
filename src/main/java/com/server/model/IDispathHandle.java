package com.server.model;

import com.server.db.model.OrderRecord;

/**
 * @author nullzZ
 * 
 */
public interface IDispathHandle {
    public boolean sendServer(OrderRecord order);
}
