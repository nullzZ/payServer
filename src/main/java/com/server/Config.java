package com.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * 
 * @author nullzZ
 *
 */
public class Config {

    public static final String KEY = "fjalwrjfkfj4723894723";
    public static final String VALUE = "235sgdsgds435435567df";
    public static final String PrivateKey = "";
    public static final String LoginServerURL = "http://127.0.0.1:8080/loginServer/s/servers";
    private static String CONFIG_PATH = System.getProperty("myPayConfig");
    public static List<String> SafeIps = new ArrayList<>();

    public static void loadSafeIp() throws ConfigurationException {
	PropertiesConfiguration SAFEREMOTEHOST_CONFIG = new PropertiesConfiguration(
		CONFIG_PATH + "/safeRemoteHost.properties");
	SAFEREMOTEHOST_CONFIG.setReloadingStrategy(new FileChangedReloadingStrategy());// 自动重载
	String[] ips = SAFEREMOTEHOST_CONFIG.getStringArray("ip");
	for (String s : ips) {
	    SafeIps.add(s);
	}
    }

}
