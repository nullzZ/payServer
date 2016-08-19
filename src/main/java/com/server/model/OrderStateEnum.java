package com.server.model;

/**
 * 
 * @author nullzZ
 *
 */
// 订单验证结果
public enum OrderStateEnum {
    FAIL(0), SUCCESS(1),;

    private int type;

    private OrderStateEnum(int type) {
	this.type = type;
    }

    public int getType() {
	return type;
    }

}
