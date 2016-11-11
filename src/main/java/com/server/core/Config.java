package com.server.core;

/**
 * @author nullzZ
 * 
 */
public class Config {
    public static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    public static final int SOON_TIME = 60;// 每次失败的递增时间
    public static final int DispatchCount = 20;// 失败发送多少次
    public static final int TemporaryOrderSize = 10000;// 临时缓存数量
}
