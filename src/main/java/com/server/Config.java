package com.server;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * 
 * @author nullzZ
 *
 */
public class Config {
    public static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    public static final int SOON_TIME = 60;
    public static final int DispatchCount = 100;

    public static final String KEY = "fjalwrjfkfj4723894723";
    public static final String VALUE = "235sgdsgds435435567df";
    public static final int PORT = 11102;

    private static String CONFIG_PATH = System.getProperty("myConfig");
    private static PropertiesConfiguration SERVERS_CONFIG;

    public static final String PrivateKey = "";

    public static void loadServers() throws ConfigurationException {
	SERVERS_CONFIG = new PropertiesConfiguration(CONFIG_PATH + "/safeRemoteHost.properties");
	SERVERS_CONFIG.setReloadingStrategy(new FileChangedReloadingStrategy());// 自动重载
    }

    public static String getServerHost(int sdkChannel, String channelId, String serverId) {
	return SERVERS_CONFIG.getString(sdkChannel + "_" + channelId + "_" + serverId);
    }
}
