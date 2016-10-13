package com.mark.ifamily;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * Date: 13-8-26 上午9:31
 */
public class IpManagementService {
    static final Logger logger = LoggerFactory.getLogger(IpManagementService.class);

    private Map<String, String> map = new ConcurrentHashMap<String, String>();


    /**
     *
     * @param key
     * @param ip
     */
    public void put(final String key,final String ip) {
        map.put(key, ip);
    }

    /**
     *
     * @param key
     * @return ip
     */
    public String get(String key) {
        return map.get(key);
    }

    /**
     *
     * @return ip list
     */
    public String list() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
        }
        return sb.toString();    
	}
    public void clear() {
        map.clear();
    }
}