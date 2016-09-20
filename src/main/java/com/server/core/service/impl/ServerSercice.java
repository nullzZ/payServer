package com.server.core.service.impl;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.server.Config;
import com.server.core.manager.ServerManager;
import com.server.core.model.ServerRecord;
import com.server.core.service.IServerService;
import com.server.core.util.HttpUtil;
import com.server.core.util.JSONUtils;

@Service
public class ServerSercice implements IServerService {
    private static final Logger logger = Logger.getLogger(ServerSercice.class);

    @Override
    public void load() {
	try {
	    JSONObject obj = HttpUtil.doPost(Config.LoginServerURL);
	    JSONArray array = obj.getJSONArray("servers");
	    if (array == null) {
		return;
	    }
	    for (int i = 0; i < array.length(); i++) {
		ServerRecord s = JSONUtils.fromJson(array.get(i).toString(), ServerRecord.class);
		ServerManager.getInstance().put(s);
	    }
	    logger.info("加载服务器成功~~~~");
	} catch (Exception e) {
	    e.printStackTrace();
	    logger.error("加载服务器失败~~~~");
	}

    }

    @Override
    public void reload() {
	ServerManager.getInstance().clear();
	this.load();
    }
}
