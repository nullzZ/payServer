package com.server.core.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.server.core.model.ServerRecord;

/**
 * 
 * @author nullzZ
 *
 */
public class ServerManager {
    private Map<String, ServerRecord> servers = new ConcurrentHashMap<>();
    private static final ServerManager instance = new ServerManager();

    private ServerManager() {

    }

    public static ServerManager getInstance() {
	return instance;
    }

    public ServerRecord get(String serverId) {
	return servers.get(serverId);
    }

    public void put(ServerRecord s) {
	servers.put(s.getServerId(), s);
    }

    public void clear() {
	servers.clear();
    }

}
