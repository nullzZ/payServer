package com.server.model;

/**
 * 
 * @author nullzZ
 * 
 *         发货状态
 */
public enum DispatchStateEnum {
    READY(0), // 准备中
    FINISH(1), // 发送完成
    FAIL(2);// 发送失败

    private int type;

    private DispatchStateEnum(int type) {
	this.type = type;
    }

    public int getType() {
	return type;
    }

}
