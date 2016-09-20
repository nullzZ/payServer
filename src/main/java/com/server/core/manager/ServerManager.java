package com.server.core.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.server.core.model.ServerRecord;

/**
 * 
 * @author nullzZ
 *
 */
public class ServerManager {
    private ConcurrentHashMap<String, HashMap<String, ServerRecord>> cach = new ConcurrentHashMap<>();
    private List<ServerRecord> servers = new ArrayList<>();
    private static final ServerManager instance = new ServerManager();

    private ServerManager() {

    }

    public static ServerManager getInstance() {
	return instance;
    }

    public ServerRecord get(String channelId, String serverId) {
	HashMap<String, ServerRecord> servers = cach.get(channelId);
	if (servers == null) {
	    return null;
	}
	return servers.get(serverId);
    }

    public void put(ServerRecord s) {
	HashMap<String, ServerRecord> map = cach.get(s.getChannelId());
	if (map == null) {
	    map = new HashMap<>();
	    cach.put(s.getChannelId(), map);
	}
	map.put(s.getServerId(), s);
	servers.add(s);
    }

    public List<ServerRecord> getServers() {
	return servers;
    }

    public void clear() {
	cach.clear();
	servers.clear();
    }

}
